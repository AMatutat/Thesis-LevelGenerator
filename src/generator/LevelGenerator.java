package generator;

import myGame.Level;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;

/**
 * Erstellt mithilfe eines Genetischen Algorithmuses ein kodiertes Level
 * 
 * @author Andr� Matutat
 *
 */
public class LevelGenerator {

	/**
	 * Zum loggen der Generation des besten levels
	 */
	public int generationLog = 0;

	/**
	 * Generiert ein kodiertes Level
	 * 
	 * @param xSize Breite des Levels
	 * @param ySize H�he des Levels
	 * @return kodiertes Level
	 */
	public CodedLevel generateLevel(int xSize, int ySize, int fitnessVersion, int parentSlectionVersion,
			int crossoverVersion, int mutationVersion) throws IllegalArgumentException {
		if (xSize < Constants.MINIMAL_XSIZE || ySize < Constants.MINIMAL_YSIZE)
			throw new IllegalArgumentException(
					"Size must be at least " + Constants.MINIMAL_XSIZE + "x" + Constants.MINIMAL_YSIZE);
		if (Constants.POPULATIONSIZE % 2 != 0)
			throw new IllegalArgumentException("Population must be even");

		this.generationLog = 0;
		CodedLevel bestLevel = null;

		// Startgeneration erzeugen
		CodedLevel[] startPopulation = new CodedLevel[Constants.POPULATIONSIZE];
		for (int i = 0; i < Constants.POPULATIONSIZE; i++)
			startPopulation[i] = (generateRandomLevel(xSize, ySize));

		// Durchlauf
		for (int generation = 0; generation < Constants.MAXIMAL_GENERATION; generation++) {

			// Start und Exit platzieren, Fitness pr�fen
			for (CodedLevel lvl : startPopulation) {
				placeStartAndEnd(lvl);

				float fitness;
				switch (fitnessVersion) {
				case 1:
					fitness = fitness1(lvl);
					break;
				default:
					fitness = fitness1(lvl);
				}

				lvl.setFitness(fitness);
				lvl.resetList();

				if ((bestLevel == null || bestLevel.getFitness() < fitness)
						&& isReachable(lvl, lvl.getExit()[0], lvl.getExit()[1]))
					bestLevel = lvl.copyLevel();

			}

			// Kombinieren
			CodedLevel[] newPopulation = new CodedLevel[Constants.POPULATIONSIZE];
			for (int i = 0; i < startPopulation.length; i += 2) {

				// Elternpaar Ausw�hlen
				CodedLevel parentA;
				CodedLevel parentB;

				switch (parentSlectionVersion) {
				case 1:
					parentA = selectParent1(startPopulation);
					break;
				default:
					parentA = selectParent1(startPopulation);
				}

				do {
					switch (parentSlectionVersion) {
					case 1:
						parentB = selectParent1(startPopulation);
						break;
					default:
						parentB = selectParent1(startPopulation);
					}
				} while (parentA == parentB);

				if (Math.random() <= Constants.CHANCE_FOR_CROSSOVER) {
					CodedLevel combined[];
					switch (crossoverVersion) {
					case 1:
						combined = crossover1(parentA, parentB);
						newPopulation[i] = combined[0];
						newPopulation[i + 1] = combined[1];
						break;
					case 2:
						combined = crossover2(parentA, parentB);
						newPopulation[i] = combined[0];
						newPopulation[i + 1] = combined[1];
						break;
					default:
						combined = crossover1(parentA, parentB);
						newPopulation[i] = combined[0];
						newPopulation[i + 1] = combined[1];
					}

				} else {
					newPopulation[i] = parentA;
					newPopulation[i + 1] = parentB;
				}
			}

			// Mutieren
			for (CodedLevel lvl : newPopulation) {
				switch (mutationVersion) {
				case 1:
					mutate1(lvl);
					break;
				case 2:
					mutate2(lvl);
					break;
				default:
					mutate1(lvl);
					break;
				}
			}

			// Neue Population ist die Startpopulation f�r die n�chste Generation
			startPopulation = newPopulation;

			for (CodedLevel lvl : newPopulation)
				if ((bestLevel == null || bestLevel.getFitness() < lvl.getFitness())
						&& isReachable(lvl, lvl.getExit()[0], lvl.getExit()[1])) {
					bestLevel = lvl.copyLevel();
					this.generationLog = generation + 1;
				}

		}

		if (bestLevel == null)
			return generateLevel(xSize, ySize, fitnessVersion, parentSlectionVersion, crossoverVersion,
					mutationVersion);
		removeUnreachableFloors(bestLevel);
		return bestLevel;
	}

	/**
	 * Erstellt ein zuf�lliges CodedLevel
	 * 
	 * @param xSize Breite des Levels
	 * @param ySize H�he des Levels
	 * @return generiertes Level
	 */
	private CodedLevel generateRandomLevel(final int xSize, final int ySize) {
		char[][] level = new char[xSize][ySize];
		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {

				if (x == 0 || y == 0 || y == ySize - 1 || x == xSize - 1)
					level[x][y] = Constants.REFERENCE_WALL;

				else if (Math.random() <= Constants.CHANCE_TO_BE_FLOOR)
					level[x][y] = Constants.REFERENCE_FLOOR;
				else
					level[x][y] = Constants.REFERENCE_WALL;

			}
		}

		return new CodedLevel(level, xSize, ySize);
	}

	/**
	 * Platziert Referenzen f�r Eingang und Ausgang des Levels auf ein zuf�llgen
	 * Floor
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
				if (y != 0 && y != lvl.getYSize() - 1 && x != 0 && x != lvl.getXSize() - 1) {
					lvl.changeField(x, y, Constants.REFERENCE_START);
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
				if (y != 0 && y != lvl.getYSize() - 1 && x != 0 && x != lvl.getXSize() - 1) {
					lvl.changeField(x, y, Constants.REFEERNCE_EXIT);
					change = true;
				}
			} while (!change);
		}
	}

	/**
	 * Berechnet die Fitness eines Levels
	 * 
	 * @param level dessen Fitness berechnet werden soll
	 * @return Fitness des Levels guteFitness>schlechteFitness
	 */
	private float fitness1(final CodedLevel level) {
		float fitness = 0f;
		for (int x = 1; x < level.getXSize() - 1; x++) {
			for (int y = 1; y < level.getYSize() - 1; y++) {
				if (level.getLevel()[x][y] == Constants.REFERENCE_WALL) {
					if (isConnected(level, x, y))
						fitness += Constants.WALL_IS_CONNECTED;
					else if (level.getCheckedWalls().size() > 1) {
						fitness += Constants.WALL_HAS_NEIGHBOR;
					}
					level.resetWallList();
				} else if (level.getLevel()[x][y] == Constants.REFEERNCE_EXIT) {
					if (isReachable(level, x, y))
						fitness += Constants.EXIT_IS_REACHABLE;
				} else if (isReachable(level, x, y))
					fitness += Constants.FLOOR_IS_REACHABLE;
			}
		}
		level.resetList();
		return fitness;
	}

	/**
	 * Pr�ft ob eine Wand mit der Aussenwand verbunden ist.
	 * 
	 * @param level
	 * @param x     X Index der Wand
	 * @param y     Y Index der Wand
	 * @return
	 */
	private boolean isConnected(CodedLevel level, int x, int y) {

		if (level.getLevel()[x][y] != Constants.REFERENCE_WALL)
			throw new IllegalArgumentException("Surface must be a wall");
		// Wenn Wand Ausenwand ist -> return true
		if (x == level.getXSize() - 1 || x == 0 || y == level.getYSize() - 1 || y == 0)
			return true;

		// Hinzuf�gen der Wall um loops zu verhindern.
		level.getCheckedWalls().add(x + "" + y);
		boolean connected = false;
		// Rekursiver aufurf mit allen Nachbarn
		if (level.getLevel()[x - 1][y] == Constants.REFERENCE_WALL
				&& !level.getCheckedWalls().contains((x - 1) + "" + y))
			if (isConnected(level, x - 1, y))
				connected = true;
		if (!connected && level.getLevel()[x + 1][y] == Constants.REFERENCE_WALL
				&& !level.getCheckedWalls().contains((x + 1) + "" + y))
			if (isConnected(level, x + 1, y))
				connected = true;
		if (!connected && level.getLevel()[x][y - 1] == Constants.REFERENCE_WALL
				&& !level.getCheckedWalls().contains(x + "" + (y - 1)))
			if (isConnected(level, x, y - 1))
				connected = true;
		if (!connected && level.getLevel()[x][y + 1] == Constants.REFERENCE_WALL
				&& !level.getCheckedWalls().contains(x + "" + (y + 1)))
			if (isConnected(level, x, y + 1))
				connected = true;

		return connected;
	}

	/**
	 * Pr�ft ob ein Floor Surface vom Start aus erreichbar ist
	 * 
	 * @param level
	 * @param x     X Index des Floors
	 * @param y     Y Index des Floors
	 * @return
	 */
	private boolean isReachable(CodedLevel level, int x, int y) {
		if (level.getLevel()[x][y] != Constants.REFERENCE_FLOOR && level.getLevel()[x][y] != Constants.REFEERNCE_EXIT
				&& level.getLevel()[x][y] != Constants.REFERENCE_START)
			throw new IllegalArgumentException("Surface must be a floor");

		if (level.getReachableFloors().size() <= 0)
			createReachableList(level, level.getStart()[0], level.getStart()[1]);

		return level.getReachableFloors().contains(x + "" + y);

	}

	private void createReachableList(CodedLevel level, int x, int y) {
		if (level.getLevel()[x][y] != Constants.REFERENCE_FLOOR && level.getLevel()[x][y] != Constants.REFEERNCE_EXIT
				&& level.getLevel()[x][y] != Constants.REFERENCE_START)
			throw new IllegalArgumentException("Surface must be a floor");

		level.getReachableFloors().add(x + "" + y);

		if ((level.getLevel()[x - 1][y] == Constants.REFERENCE_FLOOR
				|| level.getLevel()[x - 1][y] == Constants.REFEERNCE_EXIT
				|| level.getLevel()[x - 1][y] == Constants.REFERENCE_START)
				&& !level.getReachableFloors().contains((x - 1) + "" + y))
			createReachableList(level, x - 1, y);

		if ((level.getLevel()[x + 1][y] == Constants.REFERENCE_FLOOR
				|| level.getLevel()[x + 1][y] == Constants.REFEERNCE_EXIT
				|| level.getLevel()[x + 1][y] == Constants.REFERENCE_START)
				&& !level.getReachableFloors().contains((x + 1) + "" + y))
			createReachableList(level, x + 1, y);

		if ((level.getLevel()[x][y - 1] == Constants.REFERENCE_FLOOR
				|| level.getLevel()[x][y - 1] == Constants.REFEERNCE_EXIT
				|| level.getLevel()[x][y - 1] == Constants.REFERENCE_START)
				&& !level.getReachableFloors().contains(x + "" + (y - 1)))
			createReachableList(level, x, y - 1);

		if ((level.getLevel()[x][y + 1] == Constants.REFERENCE_FLOOR
				|| level.getLevel()[x][y + 1] == Constants.REFEERNCE_EXIT
				|| level.getLevel()[x][y + 1] == Constants.REFERENCE_START)
				&& !level.getReachableFloors().contains(x + "" + (y + 1)))
			createReachableList(level, x, y + 1);
	}

	/**
	 * W�hlt ein Elternteil aus Roulett Wheel Selection
	 * 
	 * @param population aus der gew�hlt werden soll
	 * @return Index des Elternteils
	 */
	private CodedLevel selectParent1(final CodedLevel[] population) {
		int fitSum = 0;
		for (CodedLevel lvl : population) {
			fitSum += lvl.getFitness();
		}
		int target = (int) (Math.random() * fitSum);
		int p = 0;

		for (CodedLevel lvl : population) {
			p += lvl.getFitness();
			if (p >= target)
				return lvl;
		}

		// non reachable code
		return null;
	}

	/**
	 * Kombiniert zwei Level
	 * 
	 * @param lvl1 Erstes Level
	 * @param lvl2 Zweites Level
	 * @return Kombiniertes Level
	 */
	private CodedLevel[] crossover1(final CodedLevel lvl1, final CodedLevel lvl2) {

		CodedLevel newLevelA = new CodedLevel(new char[lvl1.getXSize()][lvl1.getYSize()], lvl1.getXSize(),
				lvl1.getYSize());

		for (int x = 0; x < lvl1.getXSize(); x++) {
			for (int y = 0; y < lvl1.getYSize(); y++) {

				if (x >= lvl1.getXSize() / 2)
					newLevelA.changeField(x, y, lvl2.getLevel()[x][y]);
				else
					newLevelA.changeField(x, y, lvl1.getLevel()[x][y]);
			}
		}

		CodedLevel newLevelB = new CodedLevel(new char[lvl1.getXSize()][lvl1.getYSize()], lvl1.getXSize(),
				lvl1.getYSize());
		for (int x = 0; x < lvl1.getXSize(); x++) {
			for (int y = 0; y < lvl1.getYSize(); y++) {
				if (x >= lvl1.getXSize() / 2)
					newLevelB.changeField(x, y, lvl1.getLevel()[x][y]);
				else
					newLevelB.changeField(x, y, lvl2.getLevel()[x][y]);
			}
		}
		CodedLevel c[] = { newLevelA, newLevelB };

		return c;
	}

	private CodedLevel[] crossover2(final CodedLevel lvl1, final CodedLevel lvl2) {
		int cut1 = ((int) Math.random() * lvl1.getYSize());
		int cut2 = ((int) Math.random() * lvl1.getYSize());

		if (cut1 > cut2) {
			int temp = cut1;
			cut1 = cut2;
			cut2 = temp;
		}

		CodedLevel newLevelA = new CodedLevel(new char[lvl1.getXSize()][lvl1.getYSize()], lvl1.getXSize(),
				lvl1.getYSize());

		for (int y = 0; y < lvl1.getYSize(); y++) {
			for (int x = 0; x < lvl1.getXSize(); x++) {
				if (y >= cut1 && y <= cut2)
					newLevelA.changeField(x, y, lvl2.getLevel()[x][y]);
				else
					newLevelA.changeField(x, y, lvl1.getLevel()[x][y]);
			}
		}

		CodedLevel newLevelB = new CodedLevel(new char[lvl1.getXSize()][lvl1.getYSize()], lvl1.getXSize(),
				lvl1.getYSize());
		for (int y = 0; y < lvl1.getYSize(); y++) {
			for (int x = 0; x < lvl1.getXSize(); x++) {
				if (y >= cut1 && y <= cut2)
					newLevelB.changeField(x, y, lvl1.getLevel()[x][y]);
				else
					newLevelB.changeField(x, y, lvl2.getLevel()[x][y]);
			}
		}

		CodedLevel c[] = { newLevelA, newLevelB };
		return c;
	}

	/**
	 * Mutiert, mit einer gewissen Warscheinlichkeit, jedes Feld des Levels
	 * 
	 * @param lvl Level welches mutiert werden soll
	 * 
	 */
	private void mutate1(CodedLevel lvl) {
		for (int y = 1; y < lvl.getYSize() - 1; y++) {
			for (int x = 1; x < lvl.getXSize() - 1; x++) {
				if (Math.random() <= Constants.CHANCE_FOR_MUTATION) {
					if ((lvl.getLevel())[x][y] == Constants.REFERENCE_WALL)
						lvl.changeField(x, y, Constants.REFERENCE_FLOOR);
					else
						lvl.changeField(x, y, Constants.REFERENCE_WALL);
				}
			}
		}

	}

	private void mutate2(CodedLevel lvl) {
		for (int y = 2; y < lvl.getYSize() - 2; y++) {
			if (Math.random() < Constants.CHANCE_FOR_MUTATION) {
				for (int x = 2; x < lvl.getXSize() - 2; x++) {
					if (lvl.getLevel()[x][y] == Constants.REFERENCE_WALL) {
						lvl.resetWallList();
						int v = x;
						boolean first = true;

						while (!isConnected(lvl, v, y)) {

							// Nach rechts schieben
							if (v >= lvl.getXSize() / 2) {
								// Vertauschen

								char c = lvl.getLevel()[v + 1][y];
								lvl.changeField(v + 1, y, Constants.REFERENCE_WALL);
								lvl.changeField(v, y, c);

								v++;

								// Wenn ein Surface nach rechts geschoben wird, muss die alte Position erneut
								// überprüft werden
								// da evtl. dort wieder eine Wand steht.
								if (first) {
									first = false;
									--x;
								}
							}
							// Nach links schieben
							else {
								// vertauschen
								char c = lvl.getLevel()[v - 1][y];
								lvl.changeField(v - 1, y, Constants.REFERENCE_WALL);
								lvl.changeField(v, y, c);
								// Beim nächsten Run selbes Surface an neuer Position überprüfen
								v--;

							}

							lvl.resetWallList();

						}
					}
				}

			}
		}
	}

	/**
	 * Bubblesort nach Fitness
	 * 
	 * @param population
	 */
	private void sortByFitness(CodedLevel[] population) {
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

	private void removeUnreachableFloors(CodedLevel lvl) {
		lvl.resetList();
		for (int x = 1; x < lvl.getXSize() - 1; x++) {
			for (int y = 1; y < lvl.getYSize() - 1; y++) {
				if (lvl.getLevel()[x][y] == Constants.REFERENCE_FLOOR) {
					if (!isReachable(lvl, x, y))
						lvl.changeField(x, y, Constants.REFERENCE_WALL);

				}
			}
		}
		lvl.resetList();
	}

	public static void main(String[] args) throws InterruptedException {

		boolean logResults = true;
		boolean generateTexture = false;
		String startmsg = "Test";
		String imgName = "level";
		int xSize = 30;
		int ySize = 30;
		int fitnessVersion = 1;
		int parentSelectionVersion = 1;
		int crossoverVersion = 1;
		int mutationVersion = 1;

		int levelsPerSetting = 3;
		int differentSettings = 3;

		int generationOfBestLevelsSum = 0;
		float fitnessOfBestLevelsSum = 0f;

		LevelGenerator lg = new LevelGenerator();
		LevelParser pa = new LevelParser();

		String tempLogFile = "temp.xls";
		String logFile = "ChangedPMutLOW_GB" + "_+M" + mutationVersion + "_C" + crossoverVersion + "_F" + fitnessVersion
				+ ".xls";
		String sheetName = "_";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_HHmmss");
		Workbook workbook;
		WritableWorkbook tempWorkbook = null;
		WritableSheet wsheet = null;

		try {

			if (logResults) {
				try {
					workbook = Workbook.getWorkbook(new File("./results/logs/" + logFile));
					tempWorkbook = Workbook.createWorkbook(new File(tempLogFile), workbook);
					workbook.close();
				} catch (FileNotFoundException e) {
					tempWorkbook = Workbook.createWorkbook(new File(tempLogFile));
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				} finally {
					LocalDateTime now = LocalDateTime.now();
					wsheet = tempWorkbook.createSheet(sheetName + dtf.format(now), 0);
					wsheet.addCell(new Label(1, 0, "XSize"));
					wsheet.addCell(new Label(2, 0, "YSize"));
					wsheet.addCell(new Label(3, 0, "Population"));
					wsheet.addCell(new Label(4, 0, "Value Connected"));
					wsheet.addCell(new Label(5, 0, "Value Reachable"));
					wsheet.addCell(new Label(6, 0, "Exit is Reachable"));
					wsheet.addCell(new Label(7, 0, "Chance to be Floor"));
					wsheet.addCell(new Label(8, 0, "Max Generationen"));
					wsheet.addCell(new Label(9, 0, "Crossoverchance"));
					wsheet.addCell(new Label(10, 0, "Mutationschance"));
					wsheet.addCell(new Label(11, 0, "d Generation"));
					wsheet.addCell(new Label(12, 0, "d Fitness"));
				}
			}

			for (int j = 0; j < differentSettings; j++) {
				System.out.println(startmsg);
				generationOfBestLevelsSum = 0;
				fitnessOfBestLevelsSum = 0f;

				for (int i = 0; i < levelsPerSetting; i++) {
					CodedLevel lvlc = lg.generateLevel(xSize, ySize, fitnessVersion, parentSelectionVersion,
							crossoverVersion, mutationVersion);
					System.out.println(i + 1 + " :lvl generated");
					fitnessOfBestLevelsSum += lvlc.getFitness();
					generationOfBestLevelsSum += lg.generationLog;
					if (generateTexture)
						pa.generateTextureMap(pa.parseLevel(lvlc), "./results/img", (imgName + j + i));
				}

				if (logResults) {
					wsheet.addCell(new Number(1, (1 + j), xSize));
					wsheet.addCell(new Number(2, (1 + j), ySize));
					wsheet.addCell(new Number(3, (1 + j), Constants.POPULATIONSIZE));
					wsheet.addCell(new Number(4, (1 + j), Constants.WALL_IS_CONNECTED));
					wsheet.addCell(new Number(5, (1 + j), Constants.FLOOR_IS_REACHABLE));
					wsheet.addCell(new Number(6, (1 + j), Constants.EXIT_IS_REACHABLE));
					wsheet.addCell(new Number(7, (1 + j), Constants.CHANCE_TO_BE_FLOOR));
					wsheet.addCell(new Number(8, (1 + j), Constants.MAXIMAL_GENERATION));
					wsheet.addCell(new Number(9, (1 + j), Constants.CHANCE_FOR_CROSSOVER));
					wsheet.addCell(new Number(10, (1 + j), Constants.CHANCE_FOR_MUTATION));
					wsheet.addCell(new Number(11, (1 + j), (generationOfBestLevelsSum / levelsPerSetting)));
					wsheet.addCell(new Number(12, (1 + j), ((fitnessOfBestLevelsSum / levelsPerSetting))));

				}

				// Parameter �ndern
			}

			if (logResults) {
				tempWorkbook.write();
				tempWorkbook.close();
				workbook = Workbook.getWorkbook(new File(tempLogFile));
				tempWorkbook = Workbook.createWorkbook(new File("./results/logs/" + logFile), workbook);
				tempWorkbook.write();
				tempWorkbook.close();
				workbook.close();
				File fi = new File(tempLogFile);
				fi.delete();
				System.out.println("fineshed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}