package com.ooad.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ooad.DatabaseManager;

public class TransactionDAO {
    
    public List<Transaction> getTransactionsByUserId(String userId)
    {
        List<Transaction> items = new ArrayList<>();
        String query = "SELECT * FROM Transactions WHERE user_id = ?"; 
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userId); 
            
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction(
                    resultSet.getString("transaction_id"),
                    resultSet.getString("user_id"),
                    resultSet.getString("item_id")
                );
                items.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public String generateUniqueTransactionId() {
        String query = "SELECT COUNT(*) FROM Transactions";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet resultSet = pstmt.executeQuery()) {
            
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return String.format("TRANS%06d", count + 1); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "TRANS000001"; 
    }

    public boolean insertTransaction(String itemId, String userId) {
        String query = "INSERT INTO Transactions (transaction_id, user_id, item_id) VALUES (?, ?, ?)";
        
        String transactionId = generateUniqueTransactionId(); 
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, transactionId);
            pstmt.setString(2, userId);
            pstmt.setString(3, itemId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }

    
}
