package databases;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import design.NewEmployee;
import org.bson.Document;
import parser.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mrahman on 04/02/18.
 */

public class ConnectDB {

    public static MongoDatabase mongoDatabase = null;

    public static Connection connect = null;
    public static Statement statement = null;
    public static PreparedStatement ps = null;
    public static ResultSet resultSet = null;

    public Properties loadProperties() throws IOException {
        Properties prop = new Properties();
        InputStream ism = new FileInputStream("src/secret.properties");
        prop.load(ism);
        ism.close();
        return prop;
    }

    public Connection connectToMySql() throws IOException, SQLException, ClassNotFoundException {
        Properties prop = loadProperties();
        String driverClass = prop.getProperty("MYSQLJDBC.driver");
        String url = prop.getProperty("MYSQLJDBC.url");
        String userName = prop.getProperty("MYSQLJDBC.userName");
        String password = prop.getProperty("MYSQLJDBC.password");
        Class.forName(driverClass);
        connect = DriverManager.getConnection(url, userName, password);
        System.out.println("Database is connected");
        return connect;
    }

    public MongoDatabase connectToMongoDB() {
        MongoClient mongoClient = new MongoClient();
        mongoDatabase = mongoClient.getDatabase("students");
        System.out.println("Database Connected");

        return mongoDatabase;
    }

    public List<String> readDataBase(String tableName, String columnName) throws Exception {
        List<String> data = new ArrayList<String>();

        try {
            connectToMySql();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from " + tableName);
            data = getResultSetData(resultSet, columnName);
        } catch (ClassNotFoundException e) {
            throw e;
        } finally {
            close();
        }
        return data;
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

    private List<String> getResultSetData(ResultSet resultSet, String columnName) throws SQLException {
        List<String> dataList = new ArrayList<String>();
        while (resultSet.next()) {
            String itemName = resultSet.getString(columnName);
            dataList.add(itemName);
        }
        return dataList;
    }

    public void insertDataFromArrayToMySql(int[] ArrayData, String tableName, String columnName) {
        try {
            connectToMySql();
            ps = connect.prepareStatement("DROP TABLE IF EXISTS `" + tableName + "`;");
            ps.executeUpdate();
            ps = connect.prepareStatement("CREATE TABLE `" + tableName + "` (`ID` int(11) NOT NULL AUTO_INCREMENT," +
                    "`SortingNumbers` bigint(20) DEFAULT NULL," + "PRIMARY KEY (`ID`) );");
//            ps = connect.prepareStatement("CREATE TABLE `"+tableName+"` (`ID` int(11) NOT NULL AUTO_INCREMENT," +
//                    "`SortingNumbers` bigint(20) DEFAULT NULL,  " +
//                    "`column_lowestNumber` bigint(20) DEFAULT NULL,  " +
//                    "PRIMARY KEY (`ID`) );");

            ps.executeUpdate();
            for (int n = 0; n < ArrayData.length; n++) {
                ps = connect.prepareStatement("INSERT INTO " + tableName + " ( " + columnName + " ) VALUES(?)");
                ps.setInt(1, ArrayData[n]);
                ps.executeUpdate();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertDataFromStringToMySql(String ArrayData, String tableName, String columnName) {
        try {
            connectToMySql();
            ps = connect.prepareStatement("INSERT INTO " + tableName + " ( " + columnName + " ) VALUES(?)");
            ps.setString(1, ArrayData);
            ps.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> directDatabaseQueryExecute(String passQuery, String dataColumn) throws Exception {
        List<String> data = new ArrayList<String>();

        try {
            connectToMySql();
            statement = connect.createStatement();
            resultSet = statement.executeQuery(passQuery);
            data = getResultSetData(resultSet, dataColumn);
        } catch (ClassNotFoundException e) {
            throw e;
        } finally {
            close();
        }
        return data;
    }

    public void insertDataFromArrayListToMySql(List<Student> list, String tableName, String columnName) {
        try {
            connectToMySql();
            ps = connect.prepareStatement("DROP TABLE IF EXISTS `" + tableName + "`;");
            ps.executeUpdate();
            ps = connect.prepareStatement("CREATE TABLE `" + tableName + "` (`ID` int(11) NOT NULL AUTO_INCREMENT,`SortingNumbers` bigint(20) DEFAULT NULL,  PRIMARY KEY (`ID`) );");
            ps.executeUpdate();
            for (Student st : list) {
                ps = connect.prepareStatement("INSERT INTO " + tableName + " ( " + columnName + " ) VALUES(?)");
                ps.setObject(1, st);
                ps.executeUpdate();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String insertToMongoDB(User user) {
        String profile = user.getName();
        MongoDatabase mongoDatabase = connectToMongoDB();
        MongoCollection<Document> collection = mongoDatabase.getCollection("profile");
        Document document = new Document().append("name", user.getName()).append("id", user.getId());
        collection.insertOne(document);
        return profile + " has been registered";
    }

    public String insertToMongoDB(List<Student> student, String profileName) {
        MongoDatabase mongoDatabase = connectToMongoDB();
        MongoCollection myCollection = mongoDatabase.getCollection(profileName);
        boolean collectionExists = mongoDatabase.listCollectionNames()
                .into(new ArrayList<String>()).contains(profileName);
        if (collectionExists) {
            myCollection.drop();
        }
        for (int i = 0; i < student.size(); i++) {
            MongoCollection<Document> collection = mongoDatabase.getCollection(profileName);
            Document document = new Document().append("id", student.get(i).getId()).append("firstName", student.get(i).getFirstName()).append("lastName",
                    student.get(i).getLastName()).append("score", student.get(i).getScore());
            collection.insertOne(document);
        }
        return "Student has been registered";
    }

    public List<User> readUserFromMongoDB() {
        List<User> list = new ArrayList<User>();
        User user = new User();
        MongoDatabase mongoDatabase = connectToMongoDB();
        MongoCollection<Document> collection = mongoDatabase.getCollection("profile");
        BasicDBObject basicDBObject = new BasicDBObject();
        FindIterable<Document> iterable = collection.find(basicDBObject);
        for (Document doc : iterable) {
            String id = "";
            int idInt = 0;
            String name = (String) doc.get("name");
            user.setName(name);
            try {
                id = (String) doc.get("id");
                int convertId = Integer.parseInt(id);
                user.setId(convertId);
            } catch (Exception ex) {
                idInt = (int) doc.get("id");
                user.setId(idInt);
            }
            user = new User(user.getName(), user.getId());
            list.add(user);
        }
        return list;
    }

    public List<Student> readStudentListFromMongoDB(String profileName) {
        List<Student> list = new ArrayList<Student>();
        Student student = new Student();
        MongoDatabase mongoDatabase = connectToMongoDB();
        MongoCollection<Document> collection = mongoDatabase.getCollection(profileName);
        BasicDBObject basicDBObject = new BasicDBObject();
        FindIterable<Document> iterable = collection.find(basicDBObject);
        for (Document doc : iterable) {
            String firstName = (String) doc.get("firstName");
            student.setFirstName(firstName);
            String lastName = (String) doc.get("lastName");
            student.setLastName(lastName);
            String score = (String) doc.get("score");
            student.setScore(score);
            String id = (String) doc.get("id");
            student.setId(id);
            student = new Student(student.getId(), student.getFirstName(), student.getLastName(), student.getScore());
            list.add(student);
        }
        return list;
    }

    public void insertProfileToMySql(String tableName, String columnName1, String columnName2) {
        try {
            connectToMySql();
            ps = connect.prepareStatement("INSERT INTO " + tableName + " ( " + columnName1 + "," + columnName2 + " ) VALUES(?,?)");
            ps.setString(1, "Ankita Sing");
            ps.setInt(2, 3590);
            ps.executeUpdate();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<User> readFromMySql() throws IOException, SQLException, ClassNotFoundException {
        List<User> list = new ArrayList<>();
        User user = null;
        try {
            Connection conn = connectToMySql();
            String query = "SELECT * FROM profile";
            // create the java statement
            Statement st = conn.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
            // iterate through the java resultset
            while (rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("id");
                //System.out.format("%s, %s\n", name, id);
                user = new User(name, id);
                list.add(user);

            }
            st.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return list;
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

    	/*
        insertProfileToMySql("profile","name", "id");
        List<User> list = readFromMySql();
        for(User user:list){
            System.out.println(user.getName()+ " " + user.getId());
        }
        String message = insertToMongoDB(new User("Tanima Chowdhury", 3539));
        List<User> user = readFromMongoDB();
        */

    }

    public void insertDataFromArrayListToMySql(ArrayList<Integer> numberList, String tableName, String columnName) {
        try {
            connectToMySql();
            ps = connect.prepareStatement("DROP TABLE IF EXISTS `" + tableName + "`;");
            ps.executeUpdate();
//            ps = connect.prepareStatement("CREATE TABLE `"+tableName+"` (`ID` int(11) NOT NULL AUTO_INCREMENT," +
//                    "`SortingNumbers` bigint(20) DEFAULT NULL," + "PRIMARY KEY (`ID`) );");
            ps = connect.prepareStatement("CREATE TABLE `" + tableName + "` (`ID` int(11) NOT NULL AUTO_INCREMENT," + "`column_lowestNumber` bigint(20) DEFAULT NULL,  " + "PRIMARY KEY (`ID`) );");

            ps.executeUpdate();
            for (int n = 0; n < numberList.size(); n++) {
                ps = connect.prepareStatement("INSERT INTO " + tableName + " ( " + columnName + " ) VALUES(?)");
                ps.setInt(1, numberList.get(n));
                ps.executeUpdate();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void insertDataFromStringArrayListToMySql(List<String> stringList, String tableName, String columnName) {
        try {
            connectToMySql();
            ps = connect.prepareStatement("DROP TABLE IF EXISTS `" + tableName + "`;");
            ps.executeUpdate();
//            ps = connect.prepareStatement("CREATE TABLE `"+tableName+"` (`ID` int(11) NOT NULL AUTO_INCREMENT," +
//                    "`SortingNumbers` bigint(20) DEFAULT NULL," + "PRIMARY KEY (`ID`) );");
            ps = connect.prepareStatement("CREATE TABLE `" + tableName + "` (`ID` int(11) NOT NULL AUTO_INCREMENT," + "`" + columnName + "` varChar(255) DEFAULT NULL,  " + "PRIMARY KEY (`ID`) );");

            ps.executeUpdate();
            for (int n = 0; n < stringList.size(); n++) {
                ps = connect.prepareStatement("INSERT INTO " + tableName + " ( " + columnName + " ) VALUES(?)");
                ps.setString(1, stringList.get(n));
                ps.executeUpdate();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void insertDataFromArrayListToMySql(Map<Integer, NewEmployee> employeeMap, String tableName,
                                               int columnName, String columnName2, String columnName3) {
        try {
            connectToMySql();
            ps = connect.prepareStatement("DROP TABLE IF EXISTS `" + tableName + "`;");
            ps.executeUpdate();
//            ps = connect.prepareStatement("CREATE TABLE `"+tableName+"` (`ID` int(11) NOT NULL AUTO_INCREMENT," +
//                    "`SortingNumbers` bigint(20) DEFAULT NULL," + "PRIMARY KEY (`ID`) );");
            ps = connect.prepareStatement("CREATE TABLE `" + tableName + "` (`ID` int(11) NOT NULL AUTO_INCREMENT," +
                    "'" + columnName + "' int(10) default not null, `" + columnName2 + "` varChar(255) DEFAULT NULL,  " +
                    "'" + columnName3 + "' varChar(255) default null, PRIMARY KEY (`ID`) );");

            ps.executeUpdate();
            for (Map.Entry entry : employeeMap.entrySet()) {
//                ps = connect.prepareStatement("INSERT INTO "+tableName+" ( "+columnName+ " , "columnName2 +
//                        " ,"+columnName3+" ) VALUES( " + entry.getKey() +" , "+entry.getValue().getEmployeeName()+" ,") ;
//                ps = connect.prepareStatement("INSERT INTO "+tableName+" ( "+columnName+" ) VALUES(?)");
//                ps.setString(1,stringList.get(n));
                ps.executeUpdate();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void insertDataFromIntArrayListToMySql(List<Integer> intList, String tableName, String columnName) {
        try {
            connectToMySql();
            ps = connect.prepareStatement("DROP TABLE IF EXISTS `" + tableName + "`;");
            ps.executeUpdate();
//            ps = connect.prepareStatement("CREATE TABLE `"+tableName+"` (`ID` int(11) NOT NULL AUTO_INCREMENT," +
//                    "`SortingNumbers` bigint(20) DEFAULT NULL," + "PRIMARY KEY (`ID`) );");
            ps = connect.prepareStatement("CREATE TABLE `" + tableName + "` (`ID` int(11) NOT NULL AUTO_INCREMENT,"
                    + "`" + columnName + "` varChar(255) DEFAULT NULL,  " + "PRIMARY KEY (`ID`) );");

            ps.executeUpdate();
            for (int n = 0; n < intList.size(); n++) {
                ps = connect.prepareStatement("INSERT INTO " + tableName + " ( " + columnName + " ) VALUES(?)");
                ps.setInt(1, intList.get(n));
                ps.executeUpdate();
            }
        } catch (
                IOException e)

        {
            e.printStackTrace();
        } catch (
                SQLException e)

        {
            e.printStackTrace();
        } catch (
                ClassNotFoundException e)

        {
            e.printStackTrace();
        }

    }

    //add this method to read List<Student> from MySql
    public List<Student> readStudentListFromMySql(String tableName) throws SQLException, IOException, ClassNotFoundException {
        List<Student> list = new ArrayList<Student>();
        Student student = new Student();
        Connection conn = connectToMySql();
        String query = "SELECT * FROM  " + tableName;
        // create the java statement
        Statement st = conn.createStatement();
        // execute the query, and get a java resultset
        ResultSet rs = st.executeQuery(query);
        // iterate through the java resultset
        while (rs.next()) {
            String id = (String) rs.getString("studentId");
            student.setId(id);
            String firstName = (String) rs.getString("firstName");
            student.setFirstName(firstName);
            String lastName = (String) rs.getString("lastName");
            student.setLastName(lastName);
            String score = (String) rs.getString("score");
            student.setScore(score);
            student = new Student(student.getId(), student.getFirstName(), student.getLastName(), student.getScore());
            list.add(student);
        }
        st.close();
        return list;
    }

    public void insertDataFromStudentArrayListToMySql(List<Student> studentlist, String tableName) {
        try {
            connectToMySql();
            ps = connect.prepareStatement("DROP TABLE IF EXISTS `" + tableName + "`;");
            ps.executeUpdate();
//            ps = connect.prepareStatement("CREATE TABLE `"+tableName+"` (`ID` int(11) NOT NULL AUTO_INCREMENT," +
//                    "`SortingNumbers` bigint(20) DEFAULT NULL," + "PRIMARY KEY (`ID`) );");
            ps = connect.prepareStatement("CREATE TABLE `" + tableName + "` (`ID` int(11) NOT NULL AUTO_INCREMENT, "
                    + "studentId varChar(20) default null, firstName varChar(255) DEFAULT NULL, "
                    + "lastName varChar(255) default null, score varChar(10) default null, PRIMARY KEY (`ID`) );");
            ps.executeUpdate();
            for (int n = 0; n < studentlist.size(); n++) {
                ps = connect.prepareStatement("INSERT INTO " + tableName + " (studentId, firstName, lastName, score) VALUES(?,?,?,?)");
                ps.setString(1, studentlist.get(n).getId());
                ps.setString(2, studentlist.get(n).getFirstName());
                ps.setString(3, studentlist.get(n).getLastName());
                ps.setString(4, studentlist.get(n).getScore());
                ps.executeUpdate();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


