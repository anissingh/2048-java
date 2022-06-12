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
				board[i][j] = new Tile(0);
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
		if(board[tileY][tileX].getValue() == 0) {
			board[tileY][tileX] = new Tile(tileVal);
			// TODO: For debugging
			System.out.println("Tile spawned at " + tileX + ", " + tileY);
			return true;
		} else {
			boolean isTileSpawnable = false;
			// Search for the first empty spot on the board
			for(int i = 0; i < ROWS; i++) {
				for(int j = 0; j < COLS; j++) {
					// Check if current spot on board has no Tile
					if(board[i][j].getValue() == 0) {
						tileY = i;
						tileX = j;
						isTileSpawnable = true;
						break;
					}
				}
			}
			if(isTileSpawnable) {
				board[tileY][tileX] = new Tile(tileVal);
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
				boardValues[i][j] = board[i][j].getValue();
			}
		}
		return boardValues;
	}
	
	// Move all tiles left
	// O(nm), n = ROWS, m = COLS
	public void moveLeft() {
		boolean tileMoved = false;
		for(int i = 0; i < ROWS; i++) {
			// Merge all appropriate tiles
			boolean tileSet = false;
			int currIndex = 0;
			// TODO: Move to helper?
			for(int j = 0; j < COLS; j++) {
				// If we aren't analyzing a tile yet and we find our first non-zero tile, analyze that tile
				if(!tileSet && board[i][j].getValue() != 0) {
					tileSet = true;
					currIndex = j;
				} else if(tileSet && board[i][currIndex].getValue() == board[i][j].getValue()) {
					// If we are analyzing a tile and it has the same value as the next non-zero tile in the row,
					// merge them
					// Note tileSet being true ensures board[i][currIndex] value != 0 and therefore board[i][j].getValue() != 0
					board[i][currIndex] = new Tile(board[i][currIndex].getValue() * 2);
					board[i][j] = new Tile(0);
					tileSet = false;
					tileMoved = true;
				} else if(tileSet && board[i][currIndex].getValue() != board[i][j].getValue() && board[i][j].getValue() != 0) {
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
				if(board[i][j].getValue() != 0 && currIndex != j) {
					board[i][currIndex] = board[i][j];
					board[i][j] = new Tile(0);
					currIndex++;
					tileMoved = true;
				} else if(board[i][j].getValue() != 0) {
					currIndex++;
				}
			}
		}
		
		// Spawn tile after movement is done if movement happened
		if(tileMoved) {
			spawnTile();
		}
	}
	
	// Move all tiles right
	public void moveRight() {
		boolean tileMoved = false;
		for(int i = 0; i < ROWS; i++) {
			// Merge all appropriate tiles
			boolean tileSet = false;
			int currIndex = 0;
			for(int j = COLS - 1; j >= 0; j--) {
				if(!tileSet && board[i][j].getValue() != 0) {
					tileSet = true;
					currIndex = j;
				} else if(tileSet && board[i][currIndex].getValue() == board[i][j].getValue()) {
					board[i][currIndex] = new Tile(board[i][currIndex].getValue() * 2);
					board[i][j] = new Tile(0);
					tileSet = false;
					tileMoved = true;
				} else if(tileSet && board[i][currIndex].getValue() != board[i][j].getValue() && board[i][j].getValue() != 0) {
					currIndex = j;
				}
			}
			// Move all tiles to rightmost spot
			currIndex = COLS - 1;
			for(int j = COLS - 1; j >= 0; j--) {
				if(board[i][j].getValue() != 0 && currIndex != j) {
					board[i][currIndex] = board[i][j];
					board[i][j] = new Tile(0);
					currIndex--;
					tileMoved = true;
				} else if(board[i][j].getValue() != 0) {
					currIndex--;
				}
			}
		}
		
		// Spawn tile after movement is done if movement happened
		if(tileMoved) {
			spawnTile();
		}
	}
	
	// Move all tiles up
	public void moveUp() {
		boolean tileMoved = false;
		for(int j = 0; j < COLS; j++) {
			// Merge all appropriate tiles
			boolean tileSet = false;
			int currIndex = 0;
			for(int i = 0; i < ROWS; i++) {
				if(!tileSet && board[i][j].getValue() != 0) {
					tileSet = true;
					currIndex = i;
				} else if(tileSet && board[currIndex][j].getValue() == board[i][j].getValue()) {
					board[currIndex][j] = new Tile(board[currIndex][j].getValue() * 2);
					board[i][j] = new Tile(0);
					tileSet = false;
					tileMoved = true;
				} else if(tileSet && board[currIndex][j].getValue() != board[i][j].getValue() && board[i][j].getValue() != 0) {
					currIndex = i;
				}
			}
			
			// Move all tiles to leftmost spot
			currIndex = 0;
			for(int i = 0; i < ROWS; i++) {
				if(board[i][j].getValue() != 0 && currIndex != i) {
					board[currIndex][j] = board[i][j];
					board[i][j] = new Tile(0);
					currIndex++;
					tileMoved = true;
				} else if(board[i][j].getValue() != 0) {
					currIndex++;
				}
			}
		}
		
		// Spawn tile after movement is done if movement happened
		if(tileMoved) {
			spawnTile();
		}
	}
	
	// Move all tiles down
	public void moveDown() {
		boolean tileMoved = false;
		for(int j = 0; j < COLS; j++) {
			// Merge all appropriate tiles
			boolean tileSet = false;
			int currIndex = 0;
			for(int i = ROWS - 1; i >= 0; i--) {
				if(!tileSet && board[i][j].getValue() != 0) {
					tileSet = true;
					currIndex = i;
				} else if(tileSet && board[currIndex][j].getValue() == board[i][j].getValue()) {
					board[currIndex][j] = new Tile(board[currIndex][j].getValue() * 2);
					board[i][j] = new Tile(0);
					tileSet = false;
					tileMoved = true;
				} else if(tileSet && board[currIndex][j].getValue() != board[i][j].getValue() && board[i][j].getValue() != 0) {
					currIndex = i;
				}
			}
			// Move all tiles to rightmost spot
			currIndex = COLS - 1;
			for(int i = COLS - 1; i >= 0; i--) {
				if(board[i][j].getValue() != 0 && currIndex != i) {
					board[currIndex][j] = board[i][j];
					board[i][j] = new Tile(0);
					currIndex--;
					tileMoved = true;
				} else if(board[i][j].getValue() != 0) {
					currIndex--;
				}
			}
		}
		
		// Spawn tile after movement is done if movement happened
		if(tileMoved) {
			spawnTile();
		}
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
