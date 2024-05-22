package network;

import model.*;

import java.util.List;

public class Response {

    private ResponseType Type;

    private Programmer programmer;
    private Tester tester;

    private List<BugRequest> bugRequests;

    private String errorMessage;

    private SysAdmin sysAdmin;

    List<EmployeeAbstract> employees;

    private BugRequest bugRequest;

    public BugRequest getBugRequest() {
        return bugRequest;
    }

    public void setBugRequest(BugRequest bugRequest) {
        this.bugRequest = bugRequest;
    }


    public List<EmployeeAbstract> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeAbstract> employees) {
        this.employees = employees;
    }

    public Response() {}

    public ResponseType getType() {
        return Type;
    }

    public void setType(ResponseType Type) {
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

    public List<BugRequest> getBugRequests() {
        return bugRequests;
    }

    public void setBugRequests(List<BugRequest> bugRequests) {
        this.bugRequests = bugRequests;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public SysAdmin getSysAdmin() {
        return sysAdmin;
    }

    public void setSysAdmin(SysAdmin sysAdmin) {
        this.sysAdmin = sysAdmin;
    }

    @Override
    public String toString() {
        return "Response{" +
                "Type=" + Type +
                '}';
    }
}