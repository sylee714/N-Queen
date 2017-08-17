/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queen;

import java.util.Random;

/**
 *
 * @author MingKie
 */
public class Board implements Comparable<Board> {
    private int n;
    private int maxPairs;
    private final int BLANK = 0;
    private final int QUEEN = 1;
    private int totalAttacks;
    private int numOfNonAttackingPairs;
    
    private int[] state;
    private int[][] board;
    private Queen[] queens;
    
    public Board(int[][] board, int n) {
        this.board = board;
        this.n = n;
        calculateMaxPairs();
        queens = new Queen[n];
        state = new int[n];
        findQueens();
        setState();
        calculateTotalAttacks();
        setNumOfNonAttackingPairs();
    }
    
    private void calculateMaxPairs() {
        maxPairs = n * (n - 1)/2;
    }
    
    public void moveQueen(int col) {
        Random rand = new Random();
        int tempRow = queens[col].getRow();
        int newRow = rand.nextInt(n);
        while(newRow == tempRow) {
            newRow = rand.nextInt(n);
        }
        board[tempRow][col] = BLANK;
        board[newRow][col] = QUEEN;
        findQueens();
        setState();
        calculateTotalAttacks();
        setNumOfNonAttackingPairs();
        //return this;
    }
    
    private void findQueens() {
        for (int i = 0; i < n; ++i) {
            for (int  j = 0; j < n; ++j) {
                if (board[i][j] == QUEEN) {
                    Queen queen = new Queen(i, j);
                    queens[j] = queen;
                }
            }
        }
    }
    
    private void calculateTotalAttacks() {
        int attacks = 0;
        for (int i = 0; i < n; ++i) {
            
            attacks = attacks + checkRight(queens[i]) + 
                    checkUpRight(queens[i]) + checkDownRight(queens[i]);
            //System.out.println("Col " + i + " = " + (checkRight(queens[i]) + 
                    //checkUpRight(queens[i]) + checkDownRight(queens[i])));
        }
        totalAttacks = attacks;
    }
    
    private int checkRight(Queen queen) {
        int numberOfAttacks = 0;
        int row = queen.getRow();
        int col = queen.getCol();
        for (int i = col + 1; i < n; ++i) {
            if (queens[i].getRow() == row) {
                numberOfAttacks++;
            }
        }
        return numberOfAttacks;
    }
    
    private int checkUpRight(Queen queen) {
        int numberOfAttacks = 0;
        int row = queen.getRow() - 1;
        int col = queen.getCol() + 1;
        while (row < n && col < n) {
            if (queens[col].getCol() == col && queens[col].getRow() == row) {
                numberOfAttacks++;
            }
            row--;
            col++;
        }
        return numberOfAttacks;
    }
    
    private int checkDownRight(Queen queen) {
        int numberOfAttacks = 0;
        int row = queen.getRow() + 1;
        int col = queen.getCol() + 1;
        while (row < n && col < n) {
            if (queens[col].getCol() == col && queens[col].getRow() == row) {
                numberOfAttacks++;
            }
            row++;
            col++;
        }
        return numberOfAttacks;
    }
    
    public void print() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public int getTotalAttacks() {
        return totalAttacks;
    }

    public int[][] getBoard() {
        return board;
    }

    public Queen[] getQueens() {
        return queens;
    }

    public int getNumOfNonAttackingPairs() {
        return numOfNonAttackingPairs;
    }

    private void setNumOfNonAttackingPairs() {
        numOfNonAttackingPairs = maxPairs - totalAttacks;
    }
    

    private void setState() { 
        for (int i = 0; i < queens.length; ++i) {
           state[i] = queens[i].getRow();
        }
    }
    
    public int[] getState() {
        return state;
    }
    
    @Override
    public int compareTo(Board o) {
        if (totalAttacks > o.getTotalAttacks()) {
            return 1;
        } else if(totalAttacks < o.getTotalAttacks()) {
            return -1;
        } else {
            return 0;
        }
    }
}
