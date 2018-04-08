package problems;

public class Palindrome {
    public static void main(String[] args) {
        /*
          If a String is reversed and it remains unchanged, that is called Palindrome. For example, MOM,DAD,MADAM are
          Palindrome. So write java code to check if a given String is Palindrome or not.
         */
        int number = 1234564321;
        String str = Integer.toString(number);
        String revStr ="";
        for (int j=0; j<str.length(); j++) {
            revStr+=str.charAt(str.length()-1-j);
        }
        if(str.equals(revStr)) System.out.printf("This number: %d is a palindrome.\n", number);
        else
            System.out.printf("This number: %d is NOT a palindrome.\n", number);
    }
}
