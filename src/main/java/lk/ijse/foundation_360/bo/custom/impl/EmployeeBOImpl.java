package lk.ijse.foundation_360.bo.custom.impl;

import lk.ijse.foundation_360.bo.custom.EmployeeBO;
import lk.ijse.foundation_360.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.foundation_360.entity.Employee;
import java.sql.SQLException;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    private final EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();

    @Override public List<Employee> getAllActiveEmployees() throws SQLException { return employeeDAO.getAllActive(); }
    @Override public boolean addEmployee(String id, String name, String role, String status,
            Double salary, String username, String password) throws SQLException {
        return employeeDAO.add(id, name, role, status, salary, username, password);
    }
    @Override public boolean updateEmployee(String id, String name, String role, String status, Double salary) throws SQLException {
        return employeeDAO.update(id, name, role, status, salary);
    }
    @Override public boolean deactivateEmployee(String id) throws SQLException { return employeeDAO.deactivate(id); }
    @Override public String[] getEmployeeDetails(String id) throws SQLException { return employeeDAO.findCredentials(id); }
}
