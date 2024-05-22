package network.utils;

import model.*;
import network.Request;
import network.RequestType;
import network.Response;
import network.ResponseType;

import java.util.List;

public class JsonUtils {

    public static Request createNewProgrammerLoginRequest(Programmer loggedUser) {
        Request req = new Request();
        req.setType(RequestType.LOGIN_PROGRAMMER);
        req.setProgrammer(loggedUser);
        return req;
    }

    public static Request createNewBugChangedRequest(BugRequest bugRequest) {
        Request req = new Request();
        req.setType(RequestType.BUG_REQUEST_CHANGED);
        req.setBugRequest(bugRequest);
        return req;
    }

    public static Request createNewTesterLoginRequest(Tester loggedUser) {
        Request req = new Request();
        req.setType(RequestType.LOGIN_TESTER);
        req.setTester(loggedUser);
        return req;

    }

    public static Request createNewSysAdminLoginRequest(SysAdmin loggedUser) {
        Request req = new Request();
        req.setType(RequestType.LOGIN_SYSADMIN);
        req.setSysAdmin(loggedUser);
        return req;

    }

    public static Request createNewSysAdminLogoutRequest(SysAdmin user){
        Request req = new Request();
        req.setType(RequestType.LOGOUT_SYSADMIN);
        req.setSysAdmin(user);
        return req;
    }


    public static Request createNewProgrammerLogoutRequest(Programmer user){
        Request req = new Request();
        req.setType(RequestType.LOGOUT_PROGRAMMER);
        req.setProgrammer(user);
        return req;
    }


    public static Request createNewTesterLogoutRequest(Tester user){
        Request req = new Request();
        req.setType(RequestType.LOGOUT_TESTER);
        req.setTester(user);
        return req;
    }

    public static Request createNewUnresolvedBugsRequest() {
        Request req = new Request();
        req.setType(RequestType.GET_UNRESOLVED_BUG_REQUESTS);

        return req;
    }
    public static Response createNewOKResponse(){
        Response resp = new Response();
        resp.setType(ResponseType.OK);
        return resp;
    }


    public static Response createNewErrorResponse(String error){
        Response resp = new Response();
        resp.setType(ResponseType.ERROR);
        resp.setErrorMessage(error);
        return resp;
    }

    public static Response createNewProgrammerLoginResponse(Programmer loggedUser) {
        Response resp = new Response();
        resp.setType(ResponseType.LOGIN_PROGRAMMER);
        resp.setProgrammer(loggedUser);
        return resp;
    }

    public static Response createNewSysAdminLoginResponse(SysAdmin loggedUser) {
        Response resp = new Response();
        resp.setType(ResponseType.LOGIN_SYSADMIN);
        resp.setSysAdmin(loggedUser);
        return resp;
    }

    public static Response createNewBugRequestChangedResonse(BugRequest bugRequest) {
        Response resp = new Response();
        resp.setType(ResponseType.BUG_REQUEST_CHANGED);
        resp.setBugRequest(bugRequest);
        return resp;
    }

    public static Response createNewTesterLoginResponse(Tester loggedUser) {
        Response resp = new Response();
        resp.setType(ResponseType.LOGIN_TESTER);
        resp.setTester(loggedUser);
        return resp;

    }
    public static Response createNewUnresolvedBugsResponse(List<BugRequest> unresolvedBugs) {
        Response resp = new Response();
        resp.setType(ResponseType.UNRESOLVED_BUG_REQUESTS);
        resp.setBugRequests(unresolvedBugs);
        return resp;
    }

    public static Request createNewGetEmployeesRequest() {
        Request req = new Request();
        req.setType(RequestType.GET_EMPLOYEES);

        return req;

    }

    public static Request createNewSaveProgrammerRequest(Programmer programmer) {
        Request req = new Request();
        req.setType(RequestType.SAVE_PROGRAMMER);
        req.setProgrammer(programmer);

        return req;

    }

    public static Request createNewDeleteEmployeeAccountRequest(EmployeeAbstract employee) {
        Request req = new Request();
        req.setType(RequestType.DELETE_ACCOUNT);
        req.setEmployee(employee);

        return req;

    }

    public static Request createNewCheckProgrammerAccountRequest(Programmer programmer) {
        Request req = new Request();
        req.setType(RequestType.CHECK_PROGRAMMER_ACCOUNT);
        req.setProgrammer(programmer);

        return req;

    }

    public static Request createNewCheckTesterAccountRequest(Tester tester) {
        Request req = new Request();
        req.setType(RequestType.CHECK_TESTER_ACCOUNT);
        req.setTester(tester);

        return req;

    }



    public static Request createNewSaveTesterRequest(Tester tester) {
        Request req = new Request();
        req.setType(RequestType.SAVE_TESTER);
        req.setTester(tester);

        return req;

    }
    public static Request createNewSubmitBugRequest(BugRequest bugRequest) {
        Request req = new Request();
        req.setType(RequestType.SUBMIT_BUG_REQUEST);
        req.setBugRequest(bugRequest);

        return req;

    }

    public static Response createNewSubmitBugResponse(BugRequest bugRequest) {
        Response resp = new Response();
        resp.setType(ResponseType.SUBMIT_BUG_RESPONSE);
        resp.setBugRequest(bugRequest);

        return resp;

    }



    public static Response createNewGetEmployeesResponse(List<EmployeeAbstract> employees) {
         Response resp = new Response();
        resp.setType(ResponseType.GET_EMPLOYEES);
        resp.setEmployees(employees);
        return resp;

    }
//
//    public static Request createNewLoginRequest(User user){
//        Request req = new Request();
//        req.setType(RequestType.LOGIN);
//        req.setUser(user);
//        return req;
//    }
//
//    public static Request createNewLogoutRequest(User user){
//        Request req = new Request();
//        req.setType(RequestType.LOGOUT);
//        req.setUser(user);
//        return req;
//    }
//
//    public static Request createNewProbeAgeRequest(ProbaNetworkDTO probaNetworkDTO){
//        Request req = new Request();
//        req.setType(RequestType.PROBE_BY_AGE);
//        req.setProbaNetworkDTO(probaNetworkDTO);
//        return req;
//    }
//
//    public static Request createNewProbeDTOAgeRequest(ProbaNetworkDTO probaNetworkDTO){
//        Request req = new Request();
//        req.setType(RequestType.PROBE_DTO_BY_AGE);
//        req.setProbaNetworkDTO(probaNetworkDTO);
//        return req;
//    }
//
//    public static Request createNewSaveRequest(RegisterDTO register){
//        Request req = new Request();
//        req.setType(RequestType.SAVE_REGISTER);
//        req.setRegisterDTO(register);
//        return req;
//    }
//
//    public static Request createNewChildProbeRequest(ProbaNetworkDTO probaNetworkDTO){
//        Request req = new Request();
//        req.setType(RequestType.CHILD_PROBE_AGE);
//        req.setProbaNetworkDTO(probaNetworkDTO);
//        return req;
//    }
}