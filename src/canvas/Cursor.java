package canvas;

import java.awt.Graphics;

import swarm.ImageWrapper;


public class Cursor {
	private int absoluteX;
	private int absoluteY;
	private int width;
	private int height;
	private int gameworldX;
	private int gameworldY;
	
	private Camera c;
	
	private ImageWrapper cursor;
	
	public Cursor(int x, int y, int width, int height, Camera c)
	{
		this.width = width;
		this.height = height;
		this.absoluteX = x;
		this.absoluteY = y;
		
		this.c = c;
		
		cursor = new ImageWrapper("images/cursorReticle.png");  //needs to be changed when initial cursor is made
		gameworldY = c.getY()+absoluteY;
		gameworldX = c.getX()+absoluteX;
	}
	
	public void draw(Graphics g)
	{
		gameworldY = c.getY() +absoluteY;
		gameworldX = c.getX()+absoluteX;
		cursor.draw(g, gameworldX , gameworldY, width, height);
	}

	public int getX() {
		//System.out.println("Returned cursor X as: "+absoluteX);
		return absoluteX;
	}

	
	public void moveCursor(int mouseX, int mouseY)
	{
		this.absoluteX = mouseX;
		this.absoluteY = mouseY;
	}

	public int getY() {
	//	System.out.println("Returned cursor Y as: "+absoluteY);
		return absoluteY;
	}
	
	
	public int getGameworldY(){
		 
		//c.cameraY()-c.getY();
		//c.getX();
	//	System.out.println("Returned gameworld cursor Y as: "+gameworldY);
		 
	return	gameworldY;  
		
	}
	public int getGameworldX(){
		//System.out.println("Returned gameworld cursor X as: "+gameworldX);
		return gameworldX;
	}


}
