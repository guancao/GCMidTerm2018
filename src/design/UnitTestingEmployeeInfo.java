package design;

import org.testng.Assert;

public class UnitTestingEmployeeInfo {
    public static void main(String[] args) {
        //Write Unit Test for all the methods has been implemented in this package.
        EmployeeInfo testEmp = new EmployeeInfo(200, "John Doe");
        String expectedName = "Johnn Doe";
        String receivedName = testEmp.getEmployeeName();

        Assert.assertEquals(expectedName, receivedName, "constructor or getter/setter has issues.");
    }
}
