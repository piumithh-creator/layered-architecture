package lk.ijse.foundation_360.entity;

public class Client {
    private String clientId, name, contact, email, status;
    public Client() {}
    public Client(String clientId, String name, String contact, String email, String status) {
        this.clientId = clientId; this.name = name; this.contact = contact;
        this.email = email; this.status = status;
    }
    public String getClientId() { return clientId; } public void setClientId(String v) { clientId = v; }
    public String getName() { return name; } public void setName(String v) { name = v; }
    public String getContact() { return contact; } public void setContact(String v) { contact = v; }
    public String getEmail() { return email; } public void setEmail(String v) { email = v; }
    public String getStatus() { return status; } public void setStatus(String v) { status = v; }
}
