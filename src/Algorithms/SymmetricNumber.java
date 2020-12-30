/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Bài 5: Một số được gọi là số thuận nghịch độc nếu ta đọc từ trái sang phải hay từ phải sang trái số đó ta vẫn nhận được 
// một số giống nhau. Hãy liệt kê tất cả các số thuận nghịch độc có sáu chữ số (Ví dụ số: 336633). 
package Algorithms;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */
public class SymmetricNumber {
    public static String listSysmetricNumbers(){
        int count=0; String result="";
        for(int i=100000; i<1000000;i++){
            if(isSymmetric(i)){
                result += i + "\n";
                count++;
            }
        }
        result += "\nTotal are " + count + " numbers! ";

        return result;
    }
    
    private static boolean isSymmetric(int n){
        if(n < 0) n = Math.abs(n);
        
        String str = String.valueOf(n);
        int middleIndex = str.length() / 2;
        
        for(int i=0;i<middleIndex;i++){
            if(str.charAt(i) != str.charAt(str.length()-1-i))
                return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        try{
            System.out.println("Sysmetric Numbers are: ");
            System.out.println(listSysmetricNumbers());
        }
            
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}