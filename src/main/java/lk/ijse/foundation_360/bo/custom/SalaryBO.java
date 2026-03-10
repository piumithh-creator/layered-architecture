package lk.ijse.foundation_360.bo.custom;

import lk.ijse.foundation_360.bo.SuperBO;
import lk.ijse.foundation_360.entity.SalaryRecord;
import java.sql.SQLException;
import java.util.List;

public interface SalaryBO extends SuperBO {
    List<SalaryRecord> getSalaryReport() throws SQLException;
}
