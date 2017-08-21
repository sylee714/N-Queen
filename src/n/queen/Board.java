package n.queen;

import java.util.Random;

/**
 * This class represents a board. It implements interface Comparable because
 * class Node uses priority queue. It calculates the total number of 
 * attacking pairs and non-attacking pairs. 
 */
public class Board implements Comparable<Board> {
    private int n; // Number of queens
    private int maxPairs;
    private final int BLANK = 0;
    private final int QUEEN = 1;
    private int numOfAttackingPairs;
    private int numOfNonAttackingPairs;   
    private int[] state; // Stores the position of rows
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
        calculateNumOfAttackingPairs();
        setNumOfNonAttackingPairs();
    }
    
    private void calculateMaxPairs() {
        maxPairs = n * (n - 1)/2;
    }
    
    /**
     * Finds all the queens on the board and stores their positions. 
     */
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
    
    private void calculateNumOfAttackingPairs() {
        int attacks = 0;
        for (int i = 0; i < n; ++i) {
            
            attacks = attacks + checkRight(queens[i]) + 
                    checkUpRight(queens[i]) + checkDownRight(queens[i]);
        }
        numOfAttackingPairs = attacks;
    }
    
    /**
     * Check if there is any other queens on the same row to the right.
     * @param queen, the queen at that position
     * @return number of queens
     */
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
    
    /**
     * Check if there is any other queens on the same diagonal (up, right).
     * @param queen, the queen at that position
     * @return number of queens
     */
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
    
    /**
     * Check if there is any other queens on the same diagonal (down, right).
     * @param queen, the queen at that position
     * @return number of queens
     */
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
        return numOfAttackingPairs;
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
        numOfNonAttackingPairs = maxPairs - numOfAttackingPairs;
    }
    
    private void setState() { 
        for (int i = 0; i < queens.length; ++i) {
           state[i] = queens[i].getRow();
        }
    }
    
    public int[] getState() {
        return state;
    }
    
    /**
     * Move a queen to a different row but stay on the same column.
     * @param col, the queen's column
     */
    public void moveQueen(int col) {
        Random rand = new Random();
        int tempRow = queens[col].getRow();
        int newRow = rand.nextInt(n);
        while(newRow == tempRow) {
            newRow = rand.nextInt(n);
        }
        board[tempRow][col] = BLANK;
        board[newRow][col] = QUEEN;
        // Update all the fields
        findQueens();
        setState();
        calculateNumOfAttackingPairs();
        setNumOfNonAttackingPairs();
    }
    
    @Override
    public int compareTo(Board o) {
        if (numOfAttackingPairs > o.getTotalAttacks()) {
            return 1;
        } else if(numOfAttackingPairs < o.getTotalAttacks()) {
            return -1;
        } else {
            return 0;
        }
    }
}
