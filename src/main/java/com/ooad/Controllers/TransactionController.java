package com.ooad.Controllers;

import java.util.List;
import java.util.ArrayList;

import com.ooad.Models.Item;
import com.ooad.Models.ItemDAO;
import com.ooad.Models.Transaction;
import com.ooad.Models.TransactionDAO;
import com.ooad.Models.WishlistDAO;
import com.ooad.Models.Wishlist;
import javafx.scene.control.*;
import javafx.scene.text.Text;
@SuppressWarnings("exports")

public class TransactionController {
    private TransactionDAO transactionModel;

    public TransactionController() {
        transactionModel = new TransactionDAO();
    }

    public boolean createTransaction(String userId, String itemId, Text messageText) {
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
