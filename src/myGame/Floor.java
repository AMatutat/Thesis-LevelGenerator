package myGame;



public class Floor extends Surface{
	
	private Item itemPlaced;
	private Monster monsterPlaced;
	private String texture ="./res/images/floor.jpg";

	
	public String getTexture() {
		return this.texture;
	
	}

	
	public boolean setMonsterOnSurface(Monster monster) {
		if (this.monsterPlaced!=null) return false;
		this.monsterPlaced=monster;
		return true;
	}

	public boolean setItemOnSurface(Item item) {
		if (this.itemPlaced!=null) return false;
		this.itemPlaced=item;
		return true;
	}

}
