package com.ooad.Models;

import java.math.BigDecimal;

public class Item {
    private String itemId;
    private String itemName;
    private String itemSize;
    private BigDecimal itemPrice;
    private String itemCategory;
    private String itemStatus;
    private boolean itemWishlist;
    private boolean itemOfferStatus;

    public Item(String itemId, String itemName, String itemSize, BigDecimal itemPrice, 
                String itemCategory, String itemStatus, boolean itemWishlist, boolean itemOfferStatus) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemSize = itemSize;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemStatus = itemStatus;
        this.itemWishlist = itemWishlist;
        this.itemOfferStatus = itemOfferStatus;
    }

    // Getters
    public String getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public String getItemSize() { return itemSize; }
    public BigDecimal getItemPrice() { return itemPrice; }
    public String getItemCategory() { return itemCategory; }
    public String getItemStatus() { return itemStatus; }
    public boolean isItemWishlist() { return itemWishlist; }
    public boolean isItemOfferStatus() { return itemOfferStatus; }

    // Setters
    public void setItemId(String itemId) { this.itemId = itemId; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setItemSize(String itemSize) { this.itemSize = itemSize; }
    public void setItemPrice(BigDecimal itemPrice) { this.itemPrice = itemPrice; }
    public void setItemCategory(String itemCategory) { this.itemCategory = itemCategory; }
    public void setItemStatus(String itemStatus) { this.itemStatus = itemStatus; }
    public void setItemWishlist(boolean itemWishlist) { this.itemWishlist = itemWishlist; }
    public void setItemOfferStatus(boolean itemOfferStatus) { this.itemOfferStatus = itemOfferStatus; }

    @Override
    public String toString() {
        return "Item{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemSize='" + itemSize + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemCategory='" + itemCategory + '\'' +
                ", itemStatus='" + itemStatus + '\'' +
                ", itemWishlist=" + itemWishlist +
                ", itemOfferStatus=" + itemOfferStatus +
                '}';
    }
}
