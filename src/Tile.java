import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class Tile implements Drawable, Collidable  {
	private int height;
	private int width;
	private int x;
	private int y;
	private int depth;
	private ImageWrapper image;
	private Hitbox hitbox;

	public Tile(int height, int width, int x, int y, int depth,
			ImageWrapper image, Hitbox hitbox) {
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
		this.depth = depth;
		this.image = image;
		this.hitbox = hitbox;// Create hitbox here
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
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		image.draw(g, x, y, width, height);

	}

}
