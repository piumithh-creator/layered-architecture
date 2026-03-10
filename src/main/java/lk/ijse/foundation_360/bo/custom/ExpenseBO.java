package lk.ijse.foundation_360.bo.custom;

import lk.ijse.foundation_360.bo.SuperBO;
import lk.ijse.foundation_360.entity.ExpenseRecord;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseBO extends SuperBO {
    List<ExpenseRecord> getExpensesForApproval() throws SQLException;
    List<ExpenseRecord> getExpensesForReport() throws SQLException;
    boolean addExpense(int projectId, String categoryId, double amount, String reason, LocalDate date) throws SQLException;
    boolean updateExpenseStatus(int expenseId, String status, String remarks) throws SQLException;
    List<String> getProjectIds() throws SQLException;
    List<String> getCategoryIds() throws SQLException;
}
