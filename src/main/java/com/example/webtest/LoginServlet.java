package com.example.webtest;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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

// 此初始化参数只作用域当前的Servlet
@WebServlet(value = "/login", loadOnStartup = 1, initParams = {
        @WebInitParam(name = "test", value = "我是初始化参数！")
})
public class LoginServlet extends HttpServlet {

    SqlSessionFactory factory;

    @SneakyThrows
    @Override
    public void init() {
        System.out.println(getInitParameter("test"));
        // 打印全局初始化参数
        System.out.println(getServletContext().getInitParameter("lbwnb"));
        factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            String username = null;
            String password = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) username = cookie.getValue();
                if (cookie.getName().equals("password")) password = cookie.getValue();
            }
            if (username != null && password != null) {
                try(SqlSession sqlSession = factory.openSession(true)) {
                    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                    User user = mapper.getUser(username, password);
                    if (user != null) {
                        resp.sendRedirect("time");
                        return ;
                    } else {
                        Cookie cookie_username = new Cookie("username", username);
                        cookie_username.setMaxAge(0);
                        Cookie cookie_password = new Cookie("password", password);
                        cookie_password.setMaxAge(0);
                        resp.addCookie(cookie_username);
                        resp.addCookie(cookie_password);
                    }
                }
            }
        }
        req.getRequestDispatcher("/").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        // 获取POST请求携带的表单数据
        Map<String, String[]> map = req.getParameterMap();
        // 判断表单是否完整
        if (map.containsKey("username") && map.containsKey("password")) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            try(SqlSession sqlSession = factory.openSession(true)) {
                UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                User user = userMapper.getUser(username, password);

                if (user != null) {
                    if(map.containsKey("remember-me")){   //若勾选了勾选框，那么会此表单信息
                        Cookie cookie_username = new Cookie("username", username);
                        cookie_username.setMaxAge(30);
                        Cookie cookie_password = new Cookie("password", password);
                        cookie_password.setMaxAge(30);
                        resp.addCookie(cookie_username);
                        resp.addCookie(cookie_password);
                    }
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
