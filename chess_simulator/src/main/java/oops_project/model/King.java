package oops_project.model;

public class King extends Piece {

    public King(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        // Move one square in any direction
        if (rowDiff <= 1 && colDiff <= 1) {
            Piece target = board.getPieceAt(endRow, endCol);
            return target == null || !target.getColor().equals(color);
        }

        // Castling logic can be added here
        return false;
    }
}
