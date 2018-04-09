package algorithm;

import databases.ConnectDB;
import databases.MyConnectDB;
import jdk.nashorn.internal.runtime.arrays.ArrayData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static databases.ConnectDB.connect;
import static java.sql.DriverManager.getConnection;

/*
 *Created by mrahman on 04/02/2018.
 */
public class Numbers {

    /*
     * Show all the different kind of sorting algorithm by applying into (num array).
     * Display the execution time for each sorting.Example in below.
     *
     * Use any databases[MongoDB, Oracle, MySql] to store data and retrieve data.
     *
     * At the end. After running all the sorting algo, come to a conclusion which one is suitable on given data set.
     *
     */
    private static final String URL = "jdbc:mysql://localhost:3306/pntproj?serverTimezone=UTC&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "goTO123456";
    private static PreparedStatement ps = null;

    public static void main(String[] args) throws Exception {

        int[] num = new int[100000];
        storeRandomNumbers(num);
        Sort algo = new Sort();
        algo.selectionSort(num);
        for (int x : num) System.out.print(x + " ");
        long selectionSortExecutionTime = algo.executionTime;
        System.out.println("Total Execution Time of " + num.length + " numbers in Selection Sort take: " + selectionSortExecutionTime + " milli sec");
//		ConnectDB connectDB = new ConnectDB();
        try {
            // 1. get a connection to the database, 2. create a statement
            Connection myConn = getConnection(URL, USER, PASSWORD);
            Statement myStmt = myConn.createStatement();

            // 3. execute a SQL query
            // create a new table with name: tbl_primeNumber  -- just run once, if re-run the code, comment this
            String tableName = "tbl_selectionSort";
            String columnName = "selectionSort";
            String columnName2 = "executionTime";
            ps = myConn.prepareStatement("DROP TABLE IF EXISTS `" + tableName + "`;");
            ps.executeUpdate();
            String sql = "CREATE TABLE `" + tableName + "` (`ID` int(11) NOT NULL AUTO_INCREMENT,"
                    + "`" + columnName + "` bigint(20) DEFAULT NULL, " + "`" + columnName2 + "` bigint(20) DEFAULT NULL, PRIMARY KEY (`ID`) );";
            myStmt.execute(sql);

            for (int n = 0; n < num.length; n++) {
                ps = myConn.prepareStatement("INSERT INTO " + tableName + " ( " + columnName + " ) VALUES(?)");
                ps.setInt(1, num[n]);
                ps.executeUpdate();
            }
            ps = myConn.prepareStatement("INSERT INTO " + tableName + " ( " + columnName2 + " ) VALUES(?)");
            ps.setInt(1, (int) selectionSortExecutionTime);
            ps.executeUpdate();
            // myStmt.executeQuery(query);
            ResultSet myRs = ((Statement) myStmt).executeQuery("SELECT * FROM " + tableName);

/*           // 4. process the result set
            System.out.println("The sorted numbers are ::::");
            while (myRs.next()) {
                System.out.print(myRs.getString(columnName) + " ");
            }
*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Selection Sort
//        connectDB.insertDataFromArrayToMySql(num, "selection_sort", "SortingNumbers");
//        List<String> numbers = connectDB.readDataBase("selection_sort", "SortingNumbers");

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num.length; i++) {
            list.add((Integer) num[i]);
        }
//        printIntValue(list);

        int n = num.length;
        randomize(num, n);
        //Insertion Sort
        algo.insertionSort(num);
        long insertionSortExecutionTime = algo.executionTime;
        System.out.println("Total Execution Time of " + num.length + " numbers in Insertion Sort take: " + insertionSortExecutionTime + " milli sec");

        //By following above, Continue for rest of the Sorting Algorithm....
//		ConnectDB connectDB = new ConnectDB();
        try {
            // 1. get a connection to the database, 2. create a statement
            Connection myConn = getConnection(URL, USER, PASSWORD);
            Statement myStmt = myConn.createStatement();

            // 3. execute a SQL query
            // create a new table with name: tbl_primeNumber  -- just run once, if re-run the code, comment this
            String tableName = "tbl_insertionSort";
            String columnName = "insertionSort";
            String columnName2 = "executionTime";
            ps = myConn.prepareStatement("DROP TABLE IF EXISTS `" + tableName + "`;");
            ps.executeUpdate();
            String sql = "CREATE TABLE `" + tableName + "` (`ID` int(11) NOT NULL AUTO_INCREMENT,"
                    + "`" + columnName + "` bigint(20) DEFAULT NULL, " + "`" + columnName2 + "` bigint(20) DEFAULT NULL, PRIMARY KEY (`ID`) );";
            myStmt.execute(sql);

            for (int m = 0; m < num.length; m++) {
                ps = myConn.prepareStatement("INSERT INTO " + tableName + " ( " + columnName + " ) VALUES(?)");
                ps.setInt(1, num[m]);
                ps.executeUpdate();
            }
            ps = myConn.prepareStatement("INSERT INTO " + tableName + " ( " + columnName2 + " ) VALUES(?)");
            ps.setInt(1, (int) selectionSortExecutionTime);
            ps.executeUpdate();
            // myStmt.executeQuery(query);
            ResultSet myRs = ((Statement) myStmt).executeQuery("SELECT * FROM " + tableName);

/*           // 4. process the result set
            System.out.println("The sorted numbers are ::::");
            while (myRs.next()) {
                System.out.print(myRs.getString(columnName) + " ");
            }
*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Come to conclusion about which Sorting Algo is better in given data set.
        if (selectionSortExecutionTime > insertionSortExecutionTime)
            System.out.println("Insertion Sort is better than Selection Sort.)");
        else System.out.println("Selection Sort is better than Insertion Sort.)");

    }

    public static void storeRandomNumbers(int[] num) {
        Random rand = new Random();
        for (int i = 0; i < num.length; i++) {
            num[i] = rand.nextInt(10000);
        }
    }

    public static void randomize(int arr[], int n) {
        Random r = new Random();
        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = n - 1; i > 0; i--) {
            int j = r.nextInt(i);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static void printIntValue(List<Integer> array) {
        for (Integer st : array) {
            System.out.println(st);
        }
    }

    public static void printValue(List<String> array) {
        for (String st : array) {
            System.out.println(st);
        }
    }

}
