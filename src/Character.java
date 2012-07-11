public class Character extends Sprite implements Drawable{
	
	private final int JUMP_FRAMES = 5;
	private final int IMAGE_WIDTH = 2;
	private final int IMAGE_HEIGHT = 3;
	
	private final int JUMP_HEIGHT = 4;
	private final int TURN_COUNT = 120;
	private final int FALL_MAX = 16;
	
	private int turnCounter = 0;
	
	private boolean isJumping = false;
	private boolean isFalling = false;
	
	private int maxJump;
	private float ySpeed;
	private float xSpeed = 0;
	
	private SpriteSheet jumpSprite, stationarySprite;
	
	public Character(int x, int y, int width, int height, String runCycle)
	{
		super(x,y,width,height,runCycle, 8);
		
		this.jumpSprite = new SpriteSheet("images/jumpCyclePrelimSheetAlpha.png");
		this.stationarySprite = new SpriteSheet("images/playerSpriteFinalDesign.png");

		this.ySpeed = getMoveDistance();
	}
	
	public void walk(boolean direction){
		if(!(isJumping || isFalling))
			super.walk(direction);
		setDirection(direction);
		setWalking(true);
	}
	
	public void move(Map map)
	{
		jumpMovement(map);
		
		if(this.xSpeed!=0){
			setX((int)(getX()+this.xSpeed));
			getMovementBox().setX(getX());
			this.xSpeed = 0;
		}
		
		super.move(map);
		
		if(!isWalking() && !isJumping){
			if(turnCounter<0){
				setCurrentImage(0);
				updateImage(new ImageWrapper(getCurrentImage(), getWidth(), getHeight(), stationarySprite));
			}else
				this.turnCounter--;
		}else
			this.turnCounter = TURN_COUNT;
		setWalking(false);
	}
	
	public void jumpMovement(Map map){
		if(isJumping){
			if(getCurrentImage()<(JUMP_FRAMES-2)*IMAGE_WIDTH){
				setCurrentImage(getCurrentImage()+IMAGE_WIDTH);
				updateImage(new ImageWrapper(getCurrentImage(), getWidth(), getHeight(), jumpSprite));
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
						ySpeed = getMoveDistance();
						break;
					}
				}
			}
		}else{
			boolean falling = true;
			MoveTile tile = null;
			Enemy enemy = null;
			
			int x=getX()+20;
			int end = getX()+getWidth()-10;
			if(!getDirection()){
				x=getX()+10;
				end=getX()+getWidth()-20;
			}
			
			Hitbox temp = new Hitbox(getMovementBox().getX(), 
					getMovementBox().getY()+getMovementBox().getHeight()-5,
					getMovementBox().getWidth(), getMovementBox().getY()+getMovementBox().getHeight()+1);
			
			for(; x<end; x++){
				if(temp.checkCollision(map.getMap()[x/32][(getY()+getHeight())/32].getHitbox())){
					falling = false;
					if(map.getMap()[x/32][(getY()+getHeight())/32] instanceof DestTile)
						((DestTile)map.getMap()[x/32][(getY()+getHeight())/32]).destroy();
				}
			}
			//check moving tile collision
			for(MoveTile t : map.movingTiles())
				if(temp.checkCollision(t.getHitbox()) && getY()+getHeight()+this.ySpeed>=t.getY()){
					falling = false;
					tile = t;
					break;
				}
			
			//check enemy-foot collision
			for(Enemy e : map.enemies())
				if(temp.checkCollision(e.getMovementBox()) && getY()+getHeight()+this.ySpeed>=e.getY()){
					falling = false;
					enemy = e;
					break;
				}
			
			if(falling)
				fall();
			else
				land(tile, enemy);
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
			setCurrentImage(0);
		isFalling = true;
		getMovementBox().setY(getY());
		if(getCurrentImage()<(JUMP_FRAMES-2)*IMAGE_WIDTH){
			setCurrentImage(getCurrentImage()+IMAGE_WIDTH);
			updateImage(new ImageWrapper(getCurrentImage(), getWidth(), getHeight(), jumpSprite));
		}
	}
	
	public void land(MoveTile tile, Enemy enemy){
		if(this.isFalling){
			setCurrentImage(0);
			updateImage(new ImageWrapper(getCurrentImage(), getWidth(), getHeight(), getSpriteSheet()));
		}
		if(tile!=null){
			setY(tile.getY()-getHeight());
			getMovementBox().setY(getY());
			this.ySpeed = tile.getYSpeed();
			this.xSpeed = tile.getXSpeed();		
		}else if(enemy!=null){
			setY(enemy.getY()-getHeight());
			System.out.println("I straight up murdered " + enemy);
			jump();
		}else{
			setY(getY()/32*32);
			this.ySpeed = 0;
		}
		this.isFalling = false;
	}
	
	public void jump()
	{
		if(isJumping || isFalling){return;}
		setCurrentImage(0);
		updateImage(new ImageWrapper(getCurrentImage(), getWidth(), getHeight(), jumpSprite));
		isJumping = true;
		maxJump = getY() - Map.BLOCK_SIZE*JUMP_HEIGHT;  //4 blocks of 32
		ySpeed = FALL_MAX/2;
	}	
	
	
	
	
}
