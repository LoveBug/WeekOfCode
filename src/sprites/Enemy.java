package sprites;
import java.awt.Graphics;

import swarm.Hitbox;
import map.Map;


public class Enemy extends Sprite{
	
	public Enemy(int x, int y, int width, int height, String runCycle,int frames, int health) {
		super(x, y, width, height, runCycle, frames, health);
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
			System.out.println("You got owned bitch");
			m.getCharacter().damage(5);
		}
		
		super.walk(getDirection());
		super.move(m);
	}
	
}
