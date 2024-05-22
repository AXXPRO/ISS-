package model;

public class EmployeeAbstract implements Employee<Long> {


    String DTOEmail;
    String DTOPassword;
    Long DTOId;

    @Override
    public String getEmail() {
       return DTOEmail;
    }

    @Override
    public String getPassword() {
       return  DTOPassword;
    }

    @Override
    public Long getId() {
       return DTOId;
    }

    public void setEmail(String email) {
       this.DTOEmail = email;
    }

    public void setPassword(String password) {
       this.DTOPassword = password;
    }

    public void setId(Long id) {
       this.DTOId = id;
    }

    @Override
    public String toString() {
        return  DTOEmail;
    }
}