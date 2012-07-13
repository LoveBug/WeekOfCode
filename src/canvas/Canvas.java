package canvas;
import javax.swing.*;

import swarm.ImageWrapper;

import map.Map;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas{
	
	private JFrame frame;
	private CanvasPanel panel;
	
	public Canvas(){
		frame = new JFrame("Swarm");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setCursor(frame.getToolkit().createCustomCursor(
	            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
	            "null"));
		
		frame.setVisible(true);	
	}
	
	public Canvas(Map map) {
		frame = new JFrame("Swarm");
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
	
	public void setMap(Map map){
		panel.changeMap(map);
	}
	
	public Camera getCamera(){
		return this.panel.getCamera();
	}
	
	public void repaint(){
		panel.movecharacter();
		panel.repaint();
	}
	
}
