package combiner;

import java.util.ArrayList;

import constants.Parameter;
import constants.Reference;
import ga.CodedLevel;
import ga.LevelGenerator;
import parser.LevelParser;

public class SpelunkyStyle {

	private final int ROWCOUNT = 4;
	private final int COLUMNCOUNT = 4;

	private final int ROOMCOUNT = ROWCOUNT * COLUMNCOUNT;
	private final int ROOMXSIZE = 10;
	private final int ROOMYSize = 9;
	private final float CHANCE_FOR_CONNECTION = 0.7f;

	boolean KEY_PLACED = false;
	boolean DOOR_PLACED = false;

	public CodedLevel generateLevel() {

		CodedLevel[] rooms = new CodedLevel[16];
		LevelGenerator lg = new LevelGenerator();

		// Bestimmen wo start und ende ist
		int startFrame = (int) (Math.random() * ROWCOUNT);
		int endFrame;

		// gen rooms, remove starts
		for (int i = 0; i < ROOMCOUNT; i++) {
			rooms[i] = lg.generateLevel(ROOMXSIZE, ROOMYSize, Parameter.MAXIMAL_GENERATION);
			if (i != startFrame)
				rooms[i].changeField(rooms[i].getStart().x, rooms[i].getStart().y, Reference.REFERENCE_FLOOR);
		}

		ArrayList<Integer> criticalPath = new ArrayList<Integer>();
		int aktuellerFrame = startFrame;
		criticalPath.add(aktuellerFrame);
		boolean run = true;
		while (run) {
			int nextFrame = move(aktuellerFrame, rooms);
			if (nextFrame > ROOMCOUNT - 1)
				run = false;
			else if (!criticalPath.contains(nextFrame)) {

				// remove for WEST
				if (aktuellerFrame - 1 == nextFrame) {
					int field = (int) (Math.random() * (ROOMYSize - 2)) + 1;
					removeWall(rooms[aktuellerFrame], Reference.REFERENCE_WEST, field);
					removeWall(rooms[nextFrame], Reference.REFERENCE_EAST, field);
				}
				// remove for EAST
				if (aktuellerFrame + 1 == nextFrame) {
					int field = (int) (Math.random() * (ROOMYSize - 2)) + 1;
					removeWall(rooms[aktuellerFrame], Reference.REFERENCE_EAST, field);
					removeWall(rooms[nextFrame], Reference.REFERENCE_WEST, field);
				}

				// remove for South
				if (aktuellerFrame + ROWCOUNT == nextFrame) {
					int field = (int) (Math.random() * (ROOMXSIZE - 2)) + 1;
					removeWall(rooms[aktuellerFrame], Reference.REFERENCE_SOUTH, field);
					removeWall(rooms[nextFrame], Reference.REFERENCE_NORTH, field);

				}

				aktuellerFrame = nextFrame;
				criticalPath.add(aktuellerFrame);

			}
		}
		endFrame = criticalPath.get(criticalPath.size() - 1);

		// remove exits
		for (int i = 0; i < ROOMCOUNT; i++) {
			if (i != endFrame)
				rooms[i].changeField(rooms[i].getExit().x, rooms[i].getExit().y, Reference.REFERENCE_FLOOR);
		}

		while (!KEY_PLACED) {
			CodedLevel room = rooms[criticalPath.get((int) (Math.random() * criticalPath.size()))];
			int x = (int) (Math.random() * ROOMXSIZE);
			int y = (int) (Math.random() * ROOMYSize);

			if (room.getLevel()[x][y] == Reference.REFERENCE_FLOOR) {
				room.changeField(x, y, Reference.REFERENCE_FLOOR_WITH_KEY);
				KEY_PLACED = true;
			}

		}

		connectUnconnected(rooms, criticalPath);

		CodedLevel level = new CodedLevel(new char[ROWCOUNT * ROOMXSIZE][COLUMNCOUNT * ROOMYSize], ROWCOUNT * ROOMXSIZE,
				COLUMNCOUNT * ROOMYSize);

		int levelX = 0;
		int levelY = 0;
		int tempX = 0;
		for (int room = 0; room < ROOMCOUNT; room++) {
			CodedLevel roomToCoppy = rooms[room];
			levelX = 10 * ((int) room % ROWCOUNT);
			levelY = 9 * ((int) room / COLUMNCOUNT);
			for (int roomY = 0; roomY < ROOMYSize; roomY++) {
				levelX = 10 * ((int) room % ROWCOUNT);
				for (int roomX = 0; roomX < ROOMXSIZE; roomX++) {
					if (levelX == 0 || levelY == 0 || levelY == COLUMNCOUNT * ROOMYSize - 1
							|| levelX == ROWCOUNT * ROOMXSIZE - 1)
						level.changeField(levelX, levelY, Reference.REFERENCE_WALL);
					else
						level.changeField(levelX, levelY, roomToCoppy.getLevel()[roomX][roomY]);

					levelX++;
				}
				levelY++;
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
			if (aktuellerFrame % ROWCOUNT == 0)
				aktuellerFrame += ROWCOUNT;
			else
				aktuellerFrame--;
			break;

		// recht
		case 2:
		case 3:
			if (aktuellerFrame % ROWCOUNT == ROWCOUNT - 1)
				aktuellerFrame += ROWCOUNT;
			else
				aktuellerFrame++;
			break;

		// unten
		case 4:
			aktuellerFrame += ROWCOUNT;
			break;

		}

		return aktuellerFrame;

	}

	private void connectUnconnected(CodedLevel[] rooms, ArrayList<Integer> connected) {
		ArrayList<Integer> unconnectedRooms = new ArrayList<Integer>();
		for (int room = 0; room < ROOMCOUNT; room++)
			if (!connected.contains(room))
				unconnectedRooms.add(room);

		if (unconnectedRooms.size() > 0) {
			Integer room = unconnectedRooms.get(0);
			ArrayList<Integer> tempConnected = new ArrayList<Integer>();
			while (!connected.contains(room)) {
				boolean change = false;
				// linker nachbar
				if (!change && room - 1 >= 0) {
					if (Math.random() < CHANCE_FOR_CONNECTION) {
						int field = (int) (Math.random() * ROOMYSize - 2) + 1;
						removeWall(rooms[room - 1], Reference.REFERENCE_EAST, field);
						removeWall(rooms[room], Reference.REFERENCE_WEST, field);
						if (!DOOR_PLACED) {
							rooms[room - 1].changeField(ROOMXSIZE - 1, field, Reference.REFERENCE_DOOR);
							DOOR_PLACED = true;
						}

						if (connected.contains(room - 1)) {
							connected.add(room);
							change = true;
						}

						else {
							tempConnected.add(room);
							change = true;
							room = room - 1;
						}
					}
				}
				// rechter nachbar
				if (!change && room % ROWCOUNT != ROWCOUNT - 1) {
					if (Math.random() < CHANCE_FOR_CONNECTION) {
						int field = (int) (Math.random() * ROOMYSize - 2) + 1;
						removeWall(rooms[room + 1], Reference.REFERENCE_WEST, field);
						removeWall(rooms[room], Reference.REFERENCE_EAST, field);
						if (!DOOR_PLACED) {
							rooms[room + 1].changeField(0, field, Reference.REFERENCE_DOOR);
							DOOR_PLACED = true;
						}
						if (connected.contains(room + 1)) {
							connected.add(room);
							change = true;
						}

						else {
							tempConnected.add(room);
							room = room + 1;
							change = true;
						}
					}
				}

				// nachbar unten
				if (!change && room + ROWCOUNT < ROOMCOUNT) {
					if (Math.random() < CHANCE_FOR_CONNECTION) {
						int field = (int) (Math.random() * ROOMXSIZE - 2) + 1;
						removeWall(rooms[room], Reference.REFERENCE_SOUTH, field);
						removeWall(rooms[room + ROWCOUNT], Reference.REFERENCE_NORTH, field);
						if (!DOOR_PLACED) {
							rooms[room + ROWCOUNT].changeField(field, 0, Reference.REFERENCE_DOOR);
							DOOR_PLACED = true;
						}
						if (connected.contains(room + ROWCOUNT)) {
							connected.add(room);
							change = true;
						}

						else {
							tempConnected.add(room);
							room = room + ROWCOUNT;
							change = true;
						}
					}
				}

			}
			for (Integer i : tempConnected)
				connected.add(i);

			connectUnconnected(rooms, connected);
		}
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
			room.changeField(ROOMXSIZE - 1, field, Reference.REFERENCE_FLOOR);
			if (room.getLevel()[ROOMYSize - 1][field] == Reference.REFERENCE_WALL)
				room.changeField(ROOMYSize - 1, field, Reference.REFERENCE_FLOOR);
			break;

		case Reference.REFERENCE_WEST:
			room.changeField(0, field, Reference.REFERENCE_FLOOR);
			if (room.getLevel()[1][field] == Reference.REFERENCE_WALL)
				room.changeField(1, field, Reference.REFERENCE_FLOOR);
			break;
		}

	}

	public static void main(String[] args) {
		for (int i = 0; i < 4; i++) {

			CodedLevel level = new SpelunkyStyle().generateLevel();
			new LevelParser().generateTextureMap(new LevelParser().parseLevel(level), "./results/img", "spelunky_" + i);
		}

	}

}
