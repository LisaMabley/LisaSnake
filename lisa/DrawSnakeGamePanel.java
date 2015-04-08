package lisa;

import java.awt.*;
import java.util.LinkedList;
import javax.swing.JPanel;

/** This class responsible for displaying the graphics, so the snake, grid, kibble, instruction text and high score
 * 
 * @author Clara
 *
 */

public class DrawSnakeGamePanel extends JPanel {
	
	private int gameStage;  //use this to figure out what to paint
	
	private Snake snake;
	private Score score;

	// Constructor
	DrawSnakeGamePanel(Snake snake, Score score) {
		this.snake = snake;
		this.score = score;
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        /* Where are we at in the game? 4 phases.. 
         * 1. Before game starts
         * 2. During game
         * 3. Game lost aka game over
         * 4. or, game won
         */

        gameStage = SnakeGame.getGameStage();
        
        switch (gameStage) {
			case SnakeGame.GAME_PAUSED: {
				displayUnpauseInstructions(g);
				break;
			}
            case SnakeGame.DURING_GAME: {
                displayGame(g);
                break;
            }
            case SnakeGame.GAME_OVER: {
                displayGameOver(g);
                break;
            }
            case SnakeGame.GAME_WON: {
                displayGameWon(g);
                break;
            }
        }
    }

	private void displayGameWon(Graphics g) {
		// TODO Replace this with something really special!
		g.clearRect(100, 100, 350, 350);
		g.drawString("YOU WON SNAKE!!!", 150, 150);
	}

	private void displayGameOver(Graphics g) {

		g.clearRect(100,100,350,350);
		g.drawString("GAME OVER", 150, 150);
		
		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();
		String newHighScore = score.newHighScore();
		
		g.drawString("SCORE = " + textScore, 150, 250);
		
		g.drawString("HIGH SCORE = " + textHighScore, 150, 300);
		g.drawString(newHighScore, 150, 400);
		
		g.drawString("press a key to play again", 150, 350);
		g.drawString("Press q to quit the game", 150, 400);
	}

	private void displayGame(Graphics g) {
		displayGameGrid(g);
		displaySnake(g);
		displayFood(g);
	}

	private void displayGameGrid(Graphics g) {

		int maxX = SnakeGame.xPixelMaxDimension;
		int maxY= SnakeGame.yPixelMaxDimension;
		int squareSize = SnakeGame.squareSize;
		
		g.clearRect(0, 0, maxX, maxY);

		g.setColor(Color.LIGHT_GRAY);

		//Draw grid - horizontal lines
		for (int y=0; y <= maxY ; y+= squareSize){			
			g.drawLine(0, y, maxX, y);
		}
		//Draw grid - vertical lines
		for (int x=0; x <= maxX ; x+= squareSize){			
			g.drawLine(x, 0, x, maxY);
		}
	}

	private void displayFood(Graphics graphics) {
		// Draw all food that exists

		// First, draw kibble
		graphics.setColor(FoodManager.kibble.displayColor);
		int kibbleX = FoodManager.kibble.getFoodX() * SnakeGame.squareSize;
		int kibbleY = FoodManager.kibble.getFoodY() * SnakeGame.squareSize;
		graphics.fillRect(kibbleX+1, kibbleY+1, SnakeGame.squareSize-1, SnakeGame.squareSize-1);

		// If prey option is on, draw prey
		if (SnakeGame.preyOn) {
			graphics.setColor(FoodManager.prey.displayColor);
			int preyX = FoodManager.prey.getFoodX() * SnakeGame.squareSize;
			int preyY = FoodManager.prey.getFoodY() * SnakeGame.squareSize;
			graphics.fillRect(preyX+1, preyY+1, SnakeGame.squareSize-1, SnakeGame.squareSize-1);
		}

		// If obstacle option is on, draw avocados
		if (SnakeGame.obstaclesOn) {
			for (Avocado avocado : FoodManager.avocados) {
				int avocadoX = avocado.getFoodX() * SnakeGame.squareSize;
				int avocadoY = avocado.getFoodY() * SnakeGame.squareSize;
				graphics.setColor(avocado.displayColor);
				graphics.fillRect(avocadoX+1, avocadoY+1, SnakeGame.squareSize-1, SnakeGame.squareSize-1);
			}
		}
	}

	private void displaySnake(Graphics g) {

		LinkedList<Point> coordinates = snake.segmentsToDraw();
		
		//Draw head in grey
		g.setColor(Color.DARK_GRAY);
		Point head = coordinates.pop();
		g.fillRect((int)head.getX(), (int)head.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		
		//Draw rest of snake in black
		g.setColor(Color.GREEN);
		for (Point p : coordinates) {
			g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		}
	}

	private void displayUnpauseInstructions(Graphics g) {
		// Display instructions for restarting paused game

		g.drawString("GAME PAUSED", GridSquares.screenXCenter, GridSquares.screenYCenter);
		g.drawString("Press R to resume", 300, 400);
	}
}

