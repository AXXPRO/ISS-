package Interfaces;

import model.*;

import java.util.List;

public interface IService {

    public Programmer saveProgrammer(String name, String email, String password);
    public Tester saveTester(String name, String email, String password);
    public Programmer loginProgrammer(String email, String password, IObserver client);

    public Boolean checkProgrammerAccount(String email);
    public Boolean checkTesterAccount(String email);
    public Tester loginTester(String email, String password, IObserver client);

    public SysAdmin loginSysAdmin(String email, String password, IObserver client);


    public void logoutProgrammer(Programmer programmer);
    public void  logoutTester(Tester tester);

    public void logoutSysAdmin(SysAdmin sysAdmin);

    public List<BugRequest> getUnresolvedBugRequests();

    public List<EmployeeAbstract> getEmployees();

    public BugRequest submitBugRequest(String name,String description);

    public BugRequest changeBugRequest(BugRequest bugRequest);

    public void deleteEmployeeAcount(String email);

}
