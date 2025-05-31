package oops_project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import oops_project.App;

public class MenuController {

    @FXML
    private void startRegularGame() {
        App.setGameMode("Regular");
        try {
            App.setRoot("chessboard");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to start the game.");
        }
    }
    

    @FXML
    public void startTimerGame() {
        try {
            // Transition to the timer menu view for timer-based mode
            App.setRoot("timerMenu");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to start the timer mode.");
        }
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void exitApplication() {
        System.exit(0);
}
}
