/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Nhập số liệu cho dãy số thực a0, a1,..., an-1. Hãy liệt kê các phần tử xuất hiện trong dãy đúng một lần. 
// Nhập số liệu cho dãy số thực a0, a1,..., an-1. Hãy liệt kê các phần tử xuất hiện trong dãy đúng 2 lần. 
// Nhập số liệu cho dãy số thực a0, a1,..., an-1. In ra màn hình số lần xuất hiện của các phần tử.
package Algorithms;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */
public class Appearances {
    private static List<Integer> arr;
    private static Scanner sc = new Scanner(System.in);
    
    public static void getInput(){
        try{
            System.out.print("Enter number of items: ");
            int n = sc.nextInt();
            sc.nextLine();
            if(n <=0) throw new ArithmeticException();
                
            arr = new ArrayList<>();
            for(int i=0; i<n;i++){
                System.out.print("Enter items " + i + "= ");
                int value = sc.nextInt(); sc.nextLine();
                arr.add(value);
            }
        }
        catch(InputMismatchException i){
            System.out.println("Vui long nhap dung du lieu!");
        }
        catch(ArithmeticException a){
            System.out.println("Vui long nhap so lon hon 0!!");
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    public static int countAttendance(int value){
        int count = 0;
        for(int i=0;i<arr.size();i++){
            if(arr.get(i) == value)
                count++;
        }
        return count;
    }
    
    public static List<Integer> getUniqueItems(){
        List<Integer> saveTraverse = new ArrayList<>();
        for(int i=0;i<arr.size();i++){
            int value = arr.get(i);
            if(!saveTraverse.contains(value)){
                saveTraverse.add(value);
            }
        }
        return saveTraverse;
    }
    
    public static String listOneAppearance(){
        System.out.println("Items appearing one time:\n");
        String result = "";
        for(int i=0;i<arr.size();i++){
            if(countAttendance(arr.get(i)) == 1){
                result += arr.get(i) + " ";
            }
        }
        return result;
    }
    
    public static String listTwoAppearance(){
        List<Integer> uniqueItems = getUniqueItems();
        String result = "";
        
        System.err.println("Items appearing twice:");
        for(int i=0;i<uniqueItems.size();i++){
            if(countAttendance(uniqueItems.get(i)) == 2 ){
                result += uniqueItems.get(i) + " ";
            }
        }
        return result;
    }
    
    public static String listAllAppearance(){
        List<Integer> uniqueItems = getUniqueItems();
        
        String result="";
        System.err.println("Items with their appearances:");
        for(int i=0;i<uniqueItems.size();i++){
            result += uniqueItems.get(i) + " appears " + countAttendance(uniqueItems.get(i)) + " times\n";
        }
        return result;
    }
    
    public static void main(String[] args) {
        try{
            getInput();
            System.out.println(listOneAppearance());
            System.out.println(listTwoAppearance());
            System.out.println(listAllAppearance());
            }
        catch(Exception e){
            
        }
    }
}