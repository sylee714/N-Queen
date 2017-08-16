/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author MingKie
 */
public class Genetic {
    private final int MUTATION_RATE = 10;
    private final int N = 22;
    private final int MAX_PAIRS = 231;
    private final int K = 100;
    private final int BLANK = 0;
    private final int QUEEN = 1;
    private int sum;
    private boolean foundGoal;
    private final int GOAL = 0;
    private Board goalBoard;
    private ArrayList<Board> initialPopulation;
    private ArrayList<Board> population;
    
    public Genetic() {
        foundGoal = false;
        initialPopulation = new ArrayList<>();
        population = new ArrayList<>();
        generateInitialPopulation();
        setSum(initialPopulation);
    }
    
    public void solve() {
        Random rand = new Random();
        int num;
        population = initialPopulation;
        ArrayList<Board> newPopulation;
        while (!foundGoal) {
            newPopulation = new ArrayList<>();
            for (int i = 0; i < K; ++i) {
                Board x = select(population);
                Board y = select(population);
                Board child = crossOver(x, y);
                num = rand.nextInt(1000) + 1;
                if (num < MUTATION_RATE) {
                    
                }
            }
        }
        
    }
    
    /*
    private Board mutate(Board board) {
        
    }
    */

    private Board select(ArrayList<Board> population) {
        Board board = null;
        Random rand = new Random();
        int num = rand.nextInt(sum + 1);
        int sum = 0;
        for (int i = 0; i < K; ++i) {
           sum = sum + population.get(i).getNumOfNonAttackingPairs();
           if (sum > num) {
               board = population.get(i);
           }
        }
        return board;
    }
   
    
    private Board crossOver(Board x, Board y) {
        String newState = "";
        Random rand = new Random();
        int num = rand.nextInt(N) + 1;
        if (num == 1) {
            newState = y.getState();
        } else if (num == N) {
            newState = x.getState();
        } else {
            newState = x.getState().substring(0, num) + 
                y.getState().substring(num + 1, N);
        }
        return new Board(generateIntBoard(newState));
    }
    
    private int[][] generateIntBoard(String state) {
        int[][] intBoard = new int[22][22];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                intBoard[i][j] = BLANK;
            }
        }
        for (int i = 0; i < N; ++i) {
            intBoard[state.charAt(i)][i] = QUEEN;
        }
        return intBoard;
    }
    
    private void setSum(ArrayList<Board> population) {
        int sum = 0;
        for (int i = 0; i < K; ++i) {
            sum = sum + population.get(i).getNumOfNonAttackingPairs();
        }
        this.sum = sum;
    }
    
    private void generateInitialPopulation() {
        for (int i = 0; i < K; ++i) {
            BoardGenerator generator = new BoardGenerator();
            Board board = generator.getBoard();
            if (board.getNumOfNonAttackingPairs() == GOAL) {
                foundGoal = true;
                goalBoard = board;
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
        return board.getNumOfNonAttackingPairs() == MAX_PAIRS;
    }
    
}
