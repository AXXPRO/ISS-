package network;


import Interfaces.IObserver;
import Interfaces.IService;
import com.google.gson.Gson;
import model.*;

import java.io.*;
import java.net.Socket;
import java.util.List;

import network.utils.JsonUtils;


public class ClientWorker implements Runnable, IObserver {
    private IService server;
    private Socket connection;

    private InputStream input;
    private OutputStream output;
    private volatile boolean connected;
    public ClientWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output= connection.getOutputStream();
            output.flush();
            input= connection.getInputStream();
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String line = reader.readLine();
                Gson gson = new Gson();
                Request request = gson.fromJson(line, Request.class);
                Response response=handleRequest(request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }


    private static Response okResponse= JsonUtils.createNewOKResponse();

    private Response handleRequest(Request request){
        Response response = null;
        if (request.getType() == RequestType.LOGIN_PROGRAMMER){
            System.out.println("Login request ..." + request.getType());
            Programmer user = request.getProgrammer();
            try {
                Programmer loggedUser = server.loginProgrammer(user.getEmail(), user.getPassword(), this);
                if(loggedUser != null)
                    return JsonUtils.createNewProgrammerLoginResponse(loggedUser);

                throw new Exception("User doesn't exist.");
            } catch (Exception e) {
                connected = false;
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.LOGOUT_PROGRAMMER){
            System.out.println("Logout request");
            Programmer user = request.getProgrammer();

            try {
                server.logoutProgrammer(user);
                connected = false;
                return okResponse;

            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.LOGIN_TESTER){
            System.out.println("Login request ..." + request.getType());
            Tester user = request.getTester();
            try {
                Tester loggedUser = server.loginTester(user.getEmail(), user.getPassword(), this);
                if(loggedUser != null)
                    return JsonUtils.createNewTesterLoginResponse(loggedUser);

                throw new Exception("User doesn't exist.");
            } catch (Exception e) {
                connected = false;
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.LOGOUT_TESTER){
            System.out.println("Logout request");
            Tester user = request.getTester();

            try {
                server.logoutTester(user);
                connected = false;
                return okResponse;

            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }



        if (request.getType() == RequestType.LOGIN_SYSADMIN){
            System.out.println("Login request ..." + request.getType());
            SysAdmin s = request.getSysAdmin();
            try {
                SysAdmin loggedUser = server.loginSysAdmin(s.getEmail(), s.getPassword(), this);
                if(loggedUser != null)
                    return JsonUtils.createNewSysAdminLoginResponse(loggedUser);

                throw new Exception("User doesn't exist.");
            } catch (Exception e) {
                connected = false;
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.LOGOUT_SYSADMIN){
            System.out.println("Logout request");
            SysAdmin s = request.getSysAdmin();

            try {
                server.logoutSysAdmin(s);
                connected = false;
                return okResponse;

            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }


        if (request.getType() == RequestType.GET_UNRESOLVED_BUG_REQUESTS){
            System.out.println("Probe by age ...");

            try {
                List<BugRequest> bugRequests =  server.getUnresolvedBugRequests();
                return JsonUtils.createNewUnresolvedBugsResponse(bugRequests);
            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.GET_EMPLOYEES){
            System.out.println("Get Employees ...");

            try {
                List<EmployeeAbstract> employees =  server.getEmployees();
                return JsonUtils.createNewGetEmployeesResponse(employees);
            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.SUBMIT_BUG_REQUEST){
            System.out.println("Submit Bug ...");

            try {
                BugRequest bugRequest =  server.submitBugRequest(request.getBugRequest().getName(), request.getBugRequest().getDescription());
                return JsonUtils.createNewSubmitBugResponse(bugRequest);
            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.BUG_REQUEST_CHANGED){
            System.out.println("Change Bug ...");

            try {
                server.changeBugRequest(request.getBugRequest());
                return JsonUtils.createNewOKResponse();
            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.SAVE_PROGRAMMER){
            System.out.println("Change Bug ...");

            try {
                Programmer p = request.getProgrammer();
                server.saveProgrammer(p.getName(), p.getEmail(), p.getPassword());
                return JsonUtils.createNewOKResponse();
            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.SAVE_TESTER){
            System.out.println("Change Bug ...");

            try {
                Tester t = request.getTester();
                server.saveTester(t.getName(), t.getEmail(), t.getPassword());
                return JsonUtils.createNewOKResponse();
            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.DELETE_ACCOUNT){
            System.out.println("Change Bug ...");

            try {
                EmployeeAbstract e = request.getEmployee();
                server.deleteEmployeeAcount(e.getEmail());
                return JsonUtils.createNewOKResponse();
            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.CHECK_PROGRAMMER_ACCOUNT){
            System.out.println("Checking account");

            try {
                Programmer p = request.getProgrammer();
                if(server.checkProgrammerAccount(p.getEmail()))
                 return JsonUtils.createNewOKResponse();
                else {
                    return JsonUtils.createNewErrorResponse("No more account");
                }
            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.CHECK_TESTER_ACCOUNT){
            System.out.println("Checking account");

            try {
               Tester t = request.getTester();
                if(server.checkTesterAccount(t.getEmail()))
                    return JsonUtils.createNewOKResponse();
                else {
                    return JsonUtils.createNewErrorResponse("No more account");

                }
            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_REPORTS){
            System.out.println("Getting Reports");

            try {

                List<Report> reports = server.getReports();
                    return JsonUtils.createNewGetReportsResponse(reports);


            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }


        if (request.getType() == RequestType.REPORT){
            System.out.println("Checking Report");

            try {
                Report r = request.getReport();
                server.reportCoworker(r.getName(), r.getDescription(), r.getReportedEmail(), r.getUrgentStatus());
                    return JsonUtils.createNewOKResponse();

            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.SOLVE_REPORT){
            System.out.println("Solving Report");

            try {
                Report r = request.getReport();
                server.acknowledgeReport(r);
                return JsonUtils.createNewOKResponse();

            } catch (Exception e) {
                return JsonUtils.createNewErrorResponse(e.getMessage());
            }
        }


        return response;
    }

    private void sendResponse(Response response){
        System.out.println("sending response " + response);

            Gson gson = new Gson();
            String responseJSON = gson.toJson(response);


            try {

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
                writer.write(responseJSON );
                writer.newLine();

                writer.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }


    @Override
    public void bugChanged(BugRequest bugRequest) {
        Response resp = JsonUtils.createNewBugRequestChangedResonse(bugRequest);
        System.out.println("Notifying all clients that a new register was made ...");
        sendResponse(resp);

    }

    @Override
    public void reportChanged(Report report) {
        Response resp = JsonUtils.createNewReportChangedResponse(report);
        System.out.println("Notifying all clients that a new report was made ...");
        sendResponse(resp);
    }
}
