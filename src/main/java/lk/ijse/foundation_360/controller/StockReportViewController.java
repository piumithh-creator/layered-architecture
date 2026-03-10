package lk.ijse.foundation_360.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.foundation_360.entity.StockItem;
import lk.ijse.foundation_360.util.SceneNavigator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StockReportViewController implements Initializable {

    @FXML
    private TableView<StockItem> tblAvailableItems;
    @FXML
    private TableColumn<StockItem, String> colItemId;
    @FXML
    private TableColumn<StockItem, String> colItemName;
    @FXML
    private TableColumn<StockItem, String> colCategory;
    @FXML
    private TableColumn<StockItem, Integer> colQuantity;
    @FXML
    private TableColumn<StockItem, Integer> colMinRequired;
    @FXML
    private TableColumn<StockItem, String> colStatus;
    @FXML
    private TableColumn<StockItem, String> colStoreLocation;
    @FXML
    private TableColumn<StockItem, String> colLastUpdated;

    @FXML
    private TableView<StockItem> tblOutOfStockItems;
    @FXML
    private TableColumn<StockItem, String> colOutItemId;
    @FXML
    private TableColumn<StockItem, String> colOutItemName;
    @FXML
    private TableColumn<StockItem, String> colOutCategory;
    @FXML
    private TableColumn<StockItem, Integer> colOutQuantity;
    @FXML
    private TableColumn<StockItem, Integer> colOutMinRequired;
    @FXML
    private TableColumn<StockItem, String> colOutStatus;
    @FXML
    private TableColumn<StockItem, String> colOutStoreLocation;
    @FXML
    private TableColumn<StockItem, String> colOutLastUpdated;

    private ObservableList<StockItem> availableItems = FXCollections.observableArrayList();
    private ObservableList<StockItem> outOfStockItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        loadStockData();
    }

    private void setupTableColumns() {

        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colMinRequired.setCellValueFactory(new PropertyValueFactory<>("minRequired"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStoreLocation.setCellValueFactory(new PropertyValueFactory<>("storeLocation"));
        colLastUpdated.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));


        colOutItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colOutItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colOutCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colOutQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colOutMinRequired.setCellValueFactory(new PropertyValueFactory<>("minRequired"));
        colOutStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colOutStoreLocation.setCellValueFactory(new PropertyValueFactory<>("storeLocation"));
        colOutLastUpdated.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));

        tblAvailableItems.setItems(availableItems);
        tblOutOfStockItems.setItems(outOfStockItems);
    }


    private void loadStockData() {


        availableItems.add(new StockItem("ITM001", "Cement", "Materials", 500, 100, "Available", "Store A", "2024-01-15"));
        availableItems.add(new StockItem("ITM002", "Steel Bars", "Materials", 200, 50, "Available", "Store B", "2024-01-14"));

        outOfStockItems.add(new StockItem("ITM003", "Sand", "Materials", 0, 100, "Out of Stock", "Store A", "2024-01-10"));
        outOfStockItems.add(new StockItem("ITM004", "Gravel", "Materials", 20, 100, "Out of Stock", "Store B", "2024-01-12"));
    }

    @FXML
    private void exportToExcel(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Stock Report as Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(tblAvailableItems.getScene().getWindow());

            if (file != null) {
                exportToCSV(file);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Stock report exported to CSV successfully!");
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

            writer.write("Available Items Report\n\n");
            writer.write("Item ID,Item Name,Category,Quantity,Min Required,Status,Store Location,Last Updated\n");


            for (StockItem item : availableItems) {
                writer.write(String.format("%s,%s,%s,%d,%d,%s,%s,%s\n",
                    item.getItemId(), item.getItemName(), item.getCategory(),
                    item.getQuantity(), item.getMinRequired(), item.getStatus(),
                    item.getStoreLocation(), item.getLastUpdated()));
            }


            writer.write("\nOut of Stock Items Report\n\n");
            writer.write("Item ID,Item Name,Category,Quantity,Min Required,Status,Store Location,Last Updated\n");


            for (StockItem item : outOfStockItems) {
                writer.write(String.format("%s,%s,%s,%d,%d,%s,%s,%s\n",
                    item.getItemId(), item.getItemName(), item.getCategory(),
                    item.getQuantity(), item.getMinRequired(), item.getStatus(),
                    item.getStoreLocation(), item.getLastUpdated()));
            }
        }
    }

    @FXML
    private void back(ActionEvent event) {
        try {
            Stage stage = (Stage) tblAvailableItems.getScene().getWindow();
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
