package math;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class Pattern {
	private static final String URL = "jdbc:mysql://localhost:3306/pntproj";
	private static final String USER = "root";
	private static final String PASSWORD = "goTO123456";
	public static PreparedStatement ps = null;


	public static void main(String[] args) {
		/* Read this numbers, find the pattern then implement the logic from this pattern.which will give you this output.
		 * 100,99,98,97,96,95,94,93,92,91,90,88,86,84,82,80,78,76,74,72,70,67,64,61,58,55,52,49,46,43,40,36,32............
		 *
		 * Use any databases[MongoDB, Oracle, MySql] to store data and retrieve data.
		 *
		 */
		ArrayList<Integer> patternList = new ArrayList<>();
		int n = 100;
		patternList.add(n);
		int counter = 10;
		int decrement = 1;
		do {
			for (int k = 0; k < counter; k++) {
				n = n - decrement;
				patternList.add(n);
			}
			decrement++;
		} while (n > 0);
		for (Integer x : patternList) System.out.print(x+ " ");
		System.out.println();

		//put into a database

		try {
			// 1. get a connection to the database
			Connection myConn = getConnection(URL, USER, PASSWORD);

			// 2. create a statement
			Statement myStmt = myConn.createStatement();

			// 3. execute a SQL query
			// create a new table with name: tbl_patternNumber  -- just run once, if re-run the code, comment this
			String tableName = "tbl_patternNumber";
			String columnName = "patternNumber";
			ps = myConn.prepareStatement("DROP TABLE IF EXISTS `"+tableName+"`;");
			ps.executeUpdate();
			String sql = "CREATE TABLE `"+tableName+"` (`ID` int(11) NOT NULL AUTO_INCREMENT,"
					+ "`" + columnName+ "` bigint(20) DEFAULT NULL,  PRIMARY KEY (`ID`) );";
			myStmt.execute(sql);

			// clean up the table content first when run this program again
			myStmt.execute("TRUNCATE tbl_patternNumber");

			//insert values to the table
			String insertion;
			for (Integer num : patternList) {
				insertion = "INSERT INTO tbl_patternNumber ("+ columnName+ ") " + "VALUES (" + num + ")";
				myStmt.executeUpdate(insertion);
			}

			// myStmt.executeQuery(query);
			ResultSet myRs = ((Statement) myStmt).executeQuery("SELECT * FROM tbl_patternNumber");

			// 4. process the result set
			System.out.println("The number pattern is ::::");
			while (myRs.next()) {
				System.out.print(myRs.getString("patternNumber") + " ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


		
		
		

	}
}
