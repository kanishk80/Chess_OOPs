package oops_project.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oops_project.App;
import oops_project.model.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Duration;
import javafx.scene.control.Label;

public class ChessBoardController {

    @FXML
    private GridPane chessboard;

    private Board board;
    private Piece selectedPiece;
    private int selectedRow;
    private int selectedCol;
    private int whiteTimeRemaining;
    private int blackTimeRemaining;
    private Timeline whiteTimer;
    private Timeline blackTimer;
    @FXML
    private Label whiteTimerLabel;
    @FXML
    private Label blackTimerLabel;
    
    @FXML
    public void initialize() {
        board = new Board();
        if (App.getGameMode().equals("Timer")) {
            int timerSeconds = App.getTimerMinutes() * 60; // Convert minutes to seconds
            whiteTimeRemaining = timerSeconds;
            blackTimeRemaining = timerSeconds;
            System.out.println("Timer Mode Initialized. White: " + whiteTimeRemaining + " Black: " + blackTimeRemaining);
            setupTimers();
            updateTimerLabels(); // Initial display of timer
        }
        createChessboard();
        updateTurnHighlight();

    }
    
    private void setupTimers() {
        whiteTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (whiteTimeRemaining > 0) {
                whiteTimeRemaining--;
                updateTimerLabels();
            } else {
                stopTimers();
                showWinnerPopup("Black wins! White's time is up.");
            }
        }));
        whiteTimer.setCycleCount(Timeline.INDEFINITE);
    
        blackTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (blackTimeRemaining > 0) {
                blackTimeRemaining--;
                updateTimerLabels();
            } else {
                stopTimers();
                showWinnerPopup("White wins! Black's time is up.");
            }
        }));
        blackTimer.setCycleCount(Timeline.INDEFINITE);
    // Start the appropriate timer based on the turn
        if (board.isWhiteTurn()) {
            System.out.println("Starting White Timer");
            whiteTimer.play();
        } else {
            System.out.println("Starting Black Timer");
            blackTimer.play();
        }
    //Intial timer label update
    updateTimerLabels();
    }
    
    private void stopTimers() {
        if (whiteTimer != null) whiteTimer.stop();
        if (blackTimer != null) blackTimer.stop();
    }

    private void createChessboard() {
        chessboard.getChildren().clear(); // Clear any existing nodes
    
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Create a cell background (Rectangle)
                Rectangle cell = new Rectangle(64, 64);
                cell.setFill((row + col) % 2 == 0 ? Color.BEIGE : Color.BROWN);
    
                // Add the cell to the chessboard first to ensure it's rendered in the background
                chessboard.add(cell, col, row);
    
                final int currentRow = row;
                final int currentCol = col;
    
                // Check if there's a piece at this position
                Piece piece = board.getPieceAt(row, col);
                if (piece != null) {
                    // Load the piece image
                    String color = piece.getColor();
                    String pieceName = piece.getClass().getSimpleName().toLowerCase();
                    String imagePath = "/images/" + color.toLowerCase() + "-" + pieceName + ".png";
    
                    ImageView pieceImage = new ImageView(getClass().getResource(imagePath).toExternalForm());
                    pieceImage.setFitWidth(64);
                    pieceImage.setFitHeight(64);
                    pieceImage.setPreserveRatio(true);
    
                    // Create a transparent button to overlay the piece image
                    Button pieceButton = new Button();
                    pieceButton.setGraphic(pieceImage);
                    pieceButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
    
                    // Set an action for the button
                    pieceButton.setOnAction(e -> handlePieceClick(currentRow, currentCol));
    
                    // Add the button (with the piece image) on top of the cell
                    chessboard.add(pieceButton, col, row);
                }
            }
        }
    }
    

    private void handlePieceClick(int row, int col) {
        Piece piece = board.getPieceAt(row, col);
        if (piece == null) {
            System.out.println("No piece selected.");
            return;
        }

        if ((board.isWhiteTurn() && !piece.getColor().equals("white")) ||
            (!board.isWhiteTurn() && !piece.getColor().equals("black"))) {
            System.out.println("Not your turn!");
            return;
        }

        selectedPiece = piece;
        selectedRow = row;
        selectedCol = col;
        System.out.println("Piece selected at: " + row + ", " + col);

        highlightValidMoves(piece, row, col);
    }

    private void highlightValidMoves(Piece piece, int row, int col) {
        createChessboard();

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (piece.isValidMove(row, col, r, c, board)) {
                    Rectangle highlight = new Rectangle(64, 64, Color.LIGHTGREEN);
                    highlight.setOpacity(0.5);
                    final int toRow = r;
                    final int toCol = c;

                    highlight.setOnMouseClicked(e -> movePiece(row, col, toRow, toCol));
                    chessboard.add(highlight, c, r);
                }
            }
        }
    }
    private PieceContainer<Piece> capturedPieces = new PieceContainer<>();

    private void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = board.getPieceAt(fromRow, fromCol);
    
        // Simulate the move
        Piece capturedPiece = board.getPieceAt(toRow, toCol);
        board.movePiece(fromRow, fromCol, toRow, toCol);

        // Convert coordinates to chess notation
        String from = "" + (char) ('a' + fromCol) + (fromRow + 1);
        String to = "" + (char) ('a' + toCol) + (toRow + 1);
        
         // Log the move using the overloaded method
        System.out.println(piece.getClass().getSimpleName() + " moved from " + from + " to " + to);

        // Capture logic
        if (capturedPiece != null) {
        capturedPieces.addPiece(capturedPiece); // Store the captured piece
        System.out.println("Captured - " + capturedPiece.getColor() + " " + capturedPiece.getClass().getSimpleName());
    }
        // Check if the move leaves the king in check
        if (board.isKingInCheck(piece.getColor())) {
            System.out.println("Move not allowed: King would be in check!");
            board.movePiece(toRow, toCol, fromRow, fromCol); // Undo the move
            if (capturedPiece != null) {
                board.placePiece(capturedPiece, toRow, toCol); // Restore the captured piece
            }
            return; // Exit without switching turns
        }
        
        // Handle pawn promotion
        if (piece instanceof Pawn && (toRow == 0 || toRow == 7)) {
            promotePawn(toRow, toCol, piece.getColor());
        }
    
        // Checkmate and stalemate detection
        String opponentColor = board.isWhiteTurn() ? "black" : "white";
    
        if (board.isKingInCheck(opponentColor)) {
            if (!board.hasValidMoves(opponentColor)) {
                System.out.println("Checkmate! " + (board.isWhiteTurn() ? "White" : "Black") + " wins!");
                stopTimers(); // Stop timers on game end
                showWinnerPopup((board.isWhiteTurn() ? "White" : "Black") + " wins by Checkmate!");
                return; // Exit after game end
            } else {
                System.out.println((board.isWhiteTurn() ? "Black" : "White") + " is in check!");
            }
        } else if (!board.hasValidMoves(opponentColor)) {
            System.out.println("Stalemate! The game is a draw.");
            stopTimers(); // Stop timers on game end
            showWinnerPopup("Stalemate! The game is a draw.");
            return; // Exit after game end
        }
    
        // Toggle turn
        board.toggleTurn();
        updateTurnHighlight();
        // Handle timers in Timer Mode
        if (App.getGameMode().equals("Timer")) {
            if (board.isWhiteTurn()) {
                if (blackTimer != null) blackTimer.stop();
                if (whiteTimer != null) whiteTimer.play();
            } else {
                if (whiteTimer != null) whiteTimer.stop();
                if (blackTimer != null) blackTimer.play();
            }
            // Update the timer labels after each move
        updateTimerLabels();
        }
    
        // Update the timer labels after each move
        updateTimerLabels();
    
        // Reset selection and update the board
        selectedPiece = null; // Reset the selected piece
        createChessboard(); // Refresh the board
    }
    
    

    private void promotePawn(int row, int col, String color) {
        Piece piece = board.getPieceAt(row, col);
    
        // Ensure the piece is a Pawn before promotion
        if (piece instanceof Pawn) {
            Pawn pawn = (Pawn) piece; // Explicit coercion
            System.out.println("Promoting " + color + " pawn at " + (char) ('a' + col) + (row + 1));
    
            // Show the dialog for piece selection
            List<String> choices = Arrays.asList("Queen", "Rook", "Bishop", "Knight");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Queen", choices);
            dialog.setTitle("Pawn Promotion");
            dialog.setHeaderText("Select a piece to promote your pawn to:");
    
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(choice -> {
                switch (choice) {
                    case "Queen":
                        board.placePiece(new Queen(color), row, col);
                        break;
                    case "Rook":
                        board.placePiece(new Rook(color), row, col);
                        break;
                    case "Bishop":
                        board.placePiece(new Bishop(color), row, col);
                        break;
                    case "Knight":
                        board.placePiece(new Knight(color), row, col);
                        break;
                }
            });
    
            // Log the promotion
            System.out.println("Pawn promoted to " + board.getPieceAt(row, col).getClass().getSimpleName() + "!");
        } else {
            System.out.println("Error: Only pawns can be promoted!");
        }
    }
    
    @FXML
    public void backToMenu() {
    try {
        App.setRoot("menu");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @FXML
    public void exitGame() {
    System.exit(0); // Exit the application
}

private void updateTimerLabels() {
    if (whiteTimerLabel != null && blackTimerLabel != null) {
        whiteTimerLabel.setText("White Time: " + formatTime(whiteTimeRemaining));
        blackTimerLabel.setText("Black Time: " + formatTime(blackTimeRemaining));
    } else {
        System.out.println("Timer labels are not properly initialized.");
    }
}
private String formatTime(int totalSeconds) {
    int minutes = totalSeconds / 60;
    int seconds = totalSeconds % 60;
    return String.format("%02d:%02d", minutes, seconds);
}
private void updateTurnHighlight() {
    if (board.isWhiteTurn()) {
        whiteTimerLabel.getStyleClass().add("active");
        blackTimerLabel.getStyleClass().remove("active");
    } else {
        blackTimerLabel.getStyleClass().add("active");
        whiteTimerLabel.getStyleClass().remove("active");
    }
}

@FXML
private VBox popupContainer;

@FXML
private Label popupMessage;

private void showWinnerPopup(String message) {
    popupMessage.setText(message);
    popupContainer.setVisible(true); // Make the popup visible
}
@FXML
private void handlePlayAgain() {
    try {
        App.setRoot("chessboard"); // Reload the chessboard view
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
