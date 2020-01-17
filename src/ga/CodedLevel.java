package ga;

import java.util.ArrayList;

/**
 * Symbolisiert ein Level f�r die dauer des Erstellunsprozesses
 * 
 * @author Andr� Matutat
 *
 */
public class CodedLevel {

	private char[][] level;
	private float fitness;
	private final int xSize;
	private final int ySize;
	private Point exit = null;
	private Point start = null;

	private ArrayList<String> checkedWalls = new ArrayList<String>();
	private ArrayList<String> reachableFloors = new ArrayList<String>();

	/**
	 * @param level Kodiertes Level
	 * @param xSize Breite des Levels
	 * @param ySize H�he des Levels
	 */
	public CodedLevel(final char[][] level, int xSize, int ySize) {
		this.level = level;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public void setFitness(final float fitness) {
		this.fitness = fitness;
	}

	public CodedLevel copyLevel() {
		CodedLevel r = new CodedLevel(new char[this.xSize][this.ySize], this.xSize, this.ySize);
		r.setFitness(this.fitness);

		for (int x = 0; x < this.xSize; x++)
			for (int y = 0; y < this.ySize; y++)
				r.changeField(x, y, this.level[x][y]);
		return r;
	}

	/**
	 * Reseted Hilfslisten
	 */
	public void resetList() {
		this.checkedWalls.clear();
		this.reachableFloors.clear();

	}

	public void resetWallList() {
		this.checkedWalls = new ArrayList<String>();
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
		if (this.start != null)
			return true;
		return false;
	}

	public boolean hasExit() {
		if (this.exit != null)
			return true;
		return false;
	}

	public int getXSize() {
		return this.xSize;
	}

	public int getYSize() {
		return this.ySize;
	}

	public Point getExit() {
		if (hasExit())
			return this.exit;
		return null;
	}

	public Point getStart() {
		if (hasStart())
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
			this.exit = null;

		else if (this.level[x][y] == Constants.REFERENCE_START)
			this.start = null;

		if (s == Constants.REFEERNCE_EXIT) {
			if (this.hasExit())
				s = Constants.REFERENCE_FLOOR;
			else {
				this.exit=new Point(x,y);
			}
		} else if (s == Constants.REFERENCE_START) {
			if (this.hasStart())
				s = Constants.REFERENCE_FLOOR;
			else {
				this.start=new Point(x,y);
			}
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
