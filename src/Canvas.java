import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;



public class Canvas{
	
	private Tile[][] map;
	private CanvasPanel panel;
	
	public Canvas(Map map) {
		// TODO Auto-generated constructor stub
		JFrame frame = new JFrame("Swarm");
		this.map = map.getMap();
		panel = new CanvasPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void repaint(){
		panel.repaint();
	}
	
	private class CanvasPanel extends JPanel implements KeyListener{
		private static final long serialVersionUID = 8187920910989915064L;

		public CanvasPanel(){
			setPreferredSize(new Dimension(1024,768));
		}
		
		public void paint(Graphics g){
			for(int i = 0; i < map.length;i++){
				for(int j = 0; j < map[0].length;)
			g.drawImage(map[i][j].getImage().getImage(), map[i][j].getX(), map[i][j].getY(), null);
				
			
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A)
			{
				
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D)
			{
				
			}
			else if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W)
			{
				
			}
			else if(e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==KeyEvent.VK_S)
			{
				
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
