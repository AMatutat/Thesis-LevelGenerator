package myGame;

public class Test extends Surface{

	@Override
	public String getTexture() {
		return ".\\res\\images\\test.jpg";
	}

	@Override
	public boolean setMonsterOnSurface(Monster monster) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setItemOnSurface(Item item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFree() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

}
