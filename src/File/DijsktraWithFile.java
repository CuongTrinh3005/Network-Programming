/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Quoc Cuong
 */

// Find the shortest path
public class DijsktraWithFile {
    private static int max=15, n;
    private static int graph[][] = new int[max][max];
    private static Scanner sc = new Scanner(System.in);

    public static void readFile() throws FileNotFoundException, IOException{
        FileReader fr = new FileReader("D:/dijsktra.txt");
        BufferedReader br = new BufferedReader(fr);
        
        n = Integer.valueOf(br.readLine());
        System.out.println("n = " + n);
        
        // Init value for matrix 
        for(int i=1; i<=n;i++){
            graph[i][i] = 0;
            for(int j=i+1; j<=n;j++){
                graph[i][j] = graph[j][i] = Integer.MAX_VALUE;
            }
        }
        
        String line;
        while((line = br.readLine()) != null){
            String[] values = line.split(" ");
            int i = Integer.valueOf(values[0]);
            int j = Integer.valueOf(values[1]);
            graph[i][j] = graph[j][i] = Integer.valueOf(values[2]);
        }
        
        System.out.println("After reading file, we have a matrix: ");
        printMatrix(n);
    }
    
    public static void printMatrix(int n){
        for(int i=1; i<=n;i++){
            for(int j=1; j<=n;j++){
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public static String dijsktra(int source){
        String shortestPath = "";
        int visited[] = new int[max];
        int before[] = new int[max];
        int dist[] = new int[max];
        
        // Init values for these matrixes
        for(int i=1; i<=n; i++){
            dist[i] = graph[source][i];
            before[i] = source;
            visited[i] = 0;
        }
        
        // Visit the source vertex
        visited[source] = 1;
        
        for(int i=1; i<=n-1;i++){
            int u=0, temp=Integer.MAX_VALUE;
            for(int j=1;j<=n;j++){
                if(visited[j] == 0 && dist[j]<temp){
                    u = j;
                    temp = dist[j];
                }
            }
            if(u==0) break;
            visited[u] = 1;
            
            // Update the distance of v adjacent u
            for(int v=1; v<=n;v++){
                if(visited[v]==0 && dist[v]>dist[u]+graph[u][v] && graph[u][v] != Integer.MAX_VALUE && dist[u]!=0){
                    dist[v] = dist[u] + graph[u][v];
                    before[v] = u;
                }    
            }
        }
        
        // Print paths
        shortestPath += "\nShortest Paths from " + source + ": \n";
        for(int i=1; i<=n;i++){
            if(i != source){
                shortestPath += source + " --> " + i + " : ";
                if(dist[i] == Integer.MAX_VALUE){
                    shortestPath += " Not found path \n";
                }
                else{
                    int stack[] = new int[max], top=-1;
                    stack[++top] = i;
                    int front = i;
                    do{
                        front = before[front];
                        stack[++top] = front;
                    }while(front != source);
                    
                    while(top != -1){
                        shortestPath += stack[top--] + " ";
                    }
                    shortestPath += " ,min distance is " + dist[i] + '\n';
                }
            }
        }
        
        return shortestPath;
    }
    
    public static void main(String[] args) throws IOException {
        try{
            readFile();
        }
        catch(Exception e){
            System.out.println("Loi doc file");
        }
        while(true){
            try{
                System.out.print("\nDinh bat dau = ");
                int source = sc.nextInt(); sc.nextLine();
                if(source <= 0 || source > n) throw new ArithmeticException();

                System.out.println(dijsktra(source));
            }
            catch(InputMismatchException i){
                System.out.println("Vui long nhap dinh la so nguyen lon hon 0 !");
                sc.next();
            }
            catch(ArithmeticException i){
                System.out.println("Vui long nhap dinh la so nguyen lon hon 0 va nho hon tong so dinh!");
            }
            catch(Exception e){
                System.out.println("Loi doc file");
            }
        }
    }
}
