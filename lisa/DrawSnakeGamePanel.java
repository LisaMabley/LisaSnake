package lisa;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.LinkedList;
import javax.swing.JPanel;

/** This class responsible for displaying the graphics in the DURING_GAME state: the snake, grid, kibble and avocados
 * @author Clara
 */

public class DrawSnakeGamePanel extends JPanel {
	
	private int gameStage;  //use this to figure out what to paint
	
	private Snake snake;
//	private boolean imagesOn = true;
//	private Image image;
//	private ImageObserver imageObserver;

	// Constructor
	DrawSnakeGamePanel(Snake snake) {
		this.snake = snake;

//		try {
//			image = ImageIO.read(new File("Snake.gif"));
//
//		} catch (IOException e) {
//			System.out.println("Cannot find image files");
//			imagesOn = false;
//		}
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

        // FINDBUGS recommends I change switch statement without
		// default case
        switch (gameStage) {
			case SnakeGame.GAME_PAUSED: {
				displayUnpauseInstructions(g);
				break;
			}
            case SnakeGame.DURING_GAME: {
//				g.drawImage(image, 100, 100, imageObserver);
				displayGame(g);
                break;
            }
            case SnakeGame.GAME_OVER: {
				SnakeGame.displayGameOverGUI();
				break;
            }
            case SnakeGame.GAME_WON: {
				SnakeGame.gameOverGUI.winDisplay();
				SnakeGame.displayGameOverGUI();
                break;
            }
        }
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
		graphics.fillRect(kibbleX + 1, kibbleY + 1, SnakeGame.squareSize - 1, SnakeGame.squareSize - 1);

		// If avocado option is on, draw avocados
		if (SnakeGame.avocadosOn) {
			for (Avocado avocado : FoodManager.avocados) {
				int avocadoX = avocado.getFoodX() * SnakeGame.squareSize;
				int avocadoY = avocado.getFoodY() * SnakeGame.squareSize;
				graphics.setColor(avocado.displayColor);
				graphics.fillRect(avocadoX + 1, avocadoY + 1, SnakeGame.squareSize - 1, SnakeGame.squareSize - 1);
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
		g.setColor(snake.snakeColor);
		for (Point p : coordinates) {
			g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		}
	}

	private void displayUnpauseInstructions(Graphics g) {
		// Display instructions for restarting paused game
		int textX = (SnakeGame.xPixelMaxDimension / 2) - 40;
		int textY = (SnakeGame.yPixelMaxDimension / 2) - 15;

		g.drawString("GAME PAUSED", textX, textY);
		g.drawString("Press R to resume", textX - 10, textY + 25);
	}
}

