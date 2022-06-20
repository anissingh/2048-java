package com.anis.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.anis.ia.GameContent;
import com.anis.usecases.BoardManipulator;

public class MoveDownPressed extends AbstractAction {

	public static final String moveDownPName = "MOVE DOWN PRESSED";
	private static final long serialVersionUID = 7847680339545237570L;
	private final GameContent content; 
	private final BoardManipulator boardManipulator;
	
	public MoveDownPressed(BoardManipulator boardManipulator, GameContent content) {
		this.boardManipulator = boardManipulator;
		this.content = content;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!KeysPressed.isDownArrowPressed && !content.getIsAnimationOccurring()) {
			KeysPressed.isDownArrowPressed = true;
			System.out.println("Down arrow pressed");
			boolean tileMoved = boardManipulator.moveDown();
			if(tileMoved) {
				content.setUpdatePositionManager(true);
				content.setAnimationOccurring(true);
			}
		}
	}

}
