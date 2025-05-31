package oops_project.model;

public class Board {
    private Piece[][] board;
    private boolean whiteTurn = true; // White starts the game

    public Board() {
        board = new Piece[8][8];
        initializeBoard();
    }

    public Piece getPieceAt(int row, int col) {
        return board[row][col];
    }

    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
    Piece movingPiece = board[fromRow][fromCol];
    // Move logic
    board[toRow][toCol] = movingPiece;
    board[fromRow][fromCol] = null;
}

// Overloaded method using chess notation
public void movePiece(String from, String to) {
    int fromRow = from.charAt(1) - '1';
    int fromCol = from.charAt(0) - 'a';
    int toRow = to.charAt(1) - '1';
    int toCol = to.charAt(0) - 'a';

    movePiece(fromRow, fromCol, toRow, toCol); // Call the original method
}

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    // Expos a public method to toggle the turn
    public void toggleTurn() {
        whiteTurn = !whiteTurn; // Toggle turn
    }
    // Add this method to allow placing a piece at a specific position
    public void placePiece(Piece piece, int row, int col) {
        board[row][col] = piece;
    }

    public boolean isKingInCheck(String color) {
        int kingRow = -1, kingCol = -1;

        // Find the king
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof King && piece.getColor().equals(color)) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        // Check if any opponent piece can attack the king
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && !piece.getColor().equals(color)) {
                    if (piece.isValidMove(row, col, kingRow, kingCol, this)) {
                        return true; // King is in check
                    }
                }
            }
        }
        return false; // King is safe
    }

    public boolean hasValidMoves(String color) {
        for (int fromRow = 0; fromRow < 8; fromRow++) {
            for (int fromCol = 0; fromCol < 8; fromCol++) {
                Piece piece = board[fromRow][fromCol];
                if (piece != null && piece.getColor().equals(color)) {
                    for (int toRow = 0; toRow < 8; toRow++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
                            if (piece.isValidMove(fromRow, fromCol, toRow, toCol, this)) {
                                // Simulate the move
                                Piece temp = board[toRow][toCol];
                                board[toRow][toCol] = piece;
                                board[fromRow][fromCol] = null;

                                boolean kingInCheck = isKingInCheck(color);

                                // Undo the move
                                board[fromRow][fromCol] = piece;
                                board[toRow][toCol] = temp;

                                if (!kingInCheck) {
                                    return true; // Found a valid move
                                }
                            }
                        }
                    }
                }
            }
        }
        return false; // No valid moves
    }

    private void initializeBoard() {
        // Add pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn("black");
            board[6][i] = new Pawn("white");
        }

        // Add rooks
        board[0][0] = new Rook("black");
        board[0][7] = new Rook("black");
        board[7][0] = new Rook("white");
        board[7][7] = new Rook("white");

        // Add knights
        board[0][1] = new Knight("black");
        board[0][6] = new Knight("black");
        board[7][1] = new Knight("white");
        board[7][6] = new Knight("white");

        // Add bishops
        board[0][2] = new Bishop("black");
        board[0][5] = new Bishop("black");
        board[7][2] = new Bishop("white");
        board[7][5] = new Bishop("white");

        // Add queens
        board[0][3] = new Queen("black");
        board[7][3] = new Queen("white");

        // Add kings
        board[0][4] = new King("black");
        board[7][4] = new King("white");
    }
}
