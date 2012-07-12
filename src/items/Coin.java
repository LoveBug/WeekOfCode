package items;

import swarm.Hitbox;

public class Coin extends Item {

	public Coin(int x, int y, int width, int height, String runCycle, int frames) {
		super(x, y, width, height, runCycle, frames, 10);
		setMovementBox(new Hitbox(getX()+8, getMovementBox().getY()+8, 16, 16));
	}
	
	public void setX(int x) {
		super.setX(x);
		getMovementBox().setX(x+8);
	}
	
}
