package items;

import sprites.Sprite;
import map.Map;

public class Item extends Sprite{

	public Item(int x, int y, int width, int height, String runCycle, int frames) {
		super(x, y, width, height, runCycle, frames);
		this.setMoveDistance(0);
			}
	
	public void move(Map m){
		walk(true);
		super.move(m);
	}
	
}
