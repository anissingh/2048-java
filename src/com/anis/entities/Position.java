package com.anis.entities;

public class Position {
	
	private static final int UNSET_POSITION = -1;
	private static final int UNSET_SIZE = -1;
	private int xPos;
	private int yPos;
	private int xSize;
	private int ySize;
	
	public Position() {
		this.xPos = UNSET_POSITION;
		this.yPos = UNSET_POSITION;
		this.xSize = UNSET_SIZE;
		this.ySize = UNSET_SIZE;
	}
	
	public Position(int xPos, int yPos, int xSize, int ySize) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
	}
	
	public int getXPos() {
		return this.xPos;
	}
	
	public int getYPos() {
		return this.yPos;
	}
	
	public int getXSize() {
		return this.xSize;
	}
	
	public int getYSize() {
		return this.ySize;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public void setXSize(int xSize) {
		this.xSize = xSize;
	}
	
	public void setYSize(int ySize) {
		this.ySize = ySize;
	}
	
}
