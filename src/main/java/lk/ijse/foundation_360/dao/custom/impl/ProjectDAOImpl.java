package lk.ijse.foundation_360.dao.custom.impl;

import lk.ijse.foundation_360.dao.custom.ProjectDAO;
import lk.ijse.foundation_360.db.DbConnection;
import lk.ijse.foundation_360.entity.Project;
import lk.ijse.foundation_360.entity.ProjectDetail;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAOImpl implements ProjectDAO {

    @Override
    public List<ProjectDetail> getAll() throws SQLException {
        List<ProjectDetail> list = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM project");
        while (rs.next()) {
            list.add(new ProjectDetail(
                rs.getInt("project_id"), rs.getString("project_name"),
                rs.getString("client_id"), rs.getInt("design_id"),
                rs.getDate("start_date") != null ? rs.getDate("start_date").toString() : "N/A",
                rs.getDate("end_date") != null ? rs.getDate("end_date").toString() : "N/A",
                rs.getDouble("estimated_cost"), rs.getDouble("actual_cost"),
                rs.getString("status") != null ? rs.getString("status") : "PLANNING"
            ));
        }
        return list;
    }

    @Override
    public List<Project> getCompleted() throws SQLException {
        List<Project> list = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT project_id, project_name, client_id, end_date, actual_cost, estimated_cost " +
                     "FROM project WHERE status='COMPLETED' UNION " +
                     "SELECT project_id, project_name, client_id, end_date, actual_cost, estimated_cost " +
                     "FROM previous_projects";
        ResultSet rs = con.createStatement().executeQuery(sql);
        while (rs.next()) {
            String id = "PRJ-" + String.format("%03d", rs.getInt("project_id"));
            list.add(new Project(id, rs.getString("project_name"), rs.getString("client_id"),
                rs.getDate("end_date") != null ? rs.getDate("end_date").toString() : "N/A",
                rs.getDouble("actual_cost"), rs.getDouble("estimated_cost")));
        }
        return list;
    }
}
