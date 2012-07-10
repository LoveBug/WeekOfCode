import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Canvas{
	
	private Map map;
	private CanvasPanel panel;
	
	public Canvas(Map map) {
		JFrame frame = new JFrame("Swarm");
		this.map = map;
		panel = new CanvasPanel(map);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		frame.setVisible(true);	
	}
	
	public void repaint(){
		panel.movecharacter();
		panel.repaint();
	}
	
}
