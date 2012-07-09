import java.awt.Graphics;


public class Cursor {
	private int x;
	private int y;
	private int width;
	private int height;
	
	private ImageWrapper cursor;
	
	public Cursor(int x, int y, int width, int height)
	{
		this.width = width;
		this.height = height;
		this.setX(x);
		this.setY(y);
		cursor = new ImageWrapper("images/reticlePrelim.gif");  //needs to be changed when initial cursor is made
	}
	
	public void draw(Graphics g)
	{
		cursor.draw(g, x - width/2, y - height/2, width, height);
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
}
