package org.itstep.Dao.Impl;

import org.itstep.Classes.Post;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PostDaoMySqlImpl implements GenericDao<Post, Integer> {

    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private final String DB_NAME;
    private final String USER_TABLE;
    private final String COMMENT_TABLE;
    private final String POST_TABLE;

    public PostDaoMySqlImpl() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mysql.properties"));

        this.URL = properties.getProperty("jdbc.url");
        this.USER = properties.getProperty("jdbc.username");
        this.PASSWORD = properties.getProperty("jdbc.password");
        this.DB_NAME = properties.getProperty("database.name");
        this.USER_TABLE = properties.getProperty("tables.users.name");
        this.COMMENT_TABLE = properties.getProperty("tables.comments.name");
        this.POST_TABLE = properties.getProperty("tables.posts.name");

        addDB(DB_NAME);
        addTable(DB_NAME, USER_TABLE,
                "id int primary key auto_increment," +
                        "login varchar(50)," +
                        "password varchar(50)");
        addTable(DB_NAME, POST_TABLE,
                "id int primary key auto_increment, " +
                        "user_id int, " +
                        "constraint post_user_id foreign key (user_id) references " + DB_NAME + "." + USER_TABLE + "(id), " +
                        "title varchar(50), " +
                        "text varchar(255), " +
                        "img_path varchar(255), " +
                        "draft bool, " +
                        "dateTime datetime"
        );
        addTable(DB_NAME, COMMENT_TABLE,
                "id int primary key auto_increment, " +
                        "user_id int, " +
                        "constraint comment_user_id foreign key (user_id) references " + DB_NAME + "." + USER_TABLE + "(id), " +
                        "post_id int, " +
                        "constraint comment_post_id foreign key (post_id) references " + DB_NAME + "." + POST_TABLE + "(id), " +
                        "text varchar(255)");
        addUser("user", "user");
//        Post post = new Post("MyPost1", "user", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Debitis dolorem dolores fuga qui. Ab animi doloribus error esse, expedita illo ipsa iste, nemo nesciunt numquam optio quisquam ratione sit soluta!", 0);
//        post.setImg("resources/upload/cat.jpg");
//        save(post);
//        save(new Post("MyPost2", "user", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Debitis dolorem dolores fuga qui. Ab animi doloribus error esse, expedita illo ipsa iste, nemo nesciunt numquam optio quisquam ratione sit soluta!\n", 0));
    }

    private void addDB(String dbName) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute("drop database if exists " + dbName);
            String query = "create database if not exists " + dbName;
            stmt.executeUpdate(query);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void addTable(String dbName, String tableName, String columnData) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "create table if not exists " + dbName + "." + tableName + "\n(" + columnData + ");";
            stmt.executeUpdate(query);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean isUserExist(String login) {
        int id = getUserIdByLogin(login);
        return id != 0;
    }

    private int getUserIdByLogin(String login) {
        int id = 0;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + DB_NAME + "." + USER_TABLE + " WHERE login = '" + login + "';";
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

    private String getLoginById(int id) {
        String login = null;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + DB_NAME + "." + USER_TABLE + " WHERE id = '" + id + "';";
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

    private int addUser(String login, String password) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "insert into " + DB_NAME + "." + USER_TABLE + "(login, password) value ('" + login + "', '" + password + "');";
            stmt.executeUpdate(query);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return getUserIdByLogin(login);
    }

    private Integer findByTitle(String title) {
        Post post = null;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + DB_NAME + "." + POST_TABLE + " WHERE title='" + title + "';";
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
        return post != null ? post.getId() : 0;
    }

    @Override
    public Integer save(Post data) throws SQLException {
        if (isUserExist(data.getAuthor())) {
            int userId = getUserIdByLogin(data.getAuthor());
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 Statement stmt = conn.createStatement()) {
                String query = "insert into " + DB_NAME + "." + POST_TABLE +
                        "(user_id, title, text, img_path, draft, dateTime) value " +
                        "('" +
                        userId + "', '" +
                        data.getTitle() + "', '" +
                        data.getText() + "', '" +
                        data.getImg() + "', '" +
                        data.getDraft() + "', '" +
                        data.getDateTime() +
                        "');";
                stmt.executeUpdate(query);
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findByTitle(data.getTitle());
    }

    @Override
    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + DB_NAME + "." + POST_TABLE + ";";
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

    @Override
    public Post findById(Integer id) {
        Post post = null;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM " + DB_NAME + "." + POST_TABLE + " WHERE id=" + id + ";";
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

    @Override
    public void delete(Integer id) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "DELETE FROM " + DB_NAME + "." + POST_TABLE + " WHERE id=" + id + ";";
            stmt.executeUpdate(query);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Post update(Post data) throws SQLException {
        if (isUserExist(data.getAuthor())) {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 Statement stmt = conn.createStatement()) {
                String query = "update " + DB_NAME + "." + POST_TABLE +
                        " " +
                        "SET title=" + "'" + data.getTitle() + "', " +
                        "text=" + "'" + data.getText() + "', " +
                        "img_path=" + "'" + data.getImg() + "', " +
                        "draft=" + "'" + data.getDraft() + "', " +
                        "dateTime=" + "'" + data.getDateTime() + "' " +
                        "WHERE id=" + data.getId() + ";";
                stmt.executeUpdate(query);
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(data.getId());
    }

    public static void main(String[] args) throws IOException, SQLException {
        PostDaoMySqlImpl postDaoMySql = new PostDaoMySqlImpl();
        System.out.println(postDaoMySql);
    }
}
