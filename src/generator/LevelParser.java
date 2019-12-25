package generator;

import interfaces.*;
import myGame.*;

/* 
 * @author André Matutat
 *
 */
public class LevelParser {

	/**
	 * Parst ein kodiertes Level zu einer Instanz der Klasse Level
	 * @param level kodiertes Level
	 * @return erzeugtes Level
	 */
	public Level parseLevel(CodedLevel level) {
		ISurface[][] lvl = new ISurface[level.getXSize()][level.getYSize()];

		for (int x = 0; x < level.getXSize(); x++) {
			for (int y = 0; y < level.getYSize(); y++) {
				if (level.getLevel()[x][y] == Constants.REFERENCE_WALL)
					lvl[x][y] = new Wall();
				else if (level.getLevel()[x][y] == Constants.REFERENCE_FLOOR)
					lvl[x][y] = new Floor();
				else if (level.getLevel()[x][y] == Constants.REFERENCE_START)
					lvl[x][y] = new Start();
				else if (level.getLevel()[x][y] == Constants.REFEERNCE_EXIT)
					lvl[x][y] = new Exit();
			}
		}

		return new Level(level.getXSize(), level.getYSize(), lvl);
	}

	/**
	 * Setzt ein Monster auf ein Zufälliges Surface
	 * 
	 * @param lvl            in welchem Level soll das Monster platziert werden
	 * @param monster        Monster welches platziert werden soll
	 * @param surfaceToPutOn auf welchen Surface das Monster platziert werden soll
	 * @throws Exception
	 */
	public void placeMonster(Level lvl, Monster monster, ISurface surfaceToPutOn) throws Exception {
		for (int i = 0; i < lvl.getXSize() * lvl.getYSize(); i++) {
			int x = (int) Math.random() * lvl.getXSize();
			int y = (int) Math.random() * lvl.getYSize();
			if (lvl.getLevel()[x][y].getClass() == surfaceToPutOn.getClass()
					&& lvl.getLevel()[x][y].setMonsterOnSurface(monster))
				return;
		}
		throw new Exception("Level voll");
	}

	/**
	 * Setzt ein Item auf ein Zufälliges Surface
	 * 
	 * @param lvl            in welchem Level soll das Item platziert werden
	 * @param item           Item welches platziert werden soll
	 * @param surfaceToPutOn auf welchen Surface das Item platziert werden soll
	 * @throws Exception
	 */
	public void placeItem(Level lvl, Item item, ISurface surfaceToPutOn) throws Exception {

		for (int i = 0; i < lvl.getXSize() * lvl.getYSize(); i++) {
			int x = (int) Math.random() * lvl.getXSize();
			int y = (int) Math.random() * lvl.getYSize();
			if (x != 0 && y != 0 && x != lvl.getXSize() - 1 && y != lvl.getYSize() - 1
					&& lvl.getLevel()[x][y].getClass() == surfaceToPutOn.getClass()
					&& lvl.getLevel()[x][y].setItemOnSurface(item))
				return;
		}
		throw new Exception("Level voll");
	}

	/**
	 * Wechselt ein zufälliges Surface aus
	 * 
	 * @param lvl        in welchem ein Surface ausgetauscht werden soll
	 * @param newSurface neues Surface
	 * @param oldSurface Typ des alten Surface
	 * @throws Exception
	 */
	public void changeSurface(Level lvl, ISurface newSurface, ISurface oldSurface) throws Exception {
		for (int i = 0; i < lvl.getXSize() * lvl.getYSize(); i++) {
			int x = (int) Math.random() * lvl.getXSize();
			int y = (int) Math.random() * lvl.getYSize();
			if (x != 0 && y != 0 && x != lvl.getXSize() - 1 && y != lvl.getYSize() - 1
					&& lvl.getLevel()[x][y].getClass() == oldSurface.getClass()) {
				lvl.getLevel()[x][y] = newSurface;
				return;
			}
		}
		throw new Exception("Level voll");
	}

	/**
	 * Erstellt die TexturenMap für ein Level
	 * 
	 * @param lvl  Level für welchem die TexturenMap generiert werden soll
	 * @param path Pfad zum Speicherort an der die TexturenMap gespeichert werden
	 *             soll
	 * @param name Name der TexturenMap
	 * @return ob das generieren erfolgreich war
	 */
	public boolean generateTextureMap(Level lvl, String path, String name) {
		return false;
	}
}
