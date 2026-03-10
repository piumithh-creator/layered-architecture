package lk.ijse.foundation_360.dao.custom.impl;

import lk.ijse.foundation_360.dao.custom.NotificationDAO;
import lk.ijse.foundation_360.db.DbConnection;
import lk.ijse.foundation_360.dto.NotificationDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAOImpl implements NotificationDAO {

    private NotificationDTO map(ResultSet rs) throws SQLException {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(rs.getInt("id"));
        dto.setUserId(rs.getObject("user_id", Integer.class));
        dto.setUserRole(rs.getString("user_role"));
        dto.setMessage(rs.getString("message"));
        dto.setRead(rs.getBoolean("is_read"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) dto.setCreatedAt(ts.toLocalDateTime());
        dto.setRelatedTaskId(rs.getString("related_task_id"));
        return dto;
    }

    @Override
    public List<NotificationDTO> getAll() throws SQLException {
        List<NotificationDTO> list = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        ResultSet rs = con.createStatement().executeQuery(
            "SELECT * FROM notifications ORDER BY created_at DESC");
        while (rs.next()) list.add(map(rs));
        return list;
    }

    @Override
    public List<NotificationDTO> getForUser(String role, Integer userId) throws SQLException {
        List<NotificationDTO> list = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
            "SELECT * FROM notifications WHERE (user_role = ? OR user_id = ?) ORDER BY created_at DESC");
        pst.setString(1, role);
        pst.setObject(2, userId, Types.INTEGER);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) list.add(map(rs));
        return list;
    }

    @Override
    public int getUnreadCountForAdmin() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        ResultSet rs = con.createStatement().executeQuery(
            "SELECT COUNT(*) FROM notifications WHERE is_read = 0");
        if (rs.next()) return rs.getInt(1);
        return 0;
    }

    @Override
    public int getUnreadCountForUser(String role, Integer userId) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
            "SELECT COUNT(*) FROM notifications WHERE (user_role = ? OR user_id = ?) AND is_read = 0");
        pst.setString(1, role);
        pst.setObject(2, userId, Types.INTEGER);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) return rs.getInt(1);
        return 0;
    }

    @Override
    public boolean markAsRead(int id) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
            "UPDATE notifications SET is_read = 1 WHERE id = ?");
        pst.setInt(1, id);
        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean markAllAsRead(String role, Integer userId) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
            "UPDATE notifications SET is_read = 1 WHERE (user_role = ? OR user_id = ?) AND is_read = 0");
        pst.setString(1, role);
        pst.setObject(2, userId, Types.INTEGER);
        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean create(String role, String message, Integer userId, String taskId) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
            "INSERT INTO notifications (user_id, user_role, message, is_read, created_at, related_task_id) " +
            "VALUES (?, ?, ?, 0, NOW(), ?)");
        pst.setObject(1, userId, Types.INTEGER);
        pst.setString(2, role); pst.setString(3, message);
        pst.setObject(4, taskId, Types.VARCHAR);
        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("DELETE FROM notifications WHERE id = ?");
        pst.setInt(1, id);
        return pst.executeUpdate() > 0;
    }
}
