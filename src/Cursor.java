import java.awt.Graphics;


public class Cursor {
	private int x;
	private int y;
	private int width;
	private int height;
	
	private final int maxDistance = 750; 
	
	private Character c;
	
	private ImageWrapper cursor;
	
	public Cursor(int x, int y, int width, int height, Character c)
	{
		this.width = width;
		this.height = height;
		this.setX(x);
		this.setY(y);
		
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

	public void setX(int x) {
		
		if(x > -12){}  //fix please
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
