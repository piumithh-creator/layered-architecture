package lk.ijse.foundation_360.entity;
public class Approval {
    private String referenceType, referenceId, requestedBy, status, requestDate;
    public Approval() {}
    public Approval(String referenceType, String referenceId, String requestedBy, String status, String requestDate) {
        this.referenceType = referenceType; this.referenceId = referenceId;
        this.requestedBy = requestedBy; this.status = status; this.requestDate = requestDate;
    }
    public String getReferenceType() { return referenceType; } public void setReferenceType(String v) { referenceType = v; }
    public String getReferenceId() { return referenceId; } public void setReferenceId(String v) { referenceId = v; }
    public String getRequestedBy() { return requestedBy; } public void setRequestedBy(String v) { requestedBy = v; }
    public String getStatus() { return status; } public void setStatus(String v) { status = v; }
    public String getRequestDate() { return requestDate; } public void setRequestDate(String v) { requestDate = v; }
}
