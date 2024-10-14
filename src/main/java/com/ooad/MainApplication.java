package com.ooad;

import javafx.application.Application;
import javafx.stage.Stage;

import com.ooad.Controllers.UploadController;
import com.ooad.Forms.LoginForm;
import com.ooad.Forms.RegisterForm;
import com.ooad.Forms.UploadItemForm;

public class MainApplication extends Application {

    private Stage primaryStage;


    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) {
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
        UploadItemForm uploadForm = new UploadItemForm(this);
        uploadForm.start(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }
}
