package sprites;

import map.Map;

public class FlyingEnemy extends Enemy{
	public FlyingEnemy(int x, int y, int width, int height, String runCycle,
			int frames, String deathCycle, int dframes, int health) {
		super(x, y, width, height, runCycle, frames, deathCycle, frames, health);
	}

	public void jumpMovement(Map m){}
}
