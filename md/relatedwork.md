# Prozedurale Content Generierung



## Shattered Pixel Dungeon

Um ein Verständnis dafür zu bekommen, welche Art von Spiel in den vom Generator erzeugten Level gespielt wird, werden in diesen Abschnitt die Grundkonzepte von Computer Rollenspiele insbesondere des Subgenres Rogue-Like beschrieben. Danach wird das Spiel: Shattered Pixel Dungeon noch einmal genauer beleuchtet, da dieses Inspiration für das neue Praktikums Konzept ist. 

### Rollenspiele

Das Genre der Computer Rollenspiele entstand aus den klassischen Pen and Paper Rollenspiel. Der Spieler schlüpft in die Rolle einer oder mehrere Spielfiguren mit unterschiedlichsten Fähigkeiten. Im Fokus des Spielerlebnisses steht die Erzählung einer Geschichte und das eintauchen in die Spielwelt. Aber auch die Verbesserung der Spielfigur und ihrer Ausrüstung nehmen einen großen Bestandteil der Spielerfahrung ein. Viele Spiele lassen den Spieler bereits zu beginn eine von mehreren Charakterklassen wählen. Die Klasse der Figur bestimmt, welche Ausrüstung sie benutzen kann und welche Fähigkeiten erlernt werden können. So werden unterschiedliche Spielstiele erschaffen, ein Zauberer spielt sich spürbar anders als ein Krieger. Im Vordergrund des Gameplay stehen das Lösen von Rätseln, führen von Dialogen und bekämpfen von Feinden. [@Wikipedia2019] [@Wikipedia2018]

Viele Rollenspiele ermöglichen es den Spieler  direkten Einfluss auf den verlauf der Geschichte zu nehmen. So muss sich der Spieler in The Witcher 2: Assassins of Kings, im ersten von drei Akten, für eine von zwei Seiten entscheiden. Die Wahl nimmt Einfluss darauf, von welcher Seite der im Spiel gezeigte Konflikt betrachtet wird.[@Graf2011] Auch dürfen weniger wichtige Entscheidungen vom Spieler übernommen werden, die zwar weniger Einfluss auf das große ganze nehmen, jedoch die Immersion steigern können.

In der Entwicklungszeit der Rollenspiele haben sich vor allem zwei unterschiedliche Kampfsysteme hervorgetan. Rundenbasierte Systeme übernehmen das aus den Pen and Paper bekannte Runden System, in dem sich Spieler und Gegner jeweils Abwechselnd eine Kampaction ausführen, bis einer von beiden besiegt wurde. Actionbasierte Systeme laufen vor allem in Echtzeit ab, hier stehen vor allem schnelle Reaktionen und gute Reflexe im Vordergrund. Echtzeitbasierte Rollenspiele werden oft auch als Action-Rollenspiel oder Action-Adventure bezeichnet, um sie von klassischen Rollenspielen abzugrenzen. [@Wikipedia2019]

### Rogue

Das Videospiel Rogue: Exploring the Dungoens of Doom, ist ein, in den 1980er entwickeltes, Dungeoncrawler. Der Spieler bewegt sich Rundenbasiert durch ein aus ASCI-Zeichen bestehenden Levelsystem (vgl. Abbidlung ..) , um am ende das Magische Amulett von Yendor zu erlangen. Auf den Weg dorthin gilt es die feindlichen Monster zu besiegen. [@Barton2009] ![Ein typisches Level aus den Spiel Rogue [@Barton2009]](figs/rogue.jpg){width=80%}

Glenn R. Wichman, einer der Entwickler von Rogue, schrieb in einen offenen Brief

„But I think Rogue’s biggest contribution, and one that still stands out
to this day, is that the computer itself generated the adventure in Rogue.
Every time you played, you got a new adventure. That’s really what made
it so popular for all those years in the early eighties.“[@Wichman1997]

Die Level von Rogue werden bei jeden Spielstart neu generiert, man spielt also niemals zweimal das exakt selbe Spiel. In Verbindung mit Permadeath wurde so ein Abwechslungsreiches Spiel mit hohem Wiederspielwert erschaffen. Permadeath beschreibt ein Spielprinzip bei dem der Spielertot zum permanenten Verlust des Fortschritts führt und das Spiel von vorne gestartet werden muss.

### Roguelikes

Da Rogue eines der ersten Spiele war die auf PCG setzten, waren sowohl Spieler als auch Entwickler von diesen Konzept begeistert. Viele Entwickler fühlten sich durch Rogue Inspiriert und entwickelten ihre eigenen Spiele mit PCG.[@Barton2009] Es entwickelte sich das Genre der Rouglikes. 

„[...] Roguelikes are called Rouglikes, because the games are literally like Rogue [...]“ [@Brown2017]

2008 wurde auf der Internationale Rouglike Entwickler Konferenz eine Liste verschiedener Faktoren erstellt, welche dabei helfen sollen das Rouglike Genre genauer zu beschreiben und zu definieren wann ein Spiel *like Rogue* ist. Diese List wurde später unter den Namen *Berliner Interpretation* bekannt. Folgende Faktoren wurden dabei herausgearbeitet: [@Conference2008]

| High value factors            | Erläuterung                                                  |
| ----------------------------- | ------------------------------------------------------------ |
| Random environment generation | The game world is randomly generated in a way that increases replayability. Appearance and placement of items is random. Appearance of monsters is fixed, their placement is random. Fixed content (plots or puzzles or vaults) removes randomness. |
| Permadeath                    | You are not expected to win the game with your first character. You start over from the first level when you die. (It is possible to save games but the savefile is deleted upon loading.) The random environment makes this enjoyable rather than punishing. |
| Turn-based                    | Each command corresponds to a single action/movement. The game is not sensitive to time, you can take your time to choose your action. |
| Grid-based                    | The world is represented by a uniform grid of tiles. Monsters (and the player) take up one tile, regardless of size. |
| Non-modal                     | Movement, battle and other actions take place in the same mode. Every action should be available at any point of the game. Violations to this are ADOM's overworld or Angband's and Crawl's shops. |
| Complexity                    | The game has enough complexity to allow several solutions to common goals. This is obtained by providing enough item/monster and item/item interactions and is strongly connected to having just one mode. |
| Resource management           | You have to manage your limited resources (e.g. food, healing potions) and find uses for the resources you receive. |
| Hack'n'slash                  | Even though there can be much more to the game, killing lots of monsters is a very important part of a roguelike. The game is player-vs-world: there are no monster/monster relations (like enmities, or diplomacy). |
| Exploration and discovery     | The game requires careful exploration of the dungeon levels and discovery of the usage of unidentified items. This has to be done anew every time the player starts a new game. |

| Low value factors               | Erläuterung                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| Single player character         | The player controls a single character. The game is player-centric, the world is viewed through that one character and that character's death is the end of the game. |
| Monsters are similar to players | Rules that apply to the player apply to monsters as well. They have inventories, equipment, use items, cast spells etc. |
| Tactical challenge              | You have to learn about the tactics before you can make any significant progress. This process repeats itself, i.e. early game knowledge is not enough to beat the late game. (Due to random environments and permanent death, roguelikes are challenging to new players.) The game's focus is on providing tactical challenges (as opposed to strategically working on the big picture, or solving puzzles). |
| ASCII display                   | The traditional display for roguelikes is to represent the tiled world by ASCII characters. |
| Dungeons                        | Roguelikes contain dungeons, such as levels composed of rooms and corridors. |
| Numbers                         | The numbers used to describe the character (hit points, attributes etc.) are deliberately shown. |

Die Berliner Interpretation wurde über die Jahre immer wieder kritisiert. Darren Grey kritisierte in seinen Blog Beitrag *Screw the Berliner Interpretation!* den "downright nonsense"[@Grey2013] der meisten Faktoren und ist der Auffassung die Berliner Interpretation schadete der kreativen Weiterentwicklung des Genres.[@Grey2013]

Im laufe der Zeit hat die Berliner Interpretation immer mehr an Bedeutung verloren. Heute versteht man unter den Genre der Roguelikes Spiele die zufällig generierte Level mit Permadeath kombinieren. [@Brown2019] Dazu gehören unter anderen Spiele wie der Plattformer *Spelunky*, das Survival Spiel *Dont‘t Starve* oder das Action-Adventure *The Binding of Isaac*.[@Wikipedia2020]


### Shattered Pixel Dungeon

Shattered Pixel Dungeon wäre laut der Berliner Interpretation ein echtes Roguelike. Es erfüllt alle Bedingungen, bis auf die ASCII Darstellung, der Definition. 

Zu beginn des Spiels wählt der Spieler eine der vier Charakterklassen aus. Zur Auswahl stehen:

- Krieger: Nahkämpfer mit Fokus auf hohe stärke und Lebensenergie
- Magier:Fernkämpfer der Zauber verwendet
- Schurke: Nahkämpfer der auf Planung und Hinterhalte fußt 
- Jäger: Fernkämpfer der vor allem Bögen verwendet

[@Pixeldungeon.fandom2019]

Der Spieler startet auf der obersten ebene eines Dungeons. Ziel ist es, möglichst tief in das Dungeon einzudringen, je tiefer der Spieler gelant desto schwieriger wird das Spiel. In den einzelnen Ebenen begegnen den Spieler die unterschiedlichsten Monster, durch dessen Tötung der Spieler Erfahrung sammelt, wurde genug Erfahrung gesammelt, steigt der Spieler auf, dies ermöglicht ihn, seine Spielfigur mit neuen Fähigkeiten auszustatten. Ebenso lassen besiegte Gegner Ausrüstungsgegenstände wie Heiltränke oder Waffen fallen. Um Erfolg im Spiel zu haben, sollte man also nicht nur versuchen möglichst schnell möglichst tief in das Dungeon einzudringen, sondern auch viele Gegner zu besiegen um an Erfahrung und Items zu gelangen.

Darüber hinaus bietet Shattered Pixel Dungeon noch viele weiter Features wie Subklassen oder Verzauberungen, welche hier nicht weiter beschrieben werden, da diese für das Verständnis des Spielablaufes nicht nötig sind.

![Beispielausschnitt für das Leveldesign in Shattered Pixel Dungeon [@Meldrian2015]](figs/spdss.png){width=50%}

Abbildung ... zeigt einen Ausschnitt eines Dungeons aus dem Spiel. Besonders gut zu erkennen sind die Zufällig Verteilten Räume verbunden durch Flure sowie der *Fog of War* welcher die Monster außerhalb der Reichweite der Spielfigur unsichtbar macht. 

## Grundlagen Leveldesign

### Was ist Leveldesign

- Definintion

- unterschied zu level art


### Regeln für gutes Leveldesign


#### Lösbarkeit und Fehlerfreiheit
Ein level muss immer lösbar sein. Je nach Spiel und Genre bedeutet dies, das entweder das Ende des Levels erreichbar sein muss oder alle Missionen im Level absolvierbar sein müssen. Das selbe gilt für alle Nebenmissionen und sammelbare Objekte wie Münzen oder Items im Level. Ist ein Level durch schlechtes Design oder Bugs nicht lösbar, zerstört dies den Spielspaß des Spielers. 

Auch sollten Level immer so Fehlerfrei wie möglich sein. Das betrifft sowohl grafische Fehler, wie falsch platzierte Texturen, als auch gameplay Fehler. Typische Beispiele für Gameplay Fehler wären z.B die, vom Designer ungewollte, Möglichkeit ein Hinderniss zu umlaufen, falsch platzierte Gegner oder andere, vom Level abhängigen, Designfehler. Einzelne Fehler werden von Spielern oftmals hingenommen, summiert sich die anzahl der Fehler aber auf, oder schränken diese den Spielgenuss ein, kann dies ähnliche Auswirkungen haben wie ein Unlösbares Level. 

#### Gameplay First

- Hitman
- Interaktionsvermögen

#### Immersion

"Immersion trägt maßgeblich dazu bei, dass Spieler ihre Zweifel beiseitelegen und gänzlich in die Spielwelt eintauchen"[@Hagen2019] 

Immersion beschreibt die Glaubwürdigkeit der Spielwelt. Dabei kommt es nicht darauf an, wie realistisch ein Spiel ist und wie gut die reale Welt abgebildet wird, sondern darum das ein Spiel und dessen Welt eine gewisse Kontinuität den selbst erstellten Regeln aufweist. Das erschaffen eines Immersiven Spielwelt  ist nicht nur Aufgabe des Level Designers, es zieht sich durch sämtliche Bereiche der Entwicklung. 

Im Gameplay kann Immersion durch Konsequenz erreicht werden. Einmal aufgestellte Regeln sollten immer gelten, kann zum Beispiel ein Licht im Spiel ausgeschossen werden sollten alle Lichter im Spiel ausgeschossen werden können. Gegenstände sollte immer den selben Nutzen haben, kann zum Beispiel eine Zange genutzt werden um Nägel aus Bretter zu ziehen, sollte der Spieler dies auch mit der nächsten Zange an einen anderen Brett tun können. Außerdem sollte der Spieler immer in der Lage sein, sein Wissen über die Regeln der Spielwelt nutzen zu können um Aufgaben so zu lösen wie er möchte. 

Um im Leveldesign eine hohe Immersion zu erreichen, müssen vor allem die Platzierung von Gegenständen und die Auswahl des Optischenthemas sinnvoll gewählt sein. Jedes Gebäude, jeder Gegenstand und jeder Character sollte einen, in der Spielwelt nachvollziehbaren, Grund haben genau dort zu sein wo er ist. Auch sollten vom Spieler erwartete Gegenstände und Orte Vorhände sein, so braucht ein Büro Schreibtische und Computer, eine Resteraunt Toiletten und einen Lagerraum für Lebensmittel. 

Das Spielegenre *Immersive Simulation* zeichnet sich dadurch aus, dass ein besonders hoher Fokus auf die Immersion gelegt wird. [@Brown2016] Warren Spector hält immersives Gameplay für den entscheidenden Faktor, der Videospiele von allen anderen Medien unterscheidet. 

"Simulations allow players to explore not just a space but a 'possibility space'. They can make their own fun, tell their own stories, solve problems the way they want, and see the consequences of their choices. That's the thing that games can do that no other medium in human hisotry has been able to do."[@Spector2016]

#### Pacing

#### Storytelling und Emotionen

- Fort Frolic?

#### Navigation

- Light
- Farben
- Doted Lines
- Nathan Drake dont need a compass

#### Balancing
- Level bedignt
  - Mario Level
  - Half Life verteilt mehr Leben wenn man Low ist
- Gameplay bedingt
  - RE4 
  - Thief -> Höherer Schwierigkeitsgrad = mehr Quests
  - Tomb Raider hat für unterschiedliche Aspekte unterschiedliche Schwierigkeitsgrade

#### Risk and Reward

Im laufe eines Spiels (und Levels), sollte es regelmäßig zu Risk and Reward Situationen kommen. Dies sind Situationen in denen der Spieler ein höheres Risiko eingehen muss als gewohnt, z.B ein besonders starker Gegner angreifen oder durch ein stark Bewachtes gebiet schleichen, um bei Erfolg eine Belohnung zu erhalten. Die Belohnung sollte abhängig von der höhe des Risikos der Herausforderung sein. Risk and Reward Situationen sollten stets optional sein und sind für besonders gute Spieler gedacht, um diese eine neue Herausforderung zu bieten und entsprechend zu belohnen. Die Art der Belohnung schwankt von Spiel zu Spiel und kann von Punkten für den Highscore, einer Abkürzung bis hin zu einer besonders starken Waffe oder einem optionalen Storystrang alles sein. Spieler werden durch Risk and Reward Momenten in spannende Entscheidungssituationen gebracht ob sie die Herausforderung annehmen und Gefahr Laufen zu versagen oder auf Nummer sicher gehen aber dafür die Belohnung verpassen. 

#### Einzigartigkeit

Die unterschiedlichen Level im Spiel sollten zwar alle dem selben Designkonzepten Folgen, sich jedoch merklich voneinander unterscheiden. Ähnelt sich die Levelstruktur der einzelnen Level zu stark oder werden die selben Grafikassets immer und immer wieder verwendet, trübt dieses die Spielerfahrung. 

"[...] people don’t like playing the same level twice."[@Ryan1999]

Um Level Einzigartiger zu gestalten, sollte eine große Variation an unterschiedlichen Settings, Gegner und Gameplay Herausforderungen geboten werden. 

#### Effizienz
Die Entwicklung von Spielen ist sehr teuer, Spiele kosten in der Regel mehrere Millionen Dollar, dennoch müssen Ressourcen effizient genutzt werden. [@DevPlay2017]
Ein Leveldesigner baut daher nicht jedes Level von grundauf neu, vielmehr entwickelt er ein modulares Set aus verschiedenen Events im Spiel. Durch die kombination der einzelnen Teile dieses Sets, sowie leichten anpassungen für speziellere Situationen, können viele unterschiedliche Situationen erschaffen werden. 
"Modular design is your friend – a smart designer won’t design a level, he/she will design a series of modular, mechanic-driven encounters, that can be strung together to create a level.  And another level.  And another level."[@Taylor2013]

## Prozedurale Levelgenerierung

Im folgenden Abschnitt wird das Prinzip der PCG, insbesondere der PLG beschrieben. Es werden die Vor- und Nachteiler der PLG erörtert und bekannte Verfahren präsentiert. Zuerst folgt eine Erklärung was PCG bzw. PLG bedeutet und wo es angewendet wird. 

### Begriffserläuterung

Prozedurale Levelgenerierung (PLG) ist ein Teilgebiet der Prozeduralen Contentgenerierung (PCG). PCG im allgemeinen beschreibt das automatische erstellen von Inhalten. Im Kontext Videospiele sind diese unter anderen Texturen, Items, Musik und Soundeffekte, 3D-Modelle und auch Spielwelten. Dabei werden Inhalte nicht vollständig Zufällig generiert, vielmehr werden handgebaute Inhalte verändert oder neu zusammengesetzt. Dabei bestimmen zufällig erzeugte Parameterwerte die genaue Art und Weise der Veränderung. [@Beca2017] 

### Vor und Nachteile von PLG

PLG wird vor allem genutzt um kosten bei der Entwicklung zu sparen.[@Remo2008] Ein guter Algorithmus kann auf Knopfdruck hunderte unterschiedliche Level generieren, für dessen Erstellung sonst mehrere Monate gebraucht werden würde.[@IntroversionSoftware2007] Der zweiter große Faktor ist die, aus der theoretisch unendlichen Anzahl an unterschiedlichen Level, resultierende erhöhte Wiederspielbarkeit.[@Beca2017] Besonders das Roguelike Genre (vgl. Abs. ) setzt auf PLG. Roguelikes verwenden PLG zusätzlich als Gameplay Feature. Durch sich stetig verändernden Leveln, kann der Spieler das Spiel nicht durch schieres Auswendiglernen der Levelstruktur gewinnen, er muss die Gameplay Mechaniken verstehen und meistern.[@Brown2019]

Die Implementation eines PLG ist allerdings nicht trivial. Die theoretisch endlose Anzahl an Leveln erschwert das Testen und beheben von Bugs. Evtl. wurde ein Fehler nur in den getesteten Leveln behoben, kommt aber in anderen Leveln wieder vor.[@Remo2008] 

Die Einhaltung der in Abschnitt ... vorgestellten Regeln für gutes Leveldesign ist bei PLG teilweise komplexer im vergleich zu handgebauten Leveln. 

Die Lösbarkeit der Level zu gewährleisten ist bei handgebauten Level schnell und verlässlich möglich. Der Generator im Gegenzug muss darauf Programmiert sein, keine unpassierbaren Hindernisse auf den Kritischen pfaden zu platzieren oder benötigte Puzzleteile wie Türschlüssel so zu positionieren, dass sie erreicht werden können ohne dass zuvor das Puzzle gelöst werden muss. Die Kontrolle des Pacings und Ausbalancierung des Schwierigkeitsgrades sind selbst für Designer eine schwere Aufgabe, die viel Zeit zur Optimierung benötigen[@DevPlay2019], für einen Level Generator ist diese eine komplexe und fast unlösbare Aufgabe. Die Schwierigkeit eines Levels zu bestimmen ist, je nach Genre, sehr schwer und wird noch zusätzlich dadurch erschwert, dass die Generierten Level jederzeit im Spiel auftauchen können. Level sollten aber zu beginn des Spieles einfacher sein, da der Spieler die Mechaniken erst kennenlerne muss und gegen Ende schwerer werden um weiterhin eine Herausforderung zu bieten.  Prozedural generierte Level können aufgrund der verwendeten Muster, schnell repetitiv wirken. Das Spiel *No Man‘s Sky* wurde unter anderen deswegen von Spielern und Fachpresse scharf kritisiert.[Reinartz2016] Den Algorithmus muss genug Flexibilität gegeben werden um Strukturen zu Mutieren (s.a. abs Spelunky). 

Zusammengefasst lässt sich sagen, dass PLG besonders gut dafür geeignet sind um kostengünstig eine Vielzahl an abwechslungsreichen, aber nicht einzigartigen, Leveln zu erschaffen, die es schaffen Spieler dazu zu motivieren, Spiele immer wieder zu spielen. Die Umsetzung guter PLGs ist nicht trivial und erfordert komplexe Regeln und Strukturen. Einige Faktoren lassen sich mithilfe eines PLGs nur schwer oder gar nicht umsetzten. So muss das Pacing beispielsweise nicht über das Leveldesign sondern über Gamedesign Elemente gesteuert werden (vlg. abs. Spelunky). 


### PLG in der Praxis

Mittlerweile existieren eine Vielzahl an unterschiedlichen Algorithmen und Verfahren zur PLG. Im folgenden wird eine Auswahl von ihnen Vorgestellt. 

#### Random Walk

Der Random Walk Algorithmus, auch als *Drunkard‘s Walk* bekannt, wird eigentlich zur Generierung von nicht deterministischen Zeitreihen genutzt, um beispielsweise Aktienkurze in der Finanzmathematik zu modellieren. Er kann aber auch zur Erstellung Höhlenartiger Level genutzt werden. Beim Random Walk bewegt sich ein im leeren Dungeon gesetzter Akteur solange zufällig durch das Dungeon bis er die gewünschte Anzahl an unterschiedlichen Felder passiert hat. Passierte Felder werden als begehbaren Boden interpretiert, unpassierte Feld als unbegehbare Wände.[@Wikipedia2019a]

Der Folgende Pseudocode zeigt, wie ein einfaches 2D-Level mithilfe des Random Walk Algorithmuses erzeugt werden kann.[@Read2014]

```
erstelle ein Level in dem alle Felder Wände sind
wähle ein Feld als Startpunkt aus
vewandel das gewählte Feld in einen Boden
while noch nicht genug Boden im Level
	mache einen Schritt in eine Zufällige Richtung
	if neues Feld ist Wand
		vewandel das neue Feld in einen Boden
```

![Durch Random Walk erstelltes Beispiel Level. eigene Grafik](figs/randomWalk.png){width=50%}

Der Algorithmus bietet viele Parameter zum verändern, so kann die Levelgröße, die gewünschte Bodenfläche und die Wahrscheinlichkeit in die jeweiligen Himmelsrichtungen zu gehen bestimmt werden. 

Abbildung ... zeigt ein, mit den Random Walk erstellts, 50x50 großes Level mit 1200 Bodenfelder. Die Wahrscheinlichkeit in eine Richtung zu gehen lag bei jeweils 25%. Um das so erstellte Level tatsächlich bespielen zu können, müssen noch Start und Endpunkt auf Bodenflächen gesetzt werden. Das verteilen von Monstern und Items auf Bodenflächen wäre auch möglich. 

Der Algorithmus erzeugt vollständig verbundenen Level, mit unterschiedlich großen Räumen und Pfaden.[@Read2014] Die Lösbarkeit der Level kann so garantiert werden. Mit entsprechenden Skripts können Items abseits des Weges platziert werden und durch starke Monster geschützt werden, das kann zu guten Risk-Reward Momenten führen.

#### Spelunky

Spelunky ist ein 2D-Roguelike-Plattformer, es verbindet Elemente klassischer Jump and Runs mit den prozedural Generierten Leveln und Permadeath des Roguelike Genres. 2016 veröffentlichte der Spelunky Entwickler Derek Yu das gleichnamige Buch *Spelunky* in dem er auf die Entwicklungsgeschichte eingeht und unteranderem den Algorithmus zur prozeduralen Level Generierung erläutert. [@Yu2016]

Jedes Level startet mit einen 4x4 Gitter aus jeweils 10x8 Felder großen leeren Räumen. Als erster Schritt der Generierung wird der kritische Pfad im Level generiert. Der kritische Pfad verbindet den Anfang des Levels mit dem Ende des Levels und repräsentiert so den Lösungsweg. 

1. Wähle einen zufälligen Raum aus der ersten Reihe und markiere diesen als Eingang

   2. Wähle eine Zufällige Zahl von 1 bis 5
       1. Ist die Zahl 1 oder 2, gehe einen Raum nach links
         
      2. Ist die Zahl 3 oder 4, gehe einen Raum nach rechts
      
      3. Ist die Zahl eine 5, gehe einen Raum nach unten
      
      4. Wird die Levelgrenze überschritten, gehe einen Raum nach unte
      
   3. Wiederhole Schritt zwei solange bis die Levelgrenze nach unten überschritten wird
   
   4. Markiere den letzten Raum als Ausgang
   
   Während des Algorithmus werden alle Räume mit Zahlen markiert. 
   
   0. Raum wurde nicht passiert
   1. Raum braucht einen Ausgang links und rechts.
   2. Raum braucht einen Ausgang links, recht und nach unten. Liegt ein weiterer zweier Raum darüber, benötigt er zusätzlich einen Ausgang nach oben. 
   3. Raum braucht einen Ausgang links, recht und nach oben.  

Die Markierung erfolgt entsprechend der Richtung durch die der Raum betreten bzw. verlassen wird. Die Raumanordnung kann durch eine 4x4 Matrix dargestellt werden. So ist garantiert, das entlang des kritischen Pfades eine lösbare Raumabfolge generiert wird. Das Problem der Level Lösbarkeit (vgl regeln Level) wird dadurch gelöst. 

Den Räumen abseits des Kritischen Pfades werden zufällig eine der Nummern zugewiesen. Dementsprechend werden sie entweder an den Pfad angeschlossen oder sind von ihn abgeschottet. Spelunky bietet den Spieler die Möglichkeit Wände mit Bomben zu sprengen oder mithilfe von verschließbaren Kletterseilen steil nach oben zu klettern. So können selbst vermeidlich unerreichbare Räume betreten werden, vorausgesetzt der Spieler hat die nötigen Items dafür gefunden. 

Abbildung ... zeigt eine generiertes Spelunky Level. Die rote Linie entspricht dem kritischen Pfad, die Zahlen in den Raumecken die entsprechende Nummerierung. Die dunkel roten Blöcke zeigen noch einmal die Bedeutung der Nummerierung, und sind im eigentlichen Level nicht zu sehen. 

![Ein Beispiel Level aus Spelunky, in rot der kritische Pfad.[@Kazemi]](figs/spelunky.PNG){width=50%}

Um die Räume mit Inhalt zu füllen wird, abhängig von der Nummerierung des Raumes, eines von mehreren unterschiedlichen Templates verwendet. Die Templates geben an an welcher Stelle welche Art von Block platziert wird. Um die Variation trotz geringer Template Anzahl zu erhöhen, werden einige Bereiche der Templates Mutiert. 

Jedes Template wird durch einen String dargestellt und kann als 10x8 Matrix verstanden werden. Jedes Zeichen in der Matrix repräsentiert die Art des Blocks an der entsprechende Stelle (siehe Tabelle). Einige Felder, genannt Chunks, ersetzten eine 5x3 Fläche durch eines von zehn vorgefertigten Templates. Durch die Zufallselemente lassen sich auch mit wenigen Raum Templates eine Vielzahl unterschiedlich aussehender Räume generieren. 

| Referenz | Ersetzen durch |
| ----| ---- |
|     0 | Freie Fläche |
|     1 | Solider Block |
|2      | zufällig Freie Fläche oder Solide Block |
| 4        | schiebbarer Steinblock                  |
| 6        | 5x3 Chunk                               |
| L        | Leiterteil                              |
| P        | Leiterteil mit Fläche zum Stehen        |

Um Monster und Items zu verteilen, wird zum Schluss für jedes als **1** gekennzeichnetes Feld entschieden, ob zum Beispiel ein Monster darauf oder darunter platziert wird. Bei der Platzierung nehmen auch umliegende Felder Einfluss, so werden  beispielsweise Truhen bevorzugt in Nischen platziert. So werden Spieler die auch Abseits des kritischen Pfades suchen, mit Items oder Schätzen belohnt. 

Um optische Abwechslung zu bieten werden die Level mit unterschiedlichen Visuellen Themen erstellt, so gibt es unteranderem Dschungel-, Eis- und Feuerlevel. Das Pacing wird in Spelunky nicht durch das Leveldesign kontrolliert sondern mithilfe eines Geistes. Der Geist spawnt dann, wenn der Spieler Zuviel Zeit in einem Level verbracht hat und führt bei Berührung zum sofortigen Game Over. Dadurch muss der Spieler regelmäßig die Entscheidung treffen ob er den Levelrand noch nach weiteren Items absucht und Gefahr läuft vom Geist getötet zu werden oder sich sofort zum Levelausgang begibt dafür aber keine Items oder Schätze mitnehmen kann, was den weiteren Fortschritt erschwert (vgl. Risk Reward). 

In seinen Buch schrieb Yu: 

"This system doesn‘t create the most natural-looking caves ever, and players will quickly begin to recognize certain repeating landmarks and perhaps even sense that the levels are generated on a grid. But with enough templates and random mutations, there‘s still plenty of variability. More importantly, it creates fun and engaging levels that the player can‘t easily get stuck in, something much more valuable than realism when it comes to making an immersive experience"[@Yu2016]

#### Graph Based

In den Paper *Game Level Layout from Design Specification* präsentierten unter anderen Chongyang Ma, einen Algorithmus der dazu in der Lage ist Level aus planaren Graphen zu generieren. Der Generator kann mithilfe weniger vorgegebener unterschiedlich geformter Räume aus einen Graphen gleich mehrere unterschiedliche Level Generieren.[@Ma2014]

Abbildung ... zeigt einen, zur Generierung verwendbaren, planaren Graphen gibt das Levellayout vor. Die Knoten repräsentieren Räume, die Kanten stellen Verbindungen zwischen den Räumen da. Abbildung ... zeigt eine Auswahl an unterschiedlichen Räumen. Abbildung ... zeigt den daraus erzeugten Level.  

![Graph zeigt das Levellayout[@Ma2014]](figs/inputGraph.PNG){width=50%}

![Formen aus den das Level gebaut wird[@Ma2014]](figs/buildingBlocks.PNG){width=50%}

![Erzeugtes Level [@Ma2014]](figs/outputlevel.PNG){width=50%}

Ziel des Algorithmus ist es, jeden Knoten eine Form und eine Position zuzuteilen, so das die durch den Graphen vorgegeben Levelstruktur erreicht wird. Dabei dürfen sich Räume nicht überlappen und Verbindungen müssen mithilfe von Türen möglich sein. Zwar könnte man einfach jede Möglichkeit durchspielen, dies wäre aber höchst Ineffizient. Stattdessen wird der Graph zuerst in Subgraphen zerlegt um die Komplexität des Problemes aufzuteilen, diese Subgrafen werden von Ma als "Chains" bezeichnet. In Chains, haben Knoten maximal zwei Nachbarn. Gültige Layouts für Chains zu finden ist deutlich einfacher als direkt den ganzen Graphen zu lösen. Zuerst wird eine Chain gelöst indem immer zwei Räume genommen werden, wobei die Position der ersten Raumes fest ist, der zweite Raum kann bewegt und rotiert werden. Dieses verfahren wird wiederholt, bis ein gültiges Layout für die komplette Chain gefunden wurde. Dann wird eine weitere Kette den Layout hinzugefügt, solange bis das gesamte Level erzeugt wurde. Da für jede Chain mehrere mögliche Layouts existieren, wird im Falle das kein gültiges Layout für das kombinieren zweier Chains gefunden werden kann, solange Backtracking betrieben bis der Level gelöst ist. [@Ma2014]

Der Algorithmus bietet gleich mehrere Vorteile. Da das Levellayout vorgegeben wird, kann der Designer gezielt das Pacing des Levels beeinflusse. Durch den Graphen ist deutlich zu erkennen, welche Räume auf den kritischen Pfad liegen und welche Räume optional sind, Gegner und Items können entsprechend platziert werden. Im Vergleich zu anderen Generatoren werden allerdings viele Inputdaten benötigt. Der Algorithmus bietet die Möglichkeit abwechslungsreiche Level zu erschaffen, dafür müssen aber sowohl der Graph als auch die Input Räume gut gestaltet werden. 

Ondřej Nepožitek optimierte und erweiterte den Algorithmus noch um mehrerer Features. So kann sein Version unter anderem Räume mit Fluren verbinden. [@Nepozitek2018]


## Genetische Algorithmen 

### Grundlagen

Da der für diese Arbeit entwickelte Level Generator auf Genetischen Algorithmen basiert, werden in diesen Abschnitt alle, zur Verständnis dieser Arbeit, relevanten Grundlagen erklärt. Es existiert eine Vielzahl an Literatur über Genetische Algorithmen, die hier präsentierten Informationen basieren auf Russell und Norvigs „Artificial Intelligence: A Modern Approach“(Kapitel 4.1.4)[@RussellundNorvig2014]  und Volker Nissens „Einführung in Evolutionäre Algorithmen“[@Nissen2013]

#### Definition

"Evolutionäre Algorithmen (EA) sind Optimierungsverfahren, die sich am Vorbild der biologischen Evolution orientieren." [@Selzam2006]

Vereinfacht ausgedrückt: Evolutionäre Algorithmen sind ein Verfahren zur kontrollierten und gesteuerten Zufalls suche. 

Es gibt vier, aus der Historie entstandenen unterscheidungsformen:

- Genetische Algorithmen (GA)
- Genetische Programmierung (GP)
- Evolutionsstrategien (ES)
- Evolutionäre Programmierung (EP)

Diese Variationen ähneln sich von Aufbau und Struktur sehr. Im folgenden werden GA betrachtet, zum Abschluss werden die wichtigsten Unterschiede der anderen Variationen erläutert.  

#### Grundbegriffe aus der Genetik

Da EA sich an der Evolution orientieren, haben viele Fachbegriffe der Genetik ihren Weg in die Informatik gefunden. Im Folgenden werden die, für Genetische Algorithmen und diese Arbeit, relevanten Begriffe in ihrer Bedeutung innerhalb der Informatik, erläutert. 

**Individuum / Chromosom** 

"Ein Individuum im biologischen Sinne ist ein lebender Organismus, dessen
Erbinformationen in einer Menge von Chromosomen gespeichert ist. Im Zusammenhang mit genetischen Algorithmen werden die Begriffe Individuum und
Chromosom jedoch meistens gleichgesetzt." [@Selzam2006]

**Gen**

Ein Gen ist genau eine Sequenz im Individuum. Je nach Kontext kann es sich hierbei um eine einzelne Stelle oder mehrere Stellen im Individuum handeln.

**Allel**

Allel beschreibt den exakten Wert eines Gens. 

**Population / Generation**

Einer Menge gleichartiger Individuen wird als Population bezeichnet. Die Anzahl der Individuen gibt die Populationsgröße an. Sterben Individuen oder werden neune geboren, verändert sich die Größe der Population. Betrachtet man eine Population über mehrerer Zeitpunkte, spricht man von Generationen. 

#### Ablauf

GAs folgen einer Reihe an Subrutinen, die sich solange Wiederholen bis eine Abbruchbedingung erreicht ist. 

![Ablauf eines generischen GAs. Eigene Grafik](figs/gaAblauf.png){width=100%}

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

##### Kodierung

Zu beginn muss das betrachtete Problem kodiert werden. Das bedeutet das alle relevanten Aspekte auf ein Chromosom abgebildet werden müssen. 

Bei der Binären Kodierung besteht ein Chromosom aus n vielen Genen. Jeden Gen wir ein binärer Wert zugewiesen und repräsentiert dabei eine Problemvariable. Aus den Genen wird dann ein Bitstring erzeugt. 

$$ g=(g_{ 0  }....g_{ m }) \in\left\{ 0,1 \right\}^{ m }  $$

Verwendet man die Binäre Kodierung, muss man am ende die Lösung mithilfe einer Dekodierungsfunktion zurück wandeln. 

$$ T: \left\{ 0,1 \right\}^{ m } \to R^{ n } $$


Die Allel der Gene der Startpopulation werden zufällig bestimmt. 

##### Bewertung

Die Bewertung erfolgt mithilfe der sogenannten Fitnessfunktion. Die Fitnessfunktion $F$ bewertet die güte eines Individuums, also wie nahe es schon an einer möglichen Lösung ist. Dabei weißt die Fitnessfunktion den Individuum eine reelle Zahl zu, die die Fitness darstellt. Im Regelfall ist eine höhere Fitness besser als eine geringer. 

Die Implementation der Fitnessfunktion ist stark mit der eigentlichen Problemstellung verwoben. Da die Fitnessfunktion großen Einfluss darauf hat, in welche Richtung sich die Population entwickelt, sind die Bewertungskriterien so zu wählen, das sie zur Erreichung der Lösung beitragen. Da die Fitnessfunktion während der Laufzeit jedes einzelne Individuum jeder Population jeder Generation betrachtet, sollte bei der Implementierung auf Laufzeit Optimierung geachtet werden, eine komplexe Fitnessfunktion kann den gesamten GA verlangsamen. 

##### Selektion

Bei der Selektion werden Individuen der Ausgangspopulation ausgewählt und in den "mating pool" kopiert, der die Grundlage für die später folgenden Mutation und Rekombination ist. Zu beachten ist, das ausgewählte Individuen nicht aus der Ausgangspopulation entfernt werden, sie können also mehrfach ausgewählt werden. Bei den gängigsten Selektionsverfahren spielt der Fitnesswert der einzelnen Individuen eine große Rolle, da er bestimmt wie hoch die Wahrscheinlichkeit ist, in den mating pool aufgenommen zu werden. Jedoch sollte man nicht nur die besten Individuen der Population auswählen, da nicht nachvollzogen werden kann, ob sich das Individuum in der nähe des globalen Hochpunkts befindet oder sich lediglich einen lokalen Hochpunkt nährt. Würde man nur die besten Individuen erlauben sich zu vermehren, würde sich die Population in eine Richtung festfahren. Die Selektion baut einen Fitnessdruck auf, da das überleben eines Individuum stark von seiner Fitness abhängt. Der Fitnessdruck entspricht daher den *Survival of the Fittest* Gedanken der Evolutionstheorie. 

Für GA gilt: $\mu$  Eltern erzeugen $\mu$ Kinder, daher die Populationsgröße bleibt über den gesamtem GA zeitraum konstant. 

Es folgt eine Auflistung und Erklärung bekannter Selektionsverfahren.

**Fitness Proportionate Selection** 

Bei der Fitness Proportionate Selection hat jedes Individuum die Chance ausgewählt zu werden. Die Chance ausgewählt zu werden ist abhängig von der FItness des Individuums. Besonders gute Lösungen haben also hohe Chance ausgewählt zu werden, schlechtere Lösungen können dennoch ausgewählt werden um so die vielfallt der Population zu gewährleisten. 

Eine gängige Art der Umsetzung dieses Verfahren ist die **Roulett Wheel Selection**. Angelehnt an Glücksräder, wird ein Rad in $n$ Teile zerteilt, wobei

 ```
n=\Sum{ j }{  }{ F(g_{j}) }
 ```

gilt. Jedes Individuum der Population enthält entsprechend seiner Fitness Anteile am Rad. Am Rad wird ein Fix Punkt angesetzt, das Rad wird rotiert und das Individuum ausgewählt auf dessen Anteil der Fix Punkt stehenbleibt.

![Bildliche Darstellung der RWS [@tutorialspoint]](figs/roulette_wheel_selection.png){width=100%}

Die Wahrscheinlichkeit $p_{sel}$ eines Individuum $g_{k}$ mit der Fitness $F(g_{k})$ ausgewählt zu werden lässt sich wie folgt berechnen

```
p_{ sel }(g_{ k })=\frac{F(g_{ k }) }{\Sum{ j }{ }{F(g_{ j })  }  }$$
```

Das **Stochastic Universal Sampling** erweitert die Roulett Wheel Selection um einen zweiten Fixpunkt. So können zeitgleich zwei Individuen ausgewählt werden. 

Fitness Proportionate Selektionsverfahren funktionieren nicht in Fällen, in dem Fitnesswerte negativ sein können. 

**Tournament Selektion**

Bei der Tournamen Selektion werden zufällig k Individuen aus der Ursprungspopulation ausgewählt, das Individuum mit der höchsten Fitness wird in die nächste Generation aufgenommen. Dieses Verfahren ermöglicht zwar auch schlechteren Lösungen Ausgewählt zu werden, versichert aber das die schlechtesten k-1 Lösungen nicht ausgewählt werden können und die beste Lösung auf jeden Fall ausgewählt wird. Tournament Selektion funktioniert auch bei negativen Fitnesswerten. 
![Bildliche Darstellung der TS [@tutorialspoint]](figs/tournament_selection.png){width=100%}

**Rank Selektion**

Bei der Rank Selektion wird die Population anhand der Fitnesswerte der Lösungen sortiert. Für die Auswahl spielt nicht mehr der Fitnesswert sondern die Platzierung der Lösung eine Rolle. Höher platzierte Lösungen haben eine höhere Chance ausgewählt zu werden als niedrig platzierte Lösungen. Der Chancenunterschied ist je nach Problemstellung zu wählen. Rank Selektion kann auch bei negativen Fitnesswerten verwendet werden. 

**Zufällige Selektion**

Bei der zufälligen Selektion werden zufällig Individuen aus der Population ausgewählt. Dieses Verfahren wird für gewöhnlich vermieden, da es keinerlei Filter Mechanismen gibt und die suche nicht gesteuert werden kann. 

##### Rekombination

Bei der Rekombination werden zwei Individuen aus dem mating pool neu zusammengesetzt. Die beiden Ursprünglichen Individuen bezeichnet man als Eltern, die neu erzeugten Individuen als Kinder. 

Ob zwei Eltern miteinander Rekombiniert werden, ist Abhängig von der festgelegten Crossoverchance. In GAs liegt diese für gewöhnlich bei $\approx$ 60%. Die Rekombination vermischt den Genpool der Eltern und soll so für eine möglichst diverse Population sorgen. Kommt es zur Rekombination werden die Kinder in die neue Population gelegt, kommt es zu keiner Rekombination werden die Eltern in die neue Population kopiert. 

Es folgt eine Erläuterung einiger verbreitetet Crossoververfahren. Je nach Problemstellung kann auch ein Individuelles verfahren Zielführend sein. 

**Point Crossover**
Beim Point Crossover verfahren werden beide Eltern an einer oder mehrere Stellen in Segmente geteitl. Die Segmente der Eltern werden miteinander vertauscht um die Kinder zu erzeugen. 

![Bildliche Darstellung des Multi Point Crossoververfahren [@tutorialspoint]](figs/multi_point_crossover.png){width=100%}

**Uniform Crossover**

Beim Unform Crossover wird jedes Gen eines Elternteils betrachtet, es gibt eine 50% Chance dass das Gen mit dem entsprechenden Gegenstück des anderen Elternteils ausgetauscht wird. 

![Bildliche Darstellung des Uniform Crossoververfahren [@tutorialspoint]](figs/uniform_crossover.png){width=100%}

##### Mutation

Die Mutation ist eine Zufällige Veränderung der Gene um eine neue Lösung zu erhalten. Genau wie die Rekombination wird sie genutzt um eine möglichst große Diversität der Population zu erzeugen. Durch Crossover erzeugte Kinder liegen im Suchbaum in der nähe der Eltern, durch die Mutation werden die Kinder von den Eltern weiter entfernt. 

Die Mutationschance$p_{mut}$ ist gering anzusetzen, da eine zu hohe Mutationschance den GA zu auf eine zufällige Suche reduzieren würde. 

Es folgt eine Auflistung und Beschreibung bekannter Mutationsverfahren. Je nach Problemstellung kann auch eine Spezifische Mutationsmethode Zielführend sein. Je nach Kontext kann ein einzelnes Gen oder eine Sequenz an Genen mutiert werden. Für die folgenden Verfahren muss man dann beachten, das eine Sequenz auch als Gen bezeichnet wird. Je nach Bedarf kann nur eine Mutation pro Lösung durchgeführt werden oder es kann jedes Gen mit einer Chance von $p_{mut}$ mutiert werden. 

**Bit Flip Mutation** 

Bei der Bit Flip Mutation wird ein oder mehrere bits gewechselt. Bit Flip Mutation kann daher nur bei Binär Kodierten GAs verwendet werden. Abwandlungen für Reellwertige Kodierungen sind aber möglich. 

**Swap Mutation**

Bei der Swap Mutation werden zwei Zufällig ausgewählte Gene miteinander vertauscht. 

**Scramble Mutation**

Bei der Scramble Mutation wird eine Auswahl an zusammenhängenden Genen vermischt um so die Reihenfolge zu ändern. 

**Inversion Mutation**

Bei der Scramble Mutation wird die Reihenfolge einer Auswahl an zusammenhängenden Genen umgedreht. Das erste Gen in der Sequenz wird zum letzten und umgedreht. 

##### Abbruchbedingung

Die Abbruchbedingung ist für gewöhnlich dann erreicht, wenn eine gültige Lösung gefunden wurde. Je nach Problemstellung kann es auch das überschreiten eines gewissen Fitnessschwellwertes sein oder der durchlauf einer bestimmten Generationenanazahl. Es bietet sich an einen Neustart des GAs vorzunehmen, sollte nach einer gewissen Generationsanzahl keine Lösung gefunden worden sein. Der entsprechende Schwellwert sollte so gewählt werden, dass Erfahrungsgemäß keine Steigerung der Fitness zu erwarten ist und ist dementsprechend für jede Implementierung unterschiedlich. 

#### Vor und Nachteile

| Pro                                                         | Contra                                                       |
| ----------------------------------------------------------- | ------------------------------------------------------------ |
| Gut geeignet für Probleme in großen Suchräumen              | Laufzeit in kleinen Suchräumen oft länger als andere Verfahren |
| Benötigt keine abgeleiteten Informationen des Problems      | Laufzeit stark von der Komplexität der Fitnessfunktion abhängig |
| Lässt sich gut parallelisieren                              | Lösungen werden Zufällig gefunden, es gibt keine Garantie die beste Lösung zu finden. |
| Liefert eine Menge an guten Lösungen, nicht nur eine Lösung |                                                              |
| Verläuft sich nicht in lokalen Hochpunkten                  |                                                              |

### Variationen

Die mit GA vergleichbarste EA Variante sind die ES. ES folgen einer ähnlichen Struktur wie GA. Die Verfahren unterscheiden sich aber an zwei punkten drastisch voneinander. 

ES werden reellwertig Kodiert. Die reellwertige Kodierung funktioniert ähnlich zu der Binären Kodierung, nur wird hier jeden Gen ein reellwertig Wert zugewiesen. Eine Dekodierung ist nicht nötig. 

$$ g=(g_{ 0  }....g_{ m }) \in \left\{ R \right\}^{ m } $$  

Durch diese Art der Kodierung sind einige, aus GA bekannten, Verfahren zur Mutation nicht verwendbar (Bsp. Bit-Flip-Mutation). 

Der zweite große Unterschied liegt in der Auswahl der Eltern für die nächste Population. Noch bevor es zur Bewertung der Individuen kommt, werden $\lambda$ Individuen zufällig aus den $$\mu$$ großen Ausgangspopulation ausgewählt, sie bilden den mating pool, wobei $\lambda\leq\mu$ gilt. Einige Individuen müssen daher öfters in den mating pool vorkommen. Jetzt findet ähnlich zu GA die Rekombination und Mutation des mating pool statt. Die Individuen des mempoolmating pool werden jetzt mithilfe der Fitnessfunktion bewertet und mithilfe eines Selektionsverfahren werden $\mu$ Kinder für die nächste Generation ausgewählt. Die Populationsgröße bleibt also zwischen den Generationen konstant. 

Welches Verfahren geeigneter ist, hängt stark mit der eigentlichen Problemstellung zusammen. Mittlerweile sind auch viele Mischformen zwischen ES und GA verbreitet. 

"Beide Verfahren beginnen mit einer breiten Verteilung der Individuen im Suchraum.
Abgeschlossen wird sowohl bei den ES als auch bei den GA mit der Konzentration der
Individuen auf einen minimalen Ausschnitt des Lösungsraums. In den ES wie auch in
den GA wurde eine noch immer wachsende Anzahl von Varianten entwickelt. Durch
diese nicht mehr überschaubare Varianten verschwimmen die Grenzen zwischen den
Verfahren. Es kommt sogar vor, dass bei Genetischen Algorithmen anstelle der üblichen
binären Vektoren, für bestimmte Problemstellungen reelle Vektoren eingesetzt werden.
Welcher Algorithmus bei welcher Problemstellung besser ist, kann nicht eindeutig
entschieden werden."[@Lienemann2004]

Bei der Genetischen Programmierung geht es darum, mithilfe von Bäumen Probleme der symbolischen Regression zu lösen. Evolutionäre Programmierung nimmt sich ähnlicher Problematiken an, verwendet aber endliche Automaten zur Lösung der Probleme. 
