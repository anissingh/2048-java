package com.anis.usecases;

public interface BoardTileMatrix extends BoardMatrixManager {
	
	public int getTileValue(int rowIndex, int colIndex);

	public int getTilePrevX(int rowIndex, int colIndex);

	public int getTilePrevY(int rowIndex, int colIndex);
	
	public boolean getTileWasMerged(int rowIndex, int colIndex);
	
	public boolean getTileIsExpanding(int rowIndex, int colIndex);
	
	public boolean getTileIsCompressing(int rowIndex, int colIndex);
	
	public boolean isGameOver();

	public void setTileValue(int value, int rowIndex, int colIndex);

	public void setTilePrevX(int prevX, int rowIndex, int colIndex);

	public void setTilePrevY(int prevY, int rowIndex, int colIndex);
	
	public void setTileWasMerged(boolean wasMerged, int rowIndex, int colIndex);
	
	public void setTileIsExpanding(boolean isExpanding, int rowIndex, int colIndex);
	
	public void setTileIsCompressing(boolean isCompressing, int rowIndex, int colIndex);
	
}
