package lk.ijse.foundation_360.bo.custom.impl;

import lk.ijse.foundation_360.bo.custom.OrderBO;
import lk.ijse.foundation_360.dao.custom.impl.OrderDAOImpl;
import lk.ijse.foundation_360.entity.Order;
import java.sql.SQLException;
import java.util.List;

public class OrderBOImpl implements OrderBO {
    private final OrderDAOImpl orderDAO = new OrderDAOImpl();
    @Override public List<Order> getAllOrders() throws SQLException { return orderDAO.getAll(); }
}
