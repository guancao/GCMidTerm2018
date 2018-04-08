package math;

public class Fibonacci {
    public static void main(String[] args) {
         /*
          Write 40 Fibonacci numbers with java.
         */
        int[] fibonacciSeq = new int[40];
        fibonacciSeq[0] = 1;
        fibonacciSeq[1] = 1;
        for (int i = 2; i < fibonacciSeq.length; i++) {
            fibonacciSeq[i] = fibonacciSeq[i - 1] + fibonacciSeq[i - 2];
        }
        for (int x : fibonacciSeq) System.out.println(x);
    }

}
