/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queen;

import java.util.ArrayList;
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
        
        Random rand = new Random();
        int num = rand.nextInt(22);
        System.out.println(num);
        String name = "Steve";
        System.out.println(name.substring(0, 3));
        System.out.println(name.substring(3, 5));
        */
        /*
        Genetic genetic = new Genetic();
        ArrayList<Board> population = genetic.getInitialPopulation();
        for (int i = 0; i < population.size(); ++i) {
            //System.out.println(i + " = " + population.get(i).getTotalAttacks());
            System.out.println("Non attack: " + population.get(i).getNumOfNonAttackingPairs());
            System.out.println("Attack: " + population.get(i).getTotalAttacks());
            population.get(i).print();
            System.out.println("-------------------");
        }
        //for (int i = 0; i < 10; ++i) {
            Board temp = genetic.select(population);
            System.out.println("Non attack: " + temp.getNumOfNonAttackingPairs());
            System.out.println("Attack: " + temp.getTotalAttacks());
            temp.print();
        //}
        */
        
        for (int i = 0; i < 3; ++i) {
            Genetic genetic = new Genetic(22, 200);
            genetic.solve();
            genetic.getBestIndividual().print();
            System.out.println("Non attack: " + genetic.getBestIndividual().getNumOfNonAttackingPairs());
            System.out.println("Attack: " + genetic.getBestIndividual().getTotalAttacks());
        }
        /*
        BoardGenerator generator = new BoardGenerator(8);
        Board board = generator.getBoard();
        board.print();
        System.out.println("Non attack: " + board.getNumOfNonAttackingPairs());
        System.out.println("Attack: " + board.getTotalAttacks());
        board = genetic.mutate(board);
        board.print();
        System.out.println("Non attack: " + board.getNumOfNonAttackingPairs());
        System.out.println("Attack: " + board.getTotalAttacks());
        */
        
    }
    
}
