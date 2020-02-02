# Zusammenfassung

## Fazit

Ziel dieser Arbeit war es, einen Level Generator basierend auf einen Genetischen Algorithmus zu entwickeln und zu bewerten. Dafür wurden unterschiedliche Bewertungskriterien für gutes Level Design aufgestellt. Insbesondere die Faktoren Lösbarkeit, Risk and Reward, Immersion und Einzigartigkeit der generierten Level wurde untersucht.

Der vorgestellte und implementierte Algorithmus ist in der Lage, auch ohne Kenntnisse der Bewertungskriterien, spaßige und abwechslungsreiche Level zu generieren. Die Bewertungskriterien der Fitnessfunktion des GA geben nur an, in welche Art und Weise Böden und Wände im Level platziert werden sollen. Dem GA wurde kein Wissen über Raum- oder Flurstrukturen vermittelt, dennoch erschafft er Level mit unterschiedlichen Räumen und alternativen Routen für interessante Risk and Reward Situationen. Die vom GA erzeugten Level unterscheiden sich deutlich im Level Layout (vgl. B.1.), und können als einzigartig eingestuft werden. Der Generator wurde so programmiert, dass er nur lösbare Level als gültige Ergebnisse zurückliefert. Dadurch, dass der GA kein Verständnis über Raum- oder Flurstrukturen besitzt, ist die sinnvolle Platzierung von Türen und Schlüsseln nicht möglich. Als gute Einstellungen haben sich die Grundeinstellungen aus Tabelle 4.1 mit dem in Abschnitt 3.4.2 und 3.4.3 vorgestellten Mutations- und Rekombinationsverfahren mit einer Mutationswahrscheinlichkeit von 5% und Rekombinationschance von 80% herauskristallisiert. Auch wurden das Boden/Wand Verhältnis auf 40/60 angepasst.  

Zusätzlich wurden das Verfahren zur Generierung von Level so abgeändert, das die generierten Level als Räume für weitere Algorithmen benutzt werden konnten. Es wurden zwei Verfahren präsentiert, welche aus den so generierten Räumen Level zusammensetzten.

Das an das Spiel *Spelunky* (vgl. Abs. 2.3.3.2) angelehnte Spelunky-Style Verfahren, ist zwar in der Lage Level mit alternativen Routen zu erschaffen, diese ähneln sich vom Aufbau aber dermaßen stark, das kaum Unterschiede zwischen den einzelnen Level zu sehen sind.(vgl. Abs B.2.) Es ist aber in der Lage Türen und Schlüssel korrekt im Level zu platzieren, um alternativen Routen anfangs unpassierbar zu machen. Das Verfahren eignet sich nicht, um Level für das PM-Dungeon zu generieren.  

Das Verfahren *Reise zum Mittelpunkt* platziert zufällig generierte Räume und verteilt diese in einem großen, leeren, Level. Jeder Raummittelpunkt wird dann mit dem, ihm am nächsten liegenden, unverbundenen, Raummittelpunkt mithilfe eines Flures verbunden. So entstehen die klassischen Raum- Flur Struktur eines Dungeons.(vgl. Abs. B.3.) Dieses Verfahren kann zufällig, alternative Routen erzeugen, dies kann aber nicht garantiert werden. Da die Räume unterschiedlich groß sind, entsteht nicht nur unter den Level Abwechslung, sondern auch im Level selber. Dieses Verfahren erzeugt teil sehr lange Gänge welche keine Abwechslung und kein Platz für spaßiges Gameplay liefern und daher als störend empfunden werden können. Das Verfahren ist noch nicht ausgereift, kann aber durchaus für die Generierung vom PM-Dungeon Level genutzt werden.

Der Implementierte Parser, ermöglicht eine schnelle und einfache Integration der generierten Level in das, von den Studenten erschaffene, PM-Dungeon. Die Studierenden können den Parser bei Bedarf umschreiben, um ihn aber spezielle Eigenheiten ihrer Implementation anzupassen. Der Parser hilft dabei, Monster und Items zufällig im Level zu platzieren, Spezial Felder wie Fallen zu platzieren und die Textur des Levels zu generieren. Die Funktion zur Generierung der Leveltextur benötigt, je nach Level Größe, eine Menge RAM der JVM, ggf. muss dieser daher manuell erhöht werden.

Der Parser bietet keine Möglichkeit Monster und Items gezielt in alternativen Bereichen des Levels zu platzieren, der Schwierigkeitsgrad des Levels kann daher nur durch die Anzahl und stärke der platzierten Monster kontrolliert werden.

Das Ziel, einen Generator zu erschaffen der Level für das PM-Dungeon generiert und welche von den Studenten verwendet werden können, kann daher als erfolgreich erreicht angesehen werden. Der Generator kann dabei helfen den Lernerfolg und die Lernmotivation der Studenten zu erhöhen. Zusätzlich hat diese Arbeit gezeigt, das ein GA, bereits mit einer einfachen und grundlegenden Fitnessfunktion dazu in der Lage ist, spaßige und abwechslungsreiche Level, die viele Prinzipien guten Level Designs berücksichtigen, zu erzeugen.  

## Ausblick

### Verbesserung des aktuellen Ansatzes

Der erstellte Algorithmus bietet viele Möglichkeiten der Optimierung und Erweiterung. Im Folgenden werden einige Ansatzpunkte kurz beschrieben.

Es müssen weitere Messungen vorgenommen werden um auch für andere Einstellungen, als den in Tabelle 4.1 vorgestellten Grundeinstellungen, und unterschiedlichen Levelgrößen die optimalen Parametereinstellungen zu finden.

Die Fitnessfunktion liefert viel Spielraum für Anpassungen. Es wäre denkbar Level nach Raum ähnlichen oder Tunnel ähnlichen Strukturen zu durchsuchen und diese positiv zu bewerten. Das würde es auch ermöglichen, Türen und Schlüssel im Level einzubauen.

Der generieren der Texturen sollte so angepasst werden, dass auch größere Level erzeugt werden können, ohne große Mengen an Arbeitsspeicher zu benötigen.

Das Verfahren zur Platzierung von Räumen und Fluren kann so optimiert werden, das garantiert alternative Routen erstellt werden, in denen Items platziert werden können. Alternative Routen würden die Implementierung von Türen und Schlüsseln wieder als sinnvolle Erweiterung hervorheben. So könnte es zu interessanten Risk and Reward Situationen kommen. Der Algorithmus sollte so angepasst werden, dass die gesamte Levelfläche besser genutzt wird, da durch das aktuelle Verfahren zur Platzierung, ein Großteil des Levels ungenutzt bleibt.  

### GraphBased Ansatz

Alternativ wäre auch eine andere Kodierungsform denkbar.

In Abschnitt 2.3.3.3 wurde ein Verfahren präsentiert, das mithilfe von planaren Graphen und ein Set aus vorgegeben Raumformen, Level generieren kann. Ein neuer GA könnte, anstelle der direkten Generierung der Level, einen planaren Graphen erzeugen.

Die Kodierung als Graph ermöglichtes, eine Vielzahl an Bewertungskriterien in den Generierungsprozess zu integrieren.

Die Lösbarkeit eines Levels zu gewährleisten stellt sich als sehr einfach heraus, es muss lediglich geprüft werden, ob der Start und Endknoten im selben Graphen liegen.

Die schnellste Route von Start und Ziel wird, ähnlich zur Spelunky Methode, als kritischer Pfad gekennzeichnet. Alle Knoten außerhalb des kritischen Pfades können als optionale Räume betrachtet werden. Das ermöglicht im Nachhinein Items und starke Monster abseits des Hauptweges zu platzieren und so eine Vielzahl interessanter Risk und Reward Situation zu erzeugen. Ebenso kann die Schwierigkeit des Levels besser kontrolliert werden, auf den kritischen Pfad werden schwächere Monster platziert als auf optionalen Pfaden.

Türen und Schlüsseln können einfacher platziert werden. Um Türen zu platzieren, werden Kanten im Graphen markiert, eine markierte Kante bedeutet: Diese Verbindung ist durch eine Tür verschlossen. Um zu prüfen, ob der Schlüssel erreicht werden kann ohne durch die Tür zu müssen, kann einfach der Knoten entfernt werden, der von der Tür verschlossen wird und dann wird geprüft ob der Schlüssel trotzdem erreichbar ist.

Abbildung 5.1 zeigt den Aufbau der Spielwelt von *Dark Souls* als einem Graph. Jeder Knoten stellt dabei eine Region im Spiel dar, Kanten stellen Verbindungen der einzelnen Regionen untereinander dar. Jede Region könnte wiederum auch als ein Graph dargestellt werden. Mit einem GraphBased Level Generator könnten daher nicht nur einzelne Level, sondern, durch das Zusammensetzen mehrere Graphen, ganze Spielwelten, mit unterschiedlichen optischen Themen, verschiedenen Monstern und Boss Gegner in jeder Region, erzeugt werden.  

![Die Spielwelt von Dark Souls als Graph.[@Brown2018]](figs/worldOfDarkSouls.PNG){width=100%}

Um einen GraphBased Ansatz als GA umzusetzen, bieten sich unterschiedliche Rekombination- und Mutationsverfahren an. So könnte zum Beispiel die Rekombination so aussehen, dass aus beide Graphen, mithilfe des Multi-Point-Crossover Verfahren, Knoten herausgenommen werden und im anderen Graphen neu platziert werden. Neu eingefügte Knoten müssen wiederum so mit den bestehenden Graphen verbunden werden, das die planare Eigenschaft des Graphen bestehen bleibt. Bei der Mutation könnten neue Kanten zwischen Knoten hinzugefügt oder entfernt werden. Das Hinzufügen von Kanten wäre auch möglich. Als Bewertungskriterien könnte das Verhältnis zwischen optionalen und kritischen Knoten, die Anzahl der Kanten oder die Anzahl unterschiedlicher Routen verwendet werden.

Der so erzeugt Graph würde dann als Inputgraph für den in Abschnitt 2.3.3.3 beschriebenen Algorithmus dienen. Die Raumformen können entweder manuell erstellt werden oder aus Räumen bestehen, die von den in dieser Arbeit präsentierten Generator erstellt wurden.

Die Kombination aus dem Graphen basierten Ansatz und einen GA würde den in Abschnitt 2.3.3.3 beschrieben Nachteil, dass dieses Verfahren eine Menge an Informationen benötigt, deutlich abmildern. Es bleiben die beschriebenen Vorteile bestehen.  


