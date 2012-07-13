package canvas;
import javax.swing.*;

import swarm.ImageWrapper;

import map.Map;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas{
	
	private CanvasPanel panel;
	
	public Canvas(Map map) {
		JFrame frame = new JFrame("Swarm");
		panel = new CanvasPanel(map);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setCursor(frame.getToolkit().createCustomCursor(
	            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
	            "null"));
		
		frame.setVisible(true);	
	}
	
	public void setImage(ImageWrapper i){
		panel.setImage(i);
	}
	
	public void repaint(){
		panel.movecharacter();
		panel.repaint();
	}
	
}
