package com.anis.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class MoveRightReleased extends AbstractAction {

	public static final String MOVE_RIGHT_R_NAME = "MOVE RIGHT RELEASED";
	private static final long serialVersionUID = -8992556164137199693L;

	@Override
	public void actionPerformed(ActionEvent e) {
		KeysPressed.isRightArrowPressed = false;
	}
	
}
