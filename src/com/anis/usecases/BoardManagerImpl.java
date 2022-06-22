package com.anis.usecases;

import java.util.Random;

import com.anis.entities.Tile;
import com.anis.enums.Direction;

public class BoardManagerImpl implements BoardManager {

	private Tile[][] board;
	private Random rand;
	private final ScoreIncrementer scoreIncrementer;
	private boolean canMove = true;
	
	// TODO: Add functionality to load in custom board
	public BoardManagerImpl(ScoreIncrementer scoreIncrementer) {
		board = new Tile[ROWS][COLS];
		rand = new Random();
		this.scoreIncrementer = scoreIncrementer;
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
			setGameOver();
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
				setGameOver();
				return true;
			}
			
			System.out.println("No tile spawned.");
			// If no tile was spawned, the game must be over
			this.canMove = false;
			return false;
		}
		
	}
	
	// Move all tiles in the specified direction
	public boolean move(Direction direction) {
		boolean tileMoved = false;
		if(direction == Direction.LEFT || direction == Direction.RIGHT) {
			for(int i = 0; i < ROWS; i++) {
				// Merge all appropriate tiles
				tileMoved = handleLateralMerge(i, direction, tileMoved);
				// Move all tiles to leftmost spot
				tileMoved = handleLateralShifting(i, direction, tileMoved);
			}
		} else {
			// direction is Direction.UP or direction == Direction.DOWN
			for(int j = 0; j < COLS; j++) {
				// Merge all appropriate tiles
				tileMoved = handleVerticalMerging(j, direction, tileMoved);
				// Move all tiles to up-most spot
				tileMoved = handleVerticalShifting(j, direction, tileMoved);
			}
		}
		
		return tileMoved;
	}
	
	// Precondition: direction is either Direction.LEFT or Direction.RIGHT
	private boolean handleLateralMerge(int i, Direction direction, boolean tileMoved) {
		boolean tileSet = false;
		// currIndex = {0 if LEFT, COLS - 1 if RIGHT}
		int currIndex = direction == Direction.LEFT ? 0 : COLS - 1;
		// j = {0 if LEFT, COLS - 1 if RIGHT}
		int j = direction == Direction.LEFT ? 0 : COLS - 1;
		// update = {1 if LEFT, -1 if RIGHT}
		int update = direction == Direction.LEFT ? 1 : -1;
		
		for(int unused = 0; unused < COLS; unused++) {
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
				// Give the player score for merging tiles
				scoreIncrementer.incrementScore(tileBeingAnalyzedVal * 2);
			} else if(tileSet && tileBeingAnalyzedVal != currTileVal && currTileVal != 0) {
				// If we are analyzing a tile and the next non-zero tile in the row does not have the same value, then
				// stop analyzing this tile because it can't be merged and start analyzing the next one
				// Note tileSet is already true so there is no need to set it here
				currIndex = j;
			}
			// Update j
			j += update;
		}
		return tileMoved;
	}
	
	// Precondition: direction is either Direction.UP or Direction.DOWN
	private boolean handleVerticalMerging(int j, Direction direction, boolean tileMoved) {
		boolean tileSet = false;
		// currIndex = {0 if UP, ROWS - 1 if DOWN}
		int currIndex = direction == Direction.UP ? 0 : ROWS - 1;
		// i = {0 if UP, ROWS - 1 if DOWN}
		int i = direction == Direction.UP ? 0 : ROWS - 1;
		// update = {1 if UP, -1 if DOWN}
		int update = direction == Direction.UP ? 1 : -1;
		
		for(int unused = 0; unused < ROWS; unused++) {
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
				
				scoreIncrementer.incrementScore(tileBeingAnalyzedVal * 2);
			} else if(tileSet && tileBeingAnalyzedVal != currTileVal && currTileVal != 0) {
				currIndex = i;
			}
			// Update i
			i += update;
		}
		
		return tileMoved;
	}
	
	// Precondition: direction is either Direction.LEFT or Direction.RIGHT
	// Comments describe shifting left, but shifting right follows analogous logic
	private boolean handleLateralShifting(int i, Direction direction, boolean tileMoved) {
		// currIndex = {0 if LEFT, COLS - 1 if RIGHT}
		int currIndex = direction == Direction.LEFT ? 0 : COLS - 1;
		// j = {0 if LEFT, COLS - 1 if RIGHT}
		int j = direction == Direction.LEFT ? 0 : COLS - 1;
		// update = {1 if LEFT, -1 if RIGHT}
		int update = direction == Direction.LEFT ? 1 : -1;
		
		for(int unused = 0; unused < COLS; unused++) {
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
				currIndex += update;
				tileMoved = true;
			} else if(currTileVal != 0) {
				// currIndex == j here
				currIndex += update;
			}
			// Update j
			j += update;
		}
		
		return tileMoved;
	}
	
	// Precondition: direction is either Direction.UP or Direction.DOWN
	private boolean handleVerticalShifting(int j, Direction direction, boolean tileMoved) {
		// currIndex = {0 if UP, ROWS - 1 if DOWN}
		int currIndex = direction == Direction.UP ? 0 : ROWS - 1;
		// i = {0 if UP, ROWS - 1 if DOWN}
		int i = direction == Direction.UP ? 0 : ROWS - 1;
		// update = {1 if UP, -1 if DOWN}
		int update = direction == Direction.UP ? 1 : -1;
		
		for(int unused = 0; unused < ROWS; unused++) {
			int currTileVal = TileManager.getTileValue(board[i][j]);
			if(currTileVal != 0 && currIndex != i) {
				TileManager.setTileValue(board[currIndex][j], currTileVal);
				TileManager.setTilePrevX(board[currIndex][j], j);
				TileManager.setTilePrevY(board[currIndex][j], i);
				TileManager.setTileValue(board[i][j], 0);
				// Don't want to animate an empty tile sliding
				TileManager.setTilePrevX(board[i][j], j);
				TileManager.setTilePrevY(board[i][j], i);
				currIndex += update;
				tileMoved = true;
			} else if(currTileVal != 0) {
				currIndex += update;
			}
			// Update i
			i += update;
		}
		
		return tileMoved;
	}
	
	private void setGameOver() {
		// Iterate over the game board and check if surrounding tile values are equal to the current tile
		// or there is an empty spot right next to the tile
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				int currTileVal = TileManager.getTileValue(board[i][j]);
				// Check if we are at the last row
				if(i != ROWS - 1) {
					// If not, check row below
					if(checkSurroundingTileForGameOver(currTileVal, TileManager.getTileValue(board[i + 1][j]))) {
						this.canMove = true;
						return;
					}
				}
				// Check if we are at the last column
				if(j != COLS - 1) {
					// If not, check column to the right
					if(checkSurroundingTileForGameOver(currTileVal, TileManager.getTileValue(board[i][j + 1]))) {
						this.canMove = true;
						return;
					}
				}
				// Check if we are at the first row
				if(i != 0) {
					// If not, check row above
					if(checkSurroundingTileForGameOver(currTileVal, TileManager.getTileValue(board[i - 1][j]))) {
						this.canMove = true;
						return;
					}
				}
				// Check if we are at the first column
				if(j != 0) {
					// If not, check column to the left
					if(checkSurroundingTileForGameOver(currTileVal, TileManager.getTileValue(board[i][j - 1]))) {
						this.canMove = true;
						return;
					}
				}
			}
		}
		// If we make it here, the method never set canMove to true, so the player cannot move any tiles
		this.canMove = false;
	}
	
	private boolean checkSurroundingTileForGameOver(int currTileVal, int tileValToCompare) {
		return tileValToCompare == currTileVal || tileValToCompare == 0;
	}
	
	public Tile[][] getBoard() {
		return this.board;
	}
	
	public boolean canMove() {
		return this.canMove;
	}
	
	// TODO: For debugging
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
