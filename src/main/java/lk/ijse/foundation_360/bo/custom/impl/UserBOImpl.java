package lk.ijse.foundation_360.bo.custom.impl;

import lk.ijse.foundation_360.bo.custom.UserBO;
import lk.ijse.foundation_360.dao.custom.impl.UserDAOImpl;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserBOImpl implements UserBO {
    private final UserDAOImpl userDAO = new UserDAOImpl();

    @Override public String login(String username, String password) throws SQLException { return userDAO.login(username, password); }
    @Override public Integer getUserId(String username, String password) throws SQLException { return userDAO.getUserId(username, password); }
    @Override public boolean register(String username, String password, String role,
            String email, String contact, LocalDate createDate) throws SQLException {
        return userDAO.register(username, password, role, email, contact, createDate);
    }
    @Override public boolean isUsernameExists(String username) throws SQLException { return userDAO.isUsernameExists(username); }
}
