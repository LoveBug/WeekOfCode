package map;


import java.awt.Graphics;
import java.util.ArrayList;

import items.Item;


import sprites.Character;
import sprites.Sprite;
import swarm.ImageWrapper;

public class Hud{
	private Character character;
	private ArrayList<Item> characterItems;
	private int coinCount;
	private int ScreenX;
	private int ScreenY;
	private int characterHealth;
	private int characterMana;
	private ImageWrapper image;
	private int BAR_WIDTH;
	private int BAR_LENGTH;
	
	Hud(Character character, int ScreenX, int ScreenY) {
		 this.image = new ImageWrapper("images/hudBase.png");

		 this.character = character;
		 this.characterItems = character.getItemList();		
		 this.characterHealth = character.getHealth();
		 this.characterMana = character.getMana();
	}
	
	

	public void update() {
		coinCount = character.getGold();
		characterItems = character.getItemList();
		characterMana = character.getMana();
		
	}
	
	public void draw(Graphics g){
		image.draw(g, ScreenX, ScreenY, 256, 128);		
	}	
	
	 
	
	
}
