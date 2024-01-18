package com.example.snake;
import javafx.scene.shape.Rectangle;

public class SnakeSegment {
    private int xPos;
    private int yPos;
    private SnakeSegment nextSegment;

    public SnakeSegment(int xPos, int yPos, SnakeSegment nextSegment) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.nextSegment = nextSegment;
    }
    public void changeXPos(int newXPos) {
        xPos = newXPos;
    }

    public int getXPos() {
        return xPos;
    }

    public void changeYPos(int newYPos) {
        yPos = newYPos;
    }

    public int getYPos() {
        return yPos;
    }

    public SnakeSegment getNextSegment() {
        return nextSegment;
    }
}
