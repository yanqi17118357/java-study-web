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

                    // 服务器内部的跳转机制
                    // 直接将本次请求转发给其他Servlet进行处理
                    // 并由其他Servlet来返回结果
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("/time").forward(req, resp);
                } else {
                    resp.getWriter().write("您登录的用户密码不正确或此用户不存在");
                }
            }

        }else {
            resp.getWriter().write("错误，您的表单数据不完整！");
        }
    }
}
