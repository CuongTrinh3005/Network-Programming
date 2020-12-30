/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Templates.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quoc Cuong
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(123);
            System.out.println("Server is already!");
            Socket client = server.accept();
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos  = new DataOutputStream(client.getOutputStream());
            
            int choice=0;
            String req="", res = "";
            do{
                try{
                    choice = dis.readInt();
                    if(choice!=0){
                        switch(choice){
                            case 1:{
                                System.out.println("Case 1");
                                req = dis.readUTF();
                                System.out.println("Client's request: " + req);

                                res = "Case 1 handled!";
                                dos.writeUTF(res);
                                dos.flush();
                                break;
                            }
                            case 2:{
                                System.out.println("Case 2");
                                req = dis.readUTF();
                                System.out.println("Client's request: " + req);

                                res = "Case 2 handled!";
                                dos.writeUTF(res);
                                dos.flush();
                                break;
                            }
                            default:{
                                System.out.println("Case default");
                                break;
                            }
                        }
                    }
                }catch(Exception e){
                    System.out.println("Da co loi vui long thu lai");
                }               
            }while(choice != 0);
            client.close();
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception e){
            System.out.println("Da co loi vui long thu lai");
        }  
    }
}