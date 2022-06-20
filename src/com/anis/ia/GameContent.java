package com.anis.ia;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.anis.usecases.BoardTileMatrix;
import com.anis.usecases.TileSpawner;

public class GameContent extends JPanel implements ActionListener {

	private static final long serialVersionUID = 188110181513518238L;
	private static final int PADDING_X = 16;
	private static final int PADDING_Y = 16;
	private static final int TILE_SIZE_X = 100;
	private static final int TILE_SIZE_Y = 100;
	private static final int TILE_EXPANDING_SPEED = 5;
	private static final int TILE_COMPRESSING_SPEED = 5;
	private static final int TILE_ARC_X = 10;
	private static final int TILE_ARC_Y = 10;
	private static final int TILE_SLIDE_SPEED = 50;
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
    private static final Color BOARD_BACKGROUND_COLOUR = new Color(149, 140, 129); // new Color(137, 122, 100);
    private static final int BOARD_ARC_X = 10;
    private static final int BOARD_ARC_Y = 10;
    private static final Font TILE_FONT = new Font("TimesRoman", Font.BOLD, 18);
    private static final Font GAME_OVER_FONT = new Font("TimesRoman", Font.BOLD, 40);
    private static final int ANIMATION_DELAY_MS = 10;
	private final TileSpawner tileSpawner;
	private final BoardTileMatrix boardOutput;
	private final Timer timer;
	private PositionManager positionManager;
	private boolean resetPositionManager;
	private boolean isAnimationOccurring = false;
	private boolean lastRender = false;
	private boolean stopDrawing = false;
	
	
	public GameContent(TileSpawner tileSpawner, BoardTileMatrix boardOutput) {
		this.tileSpawner = tileSpawner;
		this.boardOutput = boardOutput;
		this.positionManager = initializePositionManager();
		this.resetPositionManager = false;
		this.timer = new Timer(ANIMATION_DELAY_MS, this);
		this.timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(!stopDrawing) {
			super.paintComponent(g);
			drawBackground(g);
			drawBoard(g);
		}
	}
	
	// TODO: This method is too long; break into helper methods
	private void drawBoard(Graphics g) {
		boolean tileAnimating = false;
		if(resetPositionManager) {
			positionManager = initializePositionManager();
			resetPositionManager = false;
		}
		for(int i = 0; i < BoardTileMatrix.ROWS; i++) {
			for(int j = 0; j < BoardTileMatrix.COLS; j++) {
				int startX = translateCoordinateToScreen('x', j);
				int startY = translateCoordinateToScreen('y', i);
				int tileCurrX = positionManager.getPosition('x', i, j);
				int tileCurrY = positionManager.getPosition('y', i, j);
				int sizeX = positionManager.getSize('x', i, j);
				int sizeY = positionManager.getSize('y', i, j);
				// Draw a background tile at the correct x and y position in case a tile is currently sliding to
				// that tile but isn't there yet
				drawTile(g, 0, startX, startY, sizeX, sizeY);
				if(tileCurrX != startX) {
					// Tile's old x position is not where its new x position should be, so we need to
					// animate it sliding
					tileAnimating = true;
					// Calculate new position on the screen
					int newPos = tileCurrX + (int) (Math.signum(startX - tileCurrX)) * TILE_SLIDE_SPEED;
					// Need this check before we draw the tile because the tile can "over-slide" and be too far past its
					// designated slot
					if(Math.abs(newPos - startX) < TILE_SLIDE_SPEED) {
						newPos = startX;
					}
					positionManager.updatePosition('x', newPos, i, j);
					// Don't need to check the y position of this tile because tiles can only move in one dimension
					drawTile(g, boardOutput.getTileValue(i, j), newPos, tileCurrY, sizeX, sizeY);
				} else if(tileCurrY != startY) {
					// Tile's old y position is not where its new y position should be, so we need to
					// animate it sliding
					tileAnimating = true;
					int newPos = tileCurrY + (int) (Math.signum(startY - tileCurrY)) * TILE_SLIDE_SPEED;
					if(Math.abs(newPos - startY) < TILE_SLIDE_SPEED) {
						newPos = startY;
					}
					positionManager.updatePosition('y', newPos, i, j);
					drawTile(g, boardOutput.getTileValue(i, j), tileCurrX, newPos, sizeX, sizeY);
				} else {
					// The tile has reached its destination here
					// We first need to check if it has been merged, because we will do the animation here
					if(boardOutput.getTileWasMerged(i, j)) {
						// Set tileAnimating to true because a tile is being animated
						tileAnimating = true;
						// Handle merging animation resizing for expansion/compression
						handleMergingAnimation(positionManager, i, j);
						// Draw the tile
						sizeX = positionManager.getSize('x', i, j);
						sizeY = positionManager.getSize('y', i, j);
						drawTile(g, boardOutput.getTileValue(i, j), startX, startY, sizeX, sizeY);
					} else {
						// No merging animation needs to occur here
						drawTile(g, boardOutput.getTileValue(i, j), startX, startY, sizeX, sizeY);
					}
				}
			}
		}
		
		if(!tileAnimating && isAnimationOccurring) {
			isAnimationOccurring = false;
			// Spawn tile because an animation has completed
			// Don't need to check the return value of spawnTile() because if an animation occurred,
			// the game 
			tileSpawner.spawnTile();
			
			// Check if the game is now over
			if(boardOutput.isGameOver()) {
				// Draw the last tile that was spawned
				// TODO: Make spawnTile return the x and y of tile spawned, and use those to just draw in the last
				// tile separately
				lastRender = true;
				// Return so the below lastRender check does not immediately go off
				return;
			}
		}
		
		if(lastRender) {
			drawGameOver(g);
			stopDrawing = true;
			timer.stop();
		}
		
	}
	
	private void drawBackground(Graphics g) {
		int startX = 0;
		int startY = 0;
		int sizeX = TILE_SIZE_X * BoardTileMatrix.ROWS + PADDING_X * (BoardTileMatrix.ROWS + 1);
		int sizeY = TILE_SIZE_Y * BoardTileMatrix.COLS + PADDING_Y * (BoardTileMatrix.COLS + 1);
		g.setColor(BOARD_BACKGROUND_COLOUR);
		g.fillRoundRect(startX, startY, sizeX, sizeY, BOARD_ARC_X, BOARD_ARC_Y);
	}
	
	private void handleMergingAnimation(PositionManager positionManager, int rowIndex, int colIndex) {
		int tileSizeX = positionManager.getSize('x', rowIndex, colIndex);
		int tileSizeY = positionManager.getSize('y', rowIndex, colIndex);
		
		// Invariant: Tile is either expanding or compressing
		if(boardOutput.getTileIsExpanding(rowIndex, colIndex)) {
			// Increase tile size
			tileSizeX += TILE_EXPANDING_SPEED;
			tileSizeY += TILE_EXPANDING_SPEED;
			// Update size in position manager
			positionManager.updateSize(tileSizeX, tileSizeY, rowIndex, colIndex);
			// If the tile has reached its max height
			if(tileSizeX == TILE_SIZE_X + TILE_EXPANDING_SPEED * 2 && tileSizeY == TILE_SIZE_Y + TILE_EXPANDING_SPEED * 2) {
				// Set it to be compressing for the next clock cycle
				boardOutput.setTileIsExpanding(false, rowIndex, colIndex);
				boardOutput.setTileIsCompressing(true, rowIndex, colIndex);
			}
		} else {
			// Tile is compressing here, so decrease tile size
			tileSizeX -= TILE_COMPRESSING_SPEED;
			tileSizeY -= TILE_COMPRESSING_SPEED;
			// Update size in position manager
			positionManager.updateSize(tileSizeX, tileSizeY, rowIndex, colIndex);
			// If the tile has returned to its original height
			if(tileSizeX == TILE_SIZE_X && tileSizeY == TILE_SIZE_Y) {
				// Turn off its compressing attribute and set its merged animation to false because it is no longer
				// being merged
				boardOutput.setTileIsCompressing(false, rowIndex, colIndex);
				boardOutput.setTileWasMerged(false, rowIndex, colIndex);
			}
		}
	}
	
	private void drawTile(Graphics g, int tileVal, int startX, int startY, int sizeX, int sizeY) {
		boolean drawNum;
		int numOffset = calculateTileTextOffset(tileVal);
		Color numColour = Color.BLACK;
		
		// Get the tile background colour
		switch(tileVal) {
			case 0:
				g.setColor(TILE_BG_COLOUR);
				drawNum = false;
				numOffset = 0;
				break;
			case 2: 
				g.setColor(TILE_V2_COLOUR);
				drawNum = true;
				numColour = Color.BLACK;
				break;
			case 4:
				g.setColor(TILE_V4_COLOUR);
				drawNum = true;
				numColour = Color.BLACK;
				break;
			case 8:
				g.setColor(TILE_V8_COLOUR);
				drawNum = true;
				numColour = Color.WHITE;
				break;
			case 16:
				g.setColor(TILE_V16_COLOUR);
				drawNum = true;
				numColour = Color.WHITE;
				break;
			case 32:
				g.setColor(TILE_V32_COLOUR);
				drawNum = true;
				numColour = Color.WHITE;
				break;
			case 64:
				g.setColor(TILE_V64_COLOUR);
				drawNum = true;
				numColour = Color.WHITE;
				break;
			case 128:
				g.setColor(TILE_V128_COLOUR);
				drawNum = true;
				numColour = Color.WHITE;
				break;
			case 256:
				g.setColor(TILE_V256_COLOUR);
				drawNum = true;
				numColour = Color.WHITE;
				break;
			case 512:
				g.setColor(TILE_V512_COLOUR);
				drawNum = true;
				numColour = Color.WHITE;
				break;
			case 1024:
				g.setColor(TILE_V1024_COLOUR);
				drawNum = true;
				numColour = Color.WHITE;
				break;
			case 2048:
				g.setColor(TILE_V2048_COLOUR);
				drawNum = true;
				numColour = Color.WHITE;
				break;
			default:
				g.setColor(TILE_VOTHER_COLOUR);
				drawNum = true;
				numColour = Color.WHITE;
				break;
		}
		
		// Draw the background colour for the tile
		g.fillRoundRect(startX, startY, sizeX, sizeY, TILE_ARC_X, TILE_ARC_Y);
		
		// If the tile is not empty, draw the numeric value of the tile
		if(drawNum) {
			g.setColor(numColour);
			g.setFont(TILE_FONT);
			g.drawString(String.valueOf(tileVal), startX + sizeX / 2 - numOffset, startY + sizeY / 2 + 5);
		}
	}
	
	private int calculateTileTextOffset(int tileVal) {
		return Integer.toString(tileVal).length() * 4;
	}
	
	private int translateCoordinateToScreen(char type, int coord) {
		if(type == 'x') {
			return PADDING_X * (1 + coord) + TILE_SIZE_X * coord;
		} else {
			return PADDING_Y * (1 + coord) + TILE_SIZE_Y * coord;
		}
	}
	
	private void drawGameOver(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(GAME_OVER_FONT);
		int sizeX = TILE_SIZE_X * BoardTileMatrix.ROWS + PADDING_X * (BoardTileMatrix.ROWS + 1);
		int sizeY = TILE_SIZE_Y * BoardTileMatrix.COLS + PADDING_Y * (BoardTileMatrix.COLS + 1);
		int offset = 20;
		g.drawString("Game over!", sizeX / 2 - offset, sizeY / 2);
	}
	
	// TODO: Move this to a different class
	private PositionManager initializePositionManager() {
		PositionManager positionManager = new PositionManager(BoardTileMatrix.ROWS, BoardTileMatrix.COLS);
		for(int i = 0; i < BoardTileMatrix.ROWS; i++) {
			for(int j = 0; j < BoardTileMatrix.COLS; j++) {
				int xPos = translateCoordinateToScreen('x', boardOutput.getTilePrevX(i, j));
				int yPos = translateCoordinateToScreen('y', boardOutput.getTilePrevY(i, j));;
				positionManager.initializePosition(xPos, yPos, TILE_SIZE_X, TILE_SIZE_Y, i, j);
			}
		}
		return positionManager;
	}
	
	protected void updateScreen() {
		this.revalidate();
		this.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		updateScreen();
	}
	
	public void setUpdatePositionManager(boolean resetPositionManager) {
		this.resetPositionManager = resetPositionManager;
	}
	
	public void setAnimationOccurring(boolean isAnimationOccurring) {
		this.isAnimationOccurring = isAnimationOccurring;
	}
	
	public boolean getIsAnimationOccurring() {
		return this.isAnimationOccurring;
	}


	
}
