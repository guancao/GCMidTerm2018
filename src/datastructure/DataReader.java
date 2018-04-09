package datastructure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static java.sql.DriverManager.getConnection;

public class DataReader {
    private static final String URL = "jdbc:mysql://localhost:3306/pntproj";
    private static final String USER = "root";
    private static final String PASSWORD = "goTO123456";
    private static PreparedStatement ps = null;

    public static void main(String[] args) throws IOException {
        /*
         * User API to read the below textFile and print to console.
         * Use BufferedReader class.
         * Use try....catch block to handle Exception.
         *
         * Use any databases[MongoDB, Oracle, MySql] to store data and retrieve data.
         *
         * After reading from file using BufferedReader API, store each word into Stack and LinkedList. So each word
         * should construct a node in LinkedList.Then iterate/traverse through the list to retrieve as FIFO
         * order from LinkedList and retrieve as FILO order from Stack.
         *
         * Demonstrate how to use Stack that includes push,peek,search,pop elements.
         * Use For Each loop/while loop/Iterator to retrieve data.
         */

        String textFile = System.getProperty("user.dir") + "/src/data/self-driving-car.txt";

        try {
            FileReader fr = new FileReader(textFile);
            BufferedReader br = new BufferedReader(fr);

            String str = br.readLine();
            String[] words = str.split("\\W+");
            for (String x : words) System.out.print(x + " ");
            System.out.println();

            Stack<String> wordStack = new Stack();
            List<String> wordLinkedList = new LinkedList<>();

            for (int i = 0; i < words.length; i++) {
                wordStack.push(words[i]);       //push words to the stack
                wordLinkedList.add(i, words[i]); //add words to the linked list
            }
            System.out.println(wordStack.peek());  //stack peek() show the last-in
            //before pop()
            System.out.println(wordStack.search("to"));  //find the location of "give" in the stack (from the top-- last in: 1)
            //after pop()
            System.out.println(wordStack.pop());   //stack pop() pop out the last-in
            System.out.println(wordStack.search("to"));  //find the location of "give" in the stack (from the top-- last in: 1)

            Iterator<String> it = wordLinkedList.iterator();
            while(it.hasNext()) System.out.print(it.next()+" ");  //Linkedlist FIFO
            System.out.println();


            //**************store data in database****************************
            // 1. get a connection to the database
            Connection myConn = getConnection(URL, USER, PASSWORD);

            // 2. create a statement
            Statement myStmt = myConn.createStatement();

            // 3. execute a SQL query
            // create a new table with name: tbl_primeNumber  -- just run once, if re-run the code, comment this
            String tableName = "tbl_dataReader";
            String columnName = "word";
            ps = myConn.prepareStatement("DROP TABLE IF EXISTS `" + tableName + "`;");
            ps.executeUpdate();
            String sql = "CREATE TABLE " + tableName + "(ID int(11) NOT NULL AUTO_INCREMENT, "
                    +  columnName + " varChar(255) not NULL,  PRIMARY KEY (ID) );";
            myStmt.execute(sql);

            // clean up the table content first when run this program again
            myStmt.execute("TRUNCATE " + tableName);

            //insert values to the table
            String insertion="";
            for (String num : words) {
                insertion = "INSERT INTO " + tableName + "(" + columnName + ")" + "VALUES ('" + num + "')";
                myStmt.executeUpdate(insertion);
            }

            // myStmt.executeQuery(query);
            ResultSet myRs = ((Statement) myStmt).executeQuery("SELECT * FROM " + tableName);

            // 4. process the result set
            System.out.println("The words in file are ::::");
            while (myRs.next()) {
                System.out.print(myRs.getString(columnName) + " ");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}

