package map;
import swarm.ImageWrapper;




public class MoveTile extends Tile{
	private int upper,lower,left,right;
	private int xspeed, yspeed;
		
	public MoveTile(int height, int width, int x, int y, int depth, ImageWrapper image, 
			int upperBound, int rightBound) {
		super(height, width, x, y, depth, image);
		lower = y;
		left = x;
		upper = upperBound;
		right = rightBound;
		if(lower!=upper)
			this.yspeed = 4;
		if(right!=left)
			this.xspeed = 4;
	}
	
	public void move(){
		if(xspeed!=0){
			setX(getX()+xspeed);
			if(getX()>this.right || getX()<this.left)
				xspeed = -xspeed;
		}
		if(yspeed!=0){
			setY(getY()+yspeed);
			if(getY()>this.upper || getY()<this.lower)
				yspeed = -yspeed;
		}
	}
	
	public int getYSpeed(){
		return this.yspeed;
	}
	
	public int getXSpeed(){
		return this.xspeed;
	}
	
	public void setYSpeed(int y){
		this.yspeed = y;
	}
	
	public void setXSpeed(int x){
		this.xspeed = x;
	}
}
