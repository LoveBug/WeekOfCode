import java.awt.Graphics;

public class Tile implements Drawable, Collidable  {
	private int height;
	private int width;
	private int x;
	private int y;
	private int depth;
	private ImageWrapper image;
	protected Hitbox hitbox;

	public Tile(int height, int width, int x, int y, int depth,
			ImageWrapper image) {
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
		this.depth = depth;
		this.image = image;
		this.hitbox = new Hitbox(x,y,width,height);
	}

	public ImageWrapper getImageWrapper(){
		return image;
	}
	
	/**
	 * Returns This tiles hitbox
	 */
	public Hitbox getHitbox() {
		return hitbox;
	}

	/**
	 * Returns this tiles height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 
	 * @return This tiles width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 
	 * @return This tiles X position
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return This tiles Y position
	 */
	public int getY() {
		return y;
	}
	
	
	/**
	 * 
	 * @return Depth
	 */
	
	public int getDepth(){
		return depth;
	}

	/**
	 * Draws this tile to the canvas
	 */
	public void draw(Graphics g) {
		image.draw(g, x, y, width, height);
	}

}
