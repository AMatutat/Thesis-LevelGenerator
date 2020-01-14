# Realisierung, Evaluation

<!--
*   Beschreibung der Umsetzung des Lösungskonzepts
*   Darstellung der aufgetretenen Probleme sowie deren Lösung bzw. daraus resultierende Einschränkungen des Ergebnisses (falls keine Lösung)
*   Auswertung und Interpretation der Ergebnisse
*   Vergleich mit der ursprünglichen Zielsetzung (ausführlich): Was wurde erreicht, was nicht (und warum)? (inkl. Begründung/Nachweis)

Umfang: typisch ca. 30% ... 40% der Arbeit =30 Seiten
-->

## Level Generator

### generateLevel

```java
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
					gameOfLifeMutation(lvl);
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
```

### GenerateRandomLevel

```java
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
```

### Place Start and End

```java
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
```

### fitness1 

```java
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
```

### fitness2

```java

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
```

### is Connected 

```java
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
```

### is reachable

```java

	private boolean isReachable(final CodedLevel level, final int x, final int y) {
		if (level.getLevel()[x][y] != Constants.REFERENCE_FLOOR && level.getLevel()[x][y] != Constants.REFEERNCE_EXIT
				&& level.getLevel()[x][y] != Constants.REFERENCE_START)
			throw new IllegalArgumentException("Surface must be a floor. Is " + level.getLevel()[x][y]);

		if (level.getReachableFloors().size() <= 0)
			createReachableList(level, level.getStart().x,level.getStart().y);

		return level.getReachableFloors().contains(x + "_" + y);

	}

```

### createRechableList

```java
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
```

### roulettWheelSelection

```java

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
```

### onePointCrossover

```java
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
```

### multiPointCrossover

```java
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
```

### Bit Flip Mutation

```
private void bitFlipMutation(final CodedLevel lvl) {
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
```

### fixRowMutation

```java
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
```

### gameOfLife

```java
private void gameOfLifeMutation(CodedLevel level) {
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
		level = tempLevel.copyLevel();
	}
```

### remove unrechable floors 

```java
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
```

## CodedLevel 

### changeField

```java
public void changeField(int x, int y, char s) {
		if (this.level[x][y] == Constants.REFEERNCE_EXIT)
			this.exit = null;

		else if (this.level[x][y] == Constants.REFERENCE_START)
			this.start = null;

		if (s == Constants.REFEERNCE_EXIT) {
			if (this.hasExit())
				s = Constants.REFERENCE_FLOOR;
			else {
				this.exit=new int[2];
				this.exit[0] = x;
				this.exit[1] = y;
			}
		} else if (s == Constants.REFERENCE_START) {
			if (this.hasStart())
				s = Constants.REFERENCE_FLOOR;
			else {
				this.start=new int[2];
				this.start[0] = x;
				this.start[1] = y;
			}
		}
		this.level[x][y] = s;
	}
```



## LevelParser

### parseLevel

```java
public Level parseLevel(final CodedLevel level) {
		ISurface[][] lvl = new ISurface[level.getXSize()][level.getYSize()];

		for (int x = 0; x < level.getXSize(); x++) {
			for (int y = 0; y < level.getYSize(); y++) {
				if (level.getLevel()[x][y] == Constants.REFERENCE_WALL)
					lvl[x][y] = new Wall();
				else if (level.getLevel()[x][y] == Constants.REFERENCE_FLOOR)
					lvl[x][y] = new Floor();
				else if (level.getLevel()[x][y] == Constants.REFERENCE_START)
					lvl[x][y] = new Start();
				else if (level.getLevel()[x][y] == Constants.REFEERNCE_EXIT)
					lvl[x][y] = new Exit();

			}
		}

		return new Level(level.getXSize(), level.getYSize(), lvl);
	}
```



### place

```java
public void placeMonster(final Level lvl, final Monster monster, final ISurface surfaceToPutOn) throws Exception {
		ArrayList<ISurface> checkedSurfaces = new ArrayList<ISurface>();
		while (checkedSurfaces.size() < lvl.getXSize() * lvl.getYSize()) {
			int x = (int) Math.random() * lvl.getXSize();
			int y = (int) Math.random() * lvl.getYSize();
			if (!checkedSurfaces.contains(lvl.getLevel()[x][y])
					&& lvl.getLevel()[x][y].getClass() == surfaceToPutOn.getClass()
					&& lvl.getLevel()[x][y].setMonsterOnSurface(monster))
				return;
			else
				checkedSurfaces.add(lvl.getLevel()[x][y]);

		}
		throw new Exception("Level voll");
	}
```

### Texturen Generieren

```java
	public boolean generateTextureMap(final Level lvl, final String path, final String name) {
		try {
			BufferedImage img1;
			BufferedImage joinedImgLine = null;
			BufferedImage joinedImgComplete = null;
			img1 = ImageIO.read(new File(lvl.getLevel()[0][0].getTexture()));
			for (int y = 0; y < lvl.getYSize(); y++) {
				for (int x = 1; x < lvl.getXSize(); x++) {
					BufferedImage img2 = ImageIO.read(new File(lvl.getLevel()[x][y].getTexture()));
					joinedImgLine = JoinImage.joinBufferedImageSide(img1, img2);
					img1 = joinedImgLine;
				}
				if (y == 0)
					joinedImgComplete = joinedImgLine;
				else
					joinedImgComplete = JoinImage.joinBufferedImageDown(joinedImgComplete, joinedImgLine);
				img1 = ImageIO.read(new File(lvl.getLevel()[0][y].getTexture()));
			}
			boolean success = ImageIO.write(joinedImgComplete, "png", new File(path + "\\" + name + ".png"));
			System.out.println("saved success? " + success);
			if (!success)
				return false;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
```

```java
	public static BufferedImage joinBufferedImageSide(final BufferedImage img1, final BufferedImage img2) {
		int wid = img1.getWidth() + img2.getWidth();
		int height = Math.max(img1.getHeight(), img2.getHeight());
		BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		Color oldColor = g2.getColor();
		g2.setPaint(Color.WHITE);
		g2.fillRect(0, 0, wid, height);
		g2.setColor(oldColor);
		g2.drawImage(img1, null, 0, 0);
		g2.drawImage(img2, null, img1.getWidth(), 0);
		g2.dispose();
		return newImage;
	}

	public static BufferedImage joinBufferedImageDown(final BufferedImage img1, final BufferedImage img2) {
		{
			int wid = Math.max(img1.getWidth(), img2.getWidth());
			int height = img1.getHeight() + img2.getHeight();
			BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = newImage.createGraphics();
			Color oldColor = g2.getColor();
			g2.setPaint(Color.WHITE);
			g2.fillRect(0, 0, wid, height);
			g2.setColor(oldColor);
			g2.drawImage(img1, null, 0, 0);
			g2.drawImage(img2, null, 0, img1.getHeight());
			g2.dispose();
			return newImage;
		}
	}
}

```



```java
	public static BufferedImage joinBufferedImageSide(final BufferedImage img1, final BufferedImage img2) {
		int wid = img1.getWidth() + img2.getWidth();
		int height = Math.max(img1.getHeight(), img2.getHeight());
		BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		Color oldColor = g2.getColor();
		g2.setPaint(Color.WHITE);
		g2.fillRect(0, 0, wid, height);
		g2.setColor(oldColor);
		g2.drawImage(img1, null, 0, 0);
		g2.drawImage(img2, null, img1.getWidth(), 0);
		g2.dispose();
		return newImage;
	}
	
		public static BufferedImage joinBufferedImageDown(final BufferedImage img1, final BufferedImage img2) {
		{
			int wid = Math.max(img1.getWidth(), img2.getWidth());
			int height = img1.getHeight() + img2.getHeight();
			BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = newImage.createGraphics();
			Color oldColor = g2.getColor();
			g2.setPaint(Color.WHITE);
			g2.fillRect(0, 0, wid, height);
			g2.setColor(oldColor);
			g2.drawImage(img1, null, 0, 0);
			g2.drawImage(img2, null, 0, img1.getHeight());
			g2.dispose();
			return newImage;
		}
	}
}

```



### Room ZUsamensetzer

```java
package generator;

import java.util.ArrayList;

public class ConnectedRoomGenerator {

	public CodedLevel generateLevelWithRooms(final int xSize, final int ySize) {

		CodedLevel level = new CodedLevel(new char[ (int)(xSize * 2.5)][ (int)(ySize * 2.5)], (int)(xSize * 2.5), (int)(ySize * 2.5));
		ArrayList<CodedRoom> rooms = new ArrayList<CodedRoom>();

		for (int x = 0; x < level.getXSize(); x++)
			for (int y = 0; y < level.getYSize(); y++)
				level.getLevel()[x][y] = Constants.REFERENCE_EMPTY;
		int fe = xSize * ySize;
		LevelGenerator lg = new LevelGenerator();
		while (fe > 0) {
			System.out.println("gen Level");
			int xp = (int) (Math.random() * xSize);
			int yp = (int) (Math.random() * ySize);
			if (xp < Constants.MINIMAL_XSIZE)
				xp = Constants.MINIMAL_XSIZE;
			if (yp < Constants.MINIMAL_YSIZE)
				yp = Constants.MINIMAL_YSIZE;
			CodedLevel rl = lg.generateLevel(xp, yp, 2, 1, 2, 2);
			CodedRoom r = new CodedRoom(rl.getLevel(), rl.getXSize(), rl.getYSize());
			r.changeField(rl.getStart().x, rl.getStart().y, Constants.REFERENCE_START);
			r.changeField(rl.getExit().x, rl.getExit().y, Constants.REFEERNCE_EXIT);
			rooms.add(r);
			fe -= xp * yp;
		}

		// Bestimme Raum in den Start und Exit sind
		int startRoom = (int) (Math.random() * rooms.size());
		int exitRoom = (int) (Math.random() * rooms.size());

		// remove start and exit for each room
		for (CodedLevel room : rooms) {
			if (rooms.indexOf(room) != startRoom)
				room.changeField(room.getStart().x, room.getStart().y, Constants.REFERENCE_WALL);
			if (rooms.indexOf(room) != exitRoom)
				room.changeField(room.getExit().x, room.getExit().y, Constants.REFERENCE_WALL);
		}

		// Platziere rooms
		level = placeRooms(rooms, level);

		
		ArrayList<CodedRoom> notConnected = new ArrayList<CodedRoom>();
		notConnected.add(rooms.get(0));
		for (int j = 0; j < rooms.size() - 1; j++) {
			CodedRoom room = notConnected.get(j);
			int abstand = Integer.MAX_VALUE;
			int index = j + 1;
			for (int i = 0; i < rooms.size(); i++) {
				if (!notConnected.contains(rooms.get(j))) {
					int tempabstand = Math.abs(room.getMidPoint().x - rooms.get(j).getMidPoint().x)
							+ Math.abs(room.getMidPoint().y - rooms.get(j).getMidPoint().y);
					if (abstand > tempabstand) {
						abstand = tempabstand;
						index = j;
					}
				}

			}
			notConnected.add(rooms.get(index));
		}
		this.connectRoomsWithSquareFloors(level, notConnected);

		// leere Felder mit Walls auffüllen
		for (int x = 0; x < level.getXSize(); x++)
			for (int y = 0; y < level.getYSize(); y++)
				if (level.getLevel()[x][y] == Constants.REFERENCE_EMPTY)
					level.changeField(x, y, Constants.REFERENCE_WALL);

		return level;

	}

	private CodedLevel placeRooms(ArrayList<CodedRoom> rooms, CodedLevel lvl) {
		CodedLevel level = lvl.copyLevel();
		int counter = 0;
		int placed = 0;
		while (placed < rooms.size() && counter < 100) {
			System.out.println("place room");
			CodedRoom room = rooms.get(placed);
			int yp;
			int xp;
			do {
				xp = (int) (Math.random() * (level.getXSize() - room.getXSize()));
				yp = (int) (Math.random() * (level.getYSize() - room.getYSize()));
			} while (xp + room.getXSize() > level.getXSize() || yp + room.getYSize() > level.getYSize());

			boolean free = true;

			// checken ob kein andere raum im weg ist
			for (int x = xp; x < xp + room.getXSize() - 1; x++)
				for (int y = yp; y < yp + room.getYSize() - 1; y++)
					if (level.getLevel()[x][y] != Constants.REFERENCE_EMPTY)
						free = false;

			if (free) {

				for (int x = xp; x < xp + room.getXSize(); x++) {

					for (int y = yp; y < yp + room.getYSize(); y++) {

						level.changeField(x, y, room.getLevel()[x - xp][y - yp]);
					}

				}
				room.setUpperLeftCorner(xp, yp);
				room.setUpperRightCorner(xp + room.getXSize(), yp);
				room.setLowerLeftCorner(xp, yp + room.getYSize());
				room.setLowerRightCorner(xp + room.getXSize(), yp + room.getYSize());
				room.setMidPoint(xp + room.getXSize() / 2, yp + room.getYSize() / 2);
				placed++;
			}

			counter++;
		}

		if (counter >= 100)
			return placeRooms(rooms, lvl);
		else
			return level;

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

	private void connectRoomsWithSquareFloors(CodedLevel level, ArrayList<CodedRoom> rooms) {

		for (int i = 0; i < rooms.size() - 1; i++) {
			CodedRoom r1 = rooms.get(i);
			CodedRoom r2 = rooms.get(i + 1);

			int xAbstand = r2.getMidPoint().x - r1.getMidPoint().x;
			int yAbstand = r2.getMidPoint().y - r1.getMidPoint().y;
			System.out.println(xAbstand);
			System.out.println(yAbstand);
			if (yAbstand < 0) {
				// links oben
				if (xAbstand < 0) {
					for (int y = Math.abs(yAbstand); y > 0; y--) {						
						level.changeField(r1.getMidPoint().x, r1.getMidPoint().y - y, Constants.REFERENCE_FLOOR);
					}
					for (int x = Math.abs(xAbstand); x > 0; x--) {
						
						
			if(level.getLevel()[r1.getMidPoint().x - x][ r1.getMidPoint().y + yAbstand]!=Constants.REFERENCE_START && level.getLevel()[r1.getMidPoint().x - x][ r1.getMidPoint().y + yAbstand]!=Constants.REFEERNCE_EXIT)			
						level.changeField(r1.getMidPoint().x - x, r1.getMidPoint().y + yAbstand,
								Constants.REFERENCE_FLOOR);
					}

					// recht oben
				} else {
					for (int y = Math.abs(yAbstand); y > 0; y--) {
						level.changeField(r1.getMidPoint().x, r1.getMidPoint().y - y, Constants.REFERENCE_FLOOR);
					}
					for (int x = 0; x <= xAbstand; x++) {
						
						if(level.getLevel()[r1.getMidPoint().x + x][ r1.getMidPoint().y + yAbstand]!=Constants.REFERENCE_START && level.getLevel()[r1.getMidPoint().x + x][ r1.getMidPoint().y + yAbstand]!=Constants.REFEERNCE_EXIT)			
						level.changeField(r1.getMidPoint().x + x, r1.getMidPoint().y + yAbstand,
								Constants.REFERENCE_FLOOR);
					}

				}

			} else {
				// links unten
				if (xAbstand < 0) {
					for (int y = 0; y <= yAbstand; y++) {
						level.changeField(r1.getMidPoint().x, r1.getMidPoint().y + y, Constants.REFERENCE_FLOOR);
					}

					for (int x = Math.abs(xAbstand); x > 0; x--) {
						
						if(level.getLevel()[r1.getMidPoint().x - x][ r1.getMidPoint().y + yAbstand]!=Constants.REFERENCE_START && level.getLevel()[r1.getMidPoint().x - x][ r1.getMidPoint().y + yAbstand]!=Constants.REFEERNCE_EXIT)			
						level.changeField(r1.getMidPoint().x - x, r1.getMidPoint().y + yAbstand,
								Constants.REFERENCE_FLOOR);
					}

					// recht unten
				} else {

					for (int y = 0; y <= yAbstand; y++) {
						level.changeField(r1.getMidPoint().x, r1.getMidPoint().y + y, Constants.REFERENCE_FLOOR);
					}

					for (int x = 0; x <= xAbstand; x++) {
						if(level.getLevel()[r1.getMidPoint().x + x][ r1.getMidPoint().y + yAbstand]!=Constants.REFERENCE_START && level.getLevel()[r1.getMidPoint().x + x][ r1.getMidPoint().y + yAbstand]!=Constants.REFEERNCE_EXIT)		
						level.changeField(r1.getMidPoint().x + x, r1.getMidPoint().y + yAbstand,
								Constants.REFERENCE_FLOOR);
					}
				}
			}

		}

	}

	public static void main(String[] args) {
		ConnectedRoomGenerator cr = new ConnectedRoomGenerator();
		LevelParser p = new LevelParser();
		for (int i = 0; i < 10; i++)
			p.generateTextureMap(p.parseLevel(cr.generateLevelWithRooms(50, 50)), "./results/img", "test_" + i);

	}
}

```

#### Erweiterung um Räume und Flure

Um Level in verschiedene unteräume auzuteilen, ohne auf zufällige erzeugung, Raum ähnlicher Sturkturen zu hoffen, kann der Algorithmus erweitert werden. Hierzu werden die bisher erzeugten Level, als Raum interpretiert, zufällig im Level verteilt und mithilfe von Tunneln verbunden. Um Verbindungen zwischen den einzelnen Räume zu erzeugen, wird von jeden Raum aus, in unterschiedlicher Reihenfolge, jede Richtung nach angrenzenden Nachbar Räumen abgesucht, wird ein Raum gefunden, wird auf direkten Wege eine Verbindung hergestellt. Der so erzeugte Flur kann wiederum auch als Raum interpretiert werden und von anderen Räumen als anschlusspunkt genutzt werden. Es werden wieder zufällig Levelstart und Levelende platziert. Sollte ein Raum vom Start aus nicht erreichbar sein, weil sich in seiner Reichweite kein weiterer Raumbefindet, wird der nächste Verbunde Raum gesucht und eine Verbindung hergestellt. 

Die Zusammensetzung der Level könnte auch mithilfe von GAs umgesetzt werden, wird in dieser Implementierung allerdings nicht gemacht. 