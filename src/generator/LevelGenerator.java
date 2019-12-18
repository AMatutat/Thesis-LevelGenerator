package generator;

import java.util.ArrayList;

/**
 * Erstellt mithilfe eines Genetischen Algorithmus ein char[][] kodiertes
 * level
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
	public char[][] generateLevel(int xSize, int ySize) throws IllegalArgumentException{
		if (xSize<=3 || ySize<=3) throw new IllegalArgumentException("Size must be at least 4x4");
		char[][] level = this.generateRandomLevel(xSize, ySize);
		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {
				System.out.print(level[x][y]);
			}
			System.out.println("");
		}
		return level;
	}

	/**
	 * Erstellt ein zufÃ¤lliges Level in char[][] Kodierung
	 * @param xSize Breite des Levels
	 * @param ySize	Höhe des Levels
	 * @return generiertes Level
	 */
	private char[][] generateRandomLevel(int xSize, int ySize) {
		char[][] level = new char[xSize][ySize];

		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {

				if (x == 0 || y == 0 || y == ySize-1 || x == xSize-1) {
					level[x][y] = Constants.WALLREF;
				}
				else {
					if((int)(Math.random()*100+1)<=Constants.FLOORCHANCE)
						level[x][y]=Constants.FLOORREF;
					else level[x][y]=Constants.WALLREF;
				}
			}
		}

		return level;
	}
	
	/**
	 * Berechnet die Fitness eines Levels in char[][] Kodierung
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
	 * @param lvl1 Erstes Level
	 * @param lvl2 Zweites Level
	 * @param xSize Breite der Level
	 * @param ySize Höhe des Levels
	 * @return kombiniertes Level in char[][] Kodierung
	 */
	private char[][] combine(char[][] lvl1, char[][] lvl2, int xSize, int ySize){
		char[][] newLevel = new char[xSize][ySize];
		return newLevel;
	}
	/**
	 * Verändert ein Level in char[][] Kodierung
	 * @param lvl Level welches mutiert werden soll
	 * @param xSize Breite des Levels
	 * @param ySize Höhe des Levels
	 * @return Mutiertes Level in char[][] Kodierung
	 */
	private char[][] mutate (char[][] lvl, int xSize, int ySize) {
		char[][] newLevel = new char [xSize][ySize];
		return newLevel;
	}
	/**
	 * Platziert Chars für Eingang und Ausgang des Levels auf ein ZufÃ¤llgen Floor
	 * @param lvl Level in dem Eingang und Ausgang platziert werden soll
	 * @param xSize Breite des Levels 
	 * @param ySize Höhe des Levels
	 * @return
	 */
	private boolean placeStartAndEnd(char[][] lvl, int xSize, int ySize) {
		return false;
	}
	
	public static void main (String[]args) {
		LevelGenerator lg = new LevelGenerator();
		lg.generateLevel(20, 20);
	}

}