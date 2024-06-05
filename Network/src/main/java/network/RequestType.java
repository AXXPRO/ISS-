package network;

public enum RequestType {
    LOGIN_PROGRAMMER,  LOGOUT_PROGRAMMER, LOGIN_TESTER, LOGOUT_TESTER,LOGIN_SYSADMIN,
    LOGOUT_SYSADMIN, GET_UNRESOLVED_BUG_REQUESTS, GET_EMPLOYEES, SUBMIT_BUG_REQUEST,
    BUG_REQUEST_CHANGED, SAVE_PROGRAMMER, SAVE_TESTER, DELETE_ACCOUNT, CHECK_PROGRAMMER_ACCOUNT, CHECK_TESTER_ACCOUNT,
    REPORT, GET_REPORTS, SOLVE_REPORT
}
