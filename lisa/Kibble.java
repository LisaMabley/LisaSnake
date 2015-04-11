package lisa;

import java.awt.*;

public class Kibble extends Food {

	// Constructor
	public Kibble() {
		this.growthIncrement = 2;
		this.displayColor = Color.ORANGE;
		this.pointsForEating = 1;
		
		this.placeFood();
	}
}
