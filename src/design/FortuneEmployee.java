package design;

import databases.ConnectDB;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FortuneEmployee {

	/**
	 * FortuneEmployee class has a main methods where you will be able to create Object from
	 * EmployeeInfo class to use fields and attributes.Demonstrate as many methods as possible 
	 * to use with proper business work flow.Think as a Software Architect, Product Designer and 
	 * as a Software Developer.(employee.info.system) package is given as an outline,you need to elaborate
	 * more to design an application that will meet for fortune 500 Employee Information
	 * Services.
	 *
	 * Use any databases[MongoDB, Oracle, MySql] to store data and retrieve data.
	 *
	 **/
	public static void main(String[] args) throws Exception {
		EmployeeInfo firstEmployee = new EmployeeInfo(101, "Tom Hanks", "Accounting");
		EmployeeInfo secondEmployee = new EmployeeInfo(102, "Jerry Jones", "Clerk");

		Map<Integer, NewEmployee> employeeMap = new HashMap<>();
		employeeMap.put(firstEmployee.getEmployeeId(), firstEmployee);
		employeeMap.put(secondEmployee.getEmployeeId(), secondEmployee);

//		Map<Integer, List<Integer>> compensationMap = new HashMap<>(); //id, {salary, bonus, pension}
		List<Integer> salaryList = new ArrayList<>(3); //salary
 //       List<Integer> bonusList = new ArrayList<>(3);  //bonus

        //salary
        firstEmployee.setMonthlySalary(5000);
        secondEmployee.setMonthlySalary(6000);

        salaryList.add(firstEmployee.calculateSalary());
        salaryList.add(secondEmployee.calculateSalary());

        //performace
        ArrayList<String> performance = new ArrayList<>();
        performance.add("excellent");
        performance.add("average");

        //bonus
        ArrayList<Integer> bonusList = new ArrayList<>();
        bonusList.add(firstEmployee.calculateEmployeeBonus(performance.get(0)));
        bonusList.add(secondEmployee.calculateEmployeeBonus(performance.get(1)));
//        System.out.println(firstEmployee.calculateEmployeeBonus(performance.get(0)));

        //Add data to DB
        ConnectDB connectDB = new ConnectDB();
        connectDB.insertDataFromIntArrayListToMySql(salaryList, "tbl_salary", "salary");
        connectDB.insertDataFromIntArrayListToMySql(bonusList, "tbl_bonus", "bonus");

        //read from the database
        System.out.println("The current salary list ::::");
        System.out.println(connectDB.readDataBase("tbl_salary", "salary"));
        System.out.println("The current bonus list ::::");
        System.out.println(connectDB.readDataBase("tbl_bonus", "bonus"));

    }

}
