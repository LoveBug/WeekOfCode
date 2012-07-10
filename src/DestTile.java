
public class DestTile extends Tile{
	private ImageWrapper destroyedImage;
	private boolean destroyed;
	
	public DestTile(int height, int width, int x, int y, int depth,
			ImageWrapper image, ImageWrapper destImage) {
		super(height, width, x, y, depth, image);
		this.destroyedImage = destImage;
		this.destroyed = false;
	}
	
	public void destroy(){
		if(this.destroyed)
			return;
		this.destroyed=true;
		this.setHitbox(null);
		this.getImageWrapper().setImage(destroyedImage.getImage());
	}
}
