package controllers;

import Interfaces.IObserver;
import Interfaces.IService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.BugRequest;
import model.EmployeeAbstract;
import model.Programmer;
import model.Tester;

import java.util.List;
import java.util.Objects;

public class TesterController implements IObserver {
    public AnchorPane buttonsAnchorPane;
    public Button submitBugButton;
    public Button reportCoworkerButton;
    public AnchorPane bugAnchorPane;
    public TextArea bugDescriptionTextArea;
    public ListView<EmployeeAbstract> coworkerList;
    public AnchorPane reportAnchorPane;
    public AnchorPane menusAnchorPane;
    public CheckBox urgentCheckBox;
    public TextField reportTitleInputField;
    public TextArea reportDescriptionInputField;
    public Button reportButton;
    public TextField bugNameInputField;

    public Button sendBugButton;
    public Button backButton;


    private IService service;
    private Tester tester;

    private Stage loginStage;
    private Stage stage;


    public TesterController() {

    }

    @FXML
    public void initialize() {



    }

    private boolean ensureCredentials() {

        if (!service.checkTesterAccount(tester.getEmail())) {
            stage.close();
            loginStage.show();
            return false;
        }
        return true;
    }



    private void init(){

    }
    public void setData(IService service, Tester tester, Stage stage, Stage loginStage) {
        this.service = service;
        this.tester = tester;
        this.loginStage = loginStage;
        this.stage = stage;


        initConstruct();

        init();
    }

    private void initConstruct() {
        menusAnchorPane.setVisible(false);
        reportAnchorPane.setVisible(false);
        bugAnchorPane.setVisible(false);
        buttonsAnchorPane.setVisible(true);
        



        List<EmployeeAbstract> employees = service.getEmployees().stream()
                .filter(employee -> !Objects.equals(employee.getEmail(), tester.getEmail()))
                .toList();
        coworkerList.getItems().addAll(employees);


        this.stage.setOnCloseRequest(event -> {
            service.logoutTester(tester);
        });


    }

    public void handleSubmitBugButton(ActionEvent event) {


        menusAnchorPane.getChildren().remove(backButton);
       bugAnchorPane.getChildren().add(backButton);

        menusAnchorPane.setVisible(true);
        reportAnchorPane.setVisible(false);
        bugAnchorPane.setVisible(true);
        buttonsAnchorPane.setVisible(false);
    }

    public void handleReportCoworkerButton(ActionEvent event) {


        menusAnchorPane.getChildren().remove(backButton);
        reportAnchorPane.getChildren().add(backButton);

        menusAnchorPane.setVisible(true);
        reportAnchorPane.setVisible(true);
        bugAnchorPane.setVisible(false);
        buttonsAnchorPane.setVisible(false);
    }

    public void handleReportButton(ActionEvent event) {
    }

    public void handleSendBugButton(ActionEvent event) {


        if(!ensureCredentials())
            return;

        String name = bugNameInputField.getText();
        String description = bugDescriptionTextArea.getText();
        BugRequest bugRequest = service.submitBugRequest(name, description);
        bugNameInputField.clear();
        bugDescriptionTextArea.clear();


    }

    public void handleBackButton(ActionEvent event) {

        if(reportAnchorPane.isVisible())
        {
            reportAnchorPane.getChildren().remove(backButton);

        }
        else {
            bugAnchorPane.getChildren().remove(backButton);
        }
        menusAnchorPane.getChildren().add(backButton);

        menusAnchorPane.setVisible(false);
        reportAnchorPane.setVisible(false);
        bugAnchorPane.setVisible(false);
        buttonsAnchorPane.setVisible(true);
    }

    @Override
    public void bugChanged(BugRequest bugRequest) {

    }
}
