package com.anis.ia;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.anis.usecases.BoardManager;

public class GameContent extends JPanel {

	private static final long serialVersionUID = 188110181513518238L;
	private static final int PADDING_X = 16;
	private static final int PADDING_Y = 16;
	private static final int TILE_SIZE_X = 100;
	private static final int TILE_SIZE_Y = 100;
	private static final int TILE_ARC_X = 10;
	private static final int TILE_ARC_Y = 10;
	private static final Color TILE_BG_COLOUR = new Color(204, 192, 179); // new Color(212, 204, 196);
	private static final Color TILE_V2_COLOUR = new Color(238, 228, 218);
    private static final Color TILE_V4_COLOUR = new Color(237, 224, 200);
    private static final Color TILE_V8_COLOUR = new Color(242, 177, 121);
    private static final Color TILE_V16_COLOUR = new Color(245, 149, 99);
    private static final Color TILE_V32_COLOUR = new Color(246, 124, 95);
    private static final Color TILE_V64_COLOUR = new Color(246, 94, 59);
    private static final Color TILE_V128_COLOUR= new Color(237, 207, 114);
    private static final Color TILE_V256_COLOUR = new Color(237, 204, 97);
    private static final Color TILE_V512_COLOUR = new Color(237, 200, 80);
    private static final Color TILE_V1024_COLOUR = new Color(237, 197, 63);
    private static final Color TILE_V2048_COLOUR = new Color(237, 194, 46);
    private static final Color TILE_VOTHER_COLOUR = Color.BLACK;
    private static final Font TILE_FONT = new Font("TimesRoman", Font.BOLD, 18);
	private final BoardManager boardManager;
	
	
	public GameContent(BoardManager boardManager) {
		this.boardManager = boardManager;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBoard(g);
	}
	
	private void drawBoard(Graphics g) {
		int[][] boardValues = boardManager.getBoardValues();
		for(int i = 0; i < BoardManager.ROWS; i++) {
			for(int j = 0; j < BoardManager.COLS; j++) {
				int startX = PADDING_X * (1 + j) + TILE_SIZE_X * j;
				int startY = PADDING_Y * (1 + i) + TILE_SIZE_Y * i;
				drawTile(g, boardValues[i][j], startX, startY);
			}
		}
	}
	
	private void drawTile(Graphics g, int tileVal, int startX, int startY) {
		boolean drawNum;
		int numOffset = 0;
		Color numColour = Color.BLACK;
		
		// Get the tile background colour
		switch(tileVal) {
			case 0:
				g.setColor(TILE_BG_COLOUR);
				drawNum = false;
				break;
			case 2: 
				g.setColor(TILE_V2_COLOUR);
				drawNum = true;
				numOffset = 0;
				numColour = Color.BLACK;
				break;
			case 4:
				g.setColor(TILE_V4_COLOUR);
				drawNum = true;
				numOffset = 0;
				numColour = Color.BLACK;
				break;
			case 8:
				g.setColor(TILE_V8_COLOUR);
				drawNum = true;
				numOffset = 0;
				numColour = Color.WHITE;
				break;
			case 16:
				g.setColor(TILE_V16_COLOUR);
				drawNum = true;
				numOffset = 0;
				numColour = Color.WHITE;
				break;
			case 32:
				g.setColor(TILE_V32_COLOUR);
				drawNum = true;
				numOffset = 0;
				numColour = Color.WHITE;
				break;
			case 64:
				g.setColor(TILE_V64_COLOUR);
				drawNum = true;
				numOffset = 0;
				numColour = Color.WHITE;
				break;
			case 128:
				g.setColor(TILE_V128_COLOUR);
				drawNum = true;
				numOffset = 0;
				numColour = Color.WHITE;
				break;
			case 256:
				g.setColor(TILE_V256_COLOUR);
				drawNum = true;
				numOffset = 0;
				numColour = Color.WHITE;
				break;
			case 512:
				g.setColor(TILE_V512_COLOUR);
				drawNum = true;
				numOffset = 0;
				numColour = Color.WHITE;
				break;
			case 1024:
				g.setColor(TILE_V1024_COLOUR);
				drawNum = true;
				numOffset = 0;
				numColour = Color.WHITE;
				break;
			case 2048:
				g.setColor(TILE_V2048_COLOUR);
				drawNum = true;
				numOffset = 0;
				numColour = Color.WHITE;
				break;
			default:
				g.setColor(TILE_VOTHER_COLOUR);
				drawNum = true;
				numOffset = 0;
				numColour = Color.WHITE;
				break;
		}
		
		// Draw the background colour for the tile
		g.fillRoundRect(startX, startY, TILE_SIZE_X, TILE_SIZE_Y, TILE_ARC_X, TILE_ARC_Y);
		
		// If the tile is not empty, draw the numeric value of the tile
		if(drawNum) {
			g.setColor(numColour);
			g.setFont(TILE_FONT);
			g.drawString(String.valueOf(tileVal), startX + TILE_SIZE_X / 2 - numOffset, startY + TILE_SIZE_Y / 2);
		}
	}
	
	public BoardManager getBoardManager() {
		return boardManager;
	}
	
	
}
