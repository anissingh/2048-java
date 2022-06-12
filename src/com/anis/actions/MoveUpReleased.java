package com.anis.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class MoveUpReleased extends AbstractAction {

	public static final String moveUpRName = "MOVE UP RELEASED";
	private static final long serialVersionUID = 9142010263129836157L;

	@Override
	public void actionPerformed(ActionEvent e) {
		KeysPressed.isUpArrowPressed = false;
	}

}
