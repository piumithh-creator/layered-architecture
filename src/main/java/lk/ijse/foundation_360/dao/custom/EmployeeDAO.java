package lk.ijse.foundation_360.dao.custom;

import lk.ijse.foundation_360.dao.SuperDAO;
import lk.ijse.foundation_360.entity.Employee;
import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO extends SuperDAO {
    List<Employee> getAllActive() throws SQLException;
    boolean add(String id, String name, String role, String status, Double salary,
                String username, String password) throws SQLException;
    boolean update(String id, String name, String role, String status, Double salary) throws SQLException;
    boolean deactivate(String id) throws SQLException;
    Employee findById(String id) throws SQLException;
    String[] findCredentials(String id) throws SQLException;
}
