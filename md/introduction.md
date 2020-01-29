# Einleitung

## Motivation

Im Module Programmiermethoden des Studiengangs Informatik, an der Fachhochschule Bielefeld, sollen die Studenten ihre Kenntnisse in der Programmiersprache Java erweitern und in gängige Methoden der Softwareentwicklung eingeführt werden. Die Studenten sollen ein tieferes Verständnis für die Prinzipien der Objektorientierten Programmierung erwerben und grundlegende Architekturmuster kennen lernen. Die Studenten sollen Git verwenden, um Erfahrungen in der Versionsverwaltung zu erlangen. Die Studenten sollen grundlegende Programmierkonventionen kennen und beachten lernen. [@Prüfungsordnung]

Um diese Lernziele zu erreichen, findet neben der Vorlesung, welche den Lernstoff theoretisch bearbeitet, auch ein Praktikum statt, in dem die Studenten regelmäßig vorgegebene Aufgaben in kleinen Gruppen bearbeiten müssen um dann ihre Lösungen vorzustellen. Die Teilnahme an dem Praktikum ist für den Abschluss des Studienganges verpflichtend. [@Prüfungsordnung]

Für das Sommersemester 2020 wurde ein neues Praktikums Konzept entwickelt. Die sonst oft unabhängig voneinander gestellten Aufgaben wurden in ein größeres Gesamtprojekt eingearbeitet. Die Studenten werden im Laufe des Semesters ein eigenes 2D-Roguelike-Computer-Rollenspiel, angelehnt an das bereits existierende Shattered Pixel Dungeon, in der Programmiersprache Java entwickeln. Die Studenten werden jede Woche ihr neu erlangtes Wissen nutzen, um das PM-Dungeon Stück für Stück aufzubauen und zu erweitern. Durch diese Umstrukturierung soll ein tieferes Verständnis für zusammenhänge einzelner Konzepte vermittelt werden. Ebenso soll die Motivation, eigenständig an dem Projekt weiterzuarbeiten, der Studenten dadurch gesteigert werden, da Erfolgserlebnisse nicht nur theoretisch Einfluss nehmen, sondern sich auch praktisch im Spiel wiederfinden.

Das als Vorbild dienende Shattered Pixel Dungeon gehört zu dem Genre der Roguelike Rollenspiele. Roguelike Videospiele zeichnen sich besonders dadurch aus, dass ihre Inhalte, wie zum Beispiel Monster, Items oder Level, prozedural generiert werden. [vgl. Abs 2.1.3]

Die Konzeptionierung und Implementierung solcher PCGs ist nicht trivial. Da das Module PM im zweiten Fachsemester angeordnet ist, ist die Entwicklung eines PCGs nicht nur nicht zielführend, sondern würde viele Studenten auch fachlich überfordern, da diese noch ihre Grundkenntnisse festigen müssen.

## Ziel und Struktur der Arbeit

Diese Arbeit präsentiert bekannte Verfahren zur PLG.  Die Arbeit beschäftigt sich mit Aspekten des Level Designs, jedoch nicht mit Aspekten der Level Arts. Ziel dieser Arbeit ist die Konzeptionierung und Umsetzung eines, für das PM-Dungeon angepassten, Level Generator basierend auf Genetischen Algorithmen.  

Zusätzlich beschäftigt sich die Arbeit mit der Frage, wie GAs Aspekte guten Leveldesign umsetzten ohne das diese direkten Einfluss auf den Algorithmus nehmen.  

Der so erstellte Algorithmus soll den Studenten zur Verfügung gestellt werden, damit diese ihn in ihre Implementation integrieren können. Der hier entwickelte Algorithmus, wird aufgrund der Natur von GAs, nicht in der Lage sein, Level in Echtzeit zu generieren. Die Konzeptionierung und Implementation der Spiellogik ist nicht Bestandteil dieser Arbeit.  

Die Arbeit ist in fünf Kapitel unterteilt. 

In Kapitel 2 werden alle nötigen Informationen vermittelt, die gebraucht werden um das Konzept des entwickelten Algorithmus nachvollziehen und bewerten zu können. Es beginnt mit einer Erklärung des Roguelike Genres und des Spieles Shattered Pixel Dungeon um besser nachvollziehen zu können welche Art von Spiel von den Studenten entwickelt wird. Es folgt eine Ausarbeitung an Regeln für gutes Leveldesign. Abschnitt 2.3 erklärt den Begriff der Prozeduralen Content Generierung und gibt anhand Bekannter Verfahren und Spiele einen Einblick in Prozedurale Level Generierung. Das Kapitel endet mit einer Einführung in die Thematik der Evolutionären Algorithmen. Es werden die wichtigsten Fachbegriffe erläutert, der Ablauf von Genetischen Algorithmen sowie verbreitete Verfahren zur Implementierung der Subroutinen präsentiert. 

In Kapitel 3 wird das Konzept des, in dieser Arbeit entwickelten, Level Generators basierend auf Genetischen Algorithmen erstellt. Dazu gehört die Ausarbeitung der Anforderungen und Bewertungskriterien sowie die Erstellung eines Lösungskonzeptes. In Kapitel 4 wird das Konzept umgesetzt, es werden vereinzelnde Funktionen genauer betrachtet sowie Probleme aufgezeigt. Es folgt eine regelmäßige Auswertung der Zwischenergebnisse bevor das Kapitel dann am Ende mit einer detaillierten Analyse der Ergebnisse unter Berücksichtigung der in Kapitel 2 aufgestellten Regeln für gutes Leveldesign abschließt. Kapitel 5 fasst noch einmal die wichtigsten Erkenntnisse der Arbeit zusammen, und präsentiert ein Konzept für einen Levelgenerator mit einem Graphen basierten Ansatz und Genetischen Algorithmen.

