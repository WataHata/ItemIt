package com.ooad.Controllers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UploadController {

    public static String generateUniqueItemId(Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) FROM Item";
        
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return String.format("ITEM%06d", count + 1);
            }
        }
        
        // If there's an error or no results, return a default ID
        return "ITEM000001";
            
    }

    public static boolean createItem(Connection connection, String name, String size, BigDecimal price, String category) throws SQLException {
        String insertQuery = "INSERT INTO Item (item_id, item_name, item_size, item_price, item_category, item_status, item_wishlist, item_offer_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        String itemId = generateUniqueItemId(connection);
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setString(1, itemId);
            pstmt.setString(2, name);
            pstmt.setString(3, size);
            pstmt.setBigDecimal(4, price);
            pstmt.setString(5, category);
            pstmt.setString(6, "Pending"); // Default status
            pstmt.setBoolean(7, false);      // Default wishlist status
            pstmt.setBoolean(8, false);      // Default offer status
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public static String validateInputs(String name, String category, String size, String price) {
        StringBuilder errors = new StringBuilder();

        // Validate name
        if (name == null || name.trim().isEmpty()) {
            errors.append("Name cannot be empty.\n");
        } else if (name.length() < 3) {
            errors.append("Name must be at least 3 characters long.\n");
        }

        // Validate category
        if (category == null || category.trim().isEmpty()) {
            errors.append("Category cannot be empty.\n");
        } else if (category.length() != 3) {
            errors.append("Category must be exactly 3 characters long.\n");
        }

        // Validate size
        if (size == null || size.trim().isEmpty()) {
            errors.append("Size cannot be empty.\n");
        }

        // Validate price
        if (price == null || price.trim().isEmpty()) {
            errors.append("Price cannot be empty.\n");
        } else {
            try {
                double priceValue = Double.parseDouble(price);
                if (priceValue <= 0) {
                    errors.append("Price must be a number greater than 0.\n");
                }
            } catch (NumberFormatException e) {
                errors.append("Price must be a valid number.\n");
            }
        }

        return errors.toString();
    }

    




}

