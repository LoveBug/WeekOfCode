
public class Camera {
	private Character c;
	private Cursor cur;
	
	private int centerX;
	private int centerY;
	
	public static final int SCREEN_WIDTH  = 1024;
	public static final int SCREEN_HEIGHT = 768;
	
	public Camera(Character ch, Cursor cursor)
	{
		c = ch;
		cur = cursor;
		
		centerCamera();
	}
	
	public void centerCamera()
	{
		setX(c.getX() + Math.abs(cur.getX() - c.getX()) / 2);
		setY(c.getY() + Math.abs(cur.getY() - c.getY()) / 2);
	}

	public int getX() {
		return (centerX - SCREEN_WIDTH/2);
	}

	public void setX(int centerX) {
		this.centerX = centerX;
	}

	public int getY() {
		return (centerY - SCREEN_HEIGHT/2);
	}

	public void setY(int centerY) {
		this.centerY = centerY;
	}
}
