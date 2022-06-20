package com.anis.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class MoveDownReleased extends AbstractAction {

	public static final String MOVE_DOWN_R_NAME = "MOVE DOWN RELEASED";
	private static final long serialVersionUID = 4331352593982954671L;

	@Override
	public void actionPerformed(ActionEvent e) {
		KeysPressed.isDownArrowPressed = false;
	}

}
