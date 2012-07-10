import java.awt.Graphics;

public class Character implements Drawable{
	private final int FRAMES = 8;
	private final int JUMP_FRAMES = 5;
	private final int IMAGE_WIDTH = 2;
	private final int IMAGE_HEIGHT = 3;
	
	private final int JUMP_HEIGHT = 4;
	
	private ImageWrapper image;
	private int height;
	private int width;
	private int x;
	private int y;
	
	private int moveDistance = 8;
	private int currentImage = -1;
	
	private boolean isJumping = false;
	private boolean isWalking = false;
	private boolean isFalling = false;
	private boolean direction = true;
	private int maxJump;
	
	private Hitbox movementBox;
	private Hitbox shootBox;
	
	private SpriteSheet sprite, jumpSprite;
	
	public Character(int x, int y)
	{
		this.setX(x);
		this.setY(y);
		
		this.sprite = new SpriteSheet("images/runCyclePrelimSheet.gif");
		this.jumpSprite = new SpriteSheet("images/jumpCyclePrelimSheet.gif");
		this.height = 96;
		this.width = 64;
		image = new ImageWrapper(currentImage, width, height, sprite);	//-1 = 0 cause of offset initialization
		this.movementBox = new Hitbox(x, y, width, height);
	}
	
	public void draw(Graphics g)
	{
		image.draw(g, x, y, width, height);
	}
	
	public void move(Tile[][] map)
	{
		if(isJumping){
			if(this.currentImage<(JUMP_FRAMES-2)*IMAGE_WIDTH){
				this.currentImage+=IMAGE_WIDTH;
				this.image = new ImageWrapper(currentImage, width, height, jumpSprite);
			}
			y -= moveDistance;
			this.movementBox.setY(y);
			if(y<maxJump){
				isJumping = false;  
				isFalling = true;
			}else{
				for(int x=this.x; x<this.x+this.width; x++){
					if(this.movementBox.checkCollision(map[x/32][((this.y+10)/32)].getHitbox())){
						isJumping = false;
						isFalling = true;
						break;
					}
				}
			}
		}else{
			boolean falling = true;
			for(int x=this.x; x<this.x+this.width; x++){
				if(this.movementBox.checkCollision(map[x/32][(this.y+this.height)/32].getHitbox())){
					falling = false;
					break;
				}
			}
			if(falling){
				if(!isFalling)
					this.currentImage = -1;
				isFalling = true;
				this.y+=moveDistance;
				this.movementBox.setY(y);
				if(this.currentImage<(JUMP_FRAMES-2)*IMAGE_WIDTH){
					this.currentImage+=IMAGE_WIDTH;
					this.image = new ImageWrapper(currentImage, width, height, jumpSprite);
				}
			}else{
				if(isFalling){
					this.currentImage = -1;
					this.image = new ImageWrapper(this.currentImage, width, height, sprite);
				}
				this.y = this.y/32*32;
				isFalling = false;
			}
		}
		
		
		if(isWalking){
			int movement = moveDistance;
			if(!direction){
				movement = -moveDistance;
			}
			
			this.x+=movement;
			this.movementBox.setX(x);
			int x = this.x;
			if(direction)
				x+=this.width;
			for(int y=this.y; y<this.y+this.height; y++)
				if(this.movementBox.checkCollision(map[x/32][y/32].getHitbox())){
					if(direction)
						this.x = this.x/32*32;
					else
						this.x = this.x/32*32+32;
					break;
				}
			isWalking=false;
		}
	}
	
	public void jump()
	{
		if(isJumping || isFalling){return;}
		this.currentImage = -1;					//TODO: unfuck me
		this.image = new ImageWrapper(currentImage, width, height, jumpSprite);
		
		isJumping = true;
		maxJump = y - 32*JUMP_HEIGHT;  //4 blocks of 32
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
	
	public void walk(boolean direction){
		if(!(isJumping || isFalling)){
			this.currentImage += IMAGE_WIDTH;
			if(this.currentImage>=(FRAMES-1)*IMAGE_WIDTH)
				this.currentImage=-1;
			this.image = new ImageWrapper(currentImage, width, height, sprite);
		}
		if(!direction)
			this.image.reverse();
		this.direction = direction;
		this.isWalking = true;
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
