package ga;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import combiner.LevelParser;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;

public class LevelGenerator {

	public int generationLog = 0;

	public CodedLevel generateLevel(final int xSize, final int ySize, final int fitnessVersion,
			final int parentSlectionVersion, final int crossoverVersion, final int mutationVersion)
			throws IllegalArgumentException {
		if (xSize < Constants.MINIMAL_XSIZE || ySize < Constants.MINIMAL_YSIZE)
			throw new IllegalArgumentException(
					"Size must be at least " + Constants.MINIMAL_XSIZE + "x" + Constants.MINIMAL_YSIZE);

		this.generationLog = 0;
		CodedLevel bestLevel = null;

		// Startgeneration erzeugen
		CodedLevel[] startPopulation = new CodedLevel[Constants.POPULATIONSIZE];
		for (int i = 0; i < Constants.POPULATIONSIZE; i++)
			startPopulation[i] = (generateRandomLevel(xSize, ySize));

		// Durchlauf
		for (int generation = 0; generation < Constants.MAXIMAL_GENERATION; generation++) {

			// Start und Exit platzieren, Fitness pruefen
			for (CodedLevel lvl : startPopulation) {
				placeStartAndEnd(lvl);

				float fitness;
				switch (fitnessVersion) {
				case 1:
					fitness = fitness1(lvl);
					break;
				case 2:
					fitness = fitness2(lvl);
				default:
					fitness = fitness1(lvl);
				}

				lvl.setFitness(fitness);
				lvl.resetList();

				if ((bestLevel == null || bestLevel.getFitness() < fitness)
						&& isReachable(lvl, lvl.getExit().x, lvl.getExit().y)) {
					bestLevel = lvl.copyLevel();
					generationLog = generation + 1;
				}

			}

			// Kombinieren
			CodedLevel[] newPopulation = new CodedLevel[Constants.POPULATIONSIZE];
			for (int i = 0; i < startPopulation.length; i += 2) {

				// Elternpaar Auswaehlen
				CodedLevel parentA;
				CodedLevel parentB;

				switch (parentSlectionVersion) {
				case 1:
					parentA = roulettWheelSelection(startPopulation);
					break;
				default:
					parentA = roulettWheelSelection(startPopulation);
				}

				do {
					switch (parentSlectionVersion) {
					case 1:
						parentB = roulettWheelSelection(startPopulation);
						break;
					default:
						parentB = roulettWheelSelection(startPopulation);
					}
				} while (parentA == parentB);

				if (Math.random() <= Constants.CHANCE_FOR_CROSSOVER) {
					CodedLevel combined[];
					switch (crossoverVersion) {
					case 1:
						combined = onePointCrossover(parentA, parentB);
						newPopulation[i] = combined[0];
						newPopulation[i + 1] = combined[1];
						break;
					case 2:
						combined = multipointCrossover(parentA, parentB);
						newPopulation[i] = combined[0];
						newPopulation[i + 1] = combined[1];
						break;
					default:
						combined = onePointCrossover(parentA, parentB);
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
					bitFlipMutation(lvl);
					break;
				case 2:
					fixRowMutation(lvl);
					break;
				case 3:
					lvl = gameOfLifeMutation(lvl);
					break;
				default:
					bitFlipMutation(lvl);
					break;
				}
			}

			// Neue Population ist die Startpopulation fuer die naehste Generation
			startPopulation = newPopulation;

			for (CodedLevel lvl : newPopulation)
				if ((bestLevel == null || bestLevel.getFitness() < lvl.getFitness())
						&& isReachable(lvl, lvl.getExit().x, lvl.getExit().y)) {
					bestLevel = lvl.copyLevel();
					this.generationLog = generation + 1;
				}

		}
		removeUnreachableFloors(bestLevel);
		return bestLevel;
	}

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

	private void placeStartAndEnd(final CodedLevel lvl) {
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

	private float fitness1(final CodedLevel level) {
		float fitness = 0f;
		level.resetList();
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

				} else if (level.getLevel()[x][y] == Constants.REFERENCE_FLOOR && isReachable(level, x, y))
					fitness += Constants.FLOOR_IS_REACHABLE;
			}

		}

		return fitness;
	}

	private float fitness2(final CodedLevel lvl) {
		float fitness = 0;
		lvl.resetList();
		for (int x = 1; x < lvl.getXSize() - 1; x++) {
			for (int y = 1; y < lvl.getYSize() - 1; y++) {
				if (lvl.getLevel()[x][y] == Constants.REFERENCE_WALL) {
					if (isConnected(lvl, x, y))
						fitness += Constants.WALL_IS_CONNECTED;
					if (lvl.getLevel()[x - 1][y] != Constants.REFERENCE_WALL)
						fitness += Constants.WALL_NEIGHBOR_IS_FLOOR;
					if (lvl.getLevel()[x + 1][y] != Constants.REFERENCE_WALL)
						fitness += Constants.WALL_NEIGHBOR_IS_FLOOR;
					if (lvl.getLevel()[x][y - 1] != Constants.REFERENCE_WALL)
						fitness += Constants.WALL_NEIGHBOR_IS_FLOOR;
					if (lvl.getLevel()[x][y + 1] != Constants.REFERENCE_WALL)
						fitness += Constants.WALL_NEIGHBOR_IS_FLOOR;
				} else if (lvl.getLevel()[x][y] == Constants.REFEERNCE_EXIT) {
					if (isReachable(lvl, x, y))
						fitness += Constants.EXIT_IS_REACHABLE;
				} else if (lvl.getLevel()[x][y] == Constants.REFERENCE_FLOOR && isReachable(lvl, x, y))
					fitness += Constants.FLOOR_IS_REACHABLE;

			}

		}
		if (fitness <= 0)
			fitness = 1;
		return fitness;
	}

	private boolean isConnected(final CodedLevel level, final int x, final int y) {

		if (level.getLevel()[x][y] != Constants.REFERENCE_WALL)
			throw new IllegalArgumentException("Surface must be a wall");
		// Wenn Wand Ausenwand ist -> return true
		if (x == level.getXSize() - 1 || x == 0 || y == level.getYSize() - 1 || y == 0)
			return true;

		// Hinzufuegen der Wall um loops zu verhindern.
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

	private boolean isReachable(final CodedLevel level, final int x, final int y) {
		if (level.getLevel()[x][y] != Constants.REFERENCE_FLOOR && level.getLevel()[x][y] != Constants.REFEERNCE_EXIT
				&& level.getLevel()[x][y] != Constants.REFERENCE_START)
			throw new IllegalArgumentException("Surface must be a floor. Is " + level.getLevel()[x][y]);

		if (level.getReachableFloors().size() <= 0)
			createReachableList(level, level.getStart().x, level.getStart().y);

		return level.getReachableFloors().contains(x + "_" + y);

	}

	private void createReachableList(final CodedLevel level, final int x, final int y) {
		if (level.getLevel()[x][y] != Constants.REFERENCE_FLOOR && level.getLevel()[x][y] != Constants.REFEERNCE_EXIT
				&& level.getLevel()[x][y] != Constants.REFERENCE_START)
			throw new IllegalArgumentException("Surface must be a floor Is " + level.getLevel()[x][y]);

		level.getReachableFloors().add(x + "_" + y);

		if ((level.getLevel()[x - 1][y] == Constants.REFERENCE_FLOOR
				|| level.getLevel()[x - 1][y] == Constants.REFEERNCE_EXIT
				|| level.getLevel()[x - 1][y] == Constants.REFERENCE_START)
				&& !level.getReachableFloors().contains((x - 1) + "_" + y))
			createReachableList(level, x - 1, y);

		if ((level.getLevel()[x + 1][y] == Constants.REFERENCE_FLOOR
				|| level.getLevel()[x + 1][y] == Constants.REFEERNCE_EXIT
				|| level.getLevel()[x + 1][y] == Constants.REFERENCE_START)
				&& !level.getReachableFloors().contains((x + 1) + "_" + y))
			createReachableList(level, x + 1, y);

		if ((level.getLevel()[x][y - 1] == Constants.REFERENCE_FLOOR
				|| level.getLevel()[x][y - 1] == Constants.REFEERNCE_EXIT
				|| level.getLevel()[x][y - 1] == Constants.REFERENCE_START)
				&& !level.getReachableFloors().contains(x + "_" + (y - 1)))
			createReachableList(level, x, y - 1);

		if ((level.getLevel()[x][y + 1] == Constants.REFERENCE_FLOOR
				|| level.getLevel()[x][y + 1] == Constants.REFEERNCE_EXIT
				|| level.getLevel()[x][y + 1] == Constants.REFERENCE_START)
				&& !level.getReachableFloors().contains(x + "_" + (y + 1)))
			createReachableList(level, x, y + 1);
	}

	private CodedLevel roulettWheelSelection(final CodedLevel[] population) {
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

	private CodedLevel[] onePointCrossover(final CodedLevel lvl1, final CodedLevel lvl2) {

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

	private CodedLevel[] multipointCrossover(final CodedLevel lvl1, final CodedLevel lvl2) {
		int cut1 = ((int) (Math.random() * lvl1.getYSize()));
		int cut2 = ((int) (Math.random() * lvl1.getYSize()));

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

	private void bitFlipMutation(final CodedLevel lvl) {
		for (int y = 1; y < lvl.getYSize() - 1; y++) {
			for (int x = 1; x < lvl.getXSize() - 1; x++) {
				if (Math.random() <= Constants.CHANCE_FOR_MUTATION) {
					if ((lvl.getLevel())[x][y] == Constants.REFERENCE_WALL)
						lvl.changeField(x, y, Constants.REFERENCE_FLOOR);
					else if ((lvl.getLevel())[x][y] == Constants.REFERENCE_FLOOR)
						lvl.changeField(x, y, Constants.REFERENCE_WALL);
				}
			}
		}
	}

	private void fixRowMutation(final CodedLevel lvl) {
		for (int y = 2; y < lvl.getYSize() - 2; y++) {
			if (Math.random() < Constants.CHANCE_FOR_MUTATION) {
				for (int x = 2; x < lvl.getXSize() - 2; x++) {
					if (lvl.getLevel()[x][y] == Constants.REFERENCE_WALL) {
						lvl.resetWallList();
						int actX = x;
						boolean first = true;

						while (!isConnected(lvl, actX, y)) {

							// Nach rechts schieben
							if (actX >= lvl.getXSize() / 2) {
								// Vertauschen

								char c = lvl.getLevel()[actX + 1][y];
								lvl.changeField(actX + 1, y, Constants.REFERENCE_WALL);
								lvl.changeField(actX, y, c);

								actX++;

								// Wenn ein Surface nach rechts geschoben wird, muss die alte Position erneut
								// ueberprueft werden
								// da evtl. dort wieder eine Wand steht.
								if (first) {
									first = false;
									--x;
								}
							}
							// Nach links schieben
							else {
								// vertauschen
								char c = lvl.getLevel()[actX - 1][y];
								lvl.changeField(actX - 1, y, Constants.REFERENCE_WALL);
								lvl.changeField(actX, y, c);
								// Beim naechsten Run selbes Surface an neuer Position ueberpruefen
								actX--;

							}

						}
					}
				}

			}
		}
	}

	private CodedLevel gameOfLifeMutation(CodedLevel level) {
		CodedLevel tempLevel = level.copyLevel();
		for (int x = 1; x < tempLevel.getXSize() - 1; x++) {
			for (int y = 1; y < tempLevel.getYSize() - 1; y++) {
				int lebendeNachbarn = 0;
				if (Math.random() <= Constants.CHANCE_FOR_MUTATION) {
					if (level.getLevel()[x + 1][y] == Constants.REFERENCE_WALL)
						lebendeNachbarn++;
					if (level.getLevel()[x - 1][y] == Constants.REFERENCE_WALL)
						lebendeNachbarn++;
					if (level.getLevel()[x][y + 1] == Constants.REFERENCE_WALL)
						lebendeNachbarn++;
					if (level.getLevel()[x][y - 1] == Constants.REFERENCE_WALL)
						lebendeNachbarn++;
					if (lebendeNachbarn == 3 && level.getLevel()[x][y] == Constants.REFERENCE_FLOOR)
						tempLevel.changeField(x, y, Constants.REFERENCE_WALL);
					else if ((lebendeNachbarn < 1 || lebendeNachbarn <= 3)
							&& level.getLevel()[x][y] == Constants.REFERENCE_WALL) {
						tempLevel.changeField(x, y, Constants.REFERENCE_FLOOR);
					}

				}
			}

		}
		return tempLevel;
	}

	private void removeUnreachableFloors(final CodedLevel lvl) {
		lvl.resetList();
		for (int x = 0; x < lvl.getXSize(); x++) {
			for (int y = 0; y < lvl.getYSize(); y++) {
				if (lvl.getLevel()[x][y] == Constants.REFERENCE_FLOOR) {
					if (!isReachable(lvl, x, y))
						lvl.changeField(x, y, Constants.REFERENCE_WALL);
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {

		boolean logResults = true;
		boolean generateTexture = false;
		String startmsg = "AllSettings20x20";
		String imgName = "level";
		int xSize = 20;
		int ySize = 20;
		int fitnessVersion = 1;
		int parentSelectionVersion = 1;
		int crossoverVersion = 1;
		int mutationVersion = 1;

		int levelsPerSetting = 5;
		int differentSettings = 4;

		int generationOfBestLevelsSum = 0;
		float fitnessOfBestLevelsSum = 0f;

		LevelGenerator lg = new LevelGenerator();
		LevelParser pa = new LevelParser();

		String tempLogFile = "temp20x20M1t.xls";
		// String logFile = "DiffTests" + "_+M" + mutationVersion + "_C" +
		// crossoverVersion + "_F" + fitnessVersion
		// + ".xls";
		String logFile = "DifferentParametersM1.xls";
		String sheetName = "V1_";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_HHmmss");
		Workbook workbook;
		WritableWorkbook tempWorkbook = null;
		WritableSheet wsheet = null;
		int counter = 0;

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
					wsheet.addCell(new Label(9, 0, "Crossover Version"));
					wsheet.addCell(new Label(10, 0, "Crossoverchance"));
					wsheet.addCell(new Label(11, 0, "CMutation Version"));
					wsheet.addCell(new Label(12, 0, "Mutationschance"));
					wsheet.addCell(new Label(13, 0, "Fitness Version"));
					wsheet.addCell(new Label(14, 0, "d Generation"));
					wsheet.addCell(new Label(15, 0, "d Fitness"));
				}
			}

			for (int j = 0; j < differentSettings; j++) {
				System.out.println("Setting " + j);
				Constants.CHANCE_FOR_CROSSOVER = 0f;
				for (int k = 0; k < 5; k++) {
					Constants.CHANCE_FOR_MUTATION = 0.00f;
					System.out.println("Crossover " + Constants.CHANCE_FOR_CROSSOVER);
					for (int l = 0; l < 5; l++) {
						System.out.println("Mutation " + Constants.CHANCE_FOR_MUTATION);
						counter++;
						generationOfBestLevelsSum = 0;
						fitnessOfBestLevelsSum = 0f;

						for (int i = 0; i < levelsPerSetting; i++) {
							CodedLevel lvlc = lg.generateLevel(xSize, ySize, fitnessVersion, parentSelectionVersion,
									crossoverVersion, mutationVersion);
							// System.out.println(i + 1 + " :lvl generated");
							fitnessOfBestLevelsSum += lvlc.getFitness();
							generationOfBestLevelsSum += lg.generationLog;
							if (generateTexture)
								pa.generateTextureMap(pa.parseLevel(lvlc), "./results/img", (imgName + j + i));
						}

						if (logResults) {
							wsheet.addCell(new Number(1, counter, xSize));
							wsheet.addCell(new Number(2, counter, ySize));
							wsheet.addCell(new Number(3, counter, Constants.POPULATIONSIZE));
							wsheet.addCell(new Number(4, counter, Constants.WALL_IS_CONNECTED));
							wsheet.addCell(new Number(5, counter, Constants.FLOOR_IS_REACHABLE));
							wsheet.addCell(new Number(6, counter, Constants.EXIT_IS_REACHABLE));
							wsheet.addCell(new Number(7, counter, Constants.CHANCE_TO_BE_FLOOR));
							wsheet.addCell(new Number(8, counter, Constants.MAXIMAL_GENERATION));
							wsheet.addCell(new Number(9, counter, crossoverVersion));
							wsheet.addCell(new Number(10, counter, Constants.CHANCE_FOR_CROSSOVER));
							wsheet.addCell(new Number(11, counter, mutationVersion));
							wsheet.addCell(new Number(12, counter, Constants.CHANCE_FOR_MUTATION));
							wsheet.addCell(new Number(13, counter, fitnessVersion));
							wsheet.addCell(new Number(14, counter, (generationOfBestLevelsSum / levelsPerSetting)));
							wsheet.addCell(new Number(15, counter, ((fitnessOfBestLevelsSum / levelsPerSetting))));

						}

						Constants.CHANCE_FOR_MUTATION += 0.2f;

					}
					Constants.CHANCE_FOR_CROSSOVER += 0.2f;
				}
				// Parameter �ndern
				switch (j) {

				case 0:
					fitnessVersion = 1;
					crossoverVersion = 2;
					// mutationVersion = 2;
					break;

				case 1:
					fitnessVersion = 2;
					crossoverVersion = 1;
					// mutationVersion = 2;
					break;
				case 2:
					fitnessVersion = 2;
					crossoverVersion = 2;
					mutationVersion = 2;
					break;
				case 3:
					fitnessVersion = 1;
					crossoverVersion = 2;
					mutationVersion = 3;
					break;

				case 4:
					fitnessVersion = 2;
					crossoverVersion = 1;
					mutationVersion = 3;
					break;
				case 5:
					fitnessVersion = 2;
					crossoverVersion = 2;
					mutationVersion = 3;
					break;

				}

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