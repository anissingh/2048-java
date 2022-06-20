package com.anis.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.anis.ia.GameContent;
import com.anis.usecases.BoardManager;

public class MoveDownPressed extends AbstractAction {

	public static final String moveDownPName = "MOVE DOWN PRESSED";
	private static final long serialVersionUID = 7847680339545237570L;
	private final GameContent content; 
	private final BoardManager boardManager;
	
	public MoveDownPressed(BoardManager boardManager, GameContent content) {
		this.boardManager = boardManager;
		this.content = content;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!KeysPressed.isDownArrowPressed && !content.getIsAnimationOccurring()) {
			KeysPressed.isDownArrowPressed = true;
			System.out.println("Down arrow pressed");
			boolean tileMoved = boardManager.moveDown();
			if(tileMoved) {
				content.setUpdatePositionManager(true);
				content.setAnimationOccurring(true);
			}
		}
	}

}
