package lisa;

// Created by lisa on 4/7/15.

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class SoundPlayer {
    // Creates and plays game event sounds

    private static AudioClip eatSound;
    private static AudioClip warpWallSound;
    private static AudioClip loseGameSound;
    private static AudioClip wonGameSound;

    public SoundPlayer() {
        try {
            // Create game sounds from GitHub URLs
            URL eatSoundUrl = new URL("https://raw.githubusercontent.com/LisaMabley/LisaSnake/master/kibble.wav");
            URL warpWallSoundUrl = new URL("https://raw.githubusercontent.com/LisaMabley/LisaSnake/master/warp.wav");
            URL loseGameSoundUrl = new URL("https://raw.githubusercontent.com/LisaMabley/LisaSnake/master/lostgame.wav");
            URL wonGameSoundUrl = new URL("https://raw.githubusercontent.com/LisaMabley/LisaSnake/master/wongame.wav");
            eatSound = Applet.newAudioClip(eatSoundUrl);
            warpWallSound = Applet.newAudioClip(warpWallSoundUrl);
            loseGameSound = Applet.newAudioClip(loseGameSoundUrl);
            wonGameSound = Applet.newAudioClip(wonGameSoundUrl);

        } catch (MalformedURLException exception) {
            // Those urls are fine ... but required to have this
            System.out.println("Bad URL");
            System.out.println(exception.getStackTrace().toString());
        }
    }

    public static void playEatSound() {
        if (SnakeGame.soundsOn) {
            eatSound.play();
        }
    }

    public static void playWarpWallSound() {
        if (SnakeGame.soundsOn) {
            warpWallSound.play();
        }
    }

    public static void playLoseGameSound() {
        if (SnakeGame.soundsOn) {
            loseGameSound.play();
        }
    }

    public static void playWonGameSound() {
        if (SnakeGame.soundsOn) {
            wonGameSound.play();
        }
    }
}
