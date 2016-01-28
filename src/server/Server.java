package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Vector;

public class Server implements Runnable{

    private ServerSocket ss;
    private List<Client> clients = new Vector<>();
    
    Server(int port) throws IOException {
        ss = new ServerSocket(port);
    }

    @Override
    public void run() {
        Client c;
        while(true) {
            try {
                c = new Client(ss.accept(),this);
                clients.add(c);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                continue;
            }
            new Thread(c).start();
        }
        
    }
    
    public void notifyClients(String message) {
       synchronized(clients) {
            for(Client c : clients) {
                try {
                    c.sendMessage(message);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    void removeClient(Client c) {
        clients.remove(c);
    }
}
