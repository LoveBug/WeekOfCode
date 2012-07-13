package sprites;

import items.Coin;

import items.Heart;
import items.Item;
import items.WeaponItem;

import java.util.ArrayList;

import map.Map;
import swarm.Drawable;
import swarm.Hitbox;
import swarm.ImageWrapper;


public class Character extends Sprite implements Drawable{
	private final int TURN_COUNT = 120;
	private final int JUMP_HEIGHT = 4;
	
	private int turnCounter = 0;
	
	private int gold = 0;
	private ArrayList<Item> itemList = new ArrayList<Item>(); 
	private int mana;
	private int currentItemindex=0;
	private int maxHealth;
	
	private SpriteSheet stationarySprite;
	
	public Character(int x, int y, int width, int height, String runCycle, int health)
	{
		super(x,y,width,height,runCycle, 8, "images/playerJump.png", 5, health);
		setShootBox(new Hitbox(getX()+getWidth()/4, getY()+getHeight()/4, getWidth()/2, getHeight()/2));
		mana = 75;
		maxHealth = health;
		this.stationarySprite = new SpriteSheet("images/playerStand.png");
		setItemList(new ArrayList<Item>());
	}
	
	public void walk(boolean direction){
		if(!(isJumping() || isFalling()))
			super.walk(direction);
		setDirection(direction);
		setWalking(true);
	}
	
	public void move(Map map)
	{	
		boolean temp = isWalking();
		super.move(map); 
		setWalking(temp);
		
		checkCoinCollision(map);
		
		if(this.getShootBox().checkCollision(map.getExit().getMovementBox())){
			map.setComplete(true);
		}
		
		if(!isWalking() && !isJumping()){
			if(turnCounter<0){
				setCurrentImage(0);
				updateImage(new ImageWrapper(getCurrentImage(), getWidth(), getHeight(), stationarySprite));
			}else
				this.turnCounter--;
		}else
			this.turnCounter = TURN_COUNT;
		setWalking(false);
	}
	
	private void checkCoinCollision(Map m){
		ArrayList<Item> temp = new ArrayList<Item>();
		
		for(Item i: m.items()){
			if(this.getMovementBox().checkCollision(i.getMovementBox())){
				temp.add(i);
			}
		}
		for(Item i: temp){
			if (i instanceof WeaponItem){
				itemList.add(i);
			}else if(i instanceof Coin){
				gold++;
				System.out.println("Your gold is: "+ gold);
			}
			else if(i instanceof Heart){
				if(getHealth() + 0.3*maxHealth < maxHealth)
					this.damage(-(int)(maxHealth*0.3));
				else
					setHealth(maxHealth);
			}
		}
		
		m.items().removeAll(temp);
	}
	
	
	public void jump()
	{
		if(isJumping() || isFalling()){return;}
		setCurrentImage(0);
		updateImage(new ImageWrapper(getCurrentImage(), getWidth(), getHeight(), getJumpSprites()));
		setJumping(true);
		setJumpHeight(getY() - Map.BLOCK_SIZE*JUMP_HEIGHT);  //4 blocks of 32
		setYSpeed(Sprite.FALL_MAX/2);
	}

	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getMana() {
		// TODO Auto-generated method stub
		return mana;
	}
	
	public int getMaxHealth(){
		return this.maxHealth;
	}
	
	public void setmaxHealth(int i){
		this.maxHealth = i;
	}
	
	public void setMana(int mana){
	 this.mana = mana;
	}
	public void cycleItem(){
		if(itemList.size()!=0){
		currentItemindex++;
		currentItemindex = currentItemindex%itemList.size();
		}
		}

	public Item getCurrentItem() {
		if(itemList.size() == 0){
			return null;
		}
		return itemList.get(currentItemindex);
	}
	public void giveItem(Item item){
		itemList.add(item);
	}
	public void removeItem(int x){
		itemList.remove(x);
	}

	public void setX(int x) {
		super.setX(x);
		getShootBox().setX(super.getX()+getWidth()/4);
	}
	public void setY(int y) {
		super.setY(y);
		getShootBox().setY(super.getY()+getHeight()/4);
	}

	public WeaponItem getCurrentWeapon() {
		if(getCurrentItem() instanceof WeaponItem){
			System.out.println("Current weapon returned as"+ getCurrentItem());
			return (WeaponItem)getCurrentItem();
		}
		return null;
	}
	
	
	
	
	
}
