package lk.ijse.foundation_360.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.foundation_360.bo.BOFactory;
import lk.ijse.foundation_360.bo.custom.OrderBO;
import lk.ijse.foundation_360.entity.Order;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    @FXML private TableView<Order> tblOrders;
    @FXML private TableColumn<Order, Integer> colOrderId;
    @FXML private TableColumn<Order, String> colClientId, colOrderDate, colStatus, colActions;
    @FXML private Label lblTotalOrders, lblPendingOrders, lblApprovedOrders, lblRejectedOrders;
    @FXML private ComboBox<String> cmbStatus;
    @FXML private TextField txtSearch;

    private final ObservableList<Order> orders = FXCollections.observableArrayList();
    private final ObservableList<Order> allOrders = FXCollections.observableArrayList();
    private final OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblOrders.setItems(orders);
        cmbStatus.setItems(FXCollections.observableArrayList("PENDING", "APPROVED", "REJECTED"));
        cmbStatus.setOnAction(e -> applyFilters());
        txtSearch.textProperty().addListener((ob, ov, nv) -> applyFilters());
        loadData(); updateStats();
    }

    private void loadData() {
        try {
            allOrders.setAll(orderBO.getAllOrders());
            orders.setAll(allOrders);
        } catch (Exception e) { System.err.println("Error loading orders: " + e.getMessage()); }
    }

    private void applyFilters() {
        orders.clear();
        String sf = cmbStatus.getValue(); String st = txtSearch.getText().toLowerCase().trim();
        for (Order o : allOrders) {
            if ((sf == null || sf.isEmpty() || o.getStatus().equals(sf)) &&
                (st.isEmpty() || o.getClientId().toLowerCase().contains(st))) orders.add(o);
        }
    }

    private void updateStats() {
        lblTotalOrders.setText(String.valueOf(allOrders.size()));
        lblPendingOrders.setText(String.valueOf(allOrders.stream().filter(o -> "PENDING".equals(o.getStatus())).count()));
        lblApprovedOrders.setText(String.valueOf(allOrders.stream().filter(o -> "APPROVED".equals(o.getStatus())).count()));
        lblRejectedOrders.setText(String.valueOf(allOrders.stream().filter(o -> "REJECTED".equals(o.getStatus())).count()));
    }

    @FXML private void addOrder(ActionEvent event) { showAlert("Not Implemented", "Add Order will be implemented."); }
    @FXML private void refreshData(ActionEvent event) { loadData(); updateStats(); showAlert("Success", "Refreshed!"); }
    @FXML private void clearFilter(ActionEvent event) { cmbStatus.setValue(null); txtSearch.clear(); applyFilters(); }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION); a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}
