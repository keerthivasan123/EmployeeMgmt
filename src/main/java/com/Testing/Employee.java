package com.Testing;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class Employee {
    private String id,name,password,email,country;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    private static final String dburl = "jdbc:postgresql://localhost:5432/mydb";
    private static final String dbuser = "postgres";
    private static final String dbpassword = "postgres";

    private static final String INSERT_USERS_SQL = "INSERT INTO employee" +
            "  (id, name, email, country, password) VALUES " +
            " (?, ?, ?, ?, ?);";

    private static final String QUERY = "select id,name,email,country,password from employee where id =?";

    private static final String SELECT_ALL_QUERY = "select * from employee";

    private static final String UPDATE_USERS_SQL = "update employee set name = ?, email = ?, country = ?, password = ? where id = ?;";

    private static final String DELETE_USERS_SQL = "delete from employee where id = ?;";

    private static Gson gson = new Gson();

    Employee(){
    }

    Employee( String id, String name, String password, String email, String country ){
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.country = country;
    }

    Employee( String id ){
        this.id = id;
    }

    public static PreparedStatement getPreparedStatement( String query ) throws SQLException {
            Connection connection = DriverManager.getConnection(dburl, dbuser, dbpassword);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement;
    }

    public static String getEmployeeById( String id ){
        try {
            PreparedStatement preparedStatement = Employee.getPreparedStatement( QUERY );
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                String password = rs.getString("password");
                System.out.println(id + "," + name + "," + email + "," + country + "," + password);

                Employee emp = new Employee( id, name, password, email, country );

                String result = Employee.gson.toJson(emp);
                return result;
            }

            JsonObject result = new JsonObject();
            result.addProperty("error", "Employee with id: " + id + " Does't exists");
            return result.toString();

        } catch ( SQLException e ){
            return printSQLException(e);
        }
    }

    public static String getAllEmployee() {
        try {
            PreparedStatement preparedStatement = Employee.getPreparedStatement( SELECT_ALL_QUERY );
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Employee> listOfEmployee = new ArrayList<>();

            while (rs.next()) {
                String uuid = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                String password = rs.getString("password");
                System.out.println(uuid + "," + name + "," + email + "," + country + "," + password);

                Employee emp = new Employee( uuid, name, password, email, country );

                listOfEmployee.add(emp);
            }

            String result = Employee.gson.toJson(listOfEmployee);
            return result;
        } catch ( SQLException e ){
            return printSQLException(e);
        }
    }

    public String createEmployee(){
        try {
            PreparedStatement preparedStatement = Employee.getPreparedStatement( INSERT_USERS_SQL );
            preparedStatement.setString(1, this.id);
            preparedStatement.setString(2, this.name);
            preparedStatement.setString(3, this.email);
            preparedStatement.setString(4, this.country);
            preparedStatement.setString(5, this.password);

            preparedStatement.executeUpdate();

            JsonObject result = new JsonObject();
            result.addProperty("message", "Employee Successfully Created with id: " + this.id);
            return result.toString();
        } catch ( SQLException e ){
            return printSQLException(e);
        }
    }

    public String updateEmployeeById(){
        try {
            PreparedStatement preparedStatement = Employee.getPreparedStatement( UPDATE_USERS_SQL );
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.email);
            preparedStatement.setString(3, this.country);
            preparedStatement.setString(4, this.password);
            preparedStatement.setString(5, this.id);

            preparedStatement.executeUpdate();

            JsonObject result = new JsonObject();
            result.addProperty("message", "Employee Updated Successfully.");
            return result.toString();

        } catch ( SQLException e ){
            return printSQLException(e);
        }
    }

    public static String deleteEmployeeById( String id ){
        try {
            PreparedStatement preparedStatement = Employee.getPreparedStatement( DELETE_USERS_SQL );
            preparedStatement.setString(1, id);

            int affected = preparedStatement.executeUpdate();
            System.out.println("Number of records affected :: " + affected);
            JsonObject result = new JsonObject();

            if( affected == 0 ) {
                result.addProperty( "error", "Invalid Employee Id");
            } else {
                result.addProperty( "message", "Employee with id: " + id + " Successfully Deleted");
            }
            return result.toString();
        } catch ( SQLException e ){
            return printSQLException(e);
        }
    }

    public static String printSQLException( SQLException ex ) {
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
        return result.toString();
    }
}
