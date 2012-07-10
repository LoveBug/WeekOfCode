import java.awt.Graphics;

public class Character implements Drawable{
	private final int FRAMES = 8;
	private final int JUMP_FRAMES = 5;
	private final int IMAGE_WIDTH = 2;
	private final int IMAGE_HEIGHT = 3;
	
	private final int JUMP_HEIGHT = 4;
	private final int TURN_COUNT = 120;
	
	private int turnCounter = 0;
	
	private ImageWrapper image;
	private int height;
	private int width;
	private int x;
	private int y;
	
	private int moveDistance = 8;
	private int currentImage = 0;
	
	private boolean isJumping = false;
	private boolean isWalking = false;
	private boolean isFalling = false;
	private boolean direction = true;
	private int maxJump;
	
	private Hitbox movementBox;
	private Hitbox shootBox;
	
	private SpriteSheet sprite, jumpSprite, stationarySprite;
	
	public Character(int x, int y)
	{
		this.setX(x);
		this.setY(y);
		
		this.sprite = new SpriteSheet("images/runCyclePrelimSheetAlpha.png");
		this.jumpSprite = new SpriteSheet("images/jumpCyclePrelimSheetAlpha.png");
		this.stationarySprite = new SpriteSheet("images/playerSpriteFinalDesign.png");
		
		this.height = 96;
		this.width = 64;
		image = new ImageWrapper(currentImage, width, height, sprite);
		this.movementBox = new Hitbox(x, y, width, height);
	}
	
	public void draw(Graphics g)
	{
		image.draw(g, x, y, width, height);
	}
	
	public void move(Tile[][] map)
	{
		if(isJumping){
			turnCounter = TURN_COUNT;
			if(this.currentImage<(JUMP_FRAMES-2)*IMAGE_WIDTH){
				this.currentImage+=IMAGE_WIDTH;
				updateImage(new ImageWrapper(currentImage, width, height, jumpSprite));
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
			int x=this.x+20;
			int end = this.x+this.width-10;
			if(!direction){
				x=this.x+10;
				end=this.x+width-20;
			}
			for(; x<end; x++){
				if(this.movementBox.checkCollision(map[x/32][(this.y+this.height)/32].getHitbox())){
					falling = false;
					if(map[x/32][(this.y+this.height)/32] instanceof DestTile)
						((DestTile)map[x/32][(this.y+this.height)/32]).destroy();
				}
			}
			if(falling){
				if(!isFalling)
					this.currentImage = 0;
				isFalling = true;
				this.y+=moveDistance;
				this.movementBox.setY(y);
				if(this.currentImage<(JUMP_FRAMES-2)*IMAGE_WIDTH){
					this.currentImage+=IMAGE_WIDTH;
					updateImage(new ImageWrapper(currentImage, width, height, jumpSprite));
				}
			}else{
				if(isFalling){
					this.currentImage = 0;
					updateImage(new ImageWrapper(this.currentImage, width, height, sprite));
				}
				this.y = this.y/32*32;
				isFalling = false;
			}
		}
		
		
		if(isWalking){
			turnCounter = TURN_COUNT;
			int movement = moveDistance;
			if(!direction){
				movement = -moveDistance;
			}
			
			this.x+=movement;
			this.movementBox.setX(x);
			int x = this.x;
			if(direction)
				x+=this.width;
			for(int y=this.y+5; y<this.y+this.height-10; y++)
				if(this.movementBox.checkCollision(map[x/32][y/32].getHitbox())){
					if(direction)
						this.x = this.x/32*32;
					else
						this.x = this.x/32*32+32;
					break;
				}
			isWalking=false;
		}
		
		if(!isWalking && !isJumping){
			if(turnCounter<0){
				this.currentImage=0;
				updateImage(new ImageWrapper(currentImage, width, height, stationarySprite));
			}else
				turnCounter--;
		}
	}
	
	public void jump()
	{
		if(isJumping || isFalling){return;}
		this.currentImage = 0;
		updateImage(new ImageWrapper(currentImage, width, height, jumpSprite));
		isJumping = true;
		maxJump = y - 32*JUMP_HEIGHT;  //4 blocks of 32
	}
	
	public void updateImage(ImageWrapper i){
		if(!direction)
			i.reverse();
		this.image = i;
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
				this.currentImage=0;
			updateImage(new ImageWrapper(currentImage, width, height, sprite));
		}
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
