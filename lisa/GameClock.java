package lisa;

import java.util.TimerTask;

public class GameClock extends TimerTask {

	Snake snake;
	Score score;
	DrawSnakeGamePanel gamePanel;

	public GameClock(Snake snake, Score score, DrawSnakeGamePanel gamePanel){
		this.snake = snake;
		this.score = score;
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
