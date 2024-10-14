package com.ooad.Models;

public class Item {
    private String itemId;
    private String itemName;
    private String itemSize;
    private String itemPrice;
    private String itemCategory;
    private String itemStatus;
    private String itemWishlist;
    private String itemOfferStatus;
    private String sellerId;
    private String reason;

    public Item(String itemId, String itemName, String itemSize, String itemPrice, 
                String itemCategory, String itemStatus, String itemWishlist, String itemOfferStatus, String sellerId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemSize = itemSize;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemStatus = itemStatus;
        this.itemWishlist = itemWishlist;
        this.itemOfferStatus = itemOfferStatus;
        this.sellerId = sellerId;
        this.reason = ""; 
    }

    // Getters
    public String getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public String getItemSize() { return itemSize; }
    public String getItemPrice() { return itemPrice; }
    public String getItemCategory() { return itemCategory; }
    public String getItemStatus() { return itemStatus; }
    public String getItemWishlist() { return itemWishlist; }
    public String getItemOfferStatus() { return itemOfferStatus; }
    public String getSellerId() { return sellerId; }
    public String getReason() { return reason; } 

    // Setters
    public void setItemId(String itemId) { this.itemId = itemId; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setItemSize(String itemSize) { this.itemSize = itemSize; }
    public void setItemPrice(String itemPrice) { this.itemPrice = itemPrice; }
    public void setItemCategory(String itemCategory) { this.itemCategory = itemCategory; }
    public void setItemStatus(String itemStatus) { this.itemStatus = itemStatus; }
    public void setItemWishlist(String itemWishlist) { this.itemWishlist = itemWishlist; }
    public void setItemOfferStatus(String itemOfferStatus) { this.itemOfferStatus = itemOfferStatus; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    public void setReason(String reason) { this.reason = reason; } 


}
