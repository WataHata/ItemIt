package com.ooad.Controllers;

import com.ooad.Models.Item;
import com.ooad.Models.ItemDAO;
import com.ooad.Models.UserDAO;

import javafx.scene.text.Text;

public class ItemController {
    private ItemDAO ItemModel;

    public ItemController() {
        ItemModel = new ItemDAO();
    }

    public boolean validateInputs(String name, String category, String size, String price, Text messageText) {

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
                else {
                    return true;
                }
            } catch (NumberFormatException e) {
                messageText.setText("Price must be a valid integer.\n");
            }
        }

        return false;
    }

    public boolean uploadItem(String name, String category, String size, String price, String sellerID, Text messageText)
    {
        if (validateInputs(name, category, size, price, messageText))
        {
            ItemModel.insertItem(name, size, price, category, sellerID);
            messageText.setText("Item uploaded successfully");
            return true;
        }
        return false;
    }
}
