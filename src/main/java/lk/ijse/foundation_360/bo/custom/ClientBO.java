package lk.ijse.foundation_360.bo.custom;

import lk.ijse.foundation_360.bo.SuperBO;
import lk.ijse.foundation_360.entity.Client;
import java.sql.SQLException;
import java.util.List;

public interface ClientBO extends SuperBO {
    List<Client> getAllClients() throws SQLException;
    boolean addClient(Client client) throws SQLException;
    boolean updateClient(Client client) throws SQLException;
    boolean deleteClient(String clientId) throws SQLException;
    Client findClientById(String id) throws SQLException;
}
