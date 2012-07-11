import java.awt.Graphics;


public class Sprite {
	private ImageWrapper image;
	private int height;
	private int width;
	private int x;
	private int y;
	
	private Hitbox movementBox, shootBox;
	
	private boolean direction = true;
	private SpriteSheet sprites;
	
	public Sprite(int x, int y, int width, int height, String runCycle){
		setX(x);
		setY(y);
		setHeight(96);
		setWidth(64);
		setSpriteSheet(runCycle);
	}
	
	public void draw(Graphics g){
		image.draw(g, x, y, width, height);
	}
	
	public void updateImage(ImageWrapper i){
		if(!this.direction)
			i.reverse();
		this.image = i;
	}
	
	//-------------------SETTERS---------------------------
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public void setHeight(int h) {this.height = h;}
	public void setWidth(int w) {this.width = w;}
	public void setImage(ImageWrapper image) {this.image = image;}
	public void setMovementBox(Hitbox movementBox) {this.movementBox = movementBox;}
	public void setShootBox(Hitbox shootBox) {this.shootBox = shootBox;}
	public void setDirection(boolean d) {this.direction = d;}
	public void setSpriteSheet(String s) {this.sprites = new SpriteSheet(s);}
	public void setSpriteSheet(SpriteSheet s) {this.sprites = s;}
	
	//------------------GETTERS----------------------------
	public int getY() {return this.y;}
	public int getX() {return this.x;}
	public int getWidth(){return this.width;}
	public int getHeight(){return this.height;}
	public ImageWrapper getImage() {return this.image;}
	public Hitbox getMovementBox() {return this.movementBox;}
	public Hitbox getShootBox() {return this.shootBox;}
	public boolean getDirection() {return this.direction;}	
	public SpriteSheet getSpriteSheet() {return this.sprites;}
}
