
public class MoveTile extends Tile{
	private int upper,lower,left,right;
	private int xspeed, yspeed;
	
	public MoveTile(int height, int width, int x, int y, int depth,
			ImageWrapper image, boolean vertical, int upperBound, int lowerBound, int speed) {
		super(height, width, x, y, depth, image);
		if(vertical){
			upper = upperBound;
			lower = lowerBound;
			yspeed = speed;
		}else{
			left = lowerBound;
			right = upperBound;
			xspeed = speed;
		}
	}
	
	public MoveTile(int height, int width, int x, int y, int depth,
			ImageWrapper image, int upperBound, int lowerBound, int yspeed,
			int leftBound, int rightBound, int xspeed) {
		super(height, width, x, y, depth, image);
		upper = upperBound;
		lower = lowerBound;
		this.yspeed = yspeed;
		left = leftBound;
		right = rightBound;
		this.xspeed = xspeed;
	}
	
	public void move(){
		if(xspeed>0){
			setX(getX()+xspeed);
			if(getX()>this.right || getX()<this.left)
				xspeed -= xspeed;
		}
		if(yspeed>0){
			setY(getY()+yspeed);
			if(getY()>this.upper || getX()<this.lower)
				yspeed -= yspeed;
		}
	}
}
