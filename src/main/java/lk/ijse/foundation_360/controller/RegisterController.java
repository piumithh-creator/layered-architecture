package lk.ijse.foundation_360.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.foundation_360.bo.BOFactory;
import lk.ijse.foundation_360.bo.custom.UserBO;
import lk.ijse.foundation_360.util.SceneNavigator;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML public TextField txtUsername, txtEmail, txtContact;
    @FXML public PasswordField txtPassword;
    @FXML public ComboBox<String> cmbRole;
    @FXML public DatePicker dpCreateDate;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbRole.setItems(FXCollections.observableArrayList("ADMIN","CLIENT","DRAFTSMAN","ENGINEER","STORE_KEEPER","QS","SALES"));
        dpCreateDate.setValue(LocalDate.now());
    }

    @FXML
    public void submit(ActionEvent event) {
        String username = txtUsername.getText().trim(), password = txtPassword.getText().trim();
        String role = cmbRole.getValue(), email = txtEmail.getText().trim(), contact = txtContact.getText().trim();
        LocalDate createDate = dpCreateDate.getValue();
        if (!validate(username, password, role, email, contact, createDate)) return;
        try {
            if (userBO.isUsernameExists(username)) {
                showAlert(Alert.AlertType.ERROR, "Username already exists!"); return;
            }
            if (userBO.register(username, password, role, email, contact, createDate)) {
                showAlert(Alert.AlertType.INFORMATION, "Registration successful!"); clearForm(); navigateToLogin(event);
            } else showAlert(Alert.AlertType.ERROR, "Registration failed!");
        } catch (Exception e) { showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage()); }
    }

    @FXML
    public void back(ActionEvent event) { try { navigateToLogin(event); } catch (Exception e) { e.printStackTrace(); } }

    private boolean validate(String username, String password, String role, String email, String contact, LocalDate date) {
        if (username.isEmpty()) { showAlert(Alert.AlertType.WARNING, "Enter username!"); return false; }
        if (password.isEmpty() || password.length() < 6) { showAlert(Alert.AlertType.WARNING, "Password min 6 chars!"); return false; }
        if (role == null) { showAlert(Alert.AlertType.WARNING, "Select a role!"); return false; }
        if (email.isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) { showAlert(Alert.AlertType.WARNING, "Invalid email!"); return false; }
        if (contact.isEmpty() || !contact.matches("^0\\d{9}$")) { showAlert(Alert.AlertType.WARNING, "Contact must be 10 digits!"); return false; }
        if (date == null) { showAlert(Alert.AlertType.WARNING, "Select a date!"); return false; }
        return true;
    }

    private void navigateToLogin(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        SceneNavigator.navigateToScene(stage, "/lk/ijse/foundation_360/Login.fxml", "Foundation 360 - Login");
    }
    private void clearForm() { txtUsername.clear(); txtPassword.clear(); txtEmail.clear(); txtContact.clear(); cmbRole.setValue(null); dpCreateDate.setValue(LocalDate.now()); }
    private void showAlert(Alert.AlertType type, String msg) { new Alert(type, msg).show(); }
}
