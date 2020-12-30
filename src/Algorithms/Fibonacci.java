/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */
// Nhập một số tự nhiên n. Hãy liệt kê các số Fibonaci nhỏ hơn n là số nguyên tố. 
// Liệt kê n số Fibonaci đầu tiên.
public class Fibonacci {
    public static String findFiboRange(int n){
        String fiboRange = "";
        int num1=0, num2=1;
        int i=1;
        while(i <= n){
            fiboRange += num1 + " ";
            int sum = num1 + num2;
            num1 = num2;
            num2 = sum;
            i++;
        }
        return fiboRange;
    }
    
    public static boolean isPrimeNum(int n){
        if(n <=1) return false;
        
        for(int i=2; i<n;i++){
            if(n%i==0)
                return false;
        }
        
        return true;
    }
    
    public static String findFiboPrimeRange(int n){
        String result="";
        int i=1, num1=0, num2=1;
        while(i<=n){
            if(isPrimeNum(num1) && num1 < n){
                result += num1 + " ";
            }
            int sum = num1+num2;
            num1 = num2;
            num2 = sum;
            i++;
        }
        return result;
    }
    
    public static void main(String[] args) {
        try{
            while(true){
                try{
                    Scanner sc = new Scanner(System.in);
                    System.out.print("n = ");
                    int n = sc.nextInt(); sc.nextLine();
                    if(n<=0) throw new ArithmeticException();
                    
                    System.out.println("Fibo range: ");
                    String fiboRange = findFiboRange(n);
                    System.out.println(fiboRange);

                    System.out.println("Fibo prime numbers less than " + n + ": ");
                    String primeFibo = findFiboPrimeRange(n);
                    System.out.println(primeFibo);
                    System.out.println();
                }
                catch(ArithmeticException  a){
                    System.out.println("n phai lon hon hoac bang 0!");
                }
                catch(Exception e){
                    System.out.println("Vui long nhap dung dinh dang");
                }
                
            }
            
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
}