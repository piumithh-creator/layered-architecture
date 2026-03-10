package lk.ijse.foundation_360.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import lk.ijse.foundation_360.util.SceneNavigator;

public class ReportsViewController {


    @FXML
    private void stockReport(ActionEvent event) {
        try {
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            SceneNavigator.navigateToScene(stage, "/lk/ijse/foundation_360/StockReportView.fxml", "Stock Report");
        } catch (Exception e) {
            e.printStackTrace();
            showInfo("Error", "Failed to load Stock Report!");
        }
    }


    @FXML
    private void grnReport(ActionEvent event) {
        try {
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            SceneNavigator.navigateToScene(stage, "/lk/ijse/foundation_360/GrnReportView.fxml", "GRN Report");
        } catch (Exception e) {
            e.printStackTrace();
            showInfo("Error", "Failed to load GRN Report!");
        }
    }


    @FXML
    private void projectReport(ActionEvent event) {
        try {
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            SceneNavigator.navigateToScene(stage, "/lk/ijse/foundation_360/ProjectReportView.fxml", "Project Report");
        } catch (Exception e) {
            e.printStackTrace();
            showInfo("Error", "Failed to load Project Report!");
        }
    }


    @FXML
    private void salaryReport(ActionEvent event) {
        try {
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            SceneNavigator.navigateToScene(stage, "/lk/ijse/foundation_360/SalaryReportView.fxml", "Salary Report");
        } catch (Exception e) {
            e.printStackTrace();
            showInfo("Error", "Failed to load Salary Report!");
        }
    }


    @FXML
    private void expensesReport(ActionEvent event) {
        try {
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            SceneNavigator.navigateToScene(stage, "/lk/ijse/foundation_360/ExpensesReportView.fxml", "Expenses Report");
        } catch (Exception e) {
            e.printStackTrace();
            showInfo("Error", "Failed to load Expenses Report!");
        }
    }


    @FXML
    private void profitLossReport(ActionEvent event) {
        try {
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            SceneNavigator.navigateToScene(stage, "/lk/ijse/foundation_360/ProfitLossReportView.fxml", "Profit & Loss Report");
        } catch (Exception e) {
            e.printStackTrace();
            showInfo("Error", "Failed to load Profit & Loss Report!");
        }
    }


    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
