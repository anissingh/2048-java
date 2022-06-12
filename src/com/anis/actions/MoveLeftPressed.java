package com.anis.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.anis.ia.GameContent;
import com.anis.usecases.BoardManager;

public class MoveLeftPressed extends AbstractAction {

	public static final String moveLeftPName = "MOVE LEFT PRESSED";
	private static final long serialVersionUID = -9085269388693092643L;
	private final GameContent content;
	private final BoardManager boardManager;
	
	public MoveLeftPressed(BoardManager boardManager, GameContent content) {
		this.boardManager = boardManager;
		this.content = content;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Check if left arrow was not previously pressed
		// This prevents holding down arrow keys from being detected as multiple events
		if(!KeysPressed.isLeftArrowPressed) {
			KeysPressed.isLeftArrowPressed = true;
			// Move left and repaint board
			boardManager.moveLeft();
			content.revalidate();
			content.repaint();
			// TODO: For debugging
			System.out.println("Left arrow pressed");
		}
	}

}
