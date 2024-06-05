package controllers;

import Interfaces.IObserver;
import Interfaces.IService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.util.Objects;

public class SysAdminController implements IObserver {

    public AnchorPane buttonsAnchorPane;
    public Button createAccountButton;
    public Button deleteAccountButton;
    public Button viewReportsButton;
    public AnchorPane menusAnchorPane;
    public AnchorPane createAccountAnchorPane;
    public TextField nameInputField;
    public TextField emailInputField;
    public PasswordField passwordInputField;
    public PasswordField confirmPasswordInputField;
    public RadioButton programmerCheckBox;
    public ToggleGroup accountCreation;
    public RadioButton testerCheckBox;
    public Button accountButton;
    public AnchorPane viewReportsAnchorPane;
    public AnchorPane deleteAccountAnchorPane;
    public ListView<EmployeeAbstract> employeeList;
    public Button deleteAccount;
    public Button backButton;
    public ListView<Report> reportTitleList;
    public TextArea reportDescriptionTextArea;
    public Button acknowledgeButton;

    IService server;
    Stage loginStage;
    Stage stage;
    SysAdmin sysAdmin;

    @Override
    public void bugChanged(BugRequest bugRequest) {

    }

    @Override
    public void reportChanged(Report report) {


        Platform.runLater(() -> {
            reportTitleList.getItems().add(report);
            if(report.getUrgentStatus())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("URGENT REPORT ADDED");
                alert.setHeaderText(report.toString());
                alert.setContentText(report.getDescription());

                alert.showAndWait();
            }

        });






    }

    public void handleCreateAccountButton(ActionEvent event) {
        menusAnchorPane.getChildren().remove(backButton);
        createAccountAnchorPane.getChildren().add(backButton);

        menusAnchorPane.setVisible(true);
        createAccountAnchorPane.setVisible(true);
        viewReportsAnchorPane.setVisible(false);
        deleteAccountAnchorPane.setVisible(false);

        buttonsAnchorPane.setVisible(false);


    }

    public void handleDeleteAccountButton(ActionEvent event) {

        menusAnchorPane.getChildren().remove(backButton);
        deleteAccountAnchorPane.getChildren().add(backButton);

        menusAnchorPane.setVisible(true);
        createAccountAnchorPane.setVisible(false);
        viewReportsAnchorPane.setVisible(false);
        deleteAccountAnchorPane.setVisible(true);

        buttonsAnchorPane.setVisible(false);

        employeeList.getItems().clear();

        employeeList.getItems().addAll(server.getEmployees());


    }

    public void handleViewReportsButton(ActionEvent event) {

        menusAnchorPane.getChildren().remove(backButton);
        viewReportsAnchorPane.getChildren().add(backButton);

        menusAnchorPane.setVisible(true);
        createAccountAnchorPane.setVisible(false);
        viewReportsAnchorPane.setVisible(true);
        deleteAccountAnchorPane.setVisible(false);

        buttonsAnchorPane.setVisible(false);

    }

    public void handleCreateAccount(ActionEvent event) {


        String name = nameInputField.getText();
        String email = emailInputField.getText();
        String password = passwordInputField.getText();
        String confirmPassword = confirmPasswordInputField.getText();

        if(!Objects.equals(password, confirmPassword))
            return;
        if ( programmerCheckBox.isSelected() ) {

            server.saveProgrammer(name,email,password);
        }
        else {
            server.saveTester(name,email, password);
        }

        nameInputField.clear();
        emailInputField.clear();
        passwordInputField.clear();
        confirmPasswordInputField.clear();
    }

    public void handleDeleteAccount(ActionEvent event) {

        EmployeeAbstract employeeAbstract = employeeList.getSelectionModel().getSelectedItem();
        if (employeeAbstract == null)
            return;

        server.deleteEmployeeAcount(employeeAbstract.getEmail());

        employeeList.getItems().remove(employeeAbstract);

    }

    public void handleBuckButton(ActionEvent event) {


        if(createAccountAnchorPane.isVisible())
        {
            createAccountAnchorPane.getChildren().remove(backButton);

        }
        else  if(viewReportsAnchorPane.isVisible()) {
            viewReportsAnchorPane.getChildren().remove(backButton);
        }
        else {

            deleteAccountAnchorPane.getChildren().remove(backButton);
        }


        menusAnchorPane.getChildren().add(backButton);

        menusAnchorPane.setVisible(false);
        createAccountAnchorPane.setVisible(false);
        viewReportsAnchorPane.setVisible(false);
        deleteAccountAnchorPane.setVisible(false);

        buttonsAnchorPane.setVisible(true);
    }

    public void setData(IService service, SysAdmin sysAdmin, Stage stage, Stage loginStage) {

        this.server = service;
        this.sysAdmin = sysAdmin;
        this.stage = stage;
        this.loginStage = loginStage;

        initConsturctor();


    }

    private void init() {

    }

    private void initConsturctor() {

        menusAnchorPane.setVisible(false);
        createAccountAnchorPane.setVisible(false);
        viewReportsAnchorPane.setVisible(false);
        deleteAccountAnchorPane.setVisible(false);

        buttonsAnchorPane.setVisible(true);


        employeeList.getItems().addAll(server.getEmployees());

        stage.setOnCloseRequest(event -> {
            server.logoutSysAdmin(sysAdmin);

        });

        reportTitleList.getItems().addAll(server.getReports());

        reportTitleList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                reportDescriptionTextArea.setText(newValue.getDescription());
            }
            else {
                reportDescriptionTextArea.setText("");
            }
        });



    }

    public void handleAcknowledgeButton(ActionEvent event) {

        Report report = reportTitleList.getSelectionModel().getSelectedItem();
        if(report == null)
            return;

        server.acknowledgeReport(report);

        reportTitleList.getItems().remove(report);
        reportDescriptionTextArea.clear();

    }
}
