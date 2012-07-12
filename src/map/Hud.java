package map;


import java.awt.Graphics;
import java.util.ArrayList;

import items.Item;


import sprites.Character;
import swarm.ImageWrapper;

public class Hud{
	private Character character;
	private ArrayList<Item> characterItems;
	private int coinCount;
	private int ScreenX;
	private int ScreenY;
	private int characterHealth;
	private int characterMana;
	private ImageWrapper toplayerimage;
	private ImageWrapper bottomLayerimage;
	private ImageWrapper healthtile;
	private ImageWrapper manatile;
	private int BAR_WIDTH;
	private int BAR_LENGTH;
	
	Hud(Character character, int ScreenX, int ScreenY) {
		 this.toplayerimage = new ImageWrapper("images/hudLayerTop.png");
		 this.bottomLayerimage = new ImageWrapper("images/hudLayerBottom.png");
		 this.healthtile = new ImageWrapper("images/hudRedBlur.png");
		 this.manatile = new ImageWrapper("images/hudGoldBlur.png");
		 this.character = character;
		 this.characterItems = character.getItemList();		
		 this.characterHealth = character.getHealth();
		 this.characterMana = character.getMana();
	}
	
	

	public void update() {
		coinCount = character.getGold();
		characterItems = character.getItemList();
		characterMana = character.getMana();
		characterHealth = character.getHealth();
		
	}
	
	public void draw(Graphics g){
		bottomLayerimage.draw(g, ScreenX, ScreenY, 256, 128);
		for(int i =0; i<characterHealth;i++){
		healthtile.draw(g, 10+(4*i), 10, 12, 12);
		}
		for(int j =0; j<characterMana;j++){
		manatile.draw(g, 68+(2*j), 28, 8, 8);
		}
		
		toplayerimage.draw(g, ScreenX, ScreenY, 256, 128);
		
	}	
	
	 
	
	
}
