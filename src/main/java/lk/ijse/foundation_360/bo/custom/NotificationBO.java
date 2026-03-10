package lk.ijse.foundation_360.bo.custom;

import lk.ijse.foundation_360.bo.SuperBO;
import lk.ijse.foundation_360.dto.NotificationDTO;
import java.sql.SQLException;
import java.util.List;

public interface NotificationBO extends SuperBO {
    List<NotificationDTO> getAllNotifications() throws SQLException;
    List<NotificationDTO> getNotificationsForUser(String role, Integer userId) throws SQLException;
    int getUnreadCountForAdmin() throws SQLException;
    int getUnreadCountForUser(String role, Integer userId) throws SQLException;
    boolean markAsRead(int id) throws SQLException;
    boolean markAllAsRead(String role, Integer userId) throws SQLException;
    boolean createNotification(String role, String message, Integer userId, String taskId) throws SQLException;
}
