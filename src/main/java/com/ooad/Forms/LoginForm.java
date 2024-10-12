package com.ooad.Forms;

import com.ooad.Controllers.LoginController;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginForm {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text messageText;

    private LoginController loginController;

    public LoginForm() {
        // Empty constructor needed for FXML loader
    }

    @FXML
    public void initialize() {
        this.loginController = new LoginController(usernameField, passwordField, messageText);
    }

    @FXML
    protected void handleLoginButtonAction() {
        loginController.handleLoginButtonAction();
    }
}
