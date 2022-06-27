package com.anis.ia;

import com.anis.entities.Position;

public class PositionManager {
	
	private Position[][] positions;
	
	public PositionManager(int rows, int cols) {
		positions = new Position[rows][cols];
	}
	
	public void updatePosition(char type, int pos, int rowIndex, int colIndex) {
		if(type == 'x') {
			positions[rowIndex][colIndex].setXPos(pos);
		} else {
			positions[rowIndex][colIndex].setYPos(pos);
		}
	}
	
	public int getPosition(char type, int rowIndex, int colIndex) {
		if(type == 'x') {
			return positions[rowIndex][colIndex].getXPos();
		} else {
			return positions[rowIndex][colIndex].getYPos();
		}
	}
	
	public int getSize(char type, int rowIndex, int colIndex) {
		if(type == 'x') {
			return positions[rowIndex][colIndex].getXSize();
		} else {
			return positions[rowIndex][colIndex].getYSize();
		}
	}
	
	public void updateSize(int xSize, int ySize, int rowIndex, int colIndex) {
		positions[rowIndex][colIndex].setXSize(xSize);
		positions[rowIndex][colIndex].setYSize(ySize);
	}
	
	public void initializePosition(int xPos, int yPos, int xSize, int ySize, int rowIndex, int colIndex) {
		positions[rowIndex][colIndex] = new Position(xPos, yPos, xSize, ySize);
	}
	
}
