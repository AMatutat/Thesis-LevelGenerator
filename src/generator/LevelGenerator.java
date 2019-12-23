package generator;

import java.util.ArrayList;

/**
 * Erstellt mithilfe eines Genetischen Algorithmus ein char[][] kodiertes level
 * 
 * @author André Matutat
 *
 */
public class LevelGenerator {

	/**
	 * Generiert ein char[][] kodiertes Level
	 * 
	 * @param xSize Breite des Levels
	 * @param ySize Höhe des Levels
	 * @return kodiertes Level
	 */
	public CodedLevel generateLevel(int xSize, int ySize) throws IllegalArgumentException {
		if (xSize < Constants.MINXSIZE || ySize < Constants.MINYSIZE)
			throw new IllegalArgumentException(
					"Size must be at least " + Constants.MINXSIZE + "x" + Constants.MINYSIZE);
		if (Constants.POPULATIONSIZE % 2 != 0)
			throw new IllegalArgumentException("Population must be even");

		// Startgeneration erzeugen
		CodedLevel[] startPopulation = new CodedLevel[Constants.POPULATIONSIZE];
		for (int i = 0; i < Constants.POPULATIONSIZE; i++)
			startPopulation[i] = (generateRandomLevel(xSize, ySize));


		// Durchlauf
		for (int generation = 0; generation < Constants.MAXGENERATION; generation++) {

			for (int i = 0; i < startPopulation.length; i++) {
				
				placeStartAndEnd(startPopulation[i]);
				int fitness = getFitness(startPopulation[i]);
				if (fitness == Constants.FITNESSSCHWELLWERT)
					return startPopulation[i];
				startPopulation[i].setFitness(fitness);
			}
		
			// Kombinieren
			CodedLevel[] newPopulation = new CodedLevel[Constants.POPULATIONSIZE];
			bubbleSort(startPopulation);
			for (int i = 0; i < startPopulation.length; i += 2) {
				// Elternpaar Auswählen
				CodedLevel parentA = startPopulation[selectParent(startPopulation)];
				CodedLevel parentB = startPopulation[selectParent(startPopulation)];

				if ((int) (Math.random() * 100 + 1) <= Constants.CROSSOVERCHANCE) {
					newPopulation[i] = crossover(parentA, parentB);
					newPopulation[i + 1] = crossover(parentB, parentA);
				} else {
					newPopulation[i] = parentA;
					newPopulation[i + 1] = parentB;
				}
			}

			// Mutieren
			for (int i = 0; i < Constants.POPULATIONSIZE; i++) {
				mutate(startPopulation[i]);
				startPopulation[i].printLevel();
			}

			// Neue Population ist die Startpopulation für die nächste Generation
			//startPopulation = newPopulation;
		}

		// Neustart wenn Schwellwert überschritten wurde
		//return generateLevel(xSize, ySize);
		return null;
	}

	private int selectParent(final CodedLevel[] population) {
		return 0;
	}

	private void bubbleSort(CodedLevel[] population) {
		CodedLevel temp;
		for (int i = 1; i < population.length; i++) {
			for (int j = 0; j < population.length - i; j++) {
				if (population[j].getFitness() > population[j + 1].getFitness()) {
					temp = population[j];
					population[j] = population[j + 1];
					population[j + 1] = temp;
				}
			}
		}
	}

	/**
	 * Erstellt ein zufälliges CodedLevel
	 * 
	 * @param xSize Breite des Levels
	 * @param ySize Höhe des Levels
	 * @return generiertes Level
	 */
	private CodedLevel generateRandomLevel(final int xSize, final int ySize) {
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

		return new CodedLevel(level, xSize, ySize);
	}

	/**
	 * Berechnet die Fitness eines Levels in char[][] Kodierung
	 * 
	 * @param level desses Fitness berechnet werden soll
	 * @return Fitness des Levels guteFitness>schlechteFitness
	 */
	private int getFitness(final CodedLevel level) {
		return 0;
	}

	/**
	 * Kombiniert zwei Level
	 * 
	 * @param lvl1 Erstes Level
	 * @param lvl2 Zweites Level
	 */
	private CodedLevel crossover(final CodedLevel lvl1, final CodedLevel lvl2) {
		char [][] newLevel = new char[lvl1.getXSize()][lvl1.getYSize()];
		int x;
		for (x=0; x<lvl1.getXSize()/2;x++) {
			for (int y=0;y<lvl1.getYSize();y++) {
				newLevel[x][y]=lvl1.getLevel()[x][y];
			}
		}
		for (x=x ;x<lvl1.getXSize();x++) {
			for (int y=0;y<lvl1.getYSize();y++) {
				newLevel[x][y]=lvl1.getLevel()[x][y];
			}
		}
		return new CodedLevel(newLevel,lvl1.getXSize(),lvl1.getYSize(),true,true);
	}

	/**
	 * Mutiert, mit einer gewissen Warscheinlichkeit, jedes Feld des Levels
	 * 
	 * @param lvl Level welches mutiert werden soll
	 * 
	 */
	private void mutate(CodedLevel lvl) {
		for (int y = 1; y < lvl.getYSize() - 1; y++) {
			for (int x = 1; x < lvl.getXSize() - 1; x++) {
				if ((int) (Math.random() * 100 + 1) <= Constants.MUTATIONCHANCE) {
					if ((lvl.getLevel())[x][y] == Constants.WALLREF)
						lvl.changeField(x, y, Constants.FLOORREF);
					else
						lvl.changeField(x, y, Constants.WALLREF);
				}
			}
		}

		lvl.setFitness(getFitness(lvl));
	}

	/**
	 * Platziert Chars für Eingang und Ausgang des Levels auf ein Zufällgen Floor
	 * 
	 * @param lvl Level in dem Eingang und Ausgang platziert werden soll
	 */
	private void placeStartAndEnd(CodedLevel lvl) {
		boolean change = false;

		if (!lvl.hasStart()) {
			do {
				// xSize druch 3 damit der Eingang im linken drittel spawnt
				int x = (int) (Math.random() * lvl.getXSize() / 3);
				int y = (int) (Math.random() * lvl.getYSize());
				if (y!=0 && y!=lvl.getYSize()-1 && x!=0 && x!=lvl.getXSize()-1) {
					lvl.changeField(x, y, Constants.STARTREF);
					change = true;
				}
			} while (!change);
			change = false;
		}
		if (!lvl.hasExit()) {
			do {
				// Exit soll im rechten drittel Spawnen
				int x = (int) (Math.random() * lvl.getXSize() / 3) + 2 * (int) (lvl.getXSize() / 3);
				int y = (int) (Math.random() * lvl.getYSize());
				if (y!=0 && y!=lvl.getYSize()-1 && x!=0 && x!=lvl.getXSize()-1) {
					lvl.changeField(x, y, Constants.EXITREF);
					change = true;
				}
			} while (!change);
		}
	}

	public static void main(String[] args) {
		LevelGenerator lg = new LevelGenerator();
		lg.generateLevel(5, 5);
	}

}