package myGame;

public class Key extends Surface {

	private String texture =".\\res\\images\\key.jpg";
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
