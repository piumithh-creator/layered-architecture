package lk.ijse.foundation_360.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.foundation_360.entity.SalaryRecord;

import java.net.URL;
import java.util.ResourceBundle;

public class SalaryViewController implements Initializable {

    @FXML private TextField txtSalaryId;
    @FXML private TextField txtEmployeeId;
    @FXML private TextField txtEmployeeName;
    @FXML private TextField txtBasicSalary;
    @FXML private TextField txtAllowance;
    @FXML private TextField txtDeduction;

    @FXML private TableView<SalaryRecord> tblSalary;
    @FXML private TableColumn<SalaryRecord, String> colSalaryId;
    @FXML private TableColumn<SalaryRecord, String> colEmpId;
    @FXML private TableColumn<SalaryRecord, String> colEmpName;
    @FXML private TableColumn<SalaryRecord, Double> colBasic;
    @FXML private TableColumn<SalaryRecord, Double> colAllowance;
    @FXML private TableColumn<SalaryRecord, Double> colDeduction;
    @FXML private TableColumn<SalaryRecord, Double> colNetSalary;

    private ObservableList<SalaryRecord> salaryList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        loadData();
    }

    private void setupTable() {
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colBasic.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        colAllowance.setCellValueFactory(new PropertyValueFactory<>("allowances"));
        colDeduction.setCellValueFactory(new PropertyValueFactory<>("deductions"));
        colNetSalary.setCellValueFactory(new PropertyValueFactory<>("netSalary"));

        tblSalary.setItems(salaryList);


        tblSalary.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillForm(newSelection);
            }
        });
    }

    private void loadData() {

        salaryList.add(new SalaryRecord("SAL-001", "EMP-001", "John Doe", "Engineer", 50000.0, 0, 0.0, 5000.0, 2000.0, 53000.0, "Paid"));
        salaryList.add(new SalaryRecord("SAL-002", "EMP-002", "Jane Smith", "Architect", 60000.0, 0, 0.0, 6000.0, 2500.0, 63500.0, "Paid"));
    }

    @FXML
    private void addSalary(ActionEvent event) {
        try {
            String id = txtSalaryId.getText();
            String empId = txtEmployeeId.getText();
            String name = txtEmployeeName.getText();

            if (id.isEmpty() || empId.isEmpty() || name.isEmpty()) {
                showAlert("Error", "Please fill all fields.");
                return;
            }

            double basic = Double.parseDouble(txtBasicSalary.getText());
            double allowance = Double.parseDouble(txtAllowance.getText());
            double deduction = Double.parseDouble(txtDeduction.getText());
            double net = basic + allowance - deduction;

            SalaryRecord record = new SalaryRecord(id, empId, name, "Employee", basic, 0, 0.0, allowance, deduction, net, "Paid");
            salaryList.add(record);
            clearFields(null);
            showAlert("Success", "Salary record added successfully.");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid numeric input.");
        }
    }

    @FXML
    private void updateSalary(ActionEvent event) {
        SalaryRecord selected = tblSalary.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setSalaryId(txtSalaryId.getText());
                selected.setEmployeeId(txtEmployeeId.getText());
                selected.setEmployeeName(txtEmployeeName.getText());
                selected.setBasicSalary(Double.parseDouble(txtBasicSalary.getText()));
                selected.setAllowances(Double.parseDouble(txtAllowance.getText()));
                selected.setDeductions(Double.parseDouble(txtDeduction.getText()));
                selected.setNetSalary(selected.getBasicSalary() + selected.getAllowances() - selected.getDeductions());

                tblSalary.refresh();
                clearFields(null);
                showAlert("Success", "Salary record updated successfully.");
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid numeric input.");
            }
        } else {
            showAlert("Warning", "Please select a record to update.");
        }
    }

    @FXML
    private void deleteSalary(ActionEvent event) {
        SalaryRecord selected = tblSalary.getSelectionModel().getSelectedItem();
        if (selected != null) {
            salaryList.remove(selected);
            clearFields(null);
            showAlert("Success", "Salary record deleted successfully.");
        } else {
            showAlert("Warning", "Please select a record to delete.");
        }
    }

    @FXML
    private void clearFields(ActionEvent event) {
        txtSalaryId.clear();
        txtEmployeeId.clear();
        txtEmployeeName.clear();
        txtBasicSalary.clear();
        txtAllowance.clear();
        txtDeduction.clear();
        tblSalary.getSelectionModel().clearSelection();
    }

    private void fillForm(SalaryRecord record) {
        txtSalaryId.setText(record.getSalaryId());
        txtEmployeeId.setText(record.getEmployeeId());
        txtEmployeeName.setText(record.getEmployeeName());
        txtBasicSalary.setText(String.valueOf(record.getBasicSalary()));
        txtAllowance.setText(String.valueOf(record.getAllowances()));
        txtDeduction.setText(String.valueOf(record.getDeductions()));
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
