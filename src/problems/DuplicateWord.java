package problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mrahman on 04/22/17.
 */
public class DuplicateWord {

    public static void main(String[] args) {
        /*
         * Write a java program to find the duplicate words and their number of occurrences in the string.
         * Also Find the average length of the words.
         */

        String st = "Java is a programming Language. Java is also an Island of Indonesia. Java is widely used language";
        List<String> wordList = new ArrayList<String>();
        Map<String, Integer> wordMap = new HashMap<>();
        String[] words = st.split("\\W+");
        //       for (String x: words) System.out.println(x);

        for (int i = 0; i < words.length; i++) {
            int count = 1;
            for (int j = i + 1; j < words.length; j++) {
                if (words[i].equalsIgnoreCase(words[j])) {
                    count++;
                    words[j] = "";
                }
            }
            wordMap.put(words[i], count);
        }
        //check the map
//        for (Map.Entry entry : wordMap.entrySet()) System.out.println(entry.getKey() + " " + entry.getValue());

        //find the duplicates
        for (Map.Entry entry : wordMap.entrySet()) {
            if ((int) entry.getValue() > 1)
                System.out.println(entry.getKey() + " has duplicates, total appearance ::: " + entry.getValue());
            ;
        }

        //find the average length of words
        int lenSum = 0;
        int counter = 0;
        int avg = 0;
        for (Map.Entry entry : wordMap.entrySet()) {
            lenSum += entry.getKey().toString().length();
 //           System.out.println(lenSum);
            counter++;
        }
        avg = lenSum / counter;
        System.out.println("The average length of words is :::" + avg);

    }

}
