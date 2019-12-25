package generator;

/**
 * Konstanten die vom Levelgenerator verwendet wird
 * 
 * @author André Matutat
 *
 */
public class Constants {

	/**
	 * Größe der Startpopulation
	 */
	static int POPULATIONSIZE = 100;
	/**
	 * Warscheinlichkeit das ein zufällig erstelltes Surface ein Floor ist n/100
	 */
	static int CHANCE_TO_BE_FLOOR = 70;
	/**
	 * Warscheinlichkeit das eine Mutation durchgeführt wird n/100
	 */
	static int CHANCE_FOR_MUTATION = 1;
	/**
	 * Warscheinlichkeit das ein Crossover durchgeführt wird n/100
	 */
	static int CHANCE_FOR_CROSSOVER = 60;
	/**
	 * Anzahl ab der ein komplett neuer Durchlauf gestartet wird, da sich der
	 * aktuelle Durchlauf festgefahren hat
	 */
	static int MAXIMAL_GENERATION = 200;
	/**
	 * Minmiale breite des Levels
	 */
	static int MINIMAL_XSIZE = 4;
	/**
	 * Minimale tiefe des Levels
	 */
	static int MINIMAL_YSIZE = 4;
	/**
	 * Schwelltwert ab den ein Level für gut befunden wird
	 */
	static int THRESHOLD_FITNESS = 20;
	/**
	 * Faktor der auf die Fitness gerechnet wird, für jeden erreichbaren Floor im
	 * level
	 */
	static int FLOOR_IS_REACHABLE = 5;
	/**
	 * Faktor der auf die Fitness gerechnet wird, wenn das Ende erreichbar ist
	 */
	static int EXIT_IS_REACHABLE = 40;
	/**
	 * Faktor der auf die Fitness gerechnet wird, wenn eine Wand oder Wandkette mit
	 * der Ausenwand verbunden ist
	 */
	static int WALL_IS_CONNECTED = 5;
	/**
	 * Symbolisiert ein Floor Surface im Array
	 */
	static char REFERENCE_FLOOR = 'F';
	/**
	 * Symbolisiert ein Wall Surface im Array
	 */
	static char REFERENCE_WALL = 'W';
	/**
	 * Symbolisiert ein Exit Surface im Array
	 */
	static char REFEERNCE_EXIT = 'X';
	/**
	 * Symbolisiert ein Start Surface im Array
	 */
	static char REFERENCE_START = 'S';
}
