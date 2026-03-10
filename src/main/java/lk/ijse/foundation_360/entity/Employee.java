package lk.ijse.foundation_360.entity;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class Employee {
    private final StringProperty employeeId, name, role, status;
    public Employee(String employeeId, String name, String role, String status) {
        this.employeeId = new SimpleStringProperty(employeeId);
        this.name = new SimpleStringProperty(name);
        this.role = new SimpleStringProperty(role);
        this.status = new SimpleStringProperty(status);
    }
    public StringProperty employeeIdProperty() { return employeeId; }
    public StringProperty nameProperty() { return name; }
    public StringProperty roleProperty() { return role; }
    public StringProperty statusProperty() { return status; }
    public String getEmployeeId() { return employeeId.get(); }
    public String getName() { return name.get(); }
    public String getRole() { return role.get(); }
    public String getStatus() { return status.get(); }
}
