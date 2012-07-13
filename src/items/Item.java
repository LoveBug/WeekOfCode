package items;

import sprites.Sprite;
import main.Main;
import map.Map;

public class Item extends Sprite{
	private final int ANIMATION_RATIO = Main.FPS/5;
	private int count = 0;
	
	public Item(int x,int y, int width, int height,String Spritesheet, int spritenumber){
		super(x, y, width, height, Spritesheet, spritenumber);
}

	public Item(int x, int y, int width, int height, String runCycle, int frames,int health) {
			super(x, y, width, height, runCycle, frames, null, 0, health);
		setMoveDistance(0);
	}
	
	public void move(Map m){
		if(count>=ANIMATION_RATIO){
			count = 0;
			walk(true);
		}else
			count++;
		super.move(m);
	}
	
	public void jumpMovement(Map m){}
	
	public void jump(){}
}
