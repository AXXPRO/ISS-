package model;


import jakarta.persistence.*;



@Entity
@Table(name = "bugrequests")
public class BugRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name")
    private String name;


    @Column(name = "description")
    private String description;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BugStatus status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BugStatus getStatus() {
        return status;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name;
    }
}
