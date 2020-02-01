package ga;

import constants.Parameter;
import constants.Reference;
import parser.LevelParser;

public class LevelGenerator {

	public CodedLevel generateLevel(final int xSize, final int ySize, final int generations)
			throws IllegalArgumentException {
		if (xSize < Parameter.MINIMAL_XSIZE || ySize < Parameter.MINIMAL_YSIZE)
			throw new IllegalArgumentException(
					"Size must be at least " + Parameter.MINIMAL_XSIZE + "x" + Parameter.MINIMAL_YSIZE);

		CodedLevel bestLevel = null;

		// Startgeneration erzeugen
		CodedLevel[] startPopulation = new CodedLevel[Parameter.POPULATIONSIZE];
		for (int i = 0; i < Parameter.POPULATIONSIZE; i++)
			startPopulation[i] = (generateRandomLevel(xSize, ySize));
		// Start und Exit platzieren, Fitness pruefen
		for (CodedLevel lvl : startPopulation) {
			placeStartAndEnd(lvl);

			// Bei bedarf Fitnessversion austauschen
			float fitness = fitness2(lvl);
			lvl.setFitness(fitness);
			lvl.resetList();

			if ((bestLevel == null || bestLevel.getFitness() < fitness)
					&& isReachable(lvl, lvl.getExit().x, lvl.getExit().y))
				bestLevel = lvl.copyLevel();

		}

		// Durchlauf
		for (int generation = 0; generation < generations; generation++) {

			// Kombinieren
			CodedLevel[] newPopulation = new CodedLevel[Parameter.POPULATIONSIZE];
			for (int i = 0; i < startPopulation.length; i += 2) {

				// Elternpaar Auswaehlen
				CodedLevel parentA = roulettWheelSelection(startPopulation);
				;
				CodedLevel parentB;
				do
					parentB = roulettWheelSelection(startPopulation);
				while (parentA == parentB);

				if (Math.random() <= Parameter.CHANCE_FOR_CROSSOVER) {
					CodedLevel combined[];
					// Bei Bedarf Rekombinationsfunktion austauschen
					combined = multipointCrossover(parentA, parentB);
					newPopulation[i] = combined[0];
					newPopulation[i + 1] = combined[1];

				} else {
					newPopulation[i] = parentA;
					newPopulation[i + 1] = parentB;
				}
			}

			// Mutieren
			for (CodedLevel lvl : newPopulation)
				// Bei Bedarf Mutationsfunktion austauschen
				fixRowMutation(lvl);

			// Neue Population ist die Startpopulation fuer die naehste Generation
			startPopulation = newPopulation;
			// neue Population bewerten
			for (CodedLevel lvl : startPopulation) {
				// Bei bedarf Fitnessversion austauschen
				float fitness = fitness2(lvl);
				lvl.setFitness(fitness);
				lvl.resetList();

				if ((bestLevel.getFitness() < fitness) && isReachable(lvl, lvl.getExit().x, lvl.getExit().y))
					bestLevel = lvl.copyLevel();

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
					level[x][y] = Reference.REFERENCE_WALL;

				else if (Math.random() <= Parameter.CHANCE_TO_BE_FLOOR)
					level[x][y] = Reference.REFERENCE_FLOOR;
				else
					level[x][y] = Reference.REFERENCE_WALL;

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
					lvl.changeField(x, y, Reference.REFERENCE_START);
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
					lvl.changeField(x, y, Reference.REFEERNCE_EXIT);
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
				if (level.getLevel()[x][y] == Reference.REFERENCE_WALL) {
					if (isConnected(level, x, y))
						fitness += Parameter.WALL_IS_CONNECTED;
					else if (level.getCheckedWalls().size() > 1) {
						fitness += Parameter.WALL_HAS_NEIGHBOR;
					}
					level.resetWallList();
				} else if (level.getLevel()[x][y] == Reference.REFEERNCE_EXIT) {
					if (isReachable(level, x, y))
						fitness += Parameter.EXIT_IS_REACHABLE;

				} else if (level.getLevel()[x][y] == Reference.REFERENCE_FLOOR && isReachable(level, x, y))
					fitness += Parameter.FLOOR_IS_REACHABLE;
			}

		}

		return fitness;
	}

	private float fitness2(final CodedLevel lvl) {
		float fitness = 0;
		lvl.resetList();
		for (int x = 1; x < lvl.getXSize() - 1; x++) {
			for (int y = 1; y < lvl.getYSize() - 1; y++) {
				if (lvl.getLevel()[x][y] == Reference.REFERENCE_WALL) {
					if (isConnected(lvl, x, y))
						fitness += Parameter.WALL_IS_CONNECTED;
					if (lvl.getLevel()[x - 1][y] != Reference.REFERENCE_WALL)
						fitness += Parameter.WALL_NEIGHBOR_IS_FLOOR;
					if (lvl.getLevel()[x + 1][y] != Reference.REFERENCE_WALL)
						fitness += Parameter.WALL_NEIGHBOR_IS_FLOOR;
					if (lvl.getLevel()[x][y - 1] != Reference.REFERENCE_WALL)
						fitness += Parameter.WALL_NEIGHBOR_IS_FLOOR;
					if (lvl.getLevel()[x][y + 1] != Reference.REFERENCE_WALL)
						fitness += Parameter.WALL_NEIGHBOR_IS_FLOOR;
				} else if (lvl.getLevel()[x][y] == Reference.REFEERNCE_EXIT) {
					if (isReachable(lvl, x, y))
						fitness += Parameter.EXIT_IS_REACHABLE;
				} else if (lvl.getLevel()[x][y] == Reference.REFERENCE_FLOOR && isReachable(lvl, x, y))
					fitness += Parameter.FLOOR_IS_REACHABLE;

			}

		}
		if (fitness <= 0)
			fitness = 1;
		return fitness;
	}

	private boolean isConnected(final CodedLevel level, final int x, final int y) {

		if (level.getLevel()[x][y] != Reference.REFERENCE_WALL)
			throw new IllegalArgumentException("Surface must be a wall");
		// Wenn Wand Ausenwand ist -> return true
		if (x == level.getXSize() - 1 || x == 0 || y == level.getYSize() - 1 || y == 0)
			return true;

		// Hinzufuegen der Wall um loops zu verhindern.
		level.getCheckedWalls().add(x + "" + y);
		boolean connected = false;
		// Rekursiver aufurf mit allen Nachbarn
		if (level.getLevel()[x - 1][y] == Reference.REFERENCE_WALL
				&& !level.getCheckedWalls().contains((x - 1) + "" + y))
			if (isConnected(level, x - 1, y))
				connected = true;
		if (!connected && level.getLevel()[x + 1][y] == Reference.REFERENCE_WALL
				&& !level.getCheckedWalls().contains((x + 1) + "" + y))
			if (isConnected(level, x + 1, y))
				connected = true;
		if (!connected && level.getLevel()[x][y - 1] == Reference.REFERENCE_WALL
				&& !level.getCheckedWalls().contains(x + "" + (y - 1)))
			if (isConnected(level, x, y - 1))
				connected = true;
		if (!connected && level.getLevel()[x][y + 1] == Reference.REFERENCE_WALL
				&& !level.getCheckedWalls().contains(x + "" + (y + 1)))
			if (isConnected(level, x, y + 1))
				connected = true;

		return connected;
	}

	private boolean isReachable(final CodedLevel level, final int x, final int y) {
		if (level.getLevel()[x][y] != Reference.REFERENCE_FLOOR && level.getLevel()[x][y] != Reference.REFEERNCE_EXIT
				&& level.getLevel()[x][y] != Reference.REFERENCE_START)
			throw new IllegalArgumentException("Surface must be a floor. Is " + level.getLevel()[x][y]);

		if (level.getReachableFloors().size() <= 0)
			createReachableList(level, level.getStart().x, level.getStart().y);

		return level.getReachableFloors().contains(x + "_" + y);

	}

	private void createReachableList(final CodedLevel level, final int x, final int y) {
		if (level.getLevel()[x][y] != Reference.REFERENCE_FLOOR && level.getLevel()[x][y] != Reference.REFEERNCE_EXIT
				&& level.getLevel()[x][y] != Reference.REFERENCE_START)
			throw new IllegalArgumentException("Surface must be a floor Is " + level.getLevel()[x][y]);

		level.getReachableFloors().add(x + "_" + y);

		if ((level.getLevel()[x - 1][y] == Reference.REFERENCE_FLOOR
				|| level.getLevel()[x - 1][y] == Reference.REFEERNCE_EXIT
				|| level.getLevel()[x - 1][y] == Reference.REFERENCE_START)
				&& !level.getReachableFloors().contains((x - 1) + "_" + y))
			createReachableList(level, x - 1, y);

		if ((level.getLevel()[x + 1][y] == Reference.REFERENCE_FLOOR
				|| level.getLevel()[x + 1][y] == Reference.REFEERNCE_EXIT
				|| level.getLevel()[x + 1][y] == Reference.REFERENCE_START)
				&& !level.getReachableFloors().contains((x + 1) + "_" + y))
			createReachableList(level, x + 1, y);

		if ((level.getLevel()[x][y - 1] == Reference.REFERENCE_FLOOR
				|| level.getLevel()[x][y - 1] == Reference.REFEERNCE_EXIT
				|| level.getLevel()[x][y - 1] == Reference.REFERENCE_START)
				&& !level.getReachableFloors().contains(x + "_" + (y - 1)))
			createReachableList(level, x, y - 1);

		if ((level.getLevel()[x][y + 1] == Reference.REFERENCE_FLOOR
				|| level.getLevel()[x][y + 1] == Reference.REFEERNCE_EXIT
				|| level.getLevel()[x][y + 1] == Reference.REFERENCE_START)
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

		placeStartAndEnd(newLevelA);
		placeStartAndEnd(newLevelB);

		CodedLevel c[] = { newLevelA, newLevelB };
		return c;
	}

	private void bitFlipMutation(final CodedLevel lvl) {
		for (int y = 1; y < lvl.getYSize() - 1; y++) {
			for (int x = 1; x < lvl.getXSize() - 1; x++) {
				if (Math.random() <= Parameter.CHANCE_FOR_MUTATION) {
					if ((lvl.getLevel())[x][y] == Reference.REFERENCE_WALL)
						lvl.changeField(x, y, Reference.REFERENCE_FLOOR);
					else if ((lvl.getLevel())[x][y] == Reference.REFERENCE_FLOOR)
						lvl.changeField(x, y, Reference.REFERENCE_WALL);
				}
			}
		}
	}

	private void fixRowMutation(final CodedLevel lvl) {
		for (int y = 2; y < lvl.getYSize() - 2; y++) {
			if (Math.random() < Parameter.CHANCE_FOR_MUTATION) {
				for (int x = 2; x < lvl.getXSize() - 2; x++) {
					if (lvl.getLevel()[x][y] == Reference.REFERENCE_WALL) {
						lvl.resetWallList();
						int actX = x;
						boolean first = true;

						while (!isConnected(lvl, actX, y)) {

							// Nach rechts schieben
							if (actX >= lvl.getXSize() / 2) {
								// Vertauschen

								char c = lvl.getLevel()[actX + 1][y];
								lvl.changeField(actX + 1, y, Reference.REFERENCE_WALL);
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
								lvl.changeField(actX - 1, y, Reference.REFERENCE_WALL);
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
				if (Math.random() <= Parameter.CHANCE_FOR_MUTATION) {
					if (level.getLevel()[x + 1][y] == Reference.REFERENCE_WALL)
						lebendeNachbarn++;
					if (level.getLevel()[x - 1][y] == Reference.REFERENCE_WALL)
						lebendeNachbarn++;
					if (level.getLevel()[x][y + 1] == Reference.REFERENCE_WALL)
						lebendeNachbarn++;
					if (level.getLevel()[x][y - 1] == Reference.REFERENCE_WALL)
						lebendeNachbarn++;

					if (lebendeNachbarn == 3 && level.getLevel()[x][y] == Reference.REFERENCE_FLOOR)
						tempLevel.changeField(x, y, Reference.REFERENCE_WALL);

					else if ((lebendeNachbarn < 1 || lebendeNachbarn <= 3)
							&& level.getLevel()[x][y] == Reference.REFERENCE_WALL) {
						tempLevel.changeField(x, y, Reference.REFERENCE_FLOOR);
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
				if (lvl.getLevel()[x][y] == Reference.REFERENCE_FLOOR) {
					if (!isReachable(lvl, x, y))
						lvl.changeField(x, y, Reference.REFERENCE_WALL);
				}
			}
		}
	}

}