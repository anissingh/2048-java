package com.anis.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.anis.ia.ContentAnimationManager;
import com.anis.usecases.BoardManipulator;

public class MoveLeftPressed extends AbstractAction {

	public static final String MOVE_LEFT_P_NAME = "MOVE LEFT PRESSED";
	private static final long serialVersionUID = -9085269388693092643L;
	private final ContentAnimationManager animationManager;
	private final BoardManipulator boardManipulator;
	
	public MoveLeftPressed(BoardManipulator boardManipulator, ContentAnimationManager animationManager) {
		this.boardManipulator = boardManipulator;
		this.animationManager = animationManager;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Check if left arrow was not previously pressed
		// This prevents holding down arrow keys from being detected as multiple events
		// Also check if an animation is occurring
		// This prevents tiles from being moved before a previous animation is finished, which causes
		// problems with the drawing function
		if(!KeysPressed.isLeftArrowPressed && !animationManager.getIsAnimationOccurring()) {
			KeysPressed.isLeftArrowPressed = true;
			System.out.println("Left arrow pressed");
			// Move left and repaint board
			boolean tileMoved = boardManipulator.moveLeft();
			// If a tile actually moved
			if(tileMoved) {
				// TODO: There may be an issue if the board tries to re-update in between moveLeft() and setUpdate()
				// because the positionManager won't be updated.
				// Tell content that an animation should be occurring
				animationManager.setAnimationBeginning();
			}
		}
	}

}
