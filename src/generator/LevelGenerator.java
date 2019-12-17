package generator;

import java.util.ArrayList;

/**
 * Erstellt mithilfe eines Genetischen Algorithmus ein String[][] kodiertes level
 * @author Andre
 *
 */
public class LevelGenerator{
	private ArrayList <String[][]> levels = new ArrayList <String[][]>();
	
	/**
	 * Generiert ein String[][] kodiertes Level
	 * @param xSize Breite des Levels
	 * @param ySize Höhe des Levels
	 * @return kodiertes Level
	 */
	public String[][] generateLevel(int xSize, int ySize) {
		String[][] level = new String[xSize][ySize];
				
		return level;
	}
	
}