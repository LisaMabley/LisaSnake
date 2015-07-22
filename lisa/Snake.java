package lisa;

import java.awt.*;
import java.util.LinkedList;

public class Snake {

	protected static Color snakeColor = new Color(51, 0, 102, 95);

	// Arbitrary int codes representing direction of movement
	final int DIRECTION_UP = 0;
	final int DIRECTION_DOWN = 1;
	final int DIRECTION_LEFT = 2;
	final int DIRECTION_RIGHT = 3;

	private boolean hitWall = false;
	private boolean ateTail = false;

	// Direction snake is going in, or direction user is telling snake to go
	private int currentHeading;
	// Last confirmed movement of snake. See moveSnake method
	private int lastHeading;
	// Size of snake - how many segments?
	protected int snakeSize;

	protected int justAteMustGrowThisMuch = 0;
	// Store coordinates of head - first segment
	private int snakeHeadX, snakeHeadY;

	// Constructor
	public Snake(){
		createStartSnake();
	}

	protected void createStartSnake(){
		// Snake starts as 3 horizontal squares in the center of the screen, moving left

		GridSquares.startSnake();

		snakeHeadX = GridSquares.screenXCenter;
		snakeHeadY = GridSquares.screenYCenter;
		snakeSize = 3;
		currentHeading = DIRECTION_LEFT;
		lastHeading = DIRECTION_LEFT;
		justAteMustGrowThisMuch = 0;
		hitWall = false;
		ateTail = false;
	}

	public LinkedList<Point> segmentsToDraw(){
		// Return a list of the actual x and y coordinates of the top left of each snake segment
		// Useful for the Panel class to draw the snake
		LinkedList<Point> segmentCoordinates = new LinkedList<Point>();
		for (int segment = 1 ; segment <= snakeSize ; segment++ ) {
			// Search array for each segment number
			for (int x = 0 ; x < GridSquares.maxX ; x++) {
				for (int y = 0 ; y < GridSquares.maxY ; y++) {
					if (GridSquares.grid[x][y] == segment){
						//make a Point for this segment's coordinates and add to list
						Point p = new Point(x * GridSquares.getSquareSize() , y * GridSquares.getSquareSize());
						segmentCoordinates.add(p);
					}
				}
			}
		}
		return segmentCoordinates;
	}

	public void snakeUp(){
		if (currentHeading == DIRECTION_UP || currentHeading == DIRECTION_DOWN) { return; }
		currentHeading = DIRECTION_UP;
	}
	public void snakeDown(){
		if (currentHeading == DIRECTION_DOWN || currentHeading == DIRECTION_UP) { return; }
		currentHeading = DIRECTION_DOWN;
	}
	public void snakeLeft(){
		if (currentHeading == DIRECTION_LEFT || currentHeading == DIRECTION_RIGHT) { return; }
		currentHeading = DIRECTION_LEFT;
	}
	public void snakeRight(){
		if (currentHeading == DIRECTION_RIGHT || currentHeading == DIRECTION_LEFT) { return; }
		currentHeading = DIRECTION_RIGHT;
	}

	protected void moveSnake(){
		//Called every clock tick
		
		// Must check that the direction snake is being sent in is not contrary to current heading
		// So if current heading is down, and snake is being sent up, then should ignore.
		// Without this code, if the snake is heading up, and the user presses left then down quickly,
		// the snake will back into itself.
		if (currentHeading == DIRECTION_DOWN && lastHeading == DIRECTION_UP) {
			currentHeading = DIRECTION_UP; //keep going the same way
		}
		if (currentHeading == DIRECTION_UP && lastHeading == DIRECTION_DOWN) {
			currentHeading = DIRECTION_DOWN; //keep going the same way
		}
		if (currentHeading == DIRECTION_LEFT && lastHeading == DIRECTION_RIGHT) {
			currentHeading = DIRECTION_RIGHT; //keep going the same way
		}
		if (currentHeading == DIRECTION_RIGHT && lastHeading == DIRECTION_LEFT) {
			currentHeading = DIRECTION_LEFT; //keep going the same way
		}

		// Did you eat your tail? Don't move.
		if (ateTail) {
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return;
		}

		// Did you hit a wall?
		// If warp walls are off, game over.
		if (hitWall && !SnakeGame.warpWallsOn) {
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return;
		}

		// Use snakeSquares array, and current heading, to move snake

		// Put a 1 in new snake head square, increase all other snake segments by 1
		// set tail to 0 if snake did not just eat
		// Otherwise leave tail as is until snake has grown the correct amount

		// Find the head of the snake - snakeHeadX and snakeHeadY

		// Increase all snake segments by 1
		// All non-zero elements of array represent a snake segment

		for (int x = 0 ; x < GridSquares.maxX ; x++) {
			for (int y = 0 ; y < GridSquares.maxY ; y++){
				if (GridSquares.getSquareValue(x, y) > 0) {
					GridSquares.incrementSquareValue(x, y);
				}
			}
		}

		// Now identify where to add new snake head
		if (currentHeading == DIRECTION_UP) {		
			// Subtract 1 from Y coordinate so head is one square up
			snakeHeadY-- ;
		}
		if (currentHeading == DIRECTION_DOWN) {		
			// Add 1 to Y coordinate so head is 1 square down
			snakeHeadY++ ;
		}
		if (currentHeading == DIRECTION_LEFT) {		
			// Subtract 1 from X coordinate so head is 1 square to the left
			snakeHeadX -- ;
		}
		if (currentHeading == DIRECTION_RIGHT) {		
			// Add 1 to X coordinate so head is 1 square to the right
			snakeHeadX ++ ;
		}

		// Does this make snake hit the wall?
		if (snakeHeadX >= GridSquares.maxX || snakeHeadX < 0 || snakeHeadY >= GridSquares.maxY || snakeHeadY < 0 ) {

			// No problem if warp walls are on
			if (SnakeGame.warpWallsOn) {
				SoundPlayer.playWarpWallSound();

				if (snakeHeadX >= GridSquares.maxX) {
					snakeHeadX = 0;

				} else if (snakeHeadX < 0) {
					snakeHeadX = GridSquares.maxX - 1;

				} else if (snakeHeadY >= GridSquares.maxY) {
					snakeHeadY = 0;

				} else if (snakeHeadY < 0) {
					snakeHeadY = GridSquares.maxY - 1;
				}

			} else {
				// If they aren't -- game over
				hitWall = true;
				SnakeGame.setGameStage(SnakeGame.GAME_OVER);
				return;
			}
		}

		// Does this make the snake eat its tail?
		if (GridSquares.getSquareValue(snakeHeadX, snakeHeadY) > 0) {

			ateTail = true;
			// Then the game is over
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return;
		}

		// Otherwise, game is still on. Add new head
		GridSquares.setSquare(snakeHeadX, snakeHeadY, 1);

		// If snake did not just eat, then remove tail segment to keep snake the same length.
		// Find highest number, which should now be the same as snakeSize+1, and set to 0
		
		if (justAteMustGrowThisMuch == 0) {
			for (int x = 0 ; x < GridSquares.maxX ; x++) {
				for (int y = 0 ; y < GridSquares.maxY ; y++){
					if (GridSquares.getSquareValue(x, y) == snakeSize + 1) {
						GridSquares.setSquare(x, y, 0);
					}
				}
			}
		}

		else {
			// Snake has just eaten. leave tail as is.  Decrease justAte variable by 1.
			justAteMustGrowThisMuch -- ;
			snakeSize ++;
		}

		// Update last confirmed heading
		lastHeading = currentHeading;
    }

	public String toString() {
		StringBuilder sb = new StringBuilder();
		String textSnake = "";

		// This looks the wrong way around. Actually need to do it this way or snake is drawn flipped 90 degrees.
		// FINDBUGS suggested using StringBuilder instead of concatenating string
		for (int y = 0 ; y < GridSquares.maxY ; y++) {
			for (int x = 0 ; x < GridSquares.maxX ; x++) {
				sb.append(GridSquares.grid[x][y]);
				sb.append("\n");
				}
				textSnake = sb.toString();
			}
		return textSnake;
	}
}


