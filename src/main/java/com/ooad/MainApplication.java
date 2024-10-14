package com.ooad;

import javafx.application.Application;
import javafx.stage.Stage;

import com.ooad.Forms.LoginForm;
import com.ooad.Forms.RegisterForm;
import com.ooad.Forms.UploadForm;

public class MainApplication extends Application {

    private Stage primaryStage;
    public UserSession userSession;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;  
        showUploadPage();  
    }

    public void showLoginPage() {
        LoginForm loginForm = new LoginForm(this);
        loginForm.start(primaryStage);
    }

    public void showRegisterPage() {
        RegisterForm registerForm = new RegisterForm(this);
        registerForm.start(primaryStage);
    }

    public void showUploadPage() {
        UploadForm uploadForm = new UploadForm(this);
        uploadForm.start(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }
}
