package canvas;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import sprites.Enemy;
import swarm.ImageWrapper;
import items.Item;
import map.Map;
import map.MoveTile;
import map.Tile;


public class CanvasPanel extends JPanel implements KeyListener,
		MouseMotionListener,MouseListener {

	private static final long serialVersionUID = 8187920910989915064L;
	private Cursor cursor;
	private Map map;
	private Camera cam;
	private Robot mouseController;

	private boolean leftKeyPressed;
	private boolean rightKeyPressed;
	private boolean upKeyPressed;
	
	private ImageWrapper bg;

	// private boolean downKeyPressed; for later when droppng through blocks

	private static final int blockSize = 32;

	public CanvasPanel(Map m) {
		setPreferredSize(new Dimension(1024, 768));
		setDoubleBuffered(true);
		
		try {
			mouseController = new Robot();
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.map = m;
		this.cam = new Camera(map.getCharacter());
		cursor = new Cursor(0, 0, blockSize, blockSize, cam);
		mouseController.mouseMove(0, 0);
		
		 bg = new ImageWrapper("images/Background.jpg");
		
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		
	}

	public void paint(Graphics g) {
		
		g.drawImage(bg.getImage(), 0, 0, null);
	
		
		g.translate(-cam.getX(), -cam.getY());
		
		for (int i = 0; i < map.getMap().length; i++) {
			for (int j = 0; j < map.getMap()[0].length; j++) {
				Tile tempTile = map.getMap()[i][j];
				
				tempTile.setX(tempTile.getX());
				tempTile.setY(tempTile.getY());
				
				tempTile.draw(g);
				//g.drawImage(tempTile.getImageWrapper().getImage(), tempTile.getX()-cam.getX(), tempTile.getY()-cam.getY(), null);
				
			}
		}
		
		for(MoveTile m: map.movingTiles() )
			m.draw(g);

		for(Enemy e : map.enemies())
			e.draw(g);
		
		for(Item i: map.items())
			i.draw(g);
	
		map.getCharacter().draw(g);
			
		cursor.draw(g);
		g.translate(cam.getX(), cam.getY());
		
		map.getHud().draw(g);
		
			
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_A) {
			leftKeyPressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_D) {
			rightKeyPressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_UP
				|| e.getKeyCode() == KeyEvent.VK_W) {
			upKeyPressed = true;
		}
		/*
		 * else if(e.getKeyCode()==KeyEvent.VK_DOWN ||
		 * e.getKeyCode()==KeyEvent.VK_A) { downKeyPressed = true; }
		 */
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if    (e.getKeyCode() == KeyEvent.VK_LEFT
			|| e.getKeyCode() == KeyEvent.VK_A) {
			leftKeyPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_D) {
			rightKeyPressed = false;

		} else if (e.getKeyCode() == KeyEvent.VK_UP
				|| e.getKeyCode() == KeyEvent.VK_W) {
			upKeyPressed = false;
		}else if(e.getKeyCode()== KeyEvent.VK_ESCAPE){
			System.exit(0);
			
		}
		/*
		 * else if(e.getKeyCode()==KeyEvent.VK_DOWN ||
		 * e.getKeyCode()==KeyEvent.VK_A) { downKeyPressed = false; }
		 */

	}

	public void movecharacter() {

		if (leftKeyPressed) {
			map.getCharacter().walk(false);
		
		}
		if (rightKeyPressed) {
			map.getCharacter().walk(true);
		
		}
		if (upKeyPressed) {
			map.getCharacter().jump();
		}
		/*
		 * if(downKeyPressed2) {
		 * 
		 * }
		 */
		
		cam.setX(cursor);
		cam.setY(cursor);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		cursor.moveCursor(e.getX(), e.getY());

		requestFocusInWindow();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		cursor.moveCursor(e.getX(), e.getY());
		
		requestFocusInWindow();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
