package org.itstep.sql;

import java.sql.*;

public class UserDB {

    private static final String URL = "jdbc:mysql://localhost";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private String dbName;
    private String tableName;

    public UserDB() {
        new UserDB("homeblog", "users");
    }

    public UserDB(String dbName, String tableName) {
        this.dbName = dbName;
        this.tableName = tableName;
        initDB();
    }

    private void initDB() {
        addDB();
        addTable();
        addUser("user", "user");
    }

    public boolean addUser(String login, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            int result;
            String query;
            query = "insert into " + dbName + "." + tableName + " (login, password) value\n" +
                    "(login, password);";
            result = stmt.executeUpdate(query);
            System.out.println((result == 1) ? "User was created" : "User exist or cant create");
            conn.close();
            return (result == 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    private boolean addTable() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            int result;
            String query;
            query = "create table if not exists " + dbName + "." + tableName + "\n" +
                    "(\n" +
                    "id int primary key auto_increment,\n" +
                    "login varchar(50),\n" +
                    "password varchar(50)\n" +
                    ");";
            result = stmt.executeUpdate(query);
            System.out.println((result == 0) ? "Table was created" : "Table exist or cant create");
            conn.close();
            return (result == 0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return (false);
        }
    }

    private boolean addDB() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
//            drop table
            stmt.execute("drop database if exists " + dbName);
            int result;
            String query;
            query = "create database if not exists " + dbName;
            result = stmt.executeUpdate(query);
            System.out.println((result == 1) ? "DB was created" : "DB exist or cant create");
            conn.close();
            return (result == 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

        public static void main (String[]args){
            UserDB userDB = new UserDB();
        }
    }
