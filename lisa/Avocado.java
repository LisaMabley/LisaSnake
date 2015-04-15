package lisa;

/** Created by lisa on 4/6/15.
 * Avocados are just obstacles, not food, except during a momentary interval when they are ripe.
 * Each time a new one is placed, or one is eaten by the snake, they become less likely to appear.
 * They disappear altogether after a set interval. **/

import java.awt.*;

public class Avocado extends Food {

    private static int timeToRipen = 15;
    private static int ripenessDuration = 5;
    private int willBeRipeForThisMuchLonger;
    private int ripeness;
    protected int age;
    protected static int maxAge = 75;
    private boolean isEdible = false;
    private static Color unripeColor = new Color(51, 51, 0);
    private static Color ripeColor = new Color(86, 130, 3);
    private static Color overripeColor = new Color(180, 200, 130);

    // Constructor
    public Avocado() {
        this.growthIncrement = 3;
        this.displayColor = unripeColor;
        this.pointsForEating = 2;
        this.ripeness = 0;
        this.willBeRipeForThisMuchLonger = ripenessDuration;

        this.placeFood();
    }

    public void incrementRipeness() {
        // Updates an avocado's ripeness each clock tick
        this.ripeness ++;
        this.age ++;

        // Sets avocado color and isEdible if correct duration has elapsed
        if (this.ripeness == timeToRipen) {
            this.isEdible = true;
            this.displayColor = ripeColor;

        // If avocado is already ripe, increments how long it should remain ripe
        } else if (ripeness > timeToRipen) {
            this.willBeRipeForThisMuchLonger --;
        }

        // FINDBUGS
        // This used to be a switch statement without a
        // default case. I changed it to if/else if to correct.
        if (this.willBeRipeForThisMuchLonger == 1) {
            // Changes to warning color one clock tick before changing back to unripe
            this.displayColor = overripeColor;

        } else if (this.willBeRipeForThisMuchLonger == 0) {
            // Changes avocado back to unripe if has been ripe for ripenessDuration
            this.isEdible = false;
            this.ripeness = 0;
            this.willBeRipeForThisMuchLonger = ripenessDuration;
            this.displayColor = unripeColor;
        }
    }

    // Getter
    public boolean isAvocadoEdible() {
        return this.isEdible;
    }
}
