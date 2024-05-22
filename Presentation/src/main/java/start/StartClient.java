package start;

import Interfaces.IService;
import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.ServiceProxy;

import java.io.IOException;
import java.util.Properties;

public class StartClient extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Properties serverProps = new Properties();
        try {
            serverProps.load(getClass().getClassLoader().getResourceAsStream("bd.config"));
        } catch (IOException e) {
            System.err.println("Cannot find bd.config " + e);
            return;
        }

        String IP = serverProps.getProperty("host");
        int port = Integer.parseInt(serverProps.getProperty("server.port"));

        IService server = new ServiceProxy(IP, port);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        LoginController controller = fxmlLoader.getController();
        controller.setServer(server, stage);

        stage.show();
    }

    public static void main(String[] args) {

        launch();

     //   Runtime.getRuntime().addShutdownHook(new Thread(JDBCUtils.getInstance()::closeConnection));



    }
}
