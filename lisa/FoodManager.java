package lisa;

// Created by lisa on 4/6/15.

import java.util.ArrayList;
import java.util.Random;

public class FoodManager {
    // Responsible for keeping track of where all food objects
    // are in the game grid, updating them each clock tick
    // and determining if the snake has eaten any of them

    protected static Kibble kibble;
    protected static ArrayList<Avocado> avocados;
    protected static Avocado eatenAvocado;
    protected static ArrayList<Avocado> disappearingAvocados;
    // 1 in 20 chance of new avocado each turn
    private static int avocadoProbability = 20;

    // Constructor
    public FoodManager() {
        kibble = new Kibble();
        avocados = new ArrayList<Avocado>();
        disappearingAvocados = new ArrayList<Avocado>();

        if (SnakeGame.avocadosOn) {
            Avocado newAvocado = new Avocado();
            avocados.add(newAvocado);
        }
    }

    public static void updateFood() {
        // Updates all food objects each clock tick
        Random randomNumberGenerator = new Random();

        if (didSnakeEat(kibble)) {
            kibble.placeFood();
            SnakeGame.snake.justAteMustGrowThisMuch += kibble.growthIncrement;
        }

        if (SnakeGame.avocadosOn) {
            int randomChoice = randomNumberGenerator.nextInt(avocadoProbability);
            if (randomChoice == 0) {
                Avocado newAvocado = new Avocado();
                avocados.add(newAvocado);

                // Reduce avocado probability
                // Useful as fewer open squares remain on GameGrid
                avocadoProbability ++;
            }

            if (!avocados.isEmpty()) {
                for (Avocado avocado : avocados) {
                    // Update each avocado's ripeness
                    avocado.incrementRipeness();
//                    System.out.println("Avocado at " + avocado.foodX + ", " + avocado.foodY + " value is " + GridSquares.getSquareValue(avocado.foodX, avocado.foodY));

                    if (avocado.age == avocado.maxAge) {
                        // Mark any that have reached max age for later removal
                        disappearingAvocados.add(avocado);
                    }

                    if (didSnakeEat(avocado) && avocado.isAvocadoEdible()) {
                        // Snake ate edible avocado, good for him!
                        SnakeGame.snake.justAteMustGrowThisMuch += avocado.growthIncrement;
                        eatenAvocado = avocado;
                        avocadoProbability ++;

                    } else if (didSnakeEat(avocado)) {
                        // Snake ate inedible avocado, game over
                        SnakeGame.setGameStage(SnakeGame.GAME_OVER);
                    }
                }

                if (eatenAvocado != null) {
                    // Now that we're done iterating over avocado list,
                    // it's safe to remove the one that's been eaten
                    avocados.remove(eatenAvocado);
                    eatenAvocado = null;
                }

                if (!disappearingAvocados.isEmpty()) {
                    // Also remove any that have reached their maxAge
                    for (Avocado oldAvocado : disappearingAvocados)
                    avocados.remove(oldAvocado);
                    disappearingAvocados.clear();
                }
            }
        }
    }

    private static boolean didSnakeEat(Food food) {
        //Is any food in the snake? It would be in the same square as the snake's head
        if (GridSquares.getSquareValue(food.foodX,food.foodY) == GridSquares.SNAKE_HEAD) {
            // If so, play sound and update game points
            SoundPlayer.playEatSound();
            SnakeGame.currentScore.increaseScore(food.pointsForEating);

            // Check to see if game has been won
            if (GridSquares.wonGame()) {
                SnakeGame.setGameStage(SnakeGame.GAME_WON);
            }

            return true;
        }
        return false;
    }

    protected void reset() {
        // Reset foodManager for new game
        avocados.clear();
        avocadoProbability = 20;
    }
}
