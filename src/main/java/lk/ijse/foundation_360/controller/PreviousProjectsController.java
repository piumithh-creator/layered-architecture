package lk.ijse.foundation_360.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.foundation_360.db.DbConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class PreviousProjectsController implements Initializable {

    @FXML
    private TableView<ProjectData> tblPreviousProjects;

    @FXML
    private TableColumn<ProjectData, Integer> colProjectId;

    @FXML
    private TableColumn<ProjectData, String> colProjectName;

    @FXML
    private TableColumn<ProjectData, String> colClientId;

    @FXML
    private TableColumn<ProjectData, String> colStartDate;

    @FXML
    private TableColumn<ProjectData, String> colEndDate;

    @FXML
    private TableColumn<ProjectData, Double> colEstimatedCost;

    @FXML
    private TableColumn<ProjectData, Double> colActualCost;

    private ObservableList<ProjectData> projectList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        loadData();
    }

    private void setupTable() {
        colProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        colProjectName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colEstimatedCost.setCellValueFactory(new PropertyValueFactory<>("estimatedCost"));
        colActualCost.setCellValueFactory(new PropertyValueFactory<>("actualCost"));

        tblPreviousProjects.setItems(projectList);
    }

    private void loadData() {
        projectList.clear();
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT project_id, project_name, client_id, start_date, end_date, estimated_cost, actual_cost FROM previous_projects";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                projectList.add(new ProjectData(
                        rs.getInt("project_id"),
                        rs.getString("project_name"),
                        rs.getString("client_id"),
                        rs.getString("start_date"),
                        rs.getString("end_date"),
                        rs.getDouble("estimated_cost"),
                        rs.getDouble("actual_cost")
                ));
            }
        } catch (Exception e) {




            new Alert(Alert.AlertType.ERROR, "Error loading data: " + e.getMessage()).show();
        }
    }

    public static class ProjectData {
        private int projectId;
        private String projectName;
        private String clientId;
        private String startDate;
        private String endDate;
        private double estimatedCost;
        private double actualCost;

        public ProjectData(int projectId, String projectName, String clientId, String startDate, String endDate, double estimatedCost, double actualCost) {
            this.projectId = projectId;
            this.projectName = projectName;
            this.clientId = clientId;
            this.startDate = startDate;
            this.endDate = endDate;
            this.estimatedCost = estimatedCost;
            this.actualCost = actualCost;
        }

        public int getProjectId() { return projectId; }
        public String getProjectName() { return projectName; }
        public String getClientId() { return clientId; }
        public String getStartDate() { return startDate; }
        public String getEndDate() { return endDate; }
        public double getEstimatedCost() { return estimatedCost; }
        public double getActualCost() { return actualCost; }
    }
}
