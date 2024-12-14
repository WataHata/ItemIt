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

public class OfferForm extends Application {

    private TableView<Item> tableView;
    private ItemController itemController;
    private MainApplication mainApp;
    Label statusLabel;

    public OfferForm(MainApplication mainApp) {
        this.mainApp = mainApp;
        itemController = new ItemController();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) {
        tableView = new TableView<>();
        statusLabel = new Label("Status: Ready");

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
            private final Button approveButton = new Button("Approve");
            private final Button declineButton = new Button("Decline");
            {
                approveButton.setOnAction(_ -> {
                    Item item = getTableView().getItems().get(getIndex());
                    handleApproveAction(item);
                });

                declineButton.setOnAction(_ -> {
                    Item item = getTableView().getItems().get(getIndex());
                    handleDeclineAction(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Display Buttons
                    HBox buttons = new HBox(approveButton, declineButton);
                    buttons.setSpacing(10);
                    setGraphic(buttons);
                }
            }
        });

        tableView.getColumns().addAll(nameColumn, priceColumn, sizeColumn, categoryColumn, actionColumn);
        tableView.setItems(getItemList());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(tableView);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setTitle("Homepage");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<Item> getItemList() {
        String sellerId = mainApp.userSession.getUserId();
        List<Item> items = itemController.getItemsOnOffer(sellerId);
        return FXCollections.observableArrayList(items);
    }

    public static void main(String[] args) {
        launch(args);
    }


    private void handleApproveAction(Item item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Approve Item");
        alert.setHeaderText("Approve Item");
        alert.setContentText("Are you sure you want to approve this item?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            itemController.acceptOffer(item.getItemId(), statusLabel);
            tableView.setItems(getItemList());
        }
    }

    private void handleDeclineAction(Item item) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Decline Offer");
        dialog.setHeaderText("Please provide a reason for declining this offer");
        dialog.setContentText("Reason:");
    
        dialog.showAndWait().ifPresent(reason -> {
            if (!reason.trim().isEmpty()) {
                itemController.declineOffer(item.itemId, reason, statusLabel);
                tableView.setItems(getItemList());
            }
        });
    }

}