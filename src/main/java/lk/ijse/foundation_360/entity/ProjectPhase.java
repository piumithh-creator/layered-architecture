package lk.ijse.foundation_360.entity;

public class ProjectPhase {
    private String phaseName;
    private String startDate;
    private String endDate;
    private Integer duration;
    private Double costPerPhase;
    private String completionStatus;

    public ProjectPhase() {
    }

    public ProjectPhase(String phaseName, String startDate, String endDate,
                       Integer duration, Double costPerPhase, String completionStatus) {
        this.phaseName = phaseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.costPerPhase = costPerPhase;
        this.completionStatus = completionStatus;
    }


    public String getPhaseName() { return phaseName; }
    public void setPhaseName(String phaseName) { this.phaseName = phaseName; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public Double getCostPerPhase() { return costPerPhase; }
    public void setCostPerPhase(Double costPerPhase) { this.costPerPhase = costPerPhase; }

    public String getCompletionStatus() { return completionStatus; }
    public void setCompletionStatus(String completionStatus) { this.completionStatus = completionStatus; }
}
