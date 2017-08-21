package n.queen;

/**
 * This class is the main class that runs the program.
 */
public class Main {

    /**
     * This is the main method that runs the program.
     * @param args the command line arguments
     */
    public static void main(String[] args) {  
        int n = 22;
        int populationSize = 500;
        int numOfSuccess = 0;
        int totalSearchCost = 0;
        double numOfTest = 1000;
        long totalRunTime = 0;
        System.out.println("Using Steepest Hill-Climbing");
        for (int i = 0; i < numOfTest; ++i) {
            BoardGenerator generator = new BoardGenerator(n);
            Node node = new Node(generator.getBoard(), n);
            HillClimbing hillClimbing = new HillClimbing(node, n);
            hillClimbing.solve();
            if (hillClimbing.getLast().getValue() == 0) {
                numOfSuccess++;
                hillClimbing.getLast().getBoard().print();
                System.out.println("---------------------------------------------");
            }
            totalSearchCost = totalSearchCost + hillClimbing.getSearchCost();
            totalRunTime = totalRunTime + hillClimbing.getRunTime();
        }
        System.out.println("Success percentage: " + numOfSuccess/numOfTest*100);
        System.out.println("Average search cost: " + totalSearchCost/1000.0);
        System.out.println("Average run time: " + totalRunTime/1000.0 + " milliseconds");      
        System.out.println("---------------------------------------------");
        //Population 200
        numOfSuccess = 0;
        System.out.println("Using the Genetic Algorithm");
        System.out.println("Population size: " + populationSize);
        for (int i = 0; i < 10; ++i) {
            if (numOfSuccess < 3) {
                Genetic genetic = new Genetic(n, populationSize);
                genetic.solve();
                genetic.getBestIndividual().print();
                System.out.println("Number of non-attacking pairs: " + genetic.getBestIndividual().getNumOfNonAttackingPairs());
                System.out.println("Number of attacking pairs: " + genetic.getBestIndividual().getTotalAttacks());
                System.out.println("---------------------------------------------");
                if (genetic.getBestIndividual().getTotalAttacks() == 0) {
                    numOfSuccess++;
                }
            }
        }
    }   
}
