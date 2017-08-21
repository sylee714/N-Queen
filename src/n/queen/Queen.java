package n.queen;

/**
 * This class represents a queen. It stores the position of the queen.
 */
public class Queen {
    private int col;
    private int row;
    
    public Queen(int row, int col) {     
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }  
}
