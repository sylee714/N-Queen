package n.queen;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents the genetic algorithm.
 */
public class Genetic {
    private final double MUTATION_RATE = 0.015;
    private final int GOAL = 0;
    private final int BLANK = 0;
    private final int QUEEN = 1;
    // One minute limit
    private final long TIME_LIMIT = 30000;
    private int n;
    private int maxPairs;
    private int populationSize;
    // Tournament population is used for selection process to choose 
    // the fittest indivual from the randomly chosen individuals
    private int tournamentSize;
    private Board bestIndividual;
    private ArrayList<Board> initialPopulation;
    private ArrayList<Board> currentPopulation;
    
    public Genetic(int n, int populationSize) {
        this.n = n;
        calculateMaxPairs();
        this.populationSize = populationSize;
        tournamentSize = this.populationSize/5;
        initialPopulation = new ArrayList<>();
        currentPopulation = new ArrayList<>();
        generateInitialPopulation();
        currentPopulation = initialPopulation;
    }
    
    private void calculateMaxPairs() {
        maxPairs = n * (n - 1)/2;
    }
    
    public ArrayList<Board> getInitialPopulation() {
        return initialPopulation;
    }
    
    public void solve() {
        long startTime = System.currentTimeMillis();
        long endTime;
        long timeDifference = 0;
        if (!checkInitialPopulation()) {
            setBestIndividual();
            ArrayList<Board> newPopulation;
            // && timeInSeconds < TIME_LIMIT
            while (bestIndividual.getNumOfNonAttackingPairs() < maxPairs && timeDifference < TIME_LIMIT) {
                newPopulation = new ArrayList<>();
                for (int i = 0; i < populationSize; ++i) {
                    // Select two parents
                    Board x = select(currentPopulation);
                    Board y = select(currentPopulation);
                    // Crossover
                    Board child = crossOver(x, y);
                    // Mutate the newly created child
                    if (Math.random() <= MUTATION_RATE) {
                        child = mutate(child);
                    }
                    newPopulation.add(child);
                }
                currentPopulation = newPopulation;
                setBestIndividual();
                endTime = System.currentTimeMillis();
                timeDifference = endTime - startTime;
            }   
        }
    }
    
    private void setBestIndividual() {
        Board fittest = currentPopulation.get(0);
        for (int i = 0; i < populationSize; ++i) {
            if (fittest.getNumOfNonAttackingPairs() <= currentPopulation.get(i).getNumOfNonAttackingPairs()) {
                fittest = currentPopulation.get(i);
            }
        }
        bestIndividual = fittest;
    }
    
    /**
     * Mutates the given board by moving a queen to a different row.
     * @param board, chosen individual
     * @return mutated individual
     */
    public Board mutate(Board board) {
        Random rand = new Random();
        int col = rand.nextInt(n);
        board.moveQueen(col);
        return board;
    }
    
    /**
     * Selects the fittest individual from the tournament population. 
     * @param population, population
     * @return fittest individual
     */
    public Board select(ArrayList<Board> population) {
        Random rand = new Random();
        int index;
        ArrayList<Board> tournament = new ArrayList<>();
        for (int i = 0; i < tournamentSize; ++i) {
            // Randomly choose an indivual from the population and add 
            // to the tournament population
            index = rand.nextInt(populationSize);
            tournament.add(currentPopulation.get(index));
        }
        return getFittest(tournament);
    }
    
    /**
     * Returns the fittest individual from the given population.
     * @param population, population
     * @return fittest individual
     */
    private Board getFittest(ArrayList<Board> population) {
        Board fittest = population.get(0);
        for (int i = 0; i < tournamentSize; ++i) {
            if (fittest.getNumOfNonAttackingPairs() <= 
                    population.get(i).getNumOfNonAttackingPairs()) {
                fittest = population.get(i);
            }
        }
        return fittest;
    }  
    
    /**
     * Performs one-point crossover between two parents and creates
     * a child.
     * @param x, one parent
     * @param y, another parent
     * @return newly created child
     */
    private Board crossOver(Board x, Board y) {
        int[] newState = new int[n];
        Random rand = new Random();
        int num = rand.nextInt(n) + 1;
        if (num == 1) {
            newState = y.getState();
            copyBoard(newState, y.getState(), 0, n);
        } else if (num == n) {
            newState = x.getState();
            copyBoard(newState, x.getState(), 0, n);
        } else {
            // Check if any of parents is null
            if (x == null) {
                System.out.println("x is null");
            } 
            if (y == null) {
                System.out.println("y is null");
            }
            copyBoard(newState, x.getState(), 0, num);
            copyBoard(newState, y.getState(), num, n);
        }
        return new Board(generateIntBoard(newState), n);
    }
    
    /**
     * Copies the queens of a board from the start index to the end index to a
     * new board.
     * @param newState, newly created queens
     * @param state, position of queens
     * @param start, start index
     * @param end, end index
     */
    private void copyBoard(int[] newState, int[] state, int start, int end) {
        for (int i = start; i < end; ++i) {
            newState[i] = state[i];
        }
    }
    
    /**
     * Generates a 2-D integer array based on the given state.
     * @param state, it has the row position of each queen
     * @return a 2-D integer array
     */
    private int[][] generateIntBoard(int[] state) {
        int[][] intBoard = new int[n][n];
        // Make an empty board
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                intBoard[i][j] = BLANK;
            }
        }
        // Place the queens
        for (int i = 0; i < n; ++i) {
            intBoard[state[i]][i] = QUEEN;
        }
        return intBoard;
    }
    
    /**
     * Generates an initial population with the given population size.
     */
    private void generateInitialPopulation() {
        for (int i = 0; i < populationSize; ++i) {
            BoardGenerator generator = new BoardGenerator(n);
            Board board = generator.getBoard();
            if (board.getNumOfNonAttackingPairs() == GOAL) {
                bestIndividual = board;
            }
            if (checkDuplicate(board)) {
                i--;
            } else {
                initialPopulation.add(board);
            }
        }
    }
    
    /**
     * Checks if there is no same board in the population.
     * @param board, newly created board
     * @return true if there is the same one; otherwise, false
     */
    private boolean checkDuplicate(Board board) {
        boolean foundDuplicate = false;
        for (int i = 0; i < initialPopulation.size(); ++i) {
            if (initialPopulation.get(i).getState().equals(board.getState())) {
                foundDuplicate = true;
                break;
            }
        }
        return foundDuplicate;
    }
    
    /**
     * Checks if the initial population has the goal.
     * @return true if it has the goal; otherwise, false
     */
    private boolean checkInitialPopulation() {
        boolean isGoal = false;
        for (int i = 0; i < initialPopulation.size(); ++i) {
            if (initialPopulation.get(i).getNumOfNonAttackingPairs() == GOAL) {
                isGoal = true;
                bestIndividual = initialPopulation.get(i);
                break;
            }
        }
        return isGoal;
    }
    
    public Board getBestIndividual() {
        return bestIndividual;
    }
}
