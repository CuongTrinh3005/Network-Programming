/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Viết chương trình thực hiện nhập một xâu ký tự và tìm từ dài nhất trong xâu đó. Từ đó xuất hiện ở vị trí nào? 
// (Chú ý. nếu có nhiều từ có độ dài giống nhau thì chọn từ đầu tiên tìm thấy).
package Algorithms;

import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */
public class LongestString {
    private static Scanner sc = new Scanner(System.in);
    
    public static String findLongestStr(String input){
        String[] words = input.trim().split("\\s+");
        String result = "The longest String is ", maxLenStr = "";
        int maxLen = 0;
        for (String word : words) {
            if(word.length()>maxLen){
                maxLen = word.length();
                maxLenStr = word;
            }
        }
        result += maxLenStr + " at index " + input.indexOf(maxLenStr) + " with the length is " + maxLen;
        
        return result;
    }
    
    public static String convertNameOrder(String name){
        name = name.trim();
        String[] words = name.split("\\s+");
        
        String converter = words[words.length-1] + " ";
        for(int i=0;i<words.length-1;i++){
            converter += words[i] + " ";
        }
        
        return converter;
    }
    
    public static void main(String[] args) {
        while(true){
            try{
                System.out.print("Enter a string: ");
                String inputStr = sc.nextLine();

                System.out.println(findLongestStr(inputStr));
                System.out.println();
                System.out.println("Converted Name: " + convertNameOrder(inputStr));
                System.out.println();
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }
}