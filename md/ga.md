# Einführung in Genetische Algorithmen

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

fixtext