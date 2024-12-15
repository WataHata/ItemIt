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
        tableView = new TableView<>();
        statusLabel = new Label("Status: Ready"); 

        Button offerButton = new Button("Offers");
        Button uploadButton = new Button("Upload Item");
        
        // Add button actions
        offerButton.setOnAction(_ -> openOfferForm());
        uploadButton.setOnAction(_ -> openUploadForm());

        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("ItemPrice"));
        TableColumn<Item, String> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("ItemSize"));
        TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("ItemCategory"));

        TableColumn<Item, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(_ -> new TableCell<Item, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            {
                editButton.setOnAction(_ -> {
                    Item item = getTableView().getItems().get(getIndex());
                    handleEditAction(item);
                });

                deleteButton.setOnAction(_ -> {
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
                    HBox buttons = new HBox(editButton, deleteButton);
                    buttons.setSpacing(10);
                    setGraphic(buttons);
                }
            }
        });

        tableView.getColumns().addAll(nameColumn, priceColumn, sizeColumn, categoryColumn, actionColumn);
        tableView.setItems(getItemList());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(tableView, statusLabel, offerButton, uploadButton);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setTitle("Seller Homepage");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<Item> getItemList() {
        List<Item> items = itemController.getApprovedBySellerIdItems(mainApp.userSession.getUserId());
        return FXCollections.observableArrayList(items);
    }

    private boolean handleEditAction(Item item) {
        Dialog<Item> dialog = new Dialog<>();
        dialog.setTitle("Edit Item");
        dialog.setHeaderText("Edit item details");
    
        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);
    
        TextField nameField = new TextField(item.getItemName());
        TextField priceField = new TextField(item.getItemPrice());
        TextField sizeField = new TextField(item.getItemSize());
        TextField categoryField = new TextField(item.getItemCategory());
    
        VBox content = new VBox(10,
            new Label("Name:"), nameField,
            new Label("Price:"), priceField,
            new Label("Size:"), sizeField,
            new Label("Category:"), categoryField
        );
    
        dialog.getDialogPane().setContent(content);
    
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButton) {
                itemController.updateItem(
                    item.getItemId(),
                    nameField.getText(),
                    sizeField.getText(),
                    priceField.getText(),
                    categoryField.getText(),
                    statusLabel);
                return null;
            }
            return null;
        });
    
        dialog.showAndWait();
        tableView.setItems(getItemList()); 

        return true;
    }

    private void handleDeleteAction(Item item) {
        itemController.deleteItem(item.getItemId());
    }

    private void openOfferForm() {
        mainApp.showOfferPage();
    }

    private void openUploadForm() {
        mainApp.showUploadPage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}