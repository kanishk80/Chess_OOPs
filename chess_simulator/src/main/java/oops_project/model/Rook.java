package oops_project.model;

public class Rook extends Piece {

    public Rook(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        // Moves in a straight line (horizontal or vertical)
        if (startRow == endRow || startCol == endCol) {
            // Check if path is clear
            int rowStep = Integer.compare(endRow, startRow);
            int colStep = Integer.compare(endCol, startCol);

            int currentRow = startRow + rowStep;
            int currentCol = startCol + colStep;

            while (currentRow != endRow || currentCol != endCol) {
                if (board.getPieceAt(currentRow, currentCol) != null) {
                    return false; // Path is blocked
                }
                currentRow += rowStep;
                currentCol += colStep;
            }

            // Ensure destination is either empty or an opponent's piece
            Piece target = board.getPieceAt(endRow, endCol);
            return target == null || !target.getColor().equals(color);
        }
        return false;
    }
}
