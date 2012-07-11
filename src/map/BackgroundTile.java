package map;
import swarm.ImageWrapper;




public class BackgroundTile extends Tile{
	public BackgroundTile(int height, int width, int x, int y, int depth,
			ImageWrapper image) {
		super(height, width, x, y, depth, image);
		this.setHitbox(null);
	}
}
