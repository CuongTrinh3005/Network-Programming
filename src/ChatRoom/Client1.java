/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatRoom;

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
    
    public void logIn(Socket client) throws IOException{
        Scanner sc = new Scanner(System.in);
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        DataInputStream dis = new DataInputStream(client.getInputStream());
        
        int idUser=0;
        do{
            System.out.println("Enter usename: ");
            String userName = sc.nextLine().trim();
            System.out.println("Enter password: ");
            String password = sc.nextLine();
            dos.writeUTF(userName); dos.flush();
            dos.writeUTF(password); dos.flush();
            // Receive id
            idUser = dis.readInt();
            if(idUser == 0)
                System.out.println("UserName or password is invalid!");
        }while(idUser == 0);
        
        System.err.println("Log In Successfully");
        System.err.println("UserName is " + idUser);
    }
    
    public static int showMenu(Socket client, String name) throws IOException{
        Scanner sc = new Scanner(System.in);
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        DataInputStream dis = new DataInputStream(client.getInputStream());
        
        int choice = 0;
        do{
            System.out.println("1. Choose existing chatroom");
            System.out.println("2. Create a new chatroom");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            choice = sc.nextInt(); sc.nextLine();
            dos.writeInt(choice); dos.flush();
            
            switch(choice){
                case 1:{
                    System.out.print("Enter room Id: ");
                    int idRoom = sc.nextInt(); sc.nextLine();
                    System.out.println("Id = " + idRoom);
                    dos.writeInt(idRoom); dos.flush();
                    
                    // Receive allow or not
                    boolean available = dis.readBoolean();
                    System.out.println("True or false: " + available);
                    if(!available){
                        System.out.println("Room Id is invalid!");
                        return -1;
                    }
                    else{
                        System.err.println("Starting chat in chatroom " + idRoom);
                        return 1;
                    }
                }
                case 2:{
                    System.out.println("Enter a name for new chatroom: ");
                    String roomName = sc.nextLine();
                    dos.writeUTF(roomName); dos.flush();
                    // Receive info of new room
                    String roomInfo = dis.readUTF();
                    System.err.println(roomInfo);
                    return 2;
                }
                default:{
                    System.out.println("Not found any tasks!");
                    break;
                }
            }
        }while(choice != 0);
        return 0;
    }
    
    public void execute(){
        try {
            Socket client = new Socket(host, port);
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your name: ");
            String name = sc.nextLine();
            
            // Log In
            logIn(client);
            // Show Menu
            int result = showMenu(client, name);
            if(result == 1){
                ReadThreadClient listener = new ReadThreadClient(client);
                listener.start();
                WriteThreadClient writer = new WriteThreadClient(client, name);
                writer.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception ex){
            System.out.println("Please enter a valid format!");
        }
    }
    
    public static void main(String[] args) {
        try {
            Client1 client = new Client1(InetAddress.getLocalHost(), 123);
            client.execute();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}