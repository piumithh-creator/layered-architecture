package lk.ijse.foundation_360.bo.custom;

import lk.ijse.foundation_360.bo.SuperBO;
import lk.ijse.foundation_360.entity.Approval;
import java.sql.SQLException;
import java.util.List;

public interface ApprovalBO extends SuperBO {
    List<Approval> getPendingApprovals() throws SQLException;
    boolean updateApprovalStatus(String referenceId, String referenceType, String status, String remarks) throws SQLException;
}
