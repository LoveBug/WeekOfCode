package map;

import java.io.File;

import items.Bullet;
import items.Coin;
import items.WeaponItem;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import sprites.*;
import swarm.ImageWrapper;
import sprites.Character;

import items.Item;

public class Map {
	private Tile[][] map;
	private Scanner scan;
	private int xDimension;
	private int yDimension;
	private Character character;
	private SpriteSheet worldSprites;
	private Hud hud ;
	
	private Sprite entrance;
	private Exit exit;
	
	private ArrayList<MoveTile> movingTiles = new ArrayList<MoveTile>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	private boolean complete = false;
	private boolean dead = false;
	
	private int[][] enemyFrames = {{BLOCK_SIZE*2, BLOCK_SIZE*2, 8, 6}, {BLOCK_SIZE, BLOCK_SIZE, 5, 9}};
	
	public static final int TILE_DEPTH = 5;	
	public static final int BLOCK_SIZE = 32;
	
	public Map(String filename) {
		worldSprites = new SpriteSheet("images/tilesCave.gif");

		try {
			File file = new File(filename);
			scan = new Scanner(file);
			xDimension = scan.nextInt();
			yDimension = scan.nextInt();
			map = new Tile[xDimension][yDimension];
			enemies =  new ArrayList<Enemy>();
			movingTiles = new ArrayList<MoveTile>();
			items = new ArrayList<Item>();
			readmap();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//readWeapons();

		hud = new Hud(character,0,0);
	}
		
	public Tile[][] getMap(){return map;}

	private void readmap() {
		while(scan.hasNext()){
			
			for(int j = 0; j< yDimension; j++){
				for(int i = 0; i< xDimension; i++){
					int item = scan.nextInt();
					if(item==17){	
						character = new Character(i*BLOCK_SIZE,j*BLOCK_SIZE, 64, 96, "images/playerWalk.png",1000);

						map[i][j] = new BackgroundTile(BLOCK_SIZE,BLOCK_SIZE,i*BLOCK_SIZE,j*BLOCK_SIZE,TILE_DEPTH,new ImageWrapper(0, BLOCK_SIZE, BLOCK_SIZE,worldSprites));

						entrance = new SpriteImage(i*BLOCK_SIZE, j*BLOCK_SIZE, 64, 96, "images/doorIn.png", 0);

					}else if(item<0){
						map[i][j] = new DestTile(BLOCK_SIZE, BLOCK_SIZE, i*BLOCK_SIZE, j*BLOCK_SIZE, TILE_DEPTH, 
								new ImageWrapper(item*-1, BLOCK_SIZE, BLOCK_SIZE, worldSprites), 
								new ImageWrapper(0, BLOCK_SIZE, BLOCK_SIZE, worldSprites));
					}else if(item>299){
						map[i][j] = new BackgroundTile(BLOCK_SIZE, BLOCK_SIZE, i*BLOCK_SIZE, j*BLOCK_SIZE,
								TILE_DEPTH, new ImageWrapper(0, BLOCK_SIZE, BLOCK_SIZE, worldSprites));
						exit = new Exit(i*BLOCK_SIZE,j*BLOCK_SIZE, 64, 96, "images/doorOut.png",0);
					}else if(item>199){
						map[i][j] = new BackgroundTile(BLOCK_SIZE, BLOCK_SIZE, i*BLOCK_SIZE, j*BLOCK_SIZE,
								TILE_DEPTH, new ImageWrapper(0, BLOCK_SIZE, BLOCK_SIZE, worldSprites));
						WeaponItem.Type type = null;
						switch(item - 200){
						case 0:
							items.add(new Coin(i*BLOCK_SIZE, j*BLOCK_SIZE, 32, 32, "images/pickupCoin.png", 4));
							break;
						case 1:
							type = WeaponItem.Type.SWORD;
							break;
						case 2:
							type = WeaponItem.Type.MACE;
							break;
						case 3:
							type = WeaponItem.Type.PISTOL;
							break;
						case 4:
							type = WeaponItem.Type.CROSSBOW;
							break;
						default:
							throw new RuntimeException("wat?");
						}
						if (type != null) {
							items.add(new WeaponItem(i*BLOCK_SIZE, j*BLOCK_SIZE, 32, 32,"images/weapon" + type.index + ".png", type));
						}
					}else if(item>99){
						map[i][j] = new BackgroundTile(BLOCK_SIZE, BLOCK_SIZE, i*BLOCK_SIZE, j*BLOCK_SIZE,
								TILE_DEPTH, new ImageWrapper(0, BLOCK_SIZE, BLOCK_SIZE, worldSprites));
						
						Enemy.Type type = null;
						switch(item-100){
						case 0:
							type = Enemy.Type.WALKER;
							break;
						case 1:
							type = Enemy.Type.FLYER;
							break;
						default:
							throw new RuntimeException("wat?");
						}
						if(type!=null)
							if(type == Enemy.Type.FLYER)
								enemies.add(new FlyingEnemy(i*BLOCK_SIZE, j*BLOCK_SIZE, enemyFrames[item - 100][0],enemyFrames[item - 100][1], "images/enemyMove" + (item-100) + ".png", enemyFrames[item - 100][2], "images/enemyDeath" + (item-100) + ".png", enemyFrames[item-100][3], 1));
							else
								enemies.add(new Enemy(i*BLOCK_SIZE, j*BLOCK_SIZE, enemyFrames[item - 100][0],enemyFrames[item - 100][1], "images/enemyMove" + (item-100) + ".png", enemyFrames[item - 100][2], "images/enemyDeath" + (item-100) + ".png", enemyFrames[item-100][3], 3));
					}else if(item>17){
						map[i][j] = new BackgroundTile(BLOCK_SIZE, BLOCK_SIZE, i*BLOCK_SIZE, j*BLOCK_SIZE,
								TILE_DEPTH, new ImageWrapper(0, BLOCK_SIZE, BLOCK_SIZE, worldSprites));
						int up = scan.nextInt();
						int right = scan.nextInt();
						int down = j;
						int left = i;
						if(up<0){
							down += up;
							up *= -1;
						}
						if(right<0){
							left += right;
							right *= -1;
						}
						movingTiles.add(new MoveTile(BLOCK_SIZE, BLOCK_SIZE, left*BLOCK_SIZE, down*BLOCK_SIZE,
								TILE_DEPTH, new ImageWrapper(item-17, BLOCK_SIZE, BLOCK_SIZE, worldSprites),
								(down+up)*BLOCK_SIZE, (left+right)*BLOCK_SIZE));																//minus 17 as dont want access to moving background tiles
					}else{
						ImageWrapper imgwrap = new ImageWrapper(item, BLOCK_SIZE, BLOCK_SIZE, worldSprites);
						if(item==0)
							map[i][j] = new BackgroundTile(BLOCK_SIZE, BLOCK_SIZE, i*BLOCK_SIZE, j*BLOCK_SIZE, TILE_DEPTH, imgwrap);
						else
							map[i][j] = new FloorTile(BLOCK_SIZE,BLOCK_SIZE,i*BLOCK_SIZE,j*BLOCK_SIZE,TILE_DEPTH,imgwrap);
					}
				}
			}
		}
	}

	public Character getCharacter() {
		return character;
	}
	
	public void setCharacter(Character c) {
		this.character = c;;
	}
	
	public ArrayList<MoveTile> movingTiles(){
		return this.movingTiles;
	}
	
	public ArrayList<Enemy> enemies(){
		return this.enemies;
	}
	

	public Hud getHud(){
		return hud;
	}
	public ArrayList<Item> items(){
		return this.items;

	}
	
	public void update(){
		
		
		//update moving tiles
		for(MoveTile t : this.movingTiles)
			t.move();

		for(Item e : items)
			e.move(this);
		
		//update enemies
		ArrayList<Enemy> temp = new ArrayList<Enemy>();
		for(Enemy e : enemies){
			if(e.getDead())
				temp.add(e);
			else
				e.move(this);
		}
		enemies.removeAll(temp);
		
		ArrayList<Bullet> btemp = new ArrayList<Bullet>();	
		for(Bullet b: bullets){
			b.move(this);
			if(b.isDestroy()){
				btemp.add(b);
			}
			
			for(Enemy e : enemies){
				if(b.getMovementBox().checkCollision(e.getMovementBox())){
					b.destroy();
					e.damage(1);
				}
			}
		}
		bullets.removeAll(btemp);		
		
		//update player
		this.character.move(this);
		//update hud
		this.hud.update();
		
	}

	public Sprite getEntrance() {
		return entrance;
	}

	public Exit getExit() {
		return exit;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public void addBullet(Bullet b) {
		bullets.add(b);
	}
}
