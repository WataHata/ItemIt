package com.ooad.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ooad.DatabaseManager;

public class WishlistDAO {
    public boolean addItemToWishlist(String userId, String itemId) {
        String query = "INSERT INTO wishlist (user_id, item_id) VALUES (?, ?)";
        
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, itemId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeItemFromWishlist(String wishlistId) {
        String query = "DELETE FROM wishlist WHERE wishlist_id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, wishlistId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String generateUniqueWishlistId() {
        String query = "SELECT COUNT(*) FROM Wishlist";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet resultSet = pstmt.executeQuery()) {
             
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return String.format("WISHLIST%06d", count + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "WISHLIST000001"; 
    }

    public List<Wishlist> getWishlistsByUserId(String userId) {
        List<Wishlist> wishlists = new ArrayList<>();
        String query = "SELECT wishlist_id, user_id, item_id FROM Wishlist WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userId);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String wishlistId = resultSet.getString("wishlist_id");
                String itemId = resultSet.getString("item_id");
                wishlists.add(new Wishlist(wishlistId, userId, itemId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishlists; 
    }

    public boolean removeAllWishlistsByItemId(String itemId) {
        String query = "DELETE FROM Wishlist WHERE item_id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, itemId);
    
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }

}
