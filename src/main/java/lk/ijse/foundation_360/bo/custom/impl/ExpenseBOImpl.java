package lk.ijse.foundation_360.bo.custom.impl;

import lk.ijse.foundation_360.bo.custom.ExpenseBO;
import lk.ijse.foundation_360.dao.custom.impl.ExpenseDAOImpl;
import lk.ijse.foundation_360.entity.ExpenseRecord;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ExpenseBOImpl implements ExpenseBO {
    private final ExpenseDAOImpl expenseDAO = new ExpenseDAOImpl();

    @Override public List<ExpenseRecord> getExpensesForApproval() throws SQLException { return expenseDAO.getAllForApproval(); }
    @Override public List<ExpenseRecord> getExpensesForReport() throws SQLException { return expenseDAO.getAllForReport(); }
    @Override public boolean addExpense(int projectId, String categoryId, double amount, String reason, LocalDate date) throws SQLException {
        return expenseDAO.add(projectId, categoryId, amount, reason, date);
    }
    @Override public boolean updateExpenseStatus(int expenseId, String status, String remarks) throws SQLException {
        return expenseDAO.updateStatus(expenseId, status, remarks);
    }
    @Override public List<String> getProjectIds() throws SQLException { return expenseDAO.getProjectIds(); }
    @Override public List<String> getCategoryIds() throws SQLException { return expenseDAO.getCategoryIds(); }
}
