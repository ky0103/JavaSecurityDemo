package com.ssrf.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@WebServlet("/Demo02")
public class SSRFDemo02 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getParameter("url");
        if (url != null){
            URL url1 = new URL(url);
            String htmlContent;
            URLConnection urlConnection = url1.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuffer html = new StringBuffer();
            while ((htmlContent = bufferedReader.readLine()) != null){
                html.append(htmlContent);
            }
            bufferedReader.close();
            resp.getWriter().println(html);
        }else {
            resp.getWriter().write("?url");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
