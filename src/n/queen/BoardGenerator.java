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
    private final int N = 22;
    private final int BLANK = 0;
    private final int QUEEN = 1;
    private int[][] intBoard;
    private Board board;
    
    public BoardGenerator() {
        intBoard = new int[N][N];
        initialize();
        placeQueens();
        board = new Board(intBoard);
    }
    
    /**
     * Initialize the board with no queens placed.
     */
    private void initialize() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
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
        for (int i = 0; i < N; ++i) {
            num = rand.nextInt(N);
            intBoard[num][i] = QUEEN;
        }
    }

    public Board getBoard() {
        return board;
    }
    
    
}
