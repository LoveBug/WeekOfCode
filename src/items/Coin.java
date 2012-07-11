package items;

import swarm.Hitbox;

public class Coin extends Item {

	public Coin(int x, int y, int width, int height, String runCycle, int frames) {
		super(x, y, width, height, runCycle, frames);
		setMovementBox(new Hitbox(getMovementBox().getX()-8, getMovementBox().getY()-8, 16, 16));
	}
	
	
	
}
