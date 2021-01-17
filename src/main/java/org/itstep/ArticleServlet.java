package org.itstep;

import org.itstep.sql.Comment;
import org.itstep.sql.Database;
import org.itstep.sql.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.tree.FixedHeightLayoutCache;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/article")
public class ArticleServlet extends HttpServlet {
    private int id;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        id = Integer.parseInt(req.getParameter("id"));
        Database database = Database.getInstance();
        req.setAttribute("post", database.getPostById(id));
        req.setAttribute("comments", database.getCommentsByPostId(id));
        req.getRequestDispatcher("/WEB-INF/view/article.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        getComment(req);
        resp.sendRedirect(req.getContextPath() + "/article?id=" + id);
    }

    private void getComment(HttpServletRequest req) {
        if (req.getParameter("post_id") != null &&
                req.getParameter("comment_author") != null &&
                req.getParameter("post_text") != null) {
            Comment comment = new Comment(0,
                    Integer.parseInt(req.getParameter("post_id")),
                    req.getParameter("comment_author"),
                    req.getParameter("post_text"));
            Database database = Database.getInstance();
            database.addComment(comment);
        }
    }
}
