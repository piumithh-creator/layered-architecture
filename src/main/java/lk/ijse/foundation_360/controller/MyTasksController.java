package lk.ijse.foundation_360.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import lk.ijse.foundation_360.db.DbConnection;
import lk.ijse.foundation_360.util.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MyTasksController {

    @FXML
    private ComboBox<String> cmbStatusFilter;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<TaskData> tblTasks;

    @FXML
    private TableColumn<TaskData, String> colTaskId;

    @FXML
    private TableColumn<TaskData, String> colTaskName;

    @FXML
    private TableColumn<TaskData, String> colDescription;

    @FXML
    private TableColumn<TaskData, String> colProject;

    @FXML
    private TableColumn<TaskData, String> colPriority;

    @FXML
    private TableColumn<TaskData, String> colStatus;

    @FXML
    private TableColumn<TaskData, String> colDueDate;

    @FXML
    private TableColumn<TaskData, Void> colActions;

    @FXML
    private Label lblTotalTasks;

    @FXML
    private Label lblPendingTasks;

    @FXML
    private Label lblInProgressTasks;

    @FXML
    private Label lblCompletedTasks;

    private ObservableList<TaskData> taskList = FXCollections.observableArrayList();
    private ObservableList<TaskData> filteredList = FXCollections.observableArrayList();


    public void initialize() {
        setupTableColumns();
        cmbStatusFilter.setValue("All Tasks");
        loadTasks();
        updateStatistics();
    }


    private void setupTableColumns() {
        colTaskId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().taskId));
        colTaskName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().taskName));
        colDescription.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().description));
        colProject.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().projectName));
        colPriority.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().priority));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().status));
        colDueDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().dueDate));


        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button btnView = new Button("View");
            private final Button btnUpdate = new Button("Update");
            private final HBox hbox = new HBox(8, btnView, btnUpdate);

            {
                btnView.setStyle("-fx-background-color: #3182ce; -fx-text-fill: white; -fx-padding: 5 10;");
                btnUpdate.setStyle("-fx-background-color: #d69e2e; -fx-text-fill: white; -fx-padding: 5 10;");
                hbox.setAlignment(Pos.CENTER);

                btnView.setOnAction(e -> {
                    TaskData task = getTableView().getItems().get(getIndex());
                    viewTask(task);
                });

                btnUpdate.setOnAction(e -> {
                    TaskData task = getTableView().getItems().get(getIndex());
                    updateTaskStatus(task);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hbox);
            }
        });
    }


    private void loadTasks() {
        taskList.clear();
        try {
            Connection conn = DbConnection.getInstance().getConnection();
            int userId = UserSession.getUserId();

            String query = "SELECT t.task_id, t.task_name, t.description, p.project_name, " +
                          "t.priority, t.status, t.due_date " +
                          "FROM employee_tasks t " +
                          "LEFT JOIN project p ON t.project_id = p.project_id " +
                          "WHERE t.employee_id = ? " +
                          "ORDER BY t.due_date ASC, t.priority DESC";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, userId);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    TaskData task = new TaskData(
                        String.valueOf(rs.getInt("task_id")),
                        rs.getString("task_name"),
                        rs.getString("description"),
                        rs.getString("project_name") != null ? rs.getString("project_name") : "N/A",
                        rs.getString("priority"),
                        rs.getString("status"),
                        rs.getDate("due_date") != null ? rs.getDate("due_date").toString() : "N/A"
                    );
                    taskList.add(task);
                }
            }

            filteredList.setAll(taskList);
            tblTasks.setItems(filteredList);
            updateStatistics();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to load tasks: " + e.getMessage());
        }
    }


    @FXML
    private void filterTasks(ActionEvent event) {
        applyFilters();
    }


    @FXML
    private void searchTasks() {
        applyFilters();
    }


    private void applyFilters() {
        String statusFilter = cmbStatusFilter.getValue();
        String searchText = txtSearch.getText().toLowerCase();

        filteredList.clear();

        for (TaskData task : taskList) {
            boolean matchesStatus = statusFilter.equals("All Tasks") || task.status.equals(statusFilter);
            boolean matchesSearch = searchText.isEmpty() ||
                                  task.taskName.toLowerCase().contains(searchText) ||
                                  task.description.toLowerCase().contains(searchText) ||
                                  task.projectName.toLowerCase().contains(searchText);

            if (matchesStatus && matchesSearch) {
                filteredList.add(task);
            }
        }

        tblTasks.setItems(filteredList);
    }


    private void updateStatistics() {
        int total = taskList.size();
        int pending = (int) taskList.stream().filter(t -> t.status.equals("Pending")).count();
        int inProgress = (int) taskList.stream().filter(t -> t.status.equals("In Progress")).count();
        int completed = (int) taskList.stream().filter(t -> t.status.equals("Completed")).count();

        lblTotalTasks.setText(String.valueOf(total));
        lblPendingTasks.setText(String.valueOf(pending));
        lblInProgressTasks.setText(String.valueOf(inProgress));
        lblCompletedTasks.setText(String.valueOf(completed));
    }


    private void viewTask(TaskData task) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Details");
        alert.setHeaderText(task.taskName);
        alert.setContentText(
            "Task ID: " + task.taskId + "\n" +
            "Description: " + task.description + "\n" +
            "Project: " + task.projectName + "\n" +
            "Priority: " + task.priority + "\n" +
            "Status: " + task.status + "\n" +
            "Due Date: " + task.dueDate
        );
        alert.showAndWait();
    }


    private void updateTaskStatus(TaskData task) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(task.status,
            "Pending", "In Progress", "Completed", "On Hold");
        dialog.setTitle("Update Task Status");
        dialog.setHeaderText("Update status for: " + task.taskName);
        dialog.setContentText("Select new status:");

        dialog.showAndWait().ifPresent(newStatus -> {
            try {
                Connection conn = DbConnection.getInstance().getConnection();
                String query = "UPDATE employee_tasks SET status = ? WHERE task_id = ?";

                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, newStatus);
                    pstmt.setInt(2, Integer.parseInt(task.taskId));

                    int rows = pstmt.executeUpdate();
                    if (rows > 0) {
                        showSuccess("Task status updated successfully!");
                        loadTasks();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                showError("Failed to update task status: " + e.getMessage());
            }
        });
    }


    @FXML
    private void addTask(ActionEvent event) {
        showInfo("Add Task functionality is under development");
    }


    @FXML
    private void refreshTasks(ActionEvent event) {
        loadTasks();
        showSuccess("Tasks refreshed successfully!");
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static class TaskData {
        private final String taskId;
        private final String taskName;
        private final String description;
        private final String projectName;
        private final String priority;
        private final String status;
        private final String dueDate;

        public TaskData(String taskId, String taskName, String description,
                       String projectName, String priority, String status, String dueDate) {
            this.taskId = taskId;
            this.taskName = taskName;
            this.description = description;
            this.projectName = projectName;
            this.priority = priority;
            this.status = status;
            this.dueDate = dueDate;
        }
    }
}
