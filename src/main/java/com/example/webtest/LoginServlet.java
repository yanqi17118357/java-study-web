package com.example.webtest;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.Map;

@WebServlet(value = "/login", loadOnStartup = 1)
public class LoginServlet extends HttpServlet {

    SqlSessionFactory factory;

    @SneakyThrows
    @Override
    public void init() throws ServletException {
        factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        // 获取POST请求携带的表单数据
        Map<String, String[]> map = req.getParameterMap();
        //判断表单是否完整
        if (map.containsKey("username") && map.containsKey("password")) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            try(SqlSession sqlSession = factory.openSession(true)) {
                UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                User user = userMapper.getUser(username, password);

                if (user != null) {

                    // 使用重定向到time页面
                    // * 请求转发是一次请求，重定向是两次请求
                    // * 请求转发地址栏不会发生改变， 重定向地址栏会发生改变
                    // * 请求转发可以共享请求参数 ，重定向之后，就获取不了共享参数了
                    // * 请求转发只能转发给内部的Servlet
                    resp.sendRedirect("time");
                } else {
                    resp.getWriter().write("您登录的用户密码不正确或此用户不存在");
                }
            }

        }else {
            resp.getWriter().write("错误，您的表单数据不完整！");
        }
    }
}
