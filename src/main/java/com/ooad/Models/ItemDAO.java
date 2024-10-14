package com.ooad.Models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ooad.DatabaseManager;

public class ItemDAO {
    
    
    public String generateUniqueItemId(){
        String query = "SELECT COUNT(*) FROM Item";
        
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery()) {
            
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return String.format("ITEM%06d", count + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ITEM000001";          
    }

    public boolean insertItem(String name, String size, String price, String category, String sellerID) {
        String query = "INSERT INTO Item (item_id, item_name, item_size, item_price, item_category, item_status, item_wishlist, item_offer_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        String itemId = generateUniqueItemId();
        
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, itemId);
            pstmt.setString(2, name);
            pstmt.setString(3, size);
            pstmt.setString(4, price);
            pstmt.setString(5, category);
            pstmt.setString(6, "Pending"); // Default status
            pstmt.setBoolean(7, false);      // Default wishlist status
            pstmt.setBoolean(8, false);      // Default offer status
            pstmt.setString(9, sellerID); 

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}