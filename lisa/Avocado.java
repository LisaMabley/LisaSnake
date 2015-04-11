package lisa;

// Created by lisa on 4/6/15.

import java.awt.*;

public class Avocado extends Food {
    // Avocados are just obstacles, not food
    // Except during a momentary interval when they are ripe

    private static int timeToRipen = 15;
    private static int ripenessDuration = 5;
    private int willBeRipeForThisMuchLonger;
    private int ripeness;
    public boolean isEdible = false;
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

        // Sets avocado color and isEdible if correct duration has elapsed
        if (this.ripeness == timeToRipen) {
            this.isEdible = true;
            this.displayColor = ripeColor;

        // If avocado is already ripe,
        // increments how long it should remain ripe
        } else if (ripeness > timeToRipen) {
            this.willBeRipeForThisMuchLonger --;
        }

        switch (this.willBeRipeForThisMuchLonger) {
            // Changes to warning color one clock tick
            // before changing back to unripe
            case 1:
                this.displayColor = overripeColor;
                break;

            // Changes avocado back to unripe
            case 0:
                this.isEdible = false;
                this.ripeness = 0;
                this.willBeRipeForThisMuchLonger = ripenessDuration;
                this.displayColor = unripeColor;
                break;
        }
    }
}
