package network;


import model.*;
import network.utils.JsonUtils;

import Interfaces.IObserver;
import Interfaces.IService;
import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServiceProxy implements IService {
    private String IP;
    private int port;
    Socket connection;
    private IObserver client;

    private InputStream input;
    private OutputStream output;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServiceProxy(String IP, int port) {
        this.IP = IP;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }



    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void sendRequest(Request request) {
        try {
            Gson gson = new Gson();
            String requestJSON = gson.toJson(request);
            byte[] byteArray = requestJSON.getBytes(StandardCharsets.UTF_8);

            output.write(byteArray);
            output.write('\n');
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error sending object", e);
        }

    }
    private Response readResponse() {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }









    private void initializeConnection()  {
        try {
            connection=new Socket(IP,port);
            output=connection.getOutputStream();


            input=connection.getInputStream();
            finished=false;
            output.flush();
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    @Override
    public Programmer saveProgrammer(String name, String email, String password) {

        Programmer programmer = new Programmer();
        programmer.setName(name);
        programmer.setEmail(email);
        programmer.setPassword(password);
        Request req = JsonUtils.createNewSaveProgrammerRequest(programmer);
        sendRequest(req);
        Response response=readResponse();

        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();
            closeConnection();
            throw new RuntimeException(err);
        }
        return null;
    }

    @Override
    public Tester saveTester(String name, String email, String password) {
        Tester tester = new Tester();
        tester.setName(name);
        tester.setEmail(email);
        tester.setPassword(password);
        Request req = JsonUtils.createNewSaveTesterRequest(tester);
        sendRequest(req);
        Response response=readResponse();

        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();
            closeConnection();
            throw new RuntimeException(err);
        }
        return null;
    }

    @Override
    public Programmer loginProgrammer(String email, String password, IObserver client) {
        initializeConnection();
        Programmer user=new Programmer();
        user.setEmail(email);
        user.setPassword(password);
        Request req = JsonUtils.createNewProgrammerLoginRequest(user);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.LOGIN_PROGRAMMER){
            this.client=client;
            Programmer programmer = response.getProgrammer();
            return programmer;
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();
            closeConnection();
            return null;
        }
        return null;

    }

    @Override
    public Boolean checkProgrammerAccount(String email) {
        Programmer user=new Programmer();
        user.setEmail(email);

        Request req = JsonUtils.createNewCheckProgrammerAccountRequest(user);
        sendRequest(req);
        Response response=readResponse();

        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();
            closeConnection();
            return false;
        }
        return true;
    }

    @Override
    public Boolean checkTesterAccount(String email) {
        Tester user=new Tester();
        user.setEmail(email);

        Request req = JsonUtils.createNewCheckTesterAccountRequest(user);
        sendRequest(req);
        Response response=readResponse();

        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();
            closeConnection();
            return false;
        }
        return true;
    }

    @Override
    public Tester loginTester(String email, String password, IObserver client) {
        initializeConnection();
        Tester user=new Tester();
        user.setEmail(email);
        user.setPassword(password);
        Request req = JsonUtils.createNewTesterLoginRequest(user);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.LOGIN_TESTER){
            this.client=client;
            Tester tester = response.getTester();
            return tester;
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();
            closeConnection();
            return null;
        }
        return null;
    }

    @Override
    public SysAdmin loginSysAdmin(String email, String password, IObserver client) {
        initializeConnection();
        SysAdmin user=new SysAdmin();
        user.setEmail(email);
        user.setPassword(password);
        Request req = JsonUtils.createNewSysAdminLoginRequest(user);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.LOGIN_SYSADMIN){
            this.client=client;
            SysAdmin s = response.getSysAdmin();
            return s;
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();
            closeConnection();
            return null;
        }
        return null;
    }

    @Override
    public void logoutProgrammer(Programmer programmer) {
        Request req = JsonUtils.createNewProgrammerLogoutRequest(programmer);
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new RuntimeException(err);
        }
    }

    @Override
    public void logoutTester(Tester tester) {
        Request req = JsonUtils.createNewTesterLogoutRequest(tester);
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new RuntimeException(err);
        }

    }

    @Override
    public void logoutSysAdmin(SysAdmin sysAdmin) {


        Request req = JsonUtils.createNewSysAdminLogoutRequest(sysAdmin);
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new RuntimeException(err);
        }

    }


    @Override
    public List<BugRequest> getUnresolvedBugRequests() {

        Request req = JsonUtils.createNewUnresolvedBugsRequest();
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();
            closeConnection();
            throw new RuntimeException(err);
        }
        return response.getBugRequests();
    }

    @Override
    public List<EmployeeAbstract> getEmployees() {
        Request req = JsonUtils.createNewGetEmployeesRequest();
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();
            closeConnection();
            throw new RuntimeException(err);
        }
        return response.getEmployees();
    }

    @Override
    public BugRequest submitBugRequest(String name, String description) {

        BugRequest temp = new BugRequest();
        temp.setName(name);
        temp.setDescription(description);

        Request req = JsonUtils.createNewSubmitBugRequest(temp);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();
            closeConnection();
            throw new RuntimeException(err);
        }
        return response.getBugRequest();
    }

    @Override
    public BugRequest changeBugRequest(BugRequest bugRequest) {

        Request req = JsonUtils.createNewBugChangedRequest(bugRequest);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();
            closeConnection();
            throw new RuntimeException(err);
        }
        return bugRequest;
    }

    @Override
    public void deleteEmployeeAcount(String email) {

        EmployeeAbstract employeeAbstract = new EmployeeAbstract();
        employeeAbstract.setEmail(email);
        Request req = JsonUtils.createNewDeleteEmployeeAccountRequest(employeeAbstract);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();
            closeConnection();
            throw new RuntimeException(err);
        }
    }

    private class ReaderThread implements Runnable{
        public void run() {
            BufferedReader inputTrue = new BufferedReader(new InputStreamReader(input));

            while(!finished){
                String responseJSON = "";

                try {
                    responseJSON = inputTrue.readLine();
                    Gson gson = new Gson();
                    Response response = gson.fromJson(responseJSON, Response.class);


                    if (response.getType() == ResponseType.BUG_REQUEST_CHANGED) {
                        client.bugChanged(response.getBugRequest());
                   } else
                    {
                        try {
                          qresponses.add(response);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println("Reading error " + e +'\n' + responseJSON  );
                }
            }
        }
    }

}
