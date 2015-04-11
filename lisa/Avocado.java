package lisa;

// Created by lisa on 4/6/15.

import java.awt.*;

public class Avocado extends Food {
    // Avocados are just obstacles, not food
    // Except during a momentary interval

    private static int timeToRipen = 15;
    private static int ripenessDuration = 5;
    private int willBeRipeForThisMuchLonger;
    private int ripeness;
    public boolean isEdible = false;
    private static Color unripeColor = new Color(51, 51, 0);
    private static Color ripeColor = new Color(86, 130, 3);
    private static Color overripeColor = new Color(180, 200, 130);

    public Avocado() {
        this.growthIncrement = 3;
        this.displayColor = unripeColor;
        this.pointsForEating = 2;
        this.ripeness = 0;
        this.willBeRipeForThisMuchLonger = ripenessDuration;

        this.placeFood();
    }

    public void incrementRipeness() {
        this.ripeness ++;
        if (this.ripeness == timeToRipen) {
            this.isEdible = true;
            this.displayColor = ripeColor;

        } else if (ripeness > timeToRipen) {
            this.willBeRipeForThisMuchLonger --;
        }

        switch (this.willBeRipeForThisMuchLonger) {
            case 1:
                this.displayColor = overripeColor;
                break;

            case 0:
                this.isEdible = false;
                this.ripeness = 0;
                this.willBeRipeForThisMuchLonger = ripenessDuration;
                this.displayColor = unripeColor;
                break;
        }
    }
}
