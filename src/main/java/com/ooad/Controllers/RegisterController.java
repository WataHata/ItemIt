package com.ooad.Controllers;

import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.ooad.DatabaseManager;
import com.ooad.MainApplication;

@SuppressWarnings({"exports", "unused"})

public class RegisterController {
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField phoneNumberField;
    private TextArea addressArea;
    private ToggleGroup roleGroup;
    private Text messageText;
    private VBox radioButtonContainer;
    private MainApplication mainApp;
    

    public RegisterController(MainApplication mainApp, Text referenceText) {
        this.mainApp = mainApp;
        messageText = referenceText;
    }

    public void Register(String username, String password, String phoneNumber, String address, String role) {
        if (validateInputs(username, password, phoneNumber, address)) {
            if (isUsernameUnique(username)) {
                if (registerUser(username, password, phoneNumber, address, role)) {
                    messageText.setText("Registration successful!");
                } else {
                    messageText.setText("Registration failed. Please try again.");
                }
            } else {
                messageText.setText("Username already exists. Please choose a different username.");
            }
        }  
    }

    private boolean validateInputs(String username, String password, String phoneNumber, String address) {
        System.out.println("Validating inputs...");

        if (username.isEmpty() || username.length() < 3) {
            messageText.setText("Username must be at least 3 characters long.");
            return false;
        }
        System.out.println("Username: " + username);

        if (password.isEmpty() || password.length() < 8 || !Pattern.compile("[!@#$%^&*]").matcher(password).find()) {
            messageText.setText("Password must be at least 8 characters long and include a special character (!@#$%^&*).");
            return false;
        }
        System.out.println("Password: " + password);

        if (!phoneNumber.matches("\\+62\\d{10}")) {
            messageText.setText("Phone number must start with +62 and be followed by 10 digits.");
            return false;
        }
        System.out.println("Phone Number: " + phoneNumber);

        if (address.isEmpty()) {
            messageText.setText("Address cannot be empty.");
            return false;
        }
        System.out.println("Address: " + address);

        return true;
    }

    private boolean isUsernameUnique(String username) {
        String query = "SELECT * FROM User WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                return !rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            messageText.setText("Database error occurred");
            return false;
        }
    }

    private boolean registerUser(String username, String password, String phoneNumber, String address, String role) {
        String userId = generateUniqueUserId();
        if (userId == null) {
            messageText.setText("Failed to generate user ID");
            return false;
        }
    
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
            messageText.setText("Database error occurred");
            return false;
        }
    }

    private String generateUniqueUserId() {
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
            messageText.setText("Error generating user ID");
        }
        return null;
    }
}
