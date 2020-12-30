/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Bài 4: Viết chương trình liệt kê tất cả các số nguyên tố nhỏ hơn n cho trước. 
package Algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */
public class PrimeList {
    public static String listPrimeFactors(int n){
        String result="";
        for(int i=1;i<=n;i++){
            if(isPrime(i)){
                result += i;
                result += " ";
            }
        }
        return result ;
    }
    
    public static String listFirstPrimeNum(int n){
        String result = "";
        int count=0, i=1;
        while(count<n){
            if(isPrime(i)){
                result += i + " ";
                count++;
            }
            i++;
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        while(true){
            try{
                Scanner sc = new Scanner(System.in);
                System.out.print("n=");
                int n = sc.nextInt(); sc.nextLine();
                if(n<=0)    throw new ArithmeticException();
                
                String primeFactors = listPrimeFactors(n);
                System.out.println("List of prime numbers less than n: " + primeFactors);
                
                String firstNPrime = listFirstPrimeNum(n);
                System.out.println("List of the first n numbers: " + firstNPrime);
            }
            catch(ArithmeticException i){
                System.out.println("Nhap n lon hon 0!!");
            }
            catch(InputMismatchException i){
                System.out.println("Co the phai nhap dung dinh dang cua so!");
            }
            catch(Exception ex){
                System.out.println(ex.toString());
            }
        }       
    }

    private static boolean isPrime(int n) {
        if(n<=1) return false;
        for(int i=2; i<n; i++){
            if(n % i == 0)
                return false;
        }
        return true;
    }
}