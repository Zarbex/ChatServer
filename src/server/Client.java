package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{
    
    private Socket socket;
    private BufferedReader in;
    private Server server;
    private PrintWriter out;
    
    public Client(Socket socket, Server server) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        this.server = server;
    }

    @Override
    public void run() {
        String message = null;
        while(true) {
            try {
                message = in.readLine();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                break;
            }
            if(message==null) {
                break;
            }
            System.out.println(message);
            server.notifyClients(message);
        }
        server.removeClient(this);
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    void sendMessage(String message) throws IOException {
        out.println(message);
    }
}
