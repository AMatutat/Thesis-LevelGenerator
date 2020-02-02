# Realisierung

In diesem Kapitel wird die Umsetzung der im vorherigen Kapitel vorgestellten Konzepte präsentiert. Dabei werden einzelne Funktionen genauer betrachtet. Nach der Beschreibung der Umsetzung eines Konzeptes werden
die Ergebnisse ausgewertet und Probleme hervorgehoben. Ansätze zur Lösung der Probleme werden im Kapitel 3 in der jeweils nächsten Konzept Iteration beschrieben. Abschließend folgt eine Gesamtauswertung aller Resultate anhand
der in Abschnitt 3.1 und 3.2 vorgestellten Bewertungskriterien.

## Umsetzung des Konzeptes 

Alle wichtigen Konstanten wurden der Übersichtshalber in extra dafür vorgesehene Klassen ausgelagert. Die Klasse *Reference* enthält eine Reihe an Chars, welche im kodierten Level genutzt werden. Die Klasse *Parameter*
enthält alle Einstellungsvariablen wie Rekombinationschance oder Populationsgröße.

### Genetischer Algorithmus

Der GA konnte größtenteils wie im Konzept beschrieben umgesetzt werden. Um ausreichend Raum zur Platzierung von Start und Endpunkt zu gewährleisten, wurde eine Level Mindestgröße von 4x4 implementiert. Die Funktionsweise des Selektionsverfahren, Rekombinationsverfahren und Mutationsverfahren wurde in Abschnitt 2.4 ausführlich beschrieben, eine Erläuterung der genauen Implementierung findet daher nicht statt.

Im Folgenden werden die Methoden zur Prüfung Verbundener Wände sowie erreichbarer Böden erläutert. Beide Verfahren werden von der Fitnessfunktion genutzt und haben daher großen Einfluss auf die Performance des GA. 


\begin{lstlisting}[language=java, caption=Ausschnitt der Methode isConnected]	
private boolean isConnected(CodedLevel lvl,int x,int y) {
	if (x==lvl.getXSize()- 1||x==0||y==lvl.getYSize()-1||y==0)
	return true;
	
   	lvl.getCheckedWalls().add(x+""+y);
   	if (lvl.getLevel()[x - 1][y]==Reference.REFERENCE_WALL
   		&& !lvl.getCheckedWalls().contains((x-1)+""+y))
   			if (isConnected(level,x-1,y))
   				connected = true;
   	...

   	return connected; 
\end{lstlisting}

Listing 4.1 zeigt einen Ausschnitt der Methode *isConnected*. Die Methode *isConnected* prüft ob das übergebene Wand Feld mit der Außenwand verbunden ist. Eine Wand gilt auch als Verbunden, wenn eine Nachbarwand als Verbunden gilt. In Zeile *2* wird geprüft ob die zu prüfende
Wand eine Außenwand ist, ist dies der Fall gilt sie als Verbunden. In Zeile *5* wird die Wand der Hilfsliste *checkedWalls* der Klasse *CodedLevel* hinzugefügt, um in den kommenden rekursiven Aufrufen keine Endlosschleife zu
erzeugen. In Zeile *6* wird geprüft, ob das Feld zur linken des zu prüfenden Feldes eine Wand ist und ob dieses noch nicht in der Liste der geprüften Wände ist. Sind die Bedingungen erfüllt wird die Methode *isConnected* rekursiv für
die Nachbarwand aufgerufen (Zeile *8*). Gilt diese als Verbunden, gilt auch die betrachtete Wand als Verbunden (Zeile *9* und *11*). Zeile *6* bis *9* werden für alle Nachbarfelder ausgeführt.

Die Hilfsliste muss vor jeder Prüfung geleert werden. Gilt eine Wand als nicht verbunden, kann mithilfe der Länge der Liste nachvollzogen werden, aus wie vielen Wänden die Kette besteht und der Fitnesswert entsprechend angepasst werden.


\begin{lstlisting}[language=java, caption=Ausschnitt der Methode isReachable]	
private boolean isReachable(CodedLevel lvl,int x,int y) {
	if (lvl.getReachableFloors().size()<=0)
	  createReachableList(lvl,lvl.getStart().x,lvl.getStart().y);
	return lvl.getReachableFloors().contains(x + "_" + y);
\end{lstlisting}


Listing 4.2 zeigt einen Ausschnitt der Methode *isReachable*. Die Methode *isReachable* schaut in der Hilfsliste *reachableFloors* der Klasse *CodedLevel* ob das zu prüfende Bodenfeld in der Liste steht (Zeile *5*). Ist die Liste Leer, wird zuerst die Methode *createReachableList* mit den Koordinaten des Startpunktes aufgerufen (Zeile *4*). 



\begin{lstlisting}[language=java, caption=Ausschnitt der Methode createRachableList]	
private void createReachableList(CodedLevel lvl, int x, int y) {
	lvl.getReachableFloors().add(x+"_"+y);
	
	if ((lvl.getLevel()[x-1][y] == Reference.REFERENCE_FLOOR
		|| lvl.getLevel()[x-1][y] == Reference.REFEERNCE_EXIT
		|| lvl.getLevel()[x-1][y] == Reference.REFERENCE_START)
		&& !level.getReachableFloors().contains((x-1)+ "_"+y))
			createReachableList(lvl,x-1,y);
	...
}
\end{lstlisting}

Listing 4.3 zeigt einen Ausschnitt der Methode *createReachableList*. Die Methode *createRachableList* füllt die Liste *reachableFloors* mit allen Felder, die von den übergebenen Koordinaten erreicht werden können. Dafür wird, in Zeile *4* -*8* am Beispiel des linken Nachbars zu sehen, jedes Nachbarfeld des zu prüfenden Feldes betrachtet und geschaut ob dieses als begehbar eingestuft werden kann (also Boden, Start oder Ausgang) und ob das Nachbarfeld noch nicht in der Liste gespeichert ist. Sind die Bedingungen erfüllt wird die Methode *createReachableList* rekursiv für den Nachbar aufgerufen. Am Ende stehen alle erreichbaren Felder in der Liste *reachableFloors*. 

Kommt es zur Veränderung im Level, z.B. durch Rekombination oder Mutation muss die Hilfsliste geleert werden, da nicht sichergestellt werden kann, dass die Daten noch korrekt sind. Das bedeutet, in jeder Generation wird für jedes Level der Population die Liste neu erstellt. 

Die Abbruchbedingung konnte nicht wie geplant implementiert werden. In Tests zeigte sich, dass Ein Fittnesswert von ca. 79% bereits in der ersten Generation erreicht wurde, ein Fitnesswert von ca. 80% aber nie erreicht wurde und daher eine Endlosschleife entstanden ist. 

Der GA wurde zusätzlich um die Methode *removeUnreachableFloors* erweitert, welche alle nicht erreichbaren Böden der Finalen Lösung durch Wände ersetzt. Das hat keinen Einfluss auf die Spielbarkeit des Levels, sondern dient zur optischen Aufwertung der Level.

### LevelParser

Der *LevelParser* konnte, wie im Konzept beschrieben, umgesetzt werden. Im Folgenden wird die Methode zur Generierung der Leveltextur genauer beschrieben.

\begin{lstlisting}[language=java, caption=Ausschnitt der Methode generateTextureMap]	
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
\end{lstlisting}

Listing 4.4 zeigt einen Ausschnitt der Methode *generateTExtureMap*. In Zeile *1* wird die Textur des oberen linken Feldes ausgelesen. Danach wird reihenweise über das gesamte Level iteriert (Zeile *2* und *3*). In Zeile *5* werden zwei Texturen durch die Hilfsfunktion *joinBufferedImageSide* so miteinander verbunden, dass die zweite Textur rechts an die erste Textur angefügt wird. Im nächsten Schleifendurchgang ist die erste Textur, die Zusammengesetzte Textur der vorherigen Schleifendurchläufe (Zeile *6*). Nachdem über eine komplette Reihe iteriert wurde, befindet sich in der Variablen *joinedImgLine* die Textur der ganzen Reihe. In Zeile *11* wird diese Textur durch die Hilfsfunktion *joinedBufferedImageDown* unter die Textur der vorherigen Schleifendurchläufe angefügt. Am Ende aller Schleifendurchläufe befindet sich die komplette Level Textur in der Variablen *joinedImageComplete* und wird an den vom User bestimmten Pfad abgespeichert.

Dieses Verfahren benötigt, je nach Level Größe, eine große Menge an Arbeitsspeicher. In Tests haben 200x200 große Level ausgereicht um den RAM der JVM bei Standarteinstellungen auszureizen. Eine manuelle Erhöhung des JVM RAMs ist daher empfehlenswert.

### Ergebnis

Um die optimalen Parameter Einstellungen zu erhalten, wurde die Fitness verschiedener Einstellung für ein 20x20 Level miteinander verglichen. Alle in dieser Arbeit erstellenten Level, sofern nicht anders beschrieben, wurden mit den selben, in Tabelle 4.1 zu sehenden, Grundeinstellungen generiert und unterscheiden sich nur in den verwendeten Subroutinen, der Mutationswahrscheinlichkeit (MW) und Rekombinationschance (RC). 

| Parameter        | Wert |
| ---------------- | ---- |
| Populationsgröße | 500  |
| Maximale Generation | 50 |
| Punkte für Lösbarkeit des Levels | 25 |
| Punkte für verbundene Wände | 2 |
| Punkte für erreichbare Böden | 3 |
| Boden zu Wand Verhältnis | 60/40 |
Table: Übersicht der Grundeinstellung

Alle gezeigten Level, sofern nicht anders beschrieben, wurden mit den, für die verwendeten Subroutinen, optimalen Mutationswahrscheinlichkeit und Rekombinationschance generiert. Die optimalen Einstellungen wurden durch den Vergleich der durchschnittlichen Fitness, der besten Lösung aus 10 Durchläufe pro Einstellung, ermittelt. Dabei wurden jeweils verschiedene Mutationswahrscheinlichkeiten mit verschiedenen Rekombinationschancen getestet. Die gezeigten Level stammen immer aus dem Ende eines Durchlaufes und repräsentieren die beste Lösung des Durchlaufes.

Die präsentierten Grafen zeigen die durchschnittliche Fitness bzw. durchschnittliche Generation des besten Level eines Durchlaufes (ermittelt aus 10 Durchläufe) abhängig von der eingestellten Mutationswahrscheinlichkeit mit zugehöriger besten Rekombinationschance. Insgesamt wurden 12 unterschiedliche Setups mit jeweils 51 unterschiedlichen Parametereinstellungen betrachtet. Im Folgenden werden die vier nennenswertesten Setups analysiert. Die genauen Messergebnisse sind im Abschnitt Messdaten einzusehen. 

Es ist zu beachten, dass die ermittelten optimalen Einstellungen nur für 20x20 Level mit den in Tabelle 4.1 angegebenen Grundeinstellungen gültig sind. Eine genauere Untersuchung, ob und in wie fern diese Werte auch auf andere Einstellungen übernommen werden können, muss erst genauer untersucht werden. Diese genauere Untersuchung findet im Rahmen dieser Arbeit, aus zeitlichen Gründen, nicht statt. 

![Beispiellevel. MV=1, MW=1%, RV=1, RC=60%](figs/level/F1M1C1.png){width=70%}

Abbildung 4.1 zeigt ein Generiertes Level mit den optimalen Einstellungen. Die Rekombinationschance beträgt 60% und die Mutationswahrscheinlichkeit 1%. Rot eingekreist lassen sich bereits Raumähnliche Strukturen erkennen. In Blau sind einzelne, aus der Außenwand herausguckende Wände zu erkenne, der GA sollte so angepasst werden, dass dieses Verhalten nicht mehr vorkommt. In der Mitte lassen sich noch einzelne Wände bzw. kurze Wandketten erkenne. Da sie sehr zufällig platziert wirken, stören sie die Optik bzw. die Immersion des Levels. Die schwarze Treppe stellt den Startpunkt dar, die weiße Leiter den Ausgang, das Level kann gelöst werden. 

![Einfluss der Mutationswahrscheinlichkeit auf die durchschnittliche Fitness der besten Lösung. RC=60%](figs/Graph/g1.png){width=70%}

Abbildung 4.2 zeigt den Einfluss der Mutationschane auf die Fitness.Es ist deutlich zu erkennen, dass eine Erhöhung der Mutationswahrscheinlichkeit zu einer schlechteren Fitness führt. Die Fitness der optimalen Einstellung von 1% liegt sehr nah an den der Fitness bei einer 0% Mutationswahrscheinlichkeit.Abbildung 4.3 zeigt den Einfluss der Mutationswahrscheinlichkeit auf die Generation der besten Lösung. Es ist zu erkenne das bereits ab einer Mutationswahrscheinlichkeit von 3% die Lösung aus einer sehr frühen Generation stammt, und daher ehr zufällig gefunden wurde anstatt sich mit der Zeit entwickelt zu haben. Daraus geht hervor das die Mutationsfunktion nicht dabei hilft, mit jeder Generation bessere Lösungen zu erzeugen, sondern den GA in eine Zufallssuche verwandelt.

![Einfluss der Mutationswahrscheinlichkeit auf die durchschnittliche Generation der besten Lösung. MW=1% RC=60%](figs/Graph/g2.png){width=70%}

## Optimierung des Algorithmus

Alle Anpassungen und Neuerungen wurden nach dem angepassten Konzept implementiert.  Das Programm wurde für die neue Abbruchbedingung angepasst. 

Abbildung 4.4 zeigt ein Level mit der ersten Mutations- (MV) und Rekombinationsversion (RV) sowie der neu angepassten Fitness. Durch die angepasste Fitnessfunktion sind im Vergleich zu Abbildung .. weniger einzelne Wandstücke,welche aus der Außenwand herausgucken zu erkennen. 

![Beispiellevel. MV=1, MW=1%, RV=1; RC=60%](figs/level/F2M1C1.png){width=50%}

Abbildung 4.5 zeigt den Einfluss der Mutationswahrscheinlichkeit bei der Verwendung der neuen Mutationsfunktion, der neuen Rekombinationsfunktion sowie angepasster Fitnessfunktion. Die höchste Fitness wird bei einer hohen
Mutationswahrscheinlichkeit von 40% erreicht, ähnlich hohe Werte erzielen ähnliche Fittnesswerte. Es ist davon auszugehen das bei einer so hohen Mutationswahrscheinlichkeit im Laufe der Zeit alle Gene mutiert werden. Der Zusammenhang zwischen hoher
Fitnesswerten und hoher Mutationswahrscheinlichkeit bestärkt die Annahme das die Mutationsfunktion zu *stark* ist. Allerdings liefert auch ein geringe Mutationswahrscheinlichkeit von 5% ähnlich gute Ergebnisse.

![Einfluss der Mutationswahrscheinlichkeit auf die durchschnittliche Fitness der besten Lösung. RC=60%](figs/Graph/g3.png){width=70%}

Abbildung 4.6 zeigt ein Level mit einer Mutationswahrscheinlichkeit von 5%. Abbildung 4.7 zeigt ein Level mit einer Mutationswahrscheinlichkeit von 40%. Im direkten Vergleich fällt auf, dass die hohe Mutationsrate dazu geführt hat, dass alle Wände verbunden sind, die niedrigere Mutationswahrscheinlichkeit hat noch einige einzelne Wände übriggelassen. Beide Varianten bieten wenige bis gar keine Raumähnliche Strukturen, sondern sehen ehr wie ein großer Raum aus. Abbildung 4.8 zeigt ein Level mit niedriger Mutationswahrscheinlichkeit und verringerter Bodenfelder Anzahl (40/60). Hier lassen sich Raumähnliche Strukturen verbunden mit Fluren erkennen. Der untere Bereich muss zur Absolvierung des Levels nicht betreten werden und bietet Freiraum zum Erkunden.

![Beispiellevel. MV=2, MW=5%, RV=2, RC=80%](figs/level/F2M2C2LOWPMUT.png){width=50%} 

![Beispiellevle. MV=2; MW=40%, RV=2, RC=80%](figs/level/F2M2C2HIGHPMUT.png){width=50%}

![Beispiellevel mit verringerter Bodenfläche. MV=2; MW=40%, RV=2; RC=80%](figs/level/F2M2C2LOWPMUT40PFLOOR.png){width=50%}

## Ansätze zur Erweiterung des Algorithmus

Die neue Mutationsfunktion wurde, wie im veränderten Konzept beschrieben, implementiert. Die beiden Verfahren zur Platzierung von Räumen wurden nach dem Konzept implementiert. Das *Spelunky-Style* Verfahren wurde zusätzlich um die Platzierung von Türen in optionalen Räumen und der Platzierung von Schlüsseln auf den kritischen Pfad erweitert. 

Um den *Reise zum Mittelpunkt* Algorithmus umzusetzen wurden die Klasse *CodedRoom* erzeugt welche von der Klasse *CodedLevel* erbt und zusätzlich Angaben über die Koordinaten des Mittel- und der Eckpunkte bereithält. 

Abbildung 4.9 zeigt den Einfluss der Mutationswahrscheinlichkeit bei der Verwendung der *Game of Life* Mutationsfunktion. Ähnlich zur vorherigen Mutationsfunktion liefert eine hohe Mutationswahrscheinlichkeit von 40% den höchsten Fitnesswert, eine geringere Chance von 3% liefert ähnlich gute Ergebnisse.

![Einfluss der Mutationswahrscheinlichkeit auf die durchschnittliche Fitness der besten Lösung. RC=60%](figs/Graph/g4.png){width=70%}

Abbildung 4.10 zeigt ein Level mit hoher Mutationswahrscheinlichkeit von 40%. Zum Vergleich zeigt Abbildung 4.11 ein Level mit einer Mutationswahrscheinlichkeit von 3%. Beide Varianten erzeugen Level mit einer Vielzahl einzeln Platzierter Wände. Diese
Mutationsfunktion kann als Rückschritt im Vergleich zur vorherigen Version betrachtet werden.

![Beispiellevel. MV=3, MW=40%, RV=2, RC=60%](figs/level/F2M3C2HIGHPMUT.png){width=50%} 

![Beispiellevel. MV=3, MW=3%, RV=2, RC=60%](figs/level/F2M3C2LOWPMUT.png){width=50%}

Abbildung 4.12 zeigt ein Level welches mithilfe des *Spelunky-Style* Algorithmus erzeugt wurde. Die Räume wurden dabei mit der Mutationsversion 2 und einer Mutationswahrscheinlichkeit von 5% erzeugt. Es fällt direkt negativ auf, dass sich alle Räume sehr ähnlichsehen und dass das Gittermuster sofort zu erkenne ist. Dafür liefert diese Variante neben den Kritischen Lösungspfad eine Reihe an optionalen Räumen. Der obere Rechte Raum ist mithilfe einer Tür verschlossen und kann erst mit dem Schlüssel aus dem Raum in der zweiten Reihe geöffnet werden.

![Beispiellevel erzeugt mit den Spelunky-Style Algorithmus](figs/level/Spelunky.png){width=50%} 

![Beispiellevel erzeugt mit den Reise zum Mittelpunkt Algorithmus](figs/level/test_0.png){width=50%}

Abbildung 4.13 zeigt ein Level, welches durch die zufällige Platzierung von Räumen erzeugt wurde. Die Räume wurden mit den bereits beschriebenen Parametereinstellungen erzeugt. in der Mitte des Levels sind die langen Verbindungstunnel zu erkennen, auch sind mehrere optionale Räume im Level zu finden. Negativ fällt die große Wandfläche sowie die langen Wege zwischen den Räumen auf.

## Gesamtauswertung

In diesen Abschnitt folgt eine genauere Analyse der erstellten Lösungen. Der Algorithmus bietet viele Einstellungsmöglichkeiten, es würde daher den Rahmen der Arbeit sprengen, alle Möglichkeiten genauer zu betrachten. Aus der Realisierungsphase haben sich drei Verfahren herauskristallisiert, welche nun genauer betrachtet werden. Um ein Gefühl der Vielfältigkeit der einzelnen Verfahren zu bekommen, stehen im Anhang B. weitere Beispiellevel zur Verfügung.    

Alle Verfahren erfüllen die in Abschnitt 3.1 aufgestellten Grundanforderungen an das Projekt. Jedes Verfahren erstellt Lösbare 2D-Level. Der Parser ermöglicht es den Studenten die generierten Level in ihr PM-Dungeon zu integrieren. Monster, Items und Spezialfelder können zufällig im Level zu verteilet werden. Die vorgegebenen Interfaces helfen dabei, auch selbst konzeptionierte Objekte im Level zu verteilen, dadurch liefert der Generator die Freiheit auch abseits der Aufgabenstellung aktiv und kreativ zu werden. Da Objekte zufällig platziert werden, kann der Schwierigkeitsgrad des Levels nur bedingt kontrolliert werden, da zwar die Stärke der Monster kontrolliert werden kann aber nicht sichergestellt werden kann das starke Monster nur auf optionalen Routen platziert werden. Der Parser ist in der Lage die Leveltextur zu erstellen, damit diese von den Studenten verwendet werden kann.

### Spelunky-Style

Das *Spelunky-Style* Verfahren erstellt Level, die neben den kritischen Pfad eine Vielzahl an optionalen Räumen bietet. Die Räume bieten Freiraum um spannenden Risk and Reward Situationen zu erzeugen. Zwar kann nicht kontrolliert werden, welche Monster und Items in die optionalen gebiete platziert werden, das muss aber nicht unbedingt schlecht sein. Es sollte regelmäßig dazu kommen, das gute Items in optionalen Gebieten platziert werden. Die Ungewissheit ob es sich wirklich lohnt einen optionalen Pfad zu erkunden, führt zu noch spannenderen Entscheidungen und Risk and Reward Momenten. 

Das *Spelunky-Style* Verfahren ist als einziges Verfahren in der Lage, Türen und Schlüssel zu platzieren. Türen verschließen nur optionale Gebiete und der Schlüssel wird auf den kritischen Pfad platziert. Dadurch kann die Lösbarkeit des Levels sichergestellt werden als auch garantiert werden, dass der Schlüssel immer erreichbar ist ohne die Tür vorher öffnen zu müssen.

Sowohl die Immersion als auch die Einzigartigkeit der Level sind Schwachpunkte des *Spelunky-Style* Verfahrens. Das verwendete Gittermuster ist unübersehbar und obwohl die Räume alle zufällig Generiert werden, ähneln sie sich dennoch sehr stark voneinander. Die einzelnen Level bietet kaum Alleinstellungsmerkmale, lediglich die unterschiedlichen Routen zum Ziel bieten Abwechslung. 

Auch wenn dieses Verfahren sich sehr gut dazu eignet interessante Risk and Reward Situationen zu erzeugen, verhindert die starke Ähnlichkeit der Level und das erkennbare Gittermuster das Eintauchen in der Spielwelt. Das Verfahren eignet sich in der aktuellen Form nicht um spaßige Level für das PM-Dungeon zu erzeugen.

### Reise zum Mittelpunkt

Dieses Verfahren erstellt Level die zufällig entweder nur einen Pfad oder zusätzliche optionale Routen bieten. Durch die unterschiedlich großen Räume entsteht innerhalb des Levels Abwechslung. Die Zufällige Platzierung der Räume sorgt dafür, dass jedes Level deutlich anders aussieht als andere. Risk und Reward Momente können zufällig entstehen, es kann nicht sichergestellt werden das jedes Level optionale Routen hat. Die teils langen Laufwege zwischen den Räumen können den Spielspaß trüben. Die Level bestehen aus einen großen Teil aus Wandblöcken, je nachdem wie die Kameraperspektive von den Studenten implementiert wird, ist dies entweder nicht zu sehen oder wird als Immersionsbrechend war genommen. 

Dadurch das die Größe der einzelnen Räume innerhalb eines Levels variiert, würde dieses Verfahren von einer dynamischen Anpassung der Parameter stark profitieren. 

Das Verfahren ist nicht in der Lage Türen und Schlüssel zu platzieren, da es kein Verweis darauf gibt, ob ein Raum optional oder zum kritischen Lösungspfad gehört. 

Die Struktur der Level ist stärker als bei den anderen Verfahren von Zufall abhängig. Das Verfahren ist in der Lage gute Level mit unterschiedlichen Pfaden und abwechslungsreichen Level zu erzeugen. Es eignet sich daher zur Nutzung im PM-Dungeon.

### Mutation 2 mit geringerer Bodenfläche

Dieses Verfahren verwendet nur den GA und benötigt keinen weiteren Algorithmus. Es erzeugt Level mit raumähnlichen Strukturen und optionalen Routen. In den Level sind wenige bis keine einzelne Wandfelder zu erkennen, die einzelnen Wandketten bilden optisch ansprechende Trennwände und man kann sich gut vorstellen das diese in einen Dungeon so verbaut werden. 

Da Raumähnliche Strukturen zwar erzeugt, jedoch nicht erkannt werden können, ist die sinnvolle Platzierung von Türen und Schlüsseln nicht möglich. 

Dieses Verfahren erzeugt Abwechslungsreiche Level mit optionalen Routen für spannende Risk and Reward Situationen. Von den hier präsentierten Verfahren erzeugt es die optisch ansprechendsten Level und ist daher am besten zur Verwendung im PM-Dungeon geeignet.



