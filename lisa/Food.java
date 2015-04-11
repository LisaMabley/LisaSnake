package lisa;

// Created by lisa on 4/7/15.

import java.awt.*;
import java.util.Random;

public abstract class Food {
    // Abstract superclass for all food types

    // Location coordinates in game grid
    protected int foodX;
    protected int foodY;

    // The amount the snake grows when eating each type of food
    protected int growthIncrement;

    // Number of points for eating this food
    protected int pointsForEating;

    protected Color displayColor;

    protected void placeFood(){
        // Place food in random empty square
        Random randomNumberGenerator = new Random();

        int gameStage = SnakeGame.getGameStage();

        if ( gameStage == SnakeGame.BEFORE_GAME ||
                gameStage == SnakeGame.DURING_GAME ) {
            boolean foundValidLocation = false;
            while (!foundValidLocation) {
                // Generate random location
                foodX = randomNumberGenerator.nextInt(SnakeGame.xSquares);
                foodY = randomNumberGenerator.nextInt(SnakeGame.ySquares);

                // Check if location is empty
                if (FoodManager.isEmptySquare(foodX, foodY)) {
                    GridSquares.setSquare(foodX, foodY, GridSquares.CONTAINS_FOOD);
                    foundValidLocation = true;
                }
            }
        }
    }

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }
}
