# Eigene Ideen, Konzepte, Methoden

Im folgenden wird das erste Konzept zur Erstellung eines Level Generators basierend auf GAs. erläutert. Zuerst werden die Anforderungen an das Projekt definiert und die Zielsetzung spezifiziert. 

## Anforderungen an das Projekt

Der Level Generator muss in der Programmiersprache Java geschrieben sein, da diese die durch die Prüfungsordnung festgelegte Modul Sprache ist. Der Levelgenerator muss lösbare 2D Level generieren. Level bestehen aus unterschiedlichen Räumen die mit Fluren verbunden sind. Als Lösbar gilt ein Level dann, wenn vom Startpunkt der Endpunkt aus erreicht werden kann. Die Größe des Levels soll durch den User aus wählbar sein. Um das Level grafisch darstellen zu können, muss eine Levelgrafik erzeugt werden. Im Level müssen Monster und Items von den Usern platziert werden können. Im Level sollen Türen und dazugehörige Schlüssel so platziert werden, das kein Softlock entsteht. Der Level Generator soll den Teilnehmer möglichst viel Freiheit in der Umsetzung der Spielinhalte, abseits der Level, gewähren. Die Teilnehmer sollen in der Lage sein, neben den in den Aufgaben vordefinierten Elemente, eigen konzeptionierte Elemente in die Level zu integrieren. Grundlegende Level müssen erzeugt werden können, ohne Informationen über Layout oder Inhalt zu bekommen. Die Teilnehmer müssen in der Lage sein, die erzeugten Level in ihre Implementation der Spiellogik zu verwenden. 

Den Generator sollen möglichst wenig Informationen über die Regeln guten Leveldesigns gegeben werden, damit in der abschließenden Evaluierung geschaut werden kann, wie viele Regeln passiv beachtet wurden.  

Der Generator muss nicht in der Lage sein, neue Spielregeln, wie das Sprengen von Wänden durch Bomben, in den Generierungsprozess zu beachten. Der Generator muss Texturen nicht selbständig erzeugen oder  die Größenverhältnisse übergebener Texturen anpassen. Der Generator muss keine Quests oder Boss Gegner erzeugen. Der Generator muss keine Level in Echtzeit erzeugen können. Es wird kein Fokus auf die Optimierung der Laufzeit gelegt. 

## Konzept

Der Generator wird aus zwei Teilen zusammensetzt. Der erste Teil ist der GA selbst, er generiert Level die aus Wänden, Böden einen Start und einen Ziel bestehen. Der zweite Teil ist ein Parser, der für die Integration der erzeugten Level in die Spiellogik zuständig ist. Der Parser ist auch dafür verantwortlich, das Monster und Items im Level platziert werden, er  soll die Möglichkeit bieten, einzelne Wände und Böden gegen andere Oberflächen auszutauschen. Er ist für die Generierung der Levelgrafik verantwortlich. 

Alle Konstanten werden von der Klasse Constants als statische Variablen zur Verfügung gestellt. Um Konstanten in der weiteren Beschreibung des Konzeptes darzustellen, werden alle verwendeten Konstanten in Großbuchstaben geschrieben. Die Werte der Konstanten werden erst durch praktische Tests festgelegt werden können. 

### GA

#### Kodierung

Die Kodierung der Level passt sich der vorgesehenen Implementation in die Spielogik an. Ein Level kann als zwei Dimensionales Array verstanden werden, die einzelnen Felder im Array sind Felder im Level. Der Spieler bewegt sich pro Zug genau um ein Feld. 

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

Eine Instanz der Klasse CodedLevel ist ein Chromosom, also eine mögliche Lösung bzw. ein Level. Neben den Char Array welches den Levelaufbau entspricht und Informationen über die Größe des Levels, besitzen CodedLevel eine Fitness welche die Güte der Lösung angibt sowie Informationen über den Standort der Start- bzw. Endpunktes.  

![UML CodedLevel. Eigene Grafik](figs/codedLevel.PNG){width=100%}

Neben Getter und Setter verfügt die Klasse über die changeField Methode. Diese verändert den Allel eines Gens auf den übergebenen Wert. Zwar könnte diese Änderung auch direkt am Array vorgenommen werden, dann würden allerdings Änderungen an der Position der Start und Ausgänge evtl. verloren gehen. Sollte ein Start  bzw. Ausgang gesetzt werden, obwohl schon einer Vorhanden ist, wird stattdessen ein Boden gesetzt, ist keiner vorhanden werden die Koordinaten dem entsprechenden Attribut zugewiesen.  

#### LevelGenerator

Die Klasse Level Generator beinhaltet die Implementation des GA. Sie beinhaltet die Implementationen aller Subroutinen. Sie verfügt über die Methode generateLevel, welche als Einstiegspunkt in den GA gesehen werden kann. Ihr werden die gewünschte Level Größe übergeben und dann kümmert Sie sich um die Durchführung des GA Ablaufes. 

##### Erzeugen der Startpopulation

Um die Startpopulation zu erzeugen werden die Level zufällig mit Oberflächen gefüllt. Die CHANCE_TO_BE_FLOOR gibt an, mit welche Chance eine Oberfläche ein Boden wird. Alle Außenfelder des Levels werden mit Wänden gefüllt, um einen Level Abschluss darzustellen. Ein und Ausgang werden zufällig auf Böden gesetzt.

##### Fitnessfunktion

Die Fitnessfunktion bewertet die Level. Die Bewertungskriterien sollen dabei helfen, dass das Level in größere Bodenflächen, den Räumen verbunden durch Wandketten, den Fluren. 

Jedes Bewertungskriterium hat dabei eine andere Wertigkeit. Die Bewertungskriterien sind

1. Wie viele Böden sind begehbar? 
2. Ist das Level lösbar?
3. Wie viele Wände sind Verbunden? 

Die Anzahl der begehbaren Böden ist gleichzusetzten mit der Spielfläche und der daraus folgenden Interaktion mit den Level. Sind Böden nicht erreichbar, weil sie von Wänden eingeschlossen sind, können diese Flächen nicht bespielt werden und der restliche Dungeon wird kleiner. Dadurch sollte die Erstellung von Raumflächen begünstigt werden. 

Die Lösbarkeit ist eines Levels ist Kernvoraussetzung um als gültige Lösung zu gelten. Daher nimmt dieses Kriterium großen Einfluss auf die Fitness. 

Das dritte Kriterium soll vor allem einzeln im Level platzierte Wände vermeiden, da diese in der Logik des Spiels keinen Sinn erfüllen und daher vom Spieler als störend empfunden werden und die Immersion mindern. Für jede Wand die direkt oder indirekt über Nachbarn mit der außen Wand verbunden sind gibt es Fitnesspunkte. Dadurch sollen Wandketten gefördert werden. Die Verbindung mit den Außenwänden ist vom klassischen Hausaufbau inspiriert, da dort in der Regel auch jede Wand in irgendeiner Form mit der Außenwand verbunden ist. Da die hier generierten Level auch größere Dungeon darstellen sollen, könnten auch Säulenartige Strukturen oder Stützwände wünschenswert sein. Daher gibt es für Wände, die zwar keine Anbindung an den Levelrand haben, jedoch mit anderen Wänden verbunden sind, Teilpunkte. 

##### Selektion und Rekombination

Als Selektionsverfahren wird das im Abschnitt .... beschreibende Roulette Wheel Selection Verfahren genutzt. Da keine negative Fitness erreicht werden kann als auch von einer großen Spannbreite an Bewertungen ausgegangen werden kann, bietet sich ein Rank Selection Verfahren nicht an. Alternativ wäre auch die Verwendung der Tournament Selektion denkbar. 

Sollte die es zu einen Crossover, abhängig von der CHANCE_FOR_CROSSOVER, kommen, werden beide Eltern mithilfe des One-Point-Crossover rekombiniert. Dieses Verfahren hat eine geringer Chance, gute Level Strukturen zu zerstören, da nur ein großer und nicht viele kleine Eingriffe am Level durchgeführt werden. 

Das Uniform Crossover Verfahren würde wieder eine komplett Zufällige Anordnung von Böden und Wänden zu folge ziehen und ist daher für die Generierung von Leveln nicht geeignet. 

##### Mutation

Zur Levelgenerierung bieten sich fast alle bekannten Mutationsverfahren an. In dieser Implementierung wird eine angepasste Version der Bit-Flip Mutation verwendet. Ignorieren wir bei der Mutation Start und Ausgangspunkt, bleiben noch Felder die entweder Böden oder Wände sind. Es wird für jedes Gen überprüft ob es zur Mutation kommt, und wenn ja, wird der Allel des Gen geändert. Wände werden zu Böden und Böden zu Wände. 

#### Abbruchkriterium

Die GA wird dann beendet, wenn ein lösbares Level den Fitnessschwellwert überschreitet. Der Fitnessschwellwert ergibt sich aus der maximal Erreichbaren Fitness. Da die maximal erreichbare Fitness aufgrund von Zufallsfaktoren nicht exakt bestimmt werden kann, wird sich ihr angenähert:

$$ AnzahlBöden \approx \text{ CHANCE_TO_BE_FLOOR } * \Sum{  }{  }{  }Felder \linebreak
AnzahlWände \approx \text{ 1-CHANCE_TO_BE_FLOOR } * \Sum{  }{  }{  }Felder \linebreak
MaxFitness\approx \text{PUNKTE_FÜR_ERREICHBARKEIT} * \text{ AnzahlBöden } + \text{PUNKTE_FÜR_VERBUNDEN} * \text{ AnzahlWände } + \text{ PUNKTE_FÜR_LÖSBAR } $$

Um Zufallswerte auszugleichen, wird der Schwellwert unter den berechneten Wert angesiedelt. 


### Parser

