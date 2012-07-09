import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;



public class GameTimer implements ActionListener{
	private int timeStart;
	private int timeSinceLastCall;
	private int tick;
	private Timer t;
	private Map map;
	private Canvas canvas;
	
	public GameTimer(int tick, Map map, Canvas c)
	{
		this.tick = tick;
		t = new Timer(this.tick, this); 
		this.map = map;
		this.canvas = c;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		map.getCharacter().move(map.getMap());
		canvas.repaint();
	}
}
