package network;


import model.*;

public class Request {

    private RequestType Type;
    private Programmer programmer;
    private BugRequest bugRequest;
    private Report report;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    private EmployeeAbstract employee;

    public EmployeeAbstract getEmployee() {

        return employee;
    }

    public void setEmployee(EmployeeAbstract employee) {
        this.employee = employee;
    }

    private SysAdmin sysAdmin;

    public BugRequest getBugRequest() {
        return bugRequest;
    }

    public void setBugRequest(BugRequest bugRequest) {
        this.bugRequest = bugRequest;
    }

    private Tester tester;


    public Request() {}

    public RequestType getType() {
        return Type;
    }

    public void setType(RequestType Type) {
        this.Type = Type;
    }

    public Programmer getProgrammer() {
        return programmer;
    }

    public void setProgrammer(Programmer programmer) {
        this.programmer = programmer;
    }

    public Tester getTester() {
        return tester;
    }

    public void setTester(Tester tester) {
        this.tester = tester;
    }

    @Override
    public String toString() {
        return "Request{" +
                "Type=" + Type +
                '}';
    }

    public void setSysAdmin(SysAdmin loggedUser) {
        this.sysAdmin=loggedUser;
    }

    public SysAdmin getSysAdmin() {
        return sysAdmin;
    }
}