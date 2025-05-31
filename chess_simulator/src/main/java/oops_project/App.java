package oops_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static int timerMinutes = 5; // Default timer   
    private static String gameMode = "Regular"; // Default mode

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("menu"), 560, 560);
        scene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void setGameMode(String mode) {
        gameMode = mode;
    }
    
    public static String getGameMode() {
        return gameMode;
    }
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        System.out.println("Loading FXML: " + App.class.getResource(fxml + ".fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void setTimerMinutes(int minutes) {
        timerMinutes = minutes;
    }

    public static int getTimerMinutes() {
        return timerMinutes;
    }
    public static void main(String[] args) {
        launch();
    }
    
}