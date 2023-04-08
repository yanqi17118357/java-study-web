package com.example.webtest;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.io.Resources;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// 没有M13g这个注解会上传文件返回500状态码
@MultipartConfig
@WebServlet("/file")
public class FileServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try(FileOutputStream stream = new FileOutputStream("D:\\sonna\\Desktop\\test.png")) {
            Part part = req.getPart("test-file");
            IOUtils.copy(part.getInputStream(), stream);
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("文件上传成功");
        }

    }
}
