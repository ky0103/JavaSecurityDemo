package com.ssrf.demo;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@WebServlet("/Demo05")
public class SSRFDemo05 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getParameter("url");
        URL u = new URL(url);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream1 = u.openStream();
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream1);
        BufferedImage read = ImageIO.read(imageInputStream);
        ImageIO.write(read,"png",byteArrayOutputStream);
        InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        int length;
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
