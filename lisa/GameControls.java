package lisa;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameControls implements KeyListener{
	
	Snake snake;
	
	public void keyPressed(KeyEvent ev) {

		// keyPressed events are for catching events like function keys, enter, arrow keys
		// We want to listen for arrow keys to move snake
		// Has to id if user pressed arrow key, and if so, send info to lisa.Snake object

		snake = SnakeGame.snake;

		// Is game running? No? tell the game to draw grid and restart
		if (SnakeGame.getGameStage() != SnakeGame.DURING_GAME){

			// Need to start the timer and start the game again
			SnakeGame.reset();
			SnakeGame.displayOptionsGUI();
			return;
		}

		if (ev.getKeyCode() == KeyEvent.VK_DOWN) {
			snake.snakeDown();
		}
		if (ev.getKeyCode() == KeyEvent.VK_UP) {
			snake.snakeUp();
		}
		if (ev.getKeyCode() == KeyEvent.VK_LEFT) {
			snake.snakeLeft();
		}
		if (ev.getKeyCode() == KeyEvent.VK_RIGHT) {
			snake.snakeRight();
		}
	}

	@Override
	public void keyReleased(KeyEvent ev) {
		//We don't care about keyReleased events, but are required to implement this method anyway.		
	}

	@Override
	public void keyTyped(KeyEvent ev) {
		//keyTyped events are for user typing letters on the keyboard, anything that makes a character display on the screen
		char keyPressed = ev.getKeyChar();
		char q = 'q';
		char p = 'p';
		char r = 'r';
		if (keyPressed == q) {
			System.exit(0);    //quit if user presses the q key.

		} else if (keyPressed == p) {
			// Pause if user presses the P key
			SnakeGame.setGameStage(SnakeGame.GAME_PAUSED);

		} else if (keyPressed == r) {
			// Restart if user presses the R key
			SnakeGame.setGameStage(SnakeGame.DURING_GAME);
			SnakeGame.resumePausedGame();
		}
	}
}
