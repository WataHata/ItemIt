package com.ooad.Forms;

import com.ooad.Controllers.ItemController;
import com.ooad.Models.Item;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ApprovalForm extends Application {

    private ItemController itemController;
    private Text messageText;
    private Item selectedItem;

    private TextField itemNameField;
    private TextField categoryField;
    private TextField sizeField;
    private TextField priceField;
    private TextArea declineReasonField;
    private ListView<String> pendingItemsListView;

    // List of all pending items
    private ObservableList<String> pendingItemsList;

    public ApprovalForm() {
        itemController = new ItemController();
    }

    @Override
    public void start(Stage primaryStage) {
        // Creating the grid layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // Title text
        Text titleText = new Text("Item Review Approval");
        titleText.setFont(Font.font("Tahoma", 20));
        gridPane.add(titleText, 0, 0, 2, 1);

        // ListView for showing multiple pending items
        Label pendingItemsLabel = new Label("Pending Items:");
        gridPane.add(pendingItemsLabel, 0, 1);

        pendingItemsList = FXCollections.observableArrayList();
        pendingItemsListView = new ListView<>(pendingItemsList);
        pendingItemsListView.setPrefHeight(150);
        gridPane.add(pendingItemsListView, 1, 1);

        // Fields for item details (read-only)
        Label itemNameLabel = new Label("Item Name:");
        itemNameField = new TextField();
        itemNameField.setEditable(false);

        Label categoryLabel = new Label("Category:");
        categoryField = new TextField();
        categoryField.setEditable(false);

        Label sizeLabel = new Label("Size:");
        sizeField = new TextField();
        sizeField.setEditable(false);

        Label priceLabel = new Label("Price:");
        priceField = new TextField();
        priceField.setEditable(false);

        // Adding fields to the grid
        gridPane.add(itemNameLabel, 0, 2);
        gridPane.add(itemNameField, 1, 2);
        gridPane.add(categoryLabel, 0, 3);
        gridPane.add(categoryField, 1, 3);
        gridPane.add(sizeLabel, 0, 4);
        gridPane.add(sizeField, 1, 4);
        gridPane.add(priceLabel, 0, 5);
        gridPane.add(priceField, 1, 5);

        // Decline reason field (for decline action)
        Label reasonLabel = new Label("Reason for Decline:");
        declineReasonField = new TextArea();
        declineReasonField.setPromptText("Enter reason here...");
        declineReasonField.setWrapText(true);
        gridPane.add(reasonLabel, 0, 6);
        gridPane.add(declineReasonField, 1, 6);

        // HBox for approve and decline buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        Button approveButton = new Button("Approve");
        Button declineButton = new Button("Decline");

        // Adding buttons to HBox
        buttonBox.getChildren().addAll(approveButton, declineButton);
        gridPane.add(buttonBox, 1, 7);

        // Message text for feedback
        messageText = new Text();
        gridPane.add(messageText, 1, 8);

        // Set up button actions
        approveButton.setOnAction(event -> approveItem());
        declineButton.setOnAction(event -> declineItem());

        // ListView selection handler
        // pendingItemsListView.setOnMouseClicked((MouseEvent event) -> loadSelectedItemDetails());

        // Load all pending items into ListView
        loadPendingItems();

        // Set up the scene and stage
        Scene scene = new Scene(gridPane, 700, 500);
        primaryStage.setTitle("Item Approval Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to load all pending items into ListView
    private void loadPendingItems() {
        List<Item> pendingItems = itemController.getPendingItems();
        if (!pendingItems.isEmpty()) {
            pendingItemsList.clear();
            for (Item item : pendingItems) {
                pendingItemsList.add(item.getItemName());
            }
            messageText.setText("");
        } else {
            messageText.setText("No items waiting for approval.");
        }
    }

    // Method to load selected item's details into fields
    // private void loadSelectedItemDetails() {
    //     String selectedItemName = pendingItemsListView.getSelectionModel().getSelectedItem();
    //     if (selectedItemName != null) {
    //         selectedItem = itemController.getItemByName(selectedItemName);
    //         if (selectedItem != null) {
    //             itemNameField.setText(selectedItem.getItemName());
    //             categoryField.setText(selectedItem.getItemCategory());
    //             sizeField.setText(selectedItem.getItemSize());
    //             priceField.setText(selectedItem.getItemPrice());
    //         }
    //     }
    // }

    private void approveItem() {

    }

    private void declineItem() {

    }
   

    // Clear form fields after action
    private void clearForm() {
        itemNameField.clear();
        categoryField.clear();
        sizeField.clear();
        priceField.clear();
        declineReasonField.clear();
        selectedItem = null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
