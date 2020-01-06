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
	 * Generiert ein kodiertes Level
	 * 
	 * @param xSize Breite des Levels
	 * @param ySize H�he des Levels
	 * @return kodiertes Level
	 */
	public int generationLog = 0;

	public CodedLevel generateLevel(int xSize, int ySize) throws IllegalArgumentException {
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
				float fitness = calculateFitness(lvl);
				lvl.setFitness(fitness);
				lvl.resetList();

			}

			// Kombinieren
			CodedLevel[] newPopulation = new CodedLevel[Constants.POPULATIONSIZE];
			for (int i = 0; i < startPopulation.length; i += 2) {

				// Elternpaar Ausw�hlen
				CodedLevel parentA = selectParent(startPopulation);
				CodedLevel parentB;
				do {
					parentB = selectParent(startPopulation);
				} while (parentA == parentB);
				if (Math.random() <= Constants.CHANCE_FOR_CROSSOVER) {
					newPopulation[i] = crossover(parentA, parentB);
					newPopulation[i + 1] = crossover(parentB, parentA);
				} else {
					newPopulation[i] = parentA;
					newPopulation[i + 1] = parentB;
				}
			}

			// Mutieren
			for (CodedLevel lvl : newPopulation) {
				mutate2(lvl);
			}

			// Neue Population ist die Startpopulation f�r die n�chste Generation
			startPopulation = newPopulation;
			if (bestLevel == null)
				bestLevel = startPopulation[0];
			for (CodedLevel lvl : startPopulation)
				if (bestLevel.getFitness() < lvl.getFitness() && isReachable(lvl, lvl.getExit()[0], lvl.getExit()[1])) {
					System.out.println("New best Level");
					bestLevel = lvl;
					this.generationLog=generation;
				}

		}

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
	private float calculateFitness(final CodedLevel level) {
		float fitness = 0f;
		for (int x = 1; x < level.getXSize() - 1; x++) {
			for (int y = 1; y < level.getYSize() - 1; y++) {
				if (level.getLevel()[x][y] == Constants.REFERENCE_WALL) {
					if (isConnected(level, x, y))
						fitness += Constants.WALL_IS_CONNECTED;
					else if (level.getCheckedWalls().size() > 1) {
						fitness += (Constants.WALL_IS_CONNECTED*Constants.WALL_HAS_NEIGHBOR);
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
	private CodedLevel selectParent(final CodedLevel[] population) {
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
	private CodedLevel crossover(final CodedLevel lvl1, final CodedLevel lvl2) {

		CodedLevel newLevel = new CodedLevel(new char[lvl1.getXSize()][lvl1.getYSize()], lvl1.getXSize(),
				lvl1.getYSize());
		int x;
		for (x = 0; x < lvl1.getXSize() / 2; x++) {
			for (int y = 0; y < lvl1.getYSize(); y++) {
				newLevel.changeField(x, y, lvl1.getLevel()[x][y]);
			}
		}
		for (x = x; x < lvl1.getXSize(); x++) {
			for (int y = 0; y < lvl1.getYSize(); y++) {
				newLevel.changeField(x, y, lvl1.getLevel()[x][y]);
			}
		}
		return newLevel;
	}

	/**
	 * Mutiert, mit einer gewissen Warscheinlichkeit, jedes Feld des Levels
	 * 
	 * @param lvl Level welches mutiert werden soll
	 * 
	 */
	private void mutate(CodedLevel lvl) {
		float pmut = 1 / ((lvl.getXSize() - 1) * (lvl.getYSize() - 1));
		for (int y = 1; y < lvl.getYSize() - 1; y++) {
			for (int x = 1; x < lvl.getXSize() - 1; x++) {
				if (Math.random() <= pmut) {
					if ((lvl.getLevel())[x][y] == Constants.REFERENCE_WALL)
						lvl.changeField(x, y, Constants.REFERENCE_FLOOR);
					else
						lvl.changeField(x, y, Constants.REFERENCE_WALL);
				}
			}
		}

	}
	
	private void mutate2(CodedLevel lvl) {
		float pmut=0.3f;
		for (int y=2; y<lvl.getYSize()-2;y++) {
			if(Math.random()<pmut) {
				for (int x=2;x<lvl.getXSize()-2;x++) {
					if (lvl.getLevel()[x][y]==Constants.REFERENCE_WALL) {
						lvl.resetWallList();
						int v=x;
						boolean first=true;
						
						while(!isConnected(lvl, v, y)) {
							//System.out.println(v);
							//lvl.printLevel();
							//Nach rechts schieben
							if(v>=lvl.getXSize()/2) {
								//Vertauschen
							
								char c=lvl.getLevel()[v+1][y];
								lvl.changeField(v+1, y, Constants.REFERENCE_WALL);
								lvl.changeField(v, y, c);
							
								v++;
								
								//Wenn ein Surface nach rechts geschoben wird, muss die alte Position erneut überprüft werden
								//da evtl. dort wieder eine Wand steht. 
								if (first) {
									first=false;
									--x;
								}
							}
							//Nach links schieben
							else {
								//vertauschen
								char c=lvl.getLevel()[v-1][y];
								lvl.changeField(v-1, y, Constants.REFERENCE_WALL);
								lvl.changeField(v, y, c);
								//Beim nächsten Run selbes Surface an neuer Position überprüfen
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

	}

	public static void main(String[] args) throws InterruptedException {
		LevelGenerator lg= new LevelGenerator();
		CodedLevel l=lg.generateLevel(20, 20);
				System.out.println(lg.generationLog);
		LevelParser p = new LevelParser();
		p.generateTextureMap(p.parseLevel(l), "./", "name");
		/*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_HHmmss");
		
		int x = 30;
		int y = 30;
		int gen = 0;
		float fit = 0f;
		int imax = 10;
		LevelGenerator lg = new LevelGenerator();

		String temp="temp.xls";
		String log="GenerationLog.xls";
		String sheetName="Generation_";
		
		Workbook workbook;
		WritableWorkbook wworkbook = null;
		try {
			
			
			for (int j=0;j<100;j++) {
				gen=0;
				fit=0f;
				LocalDateTime now = LocalDateTime.now();
			try {
				workbook = Workbook.getWorkbook(new File(log));
				wworkbook = Workbook.createWorkbook(new File(temp), workbook);
				workbook.close();
			} catch (FileNotFoundException e) {
				wworkbook = Workbook.createWorkbook(new File(temp));
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}

		
			
	
			for (int i = 0; i < imax; i++) {
				CodedLevel lvlc = lg.generateLevel(x, y);
				System.out.println(i+" :lvl generated");
				fit += lvlc.getFitness();
				gen += lg.generationLog;

				//LevelParser lp = new LevelParser();
				//Level lvl = lp.parseLevel(lvlc);
				//lp.generateTextureMap(lvl, ".\\res\\results", "result" + i);
			}
		

			WritableSheet wsheet = wworkbook.createSheet(sheetName+ dtf.format(now), 0);

			wsheet.addCell(new Label(0, 0, "Population"));
			wsheet.addCell(new Number(1, 0, Constants.POPULATIONSIZE));

			wsheet.addCell(new Label(0, 1, "XSize"));
			wsheet.addCell(new Number(1, 1, x));
			wsheet.addCell(new Label(0, 2, "YSize"));
			wsheet.addCell(new Number(1, 2, y));

			wsheet.addCell(new Label(0, 3, "Value Connected"));
			wsheet.addCell(new Number(1, 3, Constants.WALL_IS_CONNECTED));

			wsheet.addCell(new Label(0, 4, "Value Reachable"));
			wsheet.addCell(new Number(1, 4, Constants.FLOOR_IS_REACHABLE));

			wsheet.addCell(new Label(0, 5, "Exit is Reachable"));
			wsheet.addCell(new Number(1, 5, Constants.EXIT_IS_REACHABLE));

			wsheet.addCell(new Label(0, 6, "Max Generationen"));
			wsheet.addCell(new Number(1, 6, Constants.MAXIMAL_GENERATION));

			wsheet.addCell(new Label(0, 7, "Crossoverchance"));
			wsheet.addCell(new Number(1, 7, Constants.CHANCE_FOR_CROSSOVER));

			wsheet.addCell(new Label(0, 8, "d Generation"));
			wsheet.addCell(new Number(1, 8, (gen / imax)));

			wsheet.addCell(new Label(0, 9, "d Fitness"));
			wsheet.addCell(new Number(1, 9, ((fit / imax))));


			wworkbook.write();
			wworkbook.close();

			workbook = Workbook.getWorkbook(new File(temp));
			wworkbook = Workbook.createWorkbook(new File(log), workbook);
			wworkbook.write();
			wworkbook.close();
			workbook.close();
			File f = new File(temp); // file to be delete
			f.delete();
			System.out.println("fineshed");
			
			Constants.MAXIMAL_GENERATION+=50;
			//Constants.CHANCE_FOR_CROSSOVER+=0.01;
			}

		} catch (Exception e) {
			System.out.println(e);
		}*/
			
	}
}