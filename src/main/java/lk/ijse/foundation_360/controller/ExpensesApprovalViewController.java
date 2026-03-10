package lk.ijse.foundation_360.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lk.ijse.foundation_360.bo.BOFactory;
import lk.ijse.foundation_360.bo.custom.ExpenseBO;
import lk.ijse.foundation_360.entity.ExpenseRecord;
import lk.ijse.foundation_360.util.SceneNavigator;
import java.net.URL;
import java.util.ResourceBundle;

public class ExpensesApprovalViewController implements Initializable {

    @FXML private ComboBox<String> cmbProjects, cmbStatus;
    @FXML private TableView<ExpenseRecord> tblExpenses;
    @FXML private TableColumn<ExpenseRecord, String> colExpenseId, colProjectId, colCategory, colDescription, colSubmittedBy, colSubmissionDate, colStatus;
    @FXML private TableColumn<ExpenseRecord, Double> colAmount;
    @FXML private TableColumn<ExpenseRecord, Void> colActions;
    @FXML private Label lblTotalPending, lblTotalAmount;

    private final ObservableList<ExpenseRecord> expenses = FXCollections.observableArrayList();
    private final ObservableList<ExpenseRecord> filteredExpenses = FXCollections.observableArrayList();
    private final ExpenseBO expenseBO = (ExpenseBO) BOFactory.getInstance().getBO(BOFactory.BOType.EXPENSE);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbProjects.getItems().addAll("All Projects", "PRJ-001", "PRJ-002");
        cmbProjects.getSelectionModel().selectFirst();
        cmbStatus.getItems().addAll("All Statuses", "PENDING", "APPROVED", "REJECTED");
        cmbStatus.getSelectionModel().selectFirst();
        colExpenseId.setCellValueFactory(new PropertyValueFactory<>("expenseId"));
        colProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colSubmittedBy.setCellValueFactory(new PropertyValueFactory<>("submittedBy"));
        colSubmissionDate.setCellValueFactory(new PropertyValueFactory<>("submissionDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblExpenses.setItems(filteredExpenses);
        loadData(); setupActionColumn();
    }

    private void loadData() {
        try {
            expenses.setAll(expenseBO.getExpensesForApproval());
            filteredExpenses.setAll(expenses);
        } catch (Exception e) {
            expenses.add(new ExpenseRecord("EXP-001", "PRJ-001", "Materials", "Cement", 50000.0, "Store Keeper", "2024-01-15", "PENDING"));
            filteredExpenses.setAll(expenses);
        }
        updateSummary();
    }

    private void setupActionColumn() {
        colActions.setCellFactory(p -> new TableCell<>() {
            private final Button approve = new Button("Approve");
            private final Button reject = new Button("Reject");
            private final HBox box = new HBox(5, approve, reject);
            {
                approve.setOnAction(e -> approveExpense(getTableView().getItems().get(getIndex())));
                reject.setOnAction(e -> rejectExpense(getTableView().getItems().get(getIndex())));
            }
            @Override protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                if (empty) { setGraphic(null); return; }
                ExpenseRecord ex = getTableView().getItems().get(getIndex());
                setGraphic("PENDING".equals(ex.getStatus()) ? box : null);
            }
        });
    }

    private void approveExpense(ExpenseRecord expense) {
        Alert c = new Alert(Alert.AlertType.CONFIRMATION, "Approve expense " + expense.getExpenseId() + "?");
        c.showAndWait().ifPresent(r -> { if (r == ButtonType.OK) { expense.setStatus("APPROVED"); tblExpenses.refresh(); updateSummary(); }});
    }

    private void rejectExpense(ExpenseRecord expense) {
        TextInputDialog d = new TextInputDialog(); d.setTitle("Reject"); d.setHeaderText("Reason:");
        d.showAndWait().ifPresent(reason -> { if (!reason.trim().isEmpty()) { expense.setStatus("REJECTED"); tblExpenses.refresh(); updateSummary(); }});
    }

    private void updateSummary() {
        long pending = filteredExpenses.stream().filter(e -> "PENDING".equals(e.getStatus())).count();
        double total = filteredExpenses.stream().filter(e -> "PENDING".equals(e.getStatus())).mapToDouble(ExpenseRecord::getAmount).sum();
        lblTotalPending.setText(String.valueOf(pending)); lblTotalAmount.setText(String.format("Rs. %.2f", total));
    }

    @FXML private void handleFilter(ActionEvent e) {
        String sp = cmbProjects.getValue(), ss = cmbStatus.getValue();
        filteredExpenses.setAll(expenses.stream().filter(x ->
            ("All Projects".equals(sp) || sp.equals(x.getProjectId())) &&
            ("All Statuses".equals(ss) || ss.equals(x.getStatus()))).toList());
        updateSummary();
    }

    @FXML private void handleClear(ActionEvent e) { cmbProjects.getSelectionModel().selectFirst(); cmbStatus.getSelectionModel().selectFirst(); filteredExpenses.setAll(expenses); updateSummary(); }

    @FXML private void back(ActionEvent e) {
        try { Stage s = (Stage) tblExpenses.getScene().getWindow(); SceneNavigator.navigateToScene(s, "/lk/ijse/foundation_360/admindashboard.fxml", "Admin Dashboard"); }
        catch (Exception ex) { ex.printStackTrace(); }
    }
}
