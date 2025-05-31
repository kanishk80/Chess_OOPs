package oops_project.model;

import java.util.ArrayList;
import java.util.List;

public class PieceContainer<T extends Piece> {
    private List<T> pieces;

    public PieceContainer() {
        this.pieces = new ArrayList<>();
    }

    // Add a piece to the container
    public void addPiece(T piece) {
        pieces.add(piece);
    }

    // Get a piece from the container
    public T getPiece(int index) {
        return pieces.get(index);
    }

    // Get the total number of pieces
    public int size() {
        return pieces.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Captured Pieces: \n");
        for (T piece : pieces) {
            result.append(piece.getColor()).append(" ").append(piece.getClass().getSimpleName()).append("\n");
        }
        return result.toString();
    }
}
