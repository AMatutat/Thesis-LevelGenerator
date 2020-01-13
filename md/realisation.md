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
	public CodedLevel generateLevel(final int xSize, final int ySize,)
	CodedLevel bestLevel = null;
		// Startgeneration erzeugen
		CodedLevel[] startPopulation = new CodedLevel[Constants.POPULATIONSIZE];
		for (int i = 0; i < Constants.POPULATIONSIZE; i++)
			startPopulation[i] = (generateRandomLevel(xSize, ySize));

		// Durchlauf
		for (int generation = 0; generation < Constants.MAXIMAL_GENERATION; generation++) {
			// Start und Exit platzieren, Fitness prï¿½fen
			for (CodedLevel lvl : startPopulation) {
				placeStartAndEnd(lvl);
				float fitness = fitness(lvl);						
				}
            
				lvl.setFitness(fitness);
				lvl.resetList();
            
				if ((bestLevel == null || bestLevel.getFitness() < fitness)
						&& isReachable(lvl, lvl.getExit()[0], lvl.getExit()[1])) {
					bestLevel = lvl.copyLevel();	
				}
        }

			// Kombinieren
			CodedLevel[] newPopulation = new CodedLevel[Constants.POPULATIONSIZE];
			for (int i = 0; i < startPopulation.length; i += 2) {

				// Elternpaar Auswï¿½hlen
				CodedLevel parentA;
				CodedLevel parentB;			
					parentA = roulettWheelSelection(startPopulation);
				do {				
					parentB = roulettWheelSelection(startPopulation);				
				} while (parentA == parentB);

				if (Math.random() <= Constants.CHANCE_FOR_CROSSOVER) {
					CodedLevel combined[];					
						combined = crossover(parentA, parentB);
						newPopulation[i] = combined[0];
						newPopulation[i + 1] = combined[1];	
				} else {
					newPopulation[i] = parentA;
					newPopulation[i + 1] = parentB;
				}
			}

			// Mutieren
			for (CodedLevel lvl : newPopulation) {
				mutation(lvl);
			}

			// Neue Population ist die Startpopulation für die nächste Generation
			startPopulation = newPopulation;

			for (CodedLevel lvl : newPopulation)
				if ((bestLevel == null || bestLevel.getFitness() < lvl.getFitness())
						&& isReachable(lvl, lvl.getExit()[0], lvl.getExit()[1])) {
					bestLevel = lvl.copyLevel();				
				}
}
		removeUnreachableFloors(bestLevel);
		return bestLevel;
	}
```

### GenerateRandomLevel

```java
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
```

### Place Start and End

```java
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
				int x = (int) (Math.random() * lvl.getXSize() / 3) + 2 * (int)(lvl.getXSize()/3);
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

				} else if (level.getLevel()[x][y] == Constants.REFERENCE_FLOOR && 	 	      isReachable(level, x, y)) 
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

		// Hinzufï¿½gen der Wall um loops zu verhindern.
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
			createReachableList(level, level.getStart()[0], level.getStart()[1]);

		return level.getReachableFloors().contains(x + "_" + y);

	}
```

### createRechableList

```java
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
								// Ã¼berprÃ¼ft werden
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
								// Beim nÃ¤chsten Run selbes Surface an neuer Position Ã¼berprÃ¼fen
								v--;
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



