/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */
public class ListDivisors {
    private static List<Integer> divisors;
    public static void findDivisors(int n){
        divisors = new ArrayList<>();
        for(int i=1; i<=n;i++){
            if(n%i==0){
                divisors.add(i);
            }
        }
    }
    
    public static void findNumDivisor(){
        System.out.println("Total divisors: " + divisors.size());
    }
    
    public static void listDivisors(){
        for (Integer divisor : divisors) {
            System.out.print(divisor + " ");
        }
    }
    
    public static void main(String[] args) {
        try{
            while(true){
                try{
                    int n;
                    Scanner sc = new Scanner(System.in);
                    System.out.print("n = ");
                    n = sc.nextInt(); sc.nextLine();
                    if(n<=0) throw new ArithmeticException();

                    findDivisors(n);
                    findNumDivisor();
                    listDivisors();
                    
                    System.out.println();
                }
                catch(ArithmeticException a){
                    System.out.println("Vui long nhap so nguyen lon hon 0 !");
                }
                catch(Exception ex){
                    System.out.println("Vui long nhap so dung dinh dang!");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}