import java.awt.Graphics;


public class Cursor {
	private int x;
	private int y;
	private int width;
	private int height;
	
	private final int maxDistance = 300; 
	
	private Character c;
	
	private ImageWrapper cursor;
	
	public Cursor(int x, int y, int width, int height, Character c)
	{
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		
		this.c = c;
		
		cursor = new ImageWrapper("images/targetReticleAlpha.png");  //needs to be changed when initial cursor is made
	}
	
	public void draw(Graphics g)
	{
		cursor.draw(g, x - width/2, y - height/2, width, height);
	}

	public int getX() {
		return x;
	}

	
	public void moveCursor(int nX, int nY)
	{
		double dist = (nX - x)*(nX - x) + (nY - y)*(nY - y);
		
		if(dist < maxDistance*maxDistance)
		{
			this.x = nX;
			this.y = nY;
		}
	}

	public int getY() {
		return y;
	}


}
