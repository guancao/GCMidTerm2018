package design;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;

import java.util.Scanner;

public class EmployeeInfo extends NewEmployee {

    /*This class can be implemented from Employee interface then add additional methods in EmployeeInfo class.
     * Also, Employee interface can be implemented into an abstract class.So create an Abstract class
     * then inherit that abstract class into EmployeeInfo class.Once you done with designing EmployeeInfo class,
     * go to FortuneEmployee class to apply all the fields and attributes.
     *
     * Important: YOU MUST USE the
     * OOP(abstraction,Encapsulation, Inheritance and Polymorphism) concepts in every level possible.
     * Use all kind of keywords(super,this,static,final........)
     * Implement Nested class.
     * Use Exception Handling.
     *
     */

    /*
     * declare few static and final fields and some non-static fields
     */
    static String companyName;
    private int monthlySalary;

    /*
     * You must implement the logic for below 2 methods and
     * following 2 methods are prototype as well for other methods need to be design,
     * as you will come up with the new ideas.
     */

    /*
     * you must have multiple constructor.
     * Must implement below constructor.
     */
    public EmployeeInfo(int employeeId) {
        super(employeeId);
    }

    public EmployeeInfo(int employeeId, String name) {
        super(employeeId, name);
    }
    public EmployeeInfo(int employeeId, String name, String deptName){
        super(employeeId,name,deptName);
    }
    public void setMonthlySalary(int salary){
        this.monthlySalary =salary;
    }
    public int getMonthlySalary(){
        return monthlySalary;
    }

    @Override
    public int calculateSalary() {
        return monthlySalary*12;
    }

    @Override
    public void benefitLayout() {
        System.out.println("Your salary is :::" + calculateSalary());
        System.out.println("Your pension will be :::" + calculateEmployeePension());
     }

    /*
     * This methods should calculate Employee bonus based on salary and performance.
     * Then it will return the total yearly bonus. So you need to implement the logic.
     * Hints: 10% of the salary for best performance, 8% of the salary for average performance and so on.
     * You can set arbitrary number for performance.
     * So you probably need to send 2 arguments.
     *
     */
    public int calculateEmployeeBonus(String performance) {
        int total = 0;
        int bonusPct = 0;
        if (performance.equalsIgnoreCase("excellent")) bonusPct = 10;
        if (performance.equalsIgnoreCase("average")) bonusPct = 5;
        total = monthlySalary * 12 * bonusPct/ 100;
        return total;
    }

    /*
     * This methods should calculate Employee Pension based on salary and numbers of years with the company.
     * Then it will return the total pension. So you need to implement the logic.
     * Hints: pension will be 5% of the salary for 1 year, 10% for 2 years with the company and so on.
     *
     */
    public int calculateEmployeePension() {  //pension belongs to object, so it should be non-static
        int total = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter start date in format (example: May,2015): ");
        String joiningDate = sc.nextLine();
        System.out.println("Please enter today's date in format (example: August,2017): ");
        String todaysDate = sc.nextLine();
        String convertedJoiningDate = DateConversion.convertDate(joiningDate);
        String convertedTodaysDate = DateConversion.convertDate(todaysDate);

        //implement numbers of year from above two dates
        double serviceYear = calculateYear(convertedTodaysDate, convertedJoiningDate);
        //Calculate pension
        double pensionPct =0;
        if(serviceYear<= 20) pensionPct = serviceYear*5;
        else if(serviceYear>20) pensionPct=1;
        total = (int)(monthlySalary*pensionPct);
        return total;
    }

    //convert date from string to int and then calculate the total years of service
    private static double calculateYear(String date1, String date2){  //date format 1/2015
        int month1, month2;
        int year1, year2;
        String[] dateStr1 = date1.split("/");
        String[] dateStr2 = date2.split("/");
        month1 = Integer.parseInt(dateStr1[0]);
        month2 = Integer.parseInt(dateStr2[0]);
        year1 = Integer.parseInt(dateStr1[1]);
        year2 = Integer.parseInt(dateStr2[1]);
        double years = (double)(year2+month2/12 - year1 - month1/12);
        return years;
    }

    private static class DateConversion {

        public DateConversion(Months months) {
        }

        public static String convertDate(String date) {
            String[] extractMonth = date.split(",");
            String givenMonth = extractMonth[0];
            int monthDate = whichMonth(givenMonth);
            String actualDate = monthDate + "/" + extractMonth[1];
            return actualDate;
        }

        public static int whichMonth(String givenMonth) {
            Months months = Months.valueOf(givenMonth);
            int date = 0;
            switch (months) {
                case January:
                    date = 1;
                    break;
                case February:
                    date = 2;
                    break;
                case March:
                    date = 3;
                    break;
                case April:
                    date = 4;
                    break;
                case May:
                    date = 5;
                    break;
                case June:
                    date = 6;
                    break;
                case July:
                    date = 7;
                    break;
                case August:
                    date = 8;
                    break;
                case September:
                    date = 9;
                    break;
                case October:
                    date = 10;
                    break;
                case November:
                    date = 11;
                    break;
                case December:
                    date = 12;
                    break;
                default:
                    date = 0;
                    break;
            }
            return date;

        }
    }
}
