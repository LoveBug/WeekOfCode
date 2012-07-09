import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;



public class Canvas{
	
	private Map map;
	private CanvasPanel panel;
	
	public Canvas(Map map) {
		// TODO Auto-generated constructor stub
		JFrame frame = new JFrame("Swarm");
		this.map = map;
		panel = new CanvasPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		while(true){
			map.getCharacter().move(map.getMap());
			panel.repaint();
		}
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
		 
			for(int i = 0; i < map.getMap().length;i++){
				for(int j = 0; j < map.getMap()[0].length; j++){
					
					
					ImageWrapper testimg = map.getMap()[i][j].getImage();
					Image testimg2 = testimg.getImage();
					
					g.drawImage(map.getMap()[i][j].getImage().getImage(), map.getMap()[i][j].getX(), map.getMap()[i][j].getY(), null);
				}
				}
			map.getCharacter().draw(g);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A)
			{
				map.getCharacter().walk(false);
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D)
			{
				map.getCharacter().walk(true);
			}
			else if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W)
			{
				map.getCharacter().jump();
			}
			/*else if(e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==KeyEvent.VK_A)
			{
				
			}*/
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
