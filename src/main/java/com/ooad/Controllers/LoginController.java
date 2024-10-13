package com.ooad.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ooad.DatabaseManager;
import com.ooad.HelloApplication;

public class LoginController {
    
    private HelloApplication mainApp;

    public LoginController(HelloApplication mainApp) {
        this.mainApp = mainApp;
    }

    public String LoginButton(String username, String password) {
        if (authenticateUser(username, password)) {
            return "";
            // TODO: Switch to main view
        } 
        return "Invalid username or password";
        
    }

    public void RegisterButton() {
        mainApp.showRegisterPage();
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
            return false;
        }
    }
}
