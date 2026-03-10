package lk.ijse.foundation_360.dao.custom.impl;

import lk.ijse.foundation_360.dao.custom.EmployeeDAO;
import lk.ijse.foundation_360.db.DbConnection;
import lk.ijse.foundation_360.entity.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public List<Employee> getAllActive() throws SQLException {
        List<Employee> list = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        ResultSet rs = con.createStatement().executeQuery(
                "SELECT * FROM employee WHERE status='ACTIVE'");
        while (rs.next()) {
            list.add(new Employee(rs.getString("employee_id"), rs.getString("name"),
                    rs.getString("role"), rs.getString("status")));
        }
        return list;
    }

    @Override
    public boolean add(String id, String name, String role, String status, Double salary,
                       String username, String password) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        con.setAutoCommit(false);
        try {
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO employee (employee_id, name, role, status, salary) VALUES (?, ?, ?, ?, ?)");
            pst.setString(1, id); pst.setString(2, name); pst.setString(3, role);
            pst.setString(4, status);
            if (salary != null) pst.setDouble(5, salary); else pst.setNull(5, Types.DECIMAL);
            int rows = pst.executeUpdate();
            if (rows > 0) {
                PreparedStatement upst = con.prepareStatement(
                        "INSERT INTO users (username, password, role, employee_id) VALUES (?, ?, ?, ?)");
                upst.setString(1, username); upst.setString(2, password);
                upst.setString(3, role); upst.setString(4, id);
                upst.executeUpdate();
            }
            con.commit();
            return rows > 0;
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
        }
    }

    @Override
    public boolean update(String id, String name, String role, String status, Double salary) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        StringBuilder sql = new StringBuilder("UPDATE employee SET ");
        List<Object> params = new ArrayList<>();
        if (name != null && !name.isEmpty()) { sql.append("name=?, "); params.add(name); }
        if (role != null) { sql.append("role=?, "); params.add(role); }
        if (status != null && !status.isEmpty()) { sql.append("status=?, "); params.add(status); }
        if (salary != null) { sql.append("salary=?, "); params.add(salary); }
        if (params.isEmpty()) return false;
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE employee_id=?");
        params.add(id);
        PreparedStatement pst = con.prepareStatement(sql.toString());
        for (int i = 0; i < params.size(); i++) {
            Object p = params.get(i);
            if (p instanceof String) pst.setString(i + 1, (String) p);
            else if (p instanceof Double) pst.setDouble(i + 1, (Double) p);
        }
        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean deactivate(String id) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
                "UPDATE employee SET status='INACTIVE' WHERE employee_id=?");
        pst.setString(1, id);
        return pst.executeUpdate() > 0;
    }

    @Override
    public Employee findById(String id) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM employee WHERE employee_id=?");
        pst.setString(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return new Employee(rs.getString("employee_id"), rs.getString("name"),
                    rs.getString("role"), rs.getString("status"));
        }
        return null;
    }

    @Override
    public String[] findCredentials(String id) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(
                "SELECT e.*, u.username, u.password FROM employee e " +
                "LEFT JOIN users u ON e.employee_id = u.employee_id WHERE e.employee_id=?");
        pst.setString(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return new String[]{
                rs.getString("name"), rs.getString("role"), rs.getString("status"),
                rs.wasNull() ? "" : String.valueOf(rs.getDouble("salary")),
                rs.getString("username") != null ? rs.getString("username") : "",
                rs.getString("password") != null ? rs.getString("password") : ""
            };
        }
        return null;
    }
}
