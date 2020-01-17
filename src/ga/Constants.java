package ga;

/**
 * Konstanten die vom Levelgenerator verwendet wird
 * 
 * @author André Matutat
 *
 */
public class Constants {
	/**
	 * Warscheinlichkeit das eine Mutation durchgeführt wird
	 */
	public static float CHANCE_FOR_MUTATION = 0.1f;
	/**
	 * Warscheinlichkeit das ein Crossover durchgeführt wird
	 */
	public static float CHANCE_FOR_CROSSOVER = 0.6f;

	/**
	 * Größe der Startpopulation
	 */
	public static final int POPULATIONSIZE = 500;
	/**
	 * Anzahl ab der ein komplett neuer Durchlauf gestartet wird, da sich der
	 * aktuelle Durchlauf festgefahren hat
	 */
	public static final int MAXIMAL_GENERATION = 50;

	/**
	 * Warscheinlichkeit das ein zufällig erstelltes Surface ein Floor ist n/100
	 */
	public static float CHANCE_TO_BE_FLOOR = 0.5f;

	/**
	 * Faktor der auf die Fitness gerechnet wird, wenn eine Wand oder Wandkette mit
	 * der Ausenwand verbunden ist
	 */
	public static final int WALL_IS_CONNECTED = 2;
	/**
	 * Faktor der auf die Fitness gerechnet wird, für jeden erreichbaren Floor im
	 * level
	 */
	/**
	 * Teilpunkte, für jeden Wall Nachbar einer Wall
	 */
	public static final float WALL_HAS_NEIGHBOR = 0.1f;
	public static final float WALL_NEIGHBOR_IS_FLOOR = -0.5f;

	public static final int FLOOR_IS_REACHABLE = 3;
	/**
	 * Faktor der auf die Fitness gerechnet wird, wenn das Ende erreichbar ist
	 */
	public static final int EXIT_IS_REACHABLE = 25;

	/**
	 * Minmiale breite des Levels
	 */
	public static final int MINIMAL_XSIZE = 4;
	/**
	 * Minimale tiefe des Levels
	 */
	public static final int MINIMAL_YSIZE = 4;
	/**
	 * Symbolisiert ein Floor Surface im Array
	 */
	public static final char REFERENCE_FLOOR = 'F';
	/**
	 * Symbolisiert ein Wall Surface im Array
	 */
	public static final char REFERENCE_WALL = 'W';
	/**
	 * Symbolisiert ein Exit Surface im Array
	 */
	public static final char REFEERNCE_EXIT = 'X';
	/**
	 * Symbolisiert ein Start Surface im Array
	 */
	public static final char REFERENCE_START = 'S';
	
	public static final char REFERENCE_EMPTY = ' ';
	
	public static final char REFERENCE_NORTH = 'N';
	public static final char REFERENCE_SOUTH = 'S';
	public static final char REFERENCE_EAST = 'E';
	public static final char REFERENCE_WEST = 'W';
	public static final char REFERENCE_DOOR = 'D';
	public static final char REFERENCE_FLOOR_WITH_KEY = 'V';
}
