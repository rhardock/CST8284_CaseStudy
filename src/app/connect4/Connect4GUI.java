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
	private Player player1;
	private Player player2;

	private static final long serialVersionUID = 1L;
	private Board board;
    private JButton[][] buttons; // Visual representation of the grid
    private GamePrefs prefs;
    private int totalWins;
    private int totalLosses;
    private int totalDraws;
    private JLabel statsLabel; // To display wins and losses

    private char currentSymbol = 'R'; // Tracks whose turn it is

    public Connect4GUI() {
    	prefs = new GamePrefs();

    	int rows = prefs.getRows();
    	int cols = prefs.getCols();
    	this.totalWins = prefs.getWinCount();
    	this.totalLosses = prefs.getLossCount();
        this.totalDraws = prefs.getDrawCount();

        board = new Board(rows, cols); // Uses the default 6x7 constructor (Overloading)
        buttons = new JButton[rows][cols];

        player1 = new HumanPlayer("Human", 'R');
        player2 = new AIPlayer("Computer", 'Y');

        setupUI();
    }

    private void setupUI() {

    	int rows = board.getRows();
        int cols = board.getCols();
    	setTitle("CST8284 Connect 4 - " + rows + "x" + cols);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 1. Create the stats label
        statsLabel = new JLabel("Wins: " + totalWins +
                                " | Losses: " + totalLosses +
                                " | Draws: " + totalDraws, SwingConstants.CENTER);
        statsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(statsLabel, BorderLayout.NORTH);

        // 2. Create the grid panel
        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                buttons[r][c] = new JButton();
                buttons[r][c].setBackground(Color.WHITE);

                // Captured for the Lambda below
                final int col = c;

                // This tells the button: "When clicked, run the handleMove method for this column"
                buttons[r][c].addActionListener(e -> handleMove(col));

                gridPanel.add(buttons[r][c]);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        pack(); // Sizes the window to fit the buttons
        setSize(500, 550); // an initial size that should fit on most monitors

        setLocationRelativeTo(null); // centers the window on the screen
        setVisible(true);

        setupMenu();
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem settingsItem = new JMenuItem("Settings");
        JMenuItem quitItem = new JMenuItem("Quit");

        settingsItem.addActionListener(e -> {
            SettingsDialog dialog = new SettingsDialog(this, prefs);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                dispose(); // Close current game
                new Connect4GUI(); // Restart with new dimensions from GamePrefs
            }
        });

        // Simple exit logic
        quitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(settingsItem);
        fileMenu.addSeparator(); // Adds a nice visual line
        fileMenu.add(quitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void resetGame() {
        // 1. Clear the data model
        board.resetBoard();

        int rows = board.getRows();
        int cols = board.getCols();

        // 2. Clear the visual buttons
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                buttons[r][c].setBackground(Color.WHITE);
            }
        }

        // 3. Reset the turn to Red
        currentSymbol = 'R';
    }

    private void handleMove(int col) {
        // 1. Human Turn (Player 1)
        if (currentSymbol == player1.getSymbol()) {
            if (board.dropPiece(col, player1.getSymbol())) {
                updateBoardDisplay();

                if (!isGameOver(player1)) {
                    currentSymbol = player2.getSymbol();
                    triggerAIMove();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Column Full!");
            }
        }
    }

    private boolean isGameOver(Player player) {
        if (board.checkWin(player.getSymbol())) {
            handleGameOver(player);
            return true;
        }
        if (board.isFull()) {
            handleGameOver(null);
            return true;
        }
        return false;
    }

    private void triggerAIMove() {
        // Small delay so the AI doesn't move instantly
        Timer timer = new Timer(500, e -> {
            // Polymorphism in action: calling getMove on a Player type
            int aiCol = player2.getMove(board);

            // aiCol == -1 means board is full
            if (aiCol != -1 && board.dropPiece(aiCol, player2.getSymbol())) {
                updateBoardDisplay();
            }

            if (!isGameOver(player2)) {
                currentSymbol = player1.getSymbol();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void handleGameOver(Player winner) {
        if (winner == null) {
            totalDraws++;
            JOptionPane.showMessageDialog(this, "The game ended in a Draw!");
        }
        else {
            String winnerName = winner.getName();

            // Update logic based on which player won
            if (winner == player1) {
                totalWins++;
            } else {
                totalLosses++;
            }
            JOptionPane.showMessageDialog(this, "Game Over! " + winnerName + " wins.");
        }

        statsLabel.setText("Wins: " + totalWins + " | Losses: " + totalLosses + " | Draws: " + totalDraws);
        prefs.save(board.getRows(), board.getCols(), totalWins, totalLosses, totalDraws);

        resetGame();
    }

    private void updateBoardDisplay() {
        char[][] grid = board.getGrid();

        int rows = board.getRows();
        int cols = board.getCols();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
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
