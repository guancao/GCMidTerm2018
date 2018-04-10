package parser;

import design.EmployeeInfo;
import org.testng.Assert;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UnitTestingStudentProfile {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        //Apply Unit Test into all the methods in this package.
        String pathSelenium = System.getProperty("user.dir") + "/src/parser/selenium.xml";
        String pathQtp = System.getProperty("user.dir") + "/src/parser/qtp.xml";
        String tag = "id";
        XmlReader xmlReader = new XmlReader();
        List<Student> test = new ArrayList<>();
        test = xmlReader.parseData(tag, pathQtp);
        for (Student st: test) System.out.println(st);
        String expected = test.get(1).getFirstName();
        System.out.println(expected);

        Assert.assertEquals(expected, "Mohammadd", "check XmlReader");
    }
}
