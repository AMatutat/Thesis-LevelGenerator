package combiner;

import java.util.ArrayList;

import constants.Reference;
import ga.CodedLevel;
import ga.LevelGenerator;
import parser.LevelParser;

public class SpelunkyStyle {

	public SpelunkyStyle() {
		// TODO Auto-generated constructor stub
	}

	boolean key = false;
	boolean door = false;

	public CodedLevel generateLevel() {

		CodedLevel[] rooms = new CodedLevel[16];
		LevelGenerator lg = new LevelGenerator();

		// Bestimmen wo start und ende ist
		int startFrame = (int) (Math.random() * 4);
		int endFrame;

		// gen rooms, remove starts
		for (int i = 0; i < 16; i++) {
			rooms[i] = lg.generateLevel(10, 9, 2, 1, 2, 2);
			if (i != startFrame)
				rooms[i].changeField(rooms[i].getStart().x, rooms[i].getStart().y, Reference.REFERENCE_FLOOR);
		}

		ArrayList<Integer> criticalPath = new ArrayList<Integer>();
		int aktuellerFrame = startFrame;
		criticalPath.add(aktuellerFrame);
		boolean run = true;
		while (run) {
			int nextFrame = move(aktuellerFrame, rooms);
			if (nextFrame > 15)
				run = false;
			else if (!criticalPath.contains(nextFrame)) {

				// remove for WEST
				if (aktuellerFrame - 1 == nextFrame) {
					int field = (int) (Math.random() * 7) + 1;
					removeWall(rooms[aktuellerFrame], Reference.REFERENCE_WEST, field);
					removeWall(rooms[nextFrame], Reference.REFERENCE_EAST, field);
				}
				// remove for EAST
				if (aktuellerFrame + 1 == nextFrame) {
					int field = (int) (Math.random() * 7) + 1;
					removeWall(rooms[aktuellerFrame], Reference.REFERENCE_EAST, field);
					removeWall(rooms[nextFrame], Reference.REFERENCE_WEST, field);
				}

				// remove for South
				if (aktuellerFrame + 4 == nextFrame) {
					int field = (int) (Math.random() * 8) + 1;
					removeWall(rooms[aktuellerFrame], Reference.REFERENCE_SOUTH, field);
					removeWall(rooms[nextFrame], Reference.REFERENCE_NORTH, field);

				}

				aktuellerFrame = nextFrame;
				criticalPath.add(aktuellerFrame);

			}
		}
		endFrame = criticalPath.get(criticalPath.size() - 1);

		// remove exits
		for (int i = 0; i < 16; i++) {
			if (i != endFrame)
				rooms[i].changeField(rooms[i].getExit().x, rooms[i].getExit().y, Reference.REFERENCE_FLOOR);
		}

		while (!key) {
			CodedLevel room = rooms[criticalPath.get((int) (Math.random() * criticalPath.size()))];
			int x = (int) (Math.random() * 10);
			int y = (int) (Math.random() * 9);

			if (room.getLevel()[x][y] == Reference.REFERENCE_FLOOR) {
				room.changeField(x, y, Reference.REFERENCE_FLOOR_WITH_KEY);
				key = true;
			}

		}

		connectUnconnected(rooms, criticalPath);

		CodedLevel level = new CodedLevel(new char[40][36], 40, 36);

		int bx = 0;
		int by = 0;
		int bbx = 0;
		for (int r = 0; r < 16; r++) {
			CodedLevel toCpy = rooms[r];
			bx = 10 * ((int) r % 4);
			by = 9 * ((int) r / 4);
			for (int y = 0; y < 9; y++) {
				bx = 10 * ((int) r % 4);
				for (int x = 0; x < 10; x++) {
					if (bx == 0 || by == 0 || by == 35 || bx == 39)
						level.changeField(bx, by, Reference.REFERENCE_WALL);
					else
						level.changeField(bx, by, toCpy.getLevel()[x][y]);

					bx++;
				}
				by++;
			}
		}

		return level;
	}

	private int move(int aktuellerFrame, CodedLevel[] rooms) {
		int dice = (int) (Math.random() * 5);
		switch (dice) {
		// links
		case 0:
		case 1:
			// end of row, move down
			if (aktuellerFrame % 4 == 0)
				aktuellerFrame += 4;
			else
				aktuellerFrame--;
			break;

		case 2:
		case 3:
			if (aktuellerFrame % 4 == 3)
				aktuellerFrame += 4;
			else
				aktuellerFrame++;
			break;

		case 4:
			aktuellerFrame += 4;
			break;

		}

		return aktuellerFrame;

	}

	private void connectUnconnected(CodedLevel[] rooms, ArrayList<Integer> connected) {
		ArrayList<Integer> unconnected = new ArrayList<Integer>();
		for (int i = 0; i < 16; i++)
			if (!connected.contains(i))
				unconnected.add(i);

		if (unconnected.size() > 0) {
			Integer room = unconnected.get(0);
			ArrayList<Integer> tempCon = new ArrayList<Integer>();
			while (!connected.contains(room)) {
				boolean change = false;
				// linker nachbar
				if (!change && room - 1 >= 0) {
					if (Math.random() > 0.7) {
						int field = (int) (Math.random() * 7) + 1;
						removeWall(rooms[room - 1], Reference.REFERENCE_EAST, field);
						removeWall(rooms[room], Reference.REFERENCE_WEST, field);
						if (!door) {
							rooms[room - 1].changeField(9, field, Reference.REFERENCE_DOOR);
							door = true;
						}

						if (connected.contains(room - 1)) {
							connected.add(room);
							change = true;
						}

						else {
							tempCon.add(room);
							change = true;
							room = room - 1;
						}
					}
				}
				// rechter nachbar
				if (!change && room % 4 != 3) {
					if (Math.random() > 0.7) {
						int field = (int) (Math.random() * 7) + 1;
						removeWall(rooms[room + 1], Reference.REFERENCE_WEST, field);
						removeWall(rooms[room], Reference.REFERENCE_EAST, field);
						if (!door) {
							rooms[room + 1].changeField(0, field, Reference.REFERENCE_DOOR);
							door = true;
						}
						if (connected.contains(room + 1)) {
							connected.add(room);
							change = true;
						}

						else {
							tempCon.add(room);
							room = room + 1;
							change = true;
						}
					}
				}

				// nachbar unten
				if (!change && room + 4 < 16) {
					if (Math.random() > 0.7) {
						int field = (int) (Math.random() * 8) + 1;
						removeWall(rooms[room], Reference.REFERENCE_SOUTH, field);
						removeWall(rooms[room + 4], Reference.REFERENCE_NORTH, field);
						if (!door) {
							rooms[room + 4].changeField(field, 0, Reference.REFERENCE_DOOR);
							door = true;
						}
						if (connected.contains(room + 4)) {
							connected.add(room);
							change = true;
						}

						else {
							tempCon.add(room);
							room = room + 4;
							change = true;
						}
					}
				}

			}
			for (Integer i : tempCon)
				connected.add(i);

			connectUnconnected(rooms, connected);
		}
		System.out.println("testEnd");
	}

	private void removeWall(CodedLevel room, int direction, int field) {
		switch (direction) {
		case Reference.REFERENCE_NORTH:
			room.changeField(field, 0, Reference.REFERENCE_FLOOR);
			if (room.getLevel()[field][1] == Reference.REFERENCE_WALL)
				room.changeField(field, 1, Reference.REFERENCE_FLOOR);
			break;
		case Reference.REFERENCE_SOUTH:
			room.changeField(field, 8, Reference.REFERENCE_FLOOR);
			if (room.getLevel()[field][7] == Reference.REFERENCE_WALL)
				room.changeField(field, 7, Reference.REFERENCE_FLOOR);
			break;
		case Reference.REFERENCE_EAST:
			room.changeField(9, field, Reference.REFERENCE_FLOOR);
			if (room.getLevel()[8][field] == Reference.REFERENCE_WALL)
				room.changeField(8, field, Reference.REFERENCE_FLOOR);
			break;

		case Reference.REFERENCE_WEST:
			room.changeField(0, field, Reference.REFERENCE_FLOOR);
			if (room.getLevel()[1][field] == Reference.REFERENCE_WALL)
				room.changeField(1, field, Reference.REFERENCE_FLOOR);
			break;
		}

	}

	public static void main(String[] args) {
		for (int i = 0; i < 15; i++) {

			CodedLevel level = new SpelunkyStyle().generateLevel();
			new LevelParser().generateTextureMap(new LevelParser().parseLevel(level), "./results/img", "spelunky_" + i);
		}

	}

}
