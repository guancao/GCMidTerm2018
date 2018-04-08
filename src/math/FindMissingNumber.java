package math;

/**
 * Created by mrahman on 04/22/17.
 */
public class FindMissingNumber {

    public static void main(String[] args) {
        /*
         * If n = 10, then array will have 9 elements in the range from 1 to 10.
         * For example {10,2,1, 4, 5, 3, 7, 8, 6}. One number will be missing in array (9 in this case).
         * Write java code to find the missing number from the array. Write static helper method to find it.
         */
        int[] array = new int[]{10, 2, 1, 4, 5, 3, 7, 8, 6};
        System.out.println("The missing number is :: " + findMissing(array));

    }

    public static int findMissing(int[] array) {
        int[] helper = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int sum = 0;
        for (int i = 0; i < helper.length; i++) {
            sum += helper[i];
        }
        for (int j = 0; j < array.length; j++) {
            sum -= array[j];
        }
        return sum;
    }
}
