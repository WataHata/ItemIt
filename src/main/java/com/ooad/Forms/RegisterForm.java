package com.ooad.Forms;

import com.ooad.MainApplication;
import com.ooad.Controllers.UserController;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterForm extends Application {

    UserController userController;
    MainApplication mainApp;

    // Declare UI elements
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField phoneNumberField;
    private TextArea addressArea;
    private ToggleGroup roleGroup;
    public Text messageText = new Text();

    public RegisterForm(MainApplication mainApp) {
        this.mainApp = mainApp;
        userController = new UserController();
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a GridPane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25));

        // Title
        Text titleText = new Text("Registration");
        titleText.setFont(new Font("Tahoma", 20));
        GridPane.setConstraints(titleText, 0, 0, 2, 1, javafx.geometry.HPos.CENTER, null);
        gridPane.getChildren().add(titleText);

        // Username Label and TextField
        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 1);
        gridPane.getChildren().add(usernameLabel);
        
        usernameField = new TextField();
        GridPane.setConstraints(usernameField, 1, 1);
        gridPane.getChildren().add(usernameField);

        // Password Label and PasswordField
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 2);
        gridPane.getChildren().add(passwordLabel);
        
        passwordField = new PasswordField();
        GridPane.setConstraints(passwordField, 1, 2);
        gridPane.getChildren().add(passwordField);

        // Phone Number Label and TextField
        Label phoneLabel = new Label("Phone Number:");
        GridPane.setConstraints(phoneLabel, 0, 3);
        gridPane.getChildren().add(phoneLabel);
        
        phoneNumberField = new TextField();
        GridPane.setConstraints(phoneNumberField, 1, 3);
        gridPane.getChildren().add(phoneNumberField);

        // Address Label and TextArea
        Label addressLabel = new Label("Address:");
        GridPane.setConstraints(addressLabel, 0, 4);
        gridPane.getChildren().add(addressLabel);
        
        addressArea = new TextArea();
        GridPane.setConstraints(addressArea, 1, 4);
        gridPane.getChildren().add(addressArea);

        // Role Label and RadioButtons
        Label roleLabel = new Label("Role:");
        GridPane.setConstraints(roleLabel, 0, 5);
        gridPane.getChildren().add(roleLabel);
        
        VBox radioButtonContainer = new VBox(10);
        roleGroup = new ToggleGroup();
        
        RadioButton buyerButton = new RadioButton("Buyer");
        buyerButton.setToggleGroup(roleGroup);
        radioButtonContainer.getChildren().add(buyerButton);
        
        RadioButton userButton = new RadioButton("User");
        userButton.setToggleGroup(roleGroup);
        radioButtonContainer.getChildren().add(userButton);
        userButton.setSelected(true);
        
        GridPane.setConstraints(radioButtonContainer, 1, 5);
        gridPane.getChildren().add(radioButtonContainer);

        // Register Button
        Button registerButton = new Button("Register");
        registerButton.setOnAction(_ -> RegisterButton());
        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT);
        buttonContainer.getChildren().add(registerButton);
        GridPane.setConstraints(buttonContainer, 1, 6);
        gridPane.getChildren().add(buttonContainer);

        GridPane.setConstraints(messageText, 1, 7);
        gridPane.getChildren().add(messageText);

        Scene scene = new Scene(gridPane, 700, 300);
        primaryStage.setTitle("Registration Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void RegisterButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String phoneNumber = phoneNumberField.getText();
        String address = addressArea.getText();
        RadioButton selectedRadioButton = (RadioButton) roleGroup.getSelectedToggle();
        String role = selectedRadioButton.getText();

        userController.register(username, password, phoneNumber, address, role, messageText);

        mainApp.showLoginPage();
    }
}
