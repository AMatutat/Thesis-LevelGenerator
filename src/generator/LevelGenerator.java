package generator;

import java.util.ArrayList;

/**
 * Erstellt mithilfe eines Genetischen Algorithmus ein char[][] kodiertes level
 * 
 * @author André Matutat
 *
 */
public class LevelGenerator {
	private ArrayList<char[][]> levels = new ArrayList<char[][]>();

	/**
	 * Generiert ein char[][] kodiertes Level
	 * 
	 * @param xSize Breite des Levels
	 * @param ySize Höhe des Levels
	 * @return kodiertes Level
	 */
	public char[][] generateLevel(int xSize, int ySize) throws IllegalArgumentException {
		if (xSize <= 3 || ySize <= 3)
			throw new IllegalArgumentException("Size must be at least 4x4");
		char[][] level = this.generateRandomLevel(xSize, ySize);
		printLevel(level,xSize,ySize);
		mutate(level,xSize,ySize);
		printLevel(level,xSize,ySize);
		placeStartAndEnd(level, xSize, ySize);
		printLevel(level,xSize,ySize);
		return level;
	}

	private void printLevel(char[][] lvl, int xSize, int ySize) {
		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) 
				System.out.print(lvl[x][y]);			
			System.out.println("");
		}
		System.out.println("");
	}
	
	/**
	 * Erstellt ein zufälliges Level in char[][] Kodierung
	 * 
	 * @param xSize Breite des Levels
	 * @param ySize Höhe des Levels
	 * @return generiertes Level
	 */
	private char[][] generateRandomLevel(int xSize, int ySize) {
		char[][] level = new char[xSize][ySize];

		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {

				if (x == 0 || y == 0 || y == ySize - 1 || x == xSize - 1)
					level[x][y] = Constants.WALLREF;

				else if ((int) (Math.random() * 100 + 1) <= Constants.FLOORCHANCE)
					level[x][y] = Constants.FLOORREF;
				else
					level[x][y] = Constants.WALLREF;

			}
		}

		return level;
	}

	/**
	 * Berechnet die Fitness eines Levels in char[][] Kodierung
	 * 
	 * @param level desses Fitness berechnet werden soll
	 * @param xSize Breite des Levels
	 * @param ySize Höhe des Levels
	 * @return Fitness des Levels guteFitness>schlechteFitness
	 */
	private int getFitness(char[][] level, int xSize, int ySize) {
		return 0;
	}

	/**
	 * Kombiniert zwei Level in char[][] Kodierung
	 * 
	 * @param lvl1  Erstes Level
	 * @param lvl2  Zweites Level
	 * @param xSize Breite der Level
	 * @param ySize Höhe des Levels
	 * @return kombiniertes Level in char[][] Kodierung
	 */
	private char[][] combine(char[][] lvl1, char[][] lvl2, int xSize, int ySize) {
		char[][] newLevel = new char[xSize][ySize];
		return newLevel;
	}

	/**
	 * Verändert ein Level in char[][] Kodierung
	 * 
	 * @param lvl   Level welches mutiert werden soll
	 * @param xSize Breite des Levels
	 * @param ySize Höhe des Levels
	 */
	private void mutate(char[][] lvl, final int xSize, final int ySize) {

		for (int i = (int) (xSize * ySize * Constants.MUTATEFACTOR); i > 0; i--) {			
			int x = (int) (Math.random() * xSize);
			int y = (int) (Math.random() * ySize);
			if (x == 0 || y == 0 || y == ySize - 1 || x == xSize - 1)
				i++;
			else if (lvl[x][y] == Constants.WALLREF)
				lvl[x][y] = Constants.FLOORREF;
			else
				lvl[x][y] = Constants.WALLREF;
		}
	}

	/**
	 * Platziert Chars für Eingang und Ausgang des Levels auf ein Zufällgen Floor
	 * 
	 * @param lvl   Level in dem Eingang und Ausgang platziert werden soll
	 * @param xSize Breite des Levels
	 * @param ySize Höhe des Levels
	 */
	private void placeStartAndEnd(char[][] lvl, int xSize, int ySize) {
		boolean change=false;
		do {
			//xSize druch 3 damit der Eingang im linken drittel spawnt
			int x = (int) (Math.random() * xSize/3);
			int y = (int) (Math.random() * ySize);
			if (lvl[x][y]==Constants.FLOORREF) {
				lvl[x][y]=Constants.STARTREF;
				change=true;
			}
		}while(!change);
		change=false;
		do {
			//Exit soll im rechten drittel Spawnen
			int x = (int) (Math.random() * xSize/3)+2*(int)(xSize/3);
			int y = (int) (Math.random() * ySize);
			if (lvl[x][y]==Constants.FLOORREF) {
				lvl[x][y]=Constants.EXITREF;
				change=true;
			}
		}while(!change);

	}

	public static void main(String[] args) {
		LevelGenerator lg = new LevelGenerator();
		lg.generateLevel(10, 10);
	}

}