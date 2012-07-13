package sprites;

import java.awt.Graphics;

import swarm.Hitbox;
import swarm.ImageWrapper;

import main.Main;
import map.DestTile;
import map.Map;
import map.MoveTile;

public abstract class Sprite {
	private final int MOVE_RATIO = Main.FPS/5;
	private int count;
	
	public final int FRAMES, JUMP_FRAMES;
	public static final int FALL_MAX = 16;
	
	private ImageWrapper image;
	private int height;
	private int width;
	private int x;
	private int y;
	
	private Hitbox movementBox, shootBox;
	
	private boolean direction = true;
	private SpriteSheet sprites, jSprites, dSprites;
	private int moveDistance = 8;
	private int currentImage = 0;
	private int health;
	
	private float ySpeed, xSpeed;
	
	private boolean isWalking = false;
	private boolean isJumping = false;
	private boolean isFalling = false;
	
	private boolean isDeath = false;
	private int deathFrames;
	
	private int maxJump;
	
	public Sprite(int x, int y, int width, int height, String runCycle, int spritenumber){
		setMovementBox(new Hitbox(x,y,width,height));
		setShootBox(new Hitbox(0,0,0,0));
		setX(x);
		setY(y);
		setHeight(height);
		setWidth(width);
		setSpriteSheet(runCycle);
		setImage(new ImageWrapper(spritenumber, getWidth(), getHeight(), getSpriteSheet()));
		this.setHealth(health);
		this.FRAMES = 1;
		this.JUMP_FRAMES = 0;
	}
	
	
	public Sprite(int x, int y, int width, int height, String runCycle, int frames, 
			String jumpCycle, int jframes, String deathCycle, int dframes, int health){
		setMovementBox(new Hitbox(x,y,width,height));
		setShootBox(new Hitbox(0,0,0,0));
		setX(x);
		setY(y);
		setHeight(height);
		setWidth(width);
		setSpriteSheet(runCycle);
		if(jumpCycle==null)
			this.jSprites = null;
		else
			setJumpSprites(jumpCycle);
		if(deathCycle==null)
			this.dSprites = null;
		else
			this.dSprites = new SpriteSheet(deathCycle);
		this.deathFrames = dframes;
		setImage(new ImageWrapper(getCurrentImage(), getWidth(), getHeight(), getSpriteSheet()));
		this.setHealth(health);
		
		this.ySpeed = getMoveDistance();
		
		this.FRAMES = frames;
		this.JUMP_FRAMES = jframes;
	}
	
	public void move(Map map){
		if(health<=0){
			if(count>=MOVE_RATIO){
				count = 0;
				if(getCurrentImage()<this.deathFrames*(this.width/Map.BLOCK_SIZE)){
					setCurrentImage(getCurrentImage()+getWidth()/Map.BLOCK_SIZE);
					updateImage(new ImageWrapper(this.currentImage, getWidth(), getHeight(), this.dSprites));
				}else
					this.isDeath = true;
			}else
				count++;
			return;
		}
		jumpMovement(map);
		
		if(this.xSpeed!=0){
			setX((int)(getX()+this.xSpeed));
			this.xSpeed = 0;
		}
		
		if(isWalking){
			int movement = moveDistance;
			if(!getDirection()){
				movement = -moveDistance;
			}
			
			setX(getX()+movement);
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
			//getMovementBox().setX(getX());
			isWalking=false;
		}
	}
	
	public void jumpMovement(Map map){
		if(isJumping){
			if(getCurrentImage()<(JUMP_FRAMES-2)*getWidth()/Map.BLOCK_SIZE){
				setCurrentImage(getCurrentImage()+getWidth()/Map.BLOCK_SIZE);
				updateImage(new ImageWrapper(getCurrentImage(), getWidth(), getHeight(), getJumpSprites()));
			}
			
			if(ySpeed>7)
				ySpeed -= 0.3;
			else if(ySpeed>3)
				ySpeed -= 0.15;
			else if(ySpeed>1)
				ySpeed -= 0.05;
			
			setY((int)(getY()-ySpeed));
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
			
			int x=getX()+5;
			int end = getX()+getWidth()-10;
			if(!getDirection()){
				x=getX()+10;
				end=getX()+getWidth()-20;
			}
			
			Hitbox temp =new Hitbox(x, getY()+getHeight()-1, getWidth()-10, getHeight()+1); 
			
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
			if(this instanceof Character){
				for(Enemy e : map.enemies())
					if(temp.checkCollision(e.getShootBox()) && getY()+getHeight()+this.ySpeed>=e.getY()){
						falling = false;
						enemy = e;
						break;		
					}
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
		if(getCurrentImage()<(JUMP_FRAMES-2)*getWidth()/Map.BLOCK_SIZE){
			setCurrentImage(getCurrentImage()+getWidth()/Map.BLOCK_SIZE);
			updateImage(new ImageWrapper(getCurrentImage(), getWidth(), getHeight(), jSprites));
		}
	}
	
	public void land(MoveTile tile, Enemy enemy){
		if(this.isFalling){
			setCurrentImage(0);
			updateImage(new ImageWrapper(getCurrentImage(), getWidth(), getHeight(), getSpriteSheet()));
		}
		if(tile!=null){
			setY(tile.getY()-getHeight());
			this.ySpeed = tile.getYSpeed();
			this.xSpeed = tile.getXSpeed();		
		}else if(enemy!=null){
			setY(enemy.getY()-getHeight());
			enemy.damage(1);
			jump();
		}else{
			setY(getY()/32*32);
			this.ySpeed = 0;
		}
		this.isFalling = false;
	}
	
	public abstract void jump();
	
	public void draw(Graphics g){
		image.draw(g, x, y, width, height);
		//movementBox.draw(g);
		//shootBox.draw(g);
	}
	
	public void updateImage(ImageWrapper i){
		if(!this.direction)
			i.reverse();
		this.image = i;
	}
	
	public void walk(boolean direction){
		if(getHealth()<=0)
			return;
		this.currentImage += this.width/Map.BLOCK_SIZE;
		if(this.currentImage>=(FRAMES-1)*this.width/Map.BLOCK_SIZE)
			this.currentImage=0;
		updateImage(new ImageWrapper(this.currentImage, getWidth(), getHeight(), getSpriteSheet()));
		setDirection(direction);
		this.isWalking = true;
	}
	
	public void damage(int dam){
		if(getHealth()<=0)
			return;
		setHealth(getHealth() - dam);
		if(getHealth()<=0){
			setCurrentImage(0);
			setMovementBox(new Hitbox(0,0,0,0));
			setShootBox(new Hitbox(0,0,0,0));
		}
	}
	
	//-------------------SETTERS---------------------------
	public void setX(int x) {
		this.x = x;
		this.movementBox.setX(x);
		this.shootBox.setX(x);
	}
	public void setY(int y) {
		this.y = y;
		this.movementBox.setY(y);
		this.shootBox.setY(y);
	}
	public void setHeight(int h) {this.height = h;}
	public void setWidth(int w) {this.width = w;}
	public void setImage(ImageWrapper image) {this.image = image;}
	public void setMovementBox(Hitbox movementBox) {this.movementBox = movementBox;}
	public void setShootBox(Hitbox shootBox) {this.shootBox = shootBox;}
	public void setDirection(boolean d) {this.direction = d;}
	public void setSpriteSheet(String s) {this.sprites = new SpriteSheet(s);}
	public void setJumpSprites(String s) {this.jSprites = new SpriteSheet(s);}
	public void setSpriteSheet(SpriteSheet s) {this.sprites = s;}
	public void setJumpSprites(SpriteSheet s) {this.jSprites = s;}
	public void setMoveDistance(int m) {this.moveDistance = m;}
	public void setCurrentImage(int c) {this.currentImage = c;}
	public void setWalking(boolean w) {this.isWalking = w;}
	public void setJumping(boolean j) {this.isJumping = j;}
	public void setFalling(boolean f) {this.isJumping = f;}
	public void setJumpHeight(int j) {this.maxJump = j;}
	public void setYSpeed(float s) {this.ySpeed = s;}
	public void setHealth(int health) {this.health = health;}
	
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
	public SpriteSheet getJumpSprites() {return this.jSprites;}
	public int getMoveDistance() {return this.moveDistance;}
	public int getCurrentImage() {return this.currentImage;}
	public boolean isWalking() {return this.isWalking;}
	public boolean isJumping() {return this.isJumping;}
	public boolean isFalling() {return this.isFalling;}
	public int getJumpHeight() {return this.maxJump;}
	public float getYSpeed() {return this.ySpeed;}
	public int getHealth() {return health;}
	public boolean getDead() {return this.isDeath;}
	
}
