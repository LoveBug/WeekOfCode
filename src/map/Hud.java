package map;

import java.awt.Graphics;
import java.util.ArrayList;

import items.Item;

import sprites.Character;
import swarm.ImageWrapper;

public class Hud {
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
	private ImageWrapper weaponImage;
	private int BAR_WIDTH = 256;
	private int BAR_LENGTH = 128;
	private Item selectedItem;

	Hud(Character character, int ScreenX, int ScreenY) {
		this.toplayerimage = new ImageWrapper("images/hudLayerTop.png");
		this.bottomLayerimage = new ImageWrapper("images/hudLayerBottom.png");
		this.healthtile = new ImageWrapper("images/hudRedBlur.png");
		this.manatile = new ImageWrapper("images/hudGoldBlur.png");
		this.character = character;
		this.characterItems = character.getItemList();
		this.characterHealth = character.getHealth();
		this.characterMana = character.getMana();
		this.selectedItem = this.character.getCurrentItem();
		
	}

	public void update() {
		coinCount = character.getGold();
		characterItems = character.getItemList();
		characterMana = character.getMana();
		characterHealth = character.getHealth();
		selectedItem = character.getCurrentItem();

	}

	public void draw(Graphics g) {
		bottomLayerimage.draw(g, ScreenX, ScreenY, BAR_WIDTH, BAR_LENGTH);
		for (int i = 0; i < Math.round((characterHealth/(float)character.getMaxHealth())*58); i++) {
			healthtile.draw(g, 10 + (4 * i), 10, 12, 12);
		}

		for (int j = 0; j < characterMana; j++) {
			manatile.draw(g, 68 + (2 * j), 28, 8, 8);
		}
		
		toplayerimage.draw(g, ScreenX, ScreenY, BAR_WIDTH, BAR_LENGTH);
		if(selectedItem !=null){
		selectedItem.getImage().draw(g, ScreenX+2, ScreenY+21, 64, 64);
		}
		}

}
