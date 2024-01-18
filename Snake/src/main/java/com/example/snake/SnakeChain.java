package com.example.snake;

public class SnakeChain {
    private SnakeSegment back;
    private SnakeSegment front;
    private int size;
    private Direction currentDirection;

    public SnakeChain() {
        this.back = null;
        this.front = new SnakeSegment(15, 15, null);
        size = 1;
        currentDirection = Direction.UP;
    }

    public SnakeSegment getFront() {
        return front;
    }

    public SnakeSegment getBack() {
        return back;
    }

    public void updateCurrentDirection(Direction direction) {
        currentDirection = direction;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public boolean advanceSnake() {
        boolean movePossible = true;
        SnakeSegment current = back;
        if (size != 1) {
            while (current.getNextSegment() != null) {
                current.changeXPos(current.getNextSegment().getXPos());
                current.changeYPos(current.getNextSegment().getYPos());
                current = current.getNextSegment();
            }
        }
        switch(currentDirection) {
            case UP:
                front.changeYPos(front.getYPos() - 1);
                if (front.getYPos() > 29 || front.getYPos() < 0) {
                    movePossible = false;
                }
                break;
            case DOWN:
                front.changeYPos(front.getYPos() + 1);
                if (front.getYPos() > 29 || front.getYPos() < 0) {
                    movePossible = false;
                }
                break;
            case LEFT:
                front.changeXPos(front.getXPos() - 1);
                if (front.getXPos() > 29 || front.getXPos() < 0) {
                    movePossible = false;
                }
                break;
            case RIGHT:
                front.changeXPos(front.getXPos() + 1);
                if (front.getXPos() > 29 || front.getXPos() < 0) {
                    movePossible = false;
                }
                break;
        }
        return movePossible;
    }

    public int getSize() {
        return size;
    }

    public void addSegment() {
        SnakeSegment newSegment = new SnakeSegment(0,0,front);
        if (size == 1) {
            switch(currentDirection) {
                case UP:
                    newSegment = new SnakeSegment(front.getXPos(), front.getYPos() + 1, front);
                    break;
                case DOWN:
                    newSegment = new SnakeSegment(front.getXPos(), front.getYPos() - 1, front);
                    break;
                case LEFT:
                    newSegment = new SnakeSegment(front.getXPos() + 1, front.getYPos(), front);
                    break;
                case RIGHT:
                    newSegment = new SnakeSegment(front.getXPos() - 1, front.getYPos(), front);
                    break;
            }
        } else {
            SnakeSegment nextFromBack = back.getNextSegment();
            if (nextFromBack.getXPos() > back.getXPos()) {
                if (back.getXPos() == 0) {
                    if (back.getYPos() == 0) {
                        newSegment = new SnakeSegment(back.getXPos(), back.getYPos() + 1, back);
                    } else {
                        newSegment = new SnakeSegment(back.getXPos(), back.getYPos() - 1, back);
                    }
                } else {
                    newSegment = new SnakeSegment(back.getXPos() - 1, back.getYPos(), back);
                }
            } else if (nextFromBack.getXPos() < back.getXPos()) {
                if (back.getXPos() == 29) {
                    if (back.getYPos() == 0) {
                        newSegment = new SnakeSegment(back.getXPos(), back.getYPos() + 1, back);
                    } else {
                        newSegment = new SnakeSegment(back.getXPos(), back.getYPos() - 1, back);
                    }
                } else {
                    newSegment = new SnakeSegment(back.getXPos() + 1, back.getYPos(), back);
                }
            } else if (nextFromBack.getYPos() > back.getYPos()) {
                if (back.getYPos() == 0) {
                    if (back.getXPos() == 0) {
                        newSegment = new SnakeSegment(back.getXPos() + 1, back.getYPos(), back);
                    } else {
                        newSegment = new SnakeSegment(back.getXPos() - 1, back.getYPos(), back);
                    }
                } else {
                    newSegment = new SnakeSegment(back.getXPos(), back.getYPos() - 1, back);
                }
            } else {
                if (back.getYPos() == 29) {
                    if (back.getXPos() == 0) {
                        newSegment = new SnakeSegment(back.getXPos() + 1, back.getYPos(), back);
                    } else {
                        newSegment = new SnakeSegment(back.getXPos() - 1, back.getYPos(), back);
                    }
                } else {
                    newSegment = new SnakeSegment(back.getXPos(), back.getYPos() + 1, back);
                }
            }
        }
        back = newSegment;
        size++;
    }

    public boolean segmentAt(int xPos, int yPos) {
        SnakeSegment current = back;
        while (current != null) {
            if (current.getXPos() == xPos && current.getYPos() == yPos) {
                return true;
            }
            current = current.getNextSegment();
        }
        return false;
    }

    public boolean segmentAtNoFront(int xPos, int yPos) {
        if (size == 1) {
            return false;
        }
        SnakeSegment current = back;
        while (current != front) {
            if (current.getXPos() == xPos && current.getYPos() == yPos) {
                return true;
            }
            current = current.getNextSegment();
        }
        return false;
    }
}
