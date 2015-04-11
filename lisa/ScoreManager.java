package lisa;

// Created by lisa on 4/9/15.

public class ScoreManager {
    private static int highScore = 0;

    //Checks if current points is greater than the current high points.
    //updates high points and returns true if so.

    //These methods are useful for displaying points at the end of the game

    public static String newHighScore(Score newScore) {

        if (newScore.points > highScore) {
            highScore = newScore.points;
            System.out.println("New high points " + highScore);
            return "New High Score!!";

        } else {
            return "";
        }
    }

    public static String getStringHighScore() {
        return Integer.toString(highScore);
    }
}
