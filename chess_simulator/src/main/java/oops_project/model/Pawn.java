package oops_project.model;

public class Pawn extends Piece {
    private boolean hasMoved = false; // Tracks if the pawn has moved

    public Pawn(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        int direction = color.equals("white") ? -1 : 1;

        // Single forward move
        if (startCol == endCol && board.getPieceAt(endRow, endCol) == null) {
            if (endRow == startRow + direction) {
                return true; // Standard one-step forward
            }

            // Double forward move (only from the initial row)
            if (!hasMoved && ((color.equals("white") && startRow == 6) || (color.equals("black") && startRow == 1)) &&
                endRow == startRow + 2 * direction &&
                board.getPieceAt(startRow + direction, startCol) == null && // Path is clear
                board.getPieceAt(endRow, endCol) == null) {
                return true;
            }
        }

        // Diagonal capture
        if (Math.abs(startCol - endCol) == 1 && endRow == startRow + direction) {
            Piece target = board.getPieceAt(endRow, endCol);
            return target != null && !target.getColor().equals(color);
        }

        return false; // Invalid move otherwise
    }

    @Override
    public void move(int endRow, int endCol) {
        super.move(endRow, endCol); // Call the superclass move logic (optional)
        hasMoved = true; // Mark the pawn as having moved
    }
}
