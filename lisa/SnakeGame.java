package lisa;

import java.util.Timer;
import javax.swing.*;

public class SnakeGame {

	// Number of pixels in window.
	public static int xPixelMaxDimension = 601;
	public static int yPixelMaxDimension = 601;

	public static int xSquares;
	public static int ySquares;

	public final static int squareSize = 50;

	protected static GridSquares gridSquares;
	protected static Snake snake;
	protected static Score currentScore;
	protected static ScoreManager scoreManager;
	protected static FoodManager foodManager;

	// User options
	public static boolean soundsOn = true;
	public static boolean warpWallsOn = false;
	public static boolean avocadosOn = false;

	static final int GAME_PAUSED = 0;
	static final int BEFORE_GAME = 1;
	static final int DURING_GAME = 2;
	static final int GAME_OVER = 3;
	static final int GAME_WON = 4;
	//The values are not important. The important thing is to use the constants
	//instead of the values so you are clear what you are setting. Easy to forget what number is Game over vs. game won
	//Using constant names instead makes it easier to keep it straight. Refer to these variables 
	//using statements such as lisa.SnakeGame.GAME_OVER

	private static int gameStage = BEFORE_GAME;
	// Use this to figure out what should be happening.
	//Other classes like Snake and DrawSnakeGamePanel will need to query this, and change it's value

	protected static long clockInterval = 500;
	//controls game speed
	//Every time the clock ticks, the snake moves
	//This is the time between clock ticks, in milliseconds
	//1000 milliseconds = 1  second.

	static JFrame snakeFrame;
	static DrawSnakeGamePanel snakePanel;
	static JPanel optionsPanel;
	static GameOptionsGUI optionsFormGUI = new GameOptionsGUI();
	static JPanel gameOverPanel;
	static GameOverGUI gameOverGUI = new GameOverGUI();

	//Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too.
	//http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
	//http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html

	static SoundPlayer soundPlayer;

	protected static void displayOptionsGUI() {
		if (gameStage == GAME_WON || gameStage == GAME_OVER) {
			snakeFrame.remove(gameOverPanel);
			gameStage = BEFORE_GAME;
		}

		// Show options GUI
		optionsPanel = optionsFormGUI.rootPanel;
		optionsPanel.setFocusable(true);
		optionsPanel.requestFocusInWindow();
		snakeFrame.add(optionsPanel);
		snakeFrame.validate();
		snakeFrame.repaint();
	}

	protected static void displayGameGrid() {
		snakeFrame.remove(optionsPanel);
		snakePanel = new DrawSnakeGamePanel(snake);
		snakeFrame.add(snakePanel);
		snakeFrame.setFocusable(true);
		snakeFrame.addKeyListener(new GameControls(snake));
		snakeFrame.validate();
		snakeFrame.repaint();

		newGame();
	}

	private static void initializeGame() {
		// Set up snake and grid
		// Called by OptionsGUI when player changes grid size
		xSquares = xPixelMaxDimension / squareSize;
		ySquares = yPixelMaxDimension / squareSize;
		gridSquares = new GridSquares(xSquares, ySquares, squareSize);
		snake = new Snake();
		currentScore = new Score();
		foodManager = new FoodManager(snake, currentScore);
	}

	private static void initializeOnStartup() {
		//Create and set up the window.
		snakeFrame = new JFrame();
		snakeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
		snakeFrame.setUndecorated(true); //hide title bar
		snakeFrame.setResizable(false);
		snakeFrame.setFocusable(true);
		snakeFrame.addKeyListener(new GameControls(snake));
		snakeFrame.setVisible(true);

		soundPlayer = new SoundPlayer();
		scoreManager = new ScoreManager();
	}

	protected static void newGame() {
		gameStage = DURING_GAME;
		Timer timer = new Timer();
		GameClock clockTick = new GameClock(snake, currentScore, snakePanel);
		timer.scheduleAtFixedRate(clockTick, 0 , clockInterval);
		snakePanel.repaint();
	}

	public static void resumePausedGame() {
		Timer timer = new Timer();
		GameClock clockTick = new GameClock(snake, currentScore, snakePanel);
		timer.scheduleAtFixedRate(clockTick, 0, clockInterval);
	}

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initializeGame();
				initializeOnStartup();
				displayOptionsGUI();
			}
		});
	}

	protected static void displayGameOverGUI() {
		snakeFrame.remove(snakePanel);

		gameOverPanel = gameOverGUI.rootPanel;
		snakeFrame.add(gameOverPanel);
		snakeFrame.validate();
		snakeFrame.repaint();
	}

	public static int getGameStage() {
		return gameStage;
	}

	public static void setGameStage(int gameStage) {
		SnakeGame.gameStage = gameStage;

		switch (gameStage) {
			case 3:
				SoundPlayer.playLoseGameSound();
				break;
			case 4:
				SoundPlayer.playWonGameSound();
				break;
		}
	}

	public static void reset() {
		gameStage = BEFORE_GAME;
		GridSquares.reset();
		snake.createStartSnake();
		currentScore = new Score();
		foodManager = new FoodManager(snake, currentScore);
	}

	// Getters & Setters for User Options
	public static void setGameSpeed(int millisecondsPerTick) {
		clockInterval = millisecondsPerTick;
	}

	public static void setGridSize(int pixels) {
		xPixelMaxDimension = pixels;
		yPixelMaxDimension = pixels;
		snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
		initializeGame();
	}

	public static void setSoundsOn(boolean soundsOn) { SnakeGame.soundsOn = soundsOn; }

	public static void setWarpWallsOn(boolean warpWallsOn) {
		SnakeGame.warpWallsOn = warpWallsOn;
	}

	public static void setAvocadosOn(boolean avocadosOn) {
		SnakeGame.avocadosOn = avocadosOn;
	}
}
