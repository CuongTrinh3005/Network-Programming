/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Viết chương trình nhập vào vào ma trận A có n dòng, m cột, các phần tử là những số nguyên lớn hơn 0 và nhỏ hơn 100 được nhập vào từ bàn phím. Thực hiện các chức năng sau:
//Tìm phần tử lớn nhất của ma trận cùng chỉ số của số đó.
//Tìm và in ra các phần tử là số nguyên tố của ma trận (các phần tử không nguyên tố thì thay bằng số 0).
//Sắp xếp tất cả các cột của ma trận theo thứ tự tăng dần và in kết quả ra màn hình.

package Algorithms;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */
public class Matrix {
    public static int[][] input(int n, int m){
        int arr[][] = new int[n][m];
        Scanner sc= new Scanner(System.in);
        
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                System.out.print("arr[" + i + "][" + j + "] = ");
                arr[i][j] = sc.nextInt(); sc.nextLine();
                if(arr[i][j] < 0 || arr[i][j] > 100) throw new ArithmeticException();
            }
        }
        return arr;
    }
    
    public static String showMatrix(int arr[][], int n, int m){
        String result = "\nThe matrix is: \n";
        System.out.println("");
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                result += arr[i][j] + " ";
            }
            result += "\n";
        }
        
        return result;
    }
    
    public static String findLargestNum(int arr[][], int n, int m){
        String result="";
        int biggest = 0, indexRow=0, indexCol=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(arr[i][j] > biggest){
                    biggest = arr[i][j];
                    indexRow = i;
                    indexCol = j;
                }
            }
        }
        result += "\nBiggest Value is " + biggest + " at row " + (indexRow+1) + " and column " + (indexCol+1);
        
        return result;
    }
    
    public static boolean isPrimeNum(int value){
        if(value<=1)
            return false;
        
        for(int i=2;i<value;i++){
            if(value % i == 0)
                return false;
        }
        
        return true;
    }
    
    public static String showPrimeValue(int arr[][], int n, int m){
        String result = "\nThe prime numbers: \n";
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(isPrimeNum(arr[i][j])){
                    result += arr[i][j] + " ";
                }
                else result += 0 + " ";
            }
            result += "\n";
        }
        
        return result;
    }
    
    public static int[][] sortColumnsProperly(int[][] arr, int n, int m) {
        // Sort each colum.
        for (int col = 0; col < arr[0].length; col++) {
            // Pull the column out.
            int[] thisCol = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                thisCol[i] = arr[i][col];
            }
            // Sort it.
            Arrays.sort(thisCol);
            // Push it back in.
            for (int i = 0; i < arr.length; i++) {
                arr[i][col] = thisCol[i];
            }
        }
        return arr;
    }
    
    public static void main(String[] args) {
        try{
            while(true){
                try{
                    Scanner sc = new Scanner(System.in);
                    System.out.print("number of rows = ");
                    int row = sc.nextInt(); sc.nextLine();
                    if(row <= 0) throw new ArithmeticException();
                    
                    System.out.print("number of columns = ");
                    int col = sc.nextInt(); sc.nextLine();
                    if(col <= 0) throw new ArithmeticException();

                    int[][] arr;
                    arr = input(row, col);
                    System.out.println(showMatrix(arr, row, col));
                    System.out.println(findLargestNum(arr, row, col));
                    System.out.println(showPrimeValue(arr, row, col));
                    System.out.println("After sorting columns:");
                    System.out.println(showMatrix(sortColumnsProperly(arr, row, col), row, col));
                }
                catch(ArithmeticException a){
                    System.out.println("Vui long nhap so nguyen lon hon 0 va nho hon hoac bang 100!");
                }
                catch(InputMismatchException a){
                    System.out.println("Vui long nhap so nguyen dung dinh dang !");
                }
                catch(Exception e){
                    System.out.println(e.toString());
                }
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
}