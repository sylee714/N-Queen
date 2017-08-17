/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author MingKie
 */
public class Genetic {
    private final double MUTATION_RATE = 0.015;
    private final double UNIFORM_RATE = 0.5;
    private int n;
    private int maxPairs;
    private int populationSize;
    private int tournamentSize;
    private final int BLANK = 0;
    private final int QUEEN = 1;
    //private int sum;
    private boolean foundGoal;
    private final int GOAL = 0;
    private Board bestIndividual;
    //private ArrayList<Integer> ticketPool;
    private ArrayList<Board> initialPopulation;
    private ArrayList<Board> currentPopulation;
    
    public Genetic(int n, int populationSize) {
        this.n = n;
        calculateMaxPairs();
        this.populationSize = populationSize;
        tournamentSize = this.populationSize/5;
        foundGoal = false;
        initialPopulation = new ArrayList<>();
        currentPopulation = new ArrayList<>();
        //ticketPool = new ArrayList<>();
        generateInitialPopulation();
        currentPopulation = initialPopulation;
        //setSum(initialPopulation);
        //setTicketPool();
    }
    
    private void calculateMaxPairs() {
        maxPairs = n * (n - 1)/2;
    }
    
    /*
    private void setTicketPool() {
        ticketPool.clear();
        int numberOfTickets;
        for (int i = 0; i < populationSize; ++i) {
            numberOfTickets = currentPopulation.get(i).getNumOfNonAttackingPairs()/sum * 100;
            // When number of ticket is 0, place it only once
            if (numberOfTickets == 0) {
                ticketPool.add(currentPopulation.get(i).getNumOfNonAttackingPairs());
            } else {
                for (int j = 0; j < numberOfTickets; ++j) {
                    ticketPool.add(currentPopulation.get(i).getNumOfNonAttackingPairs());
                }
            }
        }
        Collections.shuffle(ticketPool);
    }

    public ArrayList<Integer> getTicketPool() {
        return ticketPool;
    }
    */
    
    
    public ArrayList<Board> getInitialPopulation() {
        return initialPopulation;
    }
    
    public void solve() {
        currentPopulation = initialPopulation;
        setBestIndividual();
        ArrayList<Board> newPopulation;
        while (bestIndividual.getNumOfNonAttackingPairs() < maxPairs) {
            newPopulation = new ArrayList<>();
            for (int i = 0; i < populationSize; ++i) {
                Board x = select(currentPopulation);
                Board y = select(currentPopulation);
                Board child = crossOver(x, y);
                if (Math.random() <= MUTATION_RATE) {
                    child = mutate(child);
                }
                newPopulation.add(child);
            }
            currentPopulation = newPopulation;
            setBestIndividual();
        }      
    }
    
    
    public Board mutate(Board board) {
        Random rand = new Random();
        int col = rand.nextInt(n);
        //System.out.println("col = " + col);
        board.moveQueen(col);
        return board;
    }
    

    public Board select(ArrayList<Board> population) {
        Random rand = new Random();
        int index;
        ArrayList<Board> tournament = new ArrayList<>();
        for (int i = 0; i < tournamentSize; ++i) {
            index = rand.nextInt(populationSize);
            tournament.add(currentPopulation.get(index));
        }
        return getFittest(tournament);
    }
    
    private Board getFittest(ArrayList<Board> population) {
        Board fittest = population.get(0);
        for (int i = 0; i < tournamentSize; ++i) {
            if (fittest.getNumOfNonAttackingPairs() <= population.get(i).getNumOfNonAttackingPairs()) {
                fittest = population.get(i);
            }
        }
        return fittest;
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
            if (x == null) {
                System.out.println("x is null");
            }
            
            if (y == null) {
                System.out.println("y is null");
            }
            copyBoard(newState, x.getState(), 0, num);
            copyBoard(newState, y.getState(), num, n);
        }
        //System.out.println("new state: " + newState);
        //System.out.println(newState.length());
        return new Board(generateIntBoard(newState), n);
    }
    
    private void copyBoard(int[] newState, int[] state, int start, int end) {
        for (int i = start; i < end; ++i) {
            newState[i] = state[i];
        }
    }
    
    private int[][] generateIntBoard(int[] state) {
        int[][] intBoard = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                intBoard[i][j] = BLANK;
            }
        }
        for (int i = 0; i < n; ++i) {
            //System.out.println("i = " + i);
            //System.out.println("row = " + state[i]);
            intBoard[state[i]][i] = QUEEN;
        }
        return intBoard;
    }
    
    
    /*
    private void setSum(ArrayList<Board> population) {
        int sum = 0;
        for (int i = 0; i < populationSize; ++i) {
            sum = sum + population.get(i).getNumOfNonAttackingPairs();
        }
        this.sum = sum;
    }
    */
    
    private void generateInitialPopulation() {
        for (int i = 0; i < populationSize; ++i) {
            BoardGenerator generator = new BoardGenerator(n);
            Board board = generator.getBoard();
            if (board.getNumOfNonAttackingPairs() == GOAL) {
                foundGoal = true;
                bestIndividual = board;
            }
            if (checkDuplicate(board)) {
                i--;
            } else {
                initialPopulation.add(board);
            }
        }
    }
    
    private boolean checkDuplicate(Board board) {
        boolean foundDuplicate = false;
        for (int i = 0; i < initialPopulation.size(); ++i) {
            if (initialPopulation.get(i).getState().equals(board.getState())) {
                foundDuplicate = true;
            }
        }
        return foundDuplicate;
    }
    
    private boolean checkGoal(Board board) {
        return board.getNumOfNonAttackingPairs() == maxPairs;
    }
    
    public Board getBestIndividual() {
        return bestIndividual;
    }
    
}
