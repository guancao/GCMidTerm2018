package problems;

import java.util.ArrayList;

/**
 * Created by mrahman on 04/22/17.
 */
public class Anagram {

    public static void main(String[] args) {
        //Write a Java Program to check if the two String are Anagram. Two String are called Anagram when there is
        //same character but in different order.For example,"CAT" and "ACT", "ARMY" and "MARY".
        String str1 = "ACD";
        String str2 = "TCA";
        if(str1.length()!= str2.length()) System.out.println("This is two strings are NOT anagram!");

        ArrayList<Character> charList1 = new ArrayList<>();
        ArrayList<Character> charList2 = new ArrayList<>();

        if(str1.length()==str2.length()) try {
            for (int i = 0; i < str1.length(); i++) {
                charList1.add(str1.charAt(i));
                charList2.add(str2.charAt(i));
            }
            //sort characters in each list
            sortList(charList1);
            sortList(charList2);

            //compare each character in the list
            boolean isDiff = false;
            for (int k=0; k<charList1.size(); k++){
                if(charList1.get(k)!= charList2.get(k)) isDiff = true;
            }
            if(isDiff) System.out.println("This is two strings are NOT anagram!");
            else System.out.println("This is two strings are anagram!!!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void sortList(ArrayList<Character> list){
        for (int i=1; i<list.size(); i++){
            for(int j=i; j>0; j--){
                if(list.get(j)<list.get(j-1)){
                    Character temp = list.get(j);
                    list.set(j, list.get(j-1));
                    list.set(j-1, temp);
                }
            }
        }
    }
}
