package generator;

import java.util.ArrayList;

/**
 * Symbolisiert ein Level für die dauer des Erstellunsprozesses
 * 
 * @author André Matutat
 *
 */
public class CodedLevel {

	private char[][] level;
	private float fitness;
	private final int xSize;
	private final int ySize;
	private boolean hasStart = false;
	private boolean hasExit = false;
	private int[] exit = new int[2];
	private int[] start = new int[2];

	private ArrayList<String> checkedWalls = new ArrayList<String>();
	private ArrayList<String> reachableFloors = new ArrayList<String>();

	/**
	 * @param level Kodiertes Level
	 * @param xSize Breite des Levels
	 * @param ySize Höhe des Levels
	 */
	public CodedLevel(final char[][] level, int xSize, int ySize) {
		this.level = level;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public void setFitness(final float fitness) {
		this.fitness = fitness;
	}

	/**
	 * Reseted Hilfslisten
	 */
	public void resetList() {
		this.checkedWalls = new ArrayList<String>();
		this.reachableFloors = new ArrayList<String>();

	}
	
	public void resetWallList() {
		this.checkedWalls= new ArrayList<String>();
	}

	public ArrayList<String> getCheckedWalls() {
		return this.checkedWalls;
	}

	public ArrayList<String> getReachableFloors() {
		return this.reachableFloors;

	}
	

	public float getFitness() {
		return this.fitness;
	}

	public boolean hasStart() {
		return this.hasStart;
	}

	public boolean hasExit() {
		return this.hasExit;
	}

	public int getXSize() {
		return this.xSize;
	}

	public int getYSize() {
		return this.ySize;
	}

	public int[] getExit() {
		if (hasExit)
			return this.exit;
		return null;
	}

	public int[] getStart() {
		if (hasStart)
			return this.start;
		return null;
	}

	public char[][] getLevel() {
		return this.level;
	}

	/**
	 * 
	 * @param x X Index des zu wechselnden Elements
	 * @param y y Index des zu wechselnden Elements
	 * @param s neue Element
	 */
	public void changeField(int x, int y, char s) {
		if (this.level[x][y] == Constants.REFEERNCE_EXIT)
			this.hasExit = false;

		else if (this.level[x][y] == Constants.REFERENCE_START)
			this.hasStart = false;

		if (s == Constants.REFEERNCE_EXIT) {
			this.hasExit = true;
			this.exit[0] = x;
			this.exit[1] = y;
		} else if (s == Constants.REFERENCE_START) {
			this.hasStart = true;
			this.start[0] = x;
			this.start[1] = y;
		}
		this.level[x][y] = s;
	}

	/**
	 * Gibt das Level aus
	 */
	public void printLevel() {
		for (int y = 0; y < this.ySize; y++) {
			for (int x = 0; x < this.xSize; x++)
				System.out.print(level[x][y]);
			System.out.println("");
		}
		System.out.println("");
	}

}
