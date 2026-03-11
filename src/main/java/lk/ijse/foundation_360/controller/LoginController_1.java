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
        
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter your username").show();
            return;
        }
        
        if (password == null || password.trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter your password").show();
            return;
        }
        
        try {
            String role = userBO.login(username.trim(), password);
            if (role != null) {
                Integer userId = userBO.getUserId(username.trim(), password);
                UserSession.setRole(role.toUpperCase());
                UserSession.setUsername(username.trim());
                UserSession.setUserId(userId);
                new Alert(Alert.AlertType.INFORMATION, "Login Successful!\nWelcome, " + username.trim()).show();
                openDashboardForRole(role, event);
            } else {
                new Alert(Alert.AlertType.ERROR, "Login Failed!\n\nInvalid username or password.\n\nTest Credentials:\nAdmin: admin / admin123\nEmployee: emp / emp123").show();
                txtPassword.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, 
                "Login Error!\n\n" + e.getMessage() + 
                "\n\nPlease ensure:\n" +
                "- MySQL is running on localhost:3306\n" +
                "- Database 'foundation_360' exists\n" +
                "- Database tables are initialized\n\n" +
                "Run DATABASE_SETUP.sql to initialize the database.").show();
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
