package com.ooad;

import javafx.application.Application;
import javafx.stage.Stage;
import com.ooad.Forms.LoginForm;
import com.ooad.Forms.RegisterForm;

public class MainApplication extends Application {

    private Stage primaryStage;


    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) {
        this.primaryStage = primaryStage;  
        showLoginPage();  
    }

    public void showLoginPage() {
        LoginForm loginForm = new LoginForm(this);
        loginForm.start(primaryStage);
    }

    public void showRegisterPage() {
        RegisterForm registerForm = new RegisterForm(this);
        registerForm.start(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }
}
