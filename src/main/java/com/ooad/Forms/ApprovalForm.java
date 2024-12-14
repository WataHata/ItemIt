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

public class ApprovalForm extends Application {

    private TableView<Item> tableView;
    private ItemController itemController;
    private MainApplication mainApp;
    Label statusLabel;

    public ApprovalForm(MainApplication mainApp) {
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
        List<Item> items = itemController.getPendingItems();
        return FXCollections.observableArrayList(items);
    }

    public static void main(String[] args) {
        launch(args);
    }


private void handleApproveAction(Item item) {
    itemController.approveItem(item.getItemId(), statusLabel);
    tableView.setItems(getItemList());
}

private void handleDeclineAction(Item item) {
    
    Dialog<String> dialog = new Dialog<>();
    dialog.setTitle("Decline Item");
    dialog.setHeaderText("Please provide a reason for declining this item");

    ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

    TextField reasonField = new TextField();
    reasonField.setPromptText("Enter reason for declining");

    VBox content = new VBox(10);
    content.getChildren().addAll(new Label("Reason:"), reasonField);
    dialog.getDialogPane().setContent(content);

    javafx.scene.Node confirmButton = dialog.getDialogPane().lookupButton(confirmButtonType);
    confirmButton.setDisable(true);

    reasonField.textProperty().addListener((_, _, newValue) -> {
        confirmButton.setDisable(newValue.trim().isEmpty());
    });

    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == confirmButtonType) {
            return reasonField.getText();
        }
        return null;
    });

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(reason -> {
        itemController.declineItem(item.itemId, reason, statusLabel);
        tableView.setItems(getItemList());
    });

}

}