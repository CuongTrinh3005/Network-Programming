/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quoc Cuong
 */
class ReadThreadClient extends Thread{
    private Socket client;

    public ReadThreadClient(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(client.getInputStream());
            while(true){
                String message = dis.readUTF();
                System.out.println(message);
            }
        } catch (IOException ex) {
            try {
                dis.close();
            } catch (IOException ex1) {
                Logger.getLogger(ReadThreadClient.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ReadThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        } 
    } 
}

class WriteThreadClient extends Thread{
    private Socket client;
    private String name;

    public WriteThreadClient(Socket client, String name) {
        this.client = client;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            Scanner sc = new Scanner(System.in);
            String message = "";
            
            while(true){
                message = sc.nextLine();
                dos.writeUTF(name+ ": " + message);
                dos.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(WriteThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
}

public class Client1 {
    private InetAddress host;
    private int port;

    public Client1(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public void execute(){
        try {
            Socket client = new Socket(host, port);
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your name: ");
            String name = sc.nextLine();
            
            ReadThreadClient listener = new ReadThreadClient(client);
            listener.start();
            WriteThreadClient writer = new WriteThreadClient(client, name);
            writer.start();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        try {
            Client1 client1 = new Client1(InetAddress.getLocalHost(), 123);
            client1.execute();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}