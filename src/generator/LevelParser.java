package generator;
import interfaces.*;
import myGame.*;

/**
 * Parst ein char[][] kodiertes Level zu einer Instanz der Klasse Level
 * Ermöglicht die zufällige Verteilung von Monster Items und Surfaces in einem Level.
 * Erstellt die TexturenMap eines Levels.
 * @author André Matutat
 *
 */
public class LevelParser {
	
/**
 * 
 * @param level
 * @param wall Surface welches für Wall  eingesetz werden soll
 * @param floor Surface welches für Floor eingesetz werden soll
 * @return geparstet level
 */
	public static Level parseLevel(CodedLevel level, ISurface wall, ISurface floor) {
		Level l = new Level();
		
		return l;
	}
	
	/**
	 * Setzt ein Monster auf ein Zufälliges Surface
	 * @param lvl in welchem Level soll das Monster platziert werden
	 * @param monster Monster welches platziert werden soll
	 * @param surfaceToPutOn auf welchen Surface das Monster platziert werden soll
	 */
	public static void placeMonster(Level lvl, IMonster monster, ISurface surfaceToPutOn) {
		
	}
	/**
	 * Setzt ein Item auf ein Zufälliges Surface
	 * @param lvl in welchem Level soll das Item platziert werden
	 * @param item Item welches platziert werden soll
	 * @param surfaceToPutOn auf welchen Surface das Item platziert werden soll
	 */
	public static void placeItem (Level lvl, IItem item, ISurface surfaceToPutOn) {
		
	}
	/**
	 * Wechselt ein zufälliges Surface aus
	 * @param lvl in welchem ein Surface ausgetauscht werden soll
	 * @param newSurface neues Surface
	 * @param oldSurface Typ des alten Surface
	 */
	public static void changeSurface (Level lvl, ISurface newSurface, ISurface oldSurface) {
		
	}
	/**
	 * Erstellt die TexturenMap für ein Level
	 * @param lvl Level für welchem die TexturenMap generiert werden soll
	 * @param path Pfad zum Speicherort an der die TexturenMap gespeichert werden soll
	 * @param name Name der TexturenMap
	 * @return ob das generieren erfolgreich war
	 */
	public static boolean generateTextureMap(Level lvl, String path, String name) {
		return false;
	}
}
