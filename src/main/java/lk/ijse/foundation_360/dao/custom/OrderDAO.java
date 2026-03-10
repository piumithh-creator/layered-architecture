package lk.ijse.foundation_360.dao.custom;

import lk.ijse.foundation_360.dao.SuperDAO;
import lk.ijse.foundation_360.entity.Order;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends SuperDAO {
    List<Order> getAll() throws SQLException;
}
