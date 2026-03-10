package lk.ijse.foundation_360.bo.custom;

import lk.ijse.foundation_360.bo.SuperBO;
import lk.ijse.foundation_360.entity.Employee;
import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    List<Employee> getAllActiveEmployees() throws SQLException;
    boolean addEmployee(String id, String name, String role, String status, Double salary,
                        String username, String password) throws SQLException;
    boolean updateEmployee(String id, String name, String role, String status, Double salary) throws SQLException;
    boolean deactivateEmployee(String id) throws SQLException;
    String[] getEmployeeDetails(String id) throws SQLException;
}
