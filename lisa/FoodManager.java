package lisa;

// Created by lisa on 4/6/15.

import java.util.ArrayList;
import java.util.Random;

public class FoodManager {

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

                // reduce avocado probability
                avocadoProbability ++;
            }

            if (!avocados.isEmpty()) {
                for (Avocado avocado : avocados) {
                    avocado.incrementRipeness();

                    if (avocado.age == avocado.maxAge) {
                        disappearingAvocados.add(avocado);
                    }

                    if (didSnakeEat(avocado) && avocado.isAvocadoEdible()) {
                        SnakeGame.snake.justAteMustGrowThisMuch += avocado.growthIncrement;
                        eatenAvocado = avocado;
                        avocadoProbability ++;

                    } else if (didSnakeEat(avocado)) {
                        SnakeGame.setGameStage(SnakeGame.GAME_OVER);
                    }
                }

                if (eatenAvocado != null) {
                    avocados.remove(eatenAvocado);
                    eatenAvocado = null;
                }

                if (!disappearingAvocados.isEmpty()) {
                    for (Avocado oldAvocado : disappearingAvocados)
                    avocados.remove(oldAvocado);
                    disappearingAvocados.clear();
                }
            }
        }
    }

    public static boolean isEmptySquare(int X, int Y) {
        return GridSquares.getSquareValue(X, Y) == GridSquares.EMPTY;
    }

    private static boolean didSnakeEat(Food food) {
        //Is any food in the snake? It would be in the same square as the snake's head
        if (SnakeGame.snake.isSnakeHead(food.foodX, food.foodY)) {
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
        avocados.clear();
        avocadoProbability = 20;
    }
}
