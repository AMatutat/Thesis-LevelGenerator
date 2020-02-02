package ga;

import java.util.ArrayList;

import constants.Reference;

/**
 * Symbolisiert ein Level für die dauer des Erstellunsprozesses
 * 
 * @author Andre Matutat
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
	 * @param ySize Höhe des Levels
	 */
	public CodedLevel(final char[][] level, int xSize, int ySize) {
		this.level = level;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	/**
	 * 
	 * @param fitness Fitnesswert
	 */
	public void setFitness(final float fitness) {
		this.fitness = fitness;
	}

	/**
	 * Erstellt eine Kopie des Level
	 * 
	 * @return Kopie des Level
	 */
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

	/**
	 * Reseted Wallliste
	 */
	public void resetWallList() {
		this.checkedWalls = new ArrayList<String>();
	}

	/**
	 * 
	 * @return Liste aller geprüften Wände
	 */
	public ArrayList<String> getCheckedWalls() {
		return this.checkedWalls;
	}

	/**
	 * 
	 * @return Liste aller erreichbaren Böden
	 */
	public ArrayList<String> getReachableFloors() {
		return this.reachableFloors;

	}

	/**
	 * 
	 * @return Fitnesswert des Levels
	 */
	public float getFitness() {
		return this.fitness;
	}
/**
 * 
 * @return Ob das Level einen Startpunkt besitzt
 */
	public boolean hasStart() {
		if (this.start != null)
			return true;
		return false;
	}
/**
 * 
 * @return Ob das Level einen Ausgang besitzt
 */
	public boolean hasExit() {
		if (this.exit != null)
			return true;
		return false;
	}
/**
 * 
 * @return Breite des Level
 */
	public int getXSize() {
		return this.xSize;
	}
/**
 * 
 * @return Höhe des Level
 */
	public int getYSize() {
		return this.ySize;
	}
/**
 * 
 * @return Ausgangs Position
 */
	public Point getExit() {
		if (hasExit())
			return this.exit;
		return null;
	}
/**
 * 
 * @return Start Position
 */
	public Point getStart() {
		if (hasStart())
			return this.start;
		return null;
	}
/**
 * 
 * @return Levelaufbau
 */
	public char[][] getLevel() {
		return this.level;
	}

	/**
	 * Tauscht ein Feld aus
	 * @param x X Index des zu wechselnden Elements
	 * @param y y Index des zu wechselnden Elements
	 * @param s neue Element
	 */
	public void changeField(int x, int y, char s) {
		if (this.level[x][y] == Reference.REFEERNCE_EXIT)
			this.exit = null;

		else if (this.level[x][y] == Reference.REFERENCE_START)
			this.start = null;

		if (s == Reference.REFEERNCE_EXIT) {
			if (this.hasExit())
				s = Reference.REFERENCE_FLOOR;
			else {
				this.exit = new Point(x, y);
			}
		} else if (s == Reference.REFERENCE_START) {
			if (this.hasStart())
				s = Reference.REFERENCE_FLOOR;
			else {
				this.start = new Point(x, y);
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
