package oops_project.model;

public class Queen extends Piece {

    public Queen(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        // Combine Rook and Bishop movement
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        if (startRow == endRow || startCol == endCol) {
            // Rook movement logic
            return new Rook(color).isValidMove(startRow, startCol, endRow, endCol, board);
        } else if (rowDiff == colDiff) {
            // Bishop movement logic
            return new Bishop(color).isValidMove(startRow, startCol, endRow, endCol, board);
        }
        return false;
    }
}
