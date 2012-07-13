package items;

public class WeaponItem extends Item {
	public enum Type {
		SWORD(0), CROSSBOW(5), PISTOL(1), MACE(4);
		public final int index;

		private Type(int i) {
			this.index = i;
		}
	}

	private Type type;

	public WeaponItem(int x, int y, int width, int height, String Spritesheet,Type type) {
	
		super(x, y, width, height, Spritesheet, 1, type.index);
		System.out.println("TYPE "+type + " TYPEINDEX :"+ type.index);
		this.setMoveDistance(0);
		this.type = type;
	}

	public Bullet getBullet(int x, int y, int curX, int curY) {
		switch (this.type) {
		case CROSSBOW:
			return (new Bullet(x, y, 32, 32, "images/projectileArrow.png", 0, curX, curY));
			
		case SWORD:
			return (new Bullet(x, y, 32, 32, "images/projectileFireball.png", 0, curX, curY));
		
		case PISTOL:
			return (new Bullet(x, y, 32, 32, "images/projectileBullet.png", 0, curX, curY));
		case MACE:
			break;
		default:
			System.out.println("We hit default");
			
		}
		return (new Bullet(x, y, 32, 32, "images/projectileFlechette.png", 0,
				curX, curY));
	}
}
