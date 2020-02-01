package combiner;

import java.util.ArrayList;

import constants.*;
import constants.Parameter;
import ga.CodedLevel;
import ga.LevelGenerator;
import parser.LevelParser;

public class ConnectedRoomGenerator {

	public CodedLevel generateLevelWithRooms(final int xSize, final int ySize) {

		CodedLevel level = new CodedLevel(new char[ (int)(xSize * 2.5)][ (int)(ySize * 2.5)], (int)(xSize * 2.5), (int)(ySize * 2.5));
		ArrayList<CodedRoom> rooms = new ArrayList<CodedRoom>();

		for (int x = 0; x < level.getXSize(); x++)
			for (int y = 0; y < level.getYSize(); y++)
				level.getLevel()[x][y] = Reference.REFERENCE_EMPTY;
		int fe = xSize * ySize;
		LevelGenerator lg = new LevelGenerator();
		while (fe > 0) {
			int xp = (int) (Math.random() * xSize);
			int yp = (int) (Math.random() * ySize);
			if (xp < Parameter.MINIMAL_XSIZE)
				xp =  Parameter.MINIMAL_XSIZE;
			if (yp <  Parameter.MINIMAL_YSIZE)
				yp =  Parameter.MINIMAL_YSIZE;
			CodedLevel rl = lg.generateLevel(xp, yp, Parameter.MAXIMAL_GENERATION);
			CodedRoom r = new CodedRoom(rl.getLevel(), rl.getXSize(), rl.getYSize());
			r.changeField(rl.getStart().x, rl.getStart().y, Reference.REFERENCE_START);
			r.changeField(rl.getExit().x, rl.getExit().y, Reference.REFEERNCE_EXIT);
			rooms.add(r);
			fe -= xp * yp;
		}

		// Bestimme Raum in den Start und Exit sind
		int startRoom = (int) (Math.random() * rooms.size());
		int exitRoom = (int) (Math.random() * rooms.size());

		// remove start and exit for each room
		for (CodedLevel room : rooms) {
			if (rooms.indexOf(room) != startRoom)
				room.changeField(room.getStart().x, room.getStart().y, Reference.REFERENCE_WALL);
			if (rooms.indexOf(room) != exitRoom)
				room.changeField(room.getExit().x, room.getExit().y, Reference.REFERENCE_WALL);
		}

		// Platziere rooms
		level = placeRooms(rooms, level);
		
		ArrayList<CodedRoom> notConnected = new ArrayList<CodedRoom>();
		notConnected.add(rooms.get(0));
		for (int j = 0; j < rooms.size() - 1; j++) {
			CodedRoom room = notConnected.get(j);
			int abstand = Integer.MAX_VALUE;
			int index = j + 1;
			for (int i = 0; i < rooms.size(); i++) {
				if (!notConnected.contains(rooms.get(j))) {
					int tempabstand = Math.abs(room.getMidPoint().x - rooms.get(j).getMidPoint().x)
							+ Math.abs(room.getMidPoint().y - rooms.get(j).getMidPoint().y);
					if (abstand > tempabstand) {
						abstand = tempabstand;
						index = j;
					}
				}

			}
			notConnected.add(rooms.get(index));
		}
		this.connectRoomsWithSquareFloors(level, notConnected);

		// leere Felder mit Walls auffüllen
		for (int x = 0; x < level.getXSize(); x++)
			for (int y = 0; y < level.getYSize(); y++)
				if (level.getLevel()[x][y] == Reference.REFERENCE_EMPTY)
					level.changeField(x, y, Reference.REFERENCE_WALL);

		return level;

	}

	private CodedLevel placeRooms(ArrayList<CodedRoom> rooms, CodedLevel lvl) {
		CodedLevel level = lvl.copyLevel();
		int counter = 0;
		int placed = 0;
		while (placed < rooms.size() && counter < 100) {
			CodedRoom room = rooms.get(placed);
			int yp;
			int xp;
			do {
				xp = (int) (Math.random() * (level.getXSize() - room.getXSize()));
				yp = (int) (Math.random() * (level.getYSize() - room.getYSize()));
			} while (xp + room.getXSize() > level.getXSize() || yp + room.getYSize() > level.getYSize());

			boolean free = true;

			// checken ob kein andere raum im weg ist
			for (int x = xp; x < xp + room.getXSize() - 1; x++)
				for (int y = yp; y < yp + room.getYSize() - 1; y++)
					if (level.getLevel()[x][y] != Reference.REFERENCE_EMPTY)
						free = false;

			if (free) {

				for (int x = xp; x < xp + room.getXSize(); x++) {

					for (int y = yp; y < yp + room.getYSize(); y++) {

						level.changeField(x, y, room.getLevel()[x - xp][y - yp]);
					}

				}
				room.setUpperLeftCorner(xp, yp);
				room.setUpperRightCorner(xp + room.getXSize(), yp);
				room.setLowerLeftCorner(xp, yp + room.getYSize());
				room.setLowerRightCorner(xp + room.getXSize(), yp + room.getYSize());
				room.setMidPoint(xp + room.getXSize() / 2, yp + room.getYSize() / 2);
				placed++;
			}

			counter++;
		}

		if (counter >= 100)
			return placeRooms(rooms, lvl);
		else
			return level;

	}

	private void connectRoomsWithSquareFloors(CodedLevel level, ArrayList<CodedRoom> rooms) {

		for (int i = 0; i < rooms.size() - 1; i++) {
			CodedRoom r1 = rooms.get(i);
			CodedRoom r2 = rooms.get(i + 1);

			int xAbstand = r2.getMidPoint().x - r1.getMidPoint().x;
			int yAbstand = r2.getMidPoint().y - r1.getMidPoint().y;
			if (yAbstand < 0) {
				// links oben
				if (xAbstand < 0) {
					for (int y = Math.abs(yAbstand); y > 0; y--) {						
						level.changeField(r1.getMidPoint().x, r1.getMidPoint().y - y, Reference.REFERENCE_FLOOR);
					}
					for (int x = Math.abs(xAbstand); x > 0; x--) {
						
						
			if(level.getLevel()[r1.getMidPoint().x - x][ r1.getMidPoint().y + yAbstand]!=Reference.REFERENCE_START && level.getLevel()[r1.getMidPoint().x - x][ r1.getMidPoint().y + yAbstand]!=Reference.REFEERNCE_EXIT)			
						level.changeField(r1.getMidPoint().x - x, r1.getMidPoint().y + yAbstand,
								Reference.REFERENCE_FLOOR);
					}

					// recht oben
				} else {
					for (int y = Math.abs(yAbstand); y > 0; y--) {
						level.changeField(r1.getMidPoint().x, r1.getMidPoint().y - y, Reference.REFERENCE_FLOOR);
					}
					for (int x = 0; x <= xAbstand; x++) {
						
						if(level.getLevel()[r1.getMidPoint().x + x][ r1.getMidPoint().y + yAbstand]!=Reference.REFERENCE_START && level.getLevel()[r1.getMidPoint().x + x][ r1.getMidPoint().y + yAbstand]!=Reference.REFEERNCE_EXIT)			
						level.changeField(r1.getMidPoint().x + x, r1.getMidPoint().y + yAbstand,
								Reference.REFERENCE_FLOOR);
					}

				}

			} else {
				// links unten
				if (xAbstand < 0) {
					for (int y = 0; y <= yAbstand; y++) {
						level.changeField(r1.getMidPoint().x, r1.getMidPoint().y + y, Reference.REFERENCE_FLOOR);
					}

					for (int x = Math.abs(xAbstand); x > 0; x--) {
						
						if(level.getLevel()[r1.getMidPoint().x - x][ r1.getMidPoint().y + yAbstand]!=Reference.REFERENCE_START && level.getLevel()[r1.getMidPoint().x - x][ r1.getMidPoint().y + yAbstand]!=Reference.REFEERNCE_EXIT)			
						level.changeField(r1.getMidPoint().x - x, r1.getMidPoint().y + yAbstand,
								Reference.REFERENCE_FLOOR);
					}

					// recht unten
				} else {

					for (int y = 0; y <= yAbstand; y++) {
						level.changeField(r1.getMidPoint().x, r1.getMidPoint().y + y, Reference.REFERENCE_FLOOR);
					}

					for (int x = 0; x <= xAbstand; x++) {
						if(level.getLevel()[r1.getMidPoint().x + x][ r1.getMidPoint().y + yAbstand]!=Reference.REFERENCE_START && level.getLevel()[r1.getMidPoint().x + x][ r1.getMidPoint().y + yAbstand]!=Reference.REFEERNCE_EXIT)		
						level.changeField(r1.getMidPoint().x + x, r1.getMidPoint().y + yAbstand,
								Reference.REFERENCE_FLOOR);
					}
				}
			}

		}

	}

	public static void main(String[] args) {
		ConnectedRoomGenerator cr = new ConnectedRoomGenerator();
		LevelParser p = new LevelParser();
		for (int i = 0; i < 10; i++)
			p.generateTextureMap(p.parseLevel(cr.generateLevelWithRooms(50, 50)), "./results/img", "test_" + i);

	}
}
