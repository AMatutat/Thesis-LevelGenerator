package other;

import constants.Reference;
import ga.CodedLevel;
import ga.Point;
import parser.LevelParser;

public class RandomWalk {

	public CodedLevel randomWalk(int xSize, int ySize, int wantedFloors) {

		char[][] lvl = new char[xSize][ySize];
		for (int x = 0; x < xSize; x++)
			for (int y = 0; y < ySize; y++)
				lvl[x][y] = Reference.REFERENCE_WALL;

		Point drunk = new Point((int) (Math.random() * xSize), (int) (Math.random() * ySize));

		while (wantedFloors > 0) {
			int direction = (int) (Math.random() * 4);
			switch (direction) {

			case 0:

				if (drunk.x < xSize - 2) {
					drunk.x += 1;
					if (lvl[drunk.x][drunk.y] == Reference.REFERENCE_WALL) {
						lvl[drunk.x][drunk.y] = Reference.REFERENCE_FLOOR;
						wantedFloors--;
					}
				}

				break;
			case 1:
				if (drunk.x > 1) {
					drunk.x -= 1;
					if (lvl[drunk.x][drunk.y] == Reference.REFERENCE_WALL) {
						lvl[drunk.x][drunk.y] = Reference.REFERENCE_FLOOR;
						wantedFloors--;
					}
				}
				break;
			case 2:

				if (drunk.y < ySize - 2) {
					drunk.y += 1;
					if (lvl[drunk.x][drunk.y] == Reference.REFERENCE_WALL) {
						lvl[drunk.x][drunk.y] = Reference.REFERENCE_FLOOR;
						wantedFloors--;
					}
				}
				break;
			case 3:
				if (drunk.y > 1) {
					drunk.y -= 1;
					if (lvl[drunk.x][drunk.y] == Reference.REFERENCE_WALL) {
						lvl[drunk.x][drunk.y] = Reference.REFERENCE_FLOOR;
						wantedFloors--;
					}
				}
				break;
			}

		}

		return new CodedLevel(lvl, xSize, ySize);

	}

	public static void main(String[] args) {

		CodedLevel level = new RandomWalk().randomWalk(150, 150, 70*70);
		new LevelParser().generateTextureMap(new LevelParser().parseLevel(level), "./results/img", "randomWalk");
	}

}
