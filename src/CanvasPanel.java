import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class CanvasPanel extends JPanel implements KeyListener,
		MouseMotionListener {

	private static final long serialVersionUID = 8187920910989915064L;
	private Cursor cursor;
	private Map map;

	private boolean leftKeyPressed;
	private boolean rightKeyPressed;
	private boolean upKeyPressed;
	// private boolean downKeyPressed; for later when droppng through blocks

	private static final int screenWidth = 1024;
	private static final int screenHeight = 768;

	private static final int blockSize = 32;

	public CanvasPanel(Map m) {
		setPreferredSize(new Dimension(1024, 768));
		cursor = new Cursor(screenWidth / 2 + blockSize / 2, screenHeight / 2
				+ blockSize / 2, blockSize, blockSize);
		this.map = m;
		addKeyListener(this);
		addMouseMotionListener(this);
	}

	public void paint(Graphics g) {
		for (int i = 0; i < map.getMap().length; i++) {
			for (int j = 0; j < map.getMap()[0].length; j++) {
				Tile tempTile = map.getMap()[i][j];
				
				if(!(i > screenWidth/blockSize || j > screenHeight/blockSize)){
					g.drawImage(tempTile.getImageWrapper().getImage(), tempTile.getX(), tempTile.getY(), null);
				}
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
