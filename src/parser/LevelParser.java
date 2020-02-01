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

public class LevelParser {

	public Level parseLevel(final CodedLevel level) {
		ISurface[][] lvl = new ISurface[level.getXSize()][level.getYSize()];
		for (int x = 0; x < level.getXSize(); x++) {
			for (int y = 0; y < level.getYSize(); y++) {
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

	public boolean placeMonster(final Level lvl, final Monster monster, final String surfaceToPutOn) {
		ISurface[] surfaces;
		// bei mehr Surfaces entsprechend erweitern
		if (surfaceToPutOn.equals("Floor"))
			surfaces = lvl.getFreeFloors();
		else
			surfaces = lvl.getFreeWalls();

		if (surfaces.length == 0)
			return false;

		int field = (int) Math.random() * surfaces.length;
		return surfaces[field].setMonsterOnSurface(monster);

	}

	public boolean placeItem(final Level lvl, final Item item, final String surfaceToPutOn) {
		ISurface[] surfaces;
		// bei mehr Surfaces entsprechend erweitern
		if (surfaceToPutOn.equals("Floor"))
			surfaces = lvl.getFreeFloors();
		else
			surfaces = lvl.getFreeWalls();

		if (surfaces.length == 0)
			return false;

		int field = (int) Math.random() * surfaces.length;
		return surfaces[field].setItemOnSurface(item);
	}

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
		if (surfaces.length == 0)
			return false;

		int fieldIndex = (int) Math.random() * surfaces.length;
	
		ISurface field=surfaces[fieldIndex];
		//Austauschen des alten Feldes mit dem neuen Feld 
		lvl.getLevel()[field.getX()][field.getY()] = newSurface;
		return true;

	}

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
