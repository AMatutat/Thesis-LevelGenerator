package myGame;

public class Door extends Surface{

	private String texture =".\\res\\images\\door.jpg";
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
