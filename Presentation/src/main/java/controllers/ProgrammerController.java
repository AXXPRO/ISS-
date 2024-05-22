package controllers;

import Interfaces.IObserver;
import Interfaces.IService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.util.List;
import java.util.Objects;

public class ProgrammerController implements IObserver {
    public AnchorPane buttonsAnchorPane;
    public AnchorPane reportAnchorPane;
    public AnchorPane resolveBugAnchorPane;
    public AnchorPane menusAnchorPane;
    public Button resolveBugButton;
    public Button reportCoworkerButton;
    public TextField reportTitleInputField;
    public Button backButton;
    public ListView<EmployeeAbstract> coworkerList;
    public CheckBox urgentCheckBox;
    public TextArea reportDescriptionInputField;
    public Button reportButton;
    public ListView<BugRequest> bugNamesList;
    public TextArea bugDescriptionsList;
    public Button markResolvedButton;
    public Button updateDescriptionHandle;
    public Button undoDescriptionButton;

    private IService service;
    private Programmer programmer;

    private Stage loginStage;
    private Stage stage;


    private boolean ensureCredentials() {

        if ( ! service.checkProgrammerAccount(programmer.getEmail())) {
            stage.close();
            loginStage.show();
            return  false;
        }
        return true;
    }

    public void initConstruct() {

        menusAnchorPane.setVisible(false);
        reportAnchorPane.setVisible(false);
        resolveBugAnchorPane.setVisible(false);
        buttonsAnchorPane.setVisible(true);

        bugNamesList.getItems().addAll(service.getUnresolvedBugRequests());
        bugNamesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {


                bugDescriptionsList.setText(newValue.getDescription());


            }
            else {
                bugDescriptionsList.setText("");
            }
        });




        List<EmployeeAbstract> employees = service.getEmployees().stream()
                .filter(employee -> !Objects.equals(employee.getEmail(), programmer.getEmail()))
                        .toList();
        coworkerList.getItems().addAll(employees);


        this.stage.setOnCloseRequest(event -> {
            service.logoutProgrammer(programmer);
        });

        currentBugDescription = null;

    }

    private void init(){

    }
    public void setData(IService service, Programmer programmer, Stage ownStage, Stage loginStage) {
        this.service = service;
        this.programmer = programmer;
        this.loginStage = loginStage;
        this.stage = ownStage;


        initConstruct();

        init();

    }



    String currentBugDescription;

    public void handleMarkResolvedButton(ActionEvent event) {

       if(!ensureCredentials())
           return;

        BugRequest bugRequest = bugNamesList.getSelectionModel().getSelectedItem();
        if(bugRequest == null) {
            return;
        }
        bugRequest.setStatus(BugStatus.RESOLVED);
        service.changeBugRequest(bugRequest);
    }

    public void handleUpdateDescriptionHandle(ActionEvent event) {

        if(!ensureCredentials())
            return;

        BugRequest bugRequest = bugNamesList.getSelectionModel().getSelectedItem();
        if(bugRequest == null) {
            return;
        }
        bugRequest.setDescription(bugDescriptionsList.getText());
        service.changeBugRequest(bugRequest);

    }

    public void handleUndoDescriptionUpdate(ActionEvent event) {


        if(!ensureCredentials())
            return;

        BugRequest bugRequest = bugNamesList.getSelectionModel().getSelectedItem();
        if(bugRequest == null) {
            return;
        }
        bugDescriptionsList.setText(bugRequest.getDescription());


    }

    public void handleReportButton(ActionEvent event) {


        if(!ensureCredentials())
            return;
    }

    public void handleBackButton(ActionEvent event) {

        if(reportAnchorPane.isVisible())
        {
            reportAnchorPane.getChildren().remove(backButton);

        }
        else {
            resolveBugAnchorPane.getChildren().remove(backButton);
        }
        menusAnchorPane.getChildren().add(backButton);

        menusAnchorPane.setVisible(false);
        reportAnchorPane.setVisible(false);
        resolveBugAnchorPane.setVisible(false);
        buttonsAnchorPane.setVisible(true);

    }

    public void handleReportCoworkerButton(ActionEvent event) {

        menusAnchorPane.getChildren().remove(backButton);
        reportAnchorPane.getChildren().add(backButton);

        menusAnchorPane.setVisible(true);
        reportAnchorPane.setVisible(true);
        resolveBugAnchorPane.setVisible(false);
        buttonsAnchorPane.setVisible(false);
    }

    public void handleResolveBugButton(ActionEvent event) {

        menusAnchorPane.getChildren().remove(backButton);
        resolveBugAnchorPane.getChildren().add(backButton);

        menusAnchorPane.setVisible(true);
        reportAnchorPane.setVisible(false);
        resolveBugAnchorPane.setVisible(true);
        buttonsAnchorPane.setVisible(false);
    }

    @Override
    public void bugChanged(BugRequest bugRequest) {

        Platform.runLater(() -> {

            if(bugRequest.getStatus().equals(BugStatus.RESOLVED)) {
                bugNamesList.getItems().removeIf(bug -> bug.getId().equals(bugRequest.getId()));
            } else {


                for(BugRequest bug : bugNamesList.getItems()) {
                    if(bug.getId().equals(bugRequest.getId())) {
                        bug.setDescription(bugRequest.getDescription());

                        BugRequest currentSelectedBug = bugNamesList.getSelectionModel().getSelectedItem();
                        if(currentSelectedBug == null) {
                            return;
                        }
                        if(currentSelectedBug.getId().equals(bugRequest.getId())) {
                            bugDescriptionsList.setText(bugRequest.getDescription());
                        }


                        return;
                    }
                }
                bugNamesList.getItems().add(bugRequest);
            }
        });



    }
}
