package lk.ijse.foundation_360.entity;

public class ExpenseRecord {

    private String date;
    private String expenseType;
    private String description;
    private String paidTo;
    private String paymentMethod;
    private Double amount;
    private String approvedBy;


    private String expenseId;
    private String projectId;
    private String category;
    private String submittedBy;
    private String submissionDate;
    private String status;

    public ExpenseRecord() {
    }


    public ExpenseRecord(String date, String expenseType, String description, String paidTo,
                        String paymentMethod, Double amount, String approvedBy) {
        this.date = date;
        this.expenseType = expenseType;
        this.description = description;
        this.paidTo = paidTo;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.approvedBy = approvedBy;
    }


    public ExpenseRecord(String expenseId, String projectId, String category, String description,
                        Double amount, String submittedBy, String submissionDate, String status) {
        this.expenseId = expenseId;
        this.projectId = projectId;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.submittedBy = submittedBy;
        this.submissionDate = submissionDate;
        this.status = status;
    }


    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getExpenseType() { return expenseType; }
    public void setExpenseType(String expenseType) { this.expenseType = expenseType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPaidTo() { return paidTo; }
    public void setPaidTo(String paidTo) { this.paidTo = paidTo; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }


    public String getExpenseId() { return expenseId; }
    public void setExpenseId(String expenseId) { this.expenseId = expenseId; }

    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(String submittedBy) { this.submittedBy = submittedBy; }

    public String getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(String submissionDate) { this.submissionDate = submissionDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
