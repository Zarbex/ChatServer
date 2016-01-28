package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientTesti implements Runnable{

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scan = new Scanner(System.in);
    
    public ClientTesti() throws IOException
    {
        socket = new Socket("localhost", 6789);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }
    
    public static void main(String[] args) {
        System.out.println("Client:");

        try {
            ClientTesti ct = new ClientTesti();
            
            new Thread(ct).start();

            while (true) {
                ct.read();
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run()
    {
        String s = null;
        while (true) {
            try {
                s = in.readLine();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            if (s == null) {
                continue;
            }
            System.out.println(s);
        }
    }
    
    public void read() {
        out.println("Server: "+scan.nextLine());
    }
}