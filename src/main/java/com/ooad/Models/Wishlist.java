package com.ooad.Models;

public class Wishlist {
    private String wishlistId;
    private String userId;
    private String itemId;

    public Wishlist(String wishlistId, String userId, String itemId) {
        this.wishlistId = wishlistId;
        this.userId = userId;
        this.itemId = itemId;
    }

    public String getWishlistId() { return wishlistId; }
    public String getUserId() { return userId; }
    public String getItemId() { return itemId; }

    public void setWishlistId(String wishlistId) { this.wishlistId = wishlistId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    
}