package oops_project.model;

public abstract class Piece {

    public abstract boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board);
    
    protected String color; // "white" or "black"
    
    public Piece(String color) {
        this.color = color;
    }
    public String getColor() {
        return color;
    }
    
    
    public void move(int endRow, int endCol) {
        // Default implementation (can be overridden by subclasses)
    }
}
