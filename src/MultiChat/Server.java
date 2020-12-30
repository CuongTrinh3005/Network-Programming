/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quoc Cuong
 */
class ReadThreadServer extends Thread{
    private Socket activeClient;

    public ReadThreadServer(Socket activeClient) {
        this.activeClient = activeClient;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(activeClient.getInputStream());
            while(true){
                String message = dis.readUTF();
                System.out.println(message);
                for (Socket client : Server.clients) {
                    if(client.getPort() != activeClient.getPort()){
                        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                        dos.writeUTF(message);
                        dos.flush();
                    }
                }
            }
        } catch (IOException ex) {
            try {
                dis.close();
                Logger.getLogger(ReadThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex1) {
                Logger.getLogger(ReadThreadServer.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
}

class WriteThreadServer extends Thread{

    @Override
    public void run() {
        DataOutputStream dos = null;
        Scanner sc = new Scanner(System.in);
        while(true){
            String message = sc.nextLine();
            for(Socket client:Server.clients){
                try {
                    dos = new DataOutputStream(client.getOutputStream());
                    dos.writeUTF("Server:" + message);
                    dos.flush();
                } catch (IOException ex) {
                    try {
                        dos.close();
                    } catch (IOException ex1) {
                        Logger.getLogger(WriteThreadServer.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    Logger.getLogger(WriteThreadServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

public class Server {
    public int port;
    public static List<Socket> clients;

    public Server(int port) {
        this.port = port;
    }

    public void execute(){
        try {
            ServerSocket server = new ServerSocket(port);
            WriteThreadServer writer = new WriteThreadServer();
            writer.start();
            while(true){
                Socket client = server.accept();
                System.out.println("Connect to client " + client);
                clients.add(client);
                ReadThreadServer listener = new ReadThreadServer(client);
                listener.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        Server server = new Server(123);
        Server.clients = new ArrayList<>();
        server.execute();
    }
}