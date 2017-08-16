/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queen;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author MingKie
 */
public class Node {
    private final int N = 22;
    private final int BLANK = 0;
    private final int QUEEN = 1;
    private int value;
    private Board board;
    private Queue<Board> neighbors;
    
    public Node(Board board) {
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

    private void moveUp() {
        //System.out.println("Move Up");
        for (int i = 0; i < N; ++i) {
            Queen[] queens = board.getQueens().clone();
            for (int j = 0; j < queens.length; ++j) {
                queens[j] = new Queen(board.getQueens()[j].getRow(), board.getQueens()[j].getCol());
            }
            int row = queens[i].getRow();
            while(row > 0) {
                row = row - 1;
                queens[i].setRow(row);
                Board board = new Board(generateBoard(queens));
                //board.print();
                //System.out.println("Value: " + board.getTotalAttacks());
                //System.out.println();
                neighbors.add(board);
            }
            //System.out.println("--------------");
        }
    }
    
    private void moveDown() {
        //System.out.println("Move Down");
        for (int i = 0; i < N; ++i) {
            Queen[] queens = board.getQueens().clone();
            for (int j = 0; j < queens.length; ++j) {
                queens[j] = new Queen(board.getQueens()[j].getRow(), board.getQueens()[j].getCol());
            }
            int row = queens[i].getRow();
            while(row < N - 1) {
                row = row + 1;
                queens[i].setRow(row);
                Board board = new Board(generateBoard(queens));
                //board.print();
                //System.out.println("Value: " + board.getTotalAttacks());
                //System.out.println();
                neighbors.add(board);
            }
            //System.out.println("--------------");
        }
    }
    
    private int[][] generateBoard(Queen[] queens) {
        int[][] board = new int[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                board[i][j] = BLANK;
            }
        }
        int row;
        int col;
        for (int i = 0; i < N; ++i) {
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
