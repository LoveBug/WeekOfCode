package map;

import java.util.ArrayList;

import sprites.Item;
import sprites.Character;

public class Hud {
	Character character;
	ArrayList<Item> characterItems;
	
	 Hud(Character character) {
		 
		 this.character = character;
		 this.characterItems = character.getItemList();		
	}
	
	 
	
	
}
