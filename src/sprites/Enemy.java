package sprites;
import swarm.Hitbox;
import map.Map;


public class Enemy extends Sprite{
	
	public Enemy(int x, int y, int width, int height, String runCycle,
			int frames) {
		super(x, y, width, height, runCycle, frames);
		setDirection(false);
	}
	
	public void move(Map m){
		int x = getX()-1;
		if(getDirection())
			x+=getWidth()-1;
		Hitbox temp = new Hitbox(getMovementBox().getX()-5, getMovementBox().getY()-5,
				getMovementBox().getWidth()+5, getMovementBox().getHeight()+5);
		for(int y=getY()-1; y<getY()+getHeight()+1; y++){
			if(temp.checkCollision(m.getMap()[x/32][y/32].getHitbox()))
				setDirection(!getDirection());
		}
		super.walk(getDirection());
		super.move(m);
	}
}
