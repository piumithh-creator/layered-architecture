package lk.ijse.foundation_360.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.foundation_360.bo.BOFactory;
import lk.ijse.foundation_360.bo.custom.ExpenseBO;
import lk.ijse.foundation_360.entity.ExpenseRecord;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ExpenseController implements Initializable {

    @FXML private ComboBox<String> cmbProjectId, cmbCategoryId;
    @FXML private TextField txtAmount, txtReason;
    @FXML private DatePicker dpExpenseDate;
    @FXML private TableView<ExpenseRecord> tblExpenses;
    @FXML private TableColumn<ExpenseRecord, String> colProjectId, colCategoryId, colStatus, colRequestDate;
    @FXML private TableColumn<ExpenseRecord, Double> colAmount;
    @FXML private TableColumn<ExpenseRecord, Integer> colExpenseId;

    private final ObservableList<ExpenseRecord> expenseList = FXCollections.observableArrayList();
    private final ExpenseBO expenseBO = (ExpenseBO) BOFactory.getInstance().getBO(BOFactory.BOType.EXPENSE);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("category"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRequestDate.setCellValueFactory(new PropertyValueFactory<>("submissionDate"));
        tblExpenses.setItems(expenseList);
        loadComboBoxes(); loadExpenses();
    }

    private void loadComboBoxes() {
        try {
            cmbProjectId.setItems(FXCollections.observableArrayList(expenseBO.getProjectIds()));
            cmbCategoryId.setItems(FXCollections.observableArrayList(expenseBO.getCategoryIds()));
        } catch (Exception e) { new Alert(Alert.AlertType.ERROR, "Error loading data: " + e.getMessage()).show(); }
    }

    private void loadExpenses() {
        try {
            expenseList.setAll(expenseBO.getExpensesForApproval());
        } catch (Exception e) {  }
    }

    @FXML
    void btnAddExpenseOnAction(ActionEvent event) {
        String projectId = cmbProjectId.getValue(), categoryId = cmbCategoryId.getValue();
        String amountText = txtAmount.getText(); LocalDate date = dpExpenseDate.getValue();
        if (projectId == null || categoryId == null || amountText.isEmpty() || date == null) {
            new Alert(Alert.AlertType.WARNING, "Please fill all required fields").show(); return;
        }
        try {
            double amount = Double.parseDouble(amountText);
            if (expenseBO.addExpense(Integer.parseInt(projectId), categoryId, amount, txtReason.getText(), date)) {
                new Alert(Alert.AlertType.INFORMATION, "Expense Added!").show();
                loadExpenses(); clearFields();
            }
        } catch (Exception e) { new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show(); }
    }

    @FXML void btnClearOnAction(ActionEvent event) { clearFields(); }

    private void clearFields() {
        cmbProjectId.getSelectionModel().clearSelection(); cmbCategoryId.getSelectionModel().clearSelection();
        txtAmount.clear(); txtReason.clear(); dpExpenseDate.setValue(null);
    }
}
