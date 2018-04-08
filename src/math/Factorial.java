package math;

/**
 * Created by mrahman on 04/02/18.
 */
public class Factorial {

    public static void main(String[] args) {
        /*
         * Factorial of 5! = 5 x 4 X 3 X 2 X 1 = 120.
         * Write a java program to find Factorial of a given number using Recursion as well as Iteration.
         *
         */
        int n = 10;
        System.out.println("The factorial of "+n +" is "+ factorial(n));

    }
    public static int factorial(int n){
        if(n==0) return 1;
        else return n*factorial(n-1);
    }
}