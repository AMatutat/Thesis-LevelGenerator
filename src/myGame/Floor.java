package myGame;

import interfaces.IItem;
import interfaces.IMonster;

public class Floor extends Surface{
	
	private IItem itemPlaced;
	private IMonster monsterPlaced;
	private String texture ="./res/images/floor.jpg";

	@Override
	public String getTexture() {
		return this.texture;
	
	}

	@Override
	public boolean setMonsterOnSurface(IMonster monster) {
		if (this.monsterPlaced!=null) return false;
		this.monsterPlaced=monster;
		return true;
	}

	@Override
	public boolean setItemOnSurface(IItem item) {
		if (this.itemPlaced!=null) return false;
		this.itemPlaced=item;
		return true;
	}

}
