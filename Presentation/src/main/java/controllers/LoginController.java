package controllers;

import Interfaces.IService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Programmer;
import model.SysAdmin;
import model.Tester;

import java.io.IOException;

public class LoginController {
    public TextField loginUsernameInput;
    public Button submitButton;
    public PasswordField loginPasswordInput;
    public ToggleGroup userType;
    private IService service;
    private Stage loginStage;

    public void setServer(IService server, Stage stage) {
        this.service = server;
        this.loginStage = stage;
    }

    public void attemptLogin(ActionEvent event) {

        if (userType.getSelectedToggle() == null) {

            return;
        }
        String radioValue = ( (RadioButton) userType.getSelectedToggle() ).getText();



        switch (radioValue) {
            case "Tester": {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tester-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                TesterController controller = fxmlLoader.getController();
                Tester tester = service.loginTester(loginUsernameInput.getText(), loginPasswordInput.getText(),controller);
                if(tester == null) {
                    return;
                }
                Stage stage = new Stage();
                stage.setTitle("Tester");
                stage.setScene(scene);
                controller.setData(service, tester,stage, loginStage);
                stage.show();

                loginStage.hide();
            }
                break;
            case "Programmer": {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/programmer-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ProgrammerController controller = fxmlLoader.getController();
                Programmer programmer = service.loginProgrammer(loginUsernameInput.getText(), loginPasswordInput.getText(),controller);
                if(programmer == null) {
                    return;
                }
                Stage stage = new Stage();
                stage.setTitle("Programmer");
                stage.setScene(scene);
                controller.setData(service, programmer,stage, loginStage);

                stage.show();
                loginStage.hide();
            }
                break;
             case "SysAdmin":
       {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sysadmin-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                SysAdminController controller = fxmlLoader.getController();
                SysAdmin sysAdmin = service.loginSysAdmin(loginUsernameInput.getText(), loginPasswordInput.getText(),controller);
                if(sysAdmin == null) {
                    return;
                }

                Stage stage = new Stage();
                stage.setTitle("Sysadmin");
                stage.setScene(scene);

                controller.setData(service, sysAdmin,stage, loginStage);

                stage.show();
                 loginStage.hide();
            }
             break;
        }

    }
}
