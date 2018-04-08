package problems;

import org.testng.Assert;

public class UnitTestingStringProblem {
    public static void main(String[] args) {
        //Apply Unit Test into all the methods in this package.

        Anagram anagram = new Anagram();
        Assert.assertEquals(anagram.anagramCheck("ABC","ADCB"), true, "the check is not right.");

    }
}
