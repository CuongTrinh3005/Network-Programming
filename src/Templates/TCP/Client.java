/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Templates.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quoc Cuong
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", 123);
            System.out.println("Connect to server successfully!");
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            
            int choice = 0;
            String req="", res="";
            Scanner sc = new Scanner(System.in);
            do{
                try{
                    System.out.println("-------------------------------------------------------");
                    System.out.println("1. Case 1");
                    System.out.println("2. Case 2");
                    System.out.println("0. Exit");
                    System.out.print("Enter your choice: ");
                    choice = sc.nextInt(); sc.nextLine();
                    System.out.println();
                    
                    dos.writeInt(choice);
                    
                    if(choice>0){
                        switch(choice){
                            case 1:{
                                req = "Request to case 1";
                                dos.writeUTF(req);
                                dos.flush();

                                // Server respond
                                res = dis.readUTF();
                                System.out.println(res);
                                break;
                            }
                            case 2:{
                                req = "Request to case 2";
                                dos.writeUTF(req);
                                dos.flush();

                                // Server respond
                                res = dis.readUTF();
                                System.out.println(res);
                                break;
                            }
                            default:{
                                System.out.println("Case not found!");
                                break;
                            }
                        }
                    }
                }
                catch(Exception e){
                    System.out.println("Vui long nhap dung dinh dang!");
                    sc.next(); continue;
                }
            }while(choice!=0);
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}