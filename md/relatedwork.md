

# Grundlagen

In diesem Kapitel werden alle wichtigen Informationen vermittelt, die nötig sind um die technischen Hintergründe der Arbeit zu verstehen und um das Resultat der Arbeit bewerten zu können. Im ersten Abschnitt wird das Genre der Roguelike-Spiele beschrieben, um ein Verständnis dafür zu vermitteln, welche Art von Spiel von den Studenten programmiert wird. In Abschnitt zwei werden Prinzipien für gutes Leveldesign vermittelt. Der dritte Abschnitt beschäftigt sich mit Prozeduraler Level Generierung, es werden bekannte Verfahren präsentiert, um Level automatisch generieren zu lassen. Der letzte Abschnitt vermittelt das nötige Grundwissen für Genetische Algorithmen, um das im nächsten Kapitel folgende, Konzept verstehen zu können.

Dieses Kapitel ist sowohl Strukturelle als auch Inhaltliche durch Kevin Hagens Thesis *Synthese generierter und handgebauter Welten mittels WaveFunctionCollapse* [@Hagen2019] inspiriert, führt dennoch neue Aspekte auf oder betrachtet einige Aspekte anders.  

## Shattered Pixel Dungeon

Um ein Verständnis dafür zu bekommen, welche Art von Spiel in den vom Generator erzeugten Level gespielt wird, werden in diesen Abschnitt die Grundkonzepte von Computer Rollenspiele insbesondere des Subgenres Roguelike beschrieben. Danach wird das Spiel: *Shattered Pixel Dungeon* noch einmal genauer beleuchtet, da dieses die Inspiration für das neue Praktikumskonzept ist.

### Rollenspiele

Das Genre der Computer-Rollenspiele entstand aus dem klassischen Pen-and-Paper Rollenspiel. Der Spieler schlüpft in die Rolle einer oder mehrere Spielfiguren mit unterschiedlichsten Fähigkeiten. Im Fokus des Spielerlebnisses steht die Erzählung einer Geschichte und das Eintauchen in die Spielwelt. Aber auch die Verbesserung der Spielfigur und ihrer Ausrüstung nehmen einen großen Bestandteil der Spielerfahrung ein. Viele Spiele lassen den Spieler bereits zu Beginn eine von mehreren Charakterklassen wählen. Die Klasse der Figur bestimmt, welche Ausrüstung sie benutzen kann und welche Fähigkeiten erlernt werden können. So werden unterschiedliche Spielstile erschaffen, ein Zauberer spielt sich spürbar anders als ein Krieger. Im Vordergrund des Gameplays stehen das Lösen von Rätseln, führen von Dialogen und bekämpfen von Feinden. [@Wikipedia2019] [@Wikipedia2018]

Viele Rollenspiele ermöglichen es den Spieler direkten Einfluss auf den Verlauf der Geschichte zu nehmen. So muss sich der Spieler in *The Witcher 2: Assassins of Kings* im ersten von drei Akten, für eine von zwei Seiten entscheiden. Die Wahl nimmt Einfluss darauf, von welcher Seite der im Spiel gezeigte Konflikt betrachtet wird. [@Graf2011] Auch dürfen weniger wichtige Entscheidungen vom Spieler übernommen werden, die zwar weniger Einfluss auf das große Ganze nehmen, jedoch die Immersion steigern können.

In der Entwicklungszeit der Rollenspiele haben sich vor allem zwei unterschiedliche Kampfsysteme hervorgetan. Rundenbasierte Systeme übernehmen das aus den Pen and Paper bekannte Runden System, in dem Spieler und Gegner jeweils abwechselnd eine Kampfaktion ausführen, bis einer von beiden besiegt wurde. Action basierte Systeme laufen in Echtzeit ab, hier stehen vor allem schnelle Reaktionen und gute Reflexe im Vordergrund. Echtzeit basierte Rollenspiele werden oft auch als Action-Rollenspiel oder Action-Adventure bezeichnet, um sie von klassischen Rollenspielen abzugrenzen. [@Wikipedia2019]  

### Rogue

Das Videospiel *Rogue: Exploring the Dungoens of Doom*, ist ein, in den 1980er entwickeltes, Dungeoncrawler. Der Spieler bewegt sich Rundenbasiert durch ein, aus ASCII-Zeichen bestehendes, Levelsystem (vgl. Abbildung 2.1), um am Ende das Magische Amulett von Yendor zu erlangen. Auf den Weg dorthin gilt es die feindlichen Monster zu besiegen. [@Barton2009]

![Ein typisches Level aus den Spiel Rogue [@Barton2009]](figs/rogue.jpg){width=100%}

Glenn R. Wichman, einer der Entwickler von Rogue, schrieb in einen offenen Brief:

>„But I think Rogue’s biggest contribution, and one that still stands out to this day, is that the computer itself generated the adventure in Rogue. Every time you played, you got a new adventure. That’s really what made
>it so popular for all those years in the early eighties.“[@Wichman1997]

Die Level von Rogue werden bei jeden Spielstart neu generiert, man spielt also niemals zweimal das exakt selbe Level. In Verbindung mit Permadeath wurde so ein Abwechslungsreiches-Spiel mit hohem Wiederspielwert erschaffen. Permadeath beschreibt ein Spielprinzip, bei dem der Spielertot zum permanenten Verlust des Fortschritts führt und das Spiel von vorne begonnen werden muss.

### Roguelikes

Da Rogue eines der ersten Spiele war, die auf PCG setzten, waren sowohl Spieler als auch Entwickler von diesem Konzept begeistert. Viele Entwickler fühlten sich durch Rogue inspiriert und entwickelten ihre eigenen Spiele mit PCG.[@Barton2009] Es entwickelte sich das Genre der Roguelike.

>„[...] Roguelikes are called Roguelikes, because the games are literally like Rogue [...]“ [@Brown2017]

2008 wurde auf der Internationale Roguelike Entwickler Konferenz eine Liste verschiedener Faktoren erstellt, welche dabei helfen sollen, das Roguelike Genre genauer zu beschreiben und zu definieren, wann ein Spiel *like Rogue* ist. Diese List wurde später unter den Namen *Berliner Interpretation* bekannt. Folgende Faktoren wurden dabei herausgearbeitet: [@Conference2008]

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

Table: Berliner Interpretation: High value factors [@Conference2008]


| Low value factors               | Erläuterung                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| Single player character         | The player controls a single character. The game is player-centric, the world is viewed through that one character and that character's death is the end of the game. |
| Monsters are similar to players | Rules that apply to the player apply to monsters as well. They have inventories, equipment, use items, cast spells etc. |
| Tactical challenge              | You have to learn about the tactics before you can make any significant progress. This process repeats itself, i.e. early game knowledge is not enough to beat the late game. (Due to random environments and permanent death, roguelikes are challenging to new players.) The game's focus is on providing tactical challenges (as opposed to strategically working on the big picture, or solving puzzles). |
| ASCII display                   | The traditional display for roguelikes is to represent the tiled world by ASCII characters. |
| Dungeons                        | Roguelikes contain dungeons, such as levels composed of rooms and corridors. |
| Numbers                         | The numbers used to describe the character (hit points, attributes etc.) are deliberately shown. |

Table: Berliner Interpretation: Low value factors [@Conference2008]

Die Berliner Interpretation wurde über die Jahre immer wieder kritisiert. Darren Grey kritisierte in seinen Blog Beitrag *Screw the Berliner Interpretation!* den >"downright nonsense"<[@Grey2013] der meisten Faktoren und ist der Auffassung die Berliner Interpretation schadete der kreativen Weiterentwicklung des Genres.[@Grey2013]

Im Laufe der Zeit hat die Berliner Interpretation immer mehr an Bedeutung verloren. Heute versteht man unter dem Genre Roguelike Spiele die zufällig generierten Level mit Permadeath kombinieren. [@Brown2019] Dazu gehören unter anderen Spielen wie der Plattformer *Spelunky*, das Survival Spiel *Dont‘t Starve* oder das Action-Adventure *The Binding of Isaac*. [@Wikipedia2020]  


### Shattered Pixel Dungeon

*Shattered Pixel Dungeon* wäre laut der Berliner Interpretation ein echtes Roguelike. Es erfüllt alle Bedingungen, bis auf die ASCII Darstellung, der Definition. 

![Beispielausschnitt für das Leveldesign in Shattered Pixel Dungeon [@Meldrian2015]](figs/spdss.png){width=100%}

Zu Beginn des Spiels wählt der Spieler eine der vier Charakterklassen aus. Zur Auswahl stehen:

- Krieger: Nahkämpfer mit Fokus auf hohe stärke und Lebensenergie

- Magier: Fernkämpfer der Zauber verwendet

- Schurke: Nahkämpfer der auf Planung und Hinterhalte fußt 

- Jäger: Fernkämpfer der vor allem Bögen verwendet

[@Pixeldungeon.fandom2019]

Der Spieler startet auf der obersten Ebene eines Dungeons. Ziel ist es, möglichst tief in das Dungeon einzudringen, je tiefer der Spieler gelangt desto schwieriger wird das Spiel. In den einzelnen Ebenen begegnen den Spieler die unterschiedlichsten Monster, durch dessen Tötung der Spieler Erfahrung sammelt, wurde genug Erfahrung gesammelt, steigt der Spieler auf, dies ermöglicht ihn, seine Spielfigur mit neuen Fähigkeiten auszustatten. Ebenso lassen besiegte Gegner Ausrüstungsgegenstände wie Heiltränke oder Waffen fallen. Um Erfolg im Spiel zu haben, sollte man also nicht nur versuchen möglichst schnell möglichst tief in das Dungeon einzudringen, sondern auch viele Gegner zu besiegen um an Erfahrung und Items zu gelangen.

Darüber hinaus bietet Shattered Pixel Dungeon noch viele weiter Features, wie Subklassen oder Verzauberungen, welche hier nicht weiter beschrieben werden, da diese für das Verständnis des Spielablaufes nicht nötig sind.

Abbildung 2.2 zeigt einen Ausschnitt eines Dungeons aus dem Spiel. Besonders gut zu erkennen sind die Zufällig verteilten Räume verbunden durch Flure sowie der *Fog of War*, welcher die Monster außerhalb der Reichweite der Spielfigur unsichtbar macht.  

## Regeln für gutes Leveldesign

>"In den meisten Computerspielen steht das Design der Level im Mittelpunkt. Ob
>Puzzle-, Adventure-, oder Rollenspiel, Level gehören zum Kernpunkt der Interaktion. Sie vereinen >die verschiedenen Elemente des Spiels zum großen Ganzen: Dem
>Spiel selbst. Rückt der Leveldesigner nicht all diese Elemente in ein gutes Licht, so
>geht der Spielspaß verloren."[@Hagen2019]

Im Laufe der Zeit haben viele Personen versucht eine Liste wichtiger Regeln für Gutes Leveldesign aufzustellen. Durch die großen Unterschiede in den Anforderungen an die einzelnen Spielwelten sollten diese Regeln nur als Leitfaden betrachtet werden. Auch haben unterschiedliche Entwickler unterschiedliche Meinungen darüber, welche Faktoren wichtig sind und wie sie umzusetzen sind. Im Folgenden wird versucht eine Liste oft genannter Faktoren für gutes Leveldesign aufzustellen. Die wichtigsten Informationen stammen dabei aus Dan Taylors *Ten Principles of Good Level Design* [Taylor2013] sowie seinen dazugehörigen GDC Talk [@Taylor2018] und Tim Ryans *Beginning Level Design*
[@Ryan1999]

### Lösbarkeit und Fehlerfreiheit
Ein Level muss immer lösbar sein. Je nach Spiel und Genre bedeutet dies, das entweder das Ende des Levels erreichbar sein muss oder alle Missionen im Level absolvierbar sein müssen. Dasselbe gilt für alle Nebenmissionen und sammelbare Objekte wie Münzen oder Items im Level. Ist ein Level durch schlechtes Design oder Bugs nicht lösbar, zerstört dies den Spielspaß des Spielers.

Auch sollten Level immer so fehlerfrei wie möglich sein. Das betrifft sowohl grafische Fehler, wie falsch platzierte Texturen, als auch Gameplay Fehler. Typische Beispiele für Gameplay Fehler wären die, vom Designer ungewollte, Möglichkeit ein Hindernis zu umlaufen, falsch platzierte Gegner oder andere, vom Level abhängigen, Designfehler. Einzelne Fehler werden von Spielern oftmals hingenommen, summiert sich die Anzahl der Fehler aber auf, oder schränken diese den Spielgenuss ein, kann dies ähnliche Auswirkungen haben wie ein unlösbares Level.  

### Gameplay First

>"Above all else, great level design is driven by interaction - the game’s mechanics.  Game levels don’t just provide context for mechanics, they provide the very reality in which they exist." [@Taylor2013]

Ein Level dient in aller erster Linie dazu, den Spieler eine Spielfläche für die Gameplay Mechaniken des Spiels zu bieten. Das bedeutet ein Level sollte viele Möglichkeiten geben, um die Gameplay Mechaniken zu verwenden und muss immer so gebaut sein, dass es mit den Gameplay Mechaniken spaßig zu spielen ist.

Daher braucht ein Shooter interessante Areale zum Kämpfen, Rennspiele spaßige Rennstrecken, Schleichspiele Plätze zum Verstecken und Spiele, die durch spielerische Freiheit überzeugen wollen, viele Möglichkeiten Situationen zu lösen.

Abbildung 2.3 zeigt die Karte der Mission *Bankraub* aus dem Spiel *Dishonored: Death of the Outsider*. In dieser Mission muss der Spieler in eine Bank einbrechen, um eine Waffe zu klauen. Die Karte zeigt unterschiedliche Eingänge in die Bank und unterschiedliche Taktiken die Mission zu absolvieren. So kann der Spieler ein Schlafpulver in das Belüftungssystem der Bank verteilen, um alle Personen in der Bank schlafen zu lassen. Das Pulver kann entweder gekauft werden oder auf einer Aktion gestohlen werden. Alternativ kann der Spieler sich auch durch die Kanalisation in die Bank schleichen und dort versuchen alle Gegner zu umgehen oder alle Wachen im Kampf zu töten.  

![Levelkarte aus dem Spiel Dishonored: Death of the Outsider. Grafik aus dem Spiel](figs/bankJob.png)

>"Always remember that interactivity is what makes videogames different from any other form of >entertainment: books have stories, movies have visuals, games have interaction.  If your level >design isn’t showcasing your game mechanics, your players might as well be watching a movie or >reading a book."[@Taylor2013]

### Immersion

>"Immersion trägt maßgeblich dazu bei, dass Spieler ihre Zweifel beiseitelegen und gänzlich in die Spielwelt eintauchen"[@Hagen2019] 

Immersion beschreibt die Glaubwürdigkeit der Spielwelt. Dabei kommt es nicht darauf an, wie realistisch ein Spiel ist und wie gut die reale Welt abgebildet wird, sondern darum das ein Spiel und dessen Welt eine gewisse Kontinuität gegenüber den selbst erstellten Regeln aufweist. Das Erschaffen eines Immersiven Spielwelt ist nicht nur Aufgabe des Level-Designers, es zieht sich durch sämtliche Bereiche der Entwicklung.

Im Gameplay kann Immersion durch Konsequenz erreicht werden. Einmal aufgestellte Regeln sollten immer gelten, kann zum Beispiel ein Licht im Spiel ausgeschossen werden sollten alle Lichter im Spiel ausgeschossen werden können. Gegenstände sollte immer denselben Nutzen haben, kann zum Beispiel eine Zange genutzt werden, um Nägel aus Brettern zu ziehen, sollte der Spieler dies auch mit der nächsten Zange an einem anderen Brett tun können. Außerdem sollte der Spieler immer in der Lage sein, sein Wissen über die Regeln der Spielwelt nutzen zu können, um Aufgaben so zu lösen, wie er möchte.

Um im Leveldesign eine hohe Immersion zu erreichen, müssen vor allem die Platzierung von Gegenständen und die Auswahl des optischen Themas sinnvoll gewählt sein. Jedes Gebäude, jeder Gegenstand und jeder Charakter sollte einen, in der Spielwelt nachvollziehbaren, Grund haben genau dort zu sein, wo er ist. Auch sollten vom Spieler erwartete Gegenstände und Orte Vorhände sein, so braucht ein Büro Schreibtische und Computer, eine Restaurant braucht Toiletten und einen Lagerraum für Lebensmittel.

Das Spielgenre *Immersive Simulation* zeichnet sich dadurch aus, dass ein besonders hoher Fokus auf die Immersion gelegt wird. [@Brown2016] Warren Spector hält immersives Gameplay für den entscheidenden Faktor, der Videospiele von allen anderen Medien unterscheidet.  

>"Simulations allow players to explore not just a space but a 'possibility space'. They can make their own fun, tell their own stories, solve problems the way they want, and see the consequences of their choices. That's the thing that games can do that no other medium in human hisotry has been able to do."[@Spector2016]

### Balancing

>"The trick to good level design is to present challenges that are difficult enough to merit the players’ attention and make their heart or mind race, but not so difficult as to always leave them failing and disappointed."[@Ryan1999]

Das Balancing in Spielen ist entscheidend darüber, ob die Herausforderungen im Spiel als langweilig, spaßig oder frustrierend wahrgenommen werden. Da die Erfahrung mit Spielen von Spieler zu Spieler stark schwanken kann, und so eine Situation von einer Gruppe an Spieler als zu leicht und einer anderen Gruppe an Spielern als zu schwer empfunden werden kann, ist ein fester Schwierigkeitsgrad oft nicht Zielführend. Viele Spiele sind daher dazu übergegangen, den Spieler zu Beginn aus einen von mehreren Schwierigkeitsgraden auswählen zu lassen und so den Spieler selbst bestimmen zu lassen, wie schwer er seine Herausforderungen haben möchte.

Je nach Spiel hat die Auswahl des Schwierigkeitsgrades verschiedenen Auswirkungen. Für gewöhnlich verursachen Gegner je nach Schwierigkeitsgrad mehr oder weniger Schaden und sind leichter oder schwerer zu besiegen oder es werden mehr Gegner platziert sollte auf einen hohen Schwierigkeitsgrad gespielt werden. Einige Spiele verändern auch die Aufgaben des Spielers abhängig vom Schwierigkeitsgrad. Im Spiel *Thief* (1998) müssen Spieler auf den höchsten Schwierigkeitsgrad nicht nur mit aufmerksameren Wachen klarkommen, sondern auch zusätzliche Gegenstände stehlen. Im *Spiel Shadow of the Tom Raider* kann der Spieler den Schwierigkeitsgrad für Kampf, Rätsel und Erkundung separat einstellen, das ermöglicht eine genauere Konfiguration als nur eine Einstellung. [@DevPlay2019a]

Mark Brown betrachtet die Auswahl eines Schwierigkeitsgrades kritisch und sagte:  

>"[...] they (players) might pick the easier option even though they could handle more challenge, and rob themselves of the best, and designer-intended experience"[@Brown2016a]

Viele Spieler möchten das Spiel in der vom Entwickler vorgesehenen Art und Weise spielen und wählen deshalb den mittleren Schwierigkeitsgrad aus, und passen ihn nicht an ihre Bedürfnisse an.[@DevPlay2019a] Daher haben einige Spiele damit begonnen, den Schwierigkeitsgrad dynamisch anzupassen. So werden im Spiel *Resident Evil 4*, mehr Gegner platziert, wenn der Spiele besonders gut spielt, wenn er hingegen oft stirbt oder eine schlechte Trefferrate hat, werden weniger Gegner platziert um das Spiel einfacher zu machen.[@Brown2015] Im Spiel Half Life werden mehr Medikits platziert, wenn der Spieler grade besonders wenig Lebenspunkte besitzt.[@Brown2016b]

Nicht jedes Spiel eignet sich für unterschiedliche Schwierigkeitsgrade. Der Schwierigkeitsgrad von *Jump and Runs* wie *Super Mario* ist eng mit den Leveldesign und der Platzierung von Hindernissen verbunden. Level in Mario Spielen basieren oft auf eine, in diesen Level eingeführte, Mechanik wie schwebende oder rotierenden Plattformen. Zu Beginn des Levels wird den Spieler die neue Mechanik in einer sichereren Umgebung präsentiert. Hier kann der Spieler die Mechanik erforschen, ohne die Gefahr zu sterben. Im Laufe des Levels wird diese Mechanik immer weiter verändert, zum Beispiel, indem Plattformen nach einer Zeit herunterfallen oder Gegner auf diesen laufen. Dadurch bleibt die Mechanik spannend und zeitgleich erhöht sich der Schwierigkeitsgrad über den Verlauf des Levels. [@Brown2015a]

Durch Risk and Reward Situationen lassen sich besonders schwierige Momente als optional im Level platzieren (vgl. Abschnitt Risk and Reward).

Grundsätzlich gilt zu beachten, dass Spieler mit voranschreiten im Spiel immer besser werden, daher sollten frühe Level deutlich einfacher sein als Level gegen Ende des Spiels. Dabei muss die Schwierigkeit der Level nicht linear steigen, es bietet sich an nach einem besonders schweren Level ein einfacheres Level einzubauen, um den Spieler Zeit zum Aufatmen zu lassen.  

### Pacing

Pacing ist ein Begriff aus dem Film und beschreibt die Spannungskurve des Films. Abbildung 2.4 zeigt die Pacing-Kurve von *Star Wars: A new Hope*. Gut zu erkennen sind die verschiedenen Spannungspeaks, welche nach
kurzer Zeit wieder abflachen und so ein Konstrukt aus Spannenden und ruhigeren Szenen bilden. Zu Beginn steigt die Spannungskurve bereits stark an, um direktes Interesse beim Zuschauer zu wecken. Kurz vorm Ende kommt es zum
Spannungshochpunkt bevor das Ende sämtliche Spannung entlädt.

![Pacing.Kurve von Star Wars: A new Hope [@Wesolowski2009]](figs/newHope.gif){width=70%}

Aus diesem Beispiel lassen sich drei Regeln für gutes Pacing ableiten

1. Pacing verläuft nicht linear, nach Hochpunkten sollte ein Tiefpunkt folgen
2. Zu Beginn sollte viel Spannung erzeugt werden, um den Zuschauer bei stange zu halten
3. Kurz vor Ende sollte der spannendste Moment sein, der sich am Ende entlädt 

Auch in Videospielen ist die richtige Kontrollierung des Pacings entscheidend. Dies gilt sowohl für die Geschichte des Spiels als auch für jedes Level im Spiel. Gutes Pacing sorgt dafür, dass ein Spiel nicht langweilig oder repetitiv wird.[@Brown2018a] Die Kontrolle des Pacings zu behalten ist, je nach Genre, allerdings keine leichte Aufgabe, da die Kontrolle über Spielfigur und Kamera immer beim Spieler liegen. Ein gängiger Weg dies zu umgehen ist es, eine Filmartige Sequenz abzuspielen und den Spieler kurzzeitig sämtliche Kontrolle zu entziehen. So kann der Blick des Spielers gezielt auf eine bestimmte Stelle gerichtet werden oder die Spielfigur kann besonders geschickt die Gegner ausschalten. So kann zwar das Pacing kurzzeitig kontrolliert werden, es findet aber ein Bruch der Immersion statt, da das Handeln der Spielfigur vom Spieler getrennt wird.

Einige Spiele nutzen die Interaktivität des Mediums, um den Spieler seine eigene Spannungskurve aufbauen zu lassen. Abbildung 2.5 zeigt die möglichen Spannungsverläufe einer Begegnung im Schleichspiel *Thief*. Je nachdem wie der Spieler handelt entwickelt sich die Situation anders. So wird die Immersion des Spiels weiter verstärkt, jedoch wird die Kontrolle über das Pacing dem Spieler überlassen.  

![Beispiel Pacing im Schleichspiel Thief. [@Wesolowski2009]](figs/pacingThief.gif){width=80%}

Beide Verfahren haben ihr Vor- und Nachteile, und auch das Vermischen beider Verfahren ist denkbar, in jedem Fall hängt die Pacing-Kurve stark mit der Levelstruktur zusammen. Durch das Platzieren leichterer und schwerere Aufgaben, Aktion Passagen und Erkundungs Passagen, kann das Pacing in beiden Fällen beeinflusst werden.

Denn auch in Spielen, die den Spieler volle Kontrolle überlassen, kann zum Beispiel durch die Anzahl der Platzierten Gegner Spannung erzeugt werden. So könnte am Beispiel *Thief*, zu Beginn des Levels zwei Wachen platziert werden, welche umschlichen werden müssen, im Haus bekommt es der Spieler dann mit wenigen Wachen zu tun welche gut ausmanövriert werden können bevor es am Ende durch ein schwer bewachtes Gebiet mit weniger Versteckmöglichkeiten geht.  

### Environmental Storytelling 

Neben der eigentlichen Geschichte, welche durch Dialoge, Texte oder Zwischensequenzen erzählt wird, erzählen viele Spiele weitere, oft kleine, Geschichten durch die Gestaltung ihrer Level. 

>"Environmental storytelling is the art of arranging a careful selection of the objects available in a game world so that they suggest a story to the player who sees them."[@Stewart2015]

Environmental Storytelling wird oft genutzt, um die Spielwelt lebendiger zu gestalten, da es sowohl der Spielwelt als auch den Charakteren in dieser Spielwelt eine Geschichte verpasst, welche nicht unbedingt mit der eigentlichen Hauptgeschichte zusammenhängen muss.

Abbildung 2.6 zeigt eine Szene aus dem Spiel *BioShock*. Die Umgebung ist stark beschädigt. Das Schild links im Bild verrät, das, was immer auch passiert ist, wohl in der Neujahresnacht 1959 passiert sein muss. Das Plakat in der Mitte und die Maske auf den Tresen verraten, dass hier ein Maskenball veranstaltet wurde. Geht der Spieler in die Küche des Levels, befinden sich dort zwei maskierte Gegner, welche demnach wohl Gäste auf den Ball gewesen sind.

Dieses Beispiel zeigt, wie Environmental Storytelling genutzt werden kann, um ohne Worte eine Geschichte zu erzählen, welche sowohl der Spielwelt als auch den Gegnern Tiefgang verpasst.  

![Szene aus dem Spiel BioShock[@year-pictures.info2017]](figs/bioshock.jpg){width=100%}


### Navigation
>"Good level design is fun to navigate"[@Taylor2013]

Den Spieler durch das Level zu Navigieren ist eine wichtige Aufgabe, da schnell Frust aufkommen kann, wenn der Spieler den richtigen Weg nicht findet. Es gibt eine Vielzahl an unterschiedlichen Möglichkeiten den Spieler durch das Level zu navigieren.

Eine Methode ist es den Spieler direkt zu sagen, wo er lang muss. Das Spiel gibt den Spieler eine Art Kompass zur Hand, welche den Weg zum nächsten Ziel anzeigt. Je nach Spiel wird entweder nur die grobe Richtung angezeigt oder direkt der optimale Weg zum Ziel anhand einer Linie auf der Karte oder in der Spielwelt. Diese Methode verringert einerseits den Entdeckerdrang der Spieler, da diese genau wissen, wo sie lang müssen, als auch die Immersion des Spiels, da fliegende Pfeile oder blinkende Linien selten in die Spielwelt passen. [@Brown2015b]

Viele Spiele versuchen daher, den Spieler indirekt den Weg zu weisen. Dabei werden unterschiedliche, psychologische, Tricks verwendet.

So werden Pfeile in der Spielwelt genutzt, um den Blick des Spielers in eine bestimmte Richtung zu ziehen, Durchgang ähnliche Konstrukte konstruiert, durch die der Spieler hindurchlaufen will oder Gegenstände so verteilt, dass der Spieler sie bereits vom Weiten sieht und dorthin laufen möchte. Auch wird Licht und Bewegung genutzt, um die Aufmerksamkeit des Spielers auf einen gewissen Punkt zu ziehen. [@Graf2019] [@Brown2015c]

Im Spiel *Mirrors Edge* steht der Parkour lauf im Fokus. Das bedeutet der Spieler soll sich schnell über die Dächer der Stadt bewegen und Hindernisse bewältigen. Damit der Spieler nicht den korrekten Weg suchen muss, und so Geschwindigkeit verlieren würde, was den Flow des Spiels verschlechtern würde, wurde die *Runner Vision* implementiert. Die *Runner Vision* färbt alle Objekte die Genutzt werden können in Rot ein, und ermöglichen den Spieler das Intuitive navigieren durch die Welt. Abbildung 2.7 zeigt einen Ausschnitt aus *Mirrors Edge* mit aktiver Runner Vision.  

![Szene aus Mirrors Edge [@Taylor2013]](figs/mirrors_edge.jpg){width=100%}

Andere Spiele verwenden Farben diskreter um den Spieler durch die Welt zu Navigieren. In der neuen Auflage der *Tomb Raider* Serie oder im Action Spiel *Uncharted* werden kletterbare Kanten durch farbliche Anpassung hervorgehoben. [@Brown2015c]

Oft werden auch sogenannten *Weenies* verwendet, um den Spieler in eine Richtung zu führen. Diese Technik wurde von Walt Disney genutzt, der das Schloss in die Mitte von Disneyland platziert hat, um Besucher, die den Park betreten direkt dorthin zu locken. [@Brown2015c] In Videospielen sind Weenies oft *Points of Interest* wie besonders hohe Gebäude, Brücken oder Berge. [@Brown2015c] Abbildung 2.8 zeigt eine Szene aus dem Spiel *Journey*, der Berg links im Bild dient als Weenie und lockt den Spieler in diese Richtung.  

![Szene aus Journey. Im Hintergrund ist ein Berg zu erkennen. [@Kleffmann2019]](figs/journey.jpg){width=100%}

Auch in streng linearen Spielen kann gute Spieler Navigation hilfreich sein, da es das Pacing aufrechterhält und den Schein einer größeren Welt erzeugen kann

>"The game provides multiple paths for the player, and they feel like they have the freedom to explore whichever they choose. As they always seem to stumble upon new content - not entirely aware that they were subconsciously persuaded to take that path or enter that door - it stands to reason that all the other exits and doors lead to new play spaces too. It makes the world feel bigger and less linear that it really is. It also helps keep up the pace of the game."[Brown2015c]


### Risk and Reward

Im Laufe eines Spiels (und Levels), sollte es regelmäßig zu Risk and Reward Situationen kommen. Dies sind Situationen, in denen der Spieler ein höheres Risiko eingehen muss als gewohnt, z.B ein besonders starker Gegner
angreifen oder durch ein starkes Bewachtes gebiet schleichen, um bei Erfolg eine Belohnung zu erhalten. Die Belohnung sollte abhängig von der Höhe des Risikos der Herausforderung sein. Risk and Reward Situationen sollten stets optional sein und sind für besonders gute Spieler gedacht, um diese eine neue Herausforderung zu bieten und entsprechend zu belohnen. Die Art der Belohnung schwankt von Spiel zu Spiel und kann von Punkten für den Highscore, einer Abkürzung bis hin zu einer besonders starken Waffe oder einem optionalen Storystrang alles sein. Spieler werden durch Risk and Reward Momenten in spannende Entscheidungssituationen gebracht, ob sie die Herausforderung annehmen und Gefahr Laufen zu versagen oder auf Nummer sicher gehen aber dafür die Belohnung verpassen.

### Einzigartigkeit

Die unterschiedlichen Level im Spiel sollten zwar alle demselben Designkonzepten Folgen, sich jedoch merklich voneinander unterscheiden. Ähnelt sich die Levelstruktur der einzelnen Level zu stark oder werden dieselben Grafik-Assets immer und immer wieder verwendet, trübt dieses die Spielerfahrung.

>"[...] people don't like playing the same level twice."[@Ryan1999]

Um Level Einzigartiger zu gestalten, sollte eine große Variation an unterschiedlichen Settings, Gegner und Gameplay Herausforderungen geboten werden.

### Effizienz
Die Entwicklung von Spielen ist sehr teuer, Spiele kosten in der Regel mehrere Millionen Dollar, dennoch müssen Ressourcen effizient genutzt werden. [@DevPlay2017]
Ein Level-Designer baut daher nicht jedes Level von Grund auf neu, vielmehr entwickelt er ein modulares Set aus verschiedenen Events im Spiel. Durch die Kombination der einzelnen Teile dieses Sets, sowie leichten Anpassungen für speziellere Situationen, können viele unterschiedliche Situationen erschaffen werden.

>"Modular design is your friend – a smart designer won’t design a level, he/she will design a series >of modular, mechanic-driven encounters, that can be strung together to create a level.  And >another level.  And another level."[@Taylor2013]

## Prozedurale Levelgenerierung

Im folgenden Abschnitt wird das Prinzip der PCG, insbesondere der PLG beschrieben. Es werden die Vor- und Nachteile der PLG erörtert und bekannte Verfahren präsentiert. Zuerst folgt eine Erklärung was PCG bzw. PLG bedeutet und wo es angewendet wird. 

### Begriffserläuterung

Prozedurale Levelgenerierung (PLG) ist ein Teilgebiet der Prozeduralen Content Generierung (PCG). PCG im Allgemeinen beschreibt das automatische Erstellen von Inhalten. Im Kontext Videospiele sind diese unter anderen Texturen, Items, Musik und Soundeffekte, 3D-Modelle und auch Spielwelten. Dabei werden Inhalte nicht vollständig zufällig generiert, vielmehr werden handgebaute Inhalte verändert oder neu zusammengesetzt. Dabei bestimmen zufällig erzeugte Parameterwerte die genaue Art und Weise der Veränderung. [@Beca2017]

### Vor und Nachteile

PLG wird vor allem genutzt, um kosten bei der Entwicklung zu sparen.[@Remo2008] Ein guter Algorithmus kann auf Knopfdruck hunderte unterschiedliche Level generieren, für dessen Erstellung sonst mehrere Monate gebraucht werden würde.[@IntroversionSoftware2007] Der zweite große Faktor ist die, aus der theoretisch unendlichen Anzahl an unterschiedlichen Level, resultierende erhöhte Wiederspielbarkeit.[@Beca2017] Besonders das Roguelike Genre (vgl. Abs. 2.1.3) setzt auf PLG. Roguelike verwenden PLG zusätzlich als Gameplay-Feature. Durch sich stetig verändernden Level, kann der Spieler das Spiel nicht durch schieres Auswendiglernen der Levelstruktur gewinnen, er muss die Gameplay Mechaniken verstehen und meistern. [@Brown2019]

Die Implementation eines PLG ist allerdings nicht trivial. Die theoretisch endlose Anzahl an Level erschwert das Testen und beheben von Bugs. Evtl. wurde ein Fehler nur in den getesteten Level behoben, kommt aber in anderen Level wieder vor. [@Remo2008]

Die Einhaltung der in Abschnitt 2.2 vorgestellten Regeln für gutes Leveldesign ist bei PLG teilweise komplexer im Vergleich zu handgebauten Level.

Die Lösbarkeit der Level zu gewährleisten ist bei handgebautem Level schnell und verlässlich möglich. Der Generator im Gegenzug muss darauf programmiert sein, keine unpassierbaren Hindernisse auf den kritischen Pfaden zu platzieren oder benötigte Puzzleteile wie Türschlüssel so zu positionieren, dass sie erreicht werden können ohne, dass zuvor das Puzzle gelöst werden muss. Die Kontrolle des Pacings und Ausbalancierung des Schwierigkeitsgrades sind selbst für Designer eine schwere Aufgabe, die viel Zeit zur Optimierung benötigen[@DevPlay2019], für einen Level Generator ist diese eine komplexe und fast unlösbare Aufgabe. Die Schwierigkeit eines Levels zu bestimmen ist, je nach Genre, sehr schwer und wird noch zusätzlich dadurch erschwert, dass die generierten Level jederzeit im Spiel auftauchen können. Level sollten aber zu Beginn des Spieles einfacher sein, da der Spieler die Mechaniken erst kennenlernen muss und gegen Ende schwerer werden, um weiterhin eine Herausforderung zu bieten. Prozedural generierte Level können, aufgrund der verwendeten Muster, schnell repetitiv wirken. Das Spiel *No Man‘s Sky* wurde unter anderen deswegen von Spielern und Fachpresse scharf kritisiert. [Reinartz2016] Den Algorithmus muss genug Flexibilität gegeben werden, um Strukturen zu mutieren (s.a. Abs. 2.3.3.2).

Zusammengefasst lässt sich sagen, dass PLG besonders gut dafür geeignet sind, um kostengünstig eine Vielzahl an abwechslungsreichen, aber nicht einzigartigen, Level zu erschaffen, die es schaffen Spieler dazu zu motivieren, Spiele immer wieder zu spielen. Die Umsetzung guter PLGs ist nicht trivial und erfordert komplexe Regeln und Strukturen. Einige Faktoren lassen sich mithilfe eines PLGs nur schwer oder gar nicht umsetzten. So muss das Pacing beispielsweise nicht über das Leveldesign, sondern über Gamedesign Elemente gesteuert werden (vgl. Abs. Spelunky).  


### Prozedurale Level Generierung in der Praxis

Mittlerweile existieren eine Vielzahl an unterschiedlichen Algorithmen und Verfahren zur PLG. Im Folgenden wird eine Auswahl von ihnen Vorgestellt.

#### Random Walk

Der Random Walk Algorithmus, auch als *Drunkard‘s Walk* bekannt, wird eigentlich zur Generierung von nicht deterministischen Zeitreihen genutzt, um beispielsweise Aktienkurse in der Finanzmathematik zu modellieren. Er kann aber auch zur Erstellung höhlenartiger Level genutzt werden. Beim Random Walk bewegt sich ein, im leeren Dungeon gesetzter, Akteur solange zufällig durch das Dungeon bis er die gewünschte Anzahl an unterschiedlichen Felder passiert hat. Passierte Felder werden als begehbaren Boden interpretiert, unpassierte Feld als unbegehbare Wände.[@Wikipedia2019a]

Listing 2.1 zeigt wie ein einfaches 2D-Level mithilfe des Random Walk Algorithmus erzeugt werden kann. [@Read2014]  

\begin{lstlisting}[language=python, caption=Pseudocode des Random Walk Algorithmus]
	erstelle ein Level in dem alle Felder Wände sind
	wähle ein Feld als Startpunkt aus
	vewandel das gewählte Feld in einen Boden
	while noch nicht genug Boden im Level
	mache einen Schritt in eine zufällige Richtung
	if neues Feld ist Wand
		verwandel das neue Feld in einen Boden
\end{lstlisting}


![Durch Random Walk erstelltes Beispiel Level.](figs/randomWalk.png){width=50%}

Der Algorithmus bietet viele Parameter zum Verändern, so kann die Levelgröße, die gewünschte Bodenfläche und die Wahrscheinlichkeit in die jeweiligen Himmelsrichtungen zu gehen bestimmt werden.

Abbildung 2.9 zeigt ein, mit den Random Walk erstelltes, 50x50 großes Level mit 1200 Bodenfelder. Die Wahrscheinlichkeit in eine Richtung zu gehen lag bei jeweils 25%. Um das so erstellte Level tatsächlich bespielen zu können, müssen noch Start und Endpunkt auf Bodenflächen gesetzt werden. Das Verteilen von Monstern und Items auf Bodenflächen wäre auch möglich.

Der Algorithmus erzeugt vollständig verbundenen Level, mit unterschiedlich großen Räumen und Pfaden. [@Read2014] Die Lösbarkeit der Level kann so garantiert werden. Mit entsprechenden Skripts können Items abseits des Weges platziert werden und durch starke Monster geschützt werden, das kann zu guten Risk-Reward Momenten führen.  

#### Spelunky

*Spelunky* ist ein 2D-Roguelike-Plattformer, es verbindet Elemente klassischer Jump and Runs mit den prozedural generierten Level und Permadeath des Roguelike Genres. 2016 veröffentlichte der *Spelunky* Entwickler Derek Yu das gleichnamige Buch *Spelunky* in dem er auf die Entwicklungsgeschichte eingeht und unter anderem den Algorithmus zur prozeduralen Level Generierung erläutert. [@Yu2016]

![Ein Beispiel Level aus Spelunky, in Rot der kritische Pfad.[@Kazemi]](figs/spelunky.PNG){width=70%}

Jedes Level startet mit einem 4x4 Gitter aus jeweils 10x8 Felder großen leeren Räumen. Als erster Schritt der Generierung wird der kritische Pfad im Level generiert. Der kritische Pfad verbindet den Anfang des Levels mit dem Ende des Levels und repräsentiert so den Lösungsweg. 

```
1. Wähle einen zufälligen Raum aus der ersten Reihe und markiere diesen als Eingang
2. Wähle eine zufällige Zahl von 1 bis 5
      1. Ist die Zahl 1 oder 2, gehe einen Raum nach links
      2. Ist die Zahl 3 oder 4, gehe einen Raum nach rechts
      3. Ist die Zahl eine 5, gehe einen Raum nach unten
      4. Wird die Levelgrenze überschritten, gehe einen Raum nach unten
3. Wiederhole Schritt zwei, solange bis die Levelgrenze nach unten überschritten wird
4. Markiere den letzten Raum als Ausgang
```
 Während des Algorithmus werden alle Räume mit Zahlen markiert. 
```
 0. Raum wurde nicht passiert
 1. Raum braucht einen Ausgang links und rechts.
 2. Raum braucht einen Ausgang links, rechts und nach unten. Liegt ein weiterer zweier Raum 	  darüber, benötigt er zusätzlich einen Ausgang nach oben. 
 3. Raum braucht einen Ausgang links, rechts und nach oben.  
```
Die Markierung erfolgt entsprechend der Richtung, durch die der Raum betreten bzw. verlassen wird. Die Raumanordnung kann durch eine 4x4 Matrix dargestellt werden. So ist garantiert, dass entlang des kritischen Pfades eine lösbare Raumabfolge generiert wird. Das Problem der Level Lösbarkeit (vgl. Abs. 2.2) wird dadurch gelöst.

Den Räumen abseits des kritischen Pfades werden zufällig eine der Nummern zugewiesen. Dementsprechend werden sie entweder an den Pfad angeschlossen oder sind von ihm abgeschottet. *Spelunky* bietet den Spieler die Möglichkeit Wände mit Bomben zu sprengen oder mithilfe von verschießbaren Kletterseilen steil nach oben zu klettern. So können selbst vermeidlich unerreichbare Räume betreten werden, vorausgesetzt der Spieler hat die nötigen Items dafür gefunden.

Abbildung 2.10 zeigt eine generiertes *Spelunky* Level. Die rote Linie entspricht dem kritischen Pfad, die Zahlen in den Raumecken die entsprechende Nummerierung. Die dunkelroten Blöcke zeigen noch einmal die Bedeutung der Nummerierung, und sind im eigentlichen Level nicht zu sehen.

Um die Räume mit Inhalt zu füllen wird, abhängig von der Nummerierung des Raumes, eines von mehreren unterschiedlichen Templates verwendet. Die Templates geben an, an welcher Stelle welche Art von Block platziert wird. Um die Variation trotz geringer Template Anzahl zu erhöhen, werden einige Bereiche der Templates mutiert.

Jedes Template wird durch einen String dargestellt und kann als 10x8 Matrix verstanden werden. Jedes Zeichen in der Matrix repräsentiert die Art des Blocks an der entsprechenden Stelle (siehe Tabelle 2.3). Einige Felder, genannt Chunks, ersetzten eine 5x3 Fläche durch eines von zehn vorgefertigten Templates. Durch die Zufallselemente lassen sich auch mit wenigem Raum-Templates eine Vielzahl unterschiedlich aussehender Räume generieren.  

| Referenz | Ersetzen durch |
| ----| ---- |
|     0 | Freie Fläche |
|     1 | Solider Block |
|2      | zufällig Freie Fläche oder Solide Block |
| 4        | schiebbarer Steinblock                  |
| 6        | 5x3 Chunk                               |
| L        | Leiterteil                              |
| P        | Leiterteil mit Fläche zum Stehen        |
Table: Referenz Tabelle für die Generierung von Spelunky Level [@Yu2016]

Um Monster und Items zu verteilen, wird zum Schluss für jedes als **1** gekennzeichnetes Feld entschieden, ob zum Beispiel ein Monster darauf oder darunter platziert wird. Bei der Platzierung nehmen auch umliegende Felder Einfluss, so werden beispielsweise Truhen bevorzugt in Nischen platziert. So werden Spieler, die auch abseits des kritischen Pfades suchen, mit Items oder Schätzen belohnt.

Um optische Abwechslung zu bieten werden die Level mit unterschiedlichen visuellen Themen erstellt, so gibt es unter anderem Dschungel-, Eis- und Feuerlevel. Das Pacing wird in *Spelunky* nicht durch das Leveldesign kontrolliert, sondern mithilfe eines Geistes. Der Geist spawnt dann, wenn der Spieler zuviel Zeit in einem Level verbracht hat und führt bei Berührung zum sofortigen Game Over. Dadurch muss der Spieler regelmäßig die Entscheidung treffen, ob er den Levelrand noch nach weiteren Items absucht und Gefahr läuft vom Geist getötet zu werden oder sich sofort zum Levelausgang begibt, dafür aber keine Items oder Schätze mitnehmen kann, was den weiteren Fortschritt erschwert (vgl. Abs. Risk and Reward).  

In seinem Buche schrieb Yu:

>"This system doesn‘t create the most natural-looking caves ever, and players will quickly begin to recognize certain repeating landmarks and perhaps even sense that the levels are generated on a grid. But with enough templates and random mutations, there‘s still plenty of variability. More >importantly, it creates fun and engaging levels that the player can‘t easily get stuck in, something much more valuable than realism when it comes to making an immersive experience"[@Yu2016]

#### Graph Based

In dem Paper *Game Level Layout from Design Specification* präsentierten unter anderen Chongyang Ma, einen Algorithmus, der dazu in der Lage ist, Level aus planaren Graphen zu generieren. Der Generator kann, mithilfe weniger vorgegebener, unterschiedlich geformter, Räume, aus einem Graphen gleich mehrere unterschiedliche Level Generieren. [@Ma2014]

Abbildung 2.11 zeigt einen, zur Generierung verwendbaren, planaren Graphen der das Levellayout vorgibt. Die Knoten repräsentieren Räume, die Kanten stellen Verbindungen zwischen den Räumen dar. Abbildung 2.12 zeigt eine Auswahl an unterschiedlichen Räumen. Abbildung 2.13 zeigt den daraus erzeugten Level.  

![Graph zeigt das Levellayout[@Ma2014]](figs/inputGraph.PNG){width=40%}

![Formen aus den das Level gebaut wird[@Ma2014]](figs/buildingBlocks.PNG){width=30%}

![Erzeugtes Level [@Ma2014]](figs/outputlevel.PNG){width=100%}

Ziel des Algorithmus ist es, jeden Knoten eine Form und eine Position zuzuteilen, sodass die durch den Graphen vorgegeben Levelstruktur erreicht wird. Dabei dürfen sich Räume nicht überlappen und Verbindungen müssen mithilfe von Türen möglich sein. Zwar könnte man einfach jede Möglichkeit durchspielen, dies wäre aber höchst ineffizient. Stattdessen wird der Graph zuerst in Subgraphen zerlegt, um die Komplexität des Problems aufzuteilen, diese Subgrafen werden von Ma als "Chains" bezeichnet. In Chains haben Knoten maximal zwei Nachbarn. Gültige Layouts für Chains zu finden ist deutlich einfacher als direkt den ganzen Graphen zu lösen. Zuerst wird eine Chain gelöst, indem immer zwei Räume genommen werden, wobei die Position des ersten Raums fest ist, der zweite Raum kann bewegt und rotiert werden. Dieses Verfahren wird wiederholt, bis ein gültiges Layout für die komplette Chain gefunden wurde. Dann wird eine weitere Kette dem Layout hinzugefügt, solange bis das gesamte Level erzeugt wurde. Da für jede Chain mehrere mögliche Layouts existieren, wird im Falle das kein gültiges Layout für das Kombinieren zweier Chains gefunden werden kann, solange Backtracking betrieben bis das Level gelöst ist. [@Ma2014]

Der Algorithmus bietet gleich mehrere Vorteile. Da das Levellayout vorgegeben wird, kann der Designer gezielt das Pacing des Levels beeinflusse. Durch den Graphen ist deutlich zu erkennen, welche Räume auf den kritischen Pfad liegen und welche Räume optional sind, Gegner und Items können entsprechend platziert werden. Im Vergleich zu anderen Generatoren werden allerdings viele Inputdaten benötigt. Der Algorithmus bietet die Möglichkeit abwechslungsreiche Level zu erschaffen, dafür müssen aber sowohl der Graph als auch die Inputräume gut gestaltet werden.

Ondřej Nepožitek optimierte und erweiterte den Algorithmus noch um mehrere Features. So kann seine Version unter anderem Räume mit Fluren verbinden. [@Nepozitek2018]  


## Genetische Algorithmen 

### Grundlagen

Da der für diese Arbeit entwickelte Level Generator auf Genetischen Algorithmen basiert, werden in diesen Abschnitt alle, zu dem Verständnis dieser Arbeit, relevanten Grundlagen erklärt. Es existiert eine Vielzahl an Literatur über Genetische Algorithmen, die hier präsentierten Informationen basieren auf Russell und Norvigs „Artificial Intelligence: A
Modern Approach“(Kapitel 4.1.4)[@RussellundNorvig2014]  und Volker Nissens „Einführung in Evolutionäre Algorithmen“[@Nissen2013]

### Definition

>"Evolutionäre Algorithmen (EA) sind Optimierungsverfahren, die sich am Vorbild der biologischen Evolution orientieren." [@Selzam2006]

Vereinfacht ausgedrückt: Evolutionäre Algorithmen sind ein Verfahren zur kontrollierten und gesteuerten Zufallssuche. 

Es gibt vier, aus der Historie entstandenen unterscheidungsformen:

- Genetische Algorithmen (GA)
- Genetische Programmierung (GP)
- Evolutionsstrategien (ES)
- Evolutionäre Programmierung (EP)

Diese Variationen ähneln sich von Aufbau und Struktur sehr. Im Folgenden werden GA betrachtet, zum Abschluss werden die wichtigsten Unterschiede zu den anderen Variationen erläutert.  

### Grundbegriffe aus der Genetik

Da EA sich an der Evolution orientieren, haben viele Fachbegriffe der Genetik ihren Weg in die Informatik gefunden. Im Folgenden werden die, für Genetische Algorithmen und diese Arbeit, relevanten Begriffe in ihrer Bedeutung innerhalb der Informatik, erläutert. 

**Individuum / Chromosom** 

>"Ein Individuum im biologischen Sinne ist ein lebender Organismus, dessen
>Erbinformationen in einer Menge von Chromosomen gespeichert ist. Im Zusammenhang mit >genetischen Algorithmen werden die Begriffe Individuum und
>Chromosom jedoch meistens gleichgesetzt." [@Selzam2006]

**Gen**

Ein Gen ist genau eine Sequenz im Individuum. Je nach Kontext kann es sich hierbei um eine einzelne Stelle oder mehrere Stellen im Individuum handeln.

**Allel**

Allel beschreibt den exakten Wert eines Gens. 

**Population / Generation**

Einer Menge gleichartiger Individuen wird als Population bezeichnet. Die Anzahl der Individuen gibt die Populationsgröße an. Sterben Individuen oder werden neune geboren, verändert sich die Größe der Population. Betrachtet man eine Population über mehrere Zeitpunkte, spricht man von Generationen. 

### Ablauf

GAs folgen einer Reihe an Subroutinen, die sich so lange Wiederholen bis eine Abbruchbedingung erreicht ist.

![Ablauf eines generischen GAs.](figs/gaAblauf.png){width=80%}

Abbildung 2.14 zeigt den zugrunde liegenden Ablauf von GAs. 
```
1. Kodierte Startpopulation wird erzeugt
2. Aktuelle Generation wird bewertet
3. Es wird geprüft, ob die Abbruchbedingung erreicht ist
   4. Wenn nein
     2. Es werden Individuen für die neue Generation ausgewählt
      3. Manche der ausgewählten Individuen werden miteinander rekombiniert und erzeugen so neue 		 Individuen
      4. Einige der Gene der neuen Generation werden mutiert
      5. Die neue Generation wird zu Startgeneration für den nächsten Durchlauf
      6. Go to 2
5. Ausgabe der Lösung(en)
```
Im Folgenden werden die einzelnen Subroutinen genauer beschrieben, sowie gängige Implementationen gezeigt. 

### Kodierung

Zu Beginn muss das betrachtete Problem kodiert werden. Das bedeutet das alle relevanten Aspekte auf ein Chromosom abgebildet werden müssen.

Bei der Binären Kodierung besteht ein Chromosom aus n vielen Genen. Jedem Gen wird ein binärer Wert zugewiesen und repräsentiert dabei eine Problemvariable. Aus den Genen wird dann ein Bitstring erzeugt.  

$$ g=(g_{ 0  }....g_{ m }) \in\left\{ 0,1 \right\}^{ m }  $$

Verwendet man die Binäre Kodierung, muss man am Ende die Lösung mithilfe einer Dekodierungsfunktion zurückwandeln.

$$ T: \left\{ 0,1 \right\}^{ m } \to R^{ n } $$


Die Allel der Gene der Startpopulation werden zufällig bestimmt. 

### Bewertung

Die Bewertung erfolgt mithilfe der sogenannten Fitnessfunktion. Die Fitnessfunktion $F$ bewertet die Güte eines Individuums, also wie nahe es schon an einer möglichen Lösung ist. Dabei weist die Fitnessfunktion dem Individuum eine reelle Zahl zu, welche die Fitness repräsentiert. Im Regelfall ist eine höhere Fitness besser als eine geringer.

Die Implementation der Fitnessfunktion ist stark mit der eigentlichen Problemstellung verwoben. Da die Fitnessfunktion großen Einfluss darauf hat, in welche Richtung sich die Population entwickelt, sind die Bewertungskriterien so zu wählen, dass sie zur Erreichung der Lösung beitragen. Da die Fitnessfunktion während der Laufzeit jedes einzelne Individuum jeder Population jeder Generation betrachtet, sollte bei der Implementierung auf Laufzeitoptimierung geachtet werden, eine komplexe Fitnessfunktion kann den gesamten GA verlangsamen.  

### Selektion

Bei der Selektion werden Individuen der Ausgangspopulation ausgewählt und in den "mating pool" kopiert, der die Grundlage für die später folgenden Mutation und Rekombination ist. Zu beachten ist, dass ausgewählte Individuen nicht aus der Ausgangspopulation entfernt werden, sie können also mehrfach ausgewählt werden. Bei den gängigsten Selektionsverfahren spielt der Fitnesswert der einzelnen Individuen eine große Rolle, da er bestimmt, wie hoch die Wahrscheinlichkeit ist in den mating pool aufgenommen zu werden. Jedoch sollte man nicht nur die besten Individuen der Population auswählen, da nicht nachvollzogen werden kann, ob sich das Individuum in der Nähe des globalen Hochpunkts befindet oder sich lediglich einen lokalen Hochpunkt nährt. Würde man nur die besten Individuen erlauben sich zu vermehren, würde sich die Population in eine Richtung festfahren. Die Selektion baut einen Fitnessdruck auf, da das Überleben eines Individuums stark von seiner Fitness abhängt. Der Fitnessdruck entspricht daher den *Survival of the fittest* Gedanken der Evolutionstheorie.

Für GA gilt: $\mu$  Eltern erzeugen $\mu$ Kinder, daher: die Populationsgröße bleibt über den gesamtem GA Zeitraum konstant. 

Es folgt eine Auflistung und Erklärung bekannter Selektionsverfahren.

**Fitness Proportionate Selection** 

Bei der Fitness Proportionate Selection hat jedes Individuum die Chance ausgewählt zu werden. Die Chance ausgewählt zu werden ist abhängig von der Fitness des Individuums. Besonders gute Lösungen haben also hohe Chance ausgewählt zu werden, schlechtere Lösungen können dennoch ausgewählt werden, um so die viel fallt der Population zu gewährleisten.

Eine gängige Art der Umsetzung dieses Verfahren ist die **Roulett Wheel Selection**. Angelehnt an Glücksräder, wird ein Rad in $n$ Teile zerteilt, wobei  

$$n=\sum_{j}^{}{ F(g_{j}) }$$

gilt. Jedes Individuum der Population enthält entsprechend seiner Fitness Anteile am Rad. Am Rad wird ein Fix Punkt angesetzt, das Rad wird rotiert und das Individuum ausgewählt auf dessen Anteil der Fix Punkt stehenbleibt.

![Bildliche Darstellung der RWS [@tutorialspoint]](figs/roulette_wheel_selection.png){width=100%}

Die Wahrscheinlichkeit $p_{sel}$ eines Individuum $g_{k}$ mit der Fitness $F(g_{k})$ ausgewählt zu werden lässt sich wie folgt berechnen

$$p_{ sel }(g_{ k })=\frac{F(g_{ k })}{\sum_{ j }^{ }{F(g_{ j })  }}$$

Das **Stochastic Universal Sampling** erweitert die Roulett Wheel Selection um einen zweiten Fixpunkt. So können zeitgleich zwei Individuen ausgewählt werden. 

Fitness Proportionate Selektionsverfahren funktionieren nicht in Fällen, in dem Fitnesswerte negativ sein können. 

**Tournament Selektion**

Bei der Tournament Selektion werden zufällig $k$ Individuen aus der Ursprungspopulation ausgewählt, das Individuum mit der höchsten Fitness wird in die nächste Generation aufgenommen. Dieses Verfahren ermöglicht zwar auch schlechteren Lösungen ausgewählt zu werden, versichert aber das die schlechtesten k-1 Lösungen nicht ausgewählt werden können und die beste Lösung auf jeden Fall ausgewählt wird. Tournament Selektion funktioniert auch bei
negativen Fitnesswerten.

![Bildliche Darstellung der TS [@tutorialspoint]](figs/tournament_selection.png){width=100%}

**Rank Selektion**

Bei der Rank Selektion wird die Population anhand der Fitnesswerte der Lösungen sortiert. Für die Auswahl spielt nicht mehr der Fitnesswert, sondern die Platzierung der Lösung eine Rolle. Höher platzierte Lösungen haben eine höhere Chance ausgewählt zu werden als niedrig platzierte Lösungen. Der Chancenunterschied ist je nach Problemstellung zu wählen. Rank Selektion kann auch bei negativen Fitnesswerten verwendet werden.

**Zufällige Selektion**

Bei der zufälligen Selektion werden zufällig Individuen aus der Population ausgewählt. Dieses Verfahren wird für gewöhnlich vermieden, da es keinerlei Filtermechanismen gibt und die Suche nicht gesteuert werden kann.

##### Rekombination

Bei der Rekombination werden zwei Individuen aus dem mating pool neu zusammengesetzt. Die beiden ursprünglichen Individuen bezeichnet man als Eltern, die neu erzeugten Individuen als Kinder.

Ob zwei Eltern miteinander rekombiniert werden, ist abhängig von der festgelegten Crossoverchance. In GAs liegt diese für gewöhnlich bei $\approx$ 60%. Die Rekombination vermischt den Genpool der Eltern und soll so für eine möglichst diverse Population sorgen. Kommt es zur Rekombination werden die Kinder in die neue Population gelegt, kommt es zu keiner Rekombination werden die Eltern in die neue Population kopiert.

Es folgt eine Erläuterung einiger verbreiteter Crossoververfahren. Je nach Problemstellung kann auch ein Individuelles verfahren Zielführend sein.  

**Point Crossover**
Beim Point Crossover verfahren werden beide Eltern an einer oder mehrere Stellen in Segmente geteilt. Die Segmente der Eltern werden miteinander vertauscht, um die Kinder zu erzeugen. 

![Bildliche Darstellung des Multi Point Crossoververfahren [@tutorialspoint]](figs/multi_point_crossover.png){width=100%}

**Uniform Crossover**

Beim Uniform Crossover wird jedes Gen eines Elternteils betrachtet, es gibt eine 50% Chance, dass das Gen mit dem entsprechenden Gegenstück des anderen Elternteils ausgetauscht wird.

![Bildliche Darstellung des Uniform Crossoververfahren [@tutorialspoint]](figs/uniform_crossover.png){width=100%}

### Mutation

Die Mutation ist eine zufällige Veränderung der Gene um eine neue Lösung zu erhalten. Genau wie die Rekombination wird sie genutzt, um eine möglichst große Diversität der Population zu erzeugen. Durch Crossover erzeugte Kinder liegen im Suchbaum in der Nähe der Eltern, durch die Mutation werden die Kinder von den Eltern weiter entfernt.

Die Mutationschance $p_{mut}$ ist gering anzusetzen, da eine zu hohe Mutationschance den GA zu auf eine zufällige Suche reduzieren würde.

Es folgt eine Auflistung und Beschreibung bekannter Mutationsverfahren. Je nach Problemstellung kann auch eine spezifische Mutationsmethode zielführend sein. Je nach Kontext kann ein einzelnes Gen oder eine Sequenz an Genen mutiert werden. Für die folgenden Verfahren muss man dann beachten, dass eine Sequenz auch als Gen bezeichnet wird. Je nach Bedarf kann nur eine Mutation pro Lösung durchgeführt werden oder es kann jedes Gen mit einer Chance von $p_{mut}$ mutiert werden.  

**Bit Flip Mutation** 

Bei der Bit Flip Mutation wird ein oder mehrere Bits gewechselt. Bit Flip Mutation kann daher nur bei Binär Kodierten GAs verwendet werden. Abwandlungen für Reellwertige Kodierungen sind aber möglich.

**Swap Mutation**

Bei der Swap Mutation werden zwei Zufällig ausgewählte Gene miteinander vertauscht. 

**Scramble Mutation**

Bei der Scramble Mutation wird eine Auswahl an zusammenhängenden Genen vermischt, um so die Reihenfolge zu ändern. 

**Inversion Mutation**

Bei der Inversion Mutation wird die Reihenfolge einer Auswahl an zusammenhängenden Genen umgedreht. Das erste Gen in der Sequenz wird zum letzten und umgedreht. 

### Abbruchbedingung

Die Abbruchbedingung ist für gewöhnlich dann erreicht, wenn eine gültige Lösung gefunden wurde. Je nach Problemstellung kann es auch das Überschreiten eines gewissen Fitnessschwellwerts sein oder der Durchlauf einer bestimmten Generationenanazahl. Es bietet sich an einen Neustart des GAs vorzunehmen, sollte nach einer gewissen Generationsanzahl keine Lösung gefunden worden sein. Der entsprechende Schwellwert sollte so gewählt werden, dass Erfahrungsgemäß keine Steigerung der Fitness zu erwarten ist und ist dementsprechend für jede Implementierung unterschiedlich.

### Vor und Nachteile

| Pro                                                         | Contra                                                       |
| ----------------------------------------------------------- | ------------------------------------------------------------ |
| Gut geeignet für Probleme in großen Suchräumen              | Laufzeit in kleinen Suchräumen oft länger als andere Verfahren |
| Benötigt keine abgeleiteten Informationen des Problems      | Laufzeit stark von der Komplexität der Fitnessfunktion abhängig |
| Lässt sich gut parallelisieren                              | Lösungen werden Zufällig gefunden, es gibt keine Garantie die beste Lösung zu finden. |
| Liefert eine Menge an guten Lösungen, nicht nur eine Lösung |                                                              |
| Verläuft sich nicht in lokalen Hochpunkten                  |                                                           |
Table: Vor- und Nachteile Evolutionärer Algorithmen

### Variationen

Die mit GA vergleichbarste EA Variante sind die ES. ES folgen einer ähnlichen Struktur wie GA. Die Verfahren unterscheiden sich aber an zwei Punkten drastisch voneinander. 

ES werden reellwertig kodiert. Die reellwertige Kodierung funktioniert ähnlich zu der Binären Kodierung, nur wird hier jedem Gen ein reellwertig Wert zugewiesen. Eine Dekodierung ist nicht nötig.

$$ g=(g_{ 0  }....g_{ m }) \in \left\{ R \right\}^{ m } $$  

Durch diese Art der Kodierung sind einige, aus GA bekannten, Verfahren zur Mutation nicht verwendbar (Bsp. Bit-Flip-Mutation). 

Der zweite große Unterschied liegt in der Auswahl der Eltern für die nächste Population. Noch bevor es zur Bewertung der Individuen kommt, werden $\lambda$ Individuen zufällig aus den $\mu$ großen Ausgangspopulation ausgewählt, sie bilden den mating pool, wobei $\lambda\leq\mu$ gilt. Einige Individuen müssen daher öfters in den mating pool vorkommen. Jetzt findet ähnlich zu GA die Rekombination und Mutation des mating pool statt. Die Individuen des mating pool werden jetzt mithilfe der Fitnessfunktion bewertet und mithilfe eines Selektionsverfahren werden $\mu$ Kinder für die nächste Generation ausgewählt. Die Populationsgröße bleibt also zwischen den Generationen konstant. 

Welches Verfahren geeigneter ist, hängt stark mit der eigentlichen Problemstellung zusammen. Mittlerweile sind auch viele Mischformen zwischen ES und GA verbreitet. 

>"Beide Verfahren beginnen mit einer breiten Verteilung der Individuen im Suchraum.
>Abgeschlossen wird sowohl bei den ES als auch bei den GA mit der Konzentration der
>Individuen auf einen minimalen Ausschnitt des Lösungsraums. In den ES wie auch in
>den GA wurde eine noch immer wachsende Anzahl von Varianten entwickelt. Durch
>diese nicht mehr überschaubare Varianten verschwimmen die Grenzen zwischen den
>Verfahren. Es kommt sogar vor, dass bei Genetischen Algorithmen anstelle der üblichen
>binären Vektoren, für bestimmte Problemstellungen reelle Vektoren eingesetzt werden.
>Welcher Algorithmus bei welcher Problemstellung besser ist, kann nicht eindeutig
>entschieden werden."[@Lienemann2004]

Bei der Genetischen Programmierung geht es darum, mithilfe von Bäumen Probleme der symbolischen Regression zu lösen. Evolutionäre Programmierung nimmt sich ähnlicher Problematiken an, verwendet aber endliche Automaten zur Lösung der Probleme.  

