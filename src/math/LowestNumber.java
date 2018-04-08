package math;

import databases.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class LowestNumber {
    private static final String URL = "jdbc:mysql://localhost:3306/pntproj";
    private static final String USER = "root";
    private static final String PASSWORD = "goTO123456";
    public static PreparedStatement ps = null;

    public static void main(String[] args) {
        /*
         * Write java solution to find the lowest number from this array.
         * Use one of the databases from mysql or mongodb to store and to retrieve.
         */
        int array[] = new int[]{211, 110, 99, 34, 67, 89, 67, 456, 321, 456, 78, 90, 45, 32, 56, 78, 90, 54, 32, 123, 67, 5, 679, 54, 32, 65};

        //find lowest number from the array
        int number = lowestNumber(array);
        System.out.println(number);

        List<Integer> lowestNumberList = new ArrayList<>();
        lowestNumberList.add(number);
//        lowestNumberList.add(10);  //test database with an additional number

        try {
            // 1. get a connection to the database
            Connection myConn = getConnection(URL, USER, PASSWORD);

            // 2. create a statement
            Statement myStmt = myConn.createStatement();

            // 3. execute a SQL query
            // create a new table with name: LowestNumbers
            String tableName = "tbl_lowestNumber";
            ps = myConn.prepareStatement("DROP TABLE IF EXISTS `"+tableName+"`;");
            ps.executeUpdate();
            String sql = "CREATE TABLE `"+tableName+"` (`ID` int(11) NOT NULL AUTO_INCREMENT,"
                    + "`lowestNumber` bigint(20) DEFAULT NULL,  PRIMARY KEY (`ID`) );";
			myStmt.execute(sql);

            // clean up the table content first when run this program again
            myStmt.execute("TRUNCATE tbl_lowestNumber");

            //insert values to the table
            String insertion;
            for (Integer num : lowestNumberList) {
                insertion = "INSERT INTO tbl_lowestNumber (lowestNumber) " + "VALUES (" + num + ")";
                myStmt.executeUpdate(insertion);
            }

            // myStmt.executeQuery(query);
            ResultSet myRs = ((Statement) myStmt).executeQuery("SELECT * FROM tbl_lowestNumber");

            // 4. process the result set
            while (myRs.next()) {
                System.out.println("Lowest number is: " + myRs.getString("lowestNumber"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


 /*
        ConnectDB connectDB = new ConnectDB();
        List<Integer> lowestNumber = new ArrayList<>();

        //add the result to list
        lowestNumber.add(number);
        try {
            connectDB.insertDataFromArrayListToMySql(lowestNumber, "tbl_lowestNumber", "column_lowestNumber");
            lowestNumber = connectDB.readDataBase("tbl_lowestNumber", "column_lowestNumber");

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Data is reading from the Table (tbl_lowestNumber) and displaying to the console");
        for (Integer st : lowestNumber) {
            System.out.println(st);
        }
        */
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
