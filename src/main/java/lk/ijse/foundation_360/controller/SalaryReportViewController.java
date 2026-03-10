package lk.ijse.foundation_360.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.foundation_360.bo.BOFactory;
import lk.ijse.foundation_360.bo.custom.SalaryBO;
import lk.ijse.foundation_360.entity.SalaryRecord;
import lk.ijse.foundation_360.util.SceneNavigator;
import java.io.*;
import java.net.URL;
import java.time.YearMonth;
import java.util.ResourceBundle;

public class SalaryReportViewController implements Initializable {

    @FXML private Label lblMonthYear, lblDepartment, lblEmployeeType, lblTotalSalary, lblApprovedBy;
    @FXML private TableView<SalaryRecord> tblSalary;
    @FXML private TableColumn<SalaryRecord, String> colEmployeeId, colEmployeeName, colRole, colPaymentStatus;
    @FXML private TableColumn<SalaryRecord, Double> colBasicSalary, colOtAmount, colAllowances, colDeductions, colNetSalary;
    @FXML private TableColumn<SalaryRecord, Integer> colOtHours;

    private final ObservableList<SalaryRecord> salaryRecords = FXCollections.observableArrayList();
    private final SalaryBO salaryBO = (SalaryBO) BOFactory.getInstance().getBO(BOFactory.BOType.SALARY);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colBasicSalary.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        colOtHours.setCellValueFactory(new PropertyValueFactory<>("otHours"));
        colOtAmount.setCellValueFactory(new PropertyValueFactory<>("otAmount"));
        colAllowances.setCellValueFactory(new PropertyValueFactory<>("allowances"));
        colDeductions.setCellValueFactory(new PropertyValueFactory<>("deductions"));
        colNetSalary.setCellValueFactory(new PropertyValueFactory<>("netSalary"));
        colPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        tblSalary.setItems(salaryRecords);
        loadData();
    }

    private void loadData() {
        try {
            salaryRecords.setAll(salaryBO.getSalaryReport());
            double total = salaryRecords.stream().mapToDouble(SalaryRecord::getNetSalary).sum();
            lblMonthYear.setText(YearMonth.now().toString()); lblDepartment.setText("All Departments");
            lblEmployeeType.setText("All Types"); lblApprovedBy.setText("Admin");
            lblTotalSalary.setText(String.format("Rs. %.2f", total));
        } catch (Exception e) { showAlert(Alert.AlertType.ERROR, "Error", e.getMessage()); }
    }

    @FXML private void exportToExcel(ActionEvent e) {
        try {
            FileChooser fc = new FileChooser(); fc.setTitle("Save Salary Report");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fc.showSaveDialog(tblSalary.getScene().getWindow());
            if (file != null) {
                try (FileWriter w = new FileWriter(file)) {
                    w.write("Employee ID,Name,Role,Basic Salary,OT Hours,OT Amount,Allowances,Deductions,Net Salary,Status\n");
                    for (SalaryRecord r : salaryRecords)
                        w.write(String.format("%s,%s,%s,%.2f,%d,%.2f,%.2f,%.2f,%.2f,%s\n",
                            r.getEmployeeId(), r.getEmployeeName(), r.getRole(), r.getBasicSalary(),
                            r.getOtHours(), r.getOtAmount(), r.getAllowances(), r.getDeductions(),
                            r.getNetSalary(), r.getPaymentStatus()));
                }
                showAlert(Alert.AlertType.INFORMATION, "Success", "Exported to CSV!");
            }
        } catch (Exception ex) { showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage()); }
    }

    @FXML private void exportToPDF(ActionEvent e) { showAlert(Alert.AlertType.INFORMATION, "Export to PDF", "PDF export will be implemented here."); }

    @FXML private void back(ActionEvent e) {
        try { Stage s = (Stage) tblSalary.getScene().getWindow(); SceneNavigator.navigateToScene(s, "/lk/ijse/foundation_360/ReportsView.fxml", "Reports"); }
        catch (Exception ex) { ex.printStackTrace(); }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert a = new Alert(type); a.setTitle(title); a.setHeaderText(null); a.setContentText(content); a.showAndWait();
    }
}
