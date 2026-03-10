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
import lk.ijse.foundation_360.entity.GrnItem;
import lk.ijse.foundation_360.util.SceneNavigator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GrnReportViewController implements Initializable {

    @FXML
    private Label lblGrnNumber;
    @FXML
    private Label lblSupplierName;
    @FXML
    private Label lblSupplierContact;
    @FXML
    private Label lblReceivedDate;
    @FXML
    private Label lblProjectId;
    @FXML
    private Label lblTotalValue;
    @FXML
    private Label lblReceivedBy;
    @FXML
    private Label lblVerifiedBy;

    @FXML
    private TableView<GrnItem> tblGrnItems;
    @FXML
    private TableColumn<GrnItem, String> colItemCode;
    @FXML
    private TableColumn<GrnItem, String> colItemName;
    @FXML
    private TableColumn<GrnItem, String> colCategory;
    @FXML
    private TableColumn<GrnItem, Integer> colOrderedQty;
    @FXML
    private TableColumn<GrnItem, Integer> colReceivedQty;
    @FXML
    private TableColumn<GrnItem, Double> colUnitPrice;
    @FXML
    private TableColumn<GrnItem, Double> colTotalPrice;
    @FXML
    private TableColumn<GrnItem, String> colQualityStatus;

    private ObservableList<GrnItem> grnItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        loadGrnData();
    }

    private void setupTableColumns() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colOrderedQty.setCellValueFactory(new PropertyValueFactory<>("orderedQty"));
        colReceivedQty.setCellValueFactory(new PropertyValueFactory<>("receivedQty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colQualityStatus.setCellValueFactory(new PropertyValueFactory<>("qualityStatus"));

        tblGrnItems.setItems(grnItems);
    }


    private void loadGrnData() {

        lblGrnNumber.setText("GRN-001");
        lblSupplierName.setText("ABC Suppliers");
        lblSupplierContact.setText("0771234567");
        lblReceivedDate.setText("2024-01-15");
        lblProjectId.setText("PRJ-001");
        lblReceivedBy.setText("Store Keeper");
        lblVerifiedBy.setText("Admin");


        grnItems.add(new GrnItem("ITM001", "Cement", "Materials", 100, 100, 850.00, 85000.00, "Accepted"));
        grnItems.add(new GrnItem("ITM002", "Steel Bars", "Materials", 50, 48, 1200.00, 57600.00, "Accepted"));

        double total = grnItems.stream().mapToDouble(GrnItem::getTotalPrice).sum();
        lblTotalValue.setText(String.format("Rs. %.2f", total));
    }

    @FXML
    private void exportToExcel(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save GRN Report as Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(tblGrnItems.getScene().getWindow());

            if (file != null) {
                exportToCSV(file);
                showAlert(Alert.AlertType.INFORMATION, "Success", "GRN report exported to CSV successfully!");
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

            writer.write("Goods Receive Note Report\n\n");
            writer.write("GRN Number," + lblGrnNumber.getText() + "\n");
            writer.write("Supplier Name," + lblSupplierName.getText() + "\n");
            writer.write("Supplier Contact," + lblSupplierContact.getText() + "\n");
            writer.write("Received Date," + lblReceivedDate.getText() + "\n");
            writer.write("Project ID," + lblProjectId.getText() + "\n\n");


            writer.write("Item Code,Item Name,Category,Ordered Quantity,Received Quantity,Unit Price,Total Price,Quality Status\n");


            for (GrnItem item : grnItems) {
                writer.write(String.format("%s,%s,%s,%d,%d,%.2f,%.2f,%s\n",
                    item.getItemCode(), item.getItemName(), item.getCategory(),
                    item.getOrderedQty(), item.getReceivedQty(), item.getUnitPrice(),
                    item.getTotalPrice(), item.getQualityStatus()));
            }


            writer.write("\nSummary\n");
            writer.write("Total Received Value," + lblTotalValue.getText() + "\n");
            writer.write("Received By," + lblReceivedBy.getText() + "\n");
            writer.write("Verified By," + lblVerifiedBy.getText() + "\n");
        }
    }

    @FXML
    private void back(ActionEvent event) {
        try {
            Stage stage = (Stage) tblGrnItems.getScene().getWindow();
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
}
