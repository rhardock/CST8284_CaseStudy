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

    public void resetBoard() {
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
    
    public boolean checkWin(char symbol) {
        // 1. Horizontal Check
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 4; c++) { // Only need to start up to col 3
                if (grid[r][c] == symbol && grid[r][c+1] == symbol && 
                    grid[r][c+2] == symbol && grid[r][c+3] == symbol) return true;
            }
        }
        // 2. Vertical Check
        for (int r = 0; r < 3; r++) { // Only need to start up to row 2
            for (int c = 0; c < 7; c++) {
                if (grid[r][c] == symbol && grid[r+1][c] == symbol && 
                    grid[r+2][c] == symbol && grid[r+3][c] == symbol) return true;
            }
        }
        // 3. Diagonal Down-Right (\)
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 4; c++) {
                if (grid[r][c] == symbol && grid[r+1][c+1] == symbol && 
                    grid[r+2][c+2] == symbol && grid[r+3][c+3] == symbol) return true;
            }
        }
        // 4. Diagonal Up-Right (/)
        for (int r = 3; r < 6; r++) {
            for (int c = 0; c < 4; c++) {
                if (grid[r][c] == symbol && grid[r-1][c+1] == symbol && 
                    grid[r-2][c+2] == symbol && grid[r-3][c+3] == symbol) return true;
            }
        }
        return false;
    }
    
}