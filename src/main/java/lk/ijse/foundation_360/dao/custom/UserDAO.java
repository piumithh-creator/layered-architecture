package lk.ijse.foundation_360.dao.custom;

import lk.ijse.foundation_360.dao.SuperDAO;
import java.sql.SQLException;
import java.time.LocalDate;

public interface UserDAO extends SuperDAO {
    String login(String username, String password) throws SQLException;
    Integer getUserId(String username, String password) throws SQLException;
    boolean register(String username, String password, String role,
                     String email, String contact, LocalDate createDate) throws SQLException;
    boolean isUsernameExists(String username) throws SQLException;
}
