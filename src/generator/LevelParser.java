package generator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import interfaces.*;
import myGame.*;

public class LevelParser {

	public Level parseLevel(final CodedLevel level) {
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

	public void placeMonster(final Level lvl, final Monster monster, final ISurface surfaceToPutOn) throws Exception {

		ArrayList<ISurface> checkedSurfaces = new ArrayList<ISurface>();
		while (checkedSurfaces.size() < lvl.getXSize() * lvl.getYSize()) {
			int x = (int) Math.random() * lvl.getXSize();
			int y = (int) Math.random() * lvl.getYSize();
			if (!checkedSurfaces.contains(lvl.getLevel()[x][y])
					&& lvl.getLevel()[x][y].getClass() == surfaceToPutOn.getClass()
					&& lvl.getLevel()[x][y].setMonsterOnSurface(monster))
				return;
			else
				checkedSurfaces.add(lvl.getLevel()[x][y]);

		}
		throw new Exception("Level voll");
	}

	public void placeItem(final Level lvl, final Item item, final ISurface surfaceToPutOn) throws Exception {

		ArrayList<ISurface> checkedSurfaces = new ArrayList<ISurface>();
		while (checkedSurfaces.size() < lvl.getXSize() * lvl.getYSize()) {
			int x = (int) Math.random() * lvl.getXSize();
			int y = (int) Math.random() * lvl.getYSize();
			if (!checkedSurfaces.contains(lvl.getLevel()[x][y]) && x != 0 && y != 0 && x != lvl.getXSize() - 1
					&& y != lvl.getYSize() - 1 && lvl.getLevel()[x][y].getClass() == surfaceToPutOn.getClass()
					&& lvl.getLevel()[x][y].setItemOnSurface(item))
				return;
			else
				checkedSurfaces.add(lvl.getLevel()[x][y]);
		}
		throw new Exception("Level voll");
	}

	public void changeSurface(final Level lvl, final ISurface newSurface, final ISurface oldSurfaceType)
			throws Exception {
		ArrayList<ISurface> checkedSurfaces = new ArrayList<ISurface>();
		while (checkedSurfaces.size() < lvl.getXSize() * lvl.getYSize()) {
			int x = (int) Math.random() * lvl.getXSize();
			int y = (int) Math.random() * lvl.getYSize();
			if (!checkedSurfaces.contains(lvl.getLevel()[x][y]) && x != 0 && y != 0 && x != lvl.getXSize() - 1
					&& y != lvl.getYSize() - 1 && lvl.getLevel()[x][y].getClass() == oldSurfaceType.getClass()) {
				lvl.getLevel()[x][y] = newSurface;
				return;
			} else
				checkedSurfaces.add(lvl.getLevel()[x][y]);
		}
		throw new Exception("Level voll");
	}

	public boolean generateTextureMap(final Level lvl, final String path, final String name) {
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
