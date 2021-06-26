package combiner;

import java.util.ArrayList;

import constants.Parameter;
import constants.Reference;
import ga.CodedLevel;
import ga.LevelGenerator;

/**
 * Erstellt Level aus R�umen, angelehnt an das Vorgehen des Spiels Spelunky
 *
 * @author Andre Matutat
 */
public class SpelunkyStyle {

    private final int ROWCOUNT = 4;
    private final int COLUMNCOUNT = 4;

    private final int ROOMCOUNT = ROWCOUNT * COLUMNCOUNT;
    private final int ROOMXSIZE = 10;
    private final int ROOMYSIZE = 9;
    private final float CHANCE_FOR_CONNECTION = 0.7f;

    boolean KEY_PLACED = false;
    boolean DOOR_PLACED = false;

    /**
     * Generiert Spelunky-Style Level
     *
     * @return generiertes Level
     */
    public CodedLevel generateLevel() {

        CodedLevel[] rooms = new CodedLevel[16];
        LevelGenerator lg = new LevelGenerator();

        // Bestimmen wo start ist
        int startFrame = (int) (Math.random() * ROWCOUNT);
        int endFrame;

        // generate rooms, remove starts
        for (int i = 0; i < ROOMCOUNT; i++) {
            rooms[i] = lg.generateLevel(ROOMXSIZE, ROOMYSIZE, Parameter.MAXIMAL_GENERATION);
            if (i != startFrame)
                rooms[i].changeField(
                        rooms[i].getStart().x, rooms[i].getStart().y, Reference.REFERENCE_FLOOR);
        }

        // Lege kritischen Pfad
        ArrayList<Integer> criticalPath = new ArrayList<Integer>();
        int aktuellerFrame = startFrame;
        criticalPath.add(aktuellerFrame);
        boolean run = true;
        while (run) {
            int nextFrame = move(aktuellerFrame, rooms);
            if (nextFrame > ROOMCOUNT - 1) run = false;
            else if (!criticalPath.contains(nextFrame)) {

                // remove for WEST
                if (aktuellerFrame - 1 == nextFrame) {
                    int field = (int) (Math.random() * (ROOMYSIZE - 2)) + 1;
                    removeWall(rooms[aktuellerFrame], Reference.REFERENCE_WEST, field);
                    removeWall(rooms[nextFrame], Reference.REFERENCE_EAST, field);
                }
                // remove for EAST
                if (aktuellerFrame + 1 == nextFrame) {
                    int field = (int) (Math.random() * (ROOMYSIZE - 2)) + 1;
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
        // place exit
        endFrame = criticalPath.get(criticalPath.size() - 1);

        // remove exits
        for (int i = 0; i < ROOMCOUNT; i++) {
            if (i != endFrame)
                rooms[i].changeField(
                        rooms[i].getExit().x, rooms[i].getExit().y, Reference.REFERENCE_FLOOR);
        }

        // place key
        while (!KEY_PLACED) {
            CodedLevel room = rooms[criticalPath.get((int) (Math.random() * criticalPath.size()))];
            int x = (int) (Math.random() * ROOMXSIZE);
            int y = (int) (Math.random() * ROOMYSIZE);

            if (room.getLevel()[x][y] == Reference.REFERENCE_FLOOR) {
                room.changeField(x, y, Reference.REFERENCE_FLOOR_WITH_KEY);
                KEY_PLACED = true;
            }
        }

        connectUnconnected(rooms, criticalPath);

        // parsen zum CodedLevel
        CodedLevel level =
                new CodedLevel(
                        new char[ROWCOUNT * ROOMXSIZE][COLUMNCOUNT * ROOMYSIZE],
                        ROWCOUNT * ROOMXSIZE,
                        COLUMNCOUNT * ROOMYSIZE);
        int levelX = 0;
        int levelY = 0;
        int tempX = 0;
        for (int room = 0; room < ROOMCOUNT; room++) {
            CodedLevel roomToCoppy = rooms[room];
            levelX = ROOMXSIZE * ((int) room % ROWCOUNT);
            levelY = ROOMYSIZE * ((int) room / COLUMNCOUNT);
            for (int roomY = 0; roomY < ROOMYSIZE; roomY++) {
                levelX = ROOMXSIZE * ((int) room % ROWCOUNT);
                for (int roomX = 0; roomX < ROOMXSIZE; roomX++) {
                    if (levelX == 0
                            || levelY == 0
                            || levelY == COLUMNCOUNT * ROOMYSIZE - 1
                            || levelX == ROWCOUNT * ROOMXSIZE - 1)
                        level.changeField(levelX, levelY, Reference.REFERENCE_WALL);
                    else level.changeField(levelX, levelY, roomToCoppy.getLevel()[roomX][roomY]);

                    levelX++;
                }
                levelY++;
            }
        }

        return level;
    }

    /**
     * Bewegt den kritischen Pfad in eine zuf�llige Richtung
     *
     * @param aktuellerFrame Aktueller Raum
     * @param rooms Liste aller R�ume
     * @return Index des betrotteten Raums
     */
    private int move(int aktuellerFrame, CodedLevel[] rooms) {
        int dice = (int) (Math.random() * 5);
        switch (dice) {
                // links
            case 0:
            case 1:
                // end of row, move down
                if (aktuellerFrame % ROWCOUNT == 0) aktuellerFrame += ROWCOUNT;
                else aktuellerFrame--;
                break;

                // recht
            case 2:
            case 3:
                if (aktuellerFrame % ROWCOUNT == ROWCOUNT - 1) aktuellerFrame += ROWCOUNT;
                else aktuellerFrame++;
                break;

                // unten
            case 4:
                aktuellerFrame += ROWCOUNT;
                break;
        }

        return aktuellerFrame;
    }

    /**
     * Verbindet alle nicht verbundenen Rooms mit den kritischen Pfad
     *
     * @param rooms Array mit allen Rooms
     * @param connected Liste mit verbundenen Rooms
     */
    private void connectUnconnected(CodedLevel[] rooms, ArrayList<Integer> connected) {

        // Erstellen einer Lister, aller unberbundenen Rooms
        ArrayList<Integer> unconnectedRooms = new ArrayList<Integer>();
        for (int room = 0; room < ROOMCOUNT; room++)
            if (!connected.contains(room)) {
                unconnectedRooms.add(room);
                System.out.println(room + " uncoonected");
            }

        if (unconnectedRooms.size() > 0) {

            // Index des betrachteten Rooms
            Integer room = unconnectedRooms.get(0);

            // Verbindungen zwischen unterverbundenen Rooms
            ArrayList<Integer> tempConnected = new ArrayList<Integer>();

            while (!connected.contains(room)) {
                boolean change = false;

                // linker nachbar
                if (room - 1 >= 0 && (room - 1) / ROWCOUNT == room / ROWCOUNT) {
                    if (Math.random() < CHANCE_FOR_CONNECTION) {

                        // Position des Durchgangs
                        int field = (int) (Math.random() * ROOMYSIZE - 2) + 1;
                        removeWall(rooms[room - 1], Reference.REFERENCE_EAST, field);
                        removeWall(rooms[room], Reference.REFERENCE_WEST, field);

                        // Place Door
                        if (!DOOR_PLACED) {
                            rooms[room + 1].changeField(0, field, Reference.REFERENCE_DOOR);
                            DOOR_PLACED = true;
                        }

                        // Wenn der Nachbar verbunden ist, ist dieser Room jetz auch verbunden
                        if (connected.contains(room - 1)) {
                            connected.add(room);
                            change = true;
                        }

                        // Nachbar Raum ist jetzt f�r die Verbindung zum kritschen Pfad
                        // verantwortlich
                        else {
                            tempConnected.add(room);
                            room = room - 1;
                            change = true;
                        }
                    }
                }
                // rechter nachbar
                if (!change
                        && room % ROWCOUNT != ROWCOUNT - 1
                        && (room + 1) / ROWCOUNT == room / ROWCOUNT) {

                    if (Math.random() < CHANCE_FOR_CONNECTION) {
                        int field = (int) (Math.random() * ROOMYSIZE - 2) + 1;
                        removeWall(rooms[room + 1], Reference.REFERENCE_WEST, field);
                        removeWall(rooms[room], Reference.REFERENCE_EAST, field);
                        if (!DOOR_PLACED) {
                            rooms[room + 1].changeField(0, field, Reference.REFERENCE_DOOR);
                            DOOR_PLACED = true;
                        }
                        if (connected.contains(room + 1)) {
                            connected.add(room);
                            change = true;
                        } else {
                            tempConnected.add(room);
                            room = room + 1;
                            change = true;
                        }
                    }
                }

                // unterer nachbar
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
                        } else {
                            tempConnected.add(room);
                            room = room + ROWCOUNT;
                            change = true;
                        }
                    }
                }
            }

            for (Integer i : tempConnected) connected.add(i);

            connectUnconnected(rooms, connected);
        }
    }

    /**
     * Entfernt eine Wand aus einem Raum
     *
     * @param room Raum der ver�ndert werden soll
     * @param direction Himmelsrichtung der Wand
     * @param field Index der Wand
     */
    private void removeWall(CodedLevel room, int direction, int field) {
        switch (direction) {
            case Reference.REFERENCE_NORTH:
                room.changeField(field, 0, Reference.REFERENCE_FLOOR);
                if (room.getLevel()[field][1] == Reference.REFERENCE_WALL)
                    room.changeField(field, 1, Reference.REFERENCE_FLOOR);
                break;
            case Reference.REFERENCE_SOUTH:
                room.changeField(field, ROOMYSIZE - 1, Reference.REFERENCE_FLOOR);
                if (room.getLevel()[field][ROOMYSIZE - 2] == Reference.REFERENCE_WALL)
                    room.changeField(field, ROOMYSIZE - 2, Reference.REFERENCE_FLOOR);
                break;
            case Reference.REFERENCE_EAST:
                room.changeField(ROOMXSIZE - 1, field, Reference.REFERENCE_FLOOR);
                if (room.getLevel()[ROOMYSIZE - 1][field] == Reference.REFERENCE_WALL)
                    room.changeField(ROOMYSIZE - 1, field, Reference.REFERENCE_FLOOR);
                break;

            case Reference.REFERENCE_WEST:
                room.changeField(0, field, Reference.REFERENCE_FLOOR);
                if (room.getLevel()[1][field] == Reference.REFERENCE_WALL)
                    room.changeField(1, field, Reference.REFERENCE_FLOOR);
                break;
        }
    }
}
