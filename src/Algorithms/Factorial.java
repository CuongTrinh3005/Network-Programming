/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Viết chương trình liệt kê tất cả các hoán vị của 1, 2, .., n. 
package Algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */
public class Factorial {
    private static Scanner sc = new Scanner(System.in);
    
    public static long findFactorial(long n){
        if(n <= 0) throw new ArithmeticException();
        
        long i=1, fact = 1;
//        while(i<=n){
//            fact *= i;
//            i++;
//        }
        for(i=1; i<=n; i++)
            fact *= i;
        
        return fact;
    }
    
    public static void main(String[] args) {
        while(true){
            try{
                System.out.print("n = ");
                long n = sc.nextLong(); sc.nextLine();
                if(n <= 0) throw new ArithmeticException();
                
                for(long i=1; i<=n;i++){
                    System.out.println(i + "! = " + findFactorial(i)) ;
                }
                System.out.println();
            }
            catch(InputMismatchException i){
                System.out.println("Vui long nhap so nguyen dung dinh dang !");
                sc.next();
            }
            catch(ArithmeticException a){
                System.out.println("Vui long nhap so nguyen lon hon 0 !");
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }
}