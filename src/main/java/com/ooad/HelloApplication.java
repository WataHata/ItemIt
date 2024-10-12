package com.ooad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class HelloApplication extends Application {
    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        System.out.println("Starting application");
        FXMLLoader registerLoader = new FXMLLoader(HelloApplication.class.getResource("/com/ooad/register-view.fxml"));
        System.out.println("Loaded register-view.fxml");

        URL fxmlUrl = HelloApplication.class.getResource("register-view.fxml");
        if (fxmlUrl == null) {
            System.err.println("Error: register-view.fxml not found");
            return;
        }

        Parent registerRoot = registerLoader.load();
        System.out.println("Loaded register root");

        Scene registerScene = new Scene(registerRoot, 1000, 500);
        System.out.println("Created register scene");
        
        stage.setScene(registerScene);
        stage.setTitle("Registration");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
