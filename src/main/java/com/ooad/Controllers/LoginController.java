package com.ooad.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ooad.DatabaseManager;

public class LoginController {
    private TextField usernameField;
    private PasswordField passwordField;
    private Text messageText;

    @SuppressWarnings("exports")
    public LoginController(TextField usernameField, PasswordField passwordField, Text messageText) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.messageText = messageText;
    }

    @FXML
    public void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticateUser(username, password)) {
            messageText.setText("Login successful!");
            // TODO: Switch to main view
        } else {
            messageText.setText("Invalid username or password");
        }
    }

    private boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM User WHERE username = ? AND password = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // If there's a result, authentication is successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
            messageText.setText("Database error occurred");
            return false;
        }
    }
}

