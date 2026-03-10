package lk.ijse.foundation_360.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.foundation_360.bo.BOFactory;
import lk.ijse.foundation_360.bo.custom.UserBO;
import lk.ijse.foundation_360.util.SceneNavigator;
import lk.ijse.foundation_360.util.UserSession;

public class LoginController_1 {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    private void LoginOnAction(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        try {
            String role = userBO.login(username, password);
            if (role != null) {
                Integer userId = userBO.getUserId(username, password);
                UserSession.setRole(role.toUpperCase());
                UserSession.setUsername(username);
                UserSession.setUserId(userId);
                new Alert(Alert.AlertType.INFORMATION, "Login Successful!").show();
                openDashboardForRole(role, event);
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Username or Password!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
        }
    }

    private void openDashboardForRole(String role, ActionEvent event) {
        try {
            String fxmlPath = "ADMIN".equalsIgnoreCase(role)
                ? "/lk/ijse/foundation_360/admindashboard.fxml"
                : "/lk/ijse/foundation_360/empdashboard.fxml";
            String title = "ADMIN".equalsIgnoreCase(role) ? "Admin Dashboard" : "Employee Dashboard";
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            SceneNavigator.navigateToScene(stage, fxmlPath, title);
            stage.setResizable(true);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to open dashboard!").show();
        }
    }

    @FXML
    private void RegisterOnAction(ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            SceneNavigator.navigateToScene(stage, "/lk/ijse/foundation_360/register.fxml", "Register Form");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to open registration form!").show();
        }
    }
}
