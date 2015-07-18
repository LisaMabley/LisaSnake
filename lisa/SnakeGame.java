package lisa;

import java.util.Timer;
import javax.swing.*;

public class SnakeGame {

	// Number of pixels in window.
	public static int xPixelMaxDimension = 601;
	public static int yPixelMaxDimension = 601;

	// Number of squares in grid
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
	// The values are not important. The important thing is to use the constants
	// instead of the values so you are clear what you are setting. Easy to forget what number is Game over vs. game won
	// Using constant names instead makes it easier to keep it straight. Refer to these variables
	// using statements such as SnakeGame.GAME_OVER

	private static int gameStage = BEFORE_GAME;
	// Use this to figure out what should be happening.
	// Other classes like Snake and DrawSnakeGamePanel will need to query this, and change its value

	protected static long clockInterval = 500;
	// Controls game speed
	// Every time the clock ticks, the snake moves
	// This is the time between clock ticks, in milliseconds
	// 1000 milliseconds = 1  second.

	static JFrame snakeFrame;
	static DrawSnakeGamePanel snakePanel;
	static JPanel optionsPanel;
	static JPanel gameOverPanel;
	static GameOptionsGUI gameOptionsGUI;
	static GameOverGUI gameOverGUI;

	// Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too.
	// http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
	// http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html

	static SoundPlayer soundPlayer;

	public static void main(String[] args) {
		// STEP 1: Schedule a job for the event-dispatching thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initializeOnStartup();
				initializeGame();
				displayOptionsGUI();
			}
		});
	}

	private static void initializeOnStartup() {
		// STEP 2: Create and set up all elements that can persist over multiple games
		snakeFrame = new JFrame();
		snakeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
		snakeFrame.setUndecorated(true); //hide title bar
		snakeFrame.setResizable(false);
		snakeFrame.setFocusable(true);
		snakeFrame.addKeyListener(new GameControls());
		snakeFrame.setVisible(true);

		xSquares = xPixelMaxDimension / squareSize;
		ySquares = yPixelMaxDimension / squareSize;
		gridSquares = new GridSquares(xSquares, ySquares, squareSize);

		soundPlayer = new SoundPlayer();
		scoreManager = new ScoreManager();

		gameOptionsGUI = new GameOptionsGUI();
		gameOverGUI = new GameOverGUI();
	}

	private static void initializeGame() {
		// STEP 3: Set up snake and grid
		// Called by SnakeGame at the beginning of each game
		// Called by OptionsGUI when grid size is changed
		xSquares = xPixelMaxDimension / squareSize;
		ySquares = yPixelMaxDimension / squareSize;
		gridSquares = new GridSquares(xSquares, ySquares, squareSize);
		snake = new Snake();
		currentScore = new Score();
		foodManager = new FoodManager();
	}

	protected static void displayOptionsGUI() {
		// STEP 4: Display game options GUI
		// Called by SnakeGame at the beginning of play
		// Called by GameOverGUI when Play Again button pressed

		// If this is not first round of play, remove game over panel
		if (gameStage == GAME_OVER || gameStage == GAME_WON) {
			snakeFrame.remove(gameOverPanel);
			gameStage = BEFORE_GAME;
		}

		// Show options GUI
		optionsPanel = gameOptionsGUI.rootPanel;
		optionsPanel.setFocusable(true);
		optionsPanel.requestFocusInWindow();
		snakeFrame.add(optionsPanel);
		snakeFrame.validate();
		snakeFrame.repaint();
	}

	protected static void displayGameGrid() {
		// STEP 5: Display game grid
		// Called by GameOptionsGUI when Play button clicked
		snakeFrame.remove(optionsPanel);
		snakePanel = new DrawSnakeGamePanel(snake);
		snakeFrame.add(snakePanel);
		snakeFrame.setFocusable(true);
		snakeFrame.addKeyListener(new GameControls());
		snakeFrame.validate();
		snakeFrame.repaint();

		newGame();
	}

	protected static void newGame() {
		// STEP 6: Start game clock and timer
		// Called by displayGameGrid method
		gameStage = DURING_GAME;
		Timer timer = new Timer();
		GameClock clockTick = new GameClock(snake, snakePanel);
		timer.scheduleAtFixedRate(clockTick, 0, clockInterval);
		snakePanel.repaint();
	}

	public static void resumePausedGame() {
		// OPTIONAL: If game is ever paused,
		// Create new game clock and timer to restart game
		Timer timer = new Timer();
		GameClock clockTick = new GameClock(snake, snakePanel);
		timer.scheduleAtFixedRate(clockTick, 0, clockInterval);
	}

	protected static void displayGameOverGUI() {
		// STEP 7: Display score & high scores in GameOver GUI
		System.out.println("SnakeGame displaygameovergui method called");
		snakeFrame.remove(snakePanel);

		gameOverPanel = gameOverGUI.rootPanel;
		gameOverGUI.displayScoresInGUI();
		snakeFrame.add(gameOverPanel);
		ScoreManager.checkIfNewHighScore(currentScore);
		snakeFrame.validate();
		snakeFrame.repaint();
	}

	public static void reset() {
		// STEP 8: Reset and repeat, if desired
		// Called by GameOverGUI when Play Again button pressed
		GridSquares.reset();
		snake.createStartSnake();
		currentScore = new Score();
		foodManager.reset();
	}

	// Game stage getter and setter
	public static int getGameStage() {
		return gameStage;
	}

	public static void setGameStage(int gameStage) {
		SnakeGame.gameStage = gameStage;
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
