package com.ooad.Controllers;

import com.ooad.DatabaseManager;
import com.ooad.Models.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ooad.Controllers.ApprovalController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
@SuppressWarnings({"exports", "unused"})

public class ApprovalController {
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

    private ObservableList<Item> pendingItems;
    
    public void initialize() {
        // Initialize columns
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemStatusColumn.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));

        // Set up action column with buttons
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

        loadPendingItems();
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

    private void loadPendingItems() {
        ApprovalController approvalController = new ApprovalController();
        List<Item> items = approvalController.getPendingItems();
        pendingItems = FXCollections.observableArrayList(items);
        pendingItemsTable.setItems(pendingItems);
    }
    
    public List<Item> getPendingItems() {
        List<Item> pendingItems = new ArrayList<>();
        String query = "SELECT * FROM Item WHERE item_status = 'Pending'";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Item item = new Item(
                    rs.getString("item_id"),
                    rs.getString("item_name"),
                    rs.getString("item_size"),
                    rs.getBigDecimal("item_price"),
                    rs.getString("item_category"),
                    rs.getString("item_status"),
                    rs.getBoolean("item_wishlist"),
                    rs.getBoolean("item_offer_status")
                );
                pendingItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., logging, throwing a custom exception)
        }
        
        return pendingItems;
    }


}
