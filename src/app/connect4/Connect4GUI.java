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
                add(buttons[r][c]);
            }
        }

        pack(); // Sizes the window to fit the buttons
        setSize(700, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Use the event dispatch thread for Swing for "suitable coding"
        SwingUtilities.invokeLater(() -> new Connect4GUI());
    }
}
