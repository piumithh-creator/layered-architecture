package lk.ijse.foundation_360.bo.custom.impl;

import lk.ijse.foundation_360.bo.custom.ClientBO;
import lk.ijse.foundation_360.dao.custom.impl.ClientDAOImpl;
import lk.ijse.foundation_360.entity.Client;
import java.sql.SQLException;
import java.util.List;

public class ClientBOImpl implements ClientBO {
    private final ClientDAOImpl clientDAO = new ClientDAOImpl();

    @Override public List<Client> getAllClients() throws SQLException { return clientDAO.getAll(); }
    @Override public boolean addClient(Client client) throws SQLException { return clientDAO.add(client); }
    @Override public boolean updateClient(Client client) throws SQLException { return clientDAO.update(client); }
    @Override public boolean deleteClient(String clientId) throws SQLException { return clientDAO.delete(clientId); }
    @Override public Client findClientById(String id) throws SQLException { return clientDAO.findById(id); }
}
