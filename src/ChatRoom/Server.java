/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatRoom;

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
import java.sql.*;

/**
 *
 * @author Quoc Cuong
 */
class ReadThreadServer extends Thread{
    private Socket activeClient;
    private int idUser;
    private int idRoom;
    private String content;

    public ReadThreadServer(Socket activeClient) {
        this.activeClient = activeClient;
    }

    public ReadThreadServer(Socket activeClient, int idUser, int idRoom) {
        this.activeClient = activeClient;
        this.idUser = idUser;
        this.idRoom = idRoom;
    }
    
    public static void saveChatContent(String content, int idUser, int idRoom) throws SQLException, ClassNotFoundException{
        Connection conn = Server.getConnection();
        String cmd = "INSERT INTO ChatContent VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(cmd);
        ps.setString(1, content);
        ps.setInt(2, idUser);
        ps.setInt(3, idRoom);
        ps.execute();
        
        conn.close();
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
                // save chat content to database
                saveChatContent(message, idUser, idRoom);
            }
        } catch (IOException ex) {
            try {
                dis.close();
                Logger.getLogger(ReadThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex1) {
                Logger.getLogger(ReadThreadServer.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReadThreadServer.class.getName()).log(Level.SEVERE, null, ex);
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

class LogInThread extends Thread{
    private Socket client;
    private volatile int idUser;

    public LogInThread(Socket client) {
        this.client = client;
    }

    public int getIdUser() {
        return idUser;
    }

    @Override
    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            DataInputStream dis = new DataInputStream(client.getInputStream());
        
            int idUser = 0;
            do{
                String userName = dis.readUTF();
                String password = dis.readUTF();
                idUser = Server.findUserById(userName, password);
                dos.writeInt(idUser); dos.flush();
                if(idUser == 0)
                    System.out.println("Username or password is invalid!\n");
            }while(idUser==0);
        
            this.idUser = idUser;
            System.err.println("Log In Successfully");
            System.err.println("UserName is " + idUser);
        
        } catch (IOException ex) {
            Logger.getLogger(LogInThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogInThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LogInThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

public class Server {
    public int port;
    public static List<Socket> clients;

    public Server(int port) {
        this.port = port;
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        String hostName = "localhost";
        String sqlInstance = "MSSQLSERVER";
        String dbName = "ChatRoom";
        String userName = "sa";
        String password = "cuong300599";
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String conStr = "jdbc:sqlserver://"+hostName+":1433"+";instance="+sqlInstance+";databaseName="+dbName;
        Connection conn = DriverManager.getConnection(conStr, userName, password);
        return conn;
    }
    
    public static int findUserById(String userName, String password) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();
        String cmd = "SELECT * FROM Users WHERE UserName = ? AND Password = ?";
        PreparedStatement ps = conn.prepareStatement(cmd);
        ps.setString(1, userName);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        int id = 0;
        if(rs.next()){
            id = rs.getInt(1);
        }
        
        conn.close();
        return id;
    }
    
    public static int logIn(Socket client) throws IOException, ClassNotFoundException, SQLException{
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        DataInputStream dis = new DataInputStream(client.getInputStream());
        
        int idUser = 0;
        do{
            String userName = dis.readUTF();
            String password = dis.readUTF();
            idUser = findUserById(userName, password);
            dos.writeInt(idUser); dos.flush();
            if(idUser == 0)
                System.out.println("Username or password is invalid!\n");
        }while(idUser==0);
        
        System.err.println("Log In Successfully");
        System.err.println("UserName is " + idUser);
        return idUser;
    }
    
    public static boolean findRoomById(int id) throws ClassNotFoundException, SQLException{
        Connection conn = getConnection();
        String cmd = "SELECT * from Room WHERE IdRoom = ?";
        PreparedStatement ps = conn.prepareStatement(cmd);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            conn.close();
            return true;
        }
        else{
            conn.close();
            return false;
        }    
    }
    
    public static String createNewRoom(String roomName) throws ClassNotFoundException, SQLException{
        String roomInfo = "";
        Connection conn = getConnection();
        String cmd = "INSERT INTO Room VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(cmd);
        ps.setString(1, roomName);
        ps.execute();
        
        String cmd1 = "SELECT * FROM ROOM WHERE RoomName = ?";
        PreparedStatement ps1 = conn.prepareStatement(cmd1);
        ps1.setString(1, roomName);
        ResultSet rs = ps1.executeQuery();
        if(rs.next()){
            int idRoom = rs.getInt(1);
            String name = rs.getString(2);
            roomInfo += "Id Room is " + idRoom + " and room name is " + name;
            roomInfo += "Please wait until other clients enter this room!\n";
        }
        
        conn.close();
        return roomInfo;
    }
    
    public static int handleTasks(Socket client) throws IOException, ClassNotFoundException, SQLException{
        Scanner sc = new Scanner(System.in);
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        DataInputStream dis = new DataInputStream(client.getInputStream());
        
        int choice = 0;
        do{
            choice = dis.readInt();
            System.out.println("Client choice is " + choice);
            switch(choice){
                case 1:{
                    int idRoom = dis.readInt();
                    System.out.println("Find room with ID=" + idRoom);
                    boolean available  = findRoomById(idRoom);
                    System.out.println("Room is available: " + available);
                    dos.writeBoolean(available); dos.flush();
                    if(available)
                        return idRoom;
                    else
                        return -1;
                }
                case 2:{
                    String roomName = dis.readUTF();
                    String roomInfo = createNewRoom(roomName);
                    dos.writeUTF(roomInfo); dos.flush();
                    return 2;
                }
                default:{
                    break;
                }
            }
        }while(choice!=0);
        return 0;
    }
    
    public void execute() throws ClassNotFoundException, SQLException{
        try {
            ServerSocket server = new ServerSocket(port);
            WriteThreadServer writer = new WriteThreadServer();
            writer.start();
            while(true){
                Socket client = server.accept();
                System.out.println("Connect to client " + client);
                clients.add(client);
                // LogIn
                int idUser = logIn(client);
//                LogInThread logThread = new LogInThread(client);
//                logThread.start();
//                logThread.join();
//                int idUser = logThread.getIdUser();
                
                // Handle request
                int result = handleTasks(client);
                if(result!=-1 && result != 0 && result != 2){
                    int idRoom = result;
                    ReadThreadServer listener = new ReadThreadServer(client, idUser, idRoom);
                    listener.start();
                }
                else if(result == 2)
                    return;
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }catch(ClassNotFoundException | SQLException ex){
            System.out.println("Something went wrong");
            System.out.println(ex.toString());
        }catch(Exception ex){
            System.out.println("Please enter a valid format!");
        }
    }
    
    public static void main(String[] args) {
        try {
            Server server = new Server(123);
            Server.clients = new ArrayList<>();
            server.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}