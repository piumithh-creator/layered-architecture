package lk.ijse.foundation_360.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.foundation_360.bo.BOFactory;
import lk.ijse.foundation_360.bo.custom.EmployeeBO;
import lk.ijse.foundation_360.entity.Employee;

public class EmployeeController {

    @FXML private TextField txtUserId, txtName, txtStatus, txtUsername, txtPassword, txtSalary, txtEmail;
    @FXML private ComboBox<String> comboRole;
    @FXML private TableView<Employee> tableEmployees;
    @FXML private TableColumn<Employee, String> colEmployeeId, colName, colRole, colStatus;

    private final ObservableList<Employee> employeeList = FXCollections.observableArrayList();
    private final EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);

    @FXML
    public void initialize() {
        comboRole.getItems().addAll("Engineer", "QS", "Draftsman", "Store Keeper", "Sales Rep");
        colEmployeeId.setCellValueFactory(c -> c.getValue().employeeIdProperty());
        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colRole.setCellValueFactory(c -> c.getValue().roleProperty());
        colStatus.setCellValueFactory(c -> c.getValue().statusProperty());
        loadEmployees();
    }

    private void loadEmployees() {
        try {
            employeeList.setAll(employeeBO.getAllActiveEmployees());
            tableEmployees.setItems(employeeList);
        } catch (Exception e) { showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage()); }
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        String id = txtUserId.getText(), name = txtName.getText(), role = comboRole.getValue();
        String status = txtStatus.getText(), username = txtUsername.getText(), password = txtPassword.getText();
        if (id.isEmpty() || name.isEmpty() || role == null || status.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill all required fields!"); return;
        }
        Double salary = null;
        if (!txtSalary.getText().isEmpty()) {
            try { salary = Double.parseDouble(txtSalary.getText()); } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Salary must be a valid number!"); return;
            }
        }
        try {
            if (employeeBO.addEmployee(id, name, role, status, salary, username, password)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Employee added!"); clearFields(); loadEmployees();
            }
        } catch (Exception e) { showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage()); }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        String id = txtUserId.getText();
        if (id.isEmpty()) { showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter Employee ID!"); return; }
        Double salary = null;
        if (!txtSalary.getText().isEmpty()) {
            try { salary = Double.parseDouble(txtSalary.getText()); } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Salary must be valid!"); return;
            }
        }
        try {
            if (employeeBO.updateEmployee(id, txtName.getText(), comboRole.getValue(), txtStatus.getText(), salary)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Employee updated!"); clearFields(); loadEmployees();
            } else showAlert(Alert.AlertType.WARNING, "Not Found", "Employee ID not found!");
        } catch (Exception e) { showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage()); }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        String id = txtUserId.getText();
        if (id.isEmpty()) { showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter Employee ID!"); return; }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Deactivate this employee?");
        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                try {
                    if (employeeBO.deactivateEmployee(id)) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Employee deactivated!"); clearFields(); loadEmployees();
                    }
                } catch (Exception e) { showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage()); }
            }
        });
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String id = txtUserId.getText();
        if (id.isEmpty()) { showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter Employee ID!"); return; }
        try {
            String[] details = employeeBO.getEmployeeDetails(id);
            if (details != null) {
                txtName.setText(details[0]); comboRole.setValue(details[1]);
                txtStatus.setText(details[2]); txtSalary.setText(details[3]);
                txtUsername.setText(details[4]); txtPassword.setText(details[5]);
            } else showAlert(Alert.AlertType.WARNING, "Not Found", "Employee not found!");
        } catch (Exception e) { showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage()); }
    }

    @FXML private void handleBack(ActionEvent event) { txtUserId.getScene().getWindow().hide(); }
    @FXML private void handleSalaryView(ActionEvent event) { showAlert(Alert.AlertType.INFORMATION, "Salary View", "Salary view will be implemented here."); }

    private void clearFields() {
        txtUserId.clear(); txtName.clear(); txtStatus.clear(); txtUsername.clear();
        txtPassword.clear(); txtSalary.clear(); txtEmail.clear(); comboRole.getSelectionModel().clearSelection();
    }
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert a = new Alert(type); a.setTitle(title); a.setHeaderText(null); a.setContentText(content); a.showAndWait();
    }
}
