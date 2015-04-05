package lisa;

import java.util.TimerTask;

public class GameClock extends TimerTask {

	Snake snake;
	Kibble kibble;
	Score score;
	DrawSnakeGamePanel gamePanel;
		
	public GameClock(Snake snake, Kibble kibble, Score score, DrawSnakeGamePanel gamePanel){
		this.snake = snake;
		this.kibble = kibble;
		this.score = score;
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void run() {
		// This method will be called every clock tick
						
		int stage = SnakeGame.getGameStage();

		switch (stage) {
			case SnakeGame.BEFORE_GAME: {
				// Don't do anything, waiting for user to press a key to start
				break;
			}

			case SnakeGame.DURING_GAME: {

				snake.moveSnake();
				if (snake.didEatKibble(kibble) == true) {
					if (SnakeGame.getGameStage() != SnakeGame.GAME_WON) { // LISA ADDED TO CORRECT GAME WON BUG
						// Tell kibble to update
						kibble.moveKibble(snake);
					}
					Score.increaseScore();
				}
				break;
			}

			case SnakeGame.GAME_PAUSED: {
				this.cancel();		// Stop the Timer
				break;
			}

			case SnakeGame.GAME_OVER: {
				this.cancel();		// Stop the Timer
				break;	
			}

			case SnakeGame.GAME_WON: {
				this.cancel();   // Stop timer
				break;
			}
		}
				
		gamePanel.repaint();		//In every circumstance, must update screen
	}
}
