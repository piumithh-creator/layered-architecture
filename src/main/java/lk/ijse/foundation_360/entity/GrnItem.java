package lk.ijse.foundation_360.entity;

public class GrnItem {
    private String itemCode;
    private String itemName;
    private String category;
    private Integer orderedQty;
    private Integer receivedQty;
    private Double unitPrice;
    private Double totalPrice;
    private String qualityStatus;

    public GrnItem() {
    }

    public GrnItem(String itemCode, String itemName, String category, Integer orderedQty,
                   Integer receivedQty, Double unitPrice, Double totalPrice, String qualityStatus) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.category = category;
        this.orderedQty = orderedQty;
        this.receivedQty = receivedQty;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.qualityStatus = qualityStatus;
    }


    public String getItemCode() { return itemCode; }
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getOrderedQty() { return orderedQty; }
    public void setOrderedQty(Integer orderedQty) { this.orderedQty = orderedQty; }

    public Integer getReceivedQty() { return receivedQty; }
    public void setReceivedQty(Integer receivedQty) { this.receivedQty = receivedQty; }

    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public String getQualityStatus() { return qualityStatus; }
    public void setQualityStatus(String qualityStatus) { this.qualityStatus = qualityStatus; }
}
