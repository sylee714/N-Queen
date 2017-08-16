/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queen;

import java.util.Queue;
import java.util.Random;

/**
 *
 * @author MingKie
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        int numOfSuccess = 0;
        int totalSearchCost = 0;
        long totalRunTime = 0;
        for (int i = 0; i < 1000; ++i) {
            BoardGenerator generator = new BoardGenerator();
            Node node = new Node(generator.getBoard());
            HillClimbing hillClimbing = new HillClimbing(node);
            hillClimbing.solve();
            if (hillClimbing.getLast().getValue() == 0) {
                numOfSuccess++;
            }
            System.out.println("Value of Last Node: " + hillClimbing.getLast().getValue());
            totalSearchCost = totalSearchCost + hillClimbing.getSearchCost();
            System.out.println("Search Cost: " + hillClimbing.getSearchCost());
            totalRunTime = totalRunTime + hillClimbing.getRunTime();
            System.out.println("Run time: " + hillClimbing.getRunTime());
        }
        System.out.println("----------------------------------------");
        System.out.println("Number of success: " + numOfSuccess);
        System.out.println("Average search cost: " + totalSearchCost/1000.0);
        System.out.println("Average run time: " + totalRunTime/1000.0);
        */
        Random rand = new Random();
        int num = rand.nextInt(22);
        System.out.println(num);
        String name = "Steve";
        System.out.println(name.substring(0, 3));
        System.out.println(name.substring(3, 5));
    }
    
}
