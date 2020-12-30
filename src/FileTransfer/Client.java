/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileTransfer;

import static FileTransfer.Server.makeSureDirExist;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private static final String clientDir = "D:\\CLIENT\\";
    
    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", 123);
            DataInputStream dis = new DataInputStream(client.getInputStream());
            System.out.println("Connect to server successfully!");
            
            // Upload file
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter path with filename to upload: ");
            String absPath = sc.nextLine();
            uploadFile(client, absPath);
            
            // Download file
            try{
                System.out.println("\nDownloading file:  ");
                boolean isDownloaded = dis.readBoolean();
                if(isDownloaded){
                    System.out.println("\nStarting progress: ");
                    download(client);
                }
                else{
                    System.out.println("Download failed!");
                }
            }
            catch(EOFException eof){
                System.out.println("Transfering file is not completed!");
            }
            catch(Exception e){
                System.out.println(e.toString());
                e.printStackTrace();
            }
            
            dis.close();
            client.close();
        } 
        catch(FileNotFoundException f){
            System.out.println("File does not existed, please the file name!");
        }
        catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception e){
            System.out.println("da co loi vui long thu lai!");
            System.out.println(e.toString());
        }
    }

    private static void uploadFile(Socket client, String absPath) throws FileNotFoundException, IOException {
//        String clientDir = "D:\\CLIENT\\";
//        String absPath = clientDir + fileName;
        File f = new File(absPath);
        DataInputStream dis = new DataInputStream(new FileInputStream(f));
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        
        System.out.println("File Name: " + f.getName());
        dos.writeUTF(f.getName());
        dos.flush();
        dos.writeLong(f.length());
        dos.flush();
        
        byte[] byteContent = new byte[1024];
        while(true){
            int read = 0;
            if(dis != null){
                read = dis.read(byteContent);
            }
            if(read == -1)
                break;
            dos.write(byteContent, 0, read);
            dos.flush();
        }
        dis.close();
//        dos.close();
    }
    
    public static boolean download(Socket client) throws FileNotFoundException, IOException{
        long readLen = 0;
        DataInputStream dis = new DataInputStream(client.getInputStream());
            
        String fileName = dis.readUTF();
        String filePath = clientDir + fileName;
        Long fileLength = dis.readLong();
            
        System.out.println("File Name: " +  fileName);
        System.out.println("File Length: " + fileLength);
        
        makeSureDirExist(clientDir);
        
        DataOutputStream dos = null;
        if(!(new File(filePath).exists())){
            File fClient = new File(filePath);
            dos = new DataOutputStream(new FileOutputStream(fClient));
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
            System.out.println("Download progress: " + percent + " %");
                
            // Write read content to file in server side
            dos.write(content, 0, read);
        }
        
        return true;
    }
}