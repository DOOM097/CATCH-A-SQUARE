package testgame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Optional;
import java.util.Random;

public class SquareGame {

    private int score = 0;
    private int totalClicks = 0;
    private double time = 0.0;
    private double squareSpeed = 1.0;
    private boolean isStopped = false;

    private Rectangle square;
    private Label scoreLabel;
    private Label totalClicksLabel;
    private Random random;
    private Timeline timeline;

    public void startGame(Stage primaryStage) {
        random = new Random();

        // Создание квадратика
        square = new Rectangle(50, 50, Color.RED);
        square.setX(200);
        square.setY(200);

        // Создание табло для отображения счета и общего количества кликов
        scoreLabel = new Label("                  Score:  0  ");
        scoreLabel.setAlignment(Pos.CENTER);

        scoreLabel.setStyle("-fx-font-size: 20px; -fx-border-width: 2 0 2px 0; -fx-border-color: black;");

        totalClicksLabel = new Label("                                  |   Total Clicks: 0                  ");
        totalClicksLabel.setAlignment(Pos.CENTER);
        totalClicksLabel.setStyle("-fx-font-size: 20px; -fx-border-width: 2 0 2px 0; -fx-border-color: black;");


        // Размещение квадратика и табло на панели
        Pane gamePane = new Pane();
        gamePane.getChildren().addAll(square, scoreLabel, totalClicksLabel);

        // Создание главной сцены
        BorderPane root = new BorderPane();
        root.setCenter(gamePane);

        // Установка сцены
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Square Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Обработка нажатий на квадратик и пустое место
        square.setOnMouseClicked(this::handleSquareClick);
        gamePane.setOnMouseClicked(this::handleEmptyAreaClick);

        // Обновление местоположения квадратика каждую секунду
        timeline = new Timeline(new KeyFrame(Duration.seconds(1 / squareSpeed), e -> {
            if (!isStopped) {
                moveSquare();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void moveSquare() {
        // Генерация случайных координат для квадратика
        double x = random.nextDouble() * 300;
        double y = random.nextDouble() * 400;

        // Изменение местоположения квадратика
        square.setX(x);
        square.setY(y);

        // Увеличение времени
        time++;

        // Вывод времени и скорости в консоль

        System.out.println("Speed: " + squareSpeed);
    }

    private void handleSquareClick(MouseEvent event) {
        // Проверка, был ли клик на квадратике
        if (event.getTarget() == square) {
            // Увеличение счета при клике на квадратик
            score++;
            scoreLabel.setText("                  Score:  " + score);

            // Остановка квадратика
            isStopped = true;

            // Вывод окна с сообщением
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Поздравляем!");
            alert.setHeaderText("ВЫ ПОЙМАЛИ КВАДРАТИК!");
            alert.setContentText("Хотите увеличить скорость?");
            ButtonType buttonTypeYes = new ButtonType("ДА");
            ButtonType buttonTypeNo = new ButtonType("НЕТ");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();

            // Обработка выбора в окне
            if (result.isPresent() && result.get() == buttonTypeYes) {
                // Увеличение скорости перемещения квадратика при выборе "ДА"
                squareSpeed /= 0.9;
                timeline.stop();
                timeline.getKeyFrames().clear();
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1 / squareSpeed), e -> {
                    if (!isStopped) {
                        moveSquare();
                    }
                }));
                timeline.play();
                isStopped = false;
            } else {
                // Закрытие программы при выборе "НЕТ"
                System.exit(0);
            }
        }
    }

    private void handleEmptyAreaClick(MouseEvent event) {
        // Проверка, был ли клик на пустой области
        if (event.getTarget() == square.getParent()) {
            // Увеличение общего количества кликов при клике на пустое место
            totalClicks++;
            totalClicksLabel.setText("                                  |   Total Clicks:  " + totalClicks + "                     ");
            totalClicksLabel.setAlignment(Pos.CENTER);
            totalClicksLabel.setStyle("-fx-font-size: 20px; -fx-border-width: 2 0 2px 0; -fx-border-color: black;");
        }
    }
}
