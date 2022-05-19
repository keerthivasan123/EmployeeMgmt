package com.Testing;

import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

public class EmployeeServlet extends HttpServlet  {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--get--");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();

        String id = req.getParameter("id");

        if( id != null ) {
            String result = Employee.getEmployeeById( id );
            printWriter.print(result);
        } else {
            String result = Employee.getAllEmployee();
            printWriter.print(result);
        }
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--post--");
        UUID uuid=UUID.randomUUID();
        String id = uuid.toString();
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String country = req.getParameter("country");

        System.out.println("Body: " + id + " " + name + " " + password + " " + email + " " + country );

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();
        Employee employee = new Employee( id, name, password, email, country);
        String result = employee.createEmployee();
        printWriter.print(result);
        printWriter.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--put--");

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String psw = req.getParameter("password");
        String email = req.getParameter("email");
        String country = req.getParameter("country");

        System.out.println("Body: " + id + " " + name + " " + psw + " " + email + " " + country );

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();
        Employee employee = new Employee( id, name, psw, email, country);
        String result = employee.updateEmployeeById();
        printWriter.print(result);
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--delete--");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();

        String id = req.getParameter("id");

        String result = Employee.deleteEmployeeById( id );
        printWriter.print(result);
        printWriter.flush();
    }
}
