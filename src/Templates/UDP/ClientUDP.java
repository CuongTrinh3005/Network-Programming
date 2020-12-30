/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Templates.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quoc Cuong
 */
public class ClientUDP {
    public static String reiceiveUDP(DatagramSocket client) throws IOException{
       byte[] forMessage = new byte[256];
       DatagramPacket dpMessage = new DatagramPacket(forMessage, forMessage.length);
       client.receive(dpMessage);
       return new String(dpMessage.getData(), 0, dpMessage.getLength());
    }
    
    public static void sendUDP(DatagramSocket client, String message, InetAddress serverAdd) throws IOException{
        byte[] forMessage = message.getBytes();
        DatagramPacket dpMessage = new DatagramPacket(forMessage, forMessage.length, serverAdd, 123);
        client.send(dpMessage);
    }
    
    public static void main(String[] args) {
        try {
            DatagramSocket client = new DatagramSocket();
            InetAddress serverAdd = InetAddress.getByName("localhost");
            System.out.println("Connect to server successfully!");
            Scanner sc = new Scanner(System.in);
            
            int choice=0; String mess = "", res="";
            do{
                try{
                    System.out.println("------------------------------------------------------");
                    System.out.println("1. Case 1: ");
                    System.out.println("2. Case 2: ");
                    System.out.println("0. Exit: ");
                    System.out.print("Enter your choice: ");
                    choice = sc.nextInt(); sc.nextLine();
                    System.out.println();
                    
                    sendUDP(client, String.valueOf(choice), serverAdd);
                    
                    if(choice>0){
                        switch(choice){
                            case 1:{
                                mess = "Request for task 1";
                                sendUDP(client, mess, serverAdd);
                                res = reiceiveUDP(client);
                                System.out.println("Respond from server: " + res);
                                break;
                            }
                            case 2:{
                                mess = "Request for task 2";
                                sendUDP(client, mess, serverAdd);
                                res = reiceiveUDP(client);
                                System.out.println("Respond from server: " + res);
                                break;
                            }
                            default:{
                                System.out.println("No task found!");
                                break;
                            }
                        }
                    }
                }
                catch(Exception ex){
                    System.out.println("Vui long nhap dung dinh dang)!");
                    sc.next(); continue;
                }
            }while(choice!=0);
            client.close();
        } catch (SocketException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}