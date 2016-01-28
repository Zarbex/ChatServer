package server;

import java.io.IOException;

public class Controller implements Runnable{

    private Server server;
        
    public static void main(String[] args) {
        Controller controller = new Controller();
        
        System.out.println("Server:");
        
        new Thread(controller).start();
    }

    public Controller() {
        try {
            server = new Server(6789);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Override
    public void run() {
        new Thread(server).start();
    }
}
