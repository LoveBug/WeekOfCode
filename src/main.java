
public class main {

	public static void main(String[] args) {
		Map map = new Map("testLevel.txt");
		
		Canvas canvas = new Canvas(map);
		
		while(true){
			map.getCharacter().move(map.getMap());
			canvas.repaint();
		}
		
	}

}
