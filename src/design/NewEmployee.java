package design;

public abstract class NewEmployee implements Employee {
    private int employeeId;
    private String employeeName;
    private String deptName;

    public NewEmployee(){

    }
    public NewEmployee(int employeeId){
        this.employeeId = employeeId;
    }
    public NewEmployee(int employeeId, String employeeName){
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }
    public NewEmployee(int employeeId, String employeeName, String deptName){
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.deptName = deptName;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getAssignDept() {
        return deptName;
    }

    public void setAssignDept(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public int employeeId() {
        return employeeId;
    }

    @Override
    public String employeeName() {
        return employeeName;
    }
    @Override
     public void assignDepartment(String deptName) {
        this.deptName = deptName;
    }

    public abstract int calculateSalary();


    public abstract void benefitLayout();
}
