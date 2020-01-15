# Shattered Pixel Dungeon

Um ein Verständnis dafür zu bekommen, welche Art von Spiel in den vom Generator erzeugten Level gespielt wird, werden in diesen Abschnitt die Grundkonzepte von Computer Rollenspiele insbesondere des Subgenres Rouge-Like beschrieben. Danach wird das Spiel: Shattered Pixel Dungeon noch einmal genauer beleuchtet, da dieses Inspiration für das neue Praktikums Konzept ist. 

## Rollenspiele

Das Genre der Computer Rollenspiele entstand aus den klassischen Pen and Paper Rollenspiel. Der Spiele schlüpft in die Rolle einer oder mehrere Spielfiguren mit unterschiedlichsten Fähigkeiten um verschiedene Aufgaben zu erledigen, Erfahrung zu sammeln und Ausrüstung zu erbeuten. Durch die Inspiration klassischen Pen and Paper Spiele, spielen unterschiedliche Charakterwerte, sowie die werte der Ausrüstung eine entscheidende Rolle. Neben der Erfüllung der Aufgaben, um in der Geschichte weiterzukommen. ist essenzieller Bestandteil, die Charakterwerte durch Erfahrung aufzuwerten, neue Fähigkeiten zu erlernen und die Ausrüstung zu verbessern. <https://de.wikipedia.org/wiki/Computer-Rollenspiel>

Viele Spiele lassen den Spieler bereits zu beginn eine von mehreren Charakterklassen wählen. Die Klasse der Figur bestimmt meist, welche Ausrüstung sie benutzen kann und welche Fähigkeiten sie erlernen kann. Vor allem seid den großen Erfolg der Elder Scrolls Spiele, ermöglichen es allerdings immer mehr Spiele, die Klasse während des eigentlichen Spielens zu bestimmen, indem Fähigkeitspunkte frei verteilt werden können. So kann der Spieler auch verschiedene Klasse miteinander vermischen.

![Charakter auswahl zu beginn des Spiels Dragon Age 2 <https://www.gamestar.de/artikel/dragon-age-2-tipps-tricks-zum-rollenspiel,2321599.html](figs/da2char.jpg){width=80%}

![Fähigkeitenübersicht im Spiel Risen https://www.spieletipps.de/artikel/2018/1/ ](figs/Risen.jpg){width=80%}



Klassische Computer Rollenspiele führen den Spieler so durch eine handgebaute Welt und erzählen dabei ihre Geschichte. Viele Rollenspiele ermöglichen es den Spieler, direkten Einfluss auf den Ausgang der Geschichte zu nehmen. So kann der Spieler im Rollenspiel The Witcher 2: Assassins of Kings, im ersten von drei Akten eine Entscheidung treffen, die Einfluss darauf nimmt, von welcher Seite der im Spiel gezeigte Konflikt betrachtet wird. Auch dürfen weniger wichtige Entscheidungen vom Spieler übernommen werden, die zwar weniger Einfluss auf das große ganze nehmen, jedoch die Immersion verbessern.

Neben der Geschichte spielt der Kampf die zweite Große Rolle in Rollenspiele. Im laufe der Zeit haben sich zwei Kampfsysteme im Markt etabliert. Rundenbasierte Kampfsysteme bleiben der Pen and Paper Vorlage treu, und lassen Spieler und Monster nacheinander ihre Angriffe tätigen. In Actionbasierten Kamfpsystemen wird der Kampf in Echtzeit ausgetragen, hier spielen vor allem Treffervermögen und Ausweichtalent eine rolle. 

![Rundenbasierte Kampfszene aus Final Fantasy 7](figs/ff7.jpg){width=50%}

![Kampfszene aus den Actionrollenspiel Dark Souls](figs/darksoulskampof.jpg){width=50%}

## Rouge und Rouge-Like

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

## Shattered Pixel Dungeon

"**Shattered Pixel Dungeon** is a Roguelike RPG, with pixel art graphics and lots of variety and replayability. Every game is unique, with four different playable characters, randomized levels and enemies, and over 150 items to collect and use. The game is simple to get into, but has lots of depth. Strategy is required if you want to win!" <https://pixeldungeon.fandom.com/wiki/Mod-Shattered_Pixel_Dungeon>

Zu beginn des Spiels wählt der Spieler eine der vier Charakterklassen aus. Zur Auswahl stehen:<https://pixeldungeon.fandom.com/wiki/Mod-Shattered_Pixel_Dungeon/Classes#Rogue>

- Krieger: Nahkämpfer mit Fokus auf hohe stärke und Lebensenergie
- Magier:Fernkämpfer der Zauber verwendet
- Schurke: Nahkämpfer der auf Planung und Hinterhalte fußt 
- Jäger: Fernkämpfer der vor allem Bögen verwendet

Der Spieler startet auf der obersten ebene eines Dungeons. Ziel ist es, möglichst tief in das Dungeon einzudringen, je tiefer der Spieler ist, desto schwieriger wird das Spiel. In den einzelnen Ebenen begegnen den Spieler die unterschiedlichsten Monster, durch dessen Tötung der Spieler Erfahrung sammelt, wurde genug Erfahrung gesammelt, steigt der Spieler auf, dies ermöglicht ihn, seine Spielfigur mit neuen Fähigkeiten auszustatten. Ebenso lassen besiegte Gegner Ausrüstungsgegenstände wie Heiltränke oder Waffen fallen. Um Erfolg im Spiel zu haben, sollte man also nicht nur versuchen möglichst schnell möglichst tief in das Dungeon einzudringen, sondern auch viele Gegner zu besiegen um an Erfahrung und Items zu gelangen. Das Spiel wird genau wie Rouge Rundenbasiert gespielt. 

Darüber hinaus bietet Shattered Pixel Dungeon noch viele weiter Features wie Subklassen oder Verzauberungen, welche hier nicht weiter beschrieben werden, da diese für das Verständnis des Spielablaufes nicht nötig sind.

![Beispielausschnitt für das Leveldesign in Shattered Pixel Dungeon <https://www.holarse-linuxgaming.de/wiki/pixel_dungeon>](figs/spdss.png){width=50%}

