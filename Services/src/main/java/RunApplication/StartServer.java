package RunApplication;


import model.SysAdmin;
import network.AbstractServer;
import network.ConcurrentSever;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.IRepositorySysAdmin;
import repository.IRepositoryTester;
import service.MasterService;
import model.Programmer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
@ComponentScan( basePackages={"repository", "model", "service", "RunApplication", "network"})
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "model")

public class StartServer {

    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(StartServer.class, args);
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartServer.class.getClassLoader().getResourceAsStream("server.props"));
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }





//
//        MasterService service = applicationContext.getBean(MasterService.class);
//        service.saveProgrammer("Gabriel", "email@", "123");
//        service.saveProgrammer("Andrei", "email2@", "123");
//        service.saveTester("Raul", "email@", "123");
//        service.saveTester("Costel", "email2@", "123");

//        SysAdmin sysAdmin = new SysAdmin();
//        sysAdmin.setEmail("adminemail@");
//
//
//         IRepositorySysAdmin repo =   applicationContext.getBean(IRepositorySysAdmin.class);
//      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        sysAdmin.setPassword(  passwordEncoder.encode("123"));
//
//        repo.save(sysAdmin);


        AbstractServer server = applicationContext.getBean(ConcurrentSever.class);



        try {
            server.start();
        } catch (RuntimeException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(RuntimeException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }


    }
}
