package app.connect4;

public class Board {
    // ENCAPSULATION: private fields
    private char[][] grid;
    private final int rows;
    private final int cols;

    // OVERLOADING: Constructor 1 (Default)
    public Board() {
        this(6, 7);
    }

    // OVERLOADING: Constructor 2 (Custom)
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
        resetBoard();
    }

    private void resetBoard() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c] = ' ';
    }

    public char[][] getGrid() { return grid; }
    
    public boolean dropPiece(int col, char symbol) {
        for (int r = 5; r >= 0; r--) { // Start at the bottom row
            if (grid[r][col] == ' ') { // If spot is empty
                grid[r][col] = symbol; // Place the piece
                return true;           // Success!
            }
        }
        return false; // Column was full
    }
}