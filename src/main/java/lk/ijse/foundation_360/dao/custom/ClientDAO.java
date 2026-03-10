package lk.ijse.foundation_360.dao.custom;

import lk.ijse.foundation_360.dao.SuperDAO;
import lk.ijse.foundation_360.entity.Client;
import java.sql.SQLException;
import java.util.List;

public interface ClientDAO extends SuperDAO {
    List<Client> getAll() throws SQLException;
    boolean add(Client client) throws SQLException;
    boolean update(Client client) throws SQLException;
    boolean delete(String clientId) throws SQLException;
    Client findById(String id) throws SQLException;
}
