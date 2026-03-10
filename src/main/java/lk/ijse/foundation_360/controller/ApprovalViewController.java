package lk.ijse.foundation_360.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ApprovalViewController {


    @FXML
    private void costSheetApproval(ActionEvent event) {


        showInfo("Cost Sheet Approval", "Cost Sheet Approval screen will be implemented here.");
    }


    @FXML
    private void timeApproval(ActionEvent event) {


        showInfo("Time Approval", "Time Approval screen will be implemented here.");
    }


    @FXML
    private void designApproval(ActionEvent event) {


        showInfo("Design Approval", "Design Approval screen will be implemented here.");
    }


    @FXML
    private void projectApproval(ActionEvent event) {


        showInfo("Project Approval", "Project Approval screen will be implemented here.");
    }


    @FXML
    private void expensesApproval(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/foundation_360/ExpensesApprovalView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Expenses Approval");
            stage.setMaximized(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to open expenses approval view: " + e.getMessage());
        }
    }


    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
