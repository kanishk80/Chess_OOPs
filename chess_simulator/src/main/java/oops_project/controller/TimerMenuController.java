package oops_project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import oops_project.App;

public class TimerMenuController {
    @FXML
    private TextField timerInput;

    @FXML
    public void startGameWithTimer() {
        String input = timerInput.getText();
        int timerMinutes;
        try {
            timerMinutes = Integer.parseInt(input);
            if (timerMinutes <= 0) {
                throw new NumberFormatException();
            }
            App.setTimerMinutes(timerMinutes);
            App.setGameMode("Timer");
            System.out.println("Game mode set to Timer");
            App.setRoot("chessboard");
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid positive integer.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to start the timer mode.");
        }
    }
    

    @FXML
    private void backToMenu() {
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
private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

}
