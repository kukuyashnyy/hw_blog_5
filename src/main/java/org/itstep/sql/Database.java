package org.itstep.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {

    private static volatile Database instance;
    private static final String DB_URL = "jdbc:mysql://localhost";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String DB_NAME = "home_blog";
    private static final String USER_TABLE_NAME = "users";
    private static final String COMMENT_TABLE_NAME = "comments";

    private Database() {
        addDB(DB_NAME);
        addTable(DB_NAME, USER_TABLE_NAME,
                "id int primary key auto_increment," +
                        "login varchar(50)," +
                        "password varchar(50)");
        addTable(DB_NAME, COMMENT_TABLE_NAME,
                "text_id int primary key auto_increment, " +
                        "user_id int, " +
                        "constraint text_user_id foreign key (user_id) references " + DB_NAME + "." + USER_TABLE_NAME + "(id), " +
                        "text varchar(255)");
        addUser("user", "user");
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

    public boolean addComment(String login, String text) {
        int result = 0;
        int userId = getUserIdByLogin(login);
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "insert into " + DB_NAME + "." + COMMENT_TABLE_NAME + "(user_id, text) value ('" + userId + "', '" + text + "');";
            result = stmt.executeUpdate(query);
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
                Comment comment = new Comment(login, result.getString("text"));
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

    public static void main(String[] args) {
        Database userDB = new Database();
    }
}
