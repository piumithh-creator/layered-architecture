package lk.ijse.foundation_360.dao.custom.impl;

import lk.ijse.foundation_360.dao.custom.OrderDAO;
import lk.ijse.foundation_360.db.DbConnection;
import lk.ijse.foundation_360.entity.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public List<Order> getAll() throws SQLException {
        List<Order> list = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM orders");
        while (rs.next()) {
            list.add(new Order(rs.getInt("order_id"), rs.getString("client_id"),
                rs.getDate("order_date") != null ? rs.getDate("order_date").toString() : "N/A",
                rs.getString("status") != null ? rs.getString("status") : "PENDING"));
        }
        return list;
    }
}
