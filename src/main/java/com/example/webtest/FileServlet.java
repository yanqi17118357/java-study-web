package com.example.webtest;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet("/file")
public class FileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("image/png");
        OutputStream outputStream = resp.getOutputStream();
        InputStream inputStream = Resources.getResourceAsStream("icon.png");
        IOUtils.copy(inputStream, outputStream);
    }
}
