package app.connect4;

import javax.swing.*;
import java.awt.*;

/**
 * The main window for the Connect 4 game.
 * Demonstrates: Inheritance (extends JFrame) and GUI implementation.
 */
public class Connect4GUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Board board;
    private JButton[][] buttons; // Visual representation of the grid
    
    private char currentSymbol = 'R'; // Tracks whose turn it is

    public Connect4GUI() {
        board = new Board(); // Uses the default 6x7 constructor (Overloading)
        buttons = new JButton[6][7];
        
        setupUI();
    }

    private void setupUI() {
        setTitle("CST8284 Connect 4 - " + board.getGrid().length + "x" + board.getGrid()[0].length);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 7)); // Matches the board dimensions

        // Create the buttons for the grid
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                buttons[r][c] = new JButton();
                buttons[r][c].setBackground(Color.WHITE);
                
                // Captured for the Lambda below
                final int col = c; 
                
                // This tells the button: "When clicked, run the handleMove method for this column"
                buttons[r][c].addActionListener(e -> handleMove(col));
                
                add(buttons[r][c]);
            }
        }

        pack(); // Sizes the window to fit the buttons
        setSize(700, 600);
        setVisible(true);
    }
    
    private void resetGame() {
        // 1. Clear the data model
        board.resetBoard();
        
        // 2. Clear the visual buttons
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                buttons[r][c].setBackground(Color.WHITE);
            }
        }
        
        // 3. Reset the turn to Red
        currentSymbol = 'R';
    }
    
    private void handleMove(int col) {
        if (board.dropPiece(col, currentSymbol)) {
            updateBoardDisplay(); // Go look at the board data and change button colors
            
            // check if player wins
            if (board.checkWin(currentSymbol)) {
                String winner = (currentSymbol == 'R') ? "Red" : "Yellow";
                JOptionPane.showMessageDialog(this, winner + " Wins!");
                resetGame(); // You can create a simple method to clear the board
            } else {
                currentSymbol = (currentSymbol == 'R') ? 'Y' : 'R';
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "Column Full!");
        }
    }

    private void updateBoardDisplay() {
        char[][] grid = board.getGrid(); // Get the "Truth" from the Board object
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                if (grid[r][c] == 'R') buttons[r][c].setBackground(Color.RED);
                else if (grid[r][c] == 'Y') buttons[r][c].setBackground(Color.YELLOW);
            }
        }
    }

    public static void main(String[] args) {
        // Use the event dispatch thread for Swing for "suitable coding"
        SwingUtilities.invokeLater(() -> new Connect4GUI());
    }
}
