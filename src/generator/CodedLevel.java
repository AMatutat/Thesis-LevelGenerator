package generator;

public class CodedLevel {

	private char[][] level;
	private int fitness;
	private final int xSize;
	private final int ySize;
	private boolean hasStart = false;
	private boolean hasExit = false;
	private int[] exit = new int[2];
	private int[] start = new int[2];

	public CodedLevel(final char[][] level, int xSize, int ySize) {
		this.level = level;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public void setFitness(final int fitness) {
		this.fitness = fitness;
	}

	public int getFitness() {
		return this.fitness;
	}

	public boolean hasStart() {
		return this.hasStart;
	}

	public boolean hasExit() {
		return this.hasExit;
	}

	public int getXSize() {
		return this.xSize;
	}

	public int getYSize() {
		return this.ySize;
	}

	public int[] getExit() {
		if (hasExit)
		return this.exit;
		return null;
	}

	public int[] getStart() {
		if (hasStart)
		return this.start;
		return null;
	}

	public char[][] getLevel() {
		return this.level;
	}

	public void changeField(int x, int y, char s) {
		if (this.level[x][y] == Constants.REFEERNCE_EXIT)
			this.hasExit = false;

		else if (this.level[x][y] == Constants.REFERENCE_START)
			this.hasStart = false;

		if (s == Constants.REFEERNCE_EXIT) {
			this.hasExit = true;
			this.exit[0] = x;
			this.exit[1] = y;
		} else if (s == Constants.REFERENCE_START) {
			this.hasStart = true;
			this.start[0] = x;
			this.start[1] = y;
		}
		this.level[x][y] = s;
	}

	public void printLevel() {
		for (int y = 0; y < this.ySize; y++) {
			for (int x = 0; x < this.xSize; x++)
				System.out.print(level[x][y]);
			System.out.println("");
		}
		System.out.println("");
	}

}
