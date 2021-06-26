package combiner;

import ga.CodedLevel;
import ga.Point;

/**
 * CodedLevel mit Informationen �ber die platzierung im Level.
 *
 * @author Andre Matutat
 */
public class CodedRoom extends CodedLevel {

    private Point midPoint = null;
    private Point upperRightCorner = null;
    private Point upperLeftCorner = null;
    private Point lowerRightCorner = null;
    private Point lowerLeftCorner = null;

    public CodedRoom(char[][] level, int xSize, int ySize) {
        super(level, xSize, ySize);
    }

    /** @return Levelkoordinaten des Raummittelpunkts */
    public Point getMidPoint() {
        return midPoint;
    }

    /**
     * Setzt die Koordinaten des Raummittelpunkts
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public void setMidPoint(int x, int y) {
        this.midPoint = new Point(x, y);
    }

    /**
     * Setzt die Koordinaten der oberen rechten Ecke
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public void setUpperRightCorner(int x, int y) {
        this.upperRightCorner = new Point(x, y);
    }

    /**
     * Setzt die Koordinaten der oberen linken Ecke
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public void setUpperLeftCorner(int x, int y) {
        this.upperLeftCorner = new Point(x, y);
    }

    /**
     * Setzt die Koordinaten der unteren rechten Ecke
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public void setLowerRightCorner(int x, int y) {
        this.lowerRightCorner = new Point(x, y);
    }

    /**
     * Setzt die Koordinaten der unteren linken Ecke
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public void setLowerLeftCorner(int x, int y) {
        this.lowerLeftCorner = new Point(x, y);
    }

    /** @return Levelkoordinaten der oberen rechten Ecke */
    public Point getUpperRightCorner() {
        return this.upperRightCorner;
    }

    /** @return Levelkoordinaten der oberen linken Ecke */
    public Point getUpperLeftCorner() {
        return this.upperLeftCorner;
    }

    /** @return Levelkoordinaten der unteren rechten Ecke */
    public Point getLowerRightCorner() {
        return this.lowerRightCorner;
    }

    /** @return Levelkoordinaten der unteren linken Ecke */
    public Point getLowerLeftCorner() {
        return this.lowerLeftCorner;
    }
}
