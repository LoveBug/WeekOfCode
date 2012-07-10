
public class DestTile extends Tile{
	private ImageWrapper destroyed;
	
	public DestTile(int height, int width, int x, int y, int depth,
			ImageWrapper image, ImageWrapper destImage) {
		super(height, width, x, y, depth, image);
		this.destroyed = destImage;
	}
	
	public void destroy(){
		this.setHitbox(null);
		this.getImageWrapper().setImage(destroyed.getImage());
	}
}
