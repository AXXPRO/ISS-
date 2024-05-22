package model;


import jakarta.persistence.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Entity
@Table(name = "sysadmins")
public class SysAdmin implements Employee {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;


    public void setPassword(String password) {

        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
