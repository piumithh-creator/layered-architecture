package lk.ijse.foundation_360.entity;
public class ProjectDetail {
    private int projectId, designId;
    private String projectName, clientId, startDate, endDate, status;
    private double estimatedCost, actualCost;
    public ProjectDetail() {}
    public ProjectDetail(int projectId, String projectName, String clientId, int designId,
                         String startDate, String endDate, double estimatedCost, double actualCost, String status) {
        this.projectId = projectId; this.projectName = projectName; this.clientId = clientId;
        this.designId = designId; this.startDate = startDate; this.endDate = endDate;
        this.estimatedCost = estimatedCost; this.actualCost = actualCost; this.status = status;
    }
    public int getProjectId() { return projectId; } public void setProjectId(int v) { projectId = v; }
    public String getProjectName() { return projectName; } public void setProjectName(String v) { projectName = v; }
    public String getClientId() { return clientId; } public void setClientId(String v) { clientId = v; }
    public int getDesignId() { return designId; } public void setDesignId(int v) { designId = v; }
    public String getStartDate() { return startDate; } public void setStartDate(String v) { startDate = v; }
    public String getEndDate() { return endDate; } public void setEndDate(String v) { endDate = v; }
    public double getEstimatedCost() { return estimatedCost; } public void setEstimatedCost(double v) { estimatedCost = v; }
    public double getActualCost() { return actualCost; } public void setActualCost(double v) { actualCost = v; }
    public String getStatus() { return status; } public void setStatus(String v) { status = v; }
}
