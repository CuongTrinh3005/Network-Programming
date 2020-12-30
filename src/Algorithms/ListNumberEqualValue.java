/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */
public class ListNumberEqualValue {
    public static int getSumDigits(int num){
        int sum = 0;
        while(num>0){
            int remainder = num%10;
            sum += remainder;
            num /= 10;
        }
        
        return sum;
    }
    
    public static boolean checkEqual(int value, int num){
        if(getSumDigits(num) == value)
            return true;
        else return false;
//        int sum = 0;
//        while(num > 0){
//            int remainder = num % 10;
//            sum += remainder;
//            num /= 10;
//        }
//        if(sum == value)
//            return true;
//        else return false;
    }
    
    public static String listResult(int value){
        String result = ""; int count = 0;
        for(int i=10000; i<100000; i++){
            if(checkEqual(value, i)){
                result += i + "\n";
                count++;
            }
        }
        result += "\nTotally, we have " + count + " numbers\n";
        
        return result;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a value = ");
        int value = sc.nextInt(); sc.nextLine();
        System.out.println("Results: ");
        
        // number has 5 digits
        System.out.println(listResult(value));
    }
}
