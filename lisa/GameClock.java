package lisa;

import java.util.TimerTask;

public class GameClock extends TimerTask {

	Snake snake;
	DrawSnakeGamePanel gamePanel;
	// FINDBUGS: Deleted unused field score here.

	public GameClock(Snake snake, DrawSnakeGamePanel gamePanel){
		this.snake = snake;
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void run() {
		// This method will be called every clock tick
		int stage = SnakeGame.getGameStage();

		switch (stage) {
			case SnakeGame.BEFORE_GAME: {
				// Don't do anything
				break;
			}

			case SnakeGame.DURING_GAME: {
				snake.moveSnake();
				// Don't update food if game has been won
				if (SnakeGame.getGameStage() != SnakeGame.GAME_WON) {
					FoodManager.updateFood();
				}

				break;
			}

			case SnakeGame.GAME_PAUSED: {
				// Stop the Timer
				this.cancel();
				break;
			}

			case SnakeGame.GAME_OVER: {
				// Stop the Timer and play lose sound
                this.cancel();
				SoundPlayer.playLoseGameSound();
				break;
			}

			case SnakeGame.GAME_WON: {
				// Stop timer and play win sound
				this.cancel();
				SoundPlayer.playWonGameSound();
				break;
			}
		}

		// In every circumstance, must update screen
		gamePanel.repaint();
	}
}
