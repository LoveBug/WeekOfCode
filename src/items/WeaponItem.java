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
	private int fireRate; // firerate in terms of how many frames it takes
	private int previousFrame = 0;

	public WeaponItem(int x, int y, int width, int height, String Spritesheet,
			Type type) {

		super(x, y, width, height, Spritesheet, 1, type.index);

		switch (type) {
		case SWORD:
			fireRate = 60;
			break;
		case CROSSBOW:
			fireRate = 45;
			break;
		case MACE:
			fireRate = 90;
			break;
		case PISTOL:
			fireRate = 20;
			break;
		default:
			fireRate = 0;
		}
		this.setMoveDistance(0);
		this.type = type;
	}

	public Bullet getBullet(int x, int y, int curX, int curY, int frames) {
		if(frames < previousFrame)
			previousFrame = 0;
		if (frames > fireRate + previousFrame) {
			switch (this.type) {
			case CROSSBOW:
				previousFrame = frames;
				return (new Bullet(x, y, 32, 32, "images/projectileArrow.png",
						0, curX, curY));

			case SWORD:
				previousFrame = frames;
				return (new Bullet(x, y, 32, 32,
						"images/projectileFireball.png", 0, curX, curY));

			case PISTOL:
				previousFrame = frames;
				return (new Bullet(x, y, 32, 32, "images/projectileBullet.png",
						0, curX, curY));
			case MACE:
				previousFrame = frames;
				break;
			default:
				break;

			}
			previousFrame = frames;
			return (new Bullet(x, y, 32, 32, "images/projectileFlechette.png", 0,
					curX, curY));
		}
		return null;
	}

}
