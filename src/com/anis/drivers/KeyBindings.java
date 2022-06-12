package com.anis.drivers;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class KeyBindings {
	
	private static final int WIFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

	public static void addKeyBinding(JComponent component, String keyStrokeID, String inputName, AbstractAction action) {
		component.getInputMap(WIFW).put(KeyStroke.getKeyStroke(keyStrokeID), inputName);
		component.getActionMap().put(inputName, action);
	}
	
}
