package lk.ijse.foundation_360.entity;
import java.time.LocalDate;
public class User {
    private String username, password, role, email, contact;
    private LocalDate createDate;
    public User() {}
    public String getUsername() { return username; } public void setUsername(String v) { username = v; }
    public String getPassword() { return password; } public void setPassword(String v) { password = v; }
    public String getRole() { return role; } public void setRole(String v) { role = v; }
    public String getEmail() { return email; } public void setEmail(String v) { email = v; }
    public String getContact() { return contact; } public void setContact(String v) { contact = v; }
    public LocalDate getCreateDate() { return createDate; } public void setCreateDate(LocalDate v) { createDate = v; }
}
