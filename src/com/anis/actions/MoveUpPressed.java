package com.anis.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.anis.ia.ContentAnimationManager;
import com.anis.usecases.BoardManipulator;

public class MoveUpPressed extends AbstractAction {

	public static final String MOVE_UP_P_NAME = "MOVE UP PRESSED";
	private static final long serialVersionUID = -6532155927148585733L;
	private final ContentAnimationManager animationManager;
	private final BoardManipulator boardManipulator;
	
	public MoveUpPressed(BoardManipulator boardManipulator, ContentAnimationManager animationManager) {
		this.boardManipulator = boardManipulator;
		this.animationManager = animationManager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!KeysPressed.isUpArrowPressed && !animationManager.getIsAnimationOccurring()) {
			KeysPressed.isUpArrowPressed = true;
			System.out.println("Up arrow pressed");
			boolean tileMoved = boardManipulator.moveUp();
			if(tileMoved) {
				animationManager.setAnimationBeginning();
			}
		}
	}

	
	
}
