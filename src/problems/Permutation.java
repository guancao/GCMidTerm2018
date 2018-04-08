package problems;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mrahman on 04/22/17.
 */
public class Permutation {

    public static void main(String[] args) {

        /*
         * Permutation of String "ABC" is "ABC" "BAC" "BCA" "ACB" "CAB" "CBA".
         * Write Java program to compute all Permutation of a String
         *
         */
        String str = "abcd";
        int len = str.length();

        int limit = permutCount(len); //total number of permutation
        int loopcounts =0;
        //put each character into an array
        Character[] charArray = new Character[len];
         for(int i=0; i<len; i++){
            charArray[i]=str.charAt(i);
        }

        ArrayList<String> newStrList = new ArrayList<>();
        newStrList.add(str);
        Random rand = new Random();
        int previous, now;
        while (newStrList.size()<limit && loopcounts <len*len*len){  //avoid endless working
            String tempStr="";
            previous = rand.nextInt(len-1);
            now =rand.nextInt(len-1);
            if(previous!=now){
                char temp = charArray[previous];
                charArray[previous] = charArray[now];
                charArray[now]=temp;
                for(int m=0; m<len; m++){
                    tempStr+=charArray[m];
                }
            }
            boolean inTheList=false;
            for(int n=0; n<newStrList.size(); n++){
                if(tempStr.equalsIgnoreCase(newStrList.get(n))) inTheList=true;
            }
            if(!inTheList) newStrList.add(tempStr);
            loopcounts++;
        }
        for(String x: newStrList) System.out.println(x+" ");

    }
    public static int permutCount (int len){ //the total number of permutation is n*(n-1)*(n-2)*...1
        if (len<2) return 1;
        else
            return len * permutCount(len-1);
    }

}
