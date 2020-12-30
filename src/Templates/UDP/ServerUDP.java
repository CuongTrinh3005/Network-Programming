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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quoc Cuong
 */
public class ServerUDP {
    private static InetAddress clientAdd = null;
    private static int clientPort = 0;
    
    public static String receiveUDP(DatagramSocket server) throws IOException{
        byte[] forMessage = new byte[256];
        DatagramPacket dpMessage = new DatagramPacket(forMessage, forMessage.length);
        server.receive(dpMessage);
        clientAdd = dpMessage.getAddress();
        clientPort = dpMessage.getPort();
        return new String(dpMessage.getData(), 0, dpMessage.getLength());
    }
    
    public static void sendUDP(DatagramSocket server, String message) throws IOException{
        byte[] forMessage = message.getBytes();
        DatagramPacket dpMessage = new DatagramPacket(forMessage, forMessage.length, clientAdd, clientPort);
        server.send(dpMessage);
    }
    
    public static void main(String[] args) {
        try {
            DatagramSocket server = new DatagramSocket(123);
            System.out.println("Server is already!");
            
            int choice=0; String mess = "";
            do{
                choice = Integer.valueOf(receiveUDP(server));
                if(choice != 0){
                    switch(choice){
                        case 1:{
                            String res = receiveUDP(server);
                            System.out.println(res);
                            mess = "Task 1 handled";
                            System.out.println(mess);
                            sendUDP(server, mess);
                            break;
                        }
                        case 2:{
                            String res = receiveUDP(server);
                            System.out.println(res);
                            mess = "Task 2 handled";
                            System.out.println(mess);
                            sendUDP(server, mess);
                            break;
                        }
                        default:{
                            break;
                        }
                    }
                }
            }while(choice!=0);
        } catch (SocketException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception e){
            System.out.println("Da co loi vui long thu lai");
        }  
    }
}