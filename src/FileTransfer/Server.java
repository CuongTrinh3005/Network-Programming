/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileTransfer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quoc Cuong
 */
public class Server {
    private static final String serverDir = "D:\\SERVER\\";
    
    public static void makeSureDirExist(String dirName){
        File dir = new File(dirName);
        if(!dir.exists()){
            dir.mkdir();
        }
    }
    
    public static boolean receiveFile(Socket client) throws FileNotFoundException, IOException{
        long readLen = 0;
        DataInputStream dis = new DataInputStream(client.getInputStream());
            
        String fileName = dis.readUTF();
        String filePath = serverDir + fileName;
        Long fileLength = dis.readLong();
            
        System.out.println("File Name: " +  fileName);
        System.out.println("File Length: " + fileLength);
        
        makeSureDirExist(serverDir);
        
        DataOutputStream dos = null;
        if(!(new File(filePath).exists())){
            File fServer = new File(filePath);
            dos = new DataOutputStream(new FileOutputStream(fServer));
        }
        else{
            dos = new DataOutputStream(new FileOutputStream(new File(filePath)));
        }
        
        // Read content of uploading file from client
        byte[] content = new byte[1024];
        while(readLen != fileLength){
            int read = 0;
            if(dis!=null){
                read = dis.read(content);
            }
            readLen += read;
            if(read == -1)
                break;
            float percent = (readLen / fileLength)*100;
            System.out.println("Upload progress: " + percent + " %");
                
            // Write read content to file in server side
            dos.write(content, 0, read);
        }
        
        return true;
    }
    
    public static boolean checkFileToSend(String fileName){
        String path =  serverDir + fileName;
        File f =  new File(path);
        if(!f.exists() || f.isDirectory()){
            System.out.println("Find not found to send");
            return false;
        }
        return true;
    }
    
    public static boolean sendFile(Socket client, String fileName) throws FileNotFoundException, IOException{
        String path =  serverDir + fileName;
        File f =  new File(path);
        
        DataInputStream dis = new DataInputStream(new FileInputStream(f));
        DataOutputStream dos =  new DataOutputStream(client.getOutputStream());
        // Send name file and length of file
        dos.writeUTF(f.getName());
        dos.flush();
        dos.writeLong(f.length());
        dos.flush();
        // Send content if file
        byte[] content = new byte[1024];
        while(true){
            int read  = 0;
            if(dis != null){
                read = dis.read(content);
            }
            if(read == -1)
                break;
            dos.write(content, 0, read);
            dos.flush();
        }
        return true;
    }
    
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(123);
            Socket client = server.accept();
            System.out.println("Connect succesfully!");
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            receiveFile(client);
            
            // Download file for client
            Scanner sc = new Scanner(System.in);
            System.out.print("\nEnter file name to download for client: ");
            String fileName = sc.nextLine();
            boolean available = checkFileToSend(fileName);
            if(available){
                dos.writeBoolean(available);
                sendFile(client, fileName);
            }
            else{
                dos.writeBoolean(!available);
            }
            dos.close();
            client.close();
            server.close();
        }
        catch(SocketException sk){
            System.out.println("Connection error!");
        }
        catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception e){
            System.out.println("Something went wrong, please try again!");
        }
    }
}