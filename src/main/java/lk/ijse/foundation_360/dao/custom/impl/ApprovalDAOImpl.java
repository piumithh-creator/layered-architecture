package lk.ijse.foundation_360.dao.custom.impl;

import lk.ijse.foundation_360.dao.custom.ApprovalDAO;
import lk.ijse.foundation_360.db.DbConnection;
import lk.ijse.foundation_360.entity.Approval;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApprovalDAOImpl implements ApprovalDAO {
    @Override
    public List<Approval> getPending() throws SQLException {
        List<Approval> list = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
            "SELECT reference_type, reference_id, requested_by, status, request_date " +
            "FROM approval_requests WHERE status = 'PENDING'");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            list.add(new Approval(rs.getString("reference_type"), rs.getString("reference_id"),
                rs.getString("requested_by"), rs.getString("status"), rs.getString("request_date")));
        }
        return list;
    }

    @Override
    public boolean updateStatus(String referenceId, String referenceType, String status, String remarks) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
            "UPDATE approval_requests SET status = ?, remarks = ? WHERE reference_id = ? AND reference_type = ?");
        pst.setString(1, status); pst.setString(2, remarks);
        pst.setString(3, referenceId); pst.setString(4, referenceType);
        return pst.executeUpdate() > 0;
    }
}
