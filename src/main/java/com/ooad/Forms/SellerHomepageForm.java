package com.ooad.Forms;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;

import java.util.List;
import java.util.Optional;

import com.ooad.MainApplication;
import com.ooad.Controllers.ItemController;
import com.ooad.Models.Item;

public class SellerHomepageForm extends Application {

    private TableView<Item> tableView;
    private ItemController itemController;
    private MainApplication mainApp;
    Label statusLabel;

    public SellerHomepageForm(MainApplication mainApp) {
        this.mainApp = mainApp;
        itemController = new ItemController();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) {
        // Create TableView and Columns
        tableView = new TableView<>();

        statusLabel = new Label("Status: Ready"); // Status text

        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("ItemPrice"));

        TableColumn<Item, String> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("ItemSize"));

        TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("ItemCategory"));

        // Column for Edit and Delete Buttons
        TableColumn<Item, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(param -> new TableCell<Item, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                // Button Actions
                editButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    handleEditAction(item);
                });

                deleteButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    handleDeleteAction(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Display Buttons
                    HBox buttons = new HBox(editButton, deleteButton);
                    buttons.setSpacing(10);
                    setGraphic(buttons);
                }
            }
        });

        // Add Columns to TableView
        tableView.getColumns().addAll(nameColumn, priceColumn, sizeColumn, categoryColumn, actionColumn);
        
        // Set Items in TableView
        tableView.setItems(getItemList());

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(tableView);

        // Scene and Stage
        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setTitle("Seller Homepage");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<Item> getItemList() {
        List<Item> items = itemController.getApprovedItems();
        return FXCollections.observableArrayList(items);
    }

    private void handleEditAction(Item item) {
        // Implement the logic to edit the item
        System.out.println("Editing item: " + item.getItemName());
        // You can open a new dialog or form to edit the item details
    }

    private void handleDeleteAction(Item item) {
        // Create a confirmation dialog for deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete the item: " + item.getItemName() + "?");
        alert.setContentText("Click Yes to confirm, or No to cancel.");

        // Show the dialog and wait for the user's response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked Yes, proceed with deletion
            // Call the delete method from ItemController
            // Assuming you have a method in ItemController to delete an item
            itemController.deleteItem(item.getItemId()); // Implement this method in ItemController
            System.out.println("Deleted item: " + item.getItemName());
            // Refresh the item list after deletion
            tableView.setItems(getItemList());
        } else {
            // User clicked No, do nothing
            System.out.println("Deletion cancelled.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}