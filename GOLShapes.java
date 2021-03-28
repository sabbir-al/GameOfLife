/*Project Done BY:
 * Joel D'Souza 	b00079296
 * Sabbir Alam  	b00079438
 */

abstract class GOLShapes {
	int x;
	int y;
	boolean[][] gameMatrix;
	
	public abstract void createShape();
}

class Glider extends GOLShapes{
	
	public Glider(int x, int y, boolean[][] gameMatrix) {
		this.x = x;
		this.y = y;
		this.gameMatrix = gameMatrix;
		
		this.createShape();
	}
	
	@Override
	public void createShape() {
		
		gameMatrix[x][y] = true;
		gameMatrix[x+1][y+1] = true;
		gameMatrix[x+1][y+2] = true;
		gameMatrix[x][y+2] = true;
		gameMatrix[x-1][y+2] = true;
	}
}

class RPentomino extends GOLShapes {
	
	public RPentomino(int x, int y, boolean[][] gameMatrix) {
		this.x = x;
		this.y = y;
		this.gameMatrix = gameMatrix;
		
		this.createShape();
	}
	
	@Override
	public void createShape() {
		
		gameMatrix[x][y] = true;
		gameMatrix[x][y-1] = true;
		gameMatrix[x+1][y-1] = true;
		gameMatrix[x][y+1] = true;
		gameMatrix[x-1][y] = true;
	}
}

class Tumbler extends GOLShapes{
	
	public Tumbler(int x, int y, boolean[][] gameMatrix) {
		this.x = x;
		this.y = y;
		this.gameMatrix = gameMatrix;
		
		this.createShape();
	}
	
	@Override
	public void createShape() {
		
		gameMatrix[x][y] = true;
		gameMatrix[x+1][y-1] = true;
		gameMatrix[x+2][y-2] = true;
		gameMatrix[x+3][y-1] = true;
		gameMatrix[x+3][y] = true;
		gameMatrix[x+1][y+1] = true;
		gameMatrix[x+1][y+2] = true;
		gameMatrix[x][y+2] = true;
		
		x-=2;
		gameMatrix[x][y] = true;
		gameMatrix[x-1][y-1] = true;
		gameMatrix[x-2][y-2] = true;
		gameMatrix[x-3][y-1] = true;
		gameMatrix[x-3][y] = true;
		gameMatrix[x-1][y+1] = true;
		gameMatrix[x-1][y+2] = true;
		gameMatrix[x][y+2] = true;
		
	}
}

class SmallSpaceship extends GOLShapes {
	
	public SmallSpaceship(int x, int y, boolean[][] gameMatrix) {
		this.x = x;
		this.y = y;
		this.gameMatrix = gameMatrix;
		
		this.createShape();
	}
	
	@Override
	public void createShape() {
		
		gameMatrix[x-1][y-1] = true;
		gameMatrix[x-2][y] = true;
		gameMatrix[x-2][y+1] = true;
		gameMatrix[x-2][y+2] = true;
		gameMatrix[x-1][y+2] = true;
		gameMatrix[x][y+2] = true;
		gameMatrix[x+1][y+2] = true;
		gameMatrix[x+2][y+1] = true;
		gameMatrix[x+2][y-1] = true;

	}
}

class SmallExploder extends GOLShapes {
	
	public SmallExploder(int x, int y, boolean[][] gameMatrix) {
		this.x = x;
		this.y = y;
		this.gameMatrix = gameMatrix;
		
		this.createShape();
	}
	
	@Override
	public void createShape() {
		
		gameMatrix[x][y] = true;
		gameMatrix[x][y-1] = true;
		gameMatrix[x+1][y] = true;
		gameMatrix[x+1][y+1] = true;
		gameMatrix[x][y+2] = true;
		gameMatrix[x-1][y+1] = true;
		gameMatrix[x-1][y] = true;
	}
}