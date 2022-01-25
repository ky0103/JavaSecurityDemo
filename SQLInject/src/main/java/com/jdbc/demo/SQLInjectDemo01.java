package com.jdbc.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/Demo01")
public class SQLInjectDemo01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("GetMethod Inject Demo!\nPlease send param ?id=1 \n");
        String id = req.getParameter("id");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/security?characterEncoding=utf8&useSSL=false&serverTimezone=UTC", "root", "123456");
            String sql = "select * from users where id = '"+id+"'";
            resp.getWriter().write("The SQL statement:" + sql + "\n");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()){
                resp.getWriter().write(resultSet.getString("username")+"\n");
                resp.getWriter().write(resultSet.getString("password")+"\n");
            }
        }catch (Exception e){
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
