package generator;

import java.util.ArrayList;

/**
 * Erstellt mithilfe eines Genetischen Algorithmus ein String[][] kodiertes
 * level
 * 
 * @author André Matutat
 *
 */
public class LevelGenerator {
	private ArrayList<String[][]> levels = new ArrayList<String[][]>();

	/**
	 * Generiert ein String[][] kodiertes Level
	 * 
	 * @param xSize Breite des Levels
	 * @param ySize Höhe des Levels
	 * @return kodiertes Level
	 */
	public String[][] generateLevel(int xSize, int ySize) {
		String[][] level = this.generateRandomLevel(xSize, ySize);

		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {
				System.out.print(level[x][y]);
			}
			System.out.println("");
		}

		return level;
	}

	private String[][] generateRandomLevel(int xSize, int ySize) {
		String[][] level = new String[xSize][ySize];

		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {

				if (x == 0 || y == 0 || y == ySize-1 || x == xSize-1) {
					level[x][y] = "W";
				}
				else {
					if((int)(Math.random()*100+1)<=Constants.FLOORCHANCE)
						level[x][y]="F";
					else level[x][y]="W";
				}
			}
		}

		return level;
	}
	
	private int getFitness(String[][] level, int xSize, int ySize) {
		return 0;
	}
	
	private String[][] combine(String[][]lvl1, String[][] lvl2, int xSize, int ySize){
		String[][] newLevel = new String [xSize][ySize];
		return newLevel;
	}
	
	private String[][] mutate (String[][] lvl, int xSize, int ySize) {
		String[][] newLevel = new String [xSize][ySize];
		return newLevel;
	}
	
	
	public static void main (String[]args) {
		LevelGenerator lg = new LevelGenerator();
		lg.generateLevel(20, 20);
	}

}