package lk.ijse.foundation_360.dao.custom.impl;

import lk.ijse.foundation_360.dao.custom.SalaryDAO;
import lk.ijse.foundation_360.db.DbConnection;
import lk.ijse.foundation_360.entity.SalaryRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAOImpl implements SalaryDAO {
    @Override
    public List<SalaryRecord> getActiveSalaries() throws SQLException {
        List<SalaryRecord> list = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        ResultSet rs = con.createStatement().executeQuery(
            "SELECT employee_id, name, role, salary FROM employee WHERE status='ACTIVE'");
        int count = 0;
        while (rs.next()) {
            double basic = rs.getDouble("salary");
            double allowances = basic * 0.10;
            double deductions = basic * 0.05;
            double net = basic + allowances - deductions;
            list.add(new SalaryRecord("SAL" + String.format("%03d", ++count),
                rs.getString("employee_id"), rs.getString("name"), rs.getString("role"),
                basic, 0, 0.0, allowances, deductions, net, "Pending"));
        }
        return list;
    }
}
