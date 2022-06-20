package com.anis.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.anis.ia.GameContent;
import com.anis.usecases.BoardManager;

public class MoveUpPressed extends AbstractAction {

	public static final String moveUpPName = "MOVE UP PRESSED";
	private static final long serialVersionUID = -6532155927148585733L;
	private final GameContent content;
	private final BoardManager boardManager;
	
	public MoveUpPressed(BoardManager boardManager, GameContent content) {
		this.boardManager = boardManager;
		this.content = content;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!KeysPressed.isUpArrowPressed && !content.getIsAnimationOccurring()) {
			KeysPressed.isUpArrowPressed = true;
			System.out.println("Up arrow pressed");
			boolean tileMoved = boardManager.moveUp();
			if(tileMoved) {
				content.setUpdatePositionManager(true);
				content.setAnimationOccurring(true);
			}
		}
	}

	
	
}
