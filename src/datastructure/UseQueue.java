package datastructure;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class UseQueue {

    public static void main(String[] args) {
        /*
         * Demonstrate how to use Queue that includes add,peek,remove,pool elements.
         * Use For Each loop and while loop with Iterator to retrieve data.
         *
         * Queue interface  <- Linkedlist
         */
        Queue<String> ticketOffice = new LinkedList<>();  // FIFO

        ticketOffice.add("morning star");
        ticketOffice.add("hunters");
        ticketOffice.add("fisherman");
        ticketOffice.add("moonwalker");
        ticketOffice.add("gone with wind");


        for (String x : ticketOffice) System.out.print(x + " ");
        System.out.println();

        System.out.println("take a peek:::" + ticketOffice.peek());  //FIFO
        ticketOffice.remove();            //FIFO -- "morning star" is gone.
        System.out.println("take a peek after remove():::" + ticketOffice.peek());
        System.out.println("pool()::::" + ticketOffice.poll());  //poll() == peek() and remove()

        Iterator it = ticketOffice.iterator();
        while (it.hasNext()) System.out.println(it.next());


    }

}
