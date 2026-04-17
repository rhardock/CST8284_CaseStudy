package app.connect4;

import java.util.Random;
import java.util.ArrayList;

public class AIPlayer extends Player {
    private Random rand = new Random();

    public AIPlayer(String name, char symbol) {
        super(name, symbol);
    }

    @Override
    public int getMove(Board board) {
        int cols = board.getCols();
        char opponentSymbol = (getSymbol() == 'R') ? 'Y' : 'R';

        // 1. Check if AI can win in the next move
        for (int c = 0; c < cols; c++) {
            if (canWinNext(board, c, getSymbol())) return c;
        }

        // 2. Check if AI needs to block the human from winning
        for (int c = 0; c < cols; c++) {
            if (canWinNext(board, c, opponentSymbol)) return c;
        }

        // 3. Fallback: Pick a random valid column
        ArrayList<Integer> validCols = new ArrayList<>();
        for (int c = 0; c < cols; c++) {
            // Check if column isn't full (using the dropPiece logic)
            if (isColumnValid(board, c)) {
                validCols.add(c);
            }
        }

        if (validCols.isEmpty()) return -1; // Board full

        // return a random valid column
        return validCols.get(rand.nextInt(validCols.size()));
    }

    private boolean canWinNext(Board board, int col, char symbol) {
        // Temporary simulation: This uses the existing Board logic
        if (!isColumnValid(board, col)) return false;

        // simulate the move on the real grid
        // then immediately revert it if it doesn't result in a win.
        boolean win = false;
        char[][] grid = board.getGrid();
        int row = -1;

        // Find where it would land (matching the Board.dropPiece logic)
        for (int r = board.getRows() - 1; r >= 0; r--) {
            if (grid[r][col] == ' ') {
                row = r;
                grid[r][col] = symbol; // Place temporarily
                break;
            }
        }

        if (row != -1) {
            win = board.checkWin(symbol);
            grid[row][col] = ' '; // Undo move
        }

        return win;
    }

    private boolean isColumnValid(Board board, int col) {
        return board.getGrid()[0][col] == ' ';
    }
}
