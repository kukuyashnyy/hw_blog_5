package org.itstep;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
//        Cookie cookie = new Cookie("admin", "false");
//        cookie.setMaxAge(-1);
//        resp.addCookie(cookie);
        HttpSession session = req.getSession();
        session.setAttribute("user", null);
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
