package app.connect4;

/**
 * Abstract class representing a game participant.
 * Demonstrates: Inheritance, Abstraction, and Encapsulation.
 */
public abstract class Player {
    private String name;
    private char symbol;

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    // Encapsulation: Accessors for private fields
    public String getName() { return name; }
    public char getSymbol() { return symbol; }

    /**
     * This is an abstract method. Every subclass MUST implement its own version.
     * This demonstrates Polymorphism.
     */
    public abstract int getMove(Board board);

    @Override
    public String toString() {
        return name + " (" + symbol + ")";
    }
}
