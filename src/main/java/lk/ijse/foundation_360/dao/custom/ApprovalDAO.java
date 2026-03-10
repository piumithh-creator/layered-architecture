package lk.ijse.foundation_360.dao.custom;

import lk.ijse.foundation_360.dao.SuperDAO;
import lk.ijse.foundation_360.entity.Approval;
import java.sql.SQLException;
import java.util.List;

public interface ApprovalDAO extends SuperDAO {
    List<Approval> getPending() throws SQLException;
    boolean updateStatus(String referenceId, String referenceType, String status, String remarks) throws SQLException;
}
