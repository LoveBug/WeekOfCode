package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import canvas.Canvas;

import map.Map;


public class Main implements ActionListener{
	public static final int FPS = 60;
	private Canvas canvas;
	private Map map;

	private int tick;
	private Timer t;
	
	public Main()
	{
		map = new Map("testLevelExpanded.txt");
		
		canvas = new Canvas(map);
		this.tick = 1000/FPS;
		t = new Timer(this.tick, this); 

	}

	public static void main(String[] args){
		new Main().start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		map.update();
		canvas.repaint();
	}
	
	public void start(){
		t.start();
	}
	

}