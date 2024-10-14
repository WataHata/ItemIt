package com.ooad.Forms;

import com.ooad.MainApplication;
import com.ooad.Controllers.ItemController;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.Scene;

public class UploadForm extends Application {

    private ItemController itemController;
    private MainApplication mainApp;

    private TextField itemNameField;
    private TextField categoryField;
    private TextField sizeField;
    private TextField priceField;
    private Text messageText;

    public UploadForm(MainApplication mainApp) {
        this.mainApp = mainApp;
        itemController = new ItemController();
    }

    public void start(Stage primaryStage) {

        // Creating the grid layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // Title text
        Text titleText = new Text("Upload New Item");
        titleText.setFont(Font.font("Tahoma", 20));
        GridPane.setColumnSpan(titleText, 2);
        GridPane.setHalignment(titleText, javafx.geometry.HPos.CENTER);
        gridPane.add(titleText, 0, 0);

        // Item Name label and text field
        Label itemNameLabel = new Label("Item Name:");
        gridPane.add(itemNameLabel, 0, 1);
        itemNameField = new TextField();
        gridPane.add(itemNameField, 1, 1);

        // Category label and text field
        Label categoryLabel = new Label("Category:");
        gridPane.add(categoryLabel, 0, 2);
        categoryField = new TextField();
        gridPane.add(categoryField, 1, 2);

        // Size label and text field
        Label sizeLabel = new Label("Size:");
        gridPane.add(sizeLabel, 0, 3);
        sizeField = new TextField();
        gridPane.add(sizeField, 1, 3);

        // Price label and text field
        Label priceLabel = new Label("Price:");
        gridPane.add(priceLabel, 0, 4);
        priceField = new TextField();
        gridPane.add(priceField, 1, 4);

        // HBox for buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        Button uploadButton = new Button("Upload Item");

        // Add button to HBox
        buttonBox.getChildren().add(uploadButton);
        gridPane.add(buttonBox, 1, 5);

        // Message text for feedback
        messageText = new Text();
        gridPane.add(messageText, 1, 6);

        // Set up button action
        uploadButton.setOnAction(event -> uploadItem());

        // Set up the scene and stage
        Scene scene = new Scene(gridPane, 700, 400);
        primaryStage.setTitle("Upload Item Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void uploadItem() {
        String itemName = itemNameField.getText();
        String category = categoryField.getText();
        String size = sizeField.getText();
        String price = priceField.getText();
        String sellerId = mainApp.userSession.getUserId();
        if (itemController.uploadItem(itemName, category, size, price, sellerId, messageText)) {
            itemNameField.clear();
            categoryField.clear();
            sizeField.clear();
            priceField.clear();
        }
    }
}
