package com.ooad.Forms;

import com.ooad.DatabaseManager;
import com.ooad.Controllers.UploadController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.sql.Connection;

public class UploadForm {
    @FXML
    private TextField nameField;

    @FXML
    private TextField sizeField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField priceField;

    @FXML
    private Text errorMessage;

    private UploadController uploadController;

    public UploadForm() {
        // Empty constructor needed for FXML loader
    }

    @FXML
    public void initialize() {
        uploadController = new UploadController();
    }

    @FXML
    protected void handleUploadButtonAction() {
        String name = nameField.getText();
        String size = sizeField.getText();
        String category = categoryField.getText();
        String price = priceField.getText();

        // Validate inputs
        String validationErrors = uploadController.validateInputs(name, category, size, price);
        if (!validationErrors.isEmpty()) {
            errorMessage.setText(validationErrors);
            return;
        }

        // Convert price to BigDecimal
        BigDecimal priceValue = new BigDecimal(price);

        // Upload the item
        try (Connection connection = DatabaseManager.getConnection()) {
            boolean success = uploadController.createItem(connection, name, size, priceValue, category);
            if (success) {
                errorMessage.setText("Item uploaded successfully!");
                clearFields();
            } else {
                errorMessage.setText("Failed to upload item.");
            }
        } catch (Exception e) {
            errorMessage.setText("Database error: " + e.getMessage());
        }
    }

    private void clearFields() {
        nameField.clear();
        sizeField.clear();
        categoryField.clear();
        priceField.clear();
        errorMessage.setText("");
    }
}