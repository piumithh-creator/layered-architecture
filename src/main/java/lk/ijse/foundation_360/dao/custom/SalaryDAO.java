package lk.ijse.foundation_360.dao.custom;

import lk.ijse.foundation_360.dao.SuperDAO;
import lk.ijse.foundation_360.entity.SalaryRecord;
import java.sql.SQLException;
import java.util.List;

public interface SalaryDAO extends SuperDAO {
    List<SalaryRecord> getActiveSalaries() throws SQLException;
}
