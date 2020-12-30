/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;
//Bài 3: Viết chương trình phân tích một số nguyên thành các thừa số nguyên tố 
// Ví dụ: Số 28 được phân tích thành 2 x 2 x 7. 

import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */
public class PrimeFactors {
    public static List<Integer>splitIntoPrimeFactors(int n){
        List<Integer> arrFactors = new ArrayList<>();
        // Prime is even, only is 2
        while(n % 2 == 0){
            arrFactors.add(2);
            n /= 2;
        }
        
        // Prime is odd
        for(int i=3; i<= n; i+=2){
            while(n % i == 0){
                arrFactors.add(i);
                n /= i;
            }
        }
        
        return arrFactors;
    }
    
    public static String collectResult(List<Integer> arrFactors){
        String result = "Prime elements: ";
        for(Integer factor:arrFactors)
            result += factor  + " ";
        
        return  result;
    }
    
    public static void main(String[] args) {
        try{
            while(true){
                try{
                    Scanner sc = new Scanner(System.in);
                    System.out.print("n = ");
                    int n = sc.nextInt(); sc.nextLine();
                    if(n<=0) throw new ArithmeticException();
                    List<Integer> factors = splitIntoPrimeFactors(n);
                    System.out.println(collectResult(factors));
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