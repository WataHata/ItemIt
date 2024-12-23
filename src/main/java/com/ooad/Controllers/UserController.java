package com.ooad.Controllers;

import javafx.scene.text.Text;
import java.util.regex.Pattern;
import com.ooad.Models.UserDAO;
import com.ooad.Models.User;

public class UserController {
    private UserDAO UserModel;

    public UserController() {
        UserModel = new UserDAO();
    }

    public boolean register(String username, String password, String phoneNumber, String address, String role, Text messageText) {
        if (validateInputs(username, password, phoneNumber, address, messageText)) {
            if (UserModel.isUsernameUnique(username)) {
                if (UserModel.insertUser(username, password, phoneNumber, address, role)) {
                    messageText.setText("Registration successful!");
                    return true;
                } else {
                    messageText.setText("Registration failed. Please try again.");
                }
            } else {
                messageText.setText("Username already exists. Please choose a different username.");
            }
        }  
        return false;
    }

    @SuppressWarnings("exports")
    public User login(String username, String password, Text messageText) {
        User user = UserModel.authenticateUser(username, password);
        if (user != null) {
            messageText.setText("Username or password is wrong");
        }
        return user;
    }

    private boolean validateInputs(String username, String password, String phoneNumber, String address, Text messageText) {
        if (username.isEmpty() || username.length() < 3) {
            messageText.setText("Username must be at least 3 characters long.");
            return false;
        }

        if (password.isEmpty() || password.length() < 8 || !Pattern.compile("[!@#$%^&*]").matcher(password).find()) {
            messageText.setText("Password must be at least 8 characters long and include a special character (!@#$%^&*).");
            return false;
        }

        if (!phoneNumber.matches("\\+62\\d{10}")) {
            messageText.setText("Phone number must start with +62 and be followed by 10 digits.");
            return false;
        }

        if (address.isEmpty()) {
            messageText.setText("Address cannot be empty.");
            return false;
        }

        return true;
    }
}
