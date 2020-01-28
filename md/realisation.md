# Realisierung

Im diesen Kapitel wird die Umsetzung der im vorherigen Kapitel vorgestellten Konzepte präsentiert. Dabei werden einzelne Methoden genauer betrachtet. Nach der Beschreibung der Umsetzung eines Konzeptes werden die Ergebnisse ausgewertet und Probleme hervorgehoben. Ansätze zur Lösung der Probleme werden im Kapitel 3 in der jeweils nächsten Konzept Iteration beschrieben. Abschließend folgt eine Gesamtauswertung aller Resultate anhand der in Abschnitt .. vorgestellten Bewertungskriterien. 

## Umsetzung #1

### GA

Ausschnitt aus der Methode *isConnected* : 

```
private boolean isConnected(final CodedLevel level, final int x, final int y) {
	if (x == level.getXSize() - 1 || x == 0 || y == level.getYSize() - 1 || y == 0)
		return true;
	
    level.getCheckedWalls().add(x + "" + y);
	if (level.getLevel()[x - 1][y] == Reference.REFERENCE_WALL
				&& !level.getCheckedWalls().contains((x - 1) + "" + y))
			if (isConnected(level, x - 1, y))
				connected = true;
	...
	return connected; 
```



Ausschnitt aus der Methode *isRachable* :

```
private boolean isReachable(final CodedLevel level, final int x, final int y) {
	if (level.getReachableFloors().size() <= 0)
			createReachableList(level, level.getStart().x, level.getStart().y);

		return level.getReachableFloors().contains(x + "_" + y);
	}
```



Ausschnitt aus der Methode *createRachableList* :

```
private void createReachableList(final CodedLevel level, final int x, final int y) {
	level.getReachableFloors().add(x + "_" + y);
	
	if ((level.getLevel()[x - 1][y] == Reference.REFERENCE_FLOOR
		|| level.getLevel()[x - 1][y] == Reference.REFEERNCE_EXIT
		|| level.getLevel()[x - 1][y] == Reference.REFERENCE_START)
		&& !level.getReachableFloors().contains((x - 1) + "_" + y))
			createReachableList(level, x - 1, y);
	...
}
```



### LevelParser

Der LevelParser konnte, wie im Konzept beschrieben, umgesetzt werden. Im Folgenden wird die Methode zur Generierung der TexturenMap genauer beschrieben. 

```
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
				joinedImgComplete = JoinImage.joinBufferedImageDown(
				joinedImgComplete, joinedImgLine);
				img1 = ImageIO.read(new File(lvl.getLevel()[0][y].getTexture()));
			}
		}	
```



### Evaluierung 



## Umsetzung #2

### Evaluierung 



## Umsetzung #3

### Evaluierung 

## Gesamtauswertung



