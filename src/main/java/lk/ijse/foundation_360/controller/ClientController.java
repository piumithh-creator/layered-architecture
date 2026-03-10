package lk.ijse.foundation_360.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.foundation_360.bo.BOFactory;
import lk.ijse.foundation_360.bo.custom.ClientBO;
import lk.ijse.foundation_360.entity.Client;
import lk.ijse.foundation_360.util.UserSession;
import java.io.IOException;

public class ClientController {

    @FXML private TextField txtClientId, txtClientName, txtContact, txtEmail;
    @FXML private TableView<Client> tblClients;
    @FXML private TableColumn<Client, String> colClientId, colClientName, colContact, colEmail;

    private final ObservableList<Client> clientList = FXCollections.observableArrayList();
    private final ClientBO clientBO = (ClientBO) BOFactory.getInstance().getBO(BOFactory.BOType.CLIENT);

    @FXML
    private void initialize() {
        String userRole = UserSession.getRole();
        if (userRole == null || (!"ADMIN".equalsIgnoreCase(userRole) && !"SALES_REPRESENTATIVE".equalsIgnoreCase(userRole))) {
            showAlert(Alert.AlertType.ERROR, "Access Denied", "Only Administrators and Sales Representatives can access client details.");
            navigateBack(); return;
        }
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colClientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tblClients.setItems(clientList);
        loadClients();
    }

    private void loadClients() {
        try {
            clientList.setAll(clientBO.getAllClients());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void add(ActionEvent event) {
        if (txtClientId.getText().isEmpty() || txtClientName.getText().isEmpty() ||
                txtContact.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Fill all fields!"); return;
        }
        try {
            Client c = new Client(txtClientId.getText(), txtClientName.getText(),
                    txtContact.getText(), txtEmail.getText(), "Active");
            if (clientBO.addClient(c)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Client added!"); clearFields(); loadClients();
            }
        } catch (Exception e) { showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage()); }
    }

    @FXML
    private void update(ActionEvent event) {
        Client selected = tblClients.getSelectionModel().getSelectedItem();
        if (selected == null) { showAlert(Alert.AlertType.WARNING, "Selection", "Select a client first!"); return; }
        try {
            Client c = new Client(txtClientId.getText(), txtClientName.getText(),
                    txtContact.getText(), txtEmail.getText(), "Active");
            if (clientBO.updateClient(c)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Client updated!"); clearFields(); loadClients();
            }
        } catch (Exception e) { showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage()); }
    }

    @FXML
    private void delete(ActionEvent event) {
        Client selected = tblClients.getSelectionModel().getSelectedItem();
        if (selected == null) { showAlert(Alert.AlertType.WARNING, "Selection", "Select a client first!"); return; }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Deactivate this client?");
        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                try {
                    if (clientBO.deleteClient(selected.getClientId())) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Client deactivated!");
                        clearFields(); loadClients();
                    }
                } catch (Exception e) { showAlert(Alert.AlertType.ERROR, "Error", e.getMessage()); }
            }
        });
    }

    @FXML
    private void search(ActionEvent event) {
        String id = txtClientId.getText();
        if (id.isEmpty()) { showAlert(Alert.AlertType.INFORMATION, "Search", "Enter Client ID"); return; }
        try {
            Client c = clientBO.findClientById(id);
            if (c != null) {
                txtClientName.setText(c.getName()); txtContact.setText(c.getContact()); txtEmail.setText(c.getEmail());
            } else showAlert(Alert.AlertType.INFORMATION, "Search", "Client not found!");
        } catch (Exception e) { showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage()); }
    }

    @FXML private void reactivate(ActionEvent event) { showAlert(Alert.AlertType.INFORMATION, "Info", "Reactivate by re-adding the client."); }
    @FXML private void logout(ActionEvent event) { txtClientId.getScene().getWindow().hide(); }

    private void navigateBack() {
        try {
            Stage stage = (Stage) txtClientId.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/foundation_360/admindashboard.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
            stage.setScene(scene); stage.setMaximized(true); stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
    private void clearFields() { txtClientId.clear(); txtClientName.clear(); txtContact.clear(); txtEmail.clear(); }
    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type); a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}
