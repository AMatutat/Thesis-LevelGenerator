package parser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import constants.Reference;
import ga.CodedLevel;
import interfaces.*;
import myGame.*;
/**
 * Parst CodedLevel in Level des PM-Dungeon. 
 * Hilft bei der Platzierung von Monstern und Items im Level.
 * Erschafft Leveltextur. 
 * 
 * Muss entsprechend der eigenen Implementation angepasst werden. 
 * 
 * @author Andre Matutat
 *
 */
public class LevelParser {

	/**
	 * Parst CodedLevel in Level des PM-Dungeon. 
	 * @param level vom Generator erschaffenes Level
	 * @return Level des PM-Dungeon (Klasse Level muss selbst implementiert werden)
	 */
	public Level parseLevel(final CodedLevel level) {
		ISurface[][] lvl = new ISurface[level.getXSize()][level.getYSize()];
		for (int x = 0; x < level.getXSize(); x++) {
			for (int y = 0; y < level.getYSize(); y++) {		
			
				//Surface Implementationen müssen selbst erstellt werden. ggf. (noch) nicht Implementierte Surfaces auskommentieren
				if (level.getLevel()[x][y] == Reference.REFERENCE_WALL)
					lvl[x][y] = new Wall();
				else if (level.getLevel()[x][y] == Reference.REFERENCE_FLOOR)
					lvl[x][y] = new Floor();
				else if (level.getLevel()[x][y] == Reference.REFERENCE_START)
					lvl[x][y] = new Start();
				else if (level.getLevel()[x][y] == Reference.REFEERNCE_EXIT)
					lvl[x][y] = new Exit();
				else if (level.getLevel()[x][y] == Reference.REFERENCE_DOOR)
					lvl[x][y] = new Door();
				else if (level.getLevel()[x][y] == Reference.REFERENCE_FLOOR_WITH_KEY) {
					lvl[x][y] = new Floor();
					lvl[x][y].setItemOnSurface(new Key());
				}

			}
		}

		return new Level(level.getXSize(), level.getYSize(), lvl);
	}

	/**
	 * Platziert Monster auf einer zufälligen Oberfläche
	 * @param lvl Level
	 * @param monster zu platzierendes Monster
	 * @param surfaceToPutOn Oberflächen Typ auf dem das Monster platziert werden soll
	 * @return Ob Platzierung erfolgreich war
	 */
	public boolean placeMonster(final Level lvl, final Monster monster, final String surfaceToPutOn) {
		ISurface[] surfaces;
		// bei mehr Surfaces entsprechend erweitern
		if (surfaceToPutOn.equals("Floor"))
			surfaces = lvl.getFreeFloors();
		else
			surfaces = lvl.getFreeWalls();


		//Wenn keine freien Felder mehr da sind
		if (surfaces.length == 0)
			return false;

		int field = (int) Math.random() * surfaces.length;
		return surfaces[field].setMonsterOnSurface(monster);

	}
	/**
	 * Platziert Item auf einer zufälligen Oberfläche
	 * @param lvl Level
	 * @param item zu platzierendes Item
	 * @param surfaceToPutOn Oberflächen Typ auf dem das Item platziert werden soll
	 * @return Ob Platzierung erfolgreich war
	 */
	public boolean placeItem(final Level lvl, final Item item, final String surfaceToPutOn) {
		ISurface[] surfaces;
		// bei mehr Surfaces entsprechend erweitern
		if (surfaceToPutOn.equals("Floor"))
			surfaces = lvl.getFreeFloors();
		else
			surfaces = lvl.getFreeWalls();

		//Wenn keine freien Felder mehr da sind
		if (surfaces.length == 0)
			return false;

		int field = (int) Math.random() * surfaces.length;
		return surfaces[field].setItemOnSurface(item);
	}

	/**
	 * Tauscht eine zufällige Oberfläche durch eine andere aus
	 * @param lvl Level
	 * @param newSurface neue Oberfläche die platziert werden soll
	 * @param oldSurface Oberflächen Typ der ausgetauscht werden soll
	 * @return Ob Platzierung erfolgreich war
	 */
	public boolean changeSurface(final Level lvl, final ISurface newSurface, final String oldSurface) {

		ISurface[] surfaces;
		/**
		 * bei mehr Surfaces entsprechend erweitern Nur leere Surfaces werden ersetzt,
		 * um Monster oder Item verlust zu verhindern
		 */

		if (oldSurface.equals("Floor"))
			surfaces = lvl.getFreeFloors();
		else
			surfaces = lvl.getFreeWalls();
		
		//Wenn keine freien Felder mehr da sind
		if (surfaces.length == 0)
			return false;

		int fieldIndex = (int) Math.random() * surfaces.length;
	
		ISurface field=surfaces[fieldIndex];
		//Austauschen des alten Feldes mit dem neuen Feld 
		lvl.getLevel()[field.getX()][field.getY()] = newSurface;
		return true;

	}

	/**
	 * Generiert Textur des Levels
	 * @param lvl Level
	 * @param path Speicherort
	 * @param name Name der Datei
	 * @return Ob generierung erfolgreich war.
	 * @throws OutOfMemoryError bei zu großen Level. RAM der JVM manuell erweitern 
	 */
	public boolean generateTextureMap(final Level lvl, final String path, final String name) throws OutOfMemoryError {
		try {
			BufferedImage img1;
			BufferedImage joinedImgLine = null;
			BufferedImage joinedImgComplete = null;
			img1 = ImageIO.read(new File(lvl.getLevel()[0][0].getTexture()));
			for (int y = 0; y < lvl.getYSize(); y++) {
				for (int x = 1; x < lvl.getXSize(); x++) {
					BufferedImage img2 = ImageIO.read(new File(lvl.getLevel()[x][y].getTexture()));
					joinedImgLine = JoinImage.joinBufferedImageSide(img1, img2);
					img1 = joinedImgLine;
				}
				if (y == 0)
					joinedImgComplete = joinedImgLine;
				else
					joinedImgComplete = JoinImage.joinBufferedImageDown(joinedImgComplete, joinedImgLine);
				img1 = ImageIO.read(new File(lvl.getLevel()[0][y].getTexture()));
			}
			boolean success = ImageIO.write(joinedImgComplete, "png", new File(path + "\\" + name + ".png"));
			if (!success)
				return false;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
