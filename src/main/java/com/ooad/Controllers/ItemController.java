package com.ooad.Controllers;

import java.util.List;

import com.ooad.Models.Item;
import com.ooad.Models.ItemDAO;
import com.ooad.Models.WishlistDAO;

import javafx.scene.control.*;
import javafx.scene.text.Text;

@SuppressWarnings("exports")

public class ItemController {
    private ItemDAO ItemModel;
    private WishlistDAO WishlistModel;
    private TransactionController transactionController;

    public ItemController() {
        ItemModel = new ItemDAO();
        transactionController = new TransactionController();
    }

    public boolean validateInputs(String name, String category, String size, String price, Label messageText) {
        messageText.setText("OK");
        System.out.println("Name: " + name + " (length: " + (name != null ? name.length() : "null") + ")");
        System.out.println("Category: " + category + " (length: " + (category != null ? category.length() : "null") + ")");
        System.out.println("Size: " + size + " (length: " + (size != null ? size.length() : "null") + ")");
        System.out.println("Price: " + price + " (length: " + (price != null ? price.length() : "null") + ")");

        // Validate name
        if (name == null || name.trim().isEmpty()) {
            messageText.setText("Name cannot be empty.\n");
        } else if (name.length() < 3) {
            messageText.setText("Name must be at least 3 characters long.\n");
        }

        // Validate category
        if (category == null || category.trim().isEmpty()) {
            messageText.setText("Category cannot be empty.\n");
        } else if (category.length() != 3) {
            messageText.setText("Category must be exactly 3 characters long.\n");
        }

        // Validate size
        if (size == null || size.trim().isEmpty()) {
            messageText.setText("Size cannot be empty.\n");
        }

        // Validate price
        if (price == null || price.trim().isEmpty()) {
            messageText.setText("Price cannot be empty.\n");
        } else {
            try {
                int priceValue = Integer.parseInt(price);
                if (priceValue <= 0) {
                    messageText.setText("Price must be a positive integer.\n");
                }
            } catch (NumberFormatException e) {
                messageText.setText("Price must be a valid integer.\n");
            }
        }
        return messageText.getText().equals("OK");
    }

    public boolean uploadItem(String name, String category, String size, String price, String sellerID, Label messageText)
    {
        if (validateInputs(name, category, size, price, messageText))
        {
            if ( ItemModel.insertItem(name, size, price, category, sellerID))
            {
                messageText.setText("Item uploaded successfully");
                return true;
            }
            else
            {
                messageText.setText("Something went wrong");
            }
        }
        return false;
    }

    public boolean updateItem(String itemId, String name, String category, String size, String price, Label messageText) {
        if (validateInputs(name, category, size, price, messageText)) {
            if (ItemModel.updateItem(itemId, name, size, price, category)) {
                messageText.setText("Item updated successfully");
                return true;
            } else {
                messageText.setText("Failed to update item");
                return false;
            }
        }
        return false;
    }

    public void approveItem(String itemId, Label messageText) {
        if (ItemModel.approveItem(itemId)) {
            messageText.setText("Item approved successfully!");
        } else {
            messageText.setText("Failed to approve item.");
        }
    }

    public void declineItem(String itemId, String reason, Label messageText) {
        if (reason.isEmpty()) {
            messageText.setText("Reason for decline cannot be empty.");
            return;
        }

        if (ItemModel.declineItem(itemId, reason)) {
            messageText.setText("Item declined successfully!");
        } else {
            messageText.setText("Failed to decline item.");
        }
    }

    public void makeOffer(String userId, String itemId, String offeredPrice, Label messageText) {
        if (ItemModel.makeOffer(userId, itemId, offeredPrice)) {
            messageText.setText("Item successfully offered");
        } else {
            messageText.setText("Something went wrong vinny!");
        }
    }

    public void acceptOffer(String itemId, Label messageText) {
        Item item = ItemModel.getItemById(itemId);
        String userId = item.getItemWishlist();
        if (ItemModel.removeItemOfferStatus(itemId) && transactionController.createTransaction(userId, itemId, messageText)) {
            messageText.setText("offer accepted successfully!");
        } else {
            messageText.setText("Failed to accept offer.");
        }
    }

    public void declineOffer(String itemId, String reason, Label messageText) {
        if (ItemModel.removeItemOfferStatus(itemId)) {
            messageText.setText("Offer declined successfully!");
        } else {
            messageText.setText("Failed to decline offer.");
        }
    }

    public List<Item> getPendingItems() {
        return ItemModel.getItemsWithStatus("pending");
    }

    public List<Item> getApprovedItems() {
        return ItemModel.getItemsWithStatus("approved");
    }

    public List<Item> getItemsOnOffer(String userId) {
        return ItemModel.getItemsWithOffersBySellerId(userId);
    }

    public List<Item> getApprovedBySellerIdItems(String userId) {
        return ItemModel.getApprovedItemsBySeller(userId);
    }

    public boolean deleteItem(String itemId) {
        WishlistModel.removeAllWishlistsByItemId(itemId);
        return ItemModel.deleteItemById(itemId); 
    }
    
}
