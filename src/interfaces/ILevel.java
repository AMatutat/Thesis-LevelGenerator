package interfaces;
/**
 * Muss von der Level Klasse implementiert werden
 *
 * @author Andr� Matutat
 */
public interface ILevel {
    /** @return Breite des Levels */
    public int getXSize();
    /** @return H�he des Levels */
    public int getYSize();
    /** @return Aufbau des Levels */
    public ISurface[][] getLevel();

    /** @return Liste mit allen, nicht belegten, B�den */
    public ISurface[] getFreeFloors();

    /** @return Liste mit allen, nicht belegten, W�nden */
    public ISurface[] getFreeWalls();
}
