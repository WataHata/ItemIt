package com.ooad.Controllers;

import java.util.List;
import java.util.ArrayList;

import com.ooad.Models.Item;
import com.ooad.Models.ItemDAO;
import com.ooad.Models.WishlistDAO;
import com.ooad.Models.Wishlist;
import javafx.scene.control.*;
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
            ItemDAO itemDAO = new ItemDAO();
            Item item = itemDAO.getItemById(wishlist.getItemId());
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }

    public void insertWishlist(String userId, String itemID, Label label) {
        if (WishlistModel.addItemToWishlist(String.valueOf(userId), String.valueOf(itemID))) {
            label.setText("Item added to wishlist successfully.");
        } else {
            label.setText("Item already exists in the wishlist or failed to add.");
        }
    }

    public void removeItemFromWishlist(String userId, String itemId, Label label) {
        if (WishlistModel.removeItemFromWishlist(String.valueOf(userId), String.valueOf(itemId))) {
            label.setText("Item removed from wishlist successfully.");
        } else {
            label.setText("Item not found in the wishlist or failed to remove.");
        }
    }
}
