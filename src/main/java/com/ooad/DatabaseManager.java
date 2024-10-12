package com.ooad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ooad.Models.User;

@SuppressWarnings("exports")
public class DatabaseManager {
    private static final String HOSTNAME = "clement.mysql.database.azure.com";
    private static final String PORT = "3306";
    private static final String DATABASE_NAME = "ooad_database"; // Replace with your actual database name
    private static final String USERNAME = "clement";
    private static final String PASSWORD = "VV5MpBx5Rm"; // Replace with the actual password

    private static final String CONNECTION_STRING = 
        "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DATABASE_NAME + 
        "?useSSL=true&requireSSL=true&verifyServerCertificate=true";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }   
    }

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM User")) {

            while (rs.next()) {
                User user = new User(
                    rs.getString("user_id"),
                    rs.getString("username"),
                    rs.getString("phone_number"),
                    rs.getString("password")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
    }
}
