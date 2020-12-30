/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;
// Hãy viết chương trình tính tổng các chữ số của một số nguyên bất kỳ. Ví dụ: Số 8545604 có tổng các chữ số 
// là: 8+5+4+5+6+0+4= 32.  

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */
public class SumDigits {
    public static int figureSum(int num){
        int sum = 0;
        if(num < 0) num = Math.abs(num);
        
        while(num > 0){
            int remainder  = num %  10;
            sum += remainder;
            num /= 10;
        }
        
        return  sum;
    }
    
    public static void main(String[] args) {
        while(true){
            try{
                Scanner sc = new Scanner(System.in);
                System.out.print("\nEnter a number: ");
                int num = sc.nextInt();
                sc.nextLine();
                
                System.out.println("Sum of digits of this number: " + figureSum(num));
            }
            catch(InputMismatchException i){
                System.out.println("Vui long nhap so nguyen dung dinh dang!");
            }
            catch(Exception e){
                System.out.println("Da co loi vui long thu lai");
            }
        }
    }
}
