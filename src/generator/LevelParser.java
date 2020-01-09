package generator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import interfaces.*;
import myGame.*;

/* 
 * @author Andr� Matutat
 *
 */
public class LevelParser {

	/**
	 * Parst ein kodiertes Level zu einer Instanz der Klasse Level
	 * 
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
				else 
					lvl[x][y]= new Test();
			}
		}

		return new Level(level.getXSize(), level.getYSize(), lvl);
	}

	/**
	 * Setzt ein Monster auf ein Zuf�lliges Surface
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
	 * Setzt ein Item auf ein Zuf�lliges Surface
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
	 * Wechselt ein zuf�lliges Surface aus
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
	 * Erstellt die TexturenMap f�r ein Level
	 * 
	 * @param lvl  Level f�r welchem die TexturenMap generiert werden soll
	 * @param path Pfad zum Speicherort an der die TexturenMap gespeichert werden
	 *             soll
	 * @param name Name der TexturenMap
	 * @return ob das generieren erfolgreich war
	 */
	public boolean generateTextureMap(Level lvl, String path, String name) {
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
			System.out.println("saved success? " + success);
			if (!success)
				return false;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
