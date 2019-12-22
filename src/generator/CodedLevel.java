package generator;

public class CodedLevel {

	private char[][] level;
	private int fitness;
	private final int xSize;
	private final int ySize;
	
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
