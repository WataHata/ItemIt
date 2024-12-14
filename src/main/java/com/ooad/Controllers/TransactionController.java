package com.ooad.Controllers;

import java.util.List;
import com.ooad.Models.Transaction;
import com.ooad.Models.TransactionDAO;
import javafx.scene.control.*;

@SuppressWarnings("exports")

public class TransactionController {
    private TransactionDAO transactionModel;

    public TransactionController() {
        transactionModel = new TransactionDAO();
    }

    public boolean createTransaction(String userId, String itemId, Label messageText) {
        TransactionDAO transactionDAO = new TransactionDAO();
        boolean success = transactionDAO.insertTransaction(itemId, userId);  
        if (success) {
            messageText.setText("Transaction created successfully."); 
            return true;
        } else {
            messageText.setText("Failed to create transaction."); 
            return false;
        }
    }

    public List<Transaction> getTransactionsByUserId(String userId) {
        return transactionModel.getTransactionsByUserId(userId);
    }
}
