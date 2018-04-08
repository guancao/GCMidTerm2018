package math;

import databases.ConnectDB;

import java.util.ArrayList;
import java.util.List;

public class LowestNumber {
	private static final String URL = "jdbc:mysql://localhost:3306/selproj";
	private static final String USER = "guan";
	private static final String PASSWORD = "abcd1234";

	public static void main(String[] args) {
		/*
		 * Write java solution to find the lowest number from this array.
		 * Use one of the databases from mysql or mongodb to store and to retrieve.
		 */
		int  array[] = new int[]{211,110,99,34,67,89,67,456,321,456,78,90,45,32,56,78,90,54,32,123,67,5,679,54,32,65};

		//find lowest number from the array
		int number = lowestNumber(array);
		System.out.println(number);

		ConnectDB connectDB = new ConnectDB();
		List<String> lowestValue = new ArrayList<String>();
		try {
			connectDB.insertDataFromArrayToMySql(array, "tbl_lowestNumber", "column_lowestNumber");
			lowestValue = connectDB.readDataBase("tbl_lowestNumber", "column_lowestNumber");

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Data is reading from the Table (tbl_lowestNumber) and displaying to the console");
		for(String st:lowestValue){
			System.out.println(st);
		}
	}
	//method to find lowest number in array
	public static int lowestNumber(int[] array) {
		int min = array[0];
		for (int i = 1; i < array.length; i++) {
			if (min > array[i]) min = array[i];
		}
		return min;
	}


}
