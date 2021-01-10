package org.itstep;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(urlPatterns = "/post")
public class PostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (authorized(req)) {
            req.getRequestDispatcher("WEB-INF/view/post.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }

    public boolean authorized(HttpServletRequest req) {
        if (req.getCookies() == null) return false;
        return Arrays.stream(req.getCookies())
                .anyMatch(c -> "admin".equals(c.getName()) && "true".equals(c.getValue()));
    }
}
