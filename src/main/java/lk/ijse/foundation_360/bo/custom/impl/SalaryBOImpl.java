package lk.ijse.foundation_360.bo.custom.impl;

import lk.ijse.foundation_360.bo.custom.SalaryBO;
import lk.ijse.foundation_360.dao.custom.impl.SalaryDAOImpl;
import lk.ijse.foundation_360.entity.SalaryRecord;
import java.sql.SQLException;
import java.util.List;

public class SalaryBOImpl implements SalaryBO {
    private final SalaryDAOImpl salaryDAO = new SalaryDAOImpl();
    @Override public List<SalaryRecord> getSalaryReport() throws SQLException { return salaryDAO.getActiveSalaries(); }
}
