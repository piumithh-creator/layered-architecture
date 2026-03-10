package lk.ijse.foundation_360.dao.custom.impl;

import lk.ijse.foundation_360.dao.custom.ClientDAO;
import lk.ijse.foundation_360.db.DbConnection;
import lk.ijse.foundation_360.entity.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {

    @Override
    public List<Client> getAll() throws SQLException {
        List<Client> list = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM client");
        while (rs.next()) {
            list.add(new Client(rs.getString("client_id"), rs.getString("name"),
                    rs.getString("contact"), rs.getString("email"), "Active"));
        }
        return list;
    }

    @Override
    public boolean add(Client c) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
                "INSERT INTO client(client_id, name, contact, email) VALUES (?, ?, ?, ?)");
        pst.setString(1, c.getClientId()); pst.setString(2, c.getName());
        pst.setString(3, c.getContact()); pst.setString(4, c.getEmail());
        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean update(Client c) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
                "UPDATE client SET name=?, contact=?, email=? WHERE client_id=?");
        pst.setString(1, c.getName()); pst.setString(2, c.getContact());
        pst.setString(3, c.getEmail()); pst.setString(4, c.getClientId());
        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean delete(String clientId) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("DELETE FROM client WHERE client_id=?");
        pst.setString(1, clientId);
        return pst.executeUpdate() > 0;
    }

    @Override
    public Client findById(String id) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM client WHERE client_id=?");
        pst.setString(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return new Client(rs.getString("client_id"), rs.getString("name"),
                    rs.getString("contact"), rs.getString("email"), "Active");
        }
        return null;
    }
}
