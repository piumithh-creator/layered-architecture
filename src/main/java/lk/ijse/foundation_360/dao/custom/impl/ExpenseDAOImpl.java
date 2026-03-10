package lk.ijse.foundation_360.dao.custom.impl;

import lk.ijse.foundation_360.dao.custom.ExpenseDAO;
import lk.ijse.foundation_360.db.DbConnection;
import lk.ijse.foundation_360.entity.ExpenseRecord;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAOImpl implements ExpenseDAO {

    @Override
    public List<ExpenseRecord> getAllForApproval() throws SQLException {
        List<ExpenseRecord> list = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT e.expense_id, e.project_id, e.category_id, e.reason as description, " +
                     "e.amount, e.requested_by, e.request_date, e.status FROM expense e";
        ResultSet rs = con.createStatement().executeQuery(sql);
        while (rs.next()) {
            String expId = "EXP-" + String.format("%03d", rs.getInt("expense_id"));
            list.add(new ExpenseRecord(expId,
                rs.getString("project_id") != null ? rs.getString("project_id") : "N/A",
                rs.getString("category_id") != null ? rs.getString("category_id") : "Other",
                rs.getString("description"), rs.getDouble("amount"),
                rs.getString("requested_by"),
                rs.getDate("request_date") != null ? rs.getDate("request_date").toString() : "N/A",
                rs.getString("status") != null ? rs.getString("status") : "PENDING"));
        }
        return list;
    }

    @Override
    public List<ExpenseRecord> getAllForReport() throws SQLException {
        List<ExpenseRecord> list = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT e.expense_id, e.expense_date, e.category_id, e.reason as description, " +
                     "e.requested_by as paid_to, e.amount, e.status FROM expense e";
        ResultSet rs = con.createStatement().executeQuery(sql);
        while (rs.next()) {
            list.add(new ExpenseRecord(
                rs.getDate("expense_date") != null ? rs.getDate("expense_date").toString() : "N/A",
                rs.getString("category_id") != null ? rs.getString("category_id") : "Other",
                rs.getString("description"), rs.getString("paid_to"),
                "Bank Transfer", rs.getDouble("amount"), rs.getString("status")));
        }
        return list;
    }

    @Override
    public boolean add(int projectId, String categoryId, double amount, String reason, LocalDate expenseDate) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
            "INSERT INTO expense (project_id, category_id, amount, reason, expense_date, request_date, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, 'PENDING')");
        pst.setInt(1, projectId); pst.setString(2, categoryId); pst.setDouble(3, amount);
        pst.setString(4, reason); pst.setDate(5, Date.valueOf(expenseDate));
        pst.setDate(6, Date.valueOf(LocalDate.now()));
        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean updateStatus(int expenseId, String status, String remarks) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
            "UPDATE expense SET status=?, remarks=? WHERE expense_id=?");
        pst.setString(1, status); pst.setString(2, remarks); pst.setInt(3, expenseId);
        return pst.executeUpdate() > 0;
    }

    @Override
    public List<String> getProjectIds() throws SQLException {
        List<String> list = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        ResultSet rs = con.createStatement().executeQuery("SELECT project_id FROM project");
        while (rs.next()) list.add(String.valueOf(rs.getInt("project_id")));
        return list;
    }

    @Override
    public List<String> getCategoryIds() throws SQLException {
        List<String> list = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        ResultSet rs = con.createStatement().executeQuery("SELECT category_id FROM expense_category");
        while (rs.next()) list.add(rs.getString("category_id"));
        return list;
    }
}
