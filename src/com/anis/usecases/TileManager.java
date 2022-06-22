package com.anis.usecases;

import com.anis.entities.Tile;

public class TileManager {
	
	public static Tile createTile(int value, int prevX, int prevY) {
		return new Tile(value, prevX, prevY);
	}
	
	public static int getTileValue(Tile tile) {
		return tile.getValue();
	}
	
	public static int getTilePrevX(Tile tile) {
		return tile.getPrevX();
	}
	
	public static int getTilePrevY(Tile tile) {
		return tile.getPrevY();
	}
	
	public static boolean getTileWasMerged(Tile tile) {
		return tile.getWasMerged();
	}
	
	public static boolean getTileIsExpanding(Tile tile) {
		return tile.getIsExpanding();
	}
	
	public static boolean getTileIsCompressing(Tile tile) {
		return tile.getIsCompressing();
	}
	
	public static void setTileValue(Tile tile, int value) {
		tile.setValue(value);
	}
	
	public static void setTilePrevX(Tile tile, int prevX) {
		tile.setPrevX(prevX);
	}
	
	public static void setTilePrevY(Tile tile, int prevY) {
		tile.setPrevY(prevY);
	}
	
	public static void setTileWasMerged(Tile tile, boolean wasMerged) {
		tile.setWasMerged(wasMerged);
	}
	
	public static void setTileIsExpanding(Tile tile, boolean isExpanding) {
		tile.setIsExpanding(isExpanding);
	}
	
	public static void setTileIsCompressing(Tile tile, boolean isCompressing) {
		tile.setIsCompressing(isCompressing);
	}
	
}
