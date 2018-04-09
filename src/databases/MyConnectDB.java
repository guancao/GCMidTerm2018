package databases;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class MyConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/pntproj";
    private static final String USER = "root";
    private static final String PASSWORD = "goTO123456";
    private static PreparedStatement ps = null;

    private String tableName;
    private String columnName;

    // 1. get a connection to the database
    Connection myConn = getConnection(URL, USER, PASSWORD);

    // 2. create a statement
    Statement myStmt = myConn.createStatement();

    public MyConnectDB() throws SQLException {
        // 1. get a connection to the database
        Connection myConn = getConnection(URL, USER, PASSWORD);

        // 2. create a statement
        Statement myStmt = myConn.createStatement();
    }

    public void createTable(String tableName, String columnName) {
        try {
            // 1. get a connection to the database
//            Connection myConn = getConnection(URL, USER, PASSWORD);

            // 2. create a statement
//            Statement myStmt = myConn.createStatement();

            // 3. execute a SQL query
            // create a new table
            ps = myConn.prepareStatement("DROP TABLE IF EXISTS `" + tableName + "`;");
            ps.executeUpdate();
            String sql = "CREATE TABLE `" + tableName + "` (`ID` int(11) NOT NULL AUTO_INCREMENT,"
                    + "`" + columnName + "` bigint(20) DEFAULT NULL,  PRIMARY KEY (`ID`) );";
            myStmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertTable(ArrayList<Integer> list, String tableName, String columnName) throws SQLException {
        // clean up the table content first when run this program again
        myStmt.execute("TRUNCATE " + tableName);

        //insert values to the table
        String insertion;
        for (Integer num : list) {
            insertion = "INSERT INTO " + tableName + "(" + columnName + ")" + "VALUES (" + num + ")";
            myStmt.executeUpdate(insertion);
        }

        // myStmt.executeQuery(query);
        ResultSet myRs = ((Statement) myStmt).executeQuery("SELECT * FROM " + tableName);

        // 4. process the result set
        System.out.println("The information title ::::");
        while (myRs.next()) {
            System.out.print(myRs.getString(columnName) + " ");
        }
    }
}
