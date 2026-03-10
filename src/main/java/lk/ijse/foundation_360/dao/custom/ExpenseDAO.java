package lk.ijse.foundation_360.dao.custom;

import lk.ijse.foundation_360.dao.SuperDAO;
import lk.ijse.foundation_360.entity.ExpenseRecord;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseDAO extends SuperDAO {
    List<ExpenseRecord> getAllForApproval() throws SQLException;
    List<ExpenseRecord> getAllForReport() throws SQLException;
    boolean add(int projectId, String categoryId, double amount, String reason, LocalDate expenseDate) throws SQLException;
    boolean updateStatus(int expenseId, String status, String remarks) throws SQLException;
    List<String> getProjectIds() throws SQLException;
    List<String> getCategoryIds() throws SQLException;
}
