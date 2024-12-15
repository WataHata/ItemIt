package com.ooad.Forms;

import com.ooad.MainApplication;
import com.ooad.Controllers.UserController;
import com.ooad.Models.User;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.Scene;

public class LoginForm extends Application {

    private UserController userController;
    private MainApplication mainApp;

    private TextField usernameField;
    private PasswordField passwordField;
    private Text messageText;

    public LoginForm(MainApplication mainApp) {
        this.mainApp = mainApp;
        userController = new UserController();
    }

    public void start(Stage primaryStage) {
        
        // Creating the grid layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // Title text
        Text welcomeText = new Text("Welcome to CaLouselF");
        welcomeText.setFont(Font.font("Tahoma", 20));
        GridPane.setColumnSpan(welcomeText, 2);
        GridPane.setHalignment(welcomeText, javafx.geometry.HPos.CENTER);
        gridPane.add(welcomeText, 0, 0);

        // Username label and text field
        Label usernameLabel = new Label("User Name:");
        gridPane.add(usernameLabel, 0, 1);
        usernameField = new TextField();
        gridPane.add(usernameField, 1, 1);
        
        // Password label and password field
        Label passwordLabel = new Label("Password:");
        gridPane.add(passwordLabel, 0, 2);
        passwordField = new PasswordField();
        gridPane.add(passwordField, 1, 2);
        
        // HBox for buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        Button signInButton = new Button("Sign In");
        Button registerButton = new Button("Register");
        
        // Add buttons to HBox
        buttonBox.getChildren().addAll(signInButton, registerButton);
        gridPane.add(buttonBox, 1, 4);
        signInButton.setOnAction(_ -> LoginButton());
        registerButton.setOnAction(_ -> RegisterButton());

        // Message text for feedback
        messageText = new Text();
        gridPane.add(messageText, 1, 6);

        // Set up the scene and stage
        Scene scene = new Scene(gridPane, 700, 300);
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void LoginButton(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = userController.login(username, password, messageText);
        if (user != null) {
            String role = user.getRole();
            mainApp.userSession.setRole(role);
            mainApp.userSession.setUsername(user.getUsername());
            mainApp.userSession.setUserId(user.getUserId());

            if (role.equals("admin")) mainApp.showApprovalPage();
            else if (role.equals("seller")) mainApp.showSellerHomepage();
            else if (role.equals("buyer")) mainApp.showHomePage();
            else mainApp.showLoginPage();
        }
    }

    private void RegisterButton(){
        mainApp.showRegisterPage();
    }
    
}


