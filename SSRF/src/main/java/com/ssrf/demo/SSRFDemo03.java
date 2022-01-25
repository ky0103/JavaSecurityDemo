package com.ssrf.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@WebServlet("/Demo03")
public class SSRFDemo03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getParameter("url");
        int length;
        URL u = new URL(url);
        InputStream inputStream = u.openStream();
        byte[] bytes = new byte[1024];
        while ((length = (inputStream.read(bytes))) > 0){
            resp.getOutputStream().write(bytes,0,length);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
