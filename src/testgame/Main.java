package testgame;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Instantiate your game class
        SquareGame squareGame = new SquareGame();

        // Call the method to start the game
        squareGame.startGame(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
