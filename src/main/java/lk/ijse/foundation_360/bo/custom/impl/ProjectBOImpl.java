package lk.ijse.foundation_360.bo.custom.impl;

import lk.ijse.foundation_360.bo.custom.ProjectBO;
import lk.ijse.foundation_360.dao.custom.impl.ProjectDAOImpl;
import lk.ijse.foundation_360.entity.Project;
import lk.ijse.foundation_360.entity.ProjectDetail;
import java.sql.SQLException;
import java.util.List;

public class ProjectBOImpl implements ProjectBO {
    private final ProjectDAOImpl projectDAO = new ProjectDAOImpl();

    @Override public List<ProjectDetail> getAllProjects() throws SQLException { return projectDAO.getAll(); }
    @Override public List<Project> getCompletedProjects() throws SQLException { return projectDAO.getCompleted(); }
}
