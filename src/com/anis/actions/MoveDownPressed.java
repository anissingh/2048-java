package com.anis.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.anis.ia.ContentAnimationManager;
import com.anis.usecases.BoardManipulator;

public class MoveDownPressed extends AbstractAction {

	public static final String MOVE_DOWN_P_NAME = "MOVE DOWN PRESSED";
	private static final long serialVersionUID = 7847680339545237570L;
	private final ContentAnimationManager animationManager; 
	private final BoardManipulator boardManipulator;
	
	public MoveDownPressed(BoardManipulator boardManipulator, ContentAnimationManager animationManager) {
		this.boardManipulator = boardManipulator;
		this.animationManager = animationManager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!KeysPressed.isDownArrowPressed && !animationManager.getIsAnimationOccurring()) {
			KeysPressed.isDownArrowPressed = true;
			System.out.println("Down arrow pressed");
			boolean tileMoved = boardManipulator.moveDown();
			if(tileMoved) {
				animationManager.setAnimationBeginning();
//				content.setUpdatePositionManager(true);
//				content.setAnimationOccurring(true);
			}
		}
	}

}
