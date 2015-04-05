package lisa;

import java.net.MalformedURLException;
import java.util.Timer;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class SnakeGame {

	public static int xPixelMaxDimension = 701;  //Pixels in window. 501 to have 50-pixel squares plus 1 to draw a border on last square
	public static int yPixelMaxDimension = 701;

	public static int xSquares;
	public static int ySquares;

	public final static int squareSize = 50;

	protected static Snake snake;

	protected static Kibble kibble;

	protected static Score score;

	// User options
	public static boolean warpWallsOn = true;
	public static boolean mazesOn = false;
	public static boolean soundsOn = true;

	static final int GAME_PAUSED = 0;
	static final int BEFORE_GAME = 1;
	static final int DURING_GAME = 2;
	static final int GAME_OVER = 3;
	static final int GAME_WON = 4;   //The values are not important. The important thing is to use the constants 
	//instead of the values so you are clear what you are setting. Easy to forget what number is Game over vs. game won
	//Using constant names instead makes it easier to keep it straight. Refer to these variables 
	//using statements such as lisa.SnakeGame.GAME_OVER

	private static int gameStage = BEFORE_GAME;  // Use this to figure out what should be happening.
	//Other classes like Snake and DrawSnakeGamePanel will need to query this, and change it's value

	protected static long clockInterval = 500; //controls game speed
	//Every time the clock ticks, the snake moves
	//This is the time between clock ticks, in milliseconds
	//1000 milliseconds = 1  second.

	static JFrame snakeFrame;
	static DrawSnakeGamePanel snakePanel;
	//Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too.
	//http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
	//http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html

//	 Game sounds to be accessible from any class
	public static AudioClip eatKibbleSound;
	public static AudioClip warpWallSound;
	private static AudioClip loseGameSound;
	private static AudioClip wonGameSound;

	private static void createAndShowGUI() {
		//Create and set up the window.
		snakeFrame = new JFrame();
		snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
		snakeFrame.setUndecorated(true); //hide title bar
		snakeFrame.setVisible(true);
		snakeFrame.setResizable(false);

//		GameOptionsGUI optionsPanel = new GameOptionsGUI();
//		snakeFrame.add(optionsPanel);

		snakePanel = new DrawSnakeGamePanel(snake, kibble, score);
		snakePanel.setFocusable(true);
		snakePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents

		snakeFrame.add(snakePanel);
		snakePanel.addKeyListener(new GameControls(snake));

		setGameStage(BEFORE_GAME);
		snakeFrame.setVisible(true);
	}

	private static void initializeGame() {
		//set up score, snake and first kibble
		xSquares = xPixelMaxDimension / squareSize;
		ySquares = yPixelMaxDimension / squareSize;

		snake = new Snake(xSquares, ySquares, squareSize);
		kibble = new Kibble(snake);
		score = new Score();

		gameStage = BEFORE_GAME;

		try {
			// Create game sounds
			URL kibbleSoundUrl = new URL("https://raw.githubusercontent.com/LisaMabley/LisaSnake/master/kibble.wav");
			URL warpWallSoundUrl = new URL("https://raw.githubusercontent.com/LisaMabley/LisaSnake/master/warp.wav");
			URL loseGameSoundUrl = new URL("https://raw.githubusercontent.com/LisaMabley/LisaSnake/master/lostgame.wav");
			URL wonGameSoundUrl = new URL("https://raw.githubusercontent.com/LisaMabley/LisaSnake/master/wongame.wav");
			eatKibbleSound = Applet.newAudioClip(kibbleSoundUrl);
			warpWallSound = Applet.newAudioClip(warpWallSoundUrl);
			loseGameSound = Applet.newAudioClip(loseGameSoundUrl);
			wonGameSound = Applet.newAudioClip(wonGameSoundUrl);

		} catch (MalformedURLException e) {
			// Those urls are fine ... but required to have this
			System.out.println("Bad URL");
			System.out.println(e.getStackTrace());
		}
	}

	protected static void newGame() {
		Timer timer = new Timer();
		GameClock clockTick = new GameClock(snake, kibble, score, snakePanel);
		timer.scheduleAtFixedRate(clockTick, 0 , clockInterval);
	}

	public static void resumePausedGame() {
		Timer timer = new Timer();
		GameClock clockTick = new GameClock(snake, kibble, score, snakePanel);
		timer.scheduleAtFixedRate(clockTick, 0 , clockInterval);
	}

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initializeGame();
				createAndShowGUI();
			}
		});
	}

	public static int getGameStage() {
		return gameStage;
	}

	// WAS UNUSED LISA COMMENTED OUT
//	public static boolean gameEnded() {
//		if (gameStage == GAME_OVER || gameStage == GAME_WON){
//			return true;
//		}
//		return false;
//	}

	public static void setGameStage(int gameStage) {
		SnakeGame.gameStage = gameStage;

		if (soundsOn) {
			switch (gameStage) {
				case 3:
					loseGameSound.play();
					break;
				case 4:
					wonGameSound.play();
					break;
			}
		}
	}

	// Setters for User Options LISA ADDED
	public static void setGridSize(int pixels) {
		xPixelMaxDimension = pixels;
		yPixelMaxDimension = pixels;
	}

	public static void setSoundsOn(boolean soundsOn) {
		SnakeGame.soundsOn = soundsOn;
	}

	public static void setWarpWallsOn(boolean warpWallsOn) {
		SnakeGame.warpWallsOn = warpWallsOn;
	}

	public static void setMazesOn(boolean mazesOn) {
		SnakeGame.mazesOn = mazesOn;
	}
}
