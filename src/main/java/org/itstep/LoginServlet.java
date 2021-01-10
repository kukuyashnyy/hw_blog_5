package org.itstep;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private final String LOGIN = "admin";
    private final String PASSWORD = "123";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("login"));
        System.out.println(req.getParameter("password"));
        if(req.getParameter("login") != null && req.getParameter("password") != null) {
            if(LOGIN.equals(req.getParameter("login")) && PASSWORD.equals(req.getParameter("password"))) {
                // Response with header 'Set-Cookie: admin=true; HttpOnly'
                Cookie cookie = new Cookie("admin", "true");
                cookie.setMaxAge(3600*24*30);
                cookie.setHttpOnly(true);
                resp.addCookie(cookie);
                resp.sendRedirect(req.getContextPath() + "/post");
                return;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
