package map;

import items.Item;
import sprites.Character;

import java.util.ArrayList;

public class Hud {
	Character character;
	ArrayList<Item> characterItems;
	
	 public Hud(Character character) {
		 
		 this.character = character;
		 this.characterItems = character.getItemList();		
	}
	
	 
	
	
}
