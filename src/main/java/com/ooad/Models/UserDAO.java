package com.ooad.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ooad.DatabaseManager;

public class UserDAO {

    public boolean insertUser(String username, String password, String phoneNumber, String address, String role) {
        String userId = generateUniqueUserId();
        String query = "INSERT INTO User (user_id, username, password, phone_number, address, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, phoneNumber);
            pstmt.setString(5, address);
            pstmt.setString(6, role);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM User WHERE username = ? AND password = ?";   
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);        
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet findUserById(String id) {
        String query = "SELECT * FROM User WHERE id = ?";
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            return pstmt.executeQuery(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isUsernameUnique(String username) {
        String query = "SELECT * FROM User WHERE username = ?";
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String generateUniqueUserId() {
        String query = "SELECT COUNT(*) FROM User";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                int userCount = rs.getInt(1);
                return String.format("USER%06d", userCount + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
