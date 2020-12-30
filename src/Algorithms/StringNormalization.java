/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Viết chương trình thực hiện chuẩn hoá một xâu ký tự nhập từ bàn phím (loại bỏ các dấu cách thừa, 
// chuyển ký tự đầu mỗi từ thành chữ hoa, các ký tự khác thành chữ thường)
package Algorithms;

import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */
public class StringNormalization {
    private static Scanner sc = new Scanner(System.in);
    
    public static String removeUselessSpace(String input){
        input = input.trim();
        input = input.replaceAll("\\s+", " ");
        return input;
    }
    
    public static String uppercaseFirstLetter(String input){
        String norm = "";
        String[] words = input.split(" ");
        
        for (int i=0; i<words.length;i++) {
            norm += String.valueOf(words[i].charAt(0)).toUpperCase() + words[i].substring(1).toLowerCase();
            if(i < words.length-1){
                norm += " ";
            }
        }
        
        return norm;
    }
    
    public static String normalizeString(String input){
        String norm = removeUselessSpace(input);
        norm = uppercaseFirstLetter(norm);
        return norm;
    }
    
    public static void main(String[] args) {
        try{
            while(true){
                System.out.print("Enter a string: ");
                String inputStr = sc.nextLine();
                System.out.println("Normalization String: " + normalizeString(inputStr));
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
}