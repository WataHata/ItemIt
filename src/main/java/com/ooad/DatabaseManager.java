package com.ooad;

import java.sql.*;

@SuppressWarnings("exports")
public class DatabaseManager {
    private static final String HOSTNAME = "clement.mysql.database.azure.com";
    private static final String PORT = "3306";
    private static final String DATABASE_NAME = "ooad_database";
    private static final String USERNAME = "clement";
    private static final String PASSWORD = "VV5MpBx5Rm"; 

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
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
    }
}
