import java.awt.Graphics;

public class Tile implements Drawable, Collidable  {
	private int height;
	private int width;
	private int x;
	private int y;
	private int depth;
	private ImageWrapper image;
	private Hitbox hitbox;

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
	
	public void setHitbox(Hitbox h){
		this.hitbox = h;
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

	
	public void setX(int x){
		this.x = x;
		this.hitbox.setX(x);
	}
	
	public void setY(int y){
		this.y = y;
		this.hitbox.setY(y);
	}
	/**
	 * Draws this tile to the canvas
	 */
	public void draw(Graphics g) {
		image.draw(g, x, y, width, height);
	}

}
