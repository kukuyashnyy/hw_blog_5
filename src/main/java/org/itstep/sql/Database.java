package org.itstep.sql;

import org.itstep.Classes.Comment;
import org.itstep.Classes.Post;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static volatile Database instance;
    private static final String DB_URL = "jdbc:mysql://localhost";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String DB_NAME = "home_blog";
    private static final String USER_TABLE_NAME = "users";
    private static final String COMMENT_TABLE_NAME = "comments";
    private static final String POST_TABLE_NAME = "posts";

    private Database() {
        addDB(DB_NAME);
        addTable(DB_NAME, USER_TABLE_NAME,
                "id int primary key auto_increment," +
                        "login varchar(50)," +
                        "password varchar(50)");
        addTable(DB_NAME, POST_TABLE_NAME,
                "id int primary key auto_increment, " +
                        "user_id int, " +
                        "constraint post_user_id foreign key (user_id) references " + DB_NAME + "." + USER_TABLE_NAME + "(id), " +
                        "title varchar(50), " +
                        "text varchar(255), " +
                        "img_path varchar(255), " +
                        "draft bool, " +
                        "dateTime datetime"
        );
        addTable(DB_NAME, COMMENT_TABLE_NAME,
                "id int primary key auto_increment, " +
                        "user_id int, " +
                        "constraint comment_user_id foreign key (user_id) references " + DB_NAME + "." + USER_TABLE_NAME + "(id), " +
                        "post_id int, " +
                        "constraint comment_post_id foreign key (post_id) references " + DB_NAME + "." + POST_TABLE_NAME + "(id), " +
                        "text varchar(255)");
        addUser("user", "user");
        Post post = new Post("MyPost1", "user", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Debitis dolorem dolores fuga qui. Ab animi doloribus error esse, expedita illo ipsa iste, nemo nesciunt numquam optio quisquam ratione sit soluta!", 0);
        post.setImg("resources/upload/cat.jpg");
        addPost(post);
        addPost(new Post("MyPost2", "user", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Debitis dolorem dolores fuga qui. Ab animi doloribus error esse, expedita illo ipsa iste, nemo nesciunt numquam optio quisquam ratione sit soluta!\n", 0));
    }

    public static Database getInstance() {
        Database result = instance;
        if (result != null) {
            return result;
        }
        synchronized (Database.class) {
            if (instance == null) {
                instance = new Database();
            }
            return instance;
        }
    }

    public boolean addUser(String login, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            int result;
            String query = "insert into " + DB_NAME + "." + USER_TABLE_NAME + "(login, password) value ('" + login + "', '" + password + "');";
            result = stmt.executeUpdate(query);
            conn.close();
            return (result == 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean addPost(Post post) {
        int result = 0;
        int userId = getUserIdByLogin(post.getAuthor());
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "insert into " + DB_NAME + "." + POST_TABLE_NAME +
                    "(user_id, title, text, img_path, draft, dateTime) value " +
                    "('" +
                    userId + "', '" +
                    post.getTitle() + "', '" +
                    post.getText() + "', '" +
                    post.getImg() + "', '" +
                    post.getDraft() + "', '" +
                    post.getDateTime() +
                    "');";
            result = stmt.executeUpdate(query);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return (result == 1);
    }

    public boolean addComment(Comment comment) {
        int result = 0;
        int userId = getUserIdByLogin(comment.getAuthorName());
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "insert into " + DB_NAME + "." + COMMENT_TABLE_NAME + "(user_id, post_id, text) value ('" +
                    userId + "', '" +
                    comment.getPost_id() + "', '" +
                    comment.getText() + "');";
            result = stmt.executeUpdate(query);
//            System.out.println("Add comment: " + comment.toString());
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return (result == 1);
    }

    private boolean addTable(String dbName, String tableName, String columnData) {
        int result = 0;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "create table if not exists " + dbName + "." + tableName + "\n(" + columnData + ");";
            result = stmt.executeUpdate(query);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return (result == 0);
    }

    private boolean addDB(String dbName) {
        int result = 0;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
//            drop table
            stmt.execute("drop database if exists " + dbName);
            String query = "create database if not exists " + dbName;
            result = stmt.executeUpdate(query);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return (result == 1);
    }

    public boolean isUserExist(String login, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + DB_NAME + "." + USER_TABLE_NAME + " WHERE login = '" + login + "' AND password = '" + password + "';";
            ResultSet result = stmt.executeQuery(query);
            boolean isExist = result.next();
            conn.close();
            return (isExist);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public int getUserIdByLogin(String login) {
        int id = 0;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + DB_NAME + "." + USER_TABLE_NAME + " WHERE login = '" + login + "';";
            ResultSet result = stmt.executeQuery(query);
            if (result.next()) {
                id = Integer.parseInt(result.getString("id"));
            }
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public String getLoginById(int id) {
        String login = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + DB_NAME + "." + USER_TABLE_NAME + " WHERE id = '" + id + "';";
            ResultSet result = stmt.executeQuery(query);
            if (result.next()) {
                login = result.getString("login");
            }
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return login;
    }

    public List<Comment> getComments() {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + DB_NAME + "." + COMMENT_TABLE_NAME + ";";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                String login = getLoginById(Integer.parseInt(result.getString("user_id")));
                Comment comment = new Comment(Integer.parseInt(result.getString("id")),
                        Integer.parseInt(result.getString("post_id")),
                        getLoginById(Integer.parseInt(result.getString("user_id"))),
                        result.getString("text"));
//                System.out.println("Login: " + comment.getAuthorName());
//                System.out.println("Text: " + comment.getText());
                comments.add(comment);
            }
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return comments;
    }

    public List<Comment> getCommentsByPostId(int id) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + DB_NAME + "." + COMMENT_TABLE_NAME + " WHERE post_id=" + id + ";";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                Comment comment = new Comment(Integer.parseInt(result.getString("id")),
                        Integer.parseInt(result.getString("post_id")),
                        getLoginById(Integer.parseInt(result.getString("user_id"))),
                        result.getString("text"));
//                System.out.println("Get comment:" + comment.toString());
                comments.add(comment);
            }
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return comments;
    }

    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + DB_NAME + "." + POST_TABLE_NAME + ";";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                Post post = new Post(
                        Integer.parseInt(result.getString("id")),
                        result.getString("title"),
                        getLoginById(Integer.parseInt(result.getString("user_id"))),
                        result.getString("dateTime"),
                        result.getString("text"),
                        Integer.parseInt(result.getString("draft"))
                );
                post.setImg(result.getString("img_path"));
                posts.add(post);
            }
            conn.close();
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return posts;
    }

    public List<Post> getPostsByLogin(String login) {
        int userId = getUserIdByLogin(login);
        List<Post> posts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + DB_NAME + "." + POST_TABLE_NAME + " WHERE user_id=" + userId + ";";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                Post post = new Post(
                        Integer.parseInt(result.getString("id")),
                        result.getString("title"),
                        getLoginById(Integer.parseInt(result.getString("user_id"))),
                        result.getString("dateTime"),
                        result.getString("text"),
                        Integer.parseInt(result.getString("draft"))
                );
                post.setImg(result.getString("img_path"));
                posts.add(post);
            }
            conn.close();
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return posts;
    }

    public Post getPostById(int id) {
        Post post = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + DB_NAME + "." + POST_TABLE_NAME + " WHERE id=" + id + ";";
            ResultSet result = stmt.executeQuery(query);
            if (result.next()) {
                post = new Post(
                        Integer.parseInt(result.getString("id")),
                        result.getString("title"),
                        getLoginById(Integer.parseInt(result.getString("user_id"))),
                        result.getString("dateTime"),
                        result.getString("text"),
                        Integer.parseInt(result.getString("draft"))
                );
                post.setImg(result.getString("img_path"));
            }
            conn.close();
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    public boolean deletePost(int id) {
        int result = 0;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "DELETE FROM " + DB_NAME + "." + POST_TABLE_NAME + " WHERE id=" + id + ";";
            result = stmt.executeUpdate(query);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return (result == 1);
    }

    public static void main(String[] args) {
        Database userDB = new Database();
        for (Post post :
                userDB.getPosts()) {
            System.out.println(post.toString());
        }
    }
}
