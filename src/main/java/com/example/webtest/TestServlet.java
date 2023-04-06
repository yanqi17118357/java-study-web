package com.example.webtest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
// urlPatterns与value是一样效果
// loadOnStartup设置大于0会启动时加载
// 正数的值越小，优先级越高
@WebServlet(value = {"/test1", "/test2"}, loadOnStartup = 1)
public class TestServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("我被加载了");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("<h1>恭喜你解锁了全新玩法</h1>");
    }
}
