package myGame;

import interfaces.ILevel;
import interfaces.ISurface;

public class Level implements ILevel {
	private int xSize;
	private int ySize;
	private ISurface[][] level;
	
	public Level(int xSize, int ySize, ISurface[][] level) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.level = level;
	}

	@Override
	public int getXSize() {

		return this.xSize;
	}

	@Override
	public int getYSize() {
		return this.ySize;
	}

	public ISurface[][] getLevel(){
		return this.level;
	}
}
