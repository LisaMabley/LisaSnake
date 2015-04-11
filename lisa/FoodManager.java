package lisa;

// Created by lisa on 4/6/15.

import java.util.ArrayList;
import java.util.Random;

public class FoodManager {

    protected static Kibble kibble;
    protected static ArrayList<Avocado> avocados;
    protected static Avocado eatenAvocado;
    private static Snake snake;
    private static Score score;
    // 1 in 25 chance of new avocado each turn
    private static int avocadoProbability = 25;

    // Constructor
    public FoodManager(Snake sn, Score thisGameScore) {
        kibble = new Kibble();
        snake = sn;
        avocados = new ArrayList<Avocado>();
        score = thisGameScore;

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
            snake.justAteMustGrowThisMuch += kibble.growthIncrement;
        }

        if (SnakeGame.avocadosOn) {
            int randomChoice = randomNumberGenerator.nextInt(avocadoProbability);
            if (randomChoice == 0) {
                Avocado newAvocado = new Avocado();
                avocados.add(newAvocado);
            }

            if (!avocados.isEmpty()) {
                for (Avocado avocado : avocados) {
                    avocado.incrementRipeness();

                    if (didSnakeEat(avocado) && avocado.isEdible) {
                        snake.justAteMustGrowThisMuch += avocado.growthIncrement;
                        eatenAvocado = avocado;

                    } else if (didSnakeEat(avocado)) {
                        SnakeGame.setGameStage(SnakeGame.GAME_OVER);
                    }
                }

                if (eatenAvocado != null) {
                    avocados.remove(eatenAvocado);
                    eatenAvocado = null;
                }
            }
        }
    }

    public static boolean isEmptySquare(int X, int Y) {
        return GridSquares.getSquareValue(X, Y) == GridSquares.EMPTY;
    }

    private static boolean didSnakeEat(Food food) {
        //Is any food in the snake? It would be in the same square as the snake's head
        if (snake.isSnakeHead(food.foodX, food.foodY)) {
            // If so, play sound and update game points
            SoundPlayer.playEatSound();
            score.increaseScore(food.pointsForEating);

            // Check to see if game has been won
            if (GridSquares.wonGame()) {
                SnakeGame.setGameStage(SnakeGame.GAME_WON);
            }

            return true;
        }
        return false;
    }
}
