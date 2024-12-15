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
import com.ooad.Controllers.TransactionController;
import com.ooad.Controllers.WishlistController;
import com.ooad.Models.Item;

public class HomepageForm extends Application {

    private TableView<Item> tableView;
    private ItemController itemController;
    private TransactionController transactionController;
    private WishlistController wishlistController;
    private MainApplication mainApp;
    Label statusLabel;
    String userId;

    public HomepageForm(MainApplication mainApp) {
        this.mainApp = mainApp;
        itemController = new ItemController();
        transactionController = new TransactionController();
        wishlistController = new WishlistController();
        userId =  mainApp.userSession.getUserId();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) {
        tableView = new TableView<>();
        statusLabel = new Label("Status: Ready"); 

        Button wishlistButton = new Button("Wishlist");
        Button transactionHistoryButton = new Button("Transaction History");
        
        // Add button actions
        wishlistButton.setOnAction(_ -> openWishlistForm());
        transactionHistoryButton.setOnAction(_ -> openTransactionHistoryForm());

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
            private final Button offerButton = new Button("Offer");
            private final Button buyButton = new Button("Buy");
            private final Button wishlistButton = new Button("Wishlist");
            {
                offerButton.setOnAction(_ -> {
                    Item item = getTableView().getItems().get(getIndex());
                    handleOfferAction(item);
                });

                buyButton.setOnAction(_ -> {
                    Item item = getTableView().getItems().get(getIndex());
                    handleBuyAction(item);
                });

                wishlistButton.setOnAction(_ -> {
                    Item item = getTableView().getItems().get(getIndex());
                    handleWishlistAction(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(offerButton, buyButton, wishlistButton);
                    buttons.setSpacing(10);
                    setGraphic(buttons);
                }
            }
        });
        tableView.getColumns().addAll(nameColumn, priceColumn, sizeColumn, categoryColumn, actionColumn);
        tableView.setItems(getItemList());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(wishlistButton, transactionHistoryButton, statusLabel, tableView);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setTitle("Homepage");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<Item> getItemList() {
        List<Item> items = itemController.getApprovedItems();
        return FXCollections.observableArrayList(items);
    }

    private void handleOfferAction(Item item) {
        Dialog<Double> dialog = new Dialog<>();
        dialog.setTitle("Make an Offer");
        dialog.setHeaderText("Enter your offer price for " + item.getItemName());
        
        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
    
        TextField priceInput = new TextField();
        priceInput.setPromptText("Enter price");
    
        dialog.getDialogPane().setContent(new VBox(10, new Label("Price:"), priceInput));
    
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                try {
                    return Double.parseDouble(priceInput.getText());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });
    
        dialog.showAndWait().ifPresent(offeredPrice -> {
            if (offeredPrice != null) {
                itemController.makeOffer(userId, item.getItemId(), offeredPrice.toString(), statusLabel);
                statusLabel.setText("Offer made for: " + item.getItemName());
            }
        });
    }

    private void handleBuyAction(Item item) {
        transactionController.createTransaction(userId, item.getItemId(), statusLabel);
        System.out.println("Item bought: " + item.getItemName());
    }

    private void handleWishlistAction(Item item) {
        wishlistController.insertWishlist(userId, item.getItemId(), statusLabel);
        System.out.println("Added to wishlist: " + item.getItemName());
    }

    private void openWishlistForm() {
        mainApp.showWishlistPage();
    }

    private void openTransactionHistoryForm() {
        mainApp.showTransactionPage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
