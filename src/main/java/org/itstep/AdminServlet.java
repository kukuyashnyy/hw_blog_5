package org.itstep;

import org.itstep.sql.Database;
import org.itstep.Classes.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@MultipartConfig
@WebServlet(urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {

    public static final String UPLOAD = "resources/upload/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        Database database = Database.getInstance();
        List<Post> posts = database.getPostsByLogin((String) req.getSession().getAttribute("user"));
        req.setAttribute("posts", posts);
        req.getRequestDispatcher("WEB-INF/view/admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
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

    private void getPost(HttpServletRequest req) throws IOException, ServletException {
        System.out.println(req.getParameter("title"));
        System.out.println(req.getParameter("post_author"));
        System.out.println(req.getParameter("text"));
        System.out.println(req.getParameter("draft"));

        final String title = req.getParameter("title");
        final String login = req.getParameter("post_author");
        final String text = req.getParameter("text");
        final int draft = req.getParameter("draft") != null ? 1 : 0;

        if (title != null &&
                login != null &&
                text != null &&
                req.getPart("picture").getHeader("Content-Type").matches(".*?\\bimage\\b.*?")) {
            Optional<String> file = saveFile(req.getPart("picture"));
            Post post = new Post(title, login, text, draft);
            file.ifPresent(s -> post.setImg(req.getContextPath() + UPLOAD + s));
            Database database = Database.getInstance();
            database.addPost(post);
        }
    }
    private Optional<String> saveFile(Part part) throws IOException {
        if (part == null) return Optional.empty();
        String contentDisposition = part.getHeader("Content-Disposition");
        int start = contentDisposition.indexOf("filename=");
        start += "filename=".length();
        int end = contentDisposition.lastIndexOf("\"");
        String filename = contentDisposition.substring(start + 1, end);
        String uploadsDirUrl = getServletContext().getRealPath(UPLOAD);
        String absolutePathToFile = uploadsDirUrl + "/" + filename;
        part.write(absolutePathToFile);
        return Optional.of(filename);
    }
}
