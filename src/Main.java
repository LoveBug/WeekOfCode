import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class Main implements ActionListener{
	private static int fps = 60;
	private Canvas canvas;
	private Map map;

	private int tick;
	private Timer t;
	
	public Main()
	{
		map = new Map("testLevel2.txt");
		
		canvas = new Canvas(map);
		this.tick = 1000/fps;
		t = new Timer(this.tick, this); 

	}

	public static void main(String[] args){
		new Main().start();
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
