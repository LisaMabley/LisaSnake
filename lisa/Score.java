package lisa;

public class Score {
    // New points object for each game

    protected int points;

	public Score(){
		points = 0;
	}
	
	public void increaseScore(int byHowMuch) {
		points += byHowMuch;
	}
}

