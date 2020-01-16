# Stand der Technik/Forschung, vergleichbare Arbeiten

<!--

*   Darstellung relevanter Ansätze aus der Praxis bzw. Forschung
*   Einordnung und Bewertung der Konzepte und Lösungen in Bezug auf die Ziele der Arbeit

Umfang: typisch ca. 15% ... 20% der Arbeit = 14 Seiten
-->

Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.



## Shattered Pixel Dungeon

Um ein Verständnis dafür zu bekommen, welche Art von Spiel in den vom Generator erzeugten Level gespielt wird, werden in diesen Abschnitt die Grundkonzepte von Computer Rollenspiele insbesondere des Subgenres Rouge-Like beschrieben. Danach wird das Spiel: Shattered Pixel Dungeon noch einmal genauer beleuchtet, da dieses Inspiration für das neue Praktikums Konzept ist. 

### Rollenspiele

Das Genre der Computer Rollenspiele entstand aus den klassischen Pen and Paper Rollenspiel. Der Spiele schlüpft in die Rolle einer oder mehrere Spielfiguren mit unterschiedlichsten Fähigkeiten um verschiedene Aufgaben zu erledigen, Erfahrung zu sammeln und Ausrüstung zu erbeuten. Durch die Inspiration klassischen Pen and Paper Spiele, spielen unterschiedliche Charakterwerte, sowie die werte der Ausrüstung eine entscheidende Rolle. Neben der Erfüllung der Aufgaben, um in der Geschichte weiterzukommen. ist essenzieller Bestandteil, die Charakterwerte durch Erfahrung aufzuwerten, neue Fähigkeiten zu erlernen und die Ausrüstung zu verbessern. <https://de.wikipedia.org/wiki/Computer-Rollenspiel>

Viele Spiele lassen den Spieler bereits zu beginn eine von mehreren Charakterklassen wählen. Die Klasse der Figur bestimmt meist, welche Ausrüstung sie benutzen kann und welche Fähigkeiten sie erlernen kann. Vor allem seid den großen Erfolg der Elder Scrolls Spiele, ermöglichen es allerdings immer mehr Spiele, die Klasse während des eigentlichen Spielens zu bestimmen, indem Fähigkeitspunkte frei verteilt werden können. So kann der Spieler auch verschiedene Klasse miteinander vermischen.

![Charakter auswahl zu beginn des Spiels Dragon Age 2 <https://www.gamestar.de/artikel/dragon-age-2-tipps-tricks-zum-rollenspiel,2321599.html](figs/da2char.jpg){width=80%}

![Fähigkeitenübersicht im Spiel Risen https://www.spieletipps.de/artikel/2018/1/ ](figs/Risen.jpg){width=80%}



Klassische Computer Rollenspiele führen den Spieler so durch eine handgebaute Welt und erzählen dabei ihre Geschichte. Viele Rollenspiele ermöglichen es den Spieler, direkten Einfluss auf den Ausgang der Geschichte zu nehmen. So kann der Spieler im Rollenspiel The Witcher 2: Assassins of Kings, im ersten von drei Akten eine Entscheidung treffen, die Einfluss darauf nimmt, von welcher Seite der im Spiel gezeigte Konflikt betrachtet wird. Auch dürfen weniger wichtige Entscheidungen vom Spieler übernommen werden, die zwar weniger Einfluss auf das große ganze nehmen, jedoch die Immersion verbessern.

Neben der Geschichte spielt der Kampf die zweite Große Rolle in Rollenspiele. Im laufe der Zeit haben sich zwei Kampfsysteme im Markt etabliert. Rundenbasierte Kampfsysteme bleiben der Pen and Paper Vorlage treu, und lassen Spieler und Monster nacheinander ihre Angriffe tätigen. In Actionbasierten Kamfpsystemen wird der Kampf in Echtzeit ausgetragen, hier spielen vor allem Treffervermögen und Ausweichtalent eine rolle. 

![Rundenbasierte Kampfszene aus Final Fantasy 7](figs/ff7.jpg){width=50%}

![Kampfszene aus den Actionrollenspiel Dark Souls](figs/darksoulskampof.jpg){width=50%}

### Rouge und Rouge-Like

<https://en.wikipedia.org/wiki/Rogue_(video_game)>

Das Adventure Rouge: Exploring the Dungoens of Doom, ist ein, in den 1980er entwickeltes, Videospiel. Der Spieler bewegt sich Rundenbasiert durch ein Levelsystem, den Dungeon, um am ende das Magische Amulett von Yendor zu erlangen. Auf den Weg dorthin gilt es die feindlichen Monster zu besiegen. 

![Ein typisches Level aus den Spiel Rouge](figs/rogue.jpg){width=80%}


<https://www.gamasutra.com/view/feature/4013/the_history_of_rogue_have__you_.php?print=1> <https://en.wikipedia.org/wiki/Rogue_(video_game)>

Besonders machte Rouge der damals unübliche Permanenten Tod, stirbt der Spieler verlor er seinen gesamten Fortschritt und musste von vorne beginnen, als auch die Tatsache, dass das Dungeon jedes mal zufällig neu generiert wurde. Rogue setzte als eines der ersten Spiele auf prozedurale Level Generierung um die Abwechslung und den Wiederspielwert zu steigern. In Abschnitt drei dieser Arbeit, wird genauer auf prozedurale Level Generierung eingegangen. 

Glenn R. Wichman schrieb in einen offenen Brief

"„But I think Rogue’s biggest contribution, and one that still stands out
to this day, is that the computer itself generated the adventure in Rogue.
Every time you played, you got a new adventure. That’s really what made
it so popular for all those years in the early eighties.“<http://www.digital-eel.com/deep/A_Brief_History_of_Rogue.htm>"

Da Rouge eines der ersten Spiele war die auf PCG setzten, waren sowohl Spieler als auch Entwickler von diesen Konzept begeistert. Wie Entwickler fühlten sich durch Inspiriert und entwickelten ihre eigenen Spiele mit PCG. <https://www.gamasutra.com/view/feature/4013/the_history_of_rogue_have__you_.php?print=1>

Heute bezeichnet man Spiele die das Spielprinziept des Permanenten Todes mit prozedural erzeugten Level kombiniert, als Rouge-Like. <https://en.wikipedia.org/wiki/Roguelike>

### Shattered Pixel Dungeon

"**Shattered Pixel Dungeon** is a Roguelike RPG, with pixel art graphics and lots of variety and replayability. Every game is unique, with four different playable characters, randomized levels and enemies, and over 150 items to collect and use. The game is simple to get into, but has lots of depth. Strategy is required if you want to win!" <https://pixeldungeon.fandom.com/wiki/Mod-Shattered_Pixel_Dungeon>

Zu beginn des Spiels wählt der Spieler eine der vier Charakterklassen aus. Zur Auswahl stehen:<https://pixeldungeon.fandom.com/wiki/Mod-Shattered_Pixel_Dungeon/Classes#Rogue>

- Krieger: Nahkämpfer mit Fokus auf hohe stärke und Lebensenergie
- Magier:Fernkämpfer der Zauber verwendet
- Schurke: Nahkämpfer der auf Planung und Hinterhalte fußt 
- Jäger: Fernkämpfer der vor allem Bögen verwendet

Der Spieler startet auf der obersten ebene eines Dungeons. Ziel ist es, möglichst tief in das Dungeon einzudringen, je tiefer der Spieler ist, desto schwieriger wird das Spiel. In den einzelnen Ebenen begegnen den Spieler die unterschiedlichsten Monster, durch dessen Tötung der Spieler Erfahrung sammelt, wurde genug Erfahrung gesammelt, steigt der Spieler auf, dies ermöglicht ihn, seine Spielfigur mit neuen Fähigkeiten auszustatten. Ebenso lassen besiegte Gegner Ausrüstungsgegenstände wie Heiltränke oder Waffen fallen. Um Erfolg im Spiel zu haben, sollte man also nicht nur versuchen möglichst schnell möglichst tief in das Dungeon einzudringen, sondern auch viele Gegner zu besiegen um an Erfahrung und Items zu gelangen. Das Spiel wird genau wie Rouge Rundenbasiert gespielt. 

Darüber hinaus bietet Shattered Pixel Dungeon noch viele weiter Features wie Subklassen oder Verzauberungen, welche hier nicht weiter beschrieben werden, da diese für das Verständnis des Spielablaufes nicht nötig sind.

![Beispielausschnitt für das Leveldesign in Shattered Pixel Dungeon <https://www.holarse-linuxgaming.de/wiki/pixel_dungeon>](figs/spdss.png){width=50%}

## Grundlagen des Leveldesign

### Was ist Leveldesign

- Definintion
- unterschied zu level art

### Regeln für gutes Leveldesign

1. Lösbarkeit

2. Immersion

3. Interaktionsvermögen

4. Pacing

5. Navigation

6. Schwierigkeit

7. Risk and Reward

8. Einzigartigkeit

9. Unterstützt Gameplay

   



<!--

https://books.google.de/books?hl=de&lr=&id=FLzOBgAAQBAJ&oi=fnd&pg=PR5&dq=genetische+algorithmen+vorteile+nachteile&ots=fz0g2GBudt&sig=zc9xi5ksVjbQf5KFfcrWm0xWg6g#v=onepage&q=genetische%20algorithmen%20vorteile%20nachteile&f=false(https://books.google.de/books?hl=de&lr=&id=FLzOBgAAQBAJ&oi=fnd&pg=PR5&dq=genetische+algorithmen+vorteile+nachteile&ots=fz0g2GBudt&sig=zc9xi5ksVjbQf5KFfcrWm0xWg6g#v=onepage&q=genetische algorithmen vorteile nachteile&f=false

<http://www.mathematik.tu-dortmund.de/papers/MehmetiAmreinKulmsMacinLoenserBogonosWaidhas2014.pdf>

<https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_introduction.htm>

-->



## Einführung in Genetische Algorithmen



### Herkunft

"Evolutionäre Algorithmen (EA) sind Optimierungsverfahren, die sich am Vorbild der biologischen Evolution orientieren."  <https://ls11-www.cs.tu-dortmund.de/lehre/SoSe03/PG431/Ausarbeitungen/GA_Selzam.pdf>

Vereinfacht ausgedrückt: Evolutionäre Algorithmen sind ein Verfahren zur kontrollierten und gesteuerten Zufalls suche. 

Es gibt vier, aus der Historie entstandenen unterscheidungsformen:

- Genetische Algorithmen (GA)
- Genetische Programmierung (GP)
- Evolutionsstrategien (ES)
- Evolutionäre Programmierung (EP)

GAs wurden Anfang der 60er Jahre von John Holland vorgestellt. [Holland, John: Adaptation in Natural and Artificial Systems; The University of Michigan Press, 1975] Zur selben Zeit entwickelte Hans-Paul-Schwefel und Ingo Rechenberg die Evolutionsstragien. [Rechenberg, Ingo: Evolutionsstrategie. Optimierung technischer Systeme
nach Prinzipien der biologischen Evolution; Frommann-Holzboog-Verlag, Stuttgart 1973].

Zwar gibt es zwischen den einzelnen Formen inhaltliche unterschiede, allerdings haben sich die Verfahren heutzutage so miteinander vermengt, das eine Unterscheidung in dieser Arbeit nicht weiter Zielführend ist. Im weiteren Verlauf wird von Genetischen Algorithmen gesprochen, auch dann wenn teils verfahren aus den Evolutionären Strategien besprochen oder angewandt werden.

- Genauer den unterschied erklären

### Grundbegriffe aus der Genetik

<https://ls11-www.cs.tu-dortmund.de/lehre/SoSe03/PG431/Ausarbeitungen/GA_Selzam.pdf>

Da Genetische Algorithmen sich an der Evolution orientieren, haben viele Fachbegriffe der Genetik ihren Weg in die Informatik gefunden. Im Folgenden werden die, für Genetische Algorithmen und diese Arbeit, relevanten Begriffe in ihrer Bedeutung inerhalb der Informatik, erläutert. 

**Individuum / Chromosom** 

"Ein Individuum im biologischen Sinne ist ein lebender Organismus, dessen
Erbinformationen in einer Menge von Chromosomen gespeichert ist. Im Zusammenhang mit genetischen Algorithmen werden die Begriffe Individuum und
Chromosom jedoch meistens gleichgesetzt." https://ls11-www.cs.tu-dortmund.de/lehre/SoSe03/PG431/Ausarbeitungen/GA_Selzam.pdf

**Gen**

Ein Gen ist genau eine Sequenz im Individuum. Je nach Kontext kann es sich hierbei um eine einzelne Stelle oder mehrere Stellen im Individuum handeln.

**Allel**

Allel beschreibt den exakten Wert eines Gens. 

**Population / Generation**

Einer Menge gleichartiger Individuen wird als Population bezeichnet. Die Anzahl der Individuen gibt die Populationsgröße an. Sterben Individuen oder werden neune geboren, verändert sich die Größe der Population. Betrachtet man eine Population über mehrerer Zeitpunkte, spricht man von Generationen. 

### Ablauf

GAs folgen einer Reihe an Subrutinen, die sich solange Wiederholen bis eine Abbruchbedingung erreicht ist. 

![Ablauf eines generischen GAs](figs/gaAblauf.png){width=100%}

Abbildung ... zeigt den zugrunde legenden Ablauf von GAs. 

1. Kodierte Startpopulation wird erzeugt
2. Aktuelle Generation wird bewertet
3. Es wird geprüft, ob die Abbruchbedingung erreicht ist
4. Wenn nein
   1. Es werden  Individuen für die neue Generation ausgewählt
   2. Manche der Ausgewählten Individuen werden miteinander Rekombiniert und erzeugen so neue Individuen
   3. Einige der Gene der neuen Generation werden Mutiert
   4. Die neue Generation wird zu Start Generation für den nächsten durchlauf
   5. Go to 2
5. Ausgabe der Lösung

Im folgenden werden die Einzelnen Subroutinen genauer beschrieben, sowie gängige Implementationen gezeigt. 

#### Kodierung

Zu beginn muss das betrachtete Problem kodiert werden. Das bedeutet das alle relevanten Aspekte auf ein Chromosom abgebildet werden müssen. Hierbei gibt es zwei Kodierungsverfahren, die je nach Problem ausgewählt werden müssen.

Bei der Binären Kodierung besteht ein Chromosom aus n vielen Genen. Jeden Gen wir ein binärer Wert zugewiesen und repräsentiert dabei eine Problemvariable. Aus den Genen wird dann ein Bitstring erzeugt. 

$$ g=(g_{ 0  }....g_{ m }) \in\left\{ 0,1 \right\}^{ m }  $$

Verwendet man die Binäre Kodierung, muss man am ende die Lösung mithilfe einer Dekodierungsfunktion zurück wandeln. 

$$ T: \left\{ 0,1 \right\}^{ m } \to R^{ n } $$

Eine andere Variante ist die reellwertige Kodierung. Sie funktioniert ähnlich zu der Binären Kodierung, nur wird hier jeden Gen ein reellwertige Wert zugewiesen. Eine Dekodierung ist nicht nötig. 

$$ g=(g_{ 0  }....g_{ m }) \in \left\{ R \right\}^{ m }  $$

Die Allel der Gene der Startpopulation werden zufällig bestimmt. 

#### Bewertung

Die Bewertung erfolgt mithilfe der sogenannten Fitnessfunktion, angelehnt an Darwins Survival of the Fittest. Die Fitnessfunktion bewertet die güte eines Individuums, also wie nahe es schon an einer möglichen Lösung ist. Dabei weißt die Fitnessfunktion den Individuum eine reelle Zahl zu, die die Fitness darstellt. Im Regelfall ist eine höhere Fitness besser als eine geringer. 

Die Implementation der Fitnessfunktion ist stark mit der eigentlichen Problemstellung verwoben. Da die Fitnessfunktion großen Einfluss darauf hat, in welche Richtung sich die Population entwickelt, sind die Bewertungskriterien so zu wählen, das sie zur Erreichung der Lösung beitragen. Da die Fitnessfunktion während der Laufzeit jedes einzelne Individuum jeder Population jeder Generation betrachtet, sollte bei der Implementierung auf Laufzeit Optimierung geachtet werden, eine komplexe Fitnessfunktion kann den gesamten GA verlangsamen. 

#### Selektion

- Fitnessdruck erklären

Bei der Selektion werden die Individuen ausgewählt, welche die nächste Generation bilden. Die gängigsten verfahren selektieren nach der Fitness der Individuen. Jedoch sollte man nicht nur die besten Individuen der Population auswählen, da nicht nachvollzogen werden kann, ob sich das Individuum in der nähe des globalen Hochpunkts befindet oder sich lediglich einen lokalen Hochpunkt nährt. Würde man nur die besten Individuen erlauben sich zu vermehren, würde sich die Population in eine Richtung festfahren. 

Es folgt eine Auflistung und Erklärung bekannter Selektionsverfahren. Bei jeden Verfahren werden die ausgewählten Individuen auch zurück in die Ursprungspopulation gelegt, es ist also möglich das selbe Individuum mehrfach auszuwählen. Es wird solange Ausgewählt bis die neue Population die gewünschte Größe erreicht hat, diese ist in der Regel genauso groß oder größer als die Ursprungspopulation. 

**Fitness Proportionate Selection** 

Bei der Fitness Proportionate Selection hat jedes Individuum die Chance ausgewählt zu werden. Die Chance ausgewählt zu werden ist abhängig von der FItness des Individuums. Besonders gute Lösungen haben also hohe Chance ausgewählt zu werden, schlechtere Lösungen können dennoch ausgewählt werden um so die vielfallt der Population zu gewährleisten. 

Eine gängige Art der Umsetzung dieses Verfahren ist die **Roulett Wheel Selection**. Angelehnt an Glücksräder, wird ein ein Rad in n Teile zerteilt, wobei n die Summe der Fitness der Population entspricht. Jedes Individuum der Population enthält entsprechend seiner Fitness Anteile am Rad. Am Rad wird ein Fix Punkt angesetzt, das Rad wird rotiert und das Individuum ausgewählt auf dessen Anteil der Fix Punkt stehenbleibt.

![Bildliche Darstellung der RWS  <https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_parent_selection.htm>](figs/roulette_wheel_selection.png){width=100%}

Das **Stochastic Universal Sampling** erweitert die Roulett Wheel Selection um einen zweiten Fixpunkt. So können zeitgleich zwei Individuen ausgewählt werden. 

Fitness Proportionate Selektionsverfahren funktionieren nicht in Fällen, in dem Fitnesswerte negativ sein können. 

**Tournament Selektion**

Bei der Tournamen Selektion werden zufällig k Individuen aus der Ursprungspopulation ausgewählt, das Individuum mit der höchsten Fitness wird in die nächste Generation aufgenommen. Dieses Verfahren ermöglicht zwar auch schlechteren Lösungen Ausgewählt zu werden, versichert aber das die schlechtesten k-1 Lösungen nicht ausgewählt werden können und die beste Lösung auf jeden Fall ausgewählt wird. Tournament Selektion funktioniert auch bei negativen Fitnesswerten. 
![Bildliche Darstellung der TS <https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_parent_selection.htm>](figs/tournament_selection.png){width=100%}

**Rank Selektion**

Bei der Rank Selektion wird die Population anhand der Fitnesswerte der Lösungen sortiert. Für die Auswahl spielt nicht mehr der Fitnesswert sondern die Platzierung der Lösung eine Rolle. Höher platzierte Lösungen haben eine höhere Chance ausgewählt zu werden als niedrig platzierte Lösungen. Der Chancenunterschied ist je nach Problemstellung zu wählen. Rank Selektion kann auch bei negativen Fitnesswerten verwendet werden. 

**Zufällige Selektion**

Bei der zufälligen Selektion werden zufällig Individuen aus der Population ausgewählt. Dieses Verfahren wird für gewöhnlich vermieden, da es keinerlei Filter Mechanismen gibt und die suche nicht gesteuert werden kann. 

#### Rekombination

Bei der Rekombination werden zwei Selektierte Lösungen neu zusammengesetzt. Die beiden Ursprünglichen Lösungen bezeichnet man als Eltern, die neu erzeugten Lösungen als Kinder. 

Ob zwei Eltern miteinander Rekombiniert werden, ist Abhängig von der festgelegten Crossoverchance. In GAs liegt diese für gewöhnlich bei $\approx$ 60%. Die Rekombination vermischt den Genpool der Eltern und soll so für eine möglichst diverse Population sorgen.

Es folgt eine Erläuterung einiger verbreitetet Crossoververfahren. Je nach Problemstellung kann auch ein Individuelles verfahren Zielführend sein. 

**Point Crossover**
Beim Point Crossover verfahren werden beide Eltern an einer oder mehrere Stellen in Segmente geteitl. Die Segmente der Eltern werden miteinander vertauscht um die Kinder zu erzeugen. 

![Bildliche Darstellung des Multi Point Crossoververfahren  https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_crossover.htm](figs/multi_point_crossover.png){width=100%}

**Uniform Crossover**

Beim Unform Crossover wird jedes Gen eines Elternteils betrachtet, es gibt eine 50% Chance dass das Gen mit dem entsprechenden Gegenstück des anderen Elternteils ausgetauscht wird. 

![Bildliche Darstellung des Uniform Crossoververfahren https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_crossover.htm](figs/uniform_crossover.png){width=100%}

#### Mutation

Die Mutation ist eine Zufällige Veränderung der Gene um eine neue Lösung zu erhalten. Genau wie die Rekombination wird sie genutzt um eine möglichst große Diversität der Population zu erzeugen. Durch Crossover erzeugte Kinder liegen im Suchbaum in der nähe der Eltern, durch die Mutation werden die Kinder von den Eltern weiter entfernt. 

Die Mutationschance pmut ist gering anzusetzen, da eine zu hohe Mutationschance den GA zu auf eine zufällige Suche reduzieren würde. 

Es folgt eine Auflistung und Beschreibung bekannter Mutationsverfahren. Je nach Problemstellung kann auch eine Spezifische Mutationsmethode Zielführend sein. Je nach Kontext kann ein einzelnes Gen oder eine Sequenz an Genen mutiert werden. Für die folgenden Verfahren muss man dann beachten, das eine Sequenz auch als Gen bezeichnet wird. Je nach Bedarf kann nur eine Mutation pro Lösung durchgeführt werden oder es kann jedes Gen mit einer Chance von pmut mutiert werden. 

**Bit Flip Mutation** 

Bei der Bit Flip Mutation wird ein oder mehrere bits gewechselt. Bit Flip Mutation kann daher nur bei Binär Kodierten GAs verwendet werden. Abwandlungen für Reellwertige Kodierungen sind aber möglich. 

**Swap Mutation**

Bei der Swap Mutation werden zwei Zufällig ausgewählte Gene miteinander vertauscht. 

**Scramble Mutation**

Bei der Scramble Mutation wird eine Auswahl an zusammenhängenden Genen vermischt um so die Reihenfolge zu ändern. 

**Inversion Mutation**

Bei der Scramble Mutation wird die Reihenfolge einer Auswahl an zusammenhängenden Genen umgedreht. Das erste Gen in der Sequenz wird zum letzten und umgedreht. 

#### Abbruchbedingung

Die Abbruchbedingung ist für gewöhnlich dann erreicht, wenn eine gültige Lösung gefunden wurde. Je nach Problemstellung kann es auch das überschreiten eines gewissen Fitnessschwellwertes sein oder der durchlauf einer bestimmten Generationenanazahl. Es bietet sich an einen Neustart des GAs vorzunehmen, sollte nach einer gewissen Generationsanzahl keine Lösung gefunden worden sein. Der entsprechende Schwellwert sollte so gewählt werden, dass Erfahrungsgemäß keine Steigerung der Fitness zu erwarten ist und ist dementsprechend für jede Implementierung unterschiedlich. 

### Vor und Nachteile

| Pro                                                         | Contra                                                       |
| ----------------------------------------------------------- | ------------------------------------------------------------ |
| Gut geeignet für Probleme in großen Suchräumen              | Laufzeit in kleinen Suchräumen oft länger als andere Verfahren |
| Benötigt keine abgeleiteten Informationen des Problems      | Laufzeit stark von der Komplexität der Fitnessfunktion abhängig |
| Lässt sich gut parallelisieren                              | Lösungen werden Zufällig gefunden, es gibt keine Garantie die beste Lösung zu finden. |
| Liefert eine Menge an guten Lösungen, nicht nur eine Lösung |                                                              |
| Verläuft sich nicht in lokalen Hochpunkten                  |                                                              |




### Anwengunsbeispiele

<http://wwwisg.cs.uni-magdeburg.de/sim/vilab/2007/papers/12_genetisch_sharbich.pdf>

<https://www.degruyter.com/view/j/auto.1995.43.issue-3/auto.1995.43.3.110/auto.1995.43.3.110.xml>

GAs werden beispielsweise genutzt um das Profil eines Flügels und die Form des Rumpfes von Flugzeugen zu optimieren. Beim Brückenbau lassen sich Positionierung und Gewicht einzelner Bauteile optimieren. GAs werden auch für Schwellwertanpassung bei verschieden Algorithmen und Neuronalen Netzten genutzt. Sie können auch für Scheduling-Probleme verwendet werden um so zum Beispiel Stundenpläne in Schulen zu gestalten oder Routenplanung für Paketdienste. Im allgemeinen können GAs genutzt werden um NP-Schwere Probleme zu lösen. 