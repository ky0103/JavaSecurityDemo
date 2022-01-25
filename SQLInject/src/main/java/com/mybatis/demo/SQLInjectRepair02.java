package com.mybatis.demo;

import com.mybatis.dao.UserMapper;
import com.mybatis.pojo.User;
import com.mybatis.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/Repair02")
public class SQLInjectRepair02 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectUserByLikeNameRepair(username);
        for (User user : users) {
            resp.getWriter().write(user.getUsername());
            resp.getWriter().write(user.getPassword());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
