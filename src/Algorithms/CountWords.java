/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */
public class CountWords {
    public static void main(String[] args) {
        System.out.println("Please enter a sentence: ");
        Scanner sc = new Scanner(System.in);
        String sen = sc.nextLine();
        
        int num_of_words = sen.trim().split("\\s+").length;
        System.out.println("This sentence has " + num_of_words + " words !");
    }
}