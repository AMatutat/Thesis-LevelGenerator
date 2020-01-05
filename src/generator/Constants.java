package generator;

/**
 * Konstanten die vom Levelgenerator verwendet wird
 * 
 * @author André Matutat
 *
 */
public class Constants {

	
	/**
	 * Schwelltwert ab den ein Level für gut befunden wird
	 */
	static float THRESHOLD_FITNESS= 0.75f;
	/**
	 * Faktor der auf die Fitness gerechnet wird, wenn eine Wand oder Wandkette mit
	 * der Ausenwand verbunden ist
	 */
	static int WALL_IS_CONNECTED = 2;
	/**
	 * Faktor der auf die Fitness gerechnet wird, für jeden erreichbaren Floor im
	 * level
	 */
	
	static float WALL_HAS_NEIGHBOR=0.1f;
	
	static int FLOOR_IS_REACHABLE = 3;
	/**
	 * Faktor der auf die Fitness gerechnet wird, wenn das Ende erreichbar ist
	 */
	static int EXIT_IS_REACHABLE = 25;
	/**
	 * Größe der Startpopulation
	 */
	static int POPULATIONSIZE =500;
	/**
	 * Warscheinlichkeit das ein zufällig erstelltes Surface ein Floor ist n/100
	 */
	static float CHANCE_TO_BE_FLOOR = 0.7f;

	/**
	 * Warscheinlichkeit das ein Crossover durchgeführt wird n/100
	 */
	static float CHANCE_FOR_CROSSOVER = 0.6f;
	/**
	 * Anzahl ab der ein komplett neuer Durchlauf gestartet wird, da sich der
	 * aktuelle Durchlauf festgefahren hat
	 */
	static int MAXIMAL_GENERATION = 100;
	/**
	 * Minmiale breite des Levels
	 */
	static int MINIMAL_XSIZE = 4;
	/**
	 * Minimale tiefe des Levels
	 */
	static int MINIMAL_YSIZE = 4;
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
