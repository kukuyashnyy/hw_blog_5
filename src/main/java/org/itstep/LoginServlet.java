package org.itstep;

import org.itstep.sql.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private final Database database = Database.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println(req.getParameter("login"));
//        System.out.println(req.getParameter("password"));
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if(login != null && password != null) {
            if(database.isUserExist(login, password)) {
                HttpSession session = req.getSession();
                session.setAttribute("user", login);
                resp.sendRedirect(req.getContextPath() + "/admin");
                return;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
