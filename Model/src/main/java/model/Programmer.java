package model;

import jakarta.persistence.*;



@Entity
@Table(name = "programmers")
public class Programmer  extends EmployeeAbstract {



    private Long id;


    private String name;


    private String email;


    private String password;


    public Programmer() {

    }

    public void setPassword(String password) {

        this.password = password;
        super.DTOPassword = password;
    }

    @Override
    @Column(name = "password")
    public String getPassword() {
        return password;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        super.DTOId = id;
    }
    @Column (name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "email")
    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        super.DTOEmail = email;
    }

}
