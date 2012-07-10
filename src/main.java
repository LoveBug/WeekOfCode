import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class main implements ActionListener{
	private static int fps = 160;
	private Canvas canvas;
	private Map map;
	
	private int timeStart;
	private int timeSinceLastCall;
	private int tick;
	private Timer t;
	
	
	
	public main()
	{
		map = new Map("testLevel.txt");
		
		canvas = new Canvas(map);
		this.tick = 1000/fps;
		t = new Timer(this.tick, this); 

	}

	public static void main(String[] args){
		new main().start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		map.getCharacter().move(map.getMap());
		canvas.repaint();
	}
	
	public void start(){
		t.start();
	}
	

}
