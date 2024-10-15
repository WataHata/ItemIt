package com.ooad.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            pstmt.setString(6, "pending"); // Default status
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

    public boolean approveItem(String itemId) {
        String query = "UPDATE Item SET item_status = ? WHERE item_id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "Approved");
            pstmt.setString(2, itemId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public boolean declineItem(String itemId, String reason) {
        String query = "UPDATE Item SET item_status = ?, reason = ? WHERE item_id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "Declined");
            pstmt.setString(2, reason);
            pstmt.setString(3, itemId);
    
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Item> getItemsWithStatus(String status) {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM Item WHERE item_status = '" + status + "'";
        // String query = "SELECT * FROM Item";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet resultSet = pstmt.executeQuery();
                
            while (resultSet.next()) {
                String itemId = resultSet.getString("item_id");
                String itemName = resultSet.getString("item_name");
                String itemSize = resultSet.getString("item_size");
                String itemPrice = resultSet.getString("item_price");
                String itemCategory = resultSet.getString("item_category");
                String itemStatus = resultSet.getString("item_status");
                String itemWishlist = resultSet.getString("item_wishlist");
                String itemOfferStatus = resultSet.getString("item_offer_status");
                String sellerId = resultSet.getString("seller_id");
                String reason = resultSet.getString("reason");

                Item item = new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus, sellerId);
                item.setReason(reason); 
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(query);
        System.out.println("GETTING ITEMSS");
        return items; // Return the list of pending items
    }



}
