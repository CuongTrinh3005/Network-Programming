/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.DoQuiz;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Quoc Cuong
 */
public class CountDownTimer {
    private static int interval=1;
    private static Timer timer;
    private static Scanner sc = new Scanner(System.in);
    
    public static final int setInterval(){
        if(interval==1) timer.cancel();
        return --interval;
    }
    
    public static void createTask(){
        int delay = 1000, period=1000;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(setInterval());
            }
        };
        timer.scheduleAtFixedRate(task, delay, period);
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Input seconds => : ");
        String secs = sc.nextLine();
        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval = Integer.parseInt(secs);
        System.out.println(secs);
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                System.out.println(setInterval());

            }
        }, delay, period);
    }
}
