package lk.ijse.foundation_360.entity;
public class Order {
    private int orderId;
    private String clientId, orderDate, status;
    public Order() {}
    public Order(int orderId, String clientId, String orderDate, String status) {
        this.orderId = orderId; this.clientId = clientId; this.orderDate = orderDate; this.status = status;
    }
    public int getOrderId() { return orderId; } public void setOrderId(int v) { orderId = v; }
    public String getClientId() { return clientId; } public void setClientId(String v) { clientId = v; }
    public String getOrderDate() { return orderDate; } public void setOrderDate(String v) { orderDate = v; }
    public String getStatus() { return status; } public void setStatus(String v) { status = v; }
}
