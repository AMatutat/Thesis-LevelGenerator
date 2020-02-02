package interfaces;
/**
 * Muss von der Level Klasse implementiert werden
 * @author André Matutat
 *
 */
public interface ILevel {
/**
 * 
 * @return Breite des Levels
 */
	public int getXSize();
	/**
	 * 
	 * @return Höhe des Levels
	 */
	public int getYSize();
	/**
	 * 
	 * @return Aufbau des Levels
	 */
	public ISurface[][] getLevel();

	/**
	 * 
	 * @return Liste mit allen, nicht belegten, Böden
	 */
	public ISurface[] getFreeFloors();
	
	/**
	 * 
	 * @return Liste mit allen, nicht belegten, Wänden
	 */
	public ISurface[] getFreeWalls();
}
