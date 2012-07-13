package items;

import map.Map;
import sprites.Sprite;

public class Bullet extends Sprite{
	
	private int speedX;
	private int speedY;
	
	private int startX;
	private int startY;
	
	private int directionX;
	private int directionY;
	
	private boolean destroy = false;

	public Bullet(int x, int y, int width, int height, String runCycle,
			int spritenumber, int dirX, int dirY) {
		super(x, y, width, height, runCycle, spritenumber);
		
		directionX = dirX;
		directionY = dirY;
		startX = x;
		startY = y;
		
		double unnormalX = dirX - startX;
		double unnormalY = dirY - startY;
		
		double speed = Math.sqrt((unnormalX*unnormalX) + (unnormalY*unnormalY));
		setSpeedX((int) (20.0*(unnormalX)/speed));
		setSpeedY((int) (20.0*(unnormalY)/speed));
	}
	
	@Override
	public void move(Map map){
		int movementX = speedX;
		int movementY = speedY;
		
		boolean dirX = directionX - startX > 0;
		boolean dirY = directionY - startY > 0;
			
		setX(getX()+movementX);
		getMovementBox().setX(getX());
		int x = getX();
		if(dirX)
			x+=getWidth();
		for(int y=getY()+5; y<getY()+getHeight()-10; y++){
			if(getMovementBox().checkCollision(map.getMap()[x/32][y/32].getHitbox())){
				destroy = true;
			}
		}
		setY(getY()+movementY);
		getMovementBox().setY(getY());
		int y = getY();
		if(dirY)
			y+=getWidth();
		for(int nX=getY()+5; nX<getX()+getHeight()-10; nX++){
			if(getMovementBox().checkCollision(map.getMap()[nX/32][y/32].getHitbox())){
				destroy = true;
			}
		}
		
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public boolean isDestroy() {
		return destroy;
	}
	
	public void destroy() {
		destroy = true;
	}

	@Override
	public void jump() {
		
	}

}
