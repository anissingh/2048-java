package com.anis.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.anis.ia.ContentAnimationManager;
import com.anis.usecases.BoardManipulator;

public class MoveRightPressed extends AbstractAction {

	public static final String MOVE_RIGHT_P_NAME = "MOVE RIGHT PRESSED";
	private static final long serialVersionUID = -4686321312108464563L;
	private final ContentAnimationManager animationManager;
	private final BoardManipulator boardManipulator;
	
	public MoveRightPressed(BoardManipulator boardManipulator, ContentAnimationManager animationManager) {
		this.boardManipulator = boardManipulator;
		this.animationManager = animationManager;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!KeysPressed.isRightArrowPressed && !animationManager.getIsAnimationOccurring()) {
			KeysPressed.isRightArrowPressed = true;
			System.out.println("Right arrow pressed");
			boolean tileMoved = boardManipulator.moveRight();
			if(tileMoved) {
//				content.setUpdatePositionManager(true);
//				content.setAnimationOccurring(true);
				animationManager.setAnimationBeginning();
			}
		}
	}
	
}
