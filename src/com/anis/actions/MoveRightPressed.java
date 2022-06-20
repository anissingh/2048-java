package com.anis.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.anis.ia.GameContent;
import com.anis.usecases.BoardManager;

public class MoveRightPressed extends AbstractAction {

	public static final String moveRightPName = "MOVE RIGHT PRESSED";
	private static final long serialVersionUID = -4686321312108464563L;
	private final GameContent content;
	private final BoardManager boardManager;
	
	public MoveRightPressed(BoardManager boardManager, GameContent content) {
		this.boardManager = boardManager;
		this.content = content;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!KeysPressed.isRightArrowPressed && !content.getIsAnimationOccurring()) {
			KeysPressed.isRightArrowPressed = true;
			System.out.println("Right arrow pressed");
			boolean tileMoved = boardManager.moveRight();
			if(tileMoved) {
				content.setUpdatePositionManager(true);
				content.setAnimationOccurring(true);
			}
		}
	}
	
}
