package myGame;

import interfaces.IItem;
import interfaces.IMonster;

public class Wall extends Surface{

	private String texture ="./res/images/wall.jpg";
	@Override
	public String getTexture() {
	return this.texture;
	}

	@Override
	public boolean setMonsterOnSurface(IMonster monster) {		
		return false;
	}

	@Override
	public boolean setItemOnSurface(IItem item) {		
		return false;
	}

}
