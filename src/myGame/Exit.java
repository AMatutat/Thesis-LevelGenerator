package myGame;

public class Exit extends Surface {
	private String texture = "./res/images/exit.jpg";

	@Override
	public String getTexture() {
		return this.texture;
	}

	@Override
	public boolean setMonsterOnSurface(Monster monster) {
		return false;
	}

	@Override
	public boolean setItemOnSurface(Item item) {
		return false;
	}

}
