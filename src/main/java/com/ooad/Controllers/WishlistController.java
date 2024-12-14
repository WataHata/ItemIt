package com.ooad.Controllers;

import java.util.List;
import java.util.ArrayList;

import com.ooad.Models.Item;
import com.ooad.Models.ItemDAO;
import com.ooad.Models.WishlistDAO;
import com.ooad.Models.Wishlist;
import javafx.scene.control.*;
import javafx.scene.text.Text;
@SuppressWarnings("exports")

public class WishlistController {
    private WishlistDAO WishlistModel;

    public WishlistController() {
        WishlistModel = new WishlistDAO();
    }

    public List<Item> getItembyWishlist(String userId) {
        List<Item> items = new ArrayList<>();
        List<Wishlist> wishlists = WishlistModel.getWishlistsByUserId(String.valueOf(userId));

        for (Wishlist wishlist : wishlists) {
            String itemId = wishlist.getItemId();
            // Assuming you have an ItemDAO to fetch items by itemId
            ItemDAO itemDAO = new ItemDAO();
            Item item = itemDAO.getItemById(itemId); // You need to implement this method in ItemDAO
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }

    public void insertWishlist(String userId, String itemID, Label label) {
        WishlistDAO wishlistDAO = new WishlistDAO();
        boolean success = wishlistDAO.addItemToWishlist(String.valueOf(userId), String.valueOf(itemID));
        if (success) {
            label.setText("Item added to wishlist successfully.");
        } else {
            label.setText("Item already exists in the wishlist or failed to add.");
        }
    }

    public void removeItemFromWishlist(String userId, String itemId, Label label) {
        WishlistDAO wishlistDAO = new WishlistDAO();
        boolean success = wishlistDAO.removeItemFromWishlist(String.valueOf(userId), String.valueOf(itemId));
        if (success) {
            label.setText("Item removed from wishlist successfully.");
        } else {
            label.setText("Item not found in the wishlist or failed to remove.");
        }
    }

    


}
