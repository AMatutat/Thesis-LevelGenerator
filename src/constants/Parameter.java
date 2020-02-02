package constants;
/**
 * Enthält Konstante Parameter
 * @author Andre Matutat
 *
 */
public class Parameter {

	/**
	 * Wahrscheinlichkeit das ein Feld ein Boden ist
	 */
	public static float CHANCE_TO_BE_FLOOR = 0.6f;
	/**
	 * Wahrscheinlichkeit das es zur Mutation kommt
	 */
	public static float CHANCE_FOR_MUTATION = 0.05f;
	/**
	 * Wahrscheinlichkiet das es zur Rekombination kommt
	 */
	public static float CHANCE_FOR_CROSSOVER = 0.8f;
	
	/**
	 * Populationsgröße
	 */
	public static final int POPULATIONSIZE = 500;	
	/**
	 * Maxiname Generationen 
	 */
	public static final int MAXIMAL_GENERATION = 50;	
	
	/**
	 * Fitnesspunkte für verbundenen Wände
	 */
	public static final int WALL_IS_CONNECTED = 2;
	/**
	 * Fitnesspunkte für unverbundene Wände mit Nachbarwänden
	 */
	public static final float WALL_HAS_NEIGHBOR = 0.1f;
	/**
	 * Fitnesspunkte für Wände mit Bodennachbarn
	 */
	public static final float WALL_NEIGHBOR_IS_FLOOR = -0.5f;
	/**
	 * Fitnesspunkte für erreichbare Böden
	 */
	public static final int FLOOR_IS_REACHABLE = 3;
	/**
	 * Fitnesspunkte für lösbare Level
	 */
	public static final int EXIT_IS_REACHABLE = 25;
	
	/**
	 * Minimale Breite eines Level
	 */
	public static final int MINIMAL_XSIZE = 4;
	/**
	 * Minimale Höhe eines Level
	 */
	public static final int MINIMAL_YSIZE = 4;

}
