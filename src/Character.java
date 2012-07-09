import java.awt.Graphics;

public class Character implements Drawable{
	private ImageWrapper image;
	private int height;
	private int width;
	private int x;
	private int y;
	
	private Hitbox movementBox;
	private Hitbox shootBox;
	
	public Character(int x, int y, String fileLocation)
	{
		this.setX(x);
		this.setY(y);
		
		setImage(new ImageWrapper(fileLocation));
	}
	
	public void draw(Graphics g)
	{
		image.draw(g, x, y, width, height);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public ImageWrapper getImage() {
		return image;
	}

	public void setImage(ImageWrapper image) {
		this.image = image;
	}

	public Hitbox getMovementBox() {
		return movementBox;
	}

	public void setMovementBox(Hitbox movementBox) {
		this.movementBox = movementBox;
	}

	public Hitbox getShootBox() {
		return shootBox;
	}

	public void setShootBox(Hitbox shootBox) {
		this.shootBox = shootBox;
	}
}
