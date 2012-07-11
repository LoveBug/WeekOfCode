package map;

import java.io.File;

import items.Coin;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import sprites.Character;
import sprites.Enemy;
import sprites.SpriteSheet;
import swarm.ImageWrapper;

import main.Main;

import items.Item;

public class Map {
	private int enemyTicks;
	private int eCountTick = 0;
	
	private Tile[][] map;
	private File inputfile;
	private Scanner scan;
	private int xDimension;
	private int yDimension;
	private Character character;
	private SpriteSheet worldSprites;
	
	private ArrayList<MoveTile> movingTiles = new ArrayList<MoveTile>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Item> items = new ArrayList<Item>();
	
	private int[][] enemyFrames = {{BLOCK_SIZE*2, BLOCK_SIZE*2, 8}, {BLOCK_SIZE, BLOCK_SIZE, 5}};
	
	public static final int TILE_DEPTH = 5;	
	public static final int BLOCK_SIZE = 32;
	
	public Map(String filename) {
		worldSprites = new SpriteSheet("images/tilesCave.gif");
		inputfile =  new File(filename);
		try {
			 scan = new Scanner(inputfile);
			  xDimension = scan.nextInt();
			  yDimension = scan.nextInt();
			 map = new Tile[xDimension][yDimension];
			readmap();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		enemyTicks = Main.FPS/6;
	}
		
	public Tile[][] getMap(){return map;}

	private void readmap() {
		while(scan.hasNext()){
			
			for(int j = 0; j< yDimension; j++){
				for(int i = 0; i< xDimension; i++){
					int item = scan.nextInt();
					if(item==17){
						character = new Character(i*BLOCK_SIZE,j*BLOCK_SIZE, 64, 96, "images/playerWalk.png");
						map[i][j] = new BackgroundTile(BLOCK_SIZE,BLOCK_SIZE,i*BLOCK_SIZE,j*BLOCK_SIZE,TILE_DEPTH,new ImageWrapper(0, BLOCK_SIZE, BLOCK_SIZE,worldSprites));
					}else if(item<0){
						map[i][j] = new DestTile(BLOCK_SIZE, BLOCK_SIZE, i*BLOCK_SIZE, j*BLOCK_SIZE, TILE_DEPTH, 
								new ImageWrapper(item*-1, BLOCK_SIZE, BLOCK_SIZE, worldSprites), 
								new ImageWrapper(0, BLOCK_SIZE, BLOCK_SIZE, worldSprites));
					}else if(item>199){
						map[i][j] = new BackgroundTile(BLOCK_SIZE, BLOCK_SIZE, i*BLOCK_SIZE, j*BLOCK_SIZE,
								TILE_DEPTH, new ImageWrapper(0, BLOCK_SIZE, BLOCK_SIZE, worldSprites));
						switch(item - 200){
						case 0:
							items.add(new Coin(i*BLOCK_SIZE, j*BLOCK_SIZE, 32, 32, "images/pickupCoin.png", 4));
						}
					}else if(item>99){
						map[i][j] = new BackgroundTile(BLOCK_SIZE, BLOCK_SIZE, i*BLOCK_SIZE, j*BLOCK_SIZE,
								TILE_DEPTH, new ImageWrapper(0, BLOCK_SIZE, BLOCK_SIZE, worldSprites));
						enemies.add(new Enemy(i*BLOCK_SIZE, j*BLOCK_SIZE, enemyFrames[item - 100][0],enemyFrames[item - 100][1], "images/enemyMove" + (item-100) + ".png", enemyFrames[item - 100][2]));	
					}else if(item>17){
						map[i][j] = new BackgroundTile(BLOCK_SIZE, BLOCK_SIZE, i*BLOCK_SIZE, j*BLOCK_SIZE,
								TILE_DEPTH, new ImageWrapper(0, BLOCK_SIZE, BLOCK_SIZE, worldSprites));
						movingTiles.add(new MoveTile(BLOCK_SIZE, BLOCK_SIZE, i*BLOCK_SIZE, j*BLOCK_SIZE,
								TILE_DEPTH, new ImageWrapper(item-17, BLOCK_SIZE, BLOCK_SIZE, worldSprites),
								j*BLOCK_SIZE, (j-10)*BLOCK_SIZE, 4, i*BLOCK_SIZE, (i+10)*BLOCK_SIZE, 4));																//minus 17 as dont want access to moving background tiles
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
	
	public ArrayList<MoveTile> movingTiles(){
		return this.movingTiles;
	}
	
	public ArrayList<Enemy> enemies(){
		return this.enemies;
	}
	
	public ArrayList<Item> items(){
		return this.items;
	}
	
	public void update(){
		//update moving tiles
		for(MoveTile t : this.movingTiles)
			t.move();
		
		if(eCountTick >= enemyTicks){
			for(Item e : items)
				e.move(this);
		}
		
		//update enemies
		if(eCountTick >= enemyTicks){
			eCountTick = 0;
			for(Enemy e : enemies)
				e.move(this);
		}else
			eCountTick++;
		
		//update player
		this.character.move(this);
	}
}
