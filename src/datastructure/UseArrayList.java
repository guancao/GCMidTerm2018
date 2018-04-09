package datastructure;

import databases.ConnectDB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UseArrayList {

    public static void main(String[] args) {
        /*
         * Demonstrate how to use ArrayList that includes add,peek,remove,retrieve elements.
         * Use For Each loop and while loop with Iterator to retrieve data.
         * Store all the sorted data into one of the databases.
         *
         */
        List<String> cityList = new ArrayList<>(5);
        String[] templist = new String[]{"Baltimore", "Seattle", "Boston", "Austin", "Denver"};
        try {
            for (int i = 0; i < templist.length; i++) {
                cityList.add(templist[i]);
            }
            cityList.add("Los Angeles");
            cityList.add("Leesburg");

            Iterator it = cityList.iterator();
            while(it.hasNext()) System.out.print(it.next()+" ");
            System.out.println();

            System.out.println(cityList.get(0)); //what is the first element  -FIFO
            cityList.remove("Leesburg");

            it = cityList.iterator();
            while(it.hasNext()) System.out.print(it.next()+" ");
            System.out.println();

            //add to database
            ConnectDB connectDB = new ConnectDB();
            connectDB.insertDataFromStringArrayListToMySql(cityList, "tbl_cityList", "cityName");

            //read out from the database
            List<String> readout = connectDB.readDataBase("tbl_cityList", "cityName");
            for(String y: readout) System.out.print(y+ " ");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
