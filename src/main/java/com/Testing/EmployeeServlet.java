package com.Testing;

import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class EmployeeServlet extends HttpServlet  {
    private final String url = "jdbc:postgresql://localhost:5432/mydb";
    private final String user = "postgres";
    private final String password = "postgres";

    private static final String INSERT_USERS_SQL = "INSERT INTO employee" +
            "  (id, name, email, country, password) VALUES " +
            " (?, ?, ?, ?, ?);";

    private static final String QUERY = "select id,name,email,country,password from employee where id =?";

    private static final String SELECT_ALL_QUERY = "select * from employee";

    private static final String UPDATE_USERS_SQL = "update employee set name = ?, email = ?, country = ?, password = ? where id = ?;";

    private static final String DELETE_USERS_SQL = "delete from employee where id = ?;";

    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--post--");
        String id = req.getParameter("id");
        if( id != null ) {
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(QUERY);) {
                preparedStatement.setString(1, id);
                System.out.println(preparedStatement);

                ResultSet rs = preparedStatement.executeQuery();
                PrintWriter printWriter=resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                int flag = 1;
                // Step 4: Process the ResultSet object.
                while (rs.next()) {
                    flag = 0;
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String country = rs.getString("country");
                    String password = rs.getString("password");
                    System.out.println(id + "," + name + "," + email + "," + country + "," + password);

                    Employee emp = new Employee();
                    emp.setId(id);
                    emp.setName(name);
                    emp.setCountry(country);
                    emp.setPassword(password);
                    emp.setEmail(email);

                    String result = this.gson.toJson(emp);
                    printWriter.print(result);
                }

                if( flag == 1 ) {
                    JsonObject result = new JsonObject();
                    result.addProperty("error", "Employee with id: " + id + " Does't exists");

                    printWriter.print(result.toString());
                }
                printWriter.flush();
            } catch (SQLException e) {
                printSQLException(e, resp);
            }
        } else {
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);) {
                System.out.println(preparedStatement);

                ResultSet rs = preparedStatement.executeQuery();
                PrintWriter printWriter=resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                ArrayList<Employee> listOfEmployee = new ArrayList<>();

                while (rs.next()) {
                    String uuid = rs.getString("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String country = rs.getString("country");
                    String password = rs.getString("password");
                    System.out.println(uuid + "," + name + "," + email + "," + country + "," + password);

                    Employee emp = new Employee();
                    emp.setId(uuid);
                    emp.setName(name);
                    emp.setCountry(country);
                    emp.setPassword(password);
                    emp.setEmail(email);

                    listOfEmployee.add(emp);
                }

                String result = this.gson.toJson(listOfEmployee);
                printWriter.print(result);
                printWriter.flush();

            } catch (SQLException e) {
                printSQLException(e, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--post--");
        UUID uuid=UUID.randomUUID();
        String id = uuid.toString();
        String name = req.getParameter("name");
        String psw = req.getParameter("password");
        String email = req.getParameter("email");
        String country = req.getParameter("country");

        System.out.println("Body: " + id + " " + name + " " + psw + " " + email + " " + country );

        System.out.println(INSERT_USERS_SQL);
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, country);
            preparedStatement.setString(5, psw);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

            PrintWriter printWriter=resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            JsonObject result = new JsonObject();
            result.addProperty("message", "Employee Successfully Created with id: " + id);

            printWriter.print(result.toString());
            printWriter.flush();
        } catch (SQLException e) {

            // print SQL exception information
            printSQLException(e, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(UPDATE_USERS_SQL);
        PrintWriter printWriter=resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        JsonObject result = new JsonObject();

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String psw = req.getParameter("password");
        String email = req.getParameter("email");
        String country = req.getParameter("country");

        System.out.println("Body: " + id + " " + name + " " + psw + " " + email + " " + country );
        if( id != null ) {
            try (Connection connection = DriverManager.getConnection(url, user, password);

                 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, country);
                preparedStatement.setString(4, psw);
                preparedStatement.setString(5, id);

                preparedStatement.executeUpdate();

                result.addProperty("message", "Employee Updated Successfully.");
            } catch (SQLException e) {

                // print SQL exception information
                printSQLException(e, resp);
            }
        } else {
            result.addProperty("error", "Please Provide Employee Id");
        }

        printWriter.print(result.toString());
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(DELETE_USERS_SQL);
        PrintWriter printWriter=resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        JsonObject result = new JsonObject();

        String id = req.getParameter("id");

        if( id != null ){
            try (Connection connection = DriverManager.getConnection(url, user, password);

                 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL);) {
                preparedStatement.setString(1, id);

                int affected = preparedStatement.executeUpdate();
                System.out.println("Number of records affected :: " + affected);

                if( affected == 0 ) {
                    result.addProperty( "error", "Invalid Employee Id");
                } else {
                    result.addProperty( "message", "Employee with id: " + id + " Successfully Deleted");
                }
            } catch (SQLException e) {

                printSQLException(e, resp);
            }
        } else {
            result.addProperty("error", "Please Provide Employee Id");
        }
        printWriter.print(result.toString());
        printWriter.flush();
    }

    public static void printSQLException(SQLException ex, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter=resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        JsonObject result = new JsonObject();
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                result.addProperty("SQLState", ((SQLException) e).getSQLState().toString());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                result.addProperty("ErrorCode", ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                result.addProperty("message", e.getMessage().toString());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    result.addProperty("Cause", t.getMessage().toString());
                    t = t.getCause();
                }
            }
        }
        printWriter.print(result.toString());
        printWriter.flush();
    }
}
