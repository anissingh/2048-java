package com.anis.entities;

public class Tile {

	private int value;
	
	public Tile(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	
	public String toString() {
		return Integer.toString(this.value);
	}
}
