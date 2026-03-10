package lk.ijse.foundation_360.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.foundation_360.util.SceneNavigator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ProfitLossReportViewController implements Initializable {

    @FXML
    private Label lblReportPeriod;
    @FXML
    private Label lblGeneratedOn;
    @FXML
    private Label lblTotalRevenue;
    @FXML
    private Label lblTotalExpenses;
    @FXML
    private Label lblNetProfitLoss;
    @FXML
    private Label lblProfitMargin;

    @FXML
    private TableView<RevenueRecord> tblRevenue;
    @FXML
    private TableColumn<RevenueRecord, String> colProjectId;
    @FXML
    private TableColumn<RevenueRecord, String> colProjectName;
    @FXML
    private TableColumn<RevenueRecord, Double> colContractValue;
    @FXML
    private TableColumn<RevenueRecord, Double> colAdditionalRevenue;
    @FXML
    private TableColumn<RevenueRecord, Double> colTotalRevenue;

    @FXML
    private TableView<ExpenseCategory> tblExpenses;
    @FXML
    private TableColumn<ExpenseCategory, String> colExpenseCategory;
    @FXML
    private TableColumn<ExpenseCategory, Double> colExpenseAmount;
    @FXML
    private TableColumn<ExpenseCategory, Double> colExpensePercentage;

    private ObservableList<RevenueRecord> revenueRecords = FXCollections.observableArrayList();
    private ObservableList<ExpenseCategory> expenseCategories = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        loadReportData();
    }

    private void setupTableColumns() {

        colProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        colProjectName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        colContractValue.setCellValueFactory(new PropertyValueFactory<>("contractValue"));
        colAdditionalRevenue.setCellValueFactory(new PropertyValueFactory<>("additionalRevenue"));
        colTotalRevenue.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));


        colExpenseCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colExpenseAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colExpensePercentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));

        tblRevenue.setItems(revenueRecords);
        tblExpenses.setItems(expenseCategories);
    }


    private void loadReportData() {

        lblReportPeriod.setText("January 2024");
        lblGeneratedOn.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


        revenueRecords.add(new RevenueRecord("PRJ001", "Office Building Construction", 2500000.00, 150000.00, 2650000.00));
        revenueRecords.add(new RevenueRecord("PRJ002", "Residential Complex", 3200000.00, 200000.00, 3400000.00));
        revenueRecords.add(new RevenueRecord("PRJ003", "Shopping Mall", 5000000.00, 300000.00, 5300000.00));


        expenseCategories.add(new ExpenseCategory("Materials", 1200000.00, 25.0));
        expenseCategories.add(new ExpenseCategory("Labor", 800000.00, 16.7));
        expenseCategories.add(new ExpenseCategory("Equipment", 400000.00, 8.3));
        expenseCategories.add(new ExpenseCategory("Transportation", 200000.00, 4.2));
        expenseCategories.add(new ExpenseCategory("Utilities", 150000.00, 3.1));
        expenseCategories.add(new ExpenseCategory("Administrative", 100000.00, 2.1));
        expenseCategories.add(new ExpenseCategory("Marketing", 80000.00, 1.7));
        expenseCategories.add(new ExpenseCategory("Other", 50000.00, 1.0));


        double totalRevenue = revenueRecords.stream().mapToDouble(RevenueRecord::getTotalRevenue).sum();
        double totalExpenses = expenseCategories.stream().mapToDouble(ExpenseCategory::getAmount).sum();
        double netProfitLoss = totalRevenue - totalExpenses;
        double profitMargin = (totalRevenue > 0) ? (netProfitLoss / totalRevenue) * 100 : 0;


        lblTotalRevenue.setText(String.format("Rs. %.2f", totalRevenue));
        lblTotalExpenses.setText(String.format("Rs. %.2f", totalExpenses));


        if (netProfitLoss >= 0) {
            lblNetProfitLoss.setText(String.format("Rs. %.2f", netProfitLoss));
            lblNetProfitLoss.setStyle("-fx-text-fill: #28A745;");
        } else {
            lblNetProfitLoss.setText(String.format("Rs. %.2f", netProfitLoss));
            lblNetProfitLoss.setStyle("-fx-text-fill: #DC3545;");
        }

        lblProfitMargin.setText(String.format("%.2f%%", profitMargin));
    }

    @FXML
    private void exportToExcel(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Profit & Loss Report as Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(tblRevenue.getScene().getWindow());

            if (file != null) {
                exportToCSV(file);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Profit & Loss report exported to CSV successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to export report: " + e.getMessage());
        }
    }

    @FXML
    private void exportToPDF(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Export to PDF", "PDF export functionality would be implemented here.\n\nIn a full implementation, this would generate a formatted PDF report.");
    }

    private void exportToCSV(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {

            writer.write("Profit & Loss Report\n\n");
            writer.write("Report Period," + lblReportPeriod.getText() + "\n");
            writer.write("Generated On," + lblGeneratedOn.getText() + "\n\n");


            writer.write("Revenue Details\n");
            writer.write("Project ID,Project Name,Contract Value,Additional Revenue,Total Revenue\n");


            for (RevenueRecord record : revenueRecords) {
                writer.write(String.format("%s,%s,%.2f,%.2f,%.2f\n",
                    record.getProjectId(), record.getProjectName(),
                    record.getContractValue(), record.getAdditionalRevenue(),
                    record.getTotalRevenue()));
            }


            writer.write("\nExpense Details\n");
            writer.write("Category,Amount,Percentage (%)\n");


            for (ExpenseCategory category : expenseCategories) {
                writer.write(String.format("%s,%.2f,%.2f\n",
                    category.getCategory(), category.getAmount(), category.getPercentage()));
            }


            writer.write("\nSummary\n");
            writer.write("Total Revenue," + lblTotalRevenue.getText() + "\n");
            writer.write("Total Expenses," + lblTotalExpenses.getText() + "\n");
            writer.write("Net Profit/Loss," + lblNetProfitLoss.getText() + "\n");
            writer.write("Profit Margin," + lblProfitMargin.getText() + "\n");
        }
    }

    @FXML
    private void back(ActionEvent event) {
        try {
            Stage stage = (Stage) tblRevenue.getScene().getWindow();
            SceneNavigator.navigateToScene(stage, "/lk/ijse/foundation_360/ReportsView.fxml", "Reports");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public static class RevenueRecord {
        private String projectId;
        private String projectName;
        private double contractValue;
        private double additionalRevenue;
        private double totalRevenue;

        public RevenueRecord(String projectId, String projectName, double contractValue,
                           double additionalRevenue, double totalRevenue) {
            this.projectId = projectId;
            this.projectName = projectName;
            this.contractValue = contractValue;
            this.additionalRevenue = additionalRevenue;
            this.totalRevenue = totalRevenue;
        }


        public String getProjectId() { return projectId; }
        public String getProjectName() { return projectName; }
        public double getContractValue() { return contractValue; }
        public double getAdditionalRevenue() { return additionalRevenue; }
        public double getTotalRevenue() { return totalRevenue; }
    }

    public static class ExpenseCategory {
        private String category;
        private double amount;
        private double percentage;

        public ExpenseCategory(String category, double amount, double percentage) {
            this.category = category;
            this.amount = amount;
            this.percentage = percentage;
        }


        public String getCategory() { return category; }
        public double getAmount() { return amount; }
        public double getPercentage() { return percentage; }
    }
}
