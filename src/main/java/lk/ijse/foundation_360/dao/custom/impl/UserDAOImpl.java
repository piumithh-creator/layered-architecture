package lk.ijse.foundation_360.dao.custom.impl;

import lk.ijse.foundation_360.dao.custom.UserDAO;
import lk.ijse.foundation_360.db.DbConnection;
import java.sql.*;
import java.time.LocalDate;

public class UserDAOImpl implements UserDAO {

    @Override
    public String login(String username, String password) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
            "SELECT role FROM users WHERE username=? AND password=?");
        pst.setString(1, username); pst.setString(2, password);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) return rs.getString("role");
        return null;
    }

    @Override
    public Integer getUserId(String username, String password) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
            "SELECT user_id FROM users WHERE username=? AND password=?");
        pst.setString(1, username); pst.setString(2, password);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("user_id");
            return rs.wasNull() ? null : id;
        }
        return null;
    }

    @Override
    public boolean register(String username, String password, String role,
                            String email, String contact, LocalDate createDate) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
            "INSERT INTO users (username, password, role, email, contact, create_date) VALUES (?, ?, ?, ?, ?, ?)");
        pst.setString(1, username); pst.setString(2, password); pst.setString(3, role);
        pst.setString(4, email); pst.setString(5, contact);
        pst.setDate(6, Date.valueOf(createDate));
        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean isUsernameExists(String username) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT username FROM users WHERE username=?");
        pst.setString(1, username);
        return pst.executeQuery().next();
    }
}
