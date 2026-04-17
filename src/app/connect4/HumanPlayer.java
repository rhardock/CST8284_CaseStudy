package app.connect4;

public class HumanPlayer extends Player {

    public HumanPlayer(String name, char symbol) {
        super(name, symbol); // Calls the constructor of the abstract Player class
    }

    @Override
    public int getMove(Board board) {
        // In a GUI, the move is triggered by a button click.
        // We'll leave this as a placeholder for now to satisfy the abstract requirement.
        return -1;
    }
}
