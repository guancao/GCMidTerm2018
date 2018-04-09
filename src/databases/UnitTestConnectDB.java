package databases;

import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class UnitTestConnectDB {
    public static void main(String[] args) throws Exception {
        ConnectDB connectDB = new ConnectDB();

        int[] testArr = new int[]{ 15, 25, 40};
        connectDB.insertDataFromArrayToMySql(testArr, "tbl_test", "number" );
        List<String> testList = new ArrayList<>() ;
        testList = connectDB.readDataBase("tbl_test", "number");
        for(String x: testList) System.out.println(x + " ");
 //       Assert.assertEquals(connectDB);

    }
}
