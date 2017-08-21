package n.queen;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This class represents a node which is used in class HillClimbing.
 * It finds all the possible neighbors from the current board.
 */
public class Node {
    private int n; // The number of queens
    private final int BLANK = 0;
    private final int QUEEN = 1;
    private int value;
    private Board board;
    private Queue<Board> neighbors;
    
    public Node(Board board, int n) {
        this.n = n;
        this.board = board;
        value = this.board.getTotalAttacks();
        neighbors = new PriorityQueue<>();
        generateNeighbors();
    }

    public int getValue() {
        return value;
    }
    
    private void generateNeighbors() {
        moveUp();
        moveDown();
    }

    /**
     * Moves a queen up and recalculate the number of attacking pairs
     * and add the new neighbor to the neighbors.
     */
    private void moveUp() {
        for (int i = 0; i < n; ++i) {
            // Use clone so it does not affect the original board
            Queen[] queens = board.getQueens().clone();
            // Copy queens
            for (int j = 0; j < queens.length; ++j) {
                queens[j] = new Queen(board.getQueens()[j].getRow(), board.getQueens()[j].getCol());
            }
            int row = queens[i].getRow();
            while(row > 0) {
                row = row - 1;
                queens[i].setRow(row);
                Board neighbor = new Board(generateBoard(queens), n);
                neighbors.add(neighbor);
            }
        }
    }
    
    /**
     * Moves a queen down and recalculate the number of attacking pairs
     * and add the new neighbor to the neighbors.
     */
    private void moveDown() {
        for (int i = 0; i < n; ++i) {
            // Use clone so it does not affect the original board
            Queen[] queens = board.getQueens().clone();
            // Copy queens
            for (int j = 0; j < queens.length; ++j) {
                queens[j] = new Queen(board.getQueens()[j].getRow(), board.getQueens()[j].getCol());
            }
            int row = queens[i].getRow();
            while(row < n - 1) {
                row = row + 1;
                queens[i].setRow(row);
                Board neighbor = new Board(generateBoard(queens), n);
                neighbors.add(neighbor);
            }
        }
    }
    
    /**
     * Generate a new board whenever the position of a queen is changed.
     * @param queens, all the queens on the board
     * @return new board
     */
    private int[][] generateBoard(Queen[] queens) {
        int[][] board = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                board[i][j] = BLANK;
            }
        }
        int row;
        int col;
        for (int i = 0; i < n; ++i) {
            row = queens[i].getRow();
            col = queens[i].getCol();
            board[row][col] = QUEEN;
        }
        return board;
    }

    public Board getBoard() {
        return board;
    }

    public Queue<Board> getNeighbors() {
        return neighbors;
    }
}
