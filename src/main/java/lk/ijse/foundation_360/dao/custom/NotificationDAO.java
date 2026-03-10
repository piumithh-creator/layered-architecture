package lk.ijse.foundation_360.dao.custom;

import lk.ijse.foundation_360.dao.SuperDAO;
import lk.ijse.foundation_360.dto.NotificationDTO;
import java.sql.SQLException;
import java.util.List;

public interface NotificationDAO extends SuperDAO {
    List<NotificationDTO> getAll() throws SQLException;
    List<NotificationDTO> getForUser(String role, Integer userId) throws SQLException;
    int getUnreadCountForAdmin() throws SQLException;
    int getUnreadCountForUser(String role, Integer userId) throws SQLException;
    boolean markAsRead(int id) throws SQLException;
    boolean markAllAsRead(String role, Integer userId) throws SQLException;
    boolean create(String role, String message, Integer userId, String taskId) throws SQLException;
    boolean delete(int id) throws SQLException;
}
