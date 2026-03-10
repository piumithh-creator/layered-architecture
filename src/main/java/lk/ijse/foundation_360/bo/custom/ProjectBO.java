package lk.ijse.foundation_360.bo.custom;

import lk.ijse.foundation_360.bo.SuperBO;
import lk.ijse.foundation_360.entity.Project;
import lk.ijse.foundation_360.entity.ProjectDetail;
import java.sql.SQLException;
import java.util.List;

public interface ProjectBO extends SuperBO {
    List<ProjectDetail> getAllProjects() throws SQLException;
    List<Project> getCompletedProjects() throws SQLException;
}
