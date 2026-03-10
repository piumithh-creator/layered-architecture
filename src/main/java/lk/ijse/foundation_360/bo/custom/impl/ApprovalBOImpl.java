package lk.ijse.foundation_360.bo.custom.impl;

import lk.ijse.foundation_360.bo.custom.ApprovalBO;
import lk.ijse.foundation_360.dao.custom.impl.ApprovalDAOImpl;
import lk.ijse.foundation_360.entity.Approval;
import java.sql.SQLException;
import java.util.List;

public class ApprovalBOImpl implements ApprovalBO {
    private final ApprovalDAOImpl approvalDAO = new ApprovalDAOImpl();
    @Override public List<Approval> getPendingApprovals() throws SQLException { return approvalDAO.getPending(); }
    @Override public boolean updateApprovalStatus(String referenceId, String referenceType, String status, String remarks) throws SQLException {
        return approvalDAO.updateStatus(referenceId, referenceType, status, remarks);
    }
}
