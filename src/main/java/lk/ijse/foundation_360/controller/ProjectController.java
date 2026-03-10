package lk.ijse.foundation_360.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.foundation_360.bo.BOFactory;
import lk.ijse.foundation_360.bo.custom.ProjectBO;
import lk.ijse.foundation_360.entity.ProjectDetail;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectController implements Initializable {

    @FXML private TableView<ProjectDetail> tblProjects;
    @FXML private TableColumn<ProjectDetail, Integer> colProjectId;
    @FXML private TableColumn<ProjectDetail, String> colProjectName, colClientId, colStartDate, colEndDate, colStatus;
    @FXML private TableColumn<ProjectDetail, Integer> colDesignId;
    @FXML private TableColumn<ProjectDetail, Double> colEstimatedCost, colActualCost;
    @FXML private Label lblTotalProjects, lblOngoingProjects, lblCompletedProjects, lblTotalEstCost;
    @FXML private ComboBox<String> cmbStatus;
    @FXML private TextField txtSearch;

    private final ObservableList<ProjectDetail> projects = FXCollections.observableArrayList();
    private final ObservableList<ProjectDetail> allProjects = FXCollections.observableArrayList();
    private final ProjectBO projectBO = (ProjectBO) BOFactory.getInstance().getBO(BOFactory.BOType.PROJECT);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupColumns(); setupFilter(); loadData(); updateStats();
    }

    private void setupColumns() {
        colProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        colProjectName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colDesignId.setCellValueFactory(new PropertyValueFactory<>("designId"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colEstimatedCost.setCellValueFactory(new PropertyValueFactory<>("estimatedCost"));
        colActualCost.setCellValueFactory(new PropertyValueFactory<>("actualCost"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblProjects.setItems(projects);
    }

    private void setupFilter() {
        cmbStatus.setItems(FXCollections.observableArrayList("PLANNING", "ONGOING", "COMPLETED"));
        cmbStatus.setOnAction(e -> applyFilters());
        txtSearch.textProperty().addListener((ob, ov, nv) -> applyFilters());
    }

    private void loadData() {
        try {
            allProjects.setAll(projectBO.getAllProjects());
            projects.setAll(allProjects);
        } catch (Exception e) { showAlert("Error", "Failed to load projects: " + e.getMessage()); }
    }

    private void applyFilters() {
        projects.clear();
        String statusFilter = cmbStatus.getValue();
        String searchText = txtSearch.getText().toLowerCase().trim();
        for (ProjectDetail p : allProjects) {
            boolean ms = statusFilter == null || statusFilter.isEmpty() || p.getStatus().equals(statusFilter);
            boolean mt = searchText.isEmpty() || p.getProjectName().toLowerCase().contains(searchText)
                    || p.getClientId().toLowerCase().contains(searchText);
            if (ms && mt) projects.add(p);
        }
    }

    private void updateStats() {
        lblTotalProjects.setText(String.valueOf(allProjects.size()));
        lblOngoingProjects.setText(String.valueOf(allProjects.stream().filter(p -> "ONGOING".equals(p.getStatus())).count()));
        lblCompletedProjects.setText(String.valueOf(allProjects.stream().filter(p -> "COMPLETED".equals(p.getStatus())).count()));
        lblTotalEstCost.setText(String.format("Rs. %.2f", allProjects.stream().mapToDouble(ProjectDetail::getEstimatedCost).sum()));
    }

    @FXML private void addProject(ActionEvent event) { showAlert("Not Implemented", "Add Project will be implemented next."); }
    @FXML private void refreshData(ActionEvent event) { loadData(); updateStats(); showAlert("Success", "Refreshed!"); }
    @FXML private void clearFilter(ActionEvent event) { cmbStatus.setValue(null); txtSearch.clear(); applyFilters(); }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION); a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}
