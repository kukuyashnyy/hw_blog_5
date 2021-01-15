package org.itstep;

import org.itstep.sql.Comment;
import org.itstep.sql.Database;
import org.itstep.sql.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Database database = Database.getInstance();
        List<Post> posts = database.getPostsByLogin((String) req.getSession().getAttribute("user"));
        req.setAttribute("posts", posts);
        req.getRequestDispatcher("WEB-INF/view/admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getPost(req);
        deletePost(req);
        resp.sendRedirect(req.getContextPath() + "/admin");
    }

    private void deletePost(HttpServletRequest req) {
        if (req.getParameter("delete_post_id") != null) {
            Database database = Database.getInstance();
            database.deletePost(Integer.parseInt(req.getParameter("delete_post_id")));
        }
    }

    private void getPost(HttpServletRequest req) {
        System.out.println(req.getParameter("title"));
        System.out.println(req.getParameter("post_author"));
        System.out.println(req.getParameter("text"));
        System.out.println(req.getParameter("draft"));

        final String title = req.getParameter("title");
        final String login = req.getParameter("post_author");
        final String text = req.getParameter("text");
        final int draft = req.getParameter("draft") != null ? 1 : 0;

        if (title != null && login != null && text != null) {
            Post post = new Post(title, login, text, draft);
            Database database = Database.getInstance();
            database.addPost(post);
        }
    }
}
