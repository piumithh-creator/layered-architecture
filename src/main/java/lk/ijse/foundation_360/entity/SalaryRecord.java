package lk.ijse.foundation_360.entity;

public class SalaryRecord {
    private String salaryId;
    private String employeeId;

    private String employeeName;
    private String role;
    private Double basicSalary;
    private Integer otHours;
    private Double otAmount;
    private Double allowances;
    private Double deductions;
    private Double netSalary;
    private String paymentStatus;

    public SalaryRecord() {
    }

    public SalaryRecord(String salaryId, String employeeId, String employeeName, String role, Double basicSalary,
                       Integer otHours, Double otAmount, Double allowances, Double deductions,
                       Double netSalary, String paymentStatus) {
        this.salaryId = salaryId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.role = role;
        this.basicSalary = basicSalary;
        this.otHours = otHours;
        this.otAmount = otAmount;
        this.allowances = allowances;
        this.deductions = deductions;
        this.netSalary = netSalary;
        this.paymentStatus = paymentStatus;
    }


    public String getSalaryId() { return salaryId; }
    public void setSalaryId(String salaryId) { this.salaryId = salaryId; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(Double basicSalary) { this.basicSalary = basicSalary; }

    public Integer getOtHours() { return otHours; }
    public void setOtHours(Integer otHours) { this.otHours = otHours; }

    public Double getOtAmount() { return otAmount; }
    public void setOtAmount(Double otAmount) { this.otAmount = otAmount; }

    public Double getAllowances() { return allowances; }
    public void setAllowances(Double allowances) { this.allowances = allowances; }

    public Double getDeductions() { return deductions; }
    public void setDeductions(Double deductions) { this.deductions = deductions; }

    public Double getNetSalary() { return netSalary; }
    public void setNetSalary(Double netSalary) { this.netSalary = netSalary; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}
