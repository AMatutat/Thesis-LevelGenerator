#  Level Generierung mit Genetischen Algorithmen 

Im Folgenden wird ein Konzept zur Erstellung eines Level Generators basierend auf GA erläutert. Zuerst werden die Anforderungen an das Projekt definiert und die Zielsetzung spezifiziert. Danach folgt die Ausarbeitung des Konzeptes.  Am Ende des Abschnittes wird das erstellte Konzept mit den aus Kapitel 2 bekannten Verfahren verglichen. 

*Anmerkung: Das Konzept hat im Laufe der Entwicklung einige Anpassungen erfahren, um die Übersicht zu bewahren wird daher das Konzept in drei Teile präsentiert, welche jeweils als neue Iteration betrachtet werden können. Im Kapitel Realisierung wird dasselbe Verfahren zur Unterteilung der einzelnen Iterationen angewendet. Es ist zu empfehlen, sich jeweils das Konzept und die Realisierung einer Iteration nach der anderen anzusehen um den Verlauf der Entwicklung nachzuempfinden.*

## Anforderungen an das Projekt

Der Generator muss in der Programmiersprache Java implementiert werden, da diese die durch die Prüfungsordnung festgelegte Programmiersprache ist. Der Generator muss 2D-Level, bestehend aus Wänden und Böden generieren. Die generierten Level müssen als lösbar eingestuft werden können. Als lösbar gilt ein Level dann, wenn der Levelausgang vom Levelstart aus erreichbar ist. Die Studenten müssen die Möglichkeit haben Items und Monster im generierten Level zu platzieren. Es muss eine Leveltextur erstellt werden, welche den Aufbau des Levels grafisch darstellt. Generierte Level müssen in das PM-Dungeon integrierbar sein. 

Des Weiteren sollen Türen und dazugehörige Schlüssel so im Level platziert werden, das keine Softlocks entstehen. Die Größe der Level soll zum Start von den Studenten bestimmbar sein. Die Studenten sollen in der Lage sein, neben den in den Aufgaben vordefinierten Elemente, eigen konzeptionierte Elemente in die Level zu integrieren. Der Level Generator soll den Studenten möglichst viel Freiheit in der Umsetzung der Spielinhalte, abseits der Level, gewähren. 

Dem Algorithmus sollen möglichst wenig Informationen über die Problem Domäne, daher Konzepte und Regeln guten Leveldesigns, vermittelt werden. Ziel ist es, in der abschließenden Evaluierung zu prüfen, wie viele Konzepte passiv beachtet werden. 

Der Generator muss nicht in der Lage sein, neue Spielregeln, wie das sprengen von Wänden, in den Generierungsprozess zu integrieren. Der Generator muss Texturen nicht selbständig erzeugen oder die Größenverhältnisse übergebener Texturen anpassen. Der Generator muss keine Quests oder Boss Gegner erzeugen. Der Generator muss keine Level in Echtzeit erzeugen können. Es wird kein Fokus auf die Optimierung der Laufzeit gelegt.

## Bewertungskriterien

Die erzeugten Level werden im Abschluss anhand der in Abschnitt 2.2 aufgestellten Regeln für gutes Leveldesign bewertet. Insbesondere werden folgende Faktoren untersucht: 

- Lösbarkeit und Fehlerfreiheit

- Risk and Reward

Level sollten sowohl verschiedenen Wege zum Ziel als auch optionale Gebiete bieten. Optionale gebiete erhöhen der Erkundungsdrang der Spieler. 

- Immersion

Da das PM-Dungeon aus Räumen und Fluren bestehen soll, sollte das Aussehen der Level auch dieses wiedergeben. Wände sollten sinnvoll im Level platziert sein und miteinander verbunden sein. Einzelne Wandstücke erfüllen keinen logischen Sinn und werden daher als störend war genommen. Räume sollten nicht zu klein aber auch nicht zu groß ausfallen. 

- Einzigartigkeit

Das Kriterium Balancing wird nicht betrachtet, da dieses von den im Level platzierten Monstern abhängig ist und die Studenten eigenständig Monster verteilen können. Dir Kriterien Environmental Storytelling, Gameplay First und Navigation werden nicht betrachtet, da sie für das von den Studenten entwickelte Spiel keine Bedeutung haben. Die Kontrolle des Pacing ist eine sehr schwierige Aufgabe, und noch schwieriger zu automatisieren und würde den Rahmen dieser Arbeit überschreitet und ist daher kein Bewertungskriterium. Durch die Verwendung PLG kann das Kriterium Effizienz für den Gesamtem Algorithmus als erfolgreich beachtet angesehen werden.     

## Startkonzept

Der Generator wird aus zwei Teilen zusammengesetzt. Der erste Teil ist der GA selbst, er generiert Level die aus Wänden, Böden einen Start und ein Ziel bestehen. Der zweite Teil ist ein Parser, der für die Integration der erzeugten Level in die Spiellogik zuständig ist. Der Parser ist auch dafür verantwortlich, das Monster und Items im Level platziert werden, er soll die Möglichkeit bieten, einzelne Wände und Böden gegen andere Oberflächen auszutauschen. Er ist für die Generierung der Levelgrafik verantwortlich.

### Genetischer Algorithmus

In Abschnitt 2.4.2 wurden die Unterschiede zwischen GA und ES erläutert. In dieser Arbeit wird eine Mischform aus beiden Implementiert. Es wird die reellwertige Kodierung der ES mit den Selektionsverfahren von GA kombiniert. Im Folgenden wird der Algorithmus weiterhin als GA bezeichnet. 

#### Kodierung	

Die Kodierung der Level passt sich der vorgesehenen Implementation in die Spie Logik an. Ein Level kann als zwei Dimensionales Array verstanden werden, die einzelnen Felder im Array sind Felder im Level. Der Spieler bewegt sich pro Zug genau um ein Feld. 

Die Level werden als zwei Dimensionales Char Array kodiert, jedes Feld im Array repräsentiert ein Gen. Die Gene repräsentieren eine Oberfläche, also ob das Feld eine Wand, ein Boden, der Start oder Ausgang sind. Wände werden als nicht passierbare Felder betrachtet. 

Ein Kodiertes Level könnte ausgegeben also so aussehen:

```java
WWWWWWW		
WFFFFFW
WFFFFSW
WFXFFFW
WWWWWWW

W=Wall		S=Start
F=Boden		X=Ende
```



#### CodedLevel

Eine Instanz der Klasse *CodedLevel* ist ein Chromosom, also eine mögliche Lösung bzw. ein Level. Neben den Char Array, welches den Levelaufbau entspricht und Informationen über die Größe des Levels, besitzen *CodedLevel* eine Fitness, welche die Güte der Lösung angibt sowie Informationen über den Standort des Start- bzw. Endpunktes.

![UML CodedLevel.](figs/codedLevel.PNG){width=50%}

Neben Getter und Setter verfügt die Klasse über die *changeField* Methode. Diese verändert das Allel eines Gens auf den übergebenen Wert. Zwar könnte diese Änderung auch direkt am Array vorgenommen werden, dann würden
allerdings Änderungen an der Position der Start und Ausgänge evtl. verloren gehen. Sollte ein Start bzw. Ausgang gesetzt werden, obwohl schon einer vorhanden ist, wird stattdessen ein Boden gesetzt, ist keiner vorhanden werden die
Koordinaten dem entsprechenden Attribut zugewiesen.   

#### LevelGenerator

Die Klasse *Level Generator* beinhaltet die Implementation des GA. Sie beinhaltet die Implementationen aller Subroutinen. Sie verfügt über die Methode *generateLevel*, welche als Einstiegspunkt in den GA gesehen werden
kann. Ihr wird die gewünschte Level Größe übergeben.  Sie ist für die Durchführung des GA Ablaufes verantwortlich.

##### Erzeugen der Startpopulation

Um die Startpopulation zu erzeugen werden die Level zufällig mit Oberflächen gefüllt. Die **CHANCE_TO_BE_FLOOR** gibt an, mit welcher Chance eine Oberfläche ein Boden wird. Alle Außenfelder des Levels werden mit Wänden
gefüllt, um einen Level Abschluss darzustellen. Ein und Ausgang werden zufällig auf Böden gesetzt.

##### Fitnessfunktion 

Die Fitnessfunktion bewertet die Level. Die Bewertungskriterien sollen dabei helfen, dass das Level aus größeren Bodenflächen, den Räumen verbunden durch Wandketten, den Fluren besteht. 

Jedes Bewertungskriterium hat dabei eine andere Wertigkeit. Die Bewertungskriterien sind

1. Wie viele Böden sind begehbar? 
2. Ist das Level lösbar?
3. Wie viele Wände sind Verbunden? 

Die Anzahl der begehbaren Böden ist gleichzusetzten mit der Spielfläche und der daraus folgenden Interaktion mit dem Level. Sind Böden nicht erreichbar, weil sie von Wänden eingeschlossen sind, können diese Flächen nicht bespielt werden und der restliche Dungeon wird kleiner. Dadurch sollte die Erstellung von Raumflächen begünstigt werden. 

Die Lösbarkeit ist eines Levels ist Kernvoraussetzung um als gültige Lösung zu gelten. Daher nimmt dieses Kriterium großen Einfluss auf die Fitness. 

Das dritte Kriterium soll vor allem einzeln im Level platzierte Wände vermeiden, da diese in der Logik des Spiels keinen Sinn erfüllen und daher vom Spieler als störend empfunden werden und die Immersion mindern. Für jede Wand, die direkt oder indirekt über Nachbarn mit der außen Wand verbunden sind gibt es Fitnesspunkte. Dadurch sollen Wandketten gefördert werden. Die Verbindung mit den Außenwänden ist vom klassischen Hausaufbau inspiriert, da dort in der Regel auch jede Wand in irgendeiner Form mit der Außenwand verbunden ist. Als Nachbarn gelten nur Felder die sich entweder direkt über, drunter, links oder rechts vom betrachteten Feld befinden. Da die hier generierten Level auch größere Dungeon darstellen sollen, könnten auch Säulenartige Strukturen oder Stützwände wünschenswert sein. Daher gibt es für Wände, die zwar keine Anbindung an den Levelrand haben, jedoch mit anderen Wänden verbunden sind, Teilpunkte.

##### Selektion und Rekombination

Als Selektionsverfahren wird das im Abschnitt 2.14 beschreibende Roulette Wheel Selection Verfahren genutzt. Da keine negative Fitness erreicht werden kann als auch von einer großen Spannbreite an Bewertungen ausgegangen werden kann, bietet sich ein Rank Selection Verfahren nicht an. Alternativ wäre auch die Verwendung der Tournament Selektion denkbar. 
Sollte die es zu einem Crossover, abhängig von der **CHANCE_FOR_CROSSOVER**, kommen, werden beide Eltern mithilfe des One-Point-Crossover rekombiniert. Dieses Verfahren hat eine geringe Chance, gute Level Strukturen zu zerstören, da nur ein großer und nicht viele kleine Eingriffe am Level durchgeführt werden. 
Das Uniform Crossover Verfahren würde wieder eine komplett Zufällige Anordnung von Böden und Wänden zu folge ziehen und ist daher für die Generierung von Leveln nicht geeignet.

##### Mutation 

Zur Levelgenerierung bieten sich fast alle bekannten Mutationsverfahren an. In dieser Implementierung wird eine angepasste Version der Bit-Flip Mutation verwendet. Ignorieren wir bei der Mutation Start und Ausgangspunkt, bleiben noch Felder, die entweder Böden oder Wände sind. Es wird für jedes Gen überprüft ob es zur Mutation kommt, und wenn ja, wird der Allel des Gens geändert. Wände werden zu Böden und Böden zu Wänden.

#### Abbruchkriterium

Die GA wird dann beendet, wenn ein lösbares Level den Fitnessschwellwert überschreitet. Der Fitnessschwellwert ergibt sich aus der maximal Erreichbaren Fitness. Da die maximal erreichbare Fitness aufgrund von Zufallsfaktoren nicht exakt bestimmt werden kann, wird sich ihr angenähert:

```latex
AnzahlBoeden \approx \text{ CHANCE_TO_BE_FLOOR } * \Sum{  }{  }{  }Felder \linebreak
AnzahlWaende \approx \text{ 1-CHANCE_TO_BE_FLOOR } * \Sum{  }{  }{  }Felder \linebreak
MaxFitness\approx \text{PUNKTE_FUER_ERREICHBARKEIT} * \text{ AnzahlBoeden } + \text{PUNKTE_FUER_VERBUNDEN} * \text{ AnzahlWaende } + \text{ PUNKTE_FUER_LOESBAR }
```
Um Zufallswerte auszugleichen, wird der Schwellwert unter den berechneten Wert angesiedelt. 

![UML LevelGenerator.](figs/levelGen.png){width=80%}

### LevelParser

Die Klasse *LevelParser* stellt alle Methoden zur Verfügung, die benötigt werden um von einen generierten *CodedLevel* zu einem richtigen Level mit Monstern und Items zu gelangen. Um den Parser so zu gestalten, dass er für möglichst alle Implementierungen der Studenten funktionsfähig ist, werden eine Reihe an Interfaces vorgegeben, welche von den Studenten in ihre Implementation integriert werden müssen. 

Mithilfe des Interfaces *ILevel* wird sichergestellt, dass die Klasse Level die Methoden *getXSize* und *getYSize*, welche die Maße der Level zurückgeben sowie die Methode *getLevel* welches ein zwei Dimensionales *ISurface* Array zurückliefert, welches analog zu den aus *CodedLevel* bekannten Array den Levelaufbau darstellt. Das Interface *ISurface* muss von jeder Oberfläche implementiert werden, es versichert Methoden zur Platzierung von Monstern und Items. Ebenso muss die Methode *getTexture* implementiert werden, welche den Pfad zu einer Grafik liefert, die auf der Levelgrafik die jeweilige Oberfläche darstellen soll.

Die *parseLevel* Methode verwandelt ein *CodedLevel* in ein richtiges Level um. Die Chars welche bisher als Referenzen für Oberflächen gedient haben, werden durch Instanzen der entsprechenden Oberflächen ausgetauscht. 

Der Parser nutzt die von *ISurface* bereitgestellte Methoden um übergebene Monster oder Items auf zufällige Felder zu verteilen. Sollen neben Wänden oder Böden auch andere Oberflächen integriert werden, bietet der Parser eine Funktion zum Austauschen einer zufällig gewählten Instanz des Oberflächentypes A um eine Instanz des Oberflächentypes B zu platzieren. 

Die Funktion *generateTextureMap* iteriert über das zwei Dimensionale *ISurface* Array und holt sich mithilfe der *getTexture* Methode die Texturen der einzelnen Oberflächen und fügt diese nacheinander zusammen und speichert das erzeugte Bild ab. Dadurch das die Textur nicht Typ weise sondern Instanz weise ausgelesen wird, wird es den Studenten ermöglicht, Wände mit unterschiedlichen Texturen zu verwenden.

![UML LevelParser und Interfaces.](figs/parser.PNG){width=100%}

### Locks and Keys

Um ein sinnvolles Konzept zur Platzierung von Türen und Schlüsseln zu entwickeln, muss erst ein Eindruck erlangt werden, wie die generierten Level aufgebaut sind. Grundsätzlich müssen Raumähnliche Strukturen erkannt werden, welche sich dadurch auszeichnen, dass sie vom Start Punkt aus nur über ein Feld erreichbar sind, der Tür. Auf diesem Feld kann die Tür platziert werden, der Key wird zwischen Start und Tür verteilt. 

Das Klassendiagramm für den kompletten Generator ist im Anhang A.1 zu sehen. Das finale UML, nach allen Überarbeitungen ist im Anhang A.2 einzusehen. 

### Methoden zur Auswertung und Optimierung

Um den Einfluss der verschiedenen Parameter nachvollziehen zu können, wird ein Logger in den GA implementiert. Es werden alle relevanten Parameter sowie Ausgabe Daten geloggt. Dazu zählen: 

-  Größe der Level
- Populationsgröße
- Punkte Verteilung der für die einzelnen Fitness Kriterien 
- Chance für Rekombination und Mutation 
- Durchschnittlich erreichte Fitness der Lösung
- Durchschnittlich gebrauchte Generationen für die beste Lösung 

Bevor der Fitnessschwellwert implementiert wird, wird die Abbruchbedingung so bestimmt, dass eine feste Anzahl an Generationen durchlaufen werden, die zurück gelieferte Lösung entspricht der Lösung mit der höchsten Fitness über den gesamten Generierungsprozess. Ohne Schwellwert wird sichergestellt das der
Generator nicht frühzeitig abbricht. Mithilfe der so erlangten Daten lassen sich mangelhafte Mutation und Rekombination Methoden erkennen. Die Daten helfen dabei, die optimalen Parametereinstellungen zu identifizieren

## Optimierung des Algorithmus 

### Anpassung der Fitnessfunktion

Um die einzelne Wandblöcke aus Wandketten zu entfernen muss die Fitnessfunktion erweitert werden, da dieses Verhalten von der aktuellen Fitnessversion nicht bestraft wird. Die Fitnessfunktion wurde um ein weiteres Kriterium erweitert.  

- Für jeden nicht Wand Nachbar einer Wand, werden Punkte abgezogen.

Durch dieses Kriterium ist eine negative Fitness erreichbar, daher wäre die Umsetzung eines neuen Selektionsverfahren notwendig. Da die Wahrscheinlichkeit einer negativen Fitness allerdings sehr gering ist, wurde eine minimal Fitness von *1* festgelegt, das erlaubt es, das bereits Implementierte Selektionsverfahren zu behalten.

### Neue Mutation

Da die erste Mutationsversion den GA in eine Zufallssuche verwandelt, wird ein neues Verfahren zur Mutation entwickelt.

Die neue Mutationsfunktion iteriert über jede Reihe eines Levels, kommt es zu Mutation werden alle Wände der Reihe, die sich auf der linken Seite befinden, solange nach links verschoben, bis sie mit der Außenwand verbunden sind, dasselbe passiert mit der rechten Seite, nur werden hier alle Wände nach rechts geschoben.

Dieses Mutationsverfahren muss genauer betrachtet werden, da sie direkten, positiven, Einfluss auf die Fitness nimmt und das eigentliche Ziel einer Mutation damit verfehlen könnte.

### Neue Rekombination 

Da die Mutation Version 2 dafür sorgt, dass sich Wände links und rechts der Mitte anordnen, und ein vertikaler Schnitt durch die Mitte daher wenig Einfluss auf die Weiterentwicklung der Generation nimmt, wurde ein neues Rekombinationsverfahren implementiert. Es wird das Multi-Point-Crossover verfahren genutzt um horizontal durch das Level zu schneiden. Dadurch sollen neue Anknüpfpunkte für die Wände während der Mutation entstehen.

### Neue Abbruchbedingung

Da die ursprünglich vorgesehene Abbruchbedingung nicht verwendet werden kann, wird die aus dem Abschnitt.. bekannte Methoden zur Auswertung und Optimierung bekannte Abbruchbedingung übernommen. Da die optimale Anzahl an Generationen von der Größe des Levels abhängig ist, wird die Anzahl als Parameter übergehbbar sein. Den Studenten wird eine Tabelle mit Beispielwerten zur Orientierung zur Verfügung gestellt. Diese Abbruchbedingung hat den Vorteil, dass die Studenten zu einen gewissen maßen selbst die Güte des Levels bestimmen können. Sollte zum Beispiel nur eine kleinere Änderung am Spiel getestet werden wollen, reicht unter Umständen ein zufällig zusammengesetztes Level aus.

## Ansätze zur Erweiterung des Algorithmus 

### Mutation Game of Life

Um eine Alternative zu der starken Mutation Version 2 zu bieten, wurde eine dritte Mutationsversion implementiert, inspiriert durch *Conways Game of Life*. [@Wikipedia2019b] Im *Game of Life* ist der Zustand einer Zelle von den Zuständen der Nachbarzellen abhängig. 

Die Regeln lauten:

- Eine tote Zelle mit genau drei lebenden Nachbarn wird in der Folgegeneration neu geboren.
- Lebende Zellen mit weniger als zwei lebenden Nachbarn sterben in der Folgegeneration an Einsamkeit.
- Eine lebende Zelle mit zwei oder drei lebenden Nachbarn bleibt in der Folgegeneration am Leben.
- Lebende Zellen mit mehr als drei lebenden Nachbarn sterben in der Folgegeneration an Überbevölkerung.

Im organalen *Game of Life* werden alle Nachbarn betrachtet, daher auch Zellen die Quer zu der betrachteten Zelle platziert sind. Da in dieser Implementierung nur Felder als Nachbarn bezeichnet werden, wenn sie entweder direkt über, unter, links oder rechts vom betrachteten Feld liegen, werden die Grenzwerte entsprechend angepasst. Wände werden als lebendige Zellen angesehen, Böden als tote Zellen. 

Daraus ergeben sich folgende Regeln:

- Eine tote Zelle mit genau drei lebenden Nachbarn wird in der Folgegeneration neu geboren.
- Lebende Zellen mit weniger als ein lebenden Nachbarn sterben in der Folgegeneration an Einsamkeit.
- Eine lebende Zelle mit ein oder zwei lebenden Nachbarn bleibt in der Folgegeneration am Leben.
- Lebende Zellen mit mehr als zwei lebenden Nachbarn sterben in der Folgegeneration an Überbevölkerung.

So sollen Wände erzeugt werden welche, ähnlich zu Bäumen oder anderen Pflanzen, in das Level wachsen. 

### Raum Verfahren

Da der GA kaum zufriedenstellenden Raum und Flur ähnlichen Strukturen erzeugt, werden hier zwei Methoden präsentiert, welche die durch den GA erzeugten Level als Räume nutzen und eigenständig Verbindungen zwischen
diesem Herstellen.

#### Spelunky Style

In Abschnitt 2.3.3.2 wurde beschrieben wie das Spiel *Spelunky* eine Gitteranordnung nutzt um Level zu generieren. Der hier beschriebene Algorithmus, wandelt das Spelunky-Verfahren so um, das es für das PM-Dungeon verwendet werden kann. 

Genau wie bei Spelunky wird ein 4x4 Gitter, mit jeweils 10x9 großen Räumen erzeugt. Die Räume werden vom GA erzeugt und sind daher immer unterschiedlich, die aus Spelunky bekannten Layouts und Mutation sind nicht notwendig. Die Räume werden im Gitter verteilt und es wird, genau wie bei Spelunky, ein zufälliger Raum aus der ersten Reihe als Startpunkt ausgewählt. Der kritische Pfad wird mithilfe von Zufallsschritten erzeugt. Anders als bei Spelunky werden die Räume nicht, abhängig vom Pfad verlauf, nummeriert, stattdessen wird jeder Raum beim eintritt und Verlassen so verändert, dass ein Durchgang entsteht. Sind alle Räume des kritischen Pfades verbunden, werden alle anderen Räume zufällig an den Kritischen Pfad angeschlossen, Durchgänge werden entsprechend erzeugt.  

#### Reise zum Mittelpunkt 

Diese Methode platziert Räume zufällig im Level und verbindet diese durch einen Tunnel. Jeder Raum wird mit dem ihn nähst gelegenen unverbunden Raum verbunden, dabei wird der Abstand zwischen den beiden Raummittelpunkten gemessen. Im Gegensatz zum GA gibt die als Parameter übergebene Levelgröße nicht die wirkliche Levelgröße an, sondern die gesamte Größe der Räume. Dazu werden solange zufällig große Räume erzeugt, bis die gewünschte Größe erreicht ist. Die wirkliche Levelgröße wird deutlich größer sein, um gewährleisten zu können, dass alle zufällig erzeugten Räume im Level platziert werden können.

## Unterschied zu bekannten Verfahren

In Abschnitt 2.3.3 wurden verschiedene Verfahren zur prozeduralen Levelgenerierung vorgestellt. Das hier vorgestellte Konzept grenzt sich von diesen vor allem dadurch ab, dass neben der gewünschten Levelgröße keinerlei Informationen vom User notwendig sind, um Level zu generieren. Das in Abschnitt 2.3.3.3 verwendete Graphbased Verfahren benötigt neben den planaren Graphen auch vorgefertigten Räumen zur Generierung der Level. Für den in Abschnitt 2.3.3.2 beschriebenen Algorithmus aus dem Spiel Spelunky, werden neben Raumlayouts auch die 5x3 großen Chunks benötigt. Zusätzlich erzeugt das Spelunky-Verfahren eine wiedererkennbares Gittermuster. 

Das in Abschnitt 2.3.3.1 beschriebene Randomwalk-Verfahren erzeugt zwar jedes Mal unterschiedliche Level, diese sind aber fast vollständig Zufallsgeneriert. Ihre Lösbarkeit kann zwar garantiert werden, aber es kann kein Einfluss auf die Struktur der Level genommen werden. Das forcieren von Wand und Raum Strukturen ist nicht möglich. Durch die Fitnessfunktion wird das hier präsentierte Konzept dazu gedrängt, Räume und Flure zu erzeugen.
