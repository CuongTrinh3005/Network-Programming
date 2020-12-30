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
public class ConvertBase {
    public static String convertBase(int n, int base){
        String numStr = "";
        while(n>0){
            int remainder = n % base;
            boolean addedChar = false;
            
            switch(remainder){
                case 10:{
                    numStr += "A";
                    addedChar = true;
                    break;
                }
                case 11:{
                    numStr += "B";
                    addedChar = true;
                    break;
                }
                case 12:{
                    numStr += "C";
                    addedChar = true;
                    break;
                }
                case 13:{
                    numStr += "D";
                    addedChar = true;
                    break;
                }
                case 14:{
                    numStr += "E";
                    addedChar = true;
                    break;
                }
                case 15:{
                    numStr += "F";
                    addedChar = true;
                    break;
                }
                case 16:{
                    numStr += "G";
                    addedChar = true;
                    break;
                }
                case 17:{
                    numStr += "H";
                    addedChar = true;
                    break;
                }
                case 18:{
                    numStr += "I";
                    addedChar = true;
                    break;
                }
                case 19:{
                    numStr += "K";
                    addedChar = true;
                    break;
                }
                case 20:{
                    numStr += "L";
                    addedChar = true;
                    break;
                }
                case 21:{
                    numStr += "M";
                    addedChar = true;
                    break;
                }
                case 22:{
                    numStr += "N";
                    addedChar = true;
                    break;
                }
                case 23:{
                    numStr += "O";
                    addedChar = true;
                    break;
                }
                default:{
                    break;
                }
            }
            if(!addedChar) numStr += remainder;
            n /= base;
        }

        StringBuilder reverseStr = new StringBuilder();
        reverseStr.append(numStr);
        numStr = reverseStr.reverse().toString();
        return numStr;
    }
    
    public static void main(String[] args) {
        while(true){
            try{
                int n, base;
                Scanner sc = new Scanner(System.in);
                System.out.println("\nEnter a number: ");
                n = sc.nextInt(); sc.nextLine();

                System.out.println("\nEnter base to convert: ");
                base = sc.nextInt(); sc.nextLine();
                if(base <= 1 || base > 24) throw new ArithmeticException();

                String result = ConvertBase.convertBase(n, base);
                System.out.println("\nResult:" + result);
            }
            catch(ArithmeticException  a){
                System.out.println("Co so chuyen doi phai lon hon 1 va nho hon hoac bang 24");
            }
            catch(InputMismatchException i){
                System.out.println("Vui long nhap so nguyen");
            }
            catch(Exception e){
                System.out.println("Error");
                System.out.println(e.toString());
            }
        }
    }
}