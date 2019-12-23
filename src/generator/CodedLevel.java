package generator;

public class CodedLevel {

	private char[][] level;
	private int fitness;
	private final int xSize;
	private final int ySize;
	private boolean start=false;
	private boolean exit=false;
	
	public CodedLevel(final char[][] level, int xSize, int ySize) {
		this.level=level;
		this.xSize=xSize;
		this.ySize=ySize;
	}
	
	public void setFitness(final int fitness) {
		this.fitness=fitness;
	}
	public int getFitness() {
		return this.fitness;
	}
	
	public boolean hasStart() {
		return this.start;
	}
	
	public boolean hasExit() {
		return this.exit;
	}
	public int getXSize() {
		return this.xSize;
	}
	
	public int getYSize() {
		return this.ySize;
	}
	
	public char[][] getLevel(){
		return this.level;
	}
	public void changeField(int x, int y, char s) {
		if (this.level[x][y]==Constants.EXITREF) this.exit=false;
		else if (this.level[x][y]==Constants.STARTREF) this.start=false;
		if (s==Constants.EXITREF) this.exit=true;
		else if (s==Constants.STARTREF) this.start=true;
		this.level[x][y]=s;
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
