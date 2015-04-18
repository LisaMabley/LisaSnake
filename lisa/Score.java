package lisa;
import java.util.Date;

public class Score implements Comparable<Score> {
    // Creates a new points object for each game

    protected int points;
	protected Date date;
	protected String name;

	// Constructor
	public Score(){
		this.points = 0;
		this.date = new Date();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void increaseScore(int byHowMuch) {
		points += byHowMuch;
	}

	@Override
	public int compareTo(Score score) {
		// Scores are sorted by point value
		double comparedValue = score.points;
		if (this.points > comparedValue) {
			return 1;

		} else {
			return -1;
		}
	}

	public String toString() {
		// Creates top score record to be written to file
		String dateString = ScoreManager.dateFormat.format(this.date);
		return Integer.toString(points) + ":" + this.name + ":" + dateString;
	}
}

