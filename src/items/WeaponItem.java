package items;

public class WeaponItem extends Item {
	public enum Type {
		SWORD(0), CROSSBOW(1), PISTOL(2), MACE(3);
		public final int index;

		private Type(int i) {
			this.index = i;
		}
	}

	public WeaponItem(int x, int y, int width, int height, String Spritesheet,
			Type type) {
		
		super(x, y, width, height, Spritesheet, type.index);
		this.setMoveDistance(0);
	}

}
