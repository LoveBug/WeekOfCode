import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class ImageWrapper {
	private BufferedImage picture;
	private Color transparency;

	public ImageWrapper(String location, Color colorKey) {
		File myfile = new File(location);

		try {
			picture = ImageIO.read(myfile);

		} catch (IOException e) {
			System.out.println("the error is " + e);
		}

		transparency = colorKey;
	}

	public ImageWrapper(String location) {
		File myfile = new File(location);

		try {
			picture = (BufferedImage)ImageIO.read(myfile);

		} catch (IOException e) {
			System.out.println("the error is " + e);
		}
		
		transparency = new Color(picture.getRGB(0, 0));
	}
	
	public ImageWrapper(int bit, SpriteSheet sprites)
	{
		if(bit == -1){bit = 0;}
		
		File myfile;
		
		if(bit < 16)
		{
			myfile = new File("images/platformTiles.gif");
		}
		else if(bit == 16)
		{
			myfile = new File("images/playerSpritePrelim.gif");
		}
		
	}
	
	public void draw(Graphics g, int x, int y, int width, int height)
	{
		g.drawImage(picture, x, y, width, height, null);
	}

	public Image getImage() {
		return (picture);
	}

	public void setImage(BufferedImage tempImage) {
		picture = tempImage;
	}

	public Color getTranspercy() {
		return transparency;
	}

	public void setTranspercy(Color transparency) {
		this.transparency = transparency;
	}

}
