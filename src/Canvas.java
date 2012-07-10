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
	}
	
	public void repaint(){
		panel.repaint();
	}
	
	private class CanvasPanel extends JPanel implements KeyListener, MouseMotionListener{

		private static final long serialVersionUID = 8187920910989915064L;
		private Cursor cursor;
		
		private boolean leftKeyPressed;
		private boolean rightKeyPressed;
		private boolean upKeyPressed;
		private boolean downKeyPressed;
		
		public CanvasPanel(){
			setPreferredSize(new Dimension(1024,768));
			cursor = new Cursor(1024/2 + 32/2, 768/2 +32/2, 32, 32);
			addKeyListener(this);
			addMouseMotionListener(this);
		}
		
		public void paint(Graphics g){
			for(int i = 0; i < map.getMap().length;i++){
				for(int j = 0; j < map.getMap()[0].length; j++){
					g.drawImage(map.getMap()[i][j].getImage().getImage(), map.getMap()[i][j].getX(), map.getMap()[i][j].getY(), null);
				}
			}
			map.getCharacter().draw(g);
			cursor.draw(g);
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A)
			{
				map.getCharacter().walk(false);
				leftKeyPressed = true;
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D)
			{
				map.getCharacter().walk(true);
				rightKeyPressed = true;

			}
<<<<<<< HEAD

=======
			else if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W)
			{
				upKeyPressed = true;
			}
>>>>>>> f905a3d07a335067a5e703ff6b3595c9fdb9a473
			if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W)
			{
				map.getCharacter().jump();
				upKeyPressed = true;
			}
			/*else if(e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==KeyEvent.VK_A)
			{
				downKeyPressed = true;
			}*/
			movecharacter(leftKeyPressed, rightKeyPressed, upKeyPressed);
		}

		private void movecharacter(boolean leftKeyPressed2,
				boolean rightKeyPressed2, boolean upKeyPressed2/*, boolean downKeyPressed2*/) {
			
			if(leftKeyPressed2)
			{
				map.getCharacter().walk(false);
			}
			if(rightKeyPressed2)
			{
				map.getCharacter().walk(true);
			}
			if(upKeyPressed2)
			{
				map.getCharacter().jump();
			}
			/*if(downKeyPressed2)
			{
				
			}*/
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A)
			{
				leftKeyPressed = false;
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D)
			{
				rightKeyPressed = false;

			}
			else if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W)
			{
				upKeyPressed = false;
			}
			/*else if(e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==KeyEvent.VK_A)
			{
				downKeyPressed = false;
			}*/
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			cursor.setX(e.getX());
			cursor.setY(e.getY());
			requestFocusInWindow();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			cursor.setX(e.getX());
			cursor.setY(e.getY());
			requestFocusInWindow();
		}
	}
}
