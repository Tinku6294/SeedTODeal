/******************************************************************************

 Online Java Compiler.
 Code, Compile, Run and Debug java program online.
 Write your code in this editor and press "Run" button to execute it.

 *******************************************************************************/
import java.io.*;
import java.util.*;

class Main {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter a number :- ");
        int n=sc.nextInt();
//        sc.nextLine();
        System.out.print("Enter "+n+" text");

        String[] arr = new String[n];


        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextLine();
        }

        for( int i =0;i<n;i++){
            System.out.println((i)+" "+arr[i]);
        }

    }
}