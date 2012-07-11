package items;

import sprites.Sprite;

public class Item extends Sprite{

	public Item(int x, int y, int width, int height, String runCycle, int frames) {
		super(x, y, width, height, runCycle, frames);
		this.setMoveDistance(0);
			}
	
	
	
}
