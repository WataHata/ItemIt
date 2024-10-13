package com.ooad.Forms;

import java.util.List;

import com.ooad.Controllers.ApprovalController;
import com.ooad.Models.Item;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.scene.layout.HBox;

public class ApprovalForm {
    @FXML
    private TableView<Item> pendingItemsTable;

    @FXML
    private TableColumn<Item, String> itemIdColumn;

    @FXML
    private TableColumn<Item, String> itemNameColumn;

    @FXML
    private TableColumn<Item, String> itemStatusColumn;

    @FXML
    private TableColumn<Item, Void> actionColumn;

    @FXML
    private VBox root;

    private ApprovalController approvalController;

    public ApprovalForm() {
        // Empty constructor needed for FXML loader
    }

    @FXML
    public void initialize() {
        approvalController = new ApprovalController();
        setupTable();
        loadPendingItems();
    }

    private void setupTable() {
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemStatusColumn.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));

        actionColumn.setCellFactory(col -> new TableCell<Item, Void>() {
            private final Button approveButton = new Button("Approve");
            private final Button rejectButton = new Button("Reject");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null) {
                    setGraphic(null);
                } else {
                    Item currentItem = getTableRow().getItem();
                    approveButton.setOnAction(e -> approveItem(currentItem));
                    rejectButton.setOnAction(e -> rejectItem(currentItem));
                    HBox buttons = new HBox(approveButton, rejectButton);
                    setGraphic(buttons);
                }
            }
        });
    }

    private void loadPendingItems() {
        List<Item> items = approvalController.getPendingItems();
        pendingItemsTable.setItems(FXCollections.observableArrayList(items));
    }

    private void approveItem(Item item) {
        // Logic to approve the item
        System.out.println("Approved: " + item.getItemId());
        // You can add your approval logic here (e.g., update the database)
    }

    private void rejectItem(Item item) {
        // Logic to reject the item
        System.out.println("Rejected: " + item.getItemId());
        // You can add your rejection logic here (e.g., update the database)
    }
}
