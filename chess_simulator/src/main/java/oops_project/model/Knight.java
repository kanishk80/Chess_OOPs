package oops_project.model;

public class Knight extends Piece {

    public Knight(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        // L-shaped move (2 squares in one direction, 1 square in the other)
        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            Piece target = board.getPieceAt(endRow, endCol);
            return target == null || !target.getColor().equals(color);
        }
        return false;
    }
}
