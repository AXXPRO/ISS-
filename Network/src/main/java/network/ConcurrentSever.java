package network;



import Interfaces.IService;

import java.io.Serial;
import java.net.Socket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class ConcurrentSever extends AbsConcurrentServer {
    private IService chatServer;


    @Autowired
    public ConcurrentSever(@Value("${server.port}") int port, @Qualifier("masterService") IService chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
       ClientWorker worker=new ClientWorker(chatServer, client);


        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
