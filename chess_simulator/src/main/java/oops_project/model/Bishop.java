package oops_project.model;

public class Bishop extends Piece {

    public Bishop(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        // Diagonal move (rowDiff == colDiff)
        if (rowDiff == colDiff) {
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
