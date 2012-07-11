public class Character extends Sprite implements Drawable{
	private final int FRAMES = 8;
	private final int JUMP_FRAMES = 5;
	private final int IMAGE_WIDTH = 2;
	private final int IMAGE_HEIGHT = 3;
	
	private final int JUMP_HEIGHT = 4;
	private final int TURN_COUNT = 120;
	private final int FALL_MAX = 16;
	
	private int turnCounter = 0;
	
	
	
	private int moveDistance = 8;
	private int currentImage = 0;
	
	private boolean isJumping = false;
	private boolean isWalking = false;
	private boolean isFalling = false;
	
	private int maxJump;
	private float ySpeed = moveDistance;
	private float xSpeed = 0;
	
	private SpriteSheet jumpSprite, stationarySprite;
	
	public Character(int x, int y, int height, int width, String runCycle)
	{
		super(x,y,height,width,runCycle);
		
		this.jumpSprite = new SpriteSheet("images/jumpCyclePrelimSheetAlpha.png");
		this.stationarySprite = new SpriteSheet("images/playerSpriteFinalDesign.png");
		
		setImage(new ImageWrapper(currentImage, getWidth(), getHeight(), getSpriteSheet()));
		setMovementBox(new Hitbox(x, y, getWidth(), getHeight()));
	}
	
	
	
	public void move(Map map)
	{
		jumpMovement(map);
		
		if(this.xSpeed!=0){
			setX((int)(getX()+this.xSpeed));
			getMovementBox().setX(getX());
			this.xSpeed = 0;
		}
		
		if(isWalking){
			turnCounter = TURN_COUNT;
			int movement = moveDistance;
			if(!getDirection()){
				movement = -moveDistance;
			}
			
			setX(getX()+movement);
			getMovementBox().setX(getX());
			int x = getX();
			if(getDirection())
				x+=getWidth();
			for(int y=getY()+5; y<getY()+getHeight()-10; y++)
				if(getMovementBox().checkCollision(map.getMap()[x/32][y/32].getHitbox())){
					if(getDirection())
						 setX(getX()/32*32);
					else
						setX(getX()/32*32+32);
					break;
				}
			getMovementBox().setX(getX());
			isWalking=false;
		}
		
		if(!isWalking && !isJumping){
			if(turnCounter<0){
				this.currentImage=0;
				updateImage(new ImageWrapper(currentImage, getWidth(), getHeight(), stationarySprite));
			}else
				turnCounter--;
		}
	}
	
	public void jumpMovement(Map map){
		if(isJumping){
			turnCounter = TURN_COUNT;
			if(this.currentImage<(JUMP_FRAMES-2)*IMAGE_WIDTH){
				this.currentImage+=IMAGE_WIDTH;
				updateImage(new ImageWrapper(currentImage, getWidth(), getHeight(), jumpSprite));
			}
			
			if(ySpeed>7)
				ySpeed -= 0.3;
			else if(ySpeed>3)
				ySpeed -= 0.15;
			else if(ySpeed>1)
				ySpeed -= 0.05;
			
			setY((int)(getY()-ySpeed));
			getMovementBox().setY(getY());
			if(getY()<maxJump){
				isJumping = false;  
				isFalling = true;
				
			}else{
				for(int x=getX(); x<getX()+getWidth(); x++){
					if(getMovementBox().checkCollision(map.getMap()[x/32][((getY()+10)/32)].getHitbox())){
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
			int x=getX()+20;
			int end = getX()+getWidth()-10;
			if(!getDirection()){
				x=getX()+10;
				end=getX()+getWidth()-20;
			}
			for(; x<end; x++){
				if(getMovementBox().checkCollision(map.getMap()[x/32][(getY()+getHeight())/32].getHitbox())){
					falling = false;
					if(map.getMap()[x/32][(getY()+getHeight())/32] instanceof DestTile)
						((DestTile)map.getMap()[x/32][(getY()+getHeight())/32]).destroy();
				}
			}
			//check moving tile collision
			Hitbox temp = new Hitbox(getMovementBox().getX(), 
					getMovementBox().getY()+getMovementBox().getHeight()-5,
					getMovementBox().getWidth(), getMovementBox().getY()+getMovementBox().getHeight()+1);
			for(MoveTile t : map.movingTiles())
				if(temp.checkCollision(t.getHitbox()) && getY()+getHeight()+this.ySpeed>=t.getY()){
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
		setY((int)(getY()+this.ySpeed));
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
		getMovementBox().setY(getY());
		if(this.currentImage<(JUMP_FRAMES-2)*IMAGE_WIDTH){
			this.currentImage+=IMAGE_WIDTH;
			updateImage(new ImageWrapper(currentImage, getWidth(), getHeight(), jumpSprite));
		}
	}
	
	public void land(MoveTile tile){
		if(this.isFalling){
			this.currentImage = 0;
			updateImage(new ImageWrapper(this.currentImage, getWidth(), getHeight(), getSpriteSheet()));
		}
		if(tile==null){
			setY(getY()/32*32);
			this.ySpeed = 4;
		}else{
			setY(tile.getY()-getHeight());
			getMovementBox().setY(getY());
			this.ySpeed = tile.getYSpeed();
			this.xSpeed = tile.getXSpeed();
		}
		this.isFalling = false;
	}
	
	public void jump()
	{
		if(isJumping || isFalling){return;}
		this.currentImage = 0;
		updateImage(new ImageWrapper(currentImage, getWidth(), getHeight(), jumpSprite));
		isJumping = true;
		maxJump = getY() - 32*JUMP_HEIGHT;  //4 blocks of 32
		ySpeed = FALL_MAX/2;
	}	
	
	public void walk(boolean direction){
		if(!(isJumping || isFalling)){
			this.currentImage += IMAGE_WIDTH;
			if(this.currentImage>=(FRAMES-1)*IMAGE_WIDTH)
				this.currentImage=0;
			updateImage(new ImageWrapper(currentImage, getWidth(), getHeight(), getSpriteSheet()));
		}
		setDirection(direction);
		this.isWalking = true;
	}
	
	
}
