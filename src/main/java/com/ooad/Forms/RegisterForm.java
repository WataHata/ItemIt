package com.ooad.Forms;

import com.ooad.Controllers.RegisterController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RegisterForm {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextArea addressArea;

    @FXML
    private ToggleGroup roleGroup;

    @FXML
    private Text messageText;

    @FXML
    private VBox radioButtonContainer;

    private RegisterController registerController;

    public RegisterForm() {
        // Empty constructor needed for FXML loader
    }

    @FXML
    public void initialize() {
        this.registerController = new RegisterController(usernameField, passwordField, phoneNumberField,
            addressArea, roleGroup, messageText, radioButtonContainer);
    }

    @FXML
    protected void handleRegisterButtonAction() {
        if (registerController != null) {
            registerController.handleRegisterButtonAction();
        } else {
            System.err.println("RegisterController is not initialized");
        }
    }
}
