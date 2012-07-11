package map;

import java.util.ArrayList;

import sprites.Item;
import sprites.Character;

public class Hud {
	private Character character;
	private ArrayList<Item> characterItems;
	private int coinCount;
	 Hud(Character character) {
		 
		 this.character = character;
		 this.characterItems = character.getItemList();		
	}
	
	

	public void update() {
		// TODO Auto-generated method stub
		coinCount = character.getGold();
		
		
	}
	
	 
	
	
}
