package sprites;

import swarm.Hitbox;
import main.Main;
import map.Map;


public class Enemy extends Sprite{
	private final int MOVE_RATIO = Main.FPS/20;
	private int count;
	
	public enum Type {
		WALKER(0), FLYER(1);
		public final int index;

		private Type(int i) {
			this.index = i;
		}

	}
	
	public Enemy(int x, int y, int width, int height, String runCycle,int frames, int health) {
		super(x, y, width, height, runCycle, frames, null, 0, health);
		getImage().reverse();

		setShootBox(new Hitbox(x, y, width, height/2));
		
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
		
		temp = new Hitbox(m.getCharacter().getX() + 10, m.getCharacter().getY() + 5, 
						  m.getCharacter().getWidth() - 10, m.getCharacter().getHeight() - 15);
		if(this.getShootBox().checkCollision(temp)){
			m.getCharacter().setHealth(m.getCharacter().getHealth()-3);
			m.getCharacter().damage(1);
		}
		if(count>=MOVE_RATIO){
			count = 0;
			super.walk(getDirection());
		}else
			count++;
		super.move(m);
	}
	
	public void jump(){}
}
