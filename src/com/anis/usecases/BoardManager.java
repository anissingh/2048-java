package com.anis.usecases;

import com.anis.entities.Tile;

public interface BoardManager extends TileSpawner, BoardMatrixManager, BoardManipulator {
	
	void initializeBoard();
	boolean canMove();
	Tile[][] getBoard();
	
}
