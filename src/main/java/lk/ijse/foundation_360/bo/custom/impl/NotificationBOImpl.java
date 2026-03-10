package lk.ijse.foundation_360.bo.custom.impl;

import lk.ijse.foundation_360.bo.custom.NotificationBO;
import lk.ijse.foundation_360.dao.custom.impl.NotificationDAOImpl;
import lk.ijse.foundation_360.dto.NotificationDTO;
import java.sql.SQLException;
import java.util.List;

public class NotificationBOImpl implements NotificationBO {
    private final NotificationDAOImpl notificationDAO = new NotificationDAOImpl();

    @Override public List<NotificationDTO> getAllNotifications() throws SQLException { return notificationDAO.getAll(); }
    @Override public List<NotificationDTO> getNotificationsForUser(String role, Integer userId) throws SQLException {
        return notificationDAO.getForUser(role, userId);
    }
    @Override public int getUnreadCountForAdmin() throws SQLException { return notificationDAO.getUnreadCountForAdmin(); }
    @Override public int getUnreadCountForUser(String role, Integer userId) throws SQLException {
        return notificationDAO.getUnreadCountForUser(role, userId);
    }
    @Override public boolean markAsRead(int id) throws SQLException { return notificationDAO.markAsRead(id); }
    @Override public boolean markAllAsRead(String role, Integer userId) throws SQLException {
        return notificationDAO.markAllAsRead(role, userId);
    }
    @Override public boolean createNotification(String role, String message, Integer userId, String taskId) throws SQLException {
        return notificationDAO.create(role, message, userId, taskId);
    }
}
