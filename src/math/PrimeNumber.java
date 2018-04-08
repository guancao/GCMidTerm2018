package math;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class PrimeNumber {
    private static final String URL = "jdbc:mysql://localhost:3306/pntproj";
    private static final String USER = "root";
    private static final String PASSWORD = "goTO123456";
    public static PreparedStatement ps = null;

	public static void main(String[] args) {
		/*
		 * Find list of Prime numbers from number 2 to 1 million.
		 * Try the best solution as possible.Which will take less CPU life cycle.
		 * Out put number of Prime numbers on the given range.
		 *
		 *
		 * Use any databases[MongoDB, Oracle, MySql] to store data and retrieve data.
		 *
		 */
        List<Integer> primeNumberList = new ArrayList<>();
        int limit = 1000000;
        for (int i = 2; i <= limit; i++) {
            if (isPrime(i)) primeNumberList.add(i);
        }
        for (Integer x : primeNumberList) System.out.print(x + " ");

        //put into a database
        try {
            // 1. get a connection to the database
            Connection myConn = getConnection(URL, USER, PASSWORD);

            // 2. create a statement
            Statement myStmt = myConn.createStatement();

            // 3. execute a SQL query
            // create a new table with name: tbl_primeNumber  -- just run once, if re-run the code, comment this
            String tableName = "tbl_primeNumber";
            String columnName = "primeNumber";
            ps = myConn.prepareStatement("DROP TABLE IF EXISTS `"+tableName+"`;");
            ps.executeUpdate();
            String sql = "CREATE TABLE `"+tableName+"` (`ID` int(11) NOT NULL AUTO_INCREMENT,"
                    + "`" + columnName+ "` bigint(20) DEFAULT NULL,  PRIMARY KEY (`ID`) );";
            myStmt.execute(sql);

            // clean up the table content first when run this program again
            myStmt.execute("TRUNCATE " + tableName);

            //insert values to the table
            String insertion;
            for (Integer num : primeNumberList) {
                insertion = "INSERT INTO "+ tableName + "(" + columnName + ")" + "VALUES (" + num + ")";
                myStmt.executeUpdate(insertion);
            }

            // myStmt.executeQuery(query);
            ResultSet myRs = ((Statement) myStmt).executeQuery("SELECT * FROM "+ tableName);

            // 4. process the result set
            System.out.println("The prime numbers (2~1,000,000) are ::::");
            while (myRs.next()) {
                System.out.print(myRs.getString("primeNumber") + " ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //check if it is a prime number or composite number
    public static boolean isPrime(int number) {
        if (number < 2) return false;

        boolean isPrim = true;
        int srt = (int) Math.sqrt(number);
        for (int i = srt; i >= 2; i--) {
            if (number % i == 0) isPrim = false;
        }
        return isPrim;
    }

}
