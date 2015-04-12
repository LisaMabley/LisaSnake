package lisa;

// Created by lisa on 4/7/15.

public class GridSquares {

    private static int squareSize;
    public static int maxX, maxY;

    // Represent square content
    static final int CONTAINS_FOOD = -1;
    static final int EMPTY = 0;
//    static final int SNAKE_HEAD = 1;
//    static final int SNAKESEGMENT_HORIZ = 2;
//    static final int SNAKESEGMENT_VERT = 3;
//    static final int SNAKECORNER_1 = 4;
//    static final int SNAKECORNER_2 = 5;
//    static final int SNAKECORNER_3 = 6;
//    static final int SNAKECORNER_4 = 7;
//    static final int SNAKETAIL_UP = 8;
//    static final int SNAKETAIL_DWN = 9;
//    static final int SNAKETAIL_LFT = 10;
//    static final int SNAKETAIL_RT = 11;


    // A 0 means there is nothing in this square
    // A positive number means part of the snake is in the square
    // The head of the snake is 1, rest of segments are numbered in order

    // Set center of screen
    // Cast just in case we have an odd number
    public static int screenXCenter;
    public static int screenYCenter;

    // Initialize multidimensional array
    // Representing all squares on the screen
    public static int grid[][];

    public GridSquares(int X, int Y, int size) {
        maxX = X;
        maxY = Y;
        squareSize = size;

        screenXCenter = (int) maxX/2;
        screenYCenter = (int) maxY/2;

        //Create grid squares
        grid = new int[maxX][maxY];
        // And fill each with 0
        fillSnakeSquaresWithZeros();
    }

    private static void fillSnakeSquaresWithZeros() {
        for (int x = 0; x < maxX; x++) {
            for (int y = 0 ; y < maxY ; y++) {
                grid[x][y] = 0;
            }
        }
    }

    public static int getSquareSize() {
        return squareSize;
    }

    public static void setSquare(int X, int Y, int value) {
        grid[X][Y] = value;
    }

    public static int getSquareValue(int X, int Y) {
        return grid[X][Y];
    }

    public static void incrementSquareValue(int X, int Y) {
        grid[X][Y] ++;
    }

    public static void reset() {
        fillSnakeSquaresWithZeros();
    }

    public static boolean wonGame() {
        //If all of the squares have snake segments in, the snake has eaten so much kibble
        //that it has filled the screen. Win!

        for (int x = 0 ; x < maxX ; x++) {
            for (int y = 0 ; y < maxY ; y++){
                if (grid[x][y] == 0) {
                    // There is still empty space on the screen, so haven't won
                    return false;
                }
            }
        }

        // But if we get here, the snake has filled the screen. win!
        SnakeGame.setGameStage(SnakeGame.GAME_WON);
        return true;
    }

    protected static void startSnake() {
        // Set 3 horizontal squares in the center of the screen
        // As start snake
        grid[screenXCenter][screenYCenter] = 1;
        grid[screenXCenter+1][screenYCenter] = 2;
        grid[screenXCenter+2][screenYCenter] = 3;
    }
}
