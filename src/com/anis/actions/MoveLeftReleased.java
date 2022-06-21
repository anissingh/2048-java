package com.anis.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class MoveLeftReleased extends AbstractAction {
	
	public static final String MOVE_LEFT_R_NAME = "MOVE LEFT RELEASED";
	private static final long serialVersionUID = -2558490780576610461L;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		KeysPressed.isLeftArrowPressed = false;
	}

	
	
}
