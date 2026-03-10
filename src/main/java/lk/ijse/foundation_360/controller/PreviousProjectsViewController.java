package lk.ijse.foundation_360.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.foundation_360.bo.BOFactory;
import lk.ijse.foundation_360.bo.custom.ProjectBO;
import lk.ijse.foundation_360.entity.Project;
import java.net.URL;
import java.util.ResourceBundle;

public class PreviousProjectsViewController implements Initializable {

    @FXML private TextField txtSearch;
    @FXML private TableView<Project> tblProjects;
    @FXML private TableColumn<Project, String> colProjectId, colProjectName, colClient, colCompletionDate;
    @FXML private TableColumn<Project, Double> colTotalCost, colRevenue, colProfitLoss;
    @FXML private TableColumn<Project, Void> colActions;
    @FXML private Label lblTotalProjects, lblTotalRevenue, lblTotalCosts, lblNetProfitLoss;

    private final ObservableList<Project> projects = FXCollections.observableArrayList();
    private final ObservableList<Project> filteredProjects = FXCollections.observableArrayList();
    private final ProjectBO projectBO = (ProjectBO) BOFactory.getInstance().getBO(BOFactory.BOType.PROJECT);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        colProjectName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        colClient.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        colCompletionDate.setCellValueFactory(new PropertyValueFactory<>("completionDate"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colRevenue.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        colProfitLoss.setCellValueFactory(new PropertyValueFactory<>("profitLoss"));
        tblProjects.setItems(filteredProjects);
        loadData(); setupActionColumn();
    }

    private void loadData() {
        try {
            projects.setAll(projectBO.getCompletedProjects());
        } catch (Exception e) {
            projects.add(new Project("PRJ-001", "Office Building", "ABC Co.", "2024-04-30", 2500000.0, 3200000.0));
        }
        filteredProjects.setAll(projects); updateSummary();
    }

    private void setupActionColumn() {
        colActions.setCellFactory(p -> new TableCell<>() {
            private final Button view = new Button("View");
            { view.setOnAction(e -> viewProjectDetails(getTableView().getItems().get(getIndex()))); }
            @Override protected void updateItem(Void v, boolean empty) { super.updateItem(v, empty); setGraphic(empty ? null : view); }
        });
    }

    private void viewProjectDetails(Project project) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/foundation_360/ProjectReportView.fxml"));
            Stage stage = (Stage) tblProjects.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
            stage.setScene(scene); stage.setTitle("Project Report - " + project.getProjectName()); stage.setMaximized(true); stage.show();
        } catch (Exception e) { new Alert(Alert.AlertType.ERROR, "Failed to open project details: " + e.getMessage()).show(); }
    }

    private void updateSummary() {
        double rev = filteredProjects.stream().mapToDouble(Project::getRevenue).sum();
        double cost = filteredProjects.stream().mapToDouble(Project::getTotalCost).sum();
        double pl = rev - cost;
        lblTotalProjects.setText(String.valueOf(filteredProjects.size()));
        lblTotalRevenue.setText(String.format("Rs. %.2f", rev));
        lblTotalCosts.setText(String.format("Rs. %.2f", cost));
        lblNetProfitLoss.setText(String.format("Rs. %.2f", pl));
        lblNetProfitLoss.setStyle("-fx-text-fill: " + (pl >= 0 ? "#28A745" : "#DC3545") + ";");
    }

    @FXML private void handleSearch(ActionEvent e) {
        String t = txtSearch.getText().toLowerCase().trim();
        filteredProjects.setAll(t.isEmpty() ? projects : projects.stream().filter(p ->
            p.getProjectId().toLowerCase().contains(t) || p.getProjectName().toLowerCase().contains(t) ||
            p.getClientName().toLowerCase().contains(t)).toList());
        updateSummary();
    }

    @FXML private void handleClear(ActionEvent e) { txtSearch.clear(); filteredProjects.setAll(projects); updateSummary(); }

    @FXML private void back(ActionEvent e) {
        try {
            Stage stage = (Stage) tblProjects.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/foundation_360/admindashboard.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
            stage.setScene(scene); stage.setMaximized(true); stage.show();
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}
