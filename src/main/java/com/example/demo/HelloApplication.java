package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader registerLoader = new FXMLLoader(HelloApplication.class.getResource("register-view.fxml"));
        Parent registerRoot = registerLoader.load();
        Scene registerScene = new Scene(registerRoot, 400, 500);
        stage.setScene(registerScene);
        stage.setTitle("Registration");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
