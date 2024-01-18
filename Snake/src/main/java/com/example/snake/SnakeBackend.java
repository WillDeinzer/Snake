package com.example.snake;

import java.util.Random;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class SnakeBackend {
    private Rectangle[][] grid = new Rectangle[30][30];
    private int score = 1;
    private SnakeChain snake;

    private boolean gameStillGoing = true;

    private int[] currentApplePosition;

    public SnakeBackend() {
        snake = new SnakeChain();
    }
    public GridPane createGrid() {
        GridPane mainGrid = new GridPane();
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
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
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
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
        if (frontSegment.getXPos() < 0 || frontSegment.getXPos() >= 30 || frontSegment.getYPos() < 0
                || frontSegment.getYPos() >= 30 || snake.getSize() == 900) {
            gameStillGoing = false;
        }
        grid[frontSegment.getYPos()][frontSegment.getXPos()].setFill(Color.GREEN);
        grid[frontSegment.getYPos()][frontSegment.getXPos()].setStroke(Color.DARKGREEN);
        if (snake.segmentAtNoFront(frontSegment.getXPos(), frontSegment.getYPos())) {
            gameStillGoing = false;
        }
        if (frontSegment.getXPos() == currentApplePosition[0] && frontSegment.getYPos() == currentApplePosition[1]) {
            currentApplePosition = setApplePosition();
            snake.addSegment();
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
        int randomX = random.nextInt(30);
        int randomY = random.nextInt(30);
        while (snake.segmentAt(randomX, randomY)) {
            randomX = random.nextInt(30);
            randomY = random.nextInt(30);
        }
        grid[randomY][randomX].setFill(Color.RED);
        return new int[] {randomX, randomY};
    }
}