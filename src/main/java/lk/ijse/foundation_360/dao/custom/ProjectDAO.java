package lk.ijse.foundation_360.dao.custom;

import lk.ijse.foundation_360.dao.SuperDAO;
import lk.ijse.foundation_360.entity.Project;
import lk.ijse.foundation_360.entity.ProjectDetail;
import java.sql.SQLException;
import java.util.List;

public interface ProjectDAO extends SuperDAO {
    List<ProjectDetail> getAll() throws SQLException;
    List<Project> getCompleted() throws SQLException;
}
