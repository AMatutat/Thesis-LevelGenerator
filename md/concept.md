# Eigene Ideen, Konzepte, Methoden

## Konzept

### Anforderungen an das Projekt

Da die Teilnehmer neben den vorgegebenen Aufgaben auch selbständig Kreativ werden sollen, muss der Leve Generator möglichst flexibel sein und so zum Beispiel die verwendung von Sonderfeldern ermöglichen. Der in dieser Arbeit entwickelte Level Generator wird in der Lage sein, einfache 2D Level in kodierter Form zu erzeugen, welche dann mithilfe von einen Parser in die Dungeon-Implementierung eingeladen werden können. Der Generator soll in der Lage sein, anhand der implementierten Oberflächen, die benötige Texturenmap, des levels zu erzeugen.  Es wird die möglichkeit gegeben, Iselbst Implementierte tems sowie Monster im generierten Level zufällig zu verteilen. Es sollen Türen und die dazugehörenden Schlüssel so im Level verteilt werden, dass der Schlüssel erreicht werden kann ohne durch die Tür gehen zu müssen. Die Levelgröße wird nach belieben Auswählbar sein. 

Level werden statisch generiert, und können nicht zur selben zeit genutzt werden wie sie generiert werden. Es werden keine änderungen der Spielregeln ermöglicht und unterstützt, der Spieler kann sich nur auf die davor vorgesehenen Felder bewegen, änderugen wie das Sprengen von Wänden oder überspringen von Hindernissen wird nicht unterstützt. In einigen Aufgaben werden die Teilnehmer aufgefordert, sogeannte Quests (Aufgaben im Spiel) zu erstellen, der Level Generator wird keine Quests erzeugen können, es wird aber möglich sein, ein generiertes Level um Quests zu erweitern. Sowohl die eigenständige generierung von Texturen als auch die Skalierung von Texturgrößen ist nicht bestandteil dieser Arbeit. Der Level Generator wird nicht mit Fokus auf Laufzeit entwickelt. 

### Planung

Die Level werden mithilfe eines Genetischen Algorithemn generiert. Level bestehen aus, vom spieler begehbare, Böden und aus unbegehbare Wänden, sowie genau einen Ein und genau einen Ausgang. Damit ein Level spielbar ist, muss der Ausgang über Böden mit den Eingang verbunden sein. 

Das so erzeugte, kodierte Level, wird mithilfe eines Parsers in das, von den Studenten umgesetzte, PM-Dungeon integriert. Der Parser soll Monster, Items uns Spezialfelder verteilen können. Außerdem soll der Parser die Grafik des Levels generien, damit diese von den Studierenden verwendet werden kann.

### Genetische Algorithmen

Genetische Algorithmen (von nun an GA genannt) wurden von John Holland entwickelt. Sie gehören zur klasse der stochastischen Optimierungsverfahren ( (<https://de.wikipedia.org/wiki/Evolution%C3%A4rer_Algorithmus>)und kann mit einer gesteuerten Zufallssuche verglichen werden.

In GAs existiert eine Population an möglichen Lösungen zu einem Problem welche mithilfe von Rekombination und Mutation neue Kinder erzeugen, dies wiederholt sich über mehrere Generationen. Jeder Lösung wird ein Fitnesswert zugewiesen der die Warscheinlchkeit der weiterverbreitung beinflusst, angeleht an Dawins Surival of the Fittest theorie. Durch diese Verfahren erzeugen wir mit jeder Generation bessere Lösungen. 

Es wird eine Startpopulation mit n vielen zufällig erzeugten kodierten Individiumen erstellt. Jedes Individium repräsentiert dabei einen kodireten Lösungstring, wobei jedes Zeichen dabei eine Problemvariable represnetiert. Genetetische Algorithmen werden in der Regel zwar Binär Kodiert, es gibt aber auch Reelwertigkodierte Genetische Algoirthmen. 

Jedes Individium der Startpopulation wird dann mithilfe einer Fintessfunktion einen Rellwertigen Zahlenwert zugewiesen, der die güte des jeweiligen Indiviums repräsentiert. Die Fittnessfunktion muss je nach Problemstellung spezifiziert und implementiert werden. Mithilfe der Fittnesfunktion wird die Suche gesteuert. 

Mithilfe einer Selektionsfunktion werden jetzt einzelne Individume ausgewählt um eine neue Population zu erzeigen. Es gibt eine vielzahl an verschiedenen Selektionsfunktionen. 

Bei der Fitness Proportionate Selection hat jedes Individum die Chance Ausgewhält zu werden. Die Chance ist davon abhängig wie hoch der Fitnesswert des jeweiligen Individums im vergleich zu den anderen Indiviumen ist. Besonders gute Lösungen haben also eine hohe Chance ausgewählt zu werden, schlechtere Lösungen nur eine geringe Chance. Diese Art der Selektion kann vorallem dann genutzt werden, wenn die Gefahr besteht sich in einen Lokalen Maximum festzustecken. Fitness Proportinate Slection verfahren sind nur mit positiven Fintesswerten verwendbar. 

Die Tournemant Slection wählt zufällig k Indiviumen aus der Population aus, von diesen k Indvidiumen wird das jenige Ausgewählt welches den höchsten Fitness wert hat. Tournament Selection funktioniert auch mit negativen Fintesswerten. Im vergleich zu der Fitness Proportional Slection haben schlechte Lösungen eine geringe Chance ausgewählt zu werden, die schlechteste Lösung kann gar nicht ausgewählt werden.

Bei der Rank Selection wird die Population anhand ihrer Fitness sortiert, je höhr der Rank eines Individum desto höhr die Chance Ausgewählt zu werden. Im gegensatz zur Fitness Proportinale Selection ist die Auswahl nur passiv von der Fitness abhängig, da nach der austellung des Rankings, der Fintesswert keine bedeutung mehr hat. Rank Selection unterstützt auch negative Fintesswerte. Rank Selection wird meisten dann verwendet, wenn alle Lösungen einen ähnlichen Fitnesswert haben. 

Die Random Selection wählt zufällige Individume aus, diese Strategie verwendet keinerlei Filterfunktionen und wird daher vermieden.

Bevor ein Ausgewähltes Indivium teil der neunen Population wird, besteht die Chance einer Rekombination. Bei der Rekombination werden zwei Ausgewählten Individume, die Parents, mithilfe einer Crossoverfunktion so verändert das eine neue Lösung ensteht. 

Typische sind die One-Point oder Multi-Point Corssover verfahren. Hierbei werden die Indiviume an einer, oder im Falle des Multi-Point-Corssover an zwei, stellen zerschnitten. Mit den einzelnen Hälften werden nun zwei neue Individume, die Kinder, erzeugt, indem jeweils ein Teil des ersten Elternteils mit dem entpsrechenden Teil des zweiten Elternteils ausgetauscht wird. 

Ist gibt weitere Crossoverfahren, je nach Problemstellung kann auch eine eingens entwickeltes Verfahren zielführen sein. 

kommt es zu einer Rekombination, werden nicht die Eltern sondern die Kinder in die neue Population augenommen. Unabhängig davon, ob es zu einer Rekombination kommt, werden die Ausgewählten Individume der Startpopulation wieder in den Ausgangspool zurückgelegt und haben daher wieder die Chance ausgewählt zu werden. Für Genetische Algorithmen gilt, m viele Eltern erzeugen m Kinder 

Um die vielfalt der Population zu gewährleisten wirde eine Mutation implementiert. Mit einer gewissen Warscheinlchkeit verändert die Muation einzelne Gene jedes Individums. Die Veränderung kann vom zufälligen wechseln des Gen wertes, bis hin zum vertauschen der Genreihenfolge gehen. Im Regelfall ist die Muationschance der einzelnen Gene sehr gering ca pmut= 1/Anzahl der Gene 

Die Neuepopulation wird zur Startpopulation für den nächsten Durchgang, bis die Abbruchbeding erreicht ist. 

Genetische Algorithmen haben eine hand voll an Vorteilen im vergleich zu herkömmlichen Lösungen. So lassen sich Genetische Algoirhtmen besonders gut parrallel vewenden. Außerdem liefern sie bereits nach kurzer Zeit lösungen, die mit der Zeit immer besser werden, und liefern dabei nicht nur eine Lösung sondern eine Liste an möglichen Lösungen. 

<(https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_introduction.htm>)

### Vergleich mit anderen Lösungen

Das hier beschreibene Lösungskonzept benötigt keinerlei Daten vor den Benutzung, sowohl Layout als auch Inhalt der Level werden voll automatisch generiert, die Studierenden müssen jedeglich die gewünschte Level größe angeben. Durch die  Natur von GAs ist keine Generierung der Level in echtzeit möglich. das Konzept ermöglicht es, sowohl die gewünschte Anzahl an Level vor den eigentlichen Spielstart zu generieren, als auch mithilfe von Parrallerisierung, da sich GAs besonders gut dafür eigenen. 

### Wie soll das Konzept umgesetzt werden

#### Entwicklungsumgebung

Da das Modul "Programmier Methoden" die Programmiersprache Java verwendet, und daher die Studierenden ihr eigenes PM-Dungeon, welches den Level Generator verwenden wird, auch in Java implementieren werden, wird das hier Vorgestellte Konzept und die daraus folgende Implmentierung auch auf Java basieren. Es werden prinzipien der Objektorientierten Programmierung verwendet. 

Um die Level mithilfe eines GAs zu erzeugen, werden die oben beschriebenen Schrittes umgesetzt. Um best mögliche Ergebnisse zu erzielen werden verschiedene Fintess, Rekombinations und Mutationsverfahren implementiert. Nach der Entwicklung werden mithilfe von Testdaten die besten Verfahren und Parameter Einstellungen herausgefunden. Die vielversprechensten Ergebnisse werden mithilfe einer Umfrage bewertet.

#### Umsetzung des Genetischen Algorithmuses

Die Räume werden durch ein zwei Dimensionales Char Array repräsentiert, wobei jeder Char ein Feld im Raum darstellt, die indeze repräsentieren die x bzw. y Koordinate des Feldes im Raum, der Wert des Char gibt an ob das Feld eine Wand, ein Boden, der Eingang oder der Ausgang ist. 

Die Startpopulation wird erzeugt, indem jeden Feld zufällig entweder den Wert Boden oder Wand zugewiesen wird, wobei die Warscheinlichkeit Boden bzw. Wand zu werden unterschiedlich sein kann. Alle Ausenfelder werden zu Wänden, um ein Level abzugrenzen.  Es werden zufällig Start und Ende im Level platziert. 

##### Fitness

Um die Fitness der einzelnen Räume zu bestimmen, werden verschiedenen Faktoren berücksichtigt. 

Die erste implementierung der Fitnessfunktion berücksichtigt die Anzahl der erreichbaren Böden vom Startpunkt aus. Für jeden verbundenen Boden erhöht sich die Fitness. So sollen Böden vermieden werden, die von Wänden eingeschlossen werden und somit vom Spieler nicht genutzt werden kann. Um einzelne verteilte Wände im Raum zu vermeiden, erhöht sich für jede, mit der Ausenwand verbundene Wand, die Fitness. Dabei muss eine Wand nicht direkt mit der Ausenwand verbunden sein, es reicht wenn ein Nachbar (egal ob direkt oder indirekt) mit der Ausenwand verbunden ist. Ist eine Wand nicht mit einer Ausenwand verbunden, erhöht sich die Fitness dennoch ein wenig für jede Nachbar Wand des betrachteten Feldes, das erlaubt die Verteilung von Säulenstrukturen oder Trennwände im Raum. Als Nachbar zählt eine Wand dann, wenn sie entweder direkt über, direkt unter oder direkt unter den betrachtenden Feld platziert ist, schräge verbindungen Zählen nicht, da diese bei 2D-Pixel spielen einen unschönen und stufigen Anblick erzeugen. Ist das Level Ende vom Start erreichbar erhöht sich die Fitness stark. 

Die zweite Implementierte Fitnessfunktion erweitert die erste Fitnessfunktion indem sie für jede Wand prüft, ob diese Böden als Nachbar hat, ist dies der Fall wir die Fitness verringert. Damit sollen einzelnd abstehende Wandfelder, sowie einzelne in die Wand ragende Bodenfelder verhindern. Die Bestrafung sollte möglichst gering ausfallen, um weiterhin NIschen zu ermöglichen. Da durch die Bestrafungsmechanik eine negative Fitness möglich ist, müsste dies im Selekitonsverfahren beachtet werden. Da in dieser Implementierung die Chance auf einen negativen Wert sehr gering ist, spielt die Beachtung von Negativen Fitnesswerte keine große Rolle, deswegen wird eine minimum Fitness von 1 festgelegt. 

##### Selektion

Als Selektionverfahren wird die Roulett Wheel Selection implementiert. Die Roulett Wheel Selection gehört zu den Fitness Proportionate Selection. Dadurch wird gewährleistet, das besonders gute Lösungen eine sehr hohe Chance für die nächsten Generationen ausgewählt zu werden. Da, wie oben beschreiben, keine Negativen Fitnesswerte beschrieben werden müssen, und von einer großen Spannweite an Fitnesswerten auszugehen ist, ist von einer Rank Selection abzusehen. Da bei der Tournament Selction besonders schlechte Lösungen fast keine Chance haben zu überleben, und so die Gefahr steigt, in einen Lokalen Maxium stecken zu bleiben, ist auch dieses Verfahren für die Problemstellung ungeeignet. 

Beim der Roulett Whell Selection werden die einzelnen Individume auf einen Tortendiagramm abgebildet, wobei die größe von der Fitness des jeweiligen Indiviums abhängig ist. Ähnlich zu einen Glückrat wird ein Fixpunkt am Diagramm platziert, das Diagramm rotiert und das Indivum gewählt auf den der Fixpunkt zeigt.

##### Rekombination

Als Rekombinationsverfahren wird ein One-Point-Crossover verwendet. Dabei werden die Räume in exakt der Mitte auf X-Achse geschnitten und neu zusammengesetzt. 

Für die zweite Variante der Rekombination wird ein Multi-Point-Crossover verfahren verwendet. Es werden zufällig zwei schnitte auf der Y-Achse gemacht und das Mittelteil der beiden Räume ausgetauscht.   

##### Mutation

Als erstes Mutationsverfahren wird eine Variante des Bit-Flip Verfahren implementiert. Dabei wird jedes Feld, mit Ausnahmen der Ausenwände, mit einer Gewissen Warscheinlichkeit verändert. Wände werden  zu Böden und Böden zu Wände.

Beim zweiten Muttationsverfahren wird über jede Reihe des Raumes itteriert, sollte es zu einer Muation kommen, wir jede Wand die sich auf der linken hälfte Befindet, soweit nach links geschoben, bis sie eine Verbindung mit der Ausenwand hat, jede Wand auf der rechten Seite wird solange nach Recht geschoben bis es ebenfals eine Verbindug mit der Auswand hat. 

Dieses Mutationsverfahren muss später genauer evaluiert werden, da es direkten positiven Einfluss auf die Fitness des Raumes nimmt, und ggf. die Level gewinnen, die möglichst oft Mutiert wurden. Für dieses Mutationsverfahren spricht allerdings, dass durch die Zufällige reihenfolge der Anpassung der einzelnen Reihen, in verbindung mit der Rekombination, zwar hohe Fitnesswerte erreicht werden, aber auch optisch ansprechende Level erzeugt werden. (vermerk wo genau die evaluierung stattfindet)

Das dritte Mutationserfahren ist angelehnt an John Conyway´s Game of Life. Im Game of Life verändert sich eine Zelle je nachdem wie sein Umfeld aussieht. Eine Zelle wird entweder als lebendig oder tot eingestuft. Hieraus ergeben sich eine handvoll spielregeln

- Eine tote Zelle mit exakt drei lebendigen Nachbarn, wird in der folge Generation wieder lebendig
- Eine lebende Zelle mit zwei oder drei lebendigen Nachbarn, bleibt am leben
- Eine lebende Zelle mit weniger als zwei lebendigen Nachbarn, stirbt in der folge Generation
- Eine Zelle mit mehr als drei lebendigen Nachbarn, stirb an Überbevölkerung

Da im klassischen Game of Life auch schräge Nachbarn mitgezählt werden, müssen die Spielregeln für diese Implementation angepasst werden.

- Eine tote Zelle mit exakt zwei lebendigen Nachbarn, wird in der folge Generation wieder lebendig
- Eine lebende Zelle mit ein oder zwei lebendigen Nachbarn, bleibt am leben
- Eine lebende Zelle mit weniger als einen lebendigen Nachbarn, stirbt in der folge Generation
- Eine Zelle mit mehr als drei lebendigen Nachbarn, stirb an Überbevölkerung

Als Abbruchbedingung soll ein Fitnessschwellwert verwendet werden, sollte eine Lösung diesen überschreiten, ist ein ausreichend gutes Level gefunden und die suche kann beendet werden. Der Fitnesschwellwert ist Abhängig von der größe des Levels, den verhältniss von Wänden und Böden, sowie die gewichtung der einzelnen Fitnessfaktoren. Unter berücksichtung der Zufallsfaktoren ergibt sich folgende Formel, dessen Parameter durch Tests bestimmt werden müssen. *FORMEL EINFÜGEN* 

Sollte nach einer Gewissen Generationenanzahl keine verwendbare Lösung gefunden worden sein, ist davon auszugehen, das die Population sich in einen Lokalen Maximum festgesetzt hat und ein neustart wird ausgeführt. Der Generationen Grenzwert muss durch Tests bestimmt werden. 

#### Erweiterung um Räume und Flure

Um Level in verschiedene unteräume auzuteilen, ohne auf zufällige erzeugung, Raum ähnlicher Sturkturen zu hoffen, kann der Algorithmus erweitert werden. Hierzu werden die bisher erzeugten Level, als Raum interpretiert, zufällig im Level verteilt und mithilfe von Tunneln verbunden. Um Verbindungen zwischen den einzelnen Räume zu erzeugen, wird von jeden Raum aus, in unterschiedlicher Reihenfolge, jede Richtung nach angrenzenden Nachbar Räumen abgesucht, wird ein Raum gefunden, wird auf direkten Wege eine Verbindung hergestellt. Der so erzeugte Flur kann wiederum auch als Raum interpretiert werden und von anderen Räumen als anschlusspunkt genutzt werden. Es werden wieder zufällig Levelstart und Levelende platziert. Sollte ein Raum vom Start aus nicht erreichbar sein, weil sich in seiner Reichweite kein weiterer Raumbefindet, wird der nächste Verbunde Raum gesucht und eine Verbindung hergestellt. 

In dem so erzeugten Level können auch Türen und Schlüssel verteilt werden. Türen werden in Fluren platziert, Schlüssel werden im Level so verteilt, das sie erreichbar sind ohne die Tür zu passieren. 

Die Zusammensetzung der Level könnte auch mithilfe von GAs umgesetzt werden, wird in dieser Implementierung allerdings nicht gemacht. 

#### Integration in das PM-Dungeon

Ist das Level fertig generiert kann es mithilfe des Parsers aus seiner Kodierten Form in ein spielbares Level verwandelt werden. Dabei werden alle Felderreferenzen durch die jeweilgen Implementationen der Studenten ausgetauscht. Um Monster, Items und Spezialfelder zu verteilen wird das jeweilige Objekt zufällig, auf eine dafür vorgesehne Oberfläche platziert. Der Parser ist auch in der Lage, die grafik eines Levels zu generieren. 

Den Studenten wird eine anzahl an Interfaces gegeben, welche in ihren Programmen Integriert werden müssen, damit der Parser korekt arbeiten kann. Studenten werden auch in der Lage sein den Parser nach bedarf anzupassen bzw. zu erweitern. 

##### Diagramme

![UML Klassendiagramm \label{fig:UML}](figs/LG_UML.png){width=80%}

- beschreibung der einzelnen Methoden

Die Klasse CodedLevel represäntiert die einzelnen Indivume der Population und speichert das zwei dimensionale Char Array ab. Die Klasse LevelGenerator implementiert den GA. Sie enthällt die verschiedenen Implementierungen der Funktionen sowie steuert sie den ablauf der Generierung. Sie enthällt den Startpunkt welcher vom User benutzt wird um ein Level generieren zu lassen. Die Klasse Constants enthällt alle wichtigen Konsanten des Programmes. Die Klasse LevelParser enthällt die Implementierung des Parsers. 




## Anforderungen an gutes Level Design

