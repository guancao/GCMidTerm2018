package algorithm;

import org.testng.Assert;

public class UnitTestSorting {

    /*
      This class is about Unit testing for Sorting Algorithm.
     */
    public static void main(String[] args) {
        int [] unSortedArray = {6,9,2,5,1,0,4};
        int [] sortedArray =   {0,1,2,4,5,6,9};
        //Create Sort object
        Sort sort = new Sort();
        //apply unsorted array to selectionSort.
        sort.selectionSort(unSortedArray);
        //verify if the unsorted array is sorted by the selection sort algorithm.
        try {
            Assert.assertEquals(sortedArray, unSortedArray, "Array is not Sorted");
        }catch(Exception ex){
            ex.getMessage();
        }

        //Now do for rest of the algorithm...................below
        int [] unSortedArray2 = {6,9,2,5,1,0,4};
        int [] unSortedArray3 = {6,9,2,5,1,0,4};
        int [] unSortedArray4 = {6,9,2,5,1,0,4};
        int [] unSortedArray5 = {6,9,2,5,1,0,4};
        sort.bucketSort(unSortedArray2);
        sort.mergeSort(unSortedArray3);
        sort.bubbleSort(unSortedArray4);
        sort.quickSort(unSortedArray5);
        try{
            Assert.assertEquals(sortedArray, unSortedArray2, "bucketsort is a bust");
            Assert.assertEquals(sortedArray, unSortedArray3, "merge sort is a bust");
            Assert.assertEquals(sortedArray, unSortedArray4, "bubble sort is a bust");
            Assert.assertEquals(sortedArray, unSortedArray5, "quick sort is a bust");

        }catch(Exception e){
            e.getMessage();
        }


    }
}
