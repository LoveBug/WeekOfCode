package map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import sprites.Character;
import sprites.Enemy;
import sprites.SpriteSheet;
import swarm.ImageWrapper;

import main.Main;



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
	private Hud hud ;
	
	private ArrayList<MoveTile> movingTiles = new ArrayList<MoveTile>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	public static final int TILE_DEPTH = 5;	
	public static final int BLOCK_SIZE = 32;
	
	public Map(String filename) {
		worldSprites = new SpriteSheet("images/platformTiles.gif");
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
		//Must be after Readmap is called
		 hud = new Hud(character);
	}
		
	public Tile[][] getMap(){return map;}

	private void readmap() {
		while(scan.hasNext()){
			
			for(int j = 0; j< yDimension; j++){
				for(int i = 0; i< xDimension; i++){
					int item = scan.nextInt();
					if(item==17){
						character = new Character(i*BLOCK_SIZE,j*BLOCK_SIZE, 64, 96, "images/runCyclePrelimSheetAlpha.png",100);
						map[i][j] = new BackgroundTile(BLOCK_SIZE,BLOCK_SIZE,i*BLOCK_SIZE,j*BLOCK_SIZE,TILE_DEPTH,new ImageWrapper(0, BLOCK_SIZE, BLOCK_SIZE,worldSprites));
					}else if(item<0){
						map[i][j] = new DestTile(BLOCK_SIZE, BLOCK_SIZE, i*BLOCK_SIZE, j*BLOCK_SIZE, TILE_DEPTH, 
								new ImageWrapper(item*-1, BLOCK_SIZE, BLOCK_SIZE, worldSprites), 
								new ImageWrapper(0, BLOCK_SIZE, BLOCK_SIZE, worldSprites));
					}else if(item>99){
						map[i][j] = new BackgroundTile(BLOCK_SIZE, BLOCK_SIZE, i*BLOCK_SIZE, j*BLOCK_SIZE,
								TILE_DEPTH, new ImageWrapper(0, BLOCK_SIZE, BLOCK_SIZE, worldSprites));
						enemies.add(new Enemy(i*BLOCK_SIZE, j*BLOCK_SIZE, 64,64, "images/enemyAnimationSheet.png", 8, 1));	
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
	
	public void update(){
		
		
		//update moving tiles
		for(MoveTile t : this.movingTiles)
			t.move();
		
		//update enemies
		if(eCountTick >= enemyTicks){
			eCountTick = 0;
			for(Enemy e : enemies)
				e.move(this);
		}else
			eCountTick++;
		
		//update player
		this.character.move(this);
		//update hud
		this.hud.update();
		
	}
}
