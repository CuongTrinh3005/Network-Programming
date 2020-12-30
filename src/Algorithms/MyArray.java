/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Viết chương trình nhập vào vào mảng A có n phần tử, các phần tử là những số nguyên lớn hơn 0 và nhỏ hơn 100 được nhập vào từ bàn phím. Thực hiện các chức năng sau:
// Tìm phần tử lớn nhất và lớn thứ 2 trong mảng cùng chỉ số của các số đó.
// Sắp xếp mảng theo thứ tự giảm dần.
// Nhập một số nguyên x và chèn x vào mảng A sao cho vẫn đảm bảo tính sắp xếp giảm dần.

package Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */
public class MyArray {
    private static ArrayList<Integer> arr;
    private static Scanner sc = new Scanner(System.in);
    
    public static void input(){
        System.out.print("\nNumber of items: ");
        int size = sc.nextInt(); sc.nextLine();
        if(size <= 0) throw new ArrayIndexOutOfBoundsException();
        
        arr = new ArrayList();
        for(int i=0;i<size;i++){
            System.out.print("Item " + i + " = ");
            int value = sc.nextInt(); sc.nextLine();
            if(value<0 || value>100) throw new ArithmeticException();
            arr.add(value);
        }
    }
    
    public static String showArray(){
        String result = "";
        result += "Array's size: " + arr.size() + "\n";
        for (int item : arr) {
            result += item + " ";
        }
        return result;
    }
    
    public static String sortArrDesc(){
        String result="";
        Object[] a = arr.toArray();
        Arrays.sort(a, Collections.reverseOrder());
        for (Object object : a) {
            result += object + " ";
        }
        return result;
    }
    
    public static String findMostAndSecondValue(){
        String result="";
        int biggest = 0, secondBiggest=0, biggestIndex=0, secondBiggestIndex=0;
// Another method.
//        for(Integer item:arr){
//            if(item>biggest){
//                secondBiggest = biggest;
//                secondBiggestIndex = arr.indexOf(secondBiggest);
//                
//                biggest = item;
//                biggestIndex = arr.indexOf(biggest);
//            }
//            else if(item>secondBiggest && item != biggest){
//                secondBiggest = item;
//                secondBiggestIndex = arr.indexOf(secondBiggest);
//            }
//        }

        Object temp[] = arr.toArray();
        Arrays.sort(temp);
        biggest = (int) temp[temp.length-1];
        biggestIndex = arr.indexOf(biggest);
        
        secondBiggest = (int) temp[temp.length-2];
        secondBiggestIndex = arr.indexOf(secondBiggest);
        
        result += "\nThe biggest value is " + biggest + " at index " + biggestIndex;
        result += "\nThe second biggest value is " + secondBiggest + " at index " + secondBiggestIndex;

        return result;
    }
    
    public static void insertItemRemainOrder(){
        System.out.print("Add new item value = ");
        int value = sc.nextInt(); sc.nextLine();
        if(value<0 || value>100) throw new ArithmeticException();
        
        System.out.println("\nBefore inserting: ");
        System.out.println(sortArrDesc());
    
        arr.add(value);
        System.out.println("\nAfter inserting: ");
        System.out.println(sortArrDesc());
    }
    
    public static void main(String[] args) {
        try{
            while(true){
                try{
                    input();
                    System.out.println(showArray());
                    System.out.println("\nSort array descending: ");
                    System.out.println(sortArrDesc());
                    System.out.println("\nFind most and second biggest value: ");
                    System.out.println(findMostAndSecondValue());
                    insertItemRemainOrder();
                }
                catch(ArrayIndexOutOfBoundsException a){
                    System.out.println("Kich thuoc mang phai lon hon 0");
                }
                catch(ArithmeticException a){
                    System.out.println("Nhap so nguyen trong pham vi 0 den 100 !");
                }
                catch(InputMismatchException a){
                    System.out.println("Nhap so nguyen dung dinh dang !");
                    sc.next(); // clear scanner wrong input
                    continue; // // continues to loop if exception is found
                }
                catch(Exception a){
                    System.out.println(a.toString());
                }
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}