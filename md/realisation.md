# Realisierung, Evaluation

<!--
*   Beschreibung der Umsetzung des Lösungskonzepts
*   Darstellung der aufgetretenen Probleme sowie deren Lösung bzw. daraus resultierende Einschränkungen des Ergebnisses (falls keine Lösung)
*   Auswertung und Interpretation der Ergebnisse
*   Vergleich mit der ursprünglichen Zielsetzung (ausführlich): Was wurde erreicht, was nicht (und warum)? (inkl. Begründung/Nachweis)

Umfang: typisch ca. 30% ... 40% der Arbeit =18 Seiten
-->

## Level Generator

### generateLevel

```java
public CodedLevel generateLevel(final int xSize, final int ySize){
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
                  fitness=calculateFitness(lvl);
                  lvl.setFitness(fitness);
				}

				if ((bestLevel == null || bestLevel.getFitness() < fitness)
						&& isReachable(lvl, lvl.getExit().x, lvl.getExit().y))
					bestLevel = lvl.copyLevel();

            // Kombinieren
			CodedLevel[] newPopulation = new CodedLevel[Constants.POPULATIONSIZE];
			for (int i = 0; i < startPopulation.length; i += 2) {
				// Elternpaar Auswaehlen
				CodedLevel parentA = roulettWheelSelection(startPopulation);;
				CodedLevel parentB;				
				do {					
					parentB = roulettWheelSelection(startPopulation);					
				} while (parentA == parentB);

				if (Math.random() <= Constants.CHANCE_FOR_CROSSOVER) {						
						CodedLevel combined[]= onePointCrossover(parentA, parentB);
						newPopulation[i] = combined[0];
						newPopulation[i + 1] = combined[1];				
				} else {
					newPopulation[i] = parentA;
					newPopulation[i + 1] = parentB;
				}
			}

			// Mutieren
			for (CodedLevel lvl : newPopulation) {			
					bitFlipMutation(lvl);				
			}

			// Neue Population ist die Startpopulation fuer die naehste Generation
			startPopulation = newPopulation;

			for (CodedLevel lvl : newPopulation) 
				if ((bestLevel == null || bestLevel.getFitness() < lvl.getFitness())
						&& isReachable(lvl, lvl.getExit().x, lvl.getExit().y)) 
					bestLevel = lvl.copyLevel();			
		}
		removeUnreachableFloors(bestLevel);
		return bestLevel;
	}
```


### calculateFitness 
```java
	private float calculateFitness(final CodedLevel level) {
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


## LevelParser

### 

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

#### Erweiterung um Räume und Flure

Um Level in verschiedene unteräume auzuteilen, ohne auf zufällige erzeugung, Raum ähnlicher Sturkturen zu hoffen, kann der Algorithmus erweitert werden. Hierzu werden die bisher erzeugten Level, als Raum interpretiert, zufällig im Level verteilt und mithilfe von Tunneln verbunden. Um Verbindungen zwischen den einzelnen Räume zu erzeugen, wird von jeden Raum aus, in unterschiedlicher Reihenfolge, jede Richtung nach angrenzenden Nachbar Räumen abgesucht, wird ein Raum gefunden, wird auf direkten Wege eine Verbindung hergestellt. Der so erzeugte Flur kann wiederum auch als Raum interpretiert werden und von anderen Räumen als anschlusspunkt genutzt werden. Es werden wieder zufällig Levelstart und Levelende platziert. Sollte ein Raum vom Start aus nicht erreichbar sein, weil sich in seiner Reichweite kein weiterer Raumbefindet, wird der nächste Verbunde Raum gesucht und eine Verbindung hergestellt. 

Die Zusammensetzung der Level könnte auch mithilfe von GAs umgesetzt werden, wird in dieser Implementierung allerdings nicht gemacht. 