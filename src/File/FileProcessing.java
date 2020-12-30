/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quoc Cuong
 */
public class FileProcessing {
    public static String readFile(String fileName) throws FileNotFoundException, IOException{
        String content="";
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        
        String line;
        try{
            while((line=br.readLine()) != null){
                content += line;
                content += '\n';
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return content;
    }
    
    public static String readFileStream(String fileName) throws FileNotFoundException{
        String content="";
        FileInputStream fis = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        try{
            while((line=br.readLine()) != null){
                content += line;
                content += '\n';
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return content;
    }
    
    public static Object[] convertToSorted1DArr(String content){
        ArrayList<Integer> arrNum1D = new ArrayList<>();
        String[] contentLine = content.split("\n");
        
        for(String line : contentLine){
            String[] digits = line.split("\\s+");
            for(String digit:digits){
                arrNum1D.add(Integer.valueOf(digit));
            }
        }
        
        Collections.sort(arrNum1D);
        return arrNum1D.toArray();
    }
    
    public static int getNumRows(String content){
        String[] contentLine = content.split("\n");
        return contentLine.length;
    }
    
    public static String getSortedMatrix(String content){
        String result=""; 
        int rows = getNumRows(content);
        Object[] arr1d = convertToSorted1DArr(content);
        int cols = arr1d.length / rows;
        int[][] arr2d = new int[rows][cols];

        System.out.println("\nMatrix after sorting: ");
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                arr2d[i][j] = (int) arr1d[(i*cols)+j];
                result += arr2d[i][j] + " ";
            }
            result += '\n';
        }
        return result;
    }
    
    public static String moveFile(String filePath, String destinationPath) throws IOException{
//        String message = "";
//        
//        File file = new File(filePath);
//        if(file.exists() && !file.isDirectory()){
//            file.renameTo(new File(destinationPath));
//            message = "Move file successfully!";
//        }
//        else if(file.isDirectory()){
//            if (file.isDirectory())
//            {
//                for (File f : file.listFiles())
//                {
//                    f.renameTo(new File(destinationPath));
//                }
//            }
//            message = "Move directory successfully!";
//        }
//        else{
//            message = "Move file failed!";
//        }
//        return message;

        String result="";
        File file = new File(filePath);
        if(file.exists() && !file.isDirectory()){
            System.out.println("File name is: " + file.getName());
            // Move file
            file.renameTo(new File(destinationPath));
            result = "Move file successfully !";
        }
        else{
            result = "File does not exist";
        }
        return result;
    }
    
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter file path: ");
            String filePath = sc.nextLine().trim();
            
            // Read File
            System.out.println("Read file line by line: \n");
            System.out.println(readFile(filePath));
            System.out.println("Read file line by line with stream: \n");
            System.out.println(readFileStream(filePath));
            
            System.out.println(getSortedMatrix(readFile(filePath)));
            
            // Move file
            System.out.print("Moving file: \n");
            System.out.print("Enter destination file path to move: ");
            String newPath = sc.nextLine();
            String result = moveFile(filePath, newPath);
            System.out.println(result);
        } catch (IOException ex) {
            Logger.getLogger(FileProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}