# Zusammenfassung



## Fazit

In dieser Arbeit wurde ein Konzept für einen Levelgenerator basierend auf einen Genetischen Algorithmus vorgestellt und umgesetzt. Der Generator ist in der Lage lösbare Level zu erzeugen, welche mithilfe des Implementierten Parsers in das PM-Dungeon der Studenten integriert werden kann. Der Generator ist eigenständig dazu in der Lage, optisch ansprechende und abwechslungsreiche Level zu erzeugen. Der Schwierigkeitsgrad des Level kann durch die zufällig Platzierten Monster kontrolliert werden. Die Level enthalten neben den kritischen Pfad, optionale Wege welche zu spannenden Risk and Reward Situationen führen können. Items und Monster können nicht gezielt in optionalen Räumen platziert werden. 

Mithilfe des Parsers kann die Texturenkarte für das Level generiert werden, obwohl das angewendete Verfahren eine menge an RAM benötigt. Die Studenten können eigens programmierte Monster, Items und Spezialfelder zufällig im Level verteilen.

 Es wurde eine Möglichkeit präsentiert, den Levelgenerator zur Generierung von Räumen zu nutzen. Es wurden verschiedene Verfahren gezeigt um Räume so zusammenzusetzten das sie interessante und abwechslungsreiche Level erzeugen. Die Verfahren sind noch nicht ausgereift und liefern jede glich einen Ausblick auf mögliche Ergebnisse. 

Nur das Spelunky-Style Verfahren ist dazu in der Lage Türen und Schlüssel im Level zu platzieren, für die andern Verfahren fehlt die Möglichkeit gezielt Raumstrukturen zu erkennen um Türen sinnvoll zu platzieren. 

Diese Arbeit zeigt, das bereits eine einfacher GA mit wenigen Informationen dazu in der Lage ist, optisch ansprechenden und abwechslungsreiche Level zu erzeugen. Der entwickelte GA löst alle an das Projekt gestellte Anforderungen, mit Ausnahme der Platzierung von Türen und Schlüssel, und erfüllt zusätzlich viele der präsentierten Regeln für gutes Leveldesign. 

Mithilfe des Generators können die Studenten unterschiedliche Level in ihr Spiel integrieren um damit ihr PM-Dungeon zu testen und zu spielen. 

## Ausblick

Der erstellte Algorithmus bietet viele Möglichkeiten der Weiterentwicklung und Erweiterung.

Die Fitnessfunktion liefert viel Spielraum für Anpassungen. Es wäre denkbar Level nach Raum ähnlichen oder Tunnel ähnlichen Strukturen zu durchsuchen und diese positiv zu bewerten. 

Der generieren der Texturen sollte so angepasst werden, das auch größere Level erzeugt werden können, ohne große Mengen an Arbeitsspeicher zu benötigen. 

Das Verfahren zur Platzierung von Räumen und Fluren kann so optimiert werden, das alternative Routen erstellt werden, in denen Items platziert werden können. Alternative Routen würden die Implementierung von Türen und Schlüsseln wieder als Sinnvolle Erweiterung markieren. So könnte es zu interessanten Risk and Reward Situationen kommen.  Der Algorithmus sollte so angepasst werden, dass die gesamte Levelfläche besser genutzt wird, da durch das aktuelle Verfahren zur Platzierung, ein Großteil des Levels ungenutzt bleibt.

### Graphbased Ansatz

Alternativ wäre auch eine andere Kodierungsform denkbar. 

In Abschnitt ... wurde ein Verfahren präsentiert, das mithilfe von planaren Graphen und einen Set aus vorgegeben Raumformen, Level generieren kann. Ein neuer GA könnte, anstelle der direkten Generierung der Level, einen planaren Graphen erzeugen. 

Die Kodierung als Graph ermöglicht es, eine Vielzahl an Bewertungskriterien in den generierungsprozess zu integrieren. 

Die Lösbarkeit eines Levels zu gewährleisten stellt sich als sehr einfach heraus, es muss ledeglich geprüft werden ob der Start und Endknoten im selben Graphen liegen. 

Die schnellste Route von Start und Ziel wird, ähnlich zur Spelunky Methode, als kritischer Pfad gekennzeichnet. Alle Knoten außerhalb des kritischen Pfades können als optionale Räume betrachtet werden. Das ermöglicht im nachhinein Items und starke Monster abseits des Hautpweges zu platzieren und so eine vielzahl interessanter Risk und Reward Situation zu erzeugen. Ebenso kann die Schwierigkeit des Levels besser kontrolliert werden, auf den kritischen Pfad werden schwächere Monster platziert als auf optionalen Pfaden. 

Türen und Schlüsseln können einfacher platziert werden. Um Türen zu platzieren, werden Kanten im Graphen markiert, eine markierte Kante bedeutet: diese Verbindung ist durch einer Tür verschlossen. Um zu prüfen ob der Schlüssel erreicht werden kann ohne durch die Tür zu müssen, kann einfach der Knoten entfernt werden der von der Tür verschlossen wird und dann wird geprüft ob der Schlüssel trotzdem erreichbar ist. 

Abbildung ... zeigt den Aufbau der Spielwelt von *Dark Souls* als eine Art Graph. Jeder Knoten stellt dabei eine Region im Spiel da, Kanten stellen Verbindungen der einzelnen Regionen untereinander da. Jede Region könnte wiederum auch als ein Graph dargestellt werden. Daraus schleißt sich die Möglichkeit, mithilfe eines GraphBased Levelgenerator nicht nur einzelne Level zu generieren sondern durch das zusammensetzen mehrere Graphen, ganze Spielwelten, mit unterschiedlichen optischen Themen, verschiedenen Monstern und Bossgegner in jeder Region zu erzeugen. 

![Die Spielwelt von Dark Souls als Graphenähnliches konstrukt.[@Brown]](figs/worldOfDarkSouls.PNG){width=100%}

Um einen GraphBased Ansatz als GA umzusetzen, bieten sich unterschiedliche Rekombinations und Mutationsverfahren an. So könnte zum Beispiel die Rekombination so aussehen, dass aus beide Graphen mithilfe des Mulit-Point-Crossover Knoten herausgenommen werden und im anderen Graphen neu platziert werden. Neu eingefügte Knoten müssen wiederum so mit den bestehenden Graphen verbunden werden, das die planare Eigenschaft des Graphen bestehen bleibt.Bei der Mutation könnten neue Kanten zwischen Knoten hinzugefügt oder entfernt werden. Das hinzufügen von Kanten wäre auch möglich. Als Berwertungskriterium könnte das Verhältniss zwischen optinonalen und kritschen Knoten, die anzahl der Kanten oder die Anzahl unterschiedlicher Routen verwendet werden. 

Der so erzeuget Graph würde dann als Inputgraph für den in Abschnitt ... beschriebenen Algorithmus dienen. Die Raumformen können entweder manuell erstellt werden oder aus Räumen bestehen, die von den in dieser Arbeit präsentierten Generator erstellt wurden. 

Die Kombination aus dem Graphenbasierten Ansatz und einen GA würde den in Abschnitt .. beschrieben Nachteil, das dieses Verfahren eine menge an Informationen benötigt, deutlich abmildern. Es bleiben die beschriebenen Vorteile bestehen. 


