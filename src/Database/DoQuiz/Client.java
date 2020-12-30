/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.DoQuiz;

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
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            Scanner sc = new Scanner(System.in);
            System.out.println("Connect to server successfully!");
            
            System.out.println("Login Progress: ");
            String maSV = "";
            
            do{
                System.out.print("Enter your username: ");
                String userName = sc.nextLine().trim();
                System.out.print("Enter your password: ");
                String password = sc.nextLine();
                
                // send to server
                dos.writeUTF(userName); dos.flush();
                dos.writeUTF(password); dos.flush();
                
                // receive result
                maSV = dis.readUTF();
                if("".equals(maSV)){
                    System.out.println("Username or password is invalid, try again!\n");
                }
            }while("".equals(maSV));
            System.out.println("Student ID: " + maSV);
            System.out.println("LogIn succesTHsfully!");
            
            boolean duocThi = dis.readBoolean();
            if(!duocThi){
                System.out.println("So lan da thi qua 2 lan: ");
                return;
            }
                
            // Start exam
            System.out.println("Starting exam\n");
            int numQuestions = dis.readInt();
            System.out.println("Number of questions: " + numQuestions);
            
            for(int i=1; i<=numQuestions;i++){
                String quiz = dis.readUTF();
                System.out.println(quiz);
                System.out.print("Your answer: ");
                String ansUser = sc.nextLine();
                dos.writeUTF(ansUser); dos.flush();
            }
            
            String result = dis.readUTF();
            System.out.println("Your Score is: " + result);
            // Close connection
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception ex){
            System.out.println("Something went wrong, try again!");
            System.out.println(ex.toString());
        }
    }
}