import javax.swing.*;

public class Canvas{
	
	private CanvasPanel panel;
	
	public Canvas(Map map) {
		JFrame frame = new JFrame("Swarm");
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
