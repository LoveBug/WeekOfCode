package swarm;

import java.awt.Color;
import java.awt.Graphics;

public class Hitbox{
	private int width;
	private int height;
	private int x;
	private int y;
	
	public Hitbox(int x, int y, int width, int height)
	{
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);
	}
	
	public boolean checkCollision(Hitbox box)
	{
		if(box == null){return false;}
		
		return checkCollision(box.x, box.y) || box.checkCollision(this.x, this.y)
		    || checkCollision(box.x + box.width, box.y) || box.checkCollision(this.x + this.width, this.y)
		    || checkCollision(box.x, box.y + box.height) || box.checkCollision(this.x, this.y + this.height)
		    || checkCollision(box.x + box.width, box.y + box.height) || box.checkCollision(this.x + this.width, this.y + this.height);
	}
	
	public boolean checkCollision(int x, int y){
		if(x < this.x){return false;}
		if(y < this.y){return false;}
		if(x > this.x + this.width){return false;}
		if(y > this.y + this.height){return false;}
		
		return true;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.drawRect(this.x, this.y, this.width, this.height);
	}
}
