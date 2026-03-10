package lk.ijse.foundation_360.entity;

public class StockItem {
    private String itemId;
    private String itemName;
    private String category;
    private Integer quantity;
    private Integer minRequired;
    private String status;
    private String storeLocation;
    private String lastUpdated;

    public StockItem() {
    }

    public StockItem(String itemId, String itemName, String category, Integer quantity,
                     Integer minRequired, String status, String storeLocation, String lastUpdated) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.minRequired = minRequired;
        this.status = status;
        this.storeLocation = storeLocation;
        this.lastUpdated = lastUpdated;
    }


    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getMinRequired() { return minRequired; }
    public void setMinRequired(Integer minRequired) { this.minRequired = minRequired; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getStoreLocation() { return storeLocation; }
    public void setStoreLocation(String storeLocation) { this.storeLocation = storeLocation; }

    public String getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }
}
