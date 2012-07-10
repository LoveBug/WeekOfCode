import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class ImageWrapper {
	private BufferedImage picture;
	



	public ImageWrapper(String location) {
		File myfile = new File(location);

		try {
			picture = ImageIO.read(myfile);

		} catch (IOException e) {
			System.out.println("the error is " + e);
		}
		
		
	}
	
	public ImageWrapper(int bit, int width, int height, SpriteSheet sprites){
		picture = sprites.getSprite(bit, width, height);
	}
	
	public void draw(Graphics g, int x, int y, int width, int height)
	{
		g.drawImage(picture, x, y, width, height, null);
	}

	public BufferedImage getImage() {
		return picture;
	}

	public void setImage(BufferedImage tempImage) {
		picture = tempImage;
	}

	
	public void reverse(){
		int height = this.picture.getHeight();
		int width = this.picture.getWidth();
		BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				temp.setRGB(i, j, picture.getRGB(width-i-1, j));
		picture = temp;
	}
}
