<!--

- Worum geht es hier? Was ist das betrachtete Problem bzw. die Fragestellung der Arbeit?
- Darstellung der Bedeutung und Relevanz: Warum sollte die Fragestellung bearbeitet werden?
- Einordnung in den Kontext
- Abgrenzung: Welche Probleme werden im Rahmen der Arbeit *nicht* gelöst?
- Zielsetzung: Möglichst genaue Beschreibung der Ziele der Arbeit, etwa erwarteter Nutzen oder wissenschaftlicher Beitrag

Umfang: typisch ca. 8% ... 10% der Arbeit => 8 Seiten
-->

# Einleitung

## Motivation

Im Module Programmiermethoden des Studiengangs Informatik, an der Fachhochschule Bielefeld, sollen die Teilnehmer ihre Kenntnisse in der Programmiersprache Java erweitern und in gängige Methoden der Softwareentwicklung eingeführt werden. Die Teilnehmer sollen ein tieferes Verständnis für die Prinzipien der Objektorientierten Programmierung erwerben und grundlegende Architekturmuster kennen lernen. Die Teilnehmer sollen Git verwenden, um Erfahrungen in der Versionsverwaltung zu erlangen. Die Teilnehmer sollen grundlegende Programmiere Konventionen kennen und beachten lernen.  [Prüfungsordnung]

Um diese Lernziele zu erreichen, findet neben der Vorlesung, welche den Lernstoff theoretisch bearbeitet, auch ein Praktikum statt, in dem die Teilnehmer regelmäßig vorgegebene Aufgaben in kleinen Gruppen bearbeiten müssen um dann ihre Lösungen vorzustellen. Die Teilnahme an den Praktikum ist für den Abschluss des Studienganges verpflichtend. [Prüfungsordnung]

Für das Somersemster 2020 wurde ein neues Praktikums Konzept entwickelt. Die sonst oft unabhängig voneinander gestellten Aufgaben wurden in ein größeres Gesamtprojekt eingearbeitet. Die Teilnehmer werden im laufe des Semester  eine eigenes 2D Rouge Like Computerrollenspiel, angelehnt an das bereits existierende Shattered Pixel Dungeon, in der Programmiersprache Java entwickeln. Die Teilnehmer werden jede Woche ihr neu erlangtes Wissen nutzen, um das Spiel Stück für Stück aufzubauen und zu erweitern. Durch diese Umstrukturierung soll ein tieferes Verständnis für zusammenhänge einzelner Konzepte vermittelt werden. Ebenso soll die Motivation eigenständig an den Projekt weiterzuarbeiten der Teilnehmer dadurch gestiegen werden, das Erfolgserlebnisse nicht nur theoretisch Einfluss nehmen, sondern sich auch praktisch im Spiel aufzeigen. [MA Arbeit]

Das als Vorbild dienende Shattered Pixel Dungeon gehört zu den Genre der Rouge-Like Rollenspiele. Rouge-Like Videospiele zeichnen sich besonders dadurch aus, das ihre Inhalte, wie zum Beispiel Monster, Items oder Level, prozedural generiert werden.[ <https://de.wikipedia.org/wiki/Rogue-like> ]

Die Konzeptionierung und Implementierung solcher PCGs ist nicht trivial. Da das Module Programmier Methoden im zweiten Fachsemester angeordnet ist, ist die Entwicklung eines PCGs nicht nur nicht zielführend, sondern würde viele Teilnehmer auch fachlich überfordern, da diese noch ihre Grundkenntnisse festigen müssen.



## Ziel und Struktur der Arbeit

Diese Arbeit präsentiert bekannte Verfahren zur prozeduralen Erzeugung von Videospiel Leveln.  Die Arbeit beschäftigt sich mit Aspekten des Level Designs, jedoch nicht mit Aspekten der Level Arts. Ziel dieser Arbeit ist die Konzeptionierung und Umsetzung eines, für das Spiel der Teilnehmer angepassten, Leven Generator basierend auf Genetischen Algorithmen. Der so erstellte Algorithmus soll den Teilnehmern zur Verfügung gestellt werden, damit diese ihn in ihre Implementation integrieren können. Der hier entwickelte Algorithmus, wird aufgrund der Natur von GAs, nicht in der Lage sein, Level in Echtzeit zu generieren. Die Konzeptionierung und Implementation der Spiellogik ist nicht Bestandteil dieser Arbeit. 

Um auch nicht Spielern einen Überblick zu ermögliche, was genau ein Rouge-Like Videospiel ist und was es ausmacht, wird der erste Abschnitt grundlegende Eigenschaften dieses Genres anhand des bereits erwähnten Shattered Pixel Dungeon erläutert. Im zweiten Teil dieser Arbeit wird ein Einblick in die prozedurale Level Generierung gewährt. Es werden die wichtigsten Begriffe der PCG erläutert, ebenso werden bekannte Verfahren der PLG präsentiert. Mithilfe von veröffentlichten Quellcode sowie mithilfe von Fans durchgeführten Reverse Engineering, wird die Vorgehensweise zur Level Generierung einiger bekannter Videospiele beleuchtet. Der dritte Abschnitt dieser Arbeit liefert einen Überblick der Thematik Leveldesign. Es wird aufgezeigt was genau Leveldesign ist und auch was es nicht ist. Mithilfe der Aussagen von Spieleentwicklern und Spielejournalisten, wird eine Liste wichtige Faktoren für gutes Leveldesign aufgestellt. Der vierte Teil liefert eine Einführung in die Genetischen Algorithmen. Nachdem alle Grundlagen betrachtet wurden, führt der fünfte Abschnitt in die Konzeption des hier entwickelten Level Generators ein. Der darauffolgende Abschnitt zeigt die Umsetzung des Konzeptes, sowie eine Vielzahl an Schwierigkeiten, aufgrund dessen das Konzept regelmäßig überarbeitet werden musste. Ebenso werden die durch den Generator erzeugen Level mithilfe der in Abschnitt drei aufgestellten Liste bewertet. Zum Schluss folgt eine Zusammenfassung der Arbeit, sowie eine Liste möglicher Verbesserungen. Außerdem wird ein weiteres Konzept zur Generierung von Leveln basierend auf GAs präsentiert. 