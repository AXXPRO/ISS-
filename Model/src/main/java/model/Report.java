package model;

import jakarta.persistence.*;


@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name")
    private String name;


    @Column(name = "description")
    private String description;

    @Column(name = "urgentstatus")
    private Boolean urgentStatus;

    @Column
    private String reportedEmail;

    public String getReportedEmail() {
        return reportedEmail;
    }

    public void setReportedEmail(String reportedEmail) {
        this.reportedEmail = reportedEmail;
    }

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

    public Boolean getUrgentStatus() {
        return urgentStatus;
    }

    public void setUrgentStatus(Boolean urgentStatus) {
        this.urgentStatus = urgentStatus;
    }

    @Override
    public String toString() {
        return name  + " - " + reportedEmail ;
    }
}
