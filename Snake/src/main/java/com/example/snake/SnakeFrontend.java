package com.example.snake;
import javafx.application.Application;
import java.io.IOException;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;



public class SnakeFrontend extends Application {

    private Timeline timeline;
    private boolean changedDirection = false;

    public void start(Stage stage) throws IOException {
        //Start screen
        VBox start = new VBox();
        start.setAlignment(Pos.CENTER);
        Text title = new Text("Snake");
        title.setFont(new Font(50));
        title.setFill(Color.BLACK);
        Button startButton = new Button("Start Game");
        start.getChildren().addAll(title, startButton);
        start.setSpacing(20.0);
        Scene startScene = new Scene(start, 500, 300);
        stage.setScene(startScene);
        stage.show();

        //Game screen
        VBox gameScreen = new VBox();
        SnakeBackend game = new SnakeBackend();
        GridPane gameGrid = game.createGrid();
        Text scoreCounter = new Text("Score: " + game.getScore());
        scoreCounter.setFont(new Font(30));
        Text currentHighScore = new Text("Hello");
        try {
            currentHighScore.setText("High Score: " + game.updateHighScore());
        } catch (IOException e) {
            System.out.println("IOException");
        }
        currentHighScore.setFont(new Font(20));
        scoreCounter.setFill(Color.BLACK);
        gameScreen.setAlignment(Pos.CENTER);
        gameGrid.setAlignment(Pos.CENTER);
        gameScreen.getChildren().addAll(scoreCounter, gameGrid, currentHighScore);
        gameScreen.setSpacing(20.0);
        Scene gameScene = new Scene(gameScreen, 600, 600);

        gameScene.setOnKeyPressed(e -> {
            if (!changedDirection) {
                switch(e.getCode()) {
                    case UP:
                        if (game.getDirection() == Direction.DOWN) {
                            break;
                        }
                        changedDirection = true;
                        game.changeDirection(Direction.UP);
                        break;
                    case DOWN:
                        if (game.getDirection() == Direction.UP) {
                            break;
                        }
                        changedDirection = true;
                        game.changeDirection(Direction.DOWN);
                        break;
                    case LEFT:
                        if (game.getDirection() == Direction.RIGHT) {
                            break;
                        }
                        changedDirection = true;
                        game.changeDirection(Direction.LEFT);
                        break;
                    case RIGHT:
                        if (game.getDirection() == Direction.LEFT) {
                            break;
                        }
                        changedDirection = true;
                        game.changeDirection(Direction.RIGHT);
                        break;
                }
            }
        });

        VBox endResult = new VBox();
        Text endResultText1;
        if (game.getScore() == 400) {
            endResultText1 = new Text("You won!");
        } else {
            endResultText1 = new Text("You died!");
        }
        Text endResultText2 = new Text("Your score was " + game.getScore());
        endResultText1.setFont(new Font(50));
        endResultText2.setFont(new Font(50));
        endResult.setAlignment(Pos.CENTER);
        Button playAgain = new Button("Play again");
        playAgain.setOnAction(e -> {
            game.reset();
            stage.setScene(startScene);
        });
        endResult.getChildren().addAll(endResultText1, endResultText2, playAgain);
        endResult.setSpacing(20.0);
        Scene endScene = new Scene(endResult, 500, 500);

        startButton.setOnAction(e -> {
            stage.setScene(gameScene);
            stage.show();
            timeline = new Timeline(new KeyFrame(Duration.seconds(0.09), event -> {
                if (game.isGameStillGoing()) {
                    game.snakeMove();
                    changedDirection = false;
                    scoreCounter.setText("Score: " + game.getScore());
                } else {
                    try {
                        currentHighScore.setText("High Score: " + game.updateHighScore());
                    } catch (IOException ex) {
                        System.out.println("IOException");
                    }
                    endResultText2.setText("Your score was " + game.getScore());
                    stage.setScene(endScene);
                    timeline.stop();
                }
            }));

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }
}
