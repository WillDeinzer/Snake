package com.example.snake;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Random;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class SnakeBackend {
    private Rectangle[][] grid = new Rectangle[20][20];
    private int score = 1;
    private SnakeChain snake;

    private boolean gameStillGoing = true;

    private int[] currentApplePosition;

    private String highScore = "1";

    public SnakeBackend() {
        snake = new SnakeChain();
    }
    public GridPane createGrid() {
        GridPane mainGrid = new GridPane();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Rectangle rect = new Rectangle(20,20, Color.BLACK);
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(1.0);
                mainGrid.add(rect,j,i);
                grid[i][j] = rect;
            }
        }
        currentApplePosition = setApplePosition();
        return mainGrid;
    }

    public void reset() {
        snake = new SnakeChain();
        score = 1;
        gameStillGoing = true;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                grid[i][j].setFill(Color.BLACK);
                grid[i][j].setStroke(Color.BLACK);
            }
        }
        currentApplePosition = setApplePosition();
    }

    public boolean isGameStillGoing() {
        return gameStillGoing;
    }

    public Direction getDirection() {
        return snake.getCurrentDirection();
    }

    public void snakeMove() {
        SnakeSegment frontSegment = snake.getFront();
        SnakeSegment backSegment = snake.getBack();
        if (score > 1) {
            grid[backSegment.getYPos()][backSegment.getXPos()].setFill(Color.BLACK);
            grid[backSegment.getYPos()][backSegment.getXPos()].setStroke(Color.BLACK);
        } else {
            grid[frontSegment.getYPos()][frontSegment.getXPos()].setFill(Color.BLACK);
            grid[frontSegment.getYPos()][frontSegment.getXPos()].setStroke(Color.BLACK);
        }
        if (!snake.advanceSnake()) {
            gameStillGoing = false;
            return;
        }
        if (frontSegment.getXPos() < 0 || frontSegment.getXPos() >= 20 || frontSegment.getYPos() < 0
                || frontSegment.getYPos() >= 20 || snake.getSize() == 400) {
            gameStillGoing = false;
        }
        grid[frontSegment.getYPos()][frontSegment.getXPos()].setFill(Color.GREEN);
        grid[frontSegment.getYPos()][frontSegment.getXPos()].setStroke(Color.DARKGREEN);
        if (snake.segmentAtNoFront(frontSegment.getXPos(), frontSegment.getYPos())) {
            gameStillGoing = false;
        }
        if (frontSegment.getXPos() == currentApplePosition[0] && frontSegment.getYPos() == currentApplePosition[1]) {
            snake.addSegment();
            currentApplePosition = setApplePosition();
            score++;
        }
    }

    public int getScore() {
        return score;
    }

    public void changeDirection(Direction direction) {
        snake.updateCurrentDirection(direction);
    }


    public int[] setApplePosition() {
        Random random = new Random();
        int randomX = random.nextInt(20);
        int randomY = random.nextInt(20);
        while (snake.segmentAt(randomX, randomY)) {
            randomX = random.nextInt(20);
            randomY = random.nextInt(20);
        }
        grid[randomY][randomX].setFill(Color.RED);
        return new int[] {randomX, randomY};
    }



    public String updateHighScore() throws FileNotFoundException {
        File highScoreFile = new File("highscore.txt");
        if (highScoreFile.exists()) {
            Scanner highScoreReader = new Scanner(highScoreFile);
            if (highScoreReader.hasNext()) {
                highScore = highScoreReader.next();
            }
            highScoreReader.close();
        }
        PrintWriter highScoreWriter = new PrintWriter(highScoreFile);
        if (score > Integer.parseInt(highScore)) {
            highScoreWriter.println(score);
            highScore = Integer.toString(score);
        } else {
            highScoreWriter.println(highScore);
        }
        highScoreWriter.close();
        return highScore;
    }
}