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
import lk.ijse.foundation_360.entity.ProjectPhase;
import lk.ijse.foundation_360.util.SceneNavigator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectReportViewController implements Initializable {

    @FXML
    private Label lblProjectId;
    @FXML
    private Label lblProjectName;
    @FXML
    private Label lblLocation;
    @FXML
    private Label lblClientName;
    @FXML
    private Label lblSalesRep;
    @FXML
    private Label lblProjectStatus;
    @FXML
    private Label lblTotalCost;
    @FXML
    private Label lblTotalDuration;
    @FXML
    private Label lblEstimatedRevenue;
    @FXML
    private Label lblProfitLoss;
    @FXML
    private Label lblFinalApproval;

    @FXML
    private TableView<ProjectPhase> tblPhases;
    @FXML
    private TableColumn<ProjectPhase, String> colPhaseName;
    @FXML
    private TableColumn<ProjectPhase, String> colStartDate;
    @FXML
    private TableColumn<ProjectPhase, String> colEndDate;
    @FXML
    private TableColumn<ProjectPhase, Integer> colDuration;
    @FXML
    private TableColumn<ProjectPhase, Double> colCostPerPhase;
    @FXML
    private TableColumn<ProjectPhase, String> colCompletionStatus;

    private ObservableList<ProjectPhase> phases = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        loadProjectData();
    }

    private void setupTableColumns() {
        colPhaseName.setCellValueFactory(new PropertyValueFactory<>("phaseName"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colCostPerPhase.setCellValueFactory(new PropertyValueFactory<>("costPerPhase"));
        colCompletionStatus.setCellValueFactory(new PropertyValueFactory<>("completionStatus"));

        tblPhases.setItems(phases);
    }


    private void loadProjectData() {

        lblProjectId.setText("PRJ-001");
        lblProjectName.setText("Building Construction");
        lblLocation.setText("Colombo");
        lblClientName.setText("ABC Company");
        lblSalesRep.setText("John Doe");
        lblProjectStatus.setText("Completed");
        lblFinalApproval.setText("Approved");


        phases.add(new ProjectPhase("Foundation", "2024-01-01", "2024-01-15", 14, 500000.00, "Completed"));
        phases.add(new ProjectPhase("Structure", "2024-01-16", "2024-02-28", 43, 1200000.00, "Completed"));
        phases.add(new ProjectPhase("Finishing", "2024-03-01", "2024-04-30", 60, 800000.00, "Completed"));

        double totalCost = phases.stream().mapToDouble(ProjectPhase::getCostPerPhase).sum();
        int totalDuration = phases.stream().mapToInt(ProjectPhase::getDuration).sum();


        double estimatedRevenue = 2800000.00;
        double profitLoss = estimatedRevenue - totalCost;

        lblTotalCost.setText(String.format("Rs. %.2f", totalCost));
        lblTotalDuration.setText(totalDuration + " Days");
        lblEstimatedRevenue.setText(String.format("Rs. %.2f", estimatedRevenue));


        if (profitLoss >= 0) {
            lblProfitLoss.setText(String.format("Rs. %.2f", profitLoss));
            lblProfitLoss.setStyle("-fx-text-fill: #28A745;");
        } else {
            lblProfitLoss.setText(String.format("Rs. %.2f", profitLoss));
            lblProfitLoss.setStyle("-fx-text-fill: #DC3545;");
        }
    }

    @FXML
    private void exportToExcel(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Project Report as Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(tblPhases.getScene().getWindow());

            if (file != null) {
                exportToCSV(file);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Project report exported to CSV successfully!");
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

            writer.write("Project Report\n\n");
            writer.write(String.format("Project ID,%s\n", lblProjectId.getText()));
            writer.write(String.format("Project Name,%s\n", lblProjectName.getText()));
            writer.write(String.format("Location,%s\n", lblLocation.getText()));
            writer.write(String.format("Client Name,%s\n", lblClientName.getText()));
            writer.write(String.format("Sales Representative,%s\n", lblSalesRep.getText()));
            writer.write(String.format("Project Status,%s\n", lblProjectStatus.getText()));
            writer.write(String.format("Final Approval Status,%s\n\n", lblFinalApproval.getText()));


            writer.write("Project Phases\n");
            writer.write("Phase Name,Start Date,End Date,Duration (Days),Cost Per Phase,Completion Status\n");


            for (ProjectPhase phase : phases) {
                writer.write(String.format("%s,%s,%s,%d,%.2f,%s\n",
                    phase.getPhaseName(), phase.getStartDate(), phase.getEndDate(),
                    phase.getDuration(), phase.getCostPerPhase(), phase.getCompletionStatus()));
            }


            writer.write("\nSummary\n");
            writer.write(String.format("Total Project Cost,%s\n", lblTotalCost.getText()));
            writer.write(String.format("Total Time Duration,%s\n", lblTotalDuration.getText()));
            writer.write(String.format("Estimated Revenue,%s\n", lblEstimatedRevenue.getText()));
            writer.write(String.format("Profit/Loss,%s\n", lblProfitLoss.getText()));
        }
    }

    @FXML
    private void back(ActionEvent event) {
        try {
            Stage stage = (Stage) tblPhases.getScene().getWindow();
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
