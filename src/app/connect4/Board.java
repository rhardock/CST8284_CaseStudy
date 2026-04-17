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
    
    // ENCAPSULATION: Public getters for private fields
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }    

    public void resetBoard() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c] = ' ';
    }

    public char[][] getGrid() { return grid; }
    
    public boolean dropPiece(int col, char symbol) {
    	// Dynamically start at the last row index
        for (int r = rows - 1; r >= 0; r--) {
            if (grid[r][col] == ' ') { // If spot is empty
                grid[r][col] = symbol; // Place the piece
                return true;           // Success!
            }
        }
        return false; // Column was full
    }
    
    public boolean checkWin(char symbol) {
    	
    	// Horizontal check
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols - 3; c++) {
                if (grid[r][c] == symbol && grid[r][c+1] == symbol &&
                    grid[r][c+2] == symbol && grid[r][c+3] == symbol) {
                    return true;
                }
            }
        }

        // Vertical check
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows - 3; r++) {
                if (grid[r][c] == symbol && grid[r+1][c] == symbol &&
                    grid[r+2][c] == symbol && grid[r+3][c] == symbol) {
                    return true;
                }
            }
        }

        // Diagonal Down-Right (\)
        for (int r = 0; r < rows - 3; r++) {
            for (int c = 0; c < cols - 3; c++) {
                if (grid[r][c] == symbol && grid[r+1][c+1] == symbol &&
                    grid[r+2][c+2] == symbol && grid[r+3][c+3] == symbol) {
                    return true;
                }
            }
        }

        // Diagonal Up-Right (/)
        for (int r = 3; r < rows; r++) {
            for (int c = 0; c < cols - 3; c++) {
                if (grid[r][c] == symbol && grid[r-1][c+1] == symbol &&
                    grid[r-2][c+2] == symbol && grid[r-3][c+3] == symbol) {
                    return true;
                }
            }
        }
        return false;
    }
    
}