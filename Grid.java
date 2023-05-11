import java.util.Random;

public class Grid {
	private boolean[][] bombGrid;
    private int[][] countGrid;
    private int numRows;
    private int numColumns;
    private int numBombs;
    
    public Grid() {
        numRows = 10;
        numColumns = 10;
        numBombs = 25;
        createBombGrid();
        createCountGrid();
    }
    
    public Grid(int rows, int columns) {
    	numRows = rows;
    	numColumns = columns;
    	numBombs = 25;
    	createBombGrid();
    	createCountGrid();
    }
    
    public Grid(int rows, int columns, int bombs) {
    	numRows = rows;
    	numColumns = columns;
    	numBombs = bombs;
    	createBombGrid();
    	createCountGrid();
    }
    
    public int getNumRows() {
        return numRows;
    }
    
    public int getNumColumns() {
        return numColumns;
    }
    
    public int getNumBombs() {
        return numBombs;
    }
    
    public boolean[][] getBombGrid() {
        boolean[][] copy = new boolean[numRows][numColumns];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                copy[row][col] = bombGrid[row][col];
            }
        }
        return copy;
    }
    
    public int[][] getCountGrid() {
        int[][] copy = new int[numRows][numColumns];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                copy[row][col] = countGrid[row][col];
            }
        }
        return copy;
    }
    
    public boolean isBombAtLocation(int row, int col) {
        return bombGrid[row][col];
    }
    
    public int getCountAtLocation(int row, int col) {
        return countGrid[row][col];
    }
    
    private void createBombGrid() {
        bombGrid = new boolean[numRows][numColumns];
        Random random = new Random();
        int bombsPlaced = 0;
        while (bombsPlaced < numBombs) {
            int row = random.nextInt(numRows);
            int col = random.nextInt(numColumns);
            if (!bombGrid[row][col]) {
                bombGrid[row][col] = true;
                bombsPlaced++;
            }
        }
    }
    
    private void createCountGrid() {
        countGrid = new int[numRows][numColumns];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                int count = 0;
                for (int r = row - 1; r <= row + 1; r++) {
                    for (int c = col - 1; c <= col + 1; c++) {
                        if (r >= 0 && r < numRows && c >= 0 && c < numColumns && bombGrid[r][c]) {
                            count++;
                            if (bombGrid[row][col]) {
                                countGrid[row][col] = 1+count;
                                continue;
                            }
                        }
                    }
                }
                countGrid[row][col] = count;
            }
        }
    }
}
