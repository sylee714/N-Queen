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
public class BoardGenerator {
    private int n;
    private final int BLANK = 0;
    private final int QUEEN = 1;
    private int[][] intBoard;
    private Board board;
    
    public BoardGenerator(int n) {
        this.n = n;
        intBoard = new int[n][n];
        initialize();
        placeQueens();
        board = new Board(intBoard, n);
    }
    
    /**
     * Initialize the board with no queens placed.
     */
    private void initialize() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                intBoard[i][j] = BLANK;
            }
        }
    }
    
    /**
     * Place queens randomly.
     */
    private void placeQueens() {
        Random rand = new Random();
        int num;
        for (int i = 0; i < n; ++i) {
            num = rand.nextInt(n);
            intBoard[num][i] = QUEEN;
        }
    }

    public Board getBoard() {
        return board;
    }
    
    
}
