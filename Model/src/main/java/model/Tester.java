package model;


import jakarta.persistence.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Entity
@Table(name = "testers")
public class Tester extends EmployeeAbstract {


    private Long id;


    private String name;


    private String email;


    private String password;


    public void setPassword(String password) {

        this.password = password;
        super.DTOPassword = password;
    }
    @Column(name = "password")
    @Override
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    @Override
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        super.DTOEmail = email;
    }
}
