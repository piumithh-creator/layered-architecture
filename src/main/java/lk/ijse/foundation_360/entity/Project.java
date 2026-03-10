package lk.ijse.foundation_360.entity;
public class Project {
    private String projectId, projectName, clientName, completionDate;
    private Double totalCost, revenue, profitLoss;
    public Project() {}
    public Project(String projectId, String projectName, String clientName,
                   String completionDate, Double totalCost, Double revenue) {
        this.projectId = projectId; this.projectName = projectName; this.clientName = clientName;
        this.completionDate = completionDate; this.totalCost = totalCost; this.revenue = revenue;
        this.profitLoss = revenue - totalCost;
    }
    public String getProjectId() { return projectId; } public void setProjectId(String v) { projectId = v; }
    public String getProjectName() { return projectName; } public void setProjectName(String v) { projectName = v; }
    public String getClientName() { return clientName; } public void setClientName(String v) { clientName = v; }
    public String getCompletionDate() { return completionDate; } public void setCompletionDate(String v) { completionDate = v; }
    public Double getTotalCost() { return totalCost; } public void setTotalCost(Double v) { totalCost = v; }
    public Double getRevenue() { return revenue; } public void setRevenue(Double v) { revenue = v; }
    public Double getProfitLoss() { return profitLoss; } public void setProfitLoss(Double v) { profitLoss = v; }
}
