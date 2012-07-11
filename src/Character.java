import java.awt.Graphics;

public class Character implements Drawable{
	private final int FRAMES = 8;
	private final int JUMP_FRAMES = 5;
	private final int IMAGE_WIDTH = 2;
	private final int IMAGE_HEIGHT = 3;
	
	private final int JUMP_HEIGHT = 4;
	private final int TURN_COUNT = 120;
	private final int FALL_MAX = 16;
	
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
	private float ySpeed = moveDistance;
	private float xSpeed = 0;
	
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
	
	public void move(Map map)
	{
		jumpMovement(map);
		
		if(this.xSpeed!=0){
			this.x += xSpeed;
			this.movementBox.setX(this.x);
			this.xSpeed = 0;
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
				if(this.movementBox.checkCollision(map.getMap()[x/32][y/32].getHitbox())){
					if(direction)
						this.x = this.x/32*32;
					else
						this.x = this.x/32*32+32;
					break;
				}
			this.movementBox.setX(this.x);
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
	
	public void jumpMovement(Map map){
		if(isJumping){
			turnCounter = TURN_COUNT;
			if(this.currentImage<(JUMP_FRAMES-2)*IMAGE_WIDTH){
				this.currentImage+=IMAGE_WIDTH;
				updateImage(new ImageWrapper(currentImage, width, height, jumpSprite));
			}
			
			if(ySpeed>5)
				ySpeed -= 0.5;
			else if(ySpeed>1)
				ySpeed -= 0.2;
			
			y -= ySpeed;
			this.movementBox.setY(y);
			if(y<maxJump){
				isJumping = false;  
				isFalling = true;
				
			}else{
				for(int x=this.x; x<this.x+this.width; x++){
					if(this.movementBox.checkCollision(map.getMap()[x/32][((this.y+10)/32)].getHitbox())){
						isJumping = false;
						isFalling = true;
						ySpeed = moveDistance;
						break;
					}
				}
			}
		}else{
			boolean falling = true;
			MoveTile tile = null;
			int x=this.x+20;
			int end = this.x+this.width-10;
			if(!direction){
				x=this.x+10;
				end=this.x+width-20;
			}
			for(; x<end; x++){
				if(this.movementBox.checkCollision(map.getMap()[x/32][(this.y+this.height)/32].getHitbox())){
					falling = false;
					if(map.getMap()[x/32][(this.y+this.height)/32] instanceof DestTile)
						((DestTile)map.getMap()[x/32][(this.y+this.height)/32]).destroy();
				}
			}
			//check moving tile collision
			Hitbox temp = new Hitbox(this.movementBox.getX(), 
					this.movementBox.getY()+this.movementBox.getHeight()-5,
					this.movementBox.getWidth(), this.movementBox.getY()+this.movementBox.getHeight()+1);
			for(MoveTile t : map.movingTiles())
				if(temp.checkCollision(t.getHitbox()) && this.y+this.height+this.ySpeed>=t.getY()){
					falling = false;
					tile = t;
					break;
				}
			
			if(falling)
				fall();
			else
				land(tile);
		}
	}
	
	public void fall(){
		this.y+=ySpeed;
		if(ySpeed<0)
			ySpeed = 0;
		if(ySpeed<FALL_MAX)
			if(ySpeed<10)
				ySpeed+=0.75;
			else
				ySpeed+=0.25;
		if(!isFalling)
			this.currentImage = 0;
		isFalling = true;
		this.movementBox.setY(y);
		if(this.currentImage<(JUMP_FRAMES-2)*IMAGE_WIDTH){
			this.currentImage+=IMAGE_WIDTH;
			updateImage(new ImageWrapper(currentImage, width, height, jumpSprite));
		}
	}
	
	public void land(MoveTile tile){
		if(this.isFalling){
			this.currentImage = 0;
			updateImage(new ImageWrapper(this.currentImage, width, height, sprite));
		}
		if(tile==null){
			this.y = this.y/32*32;
			this.ySpeed = 4;
		}else{
			this.y = tile.getY()-this.height;
			this.movementBox.setY(this.y);
			this.ySpeed = tile.getYSpeed();
			this.xSpeed = tile.getXSpeed();
		}
		this.isFalling = false;
	}
	
	public void jump()
	{
		if(isJumping || isFalling){return;}
		this.currentImage = 0;
		updateImage(new ImageWrapper(currentImage, width, height, jumpSprite));
		isJumping = true;
		maxJump = y - 32*JUMP_HEIGHT;  //4 blocks of 32
		ySpeed = FALL_MAX;
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
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
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
