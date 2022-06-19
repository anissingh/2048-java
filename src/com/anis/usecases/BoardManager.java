package com.anis.usecases;

import com.anis.entities.Tile;
import java.util.Random;

public class BoardManager {
	
	public static final int ROWS = 4;
	public static final int COLS = 4;
	private Tile[][] board;
	private Random rand;
	
	// TODO: Add functionality to load in custom board
	public BoardManager() {
		board = new Tile[ROWS][COLS];
		rand = new Random();
	}
	
	// Initializes the board at the start of the game
	public void initializeBoard() {
		// Iterate over board array and create new tiles with the value 0, which means no tile is present
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				// j is the column # (x), i is the row # (y)
				board[i][j] = TileManager.createTile(0, j, i);
			}
		}
		
		// Spawn two tiles
		spawnTile();
		spawnTile();
	}
	
	// Spawns a tile and returns true if a tile was spawned. Returns false if a tile was not spawned because
	// the board was full
	public boolean spawnTile() {
		int tileY = rand.nextInt(ROWS);
		int tileX = rand.nextInt(COLS);
		
		int tileVal = rand.nextInt(3);
		// Slightly lower chance to spawn a 4 than a 2
		if(tileVal == 0) {
			// Set Tile value in board position
			tileVal = 4;
		} else {
			tileVal = 2;
		}
	
		// TODO: Try spawning a tile 3 times before auto-searching
		// Check if there is no tile at this location
		if(TileManager.getTileValue(board[tileY][tileX]) == 0) {
			TileManager.setTileValue(board[tileY][tileX], tileVal);
			// TODO: For debugging
			System.out.println("Tile spawned at " + tileX + ", " + tileY);
			return true;
		} else {
			boolean isTileSpawnable = false;
			// Search for the first empty spot on the board
			for(int i = 0; i < ROWS; i++) {
				for(int j = 0; j < COLS; j++) {
					// Check if current spot on board has no Tile
					if(TileManager.getTileValue(board[i][j]) == 0) {
						tileY = i;
						tileX = j;
						isTileSpawnable = true;
						break;
					}
				}
			}
			if(isTileSpawnable) {
				TileManager.setTileValue(board[tileY][tileX], tileVal);
				// TODO: For debugging
				System.out.println("Tile spawned at " + tileX + ", " + tileY);
				return true;
			}
			
			return false;
		}
		
	}
	
	// Returns a 2D integer array representing the value of the tiles on the board
	public int[][] getBoardValues() {
		int[][] boardValues = new int[ROWS][COLS];
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				boardValues[i][j] = TileManager.getTileValue(board[i][j]);
			}
		}
		return boardValues;
	}
	
	// Move all tiles left
	public boolean moveLeft() {
		boolean tileMoved = false;
		for(int i = 0; i < ROWS; i++) {
			// Merge all appropriate tiles
			boolean tileSet = false;
			int currIndex = 0;
			// TODO: Move to helper?
			for(int j = 0; j < COLS; j++) {
				int tileBeingAnalyzedVal = TileManager.getTileValue(board[i][currIndex]);
				int currTileVal = TileManager.getTileValue(board[i][j]);
				
				// Reset the prevX and prevY of the tile from last movement
				TileManager.setTilePrevX(board[i][j], j);
				TileManager.setTilePrevY(board[i][j], i);
				
				// If we aren't analyzing a tile yet and we find our first non-zero tile, analyze that tile
				if(!tileSet && currTileVal != 0) {
					tileSet = true;
					currIndex = j;
				} else if(tileSet && tileBeingAnalyzedVal == currTileVal) {
					// If we are analyzing a tile and it has the same value as the next non-zero tile in the row,
					// merge them
					// Note tileSet being true ensures board[i][currIndex] value != 0 and therefore board[i][j].getValue() != 0
					// TODO: Use TileManager here
					board[i][currIndex].setValue(tileBeingAnalyzedVal * 2);
					// TODO: Do animations for merging. For now they are disabled by the following 2 lines of code
					TileManager.setTilePrevX(board[i][currIndex], currIndex);
					TileManager.setTilePrevY(board[i][currIndex], i);
					// This is the tile that was merged, so set its wasMerged attribute to true
					TileManager.setTileWasMerged(board[i][currIndex], true);
					// Also set the tile to be expanding
					TileManager.setTileIsExpanding(board[i][currIndex], true);
					board[i][j].setValue(0);
					// TODO: Do animations for merging. For now they are disabled by the following 2 lines of code
					TileManager.setTilePrevX(board[i][j], j);
					TileManager.setTilePrevY(board[i][j], i);
					tileSet = false;
					tileMoved = true;
				} else if(tileSet && tileBeingAnalyzedVal != currTileVal && currTileVal != 0) {
					// If we are analyzing a tile and the next non-zero tile in the row does not have the same value, then
					// stop analyzing this tile because it can't be merged and start analyzing the next one
					// Note tileSet is already true so there is no need to set it here
					currIndex = j;
				}
			}
			// Move all tiles to leftmost spot
			currIndex = 0;
			for(int j = 0; j < COLS; j++) {
				// Check if board[i][j] tile value is non-zero
				// If it is, set the leftmost free index to the tile at board[i][j]
				// If the leftmost free index is not j, replace tile at board[i][j] with an empty tile because
				// it was moved to another location
				int currTileVal = TileManager.getTileValue(board[i][j]);
				if(currTileVal != 0 && currIndex != j) {
					TileManager.setTileValue(board[i][currIndex], currTileVal);
					TileManager.setTilePrevX(board[i][currIndex], j);
					TileManager.setTilePrevY(board[i][currIndex], i);
					// board[i][currIndex] = board[i][j];
					TileManager.setTileValue(board[i][j], 0);
					// Don't want to animate an empty tile sliding
					TileManager.setTilePrevX(board[i][j], j);
					TileManager.setTilePrevY(board[i][j], i);
					currIndex++;
					tileMoved = true;
				} else if(currTileVal != 0) {
					// currIndex == j here
					currIndex++;
				}
			}
		}
		
		// Return whether or not the tile moved
		return tileMoved;
	}
	
	// Move all tiles right
	public boolean moveRight() {
		boolean tileMoved = false;
		for(int i = 0; i < ROWS; i++) {
			// Merge all appropriate tiles
			boolean tileSet = false;
			int currIndex = 0;
			for(int j = COLS - 1; j >= 0; j--) {
				int tileBeingAnalyzedVal = TileManager.getTileValue(board[i][currIndex]);
				int currTileVal = TileManager.getTileValue(board[i][j]);
				TileManager.setTilePrevX(board[i][j], j);
				TileManager.setTilePrevY(board[i][j], i);
				
				if(!tileSet && currTileVal != 0) {
					tileSet = true;
					currIndex = j;
				} else if(tileSet && tileBeingAnalyzedVal == currTileVal) {
					// TODO: Use TileManager here
					board[i][currIndex].setValue(tileBeingAnalyzedVal * 2);
					// TODO: Do animations for merging. For now they are disabled by the following 2 lines of code
					TileManager.setTilePrevX(board[i][currIndex], currIndex);
					TileManager.setTilePrevY(board[i][currIndex], i);

					TileManager.setTileWasMerged(board[i][currIndex], true);
					TileManager.setTileIsExpanding(board[i][currIndex], true);
					
					board[i][j].setValue(0);
					// TODO: Do animations for merging. For now they are disabled by the following 2 lines of code
					TileManager.setTilePrevX(board[i][j], j);
					TileManager.setTilePrevY(board[i][j], i);
					tileSet = false;
					tileMoved = true;
				} else if(tileSet && tileBeingAnalyzedVal != currTileVal && currTileVal != 0) {
					currIndex = j;
				}
			}
			// Move all tiles to rightmost spot
			currIndex = COLS - 1;
			for(int j = COLS - 1; j >= 0; j--) {
				int currTileVal = TileManager.getTileValue(board[i][j]);
				if(currTileVal != 0 && currIndex != j) {
					TileManager.setTileValue(board[i][currIndex], currTileVal);
					TileManager.setTilePrevX(board[i][currIndex], j);
					TileManager.setTilePrevY(board[i][currIndex], i);
					TileManager.setTileValue(board[i][j], 0);
					// Don't want to animate an empty tile sliding
					TileManager.setTilePrevX(board[i][j], j);
					TileManager.setTilePrevY(board[i][j], i);
					currIndex--;
					tileMoved = true;
				} else if(currTileVal != 0) {
					currIndex--;
				}
			}
		}
		
		return tileMoved;
	}
	
	// Move all tiles up
	public boolean moveUp() {
		boolean tileMoved = false;
		for(int j = 0; j < COLS; j++) {
			// Merge all appropriate tiles
			boolean tileSet = false;
			int currIndex = 0;
			for(int i = 0; i < ROWS; i++) {
				int tileBeingAnalyzedVal = TileManager.getTileValue(board[currIndex][j]);
				int currTileVal = TileManager.getTileValue(board[i][j]);
				TileManager.setTilePrevX(board[i][j], j);
				TileManager.setTilePrevY(board[i][j], i);
				
				if(!tileSet && currTileVal != 0) {
					tileSet = true;
					currIndex = i;
				} else if(tileSet && tileBeingAnalyzedVal == currTileVal) {
					// TODO: Use TileManager here
					board[currIndex][j].setValue(tileBeingAnalyzedVal * 2);
					// TODO: Do animations for merging. For now they are disabled by the following 2 lines of code
					TileManager.setTilePrevX(board[currIndex][j], j);
					TileManager.setTilePrevY(board[currIndex][j], currIndex);

					TileManager.setTileWasMerged(board[currIndex][j], true);
					TileManager.setTileIsExpanding(board[currIndex][j], true);
					
					board[i][j].setValue(0);
					// TODO: Do animations for merging. For now they are disabled by the following 2 lines of code
					TileManager.setTilePrevX(board[i][j], j);
					TileManager.setTilePrevY(board[i][j], i);
					tileSet = false;
					tileMoved = true;
				} else if(tileSet && tileBeingAnalyzedVal != currTileVal && currTileVal != 0) {
					currIndex = i;
				}
			}
			
			// Move all tiles to leftmost spot
			currIndex = 0;
			for(int i = 0; i < ROWS; i++) {
				int currTileVal = TileManager.getTileValue(board[i][j]);
				if(currTileVal != 0 && currIndex != i) {
					TileManager.setTileValue(board[currIndex][j], currTileVal);
					TileManager.setTilePrevX(board[currIndex][j], j);
					TileManager.setTilePrevY(board[currIndex][j], i);
					TileManager.setTileValue(board[i][j], 0);
					// Don't want to animate an empty tile sliding
					TileManager.setTilePrevX(board[i][j], j);
					TileManager.setTilePrevY(board[i][j], i);
					currIndex++;
					tileMoved = true;
				} else if(currTileVal != 0) {
					currIndex++;
				}
			}
		}
		
		// Return whether or not a tile moved
		return tileMoved;
	}
	
	// Move all tiles down
	public boolean moveDown() {
		boolean tileMoved = false;
		for(int j = 0; j < COLS; j++) {
			// Merge all appropriate tiles
			boolean tileSet = false;
			int currIndex = 0;
			for(int i = ROWS - 1; i >= 0; i--) {
				int tileBeingAnalyzedVal = TileManager.getTileValue(board[currIndex][j]);
				int currTileVal = TileManager.getTileValue(board[i][j]);
				TileManager.setTilePrevX(board[i][j], j);
				TileManager.setTilePrevY(board[i][j], i);
				
				if(!tileSet && currTileVal != 0) {
					tileSet = true;
					currIndex = i;
				} else if(tileSet && tileBeingAnalyzedVal == currTileVal) {
					// TODO: Use TileManager here
					board[currIndex][j].setValue(tileBeingAnalyzedVal * 2);
					// TODO: Do animations for merging. For now they are disabled by the following 2 lines of code
					TileManager.setTilePrevX(board[currIndex][j], j);
					TileManager.setTilePrevY(board[currIndex][j], currIndex);

					TileManager.setTileWasMerged(board[currIndex][j], true);
					TileManager.setTileIsExpanding(board[currIndex][j], true);
					
					board[i][j].setValue(0);
					// TODO: Do animations for merging. For now they are disabled by the following 2 lines of code
					TileManager.setTilePrevX(board[i][j], j);
					TileManager.setTilePrevY(board[i][j], i);
					tileSet = false;
					tileMoved = true;
				} else if(tileSet && tileBeingAnalyzedVal != currTileVal && currTileVal != 0) {
					currIndex = i;
				}
			}
			// Move all tiles to rightmost spot
			currIndex = COLS - 1;
			for(int i = COLS - 1; i >= 0; i--) {
				int currTileVal = TileManager.getTileValue(board[i][j]);
				if(currTileVal != 0 && currIndex != i) {
					TileManager.setTileValue(board[currIndex][j], currTileVal);
					TileManager.setTilePrevX(board[currIndex][j], j);
					TileManager.setTilePrevY(board[currIndex][j], i);
					TileManager.setTileValue(board[i][j], 0);
					// Don't want to animate an empty tile sliding
					TileManager.setTilePrevX(board[i][j], j);
					TileManager.setTilePrevY(board[i][j], i);
					currIndex--;
					tileMoved = true;
				} else if(currTileVal != 0) {
					currIndex--;
				}
			}
		}
		
		// Return whether or not a tile moved
		return tileMoved;
	}
	
	// TODO: Remove when done making GameContent not interact with tile objects themselves
	public Tile[][] getBoard() {
		return this.board;
	}
	
	// Prints a text-representation of the board
	public void printBoard() {
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				System.out.print(board[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
}
