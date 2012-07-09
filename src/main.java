
public class main {
	private static int fps = 30;

	public static void main(String[] args) {
		Map map = new Map("testLevel.txt");
		
		Canvas canvas = new Canvas(map);
		
		GameTimer t = new GameTimer(1000 / fps, map, canvas );
		
	}

}
