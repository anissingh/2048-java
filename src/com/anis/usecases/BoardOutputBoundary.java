package com.anis.usecases;


public class BoardOutputBoundary {
	
	private BoardManager boardManager;
	
	public BoardOutputBoundary(BoardManager boardManager) {
		this.boardManager = boardManager;
	}

	public int getTileValue(int rowIndex, int colIndex) {
		return boardManager.getBoard()[rowIndex][colIndex].getValue();
	}

	public int getTilePrevX(int rowIndex, int colIndex) {
		return boardManager.getBoard()[rowIndex][colIndex].getPrevX();
	}

	public int getTilePrevY(int rowIndex, int colIndex) {
		return boardManager.getBoard()[rowIndex][colIndex].getPrevY();
	}
	
	public boolean getTileWasMerged(int rowIndex, int colIndex) {
		return boardManager.getBoard()[rowIndex][colIndex].getWasMerged();
	}
	
	public boolean getTileIsExpanding(int rowIndex, int colIndex) {
		return boardManager.getBoard()[rowIndex][colIndex].getIsExpanding();
	}
	
	public boolean getTileIsCompressing(int rowIndex, int colIndex) {
		return boardManager.getBoard()[rowIndex][colIndex].getIsCompressing();
	}
	
	public boolean isGameOver() {
		return !boardManager.canMove();
	}

	public void setTileValue(int value, int rowIndex, int colIndex) {
		boardManager.getBoard()[rowIndex][colIndex].setValue(value);
	}

	public void setTilePrevX(int prevX, int rowIndex, int colIndex) {
		boardManager.getBoard()[rowIndex][colIndex].setPrevX(prevX);
	}

	public void setTilePrevY(int prevY, int rowIndex, int colIndex) {
		boardManager.getBoard()[rowIndex][colIndex].setPrevY(prevY);
	}
	
	public void setTileWasMerged(boolean wasMerged, int rowIndex, int colIndex) {
		boardManager.getBoard()[rowIndex][colIndex].setWasMerged(wasMerged);
	}
	
	public void setTileIsExpanding(boolean isExpanding, int rowIndex, int colIndex) {
		boardManager.getBoard()[rowIndex][colIndex].setIsExpanding(isExpanding);
	}
	
	public void setTileIsCompressing(boolean isCompressing, int rowIndex, int colIndex) {
		boardManager.getBoard()[rowIndex][colIndex].setIsCompressing(isCompressing);
	}

}
