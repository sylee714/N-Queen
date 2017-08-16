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
    private final int N = 22;
    private final int MAX_PAIRS = 231;
    private final int QUEEN = 1;
    private int totalAttacks;
    private int numOfNonAttackingPairs;
    private String state;
    private int[][] board;
    private Queen[] queens;
    
    public Board(int[][] board) {
        this.board = board;
        queens = new Queen[N];
        findQueens();
        setState();
        calculateTotalAttacks();
        setNumOfNonAttackingPairs();
    }
    
    public void moveQueen(int col) {
        Random rand = new Random();
        int num = rand.nextInt(N);
        
    }

    public int getNumOfNonAttackingPairs() {
        return numOfNonAttackingPairs;
    }

    private void setNumOfNonAttackingPairs() {
        numOfNonAttackingPairs = MAX_PAIRS - totalAttacks;
    }
    

    private void setState() {
        String temp = "";
        for (int i = 0; i < queens.length; ++i) {
           temp = temp + queens[i].getRow() + "";
        }
        state = temp;
    }
    
    public String getState() {
        return state;
    }
    
    private void findQueens() {
        for (int i = 0; i < N; ++i) {
            for (int  j = 0; j < N; ++j) {
                if (board[i][j] == QUEEN) {
                    Queen queen = new Queen(i, j);
                    queens[j] = queen;
                }
            }
        }
    }
    
    private void calculateTotalAttacks() {
        int attacks = 0;
        for (int i = 0; i < N; ++i) {
            attacks = attacks + checkRight(queens[i]) + 
                    checkUpRight(queens[i]) + checkDownRight(queens[i]);
        }
        totalAttacks = attacks;
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
    
    private int checkRight(Queen queen) {
        int numberOfAttacks = 0;
        int row = queen.getRow();
        int col = queen.getCol();
        for (int i = col + 1; i < N; ++i) {
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
        while (row < N && col < N) {
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
        while (row < N && col < N) {
            if (queens[col].getCol() == col && queens[col].getRow() == row) {
                numberOfAttacks++;
            }
            row++;
            col++;
        }
        return numberOfAttacks;
    }
    
    public void print() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
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
