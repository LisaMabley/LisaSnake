package lisa;

// Created by lisa on 4/6/15.

import java.awt.*;

public class Prey extends Food {
    // Prey can always warp, whether warp walls for player are on or not

    public Prey() {
        this.growthIncrement = 3;
        this.displayColor = Color.PINK;
        this.pointsForEating = 2;

        this.placeFood();
    }

//    public void evade(Snake snake) {
//        // Prey moves one square in a random direction each clock tick.
//        // Or occasionally stays in place (taking a moment's rest)
//        // If move would place prey in snake's body, or adjacent to its head,
//        // Choose a different move
//
//        boolean preyInSnake = true;
//        boolean preyAdjacentToSnakeHead = true;
//        int testX = 0;
//        int testY = 0;
//
//        while (preyInSnake || preyAdjacentToSnakeHead) {
//            int directionToMove = randomNumberGenerator.nextInt(10);
//            testY = preyY;
//            testX = preyX;
//
//            switch (directionToMove) {
//
//                case 0:
//                    // Move up
//                    if (testY > 0) {
//                        testY --;
//
//                    } else {
//                        testY = SnakeGame.ySquares - 1;
//                    }
//                    System.out.println("moving up");
//                    break;
//
//                case 1:
//                    // Move down
//                    if (testY < (SnakeGame.ySquares - 1)) {
//                        testY ++;
//
//                    } else {
//                        testY = 0;
//                    }
//                    System.out.println("moving down");
//                    break;
//
//                case 2:
//                    // Move left
//                    if (testX > 0) {
//                        testX --;
//
//                    } else {
//                        testX = SnakeGame.xSquares - 1;
//                    }
//                    System.out.println("moving left");
//                    break;
//
//                case 3:
//                    // Move right
//                    if (testX < (SnakeGame.xSquares - 1)) {
//                        testX ++;
//
//                    } else {
//                        testX = 0;
//                    }
//                    System.out.println("moving right");
//                    break;
//
//                default:
//                    // Don't move.
//                    System.out.println("Not moving");
//                    break;
//            }
//
//            preyInSnake = snake.isSnakeSegment(testX, testY);
//            preyAdjacentToSnakeHead = snake.isAdjacentToSnakeHead(testX, testY);
//        }
//
//        preyX = testX;
//        preyY = testY;
//    }
//
//    private boolean canPreyMoveUp(Snake snake, Kibble kibble) {
//        boolean canMoveUp = true;
//        int testY;
//
//        if (preyY == 0) {
//            testY = SnakeGame.ySquares - 1;
//
//        } else {
//            testY = preyY --;
//        }
//
//        // If moving up would put prey in snake body or adjacent to snake head
//        if (snake.isSnakeSegment(preyX, testY) || snake.isAdjacentToSnakeHead(preyX, testY)) {
//            canMoveUp = false;
//        }
//
//        // If moving up would put prey in kibble square
//        if (kibble.getKibbleX() == preyX && kibble.getKibbleY() == testY) {
//            canMoveUp = false;
//        }
//
//        return canMoveUp;
//    }
//
//    private boolean canPreyMoveDown(Snake snake, Kibble kibble) {
//        int testY;
//
//        if (preyY == SnakeGame.ySquares - 1) {
//            testY = 0;
//
//        } else {
//            testY = preyY ++;
//        }
//
//        boolean canMoveDown = isMoveValid(snake, kibble, preyX, testY);
//
//        return canMoveDown;
//    }
//
//    private boolean isMoveValid(Snake snake, Kibble kibble, int proposedX, int proposedY) {
//        boolean canMove = true;
//
//        // If moving would put prey in snake body or adjacent to snake head
//        if (snake.isSnakeSegment(proposedX, proposedY) || snake.isAdjacentToSnakeHead(proposedX, proposedY)) {
//            canMove = false;
//        }
//
//        // If moving up would put prey in kibble square
//        if (kibble.getKibbleX() == proposedX && kibble.getKibbleY() == proposedY) {
//            canMove = false;
//        }
//
//        return canMove;
//    }
}
