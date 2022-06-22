package com.anis.entities;

public class Tile {

	private int value;
	private int prevX;
	private int prevY;
	private boolean wasMerged;
	private boolean isExpanding;
	private boolean isCompressing;
	
	public Tile(int value, int prevX, int prevY) {
		this.value = value;
		this.prevX = prevX;
		this.prevY = prevY;
		this.wasMerged = false;
		this.isExpanding = false;
		this.isCompressing = false;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getPrevX() {
		return this.prevX;
	}
	
	public void setPrevX(int prevX) {
		this.prevX = prevX;
	}
	
	public int getPrevY() {
		return this.prevY;
	}
	
	public void setPrevY(int prevY) {
		this.prevY = prevY;
	}
	
	public boolean getWasMerged() {
		return this.wasMerged;
	}
	
	public void setWasMerged(boolean wasMerged) {
		this.wasMerged = wasMerged;
	}
	
	public boolean getIsExpanding() {
		return this.isExpanding;
	}
	
	public void setIsExpanding(boolean isExpanding) {
		this.isExpanding = isExpanding;
	}
	
	public boolean getIsCompressing() {
		return this.isCompressing;
	}
	
	public void setIsCompressing(boolean isCompressing) {
		this.isCompressing = isCompressing;
	}
	
	public String toString() {
		return Integer.toString(this.value);
	}
}
