package generator;

public class CodedRoom extends CodedLevel {

	private Point midPoint = null;
	private Point upperRightCorner = null;
	private Point upperLeftCorner = null;
	private Point lowerRightCorner = null;
	private Point lowerLeftCorner = null;

	public CodedRoom(char[][] level, int xSize, int ySize) {
		super(level, xSize, ySize);
	}

	public Point getMidPoint() {
		return midPoint;
	}
	
	public void setMidPoint(int x,int y) {
		this.midPoint=new Point(x,y);
	}

	public void setUpperRightCorner(int x, int y) {
		this.upperRightCorner = new Point(x, y);
	}

	public void setUpperLeftCorner(int x, int y) {
		this.upperLeftCorner = new Point(x, y);
	}

	public void setLowerRightCorner(int x, int y) {
		this.lowerRightCorner = new Point(x, y);
	}

	public void setLowerLeftCorner(int x, int y) {
		this.lowerLeftCorner = new Point(x, y);
	}

	public Point getUpperRightCorner() {
		return this.upperRightCorner;
	}

	public Point getUpperLeftCorner() {
		return this.upperLeftCorner;
	}

	public Point getLowerRightCorner() {
		return this.lowerRightCorner;
	}

	public Point getLowerLeftCorner() {
		return this.lowerLeftCorner;
	}

}
