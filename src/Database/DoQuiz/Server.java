/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.DoQuiz;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Quoc Cuong
 */
class Quiz{
    private int ID;
    private String level;
    private String content;
    private String AnsA;
    private String AnsB;
    private String AnsC;
    private String AnsD;
    private String AnsRight;
    private String AnsUser="";

    public Quiz(int ID, String level, String content, String AnsA, String AnsB, String AnsC, String AnsD, String AnsRight) {
        this.ID = ID;
        this.level = level;
        this.content = content;
        this.AnsA = AnsA;
        this.AnsB = AnsB;
        this.AnsC = AnsC;
        this.AnsD = AnsD;
        this.AnsRight = AnsRight;
    }

    public int getID() {
        return ID;
    }

    public String getLevel() {
        return level;
    }

    public String getContent() {
        return content;
    }

    public String getAnsA() {
        return AnsA;
    }

    public String getAnsB() {
        return AnsB;
    }

    public String getAnsC() {
        return AnsC;
    }

    public String getAnsD() {
        return AnsD;
    }

    public String getAnsRight() {
        return AnsRight;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAnsA(String AnsA) {
        this.AnsA = AnsA;
    }

    public void setAnsB(String AnsB) {
        this.AnsB = AnsB;
    }

    public void setAnsC(String AnsC) {
        this.AnsC = AnsC;
    }

    public void setAnsD(String AnsD) {
        this.AnsD = AnsD;
    }

    public void setAnsRight(String AnsRight) {
        this.AnsRight = AnsRight;
    }

    public String getAnsUser() {
        return AnsUser;
    }

    public void setAnsUser(String AnsUser) {
        this.AnsUser = AnsUser;
    }
}

public class Server {
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        String hostName = "localhost";
        String sqlInstance = "MSSQLSERVER";
        String dbName = "KIEMTRALTM";
        String userName = "sa";
        String password = "cuong300599";
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String conString = "jdbc:sqlserver://"+hostName+":1433"+";instance="+sqlInstance+";databaseName="+dbName;
        Connection conn = DriverManager.getConnection(conString, userName, password);
        
        return conn;
    }
    
    private static String LogIn(String userName, String password) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();
        String cmd = "SELECT * from SINHVIEN WHERE USERNAME = ? AND PASSWORD = ?";
        PreparedStatement ps = conn.prepareStatement(cmd);
        ps.setString(1, userName);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        String maSV = "";
        if(rs.next()){
            maSV = rs.getString(1);
        }
        
        conn.close();
        return maSV; 
    }
    
    public static List<Quiz> getQuiz() throws SQLException, ClassNotFoundException{
        Connection conn = getConnection();
        String cmd = "SELECT TOP 10 * FROM BODE ORDER BY NEWID()";
        PreparedStatement ps = conn.prepareStatement(cmd);
        ResultSet rs = ps.executeQuery();
        List<Quiz> questions = new ArrayList<>();
        while(rs.next()){
            int id = rs.getInt(1);
            String level=rs.getString(2);
            String content=rs.getString(3);
            String AnsA=rs.getString(4);
            String AnsB=rs.getString(5);
            String AnsC=rs.getString(6);
            String AnsD=rs.getString(7);
            String AnsRight=rs.getString(8);
            Quiz quiz = new Quiz(id, level, content, AnsA, AnsB, AnsC, AnsD, AnsRight);
            questions.add(quiz);
        }
        
        conn.close();
        return questions;
    }
    
    public static String createQuizForUser(Quiz question, int index){
        String quiz="";
        quiz += index+". "+question.getContent()+"\nA."+question.getAnsA()+"\nB."+question.getAnsB()+"\nC."+
                    question.getAnsC()+"\nD."+question.getAnsD()+"\n";
        
        return quiz;
    }
    
    public static String showResult(List<Quiz> questions, String MASV) throws ClassNotFoundException, SQLException{
        String result="";
        int numOfCorrectAns = 0;
        float scorePerRightAns = (float) Math.round(10.0 / questions.size());
        for (Quiz question : questions) {
            if(question.getAnsRight().equalsIgnoreCase(question.getAnsUser()))
                numOfCorrectAns++;
        }
        float score = Math.round(scorePerRightAns * numOfCorrectAns);
        int lan = getSoLanDaThi(MASV);
        String note = "Thi Lan " + (lan+1);
        long millisenconds = System.currentTimeMillis();
        saveScore(MASV, lan+1, new Date(millisenconds), score , note);
        result += "Your score is " + score + " with " + numOfCorrectAns + " right answers !";
        
        return result;
    }
    
    public static void saveScore(String maSV, int lanThi, Date Ngay, float score, String baiThi) throws ClassNotFoundException, SQLException{
        Connection conn = getConnection();
        String cmd = "INSERT INTO BANGDIEM VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(cmd);
        ps.setString(1, maSV);
        ps.setInt(2,lanThi);
        ps.setString(3, Ngay.toString());
        ps.setFloat(4, score);
        ps.setString(5, baiThi);
        ps.executeUpdate();
        conn.close();
    }
    
    public static int getSoLanDaThi(String MaSV) throws ClassNotFoundException, SQLException{
        int lan=0;
        Connection conn = getConnection();
        String cmd = "SELECT * FROM BANGDIEM WHERE MASV = ?";
        PreparedStatement ps = conn.prepareStatement(cmd);
        ps.setString(1, MaSV);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
            lan++;
        
        conn.close();
        return lan;
    }
    
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(123);
            System.out.println("Server is already!");
            Socket client = server.accept();
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            
            // Login process
            String maSV = "", userName="", password="";
            do{
                userName = dis.readUTF();
                password = dis.readUTF();
                
                maSV = LogIn(userName, password);
                dos.writeUTF(maSV); dos.flush();
            }while("".equals(maSV));
            
            int lanThi = getSoLanDaThi(maSV);
            if(lanThi<2)
                dos.writeBoolean(true); // allow to take exam
            else{
                dos.writeBoolean(false);
                return;
            }
                
            // Exam
            List<Quiz> questions = getQuiz();
            dos.writeInt(questions.size());
            int index=0;
            for (Quiz question : questions) {
                String quiz = createQuizForUser(question, ++index);
                dos.writeUTF(quiz); dos.flush();
                // Receive answer from user
                String ansUser = dis.readUTF();
                questions.get(questions.indexOf(question)).setAnsUser(ansUser);
            }
            // Count score
            String result = showResult(questions, maSV);
            dos.writeUTF(result);
            // Close connection
            client.close();
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception ex){
            System.out.println("Something went wrong, try again!");
            System.out.println(ex.toString());
        }
        
    }
}