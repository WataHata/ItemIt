package com.ooad.Forms;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;

import java.util.List;

import com.ooad.MainApplication;
import com.ooad.Models.Item;
import com.ooad.Models.Transaction;
import com.ooad.Controllers.TransactionController;
import com.ooad.Models.ItemDAO;

public class TransactionHistory extends Application {

    private TableView<TransactionItemView> tableView;
    private TransactionController transactionController;
    private MainApplication mainApp;
    Label statusLabel;

    public class TransactionItemView {
        private String transactionId;
        private String itemName;
        private String itemPrice;
        private String itemSize;
        private String itemCategory;

        public TransactionItemView(String transactionId, @SuppressWarnings("exports") Item item) {
            this.transactionId = transactionId;
            this.itemName = item.getItemName();
            this.itemPrice = item.getItemPrice();
            this.itemSize = item.getItemSize();
            this.itemCategory = item.getItemCategory();
        }

        public String getTransactionId() { return transactionId; }
        public String getItemName() { return itemName; }
        public String getItemPrice() { return itemPrice; }
        public String getItemSize() { return itemSize; }
        public String getItemCategory() { return itemCategory; }
    }

    public TransactionHistory(MainApplication mainApp) {
        this.mainApp = mainApp;
        transactionController = new TransactionController();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) {
        tableView = new TableView<>();
        statusLabel = new Label("Status: Ready");

        TableColumn<TransactionItemView, String> transactionIdColumn = new TableColumn<>("Transaction ID");
        transactionIdColumn.setCellValueFactory( new PropertyValueFactory<>("transactionId"));
        TableColumn<TransactionItemView, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        TableColumn<TransactionItemView, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory( new PropertyValueFactory<>("itemPrice"));
        TableColumn<TransactionItemView, String> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        TableColumn<TransactionItemView, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        tableView.getColumns().addAll(transactionIdColumn, nameColumn, priceColumn, sizeColumn, categoryColumn);
        tableView.setItems(getTransactionItemList());
        
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(tableView);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setTitle("Transaction History");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<TransactionItemView> getTransactionItemList() {
        ObservableList<TransactionItemView> transactionItemViews = FXCollections.observableArrayList();
        
        String userId = mainApp.userSession.getUserId();      
        List<Transaction> transactions = transactionController.getTransactionsByUserId(userId);  
        ItemDAO itemDAO = new ItemDAO();
        
        for (Transaction transaction : transactions) {
            Item item = itemDAO.getItemById(transaction.getItem_id());
            if (item != null) {
                transactionItemViews.add( new TransactionItemView(transaction.getTransaction_id(), item));
            }
        }
        
        return transactionItemViews;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
