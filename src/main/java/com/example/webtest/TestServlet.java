package com.example.webtest;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.Enumeration;

@Log
@WebServlet("/test")
public class TestServlet implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        //转换为HttpServletResponse（同上）
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //设定内容类型以及编码格式（普通HTML文本使用text/html，之后会讲解文件传输）
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        //获取Writer直接写入内容
        response.getWriter().write("我是响应内容！");
        //所有内容写入完成之后，再发送给浏览器
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
    }
}
