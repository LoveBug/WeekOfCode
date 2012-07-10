import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Map {
	private Tile[][] map;
	private File inputfile;
	private Scanner scan;
	private int xDimension;
	private int yDimension;
	private Character character;
	private SpriteSheet worldSprites;
	
	public static final int TILE_DEPTH = 5;
	public static final int BACKGROUND = 0;
	public static final int NO_SIDES =1;
	public static final int OPEN_BOT = 2;
	public static final int OPEN_RIGHT = 4;
	public static final int OPEN_LEFT = 8;
	public static final int OPEN_TOP = 16;
	public static final int TOP_RIGHT = 32;
	public static final int TOP_LEFT = 64;
	public static final int BOT_LEFT = 128;
	public static final int BOT_RIGHT = 256;
	public static final int CLOSED_TOP = 512;
	public static final int CLOSED_RIGHT = 1024;
	public static final int CLOSED_LEFT = 2048;
	public static final int CLOSED_BOT = 4096;
	public static final int PLAYER = 8192;
	
	public static final int BLOCK_SIZE = 32;
	
	public Map(String filename) {
	// TODO Auto-generated constructor stub
		worldSprites = new SpriteSheet("images/platformTiles.gif");
		inputfile =  new File(filename);
		try {
			 scan = new Scanner(inputfile);
			  xDimension = scan.nextInt();
			  yDimension = scan.nextInt();
			 map = new Tile[xDimension][yDimension];
			readmap();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public Tile[][] getMap(){return map;}

	private void readmap() {
		// TODO Auto-generated method stub
		while(scan.hasNext()){
			
			for(int j = 0; j< yDimension; j++){
				for(int i = 0; i< xDimension; i++){
					int item = scan.nextInt();
					if(item==16){
						character = new Character(i*BLOCK_SIZE,j*BLOCK_SIZE);
						map[i][j] = new BackgroundTile(BLOCK_SIZE,BLOCK_SIZE,i*BLOCK_SIZE,j*BLOCK_SIZE,TILE_DEPTH,new ImageWrapper(-1, BLOCK_SIZE, BLOCK_SIZE,worldSprites));
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
	

}
