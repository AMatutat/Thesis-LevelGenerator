# Realisierung

Im diesen Kapitel wird die Umsetzung der im vorherigen Kapitel vorgestellten Konzepte präsentiert. Dabei werden einzelne Methoden genauer betrachtet. Nach der Beschreibung der Umsetzung eines Konzeptes werden die Ergebnisse ausgewertet und Probleme hervorgehoben. Ansätze zur Lösung der Probleme werden im Kapitel 3 in der jeweils nächsten Konzept Iteration beschrieben. Abschließend folgt eine Gesamtauswertung aller Resultate anhand der in Abschnitt .. vorgestellten Bewertungskriterien. 

## Umsetzung #1

Alle wichtigen Konstanten wurden der Übersichtshalber in extra dafür vorgesehene Klassen ausgelagert. Die Klasse *Reference* enthält eine Reihe an chars, welche im kodierten Level genutzt werden. Die Klasse *Paramter* enthält alle Einstellungsvariablen wie Rekombinationschance oder Populationsgröße. 

### GA

Der GA konnte größtenteils wie im Konzept beschrieben umgesetzt werden. Um ausreichend Raum zur Platzierung von Start und Endpunkt zu gewährleisten, wurde eine Level Mindestgröße von 4x4 implementiert. Die Funktionsweise des Selektionsverfahren, Rekombinatonsverfahren und Mutationsverfahren wurde in Abschnitt .. ausführlich beschrieben, eine Erläuterung der genauen Implementierung findet daher nicht statt.

Im Folgenden werden die Methoden zur Prüfung Verbundener Wände sowie erreichbarer Böden erläutert. Beide Verfahren werden von der Fitnessfunktion genutzt und haben daher großen Einfluss auf die Performance des GA. 

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

Die Methode *isConnected* prüft ob das übergebene Wandfeld mit der Außenwand verbunden ist. Eine Wand gilt auch als Verbunden, wenn eine Nachbarwand als Verbunden gilt. In Zeile *2* wird geprüft ob die zu prüfende Wand eine Außenwand ist, ist dies der Fall gilt sie als Verbunden. In Zeile *5* wird die Wand der Hilfsliste *checkedWalls* der Klasse *CodedLevel* hinzugefügt, um in den kommenden Rekursiven Aufrufen keine Endlosschleife zu erzeugen. In Zeile *6* wird geprüft, ob das Feld zur linken des zu prüfenden Feldes eine Wand ist und ob dieses noch  nicht in der Liste der geprüften Wände ist. Sind die Bedingungen erfüllt wird die Methode *isConnected* Rekursiv für die Nachbarwand aufgerufen (Zeile *8*). Gilt diese als Verbunden, gilt auch die betrachtete Wand als Verbunden (Zeile *9* und *11*). Zeile *6* bis *9* werden für alle Nachbarfelder ausgeführt. 

Die Hilfliste muss vor jeder Prüfung geleert werden. Gilt eine Wand als nicht Verbunden, kann mithilfe der Länge der Liste nachvollzogen werden, aus wie vielen Wänden die Kette besteht und der Fitnesswert entsprechend angepasst werden. 

Ausschnitt aus der Methode *isReachable* :

```
private boolean isReachable(final CodedLevel level, final int x, final int y) {
	if (level.getReachableFloors().size() <= 0)
			createReachableList(level, level.getStart().x, level.getStart().y);

		return level.getReachableFloors().contains(x + "_" + y);
	}
```

Die Methode *isReachable* schaut in der Hilfliste *reachableFloors* der Klasse *CodedLevel* ob das zu prüfende Bodenfeld in der Liste steht (Zeile *5*). Ist die Liste Leer, wird zuerst die Methode *createReachableList* mit den Koordinaten des Startpunktes aufgerufen. 

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

Die Methode *createRachableList* füllt die Liste *reachableFloors* mit allen Felder, die von den übergebenen Koordinaten erreicht werden können. Dafür wird, in Zeile *4* -*8* am Beispiel des linken Nachbars zu sehen, jedes Nachbarfeld des zu prüfenden Feldes betrachtet und geschaut ob dieses als Begehbar eingestuft werden kann (also Boden,Start oder Ausgang) und ob das Nachbarfeld noch nicht in der Liste gespeichert ist. Sind die Bedingungen erfüllt wird die Methode *createReachableList* Rekursiv für den Nachbar aufgerufen. Am Ende stehen alle erreichbaren Felder in der Liste *reachableFloors*. 

Kommt es zur Veränderung im Level, z.B durch Rekombination oder Mutation muss die Hilfsliste geleert werden, da nicht sichergestellt werden kann, das die Daten noch korrekt sind. Das bedeutet, in jeder Generation wird für jedes Level der Population die Liste neu erstellt. 

Die Abbruchbedingung konnte nicht wie geplant implementiert werden. In Tests zeigte sich, das Ein Fittnesswert von ca. 79% bereits in der ersten Generation erreicht wurde, ein Fitnesswert von ca 80% aber nie erreicht wurde und daher eine Endlosschleife entstanden ist. 

Der GA wurde zusätzlich um die Methode *removeUnreachableFloors* erweitert, welche alle nicht erreichbaren Böden der Finalen Lösung durch Wände ersetzt. Das hat keinen Einfluss auf die Spielbarkeit des Levels, sondern dient zur optischen Aufwertung der Level. 

### LevelParser

Der LevelParser konnte, wie im Konzept beschrieben, umgesetzt werden. Im Folgenden wird die Methode zur Generierung der TexturenMap genauer beschrieben. 

Ausschnitt aus der Methode *generateTextureMap* : 

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

In Zeile *1* wird die Textur des oberen linken Feldes ausgelesen. Danach wird Reihenweise über das gesamte Level iteriert (Zeile *2* und *3*). In Zeile *5* werden zwei Texturen durch die Hilfsfunktion *joinBufferedImageSide* so miteinander Verbunden, das die zweite Textur recht an die erste Textur angefügt wird. Im nächsten Schleifendurchgang ist die erste Textur, die Zusammengesetzte Textur der vorherigen Schleifendurchläufe (Zeile *6*). Nachdem über eine komplette Reihe iteriert wurde, befindet sich in der Variable *joinedImgLine* die Textur der ganzen Reihe. In Zeile *11* wird diese Textur durch die Hilfsfunktion *joinedBufferedImageDown* unter die Textur der vorherigen Schleifendurchläufe angefügt. Am ende aller Schleifendurchläufe befindet sich die komplette Level Textur in der Variable *joinedImageComplete* und wird an den vom User bestimmten Pfad abgespeichert.

Dieses Verfahren benötigt, je nach Level Größe, eine große menge an Arbeitsspeicher. In Tests haben 200x200 große Level ausgereicht um den RAM der JVM bei Standarteinstellungen auszureizen. Eine manuelle Erhöhung des JVM RAMs ist daher empfehlenswert. 

### Evaluierung 



## Umsetzung #2

### Evaluierung 



## Umsetzung #3

### Evaluierung 

## Gesamtauswertung



