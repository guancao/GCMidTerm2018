package parser;

import databases.ConnectDB;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProcessStudentInfo {

    /*
     * Under XmlReader class, the parseData() will return list of Student Info which will contain Student first name, last name and score.
     * You need to implement the method name "convertIntToChar()" which will convert String score into char Grade.('A'for 90 to 100,'B'for 80 to 89 and
     * 'C' for 70 to 79.
     *
     * Here in the main method fill in the code that outlined to read xml data parsed into 2 separate ArrayList, then store into map.
     * Once map has all data, retrieve those data and out put to console.
     *
     * Do any necessary steps that require for below successful output.
     * ......................................................
     * Student (id= 1) "Ibrahim" "Khan"        		Grade= B
     * Student (id= 2) "Asif" "Roni"          		Grade= A
     * Student (id= 3) "Gumani" "Hose"              Grade= A
     * Student (id= 4) "Sukanto" "Shaha"            Grade= B
     * Student (id= 5) "MD" "Hossain"               Grade= C
     * ......................................................
     *
     *
     * Use any databases[MongoDB, Oracle, MySql] to store data and to retrieve data.
     *
     */

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, SQLException, ClassNotFoundException {
        //Path of XML data to be read.
        String pathSelenium = System.getProperty("user.dir") + "/src/parser/selenium.xml";
        String pathQtp = System.getProperty("user.dir") + "/src/parser/qtp.xml";
        String tag = "id";
        //Create ConnectDB Object
        ConnectDB connectDB = new ConnectDB();
        //Declare a Map with List<String> into it.
        Map<String, List<Student>> list = new LinkedHashMap<String, List<Student>>();
				
        /*Declare 2 ArrayList with Student data type to store Selenium student into one of the ArrayList and
				  Qtp student into another ArrayList. */

        List<Student> seleniumStudents = new ArrayList<Student>();
        List<Student> qtpStudents = new ArrayList<Student>();

        //Create XMLReader object.
        XmlReader xmlReader = new XmlReader();

        //Parse Data using parseData method and then store data into Selenium ArrayList.
        seleniumStudents = xmlReader.parseData(tag, pathSelenium);
        System.out.println("****The Selenium students****");
		for (Student st: seleniumStudents) System.out.println(st);

        //Parse Data using parseData method and then store data into Qtp ArrayList.
        qtpStudents = xmlReader.parseData(tag, pathQtp);
        System.out.println("**The QTP students**");
        for (Student st: qtpStudents) System.out.println(st);

        //add Selenium ArrayList data into map.
        list.put("Selenium", seleniumStudents);

        //add Qtp ArrayList data into map.
        list.put("QTP", qtpStudents);

        //Retrieve map data and display output.
        for (Map.Entry entry : list.entrySet()) {
            System.out.println(entry.getKey() + "    " + entry.getValue());
        }

        //Store Qtp data into Qtp table in Database
        connectDB.insertToMongoDB(qtpStudents, "qtp");
        connectDB.insertDataFromStudentArrayListToMySql(qtpStudents, "tbl_qtp");

        //Store Selenium data into Selenium table in Database
        connectDB.insertToMongoDB(seleniumStudents, "selenium");
        connectDB.insertDataFromStudentArrayListToMySql(seleniumStudents, "tbl_selenium");

        //Retrieve Qtp students from Database
        List<Student> stList = connectDB.readStudentListFromMongoDB("qtp");
        System.out.println("**** Mongo DB read out**** The QTP student :::::::::::");
        for (Student st : stList) {
            System.out.println("Student (id= "+ st.getId()+ ")\t"+ st.getFirstName() + "\t\t" + st.getLastName() + " \t Grade= " + st.getScore());
        }
        List<Student> stListMySql = connectDB.readStudentListFromMySql("tbl_qtp");
        System.out.println("======read from MySQL database ::: QTP=========");
        for (Student stu : stListMySql) {
            System.out.println("Student (id= "+ stu.getId()+ ")\t"+ stu.getFirstName() + "\t\t" + stu.getLastName() + " \t Grade= " + stu.getScore());
        }
//
//
        //Retrieve Selenium students from Database
        List<Student> stSelList = connectDB.readStudentListFromMongoDB("selenium");
        System.out.println("** Mongo DB read out** The selenium student :::::::::::");
        for (Student st : stSelList) {
            System.out.println("Student (id= "+ st.getId()+ ")\t"+ st.getFirstName() + "\t\t" + st.getLastName() + " \t Grade= " + st.getScore());
        }

        List<Student> selstListMySql = connectDB.readStudentListFromMySql("tbl_selenium");
        System.out.println("======read from MySQL database ::: Selenium =========");
        for (Student stus : selstListMySql) {
            System.out.println("Student (id= "+ stus.getId()+ ")\t"+ stus.getFirstName() + "\t\t" + stus.getLastName() + " \t Grade= " + stus.getScore());
        }
    }
}
