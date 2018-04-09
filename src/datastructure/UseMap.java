package datastructure;

import databases.ConnectDB;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.*;

public class UseMap {

	public static void main(String[] args) throws Exception {
		/*
		 * Demonstrate how to use Map that includes storing and retrieving elements.
		 * Add List<String> into a Map. Like, Map<String, List<string>> list = new HashMap<String, List<String>>();
		 * Use For Each loop and while loop with Iterator to retrieve data.
		 *
		 * Use any databases[MongoDB, Oracle, MySql] to store data and retrieve data.
		 */

		Map<String, List<String>> foodCat = new HashMap<>();
		List<String> americanFood = new ArrayList<>();
		americanFood.add("hamburg");
		americanFood.add("hot dog");
		americanFood.add("steak");
		americanFood.add("fried cheese");

		List<String> chineseFood = new ArrayList<>();
		chineseFood.add("bamboo shoots");
		chineseFood.add("steam bun");
		chineseFood.add("fried peanuts");

		List<String> mexicanFood = new ArrayList<>();
		mexicanFood.add("tacos");
		mexicanFood.add("tortia");
		mexicanFood.add("chilli");

		foodCat.put("USA", americanFood);
		foodCat.put("China", chineseFood);
		foodCat.put("Mexico", mexicanFood);

		for(Map.Entry entry: foodCat.entrySet()){
			System.out.println(entry.getKey() + " "+ entry.getValue());
		}

		//database
		ConnectDB connectDB = new ConnectDB();
		List<String> countryList = new ArrayList<>();
		List<String> americanFoodList = new ArrayList<>();
		List<String> mexicanFoodList = new ArrayList<>();
		List<String> chineseFoodList = new ArrayList<>();
		for(Map.Entry entry: foodCat.entrySet()){
			countryList.add((String) entry.getKey());
			if(entry.getKey().equals("USA")){
				americanFoodList = (List<String>) ((ArrayList) entry.getValue()).clone();
			}
            if(entry.getKey().equals("China")){
                chineseFoodList = (List<String>) ((ArrayList) entry.getValue()).clone();
            }if(entry.getKey().equals("Mexico")){
                mexicanFoodList = (List<String>) ((ArrayList) entry.getValue()).clone();
            }
		}
		//create database
		connectDB.insertDataFromStringArrayListToMySql(countryList, "tbl_foodmap", "country");
        connectDB.insertDataFromStringArrayListToMySql(americanFoodList, "tbl_americanfoodmap", "americanfood");
        connectDB.insertDataFromStringArrayListToMySql(chineseFoodList, "tbl_chinesefoodmap", "chinesefood");
        connectDB.insertDataFromStringArrayListToMySql(mexicanFoodList, "tbl_mexicanfoodmap", "mexicanfood");

        //retrieve data from database
        List<String> readoutCountry = connectDB.readDataBase("tbl_foodmap", "country");
        for(String y: readoutCountry) System.out.print(y+ " ");
        List<String> readoutAmericanFood = connectDB.readDataBase("tbl_americanfoodmap", "americanfood");
        for(String y: readoutAmericanFood) System.out.print(y+ " ");
        List<String> readoutChineseFood = connectDB.readDataBase("tbl_chinesefoodmap", "chinesefood");
        for(String y: readoutChineseFood) System.out.print(y+ " ");
        List<String> readoutMexicanFood = connectDB.readDataBase("tbl_mexicanfoodmap", "mexicanfood");
        for(String y: readoutMexicanFood) System.out.print(y+ " ");


	}

}
