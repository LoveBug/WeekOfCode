package sprites;
import java.awt.Graphics;

import swarm.Hitbox;
import map.Map;


public class Enemy extends Sprite{
	
	private boolean lastDirection;
	
	public Enemy(int x, int y, int width, int height, String runCycle,
			int frames, int health) {
		super(x, y, width, height, runCycle, frames, health);
		getImage().reverse();
		setDirection(false);
	}
	
	public void move(Map m){
		int x = getX()-1;
		if(getDirection())
			x+=getWidth()+2;
		Hitbox temp = new Hitbox(x, getMovementBox().getY()-5,
				x+1, getMovementBox().getHeight()+5);
		
		for(int y=getY()+1; y<getY()+getHeight()-1; y++){
			if(temp.checkCollision(m.getMap()[x/32][y/32].getHitbox())){
				
				setDirection(!getDirection());
				break;
			}
		}
		
		super.walk(getDirection());
		super.move(m);
	}
	
}
