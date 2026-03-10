package lk.ijse.foundation_360.bo.custom;

import lk.ijse.foundation_360.bo.SuperBO;
import lk.ijse.foundation_360.entity.Order;
import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {
    List<Order> getAllOrders() throws SQLException;
}
