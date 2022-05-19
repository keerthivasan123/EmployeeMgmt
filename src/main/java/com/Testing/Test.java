package com.Testing;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.Scanner;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Test extends HttpServlet {

    private final String url = "jdbc:postgresql://localhost:5432/mydb";
    private final String user = "postgres";
    private final String password = "postgres";

    private static final String INSERT_USERS_SQL = "INSERT INTO employee" +
            "  (id, name, email, country, password) VALUES " +
            " (?, ?, ?, ?, ?);";

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

        } catch (SQLException e) {

            // print SQL exception information
            printSQLException(e);
        }
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
