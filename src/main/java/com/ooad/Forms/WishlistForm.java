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
import com.ooad.Controllers.WishlistController;
import com.ooad.Models.Item;

public class WishlistForm extends Application {

    private TableView<Item> tableView;
    private WishlistController wishlistController;
    private MainApplication mainApp;
    Label statusLabel;

    public WishlistForm(MainApplication mainApp) {
        this.mainApp = mainApp;
        wishlistController = new WishlistController();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) {
        statusLabel = new Label("Status: Ready"); // Status text
        // Create TableView and Columns
        tableView = new TableView<>();

        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("ItemPrice"));

        TableColumn<Item, String> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("ItemSize"));

         TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("ItemCategory"));

        // Column for Buttons
        TableColumn<Item, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(_ -> new TableCell<Item, Void>() {
            private final Button removeButton = new Button("Remove");
           

            {
                // Button Actions
                removeButton.setOnAction(_ -> {
                    Item item = getTableView().getItems().get(getIndex());
                    handleRemoveAction(item);
                });

            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(removeButton);
                    buttons.setSpacing(10);
                    setGraphic(buttons);
                }
            }
        });

        tableView.getColumns().addAll(nameColumn, priceColumn, sizeColumn,categoryColumn, actionColumn);
        tableView.setItems(getWishlistItemList());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(tableView);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setTitle("Wishlist");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<Item> getWishlistItemList() {
        String userId = mainApp.userSession.getUserId();
        List<Item> items = wishlistController.getItembyWishlist(userId);
        return FXCollections.observableArrayList(items);
    }

    private void handleRemoveAction(Item item) {
        String userId = mainApp.userSession.getUserId();
        wishlistController.removeItemFromWishlist(userId, item.getItemId(), statusLabel);
        tableView.setItems(getWishlistItemList());
        System.out.println("Removed from Wishlist: " + item.getItemName());
    }

    public static void main(String[] args) {
        launch(args);
    }
}