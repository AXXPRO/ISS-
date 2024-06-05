package service;

import Interfaces.IObserver;
import Interfaces.IService;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.*;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("masterService")
public class MasterService  implements IService {

   private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private IRepositoryProgrammer repositoryProgrammer;
    private IRepositoryTester repositoryTester;
    private IRepositorySysAdmin repositorySysAdmin;

    private IRepositoryReport repositoryReport;

    private IRepositoryBugRequest repositoryBugRequest;


    private Map<Long, IObserver> loggedProgrammers;
    private Map<Long, IObserver> loggedTesters;
    private Map<Long, IObserver> loggedSysAdmins;


    @Autowired
    public MasterService(IRepositoryProgrammer repositoryProgrammer, IRepositoryTester repositoryTester, IRepositorySysAdmin repositorySysAdmin, IRepositoryReport repositoryReport, IRepositoryBugRequest repositoryBugRequest) {
        this.repositoryProgrammer = repositoryProgrammer;
        this.repositoryTester = repositoryTester;
        this.repositorySysAdmin = repositorySysAdmin;
        this.repositoryReport = repositoryReport;
        this.repositoryBugRequest = repositoryBugRequest;

        loggedProgrammers = new ConcurrentHashMap<>();
        loggedTesters = new ConcurrentHashMap<>();
        loggedSysAdmins = new ConcurrentHashMap<>();
    }

    synchronized  public Programmer saveProgrammer(String name, String email, String password){

        if (repositoryProgrammer.loginProgrammer(email) != null)
            throw new RuntimeException("User already exists.");



        Programmer programmer = new Programmer();
        programmer.setName(name);
        programmer.setEmail(email);
        programmer.setPassword(passwordEncoder.encode(password));


        return repositoryProgrammer.save(programmer);

    }
    synchronized  public Tester saveTester(String name, String email, String password){

        if (repositoryTester.loginTester(email) != null)
            throw new RuntimeException("User already exists.");

        Tester tester = new Tester();
        tester.setName(name);
        tester.setEmail(email);
        tester.setPassword(passwordEncoder.encode(password));
        return repositoryTester.save(tester);

    }
    synchronized public Programmer loginProgrammer(String email, String password, IObserver client){
      Programmer p = repositoryProgrammer.loginProgrammer(email);
      if(p != null && passwordEncoder.matches(password, p.getPassword())){
              if (loggedProgrammers.get(p.getId()) != null)
                  throw new RuntimeException("User already logged in.");
              loggedProgrammers.put(p.getId(), client);


          return p;
      }
      return null;
    }

    @Override
    public Boolean checkProgrammerAccount(String email) {
        Programmer p = repositoryProgrammer.loginProgrammer(email);
        return p != null;
    }

    @Override
    public Boolean checkTesterAccount(String email) {
        Tester p = repositoryTester.loginTester(email);
        return p != null;
    }

    synchronized public Tester loginTester(String email, String password, IObserver client){
        Tester t =  repositoryTester.loginTester(email);

        if(t != null && passwordEncoder.matches(password, t.getPassword())){
            if(loggedTesters.get(t.getId()) != null)
                throw new RuntimeException("User already logged in.");
            loggedTesters.put(t.getId(), client);
            return  t;
        }
        return null;
    }

    @Override
    public SysAdmin loginSysAdmin(String email, String password, IObserver client) {
        SysAdmin s =  repositorySysAdmin.loginSysAdmin(email);

        if(s != null && passwordEncoder.matches(password, s.getPassword())){
            if(loggedSysAdmins.get(s.getId()) != null)
                throw new RuntimeException("User already logged in.");
            loggedSysAdmins.put(s.getId(), client);
            return  s;
        }
        return null;
    }

    @Override
    public void logoutProgrammer(Programmer programmer) {
        IObserver localClient=loggedProgrammers.remove(programmer.getId());
        if (localClient==null)
            throw new RuntimeException("User "+programmer.getId()+" is not logged in.");
    }

    @Override
    public void logoutTester(Tester tester) {
        IObserver localClient=loggedTesters.remove(tester.getId());
        if (localClient==null)
            throw new RuntimeException("User "+tester.getId()+" is not logged in.");
    }

    @Override
    public void logoutSysAdmin(SysAdmin sysAdmin) {
        IObserver localClient=loggedSysAdmins.remove(sysAdmin.getId());
        if (localClient==null)
            throw new RuntimeException("User "+sysAdmin.getId()+" is not logged in.");
    }


    @Override
    public List<BugRequest> getUnresolvedBugRequests() {
        return repositoryBugRequest.getUnresolvedBugRequests();
    }

    @Override
    public List<EmployeeAbstract> getEmployees() {
        List<EmployeeAbstract> employees = new ArrayList<>();



        employees.addAll(getAllProgrammers());
        employees.addAll(getAllTesters());
        return employees;
    }

    @Override
    public BugRequest submitBugRequest(String name, String description) {
        BugRequest bugRequest = new BugRequest();
        bugRequest.setName(name);
        bugRequest.setDescription(description);
        bugRequest.setStatus(BugStatus.UNRESOLVED);
        BugRequest b =  repositoryBugRequest.save(bugRequest);

        bugRequestChange(b);
        return b;

    }

    @Override
    public BugRequest changeBugRequest(BugRequest bugRequest) {
        Optional<BugRequest> bugFound = repositoryBugRequest.findById(bugRequest.getId());
        if(bugFound.isEmpty())
            throw new RuntimeException("Bug not found");
        BugRequest b = bugFound.get();
        b.setStatus(bugRequest.getStatus());
        b.setDescription(bugRequest.getDescription());
        b.setName(bugRequest.getName());

        BugRequest bug = repositoryBugRequest.save(b);

        bugRequestChange(bug);
        return bug;
    }

    @Override
    public void deleteEmployeeAcount(String email) {
        Programmer p = repositoryProgrammer.loginProgrammer(email);
        if (p != null)
        {
            repositoryProgrammer.deleteById(p.getId());

        }

        else {

            Tester t = repositoryTester.loginTester(email);

            repositoryTester.deleteById(t.getId());
        }


    }

    @Override
    public void reportCoworker(String title, String description, String email, boolean urgent) {

        Report report = new Report();
        report.setName(title);
        report.setDescription(description);
        report.setUrgentStatus(urgent);
        report.setReportedEmail(email);
        repositoryReport.save(report);


        reportChange(report);


    }

    @Override
    public List<Report> getReports() {
        return repositoryReport.findAll();
    }

    @Override
    public void acknowledgeReport(Report report) {

        Optional<Report> reportFound = repositoryReport.findById(report.getId());
        if(reportFound.isEmpty())
            throw new RuntimeException("Report not found");
        Report r = reportFound.get();
        repositoryReport.delete(r);



    }


    final int defaultThreadsNo = 5;
    private void bugRequestChange(BugRequest bugRequest){
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for( IObserver chatClient : loggedProgrammers.values()){
            executor.execute(() -> {
                try {
                    chatClient.bugChanged(bugRequest);
                } catch (RuntimeException e) {
                    System.err.println("Error notifying friend " + e);
                }
            });
        }



        executor.shutdown();

    }

    private void reportChange(Report report){
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        for( IObserver chatClient : loggedSysAdmins.values()){
            executor.execute(() -> {
                try {
                    chatClient.reportChanged(report);
                } catch (RuntimeException e) {
                    System.err.println("Error notifying friend " + e);
                }
            });
        }



        executor.shutdown();

    }


    private List<Programmer> getAllProgrammers(){
        return repositoryProgrammer.findAll();
    }

    private List<Tester> getAllTesters(){
        return repositoryTester.findAll();
    }


}
