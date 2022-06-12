package com.anis.drivers;

import javax.swing.JFrame;

import com.anis.actions.MoveDownPressed;
import com.anis.actions.MoveDownReleased;
import com.anis.actions.MoveLeftPressed;
import com.anis.actions.MoveLeftReleased;
import com.anis.actions.MoveRightPressed;
import com.anis.actions.MoveRightReleased;
import com.anis.actions.MoveUpPressed;
import com.anis.actions.MoveUpReleased;
import com.anis.ia.GameContent;
import com.anis.usecases.BoardManager;

public class Main {
	
	private static final String windowTitle = "2048";
	private static final int WIDTH = 700;
	private static final int HEIGHT = 700;
	
	
	private static void initWindow(JFrame frame, GameContent content, BoardManager bm) {
		frame.setSize(WIDTH, HEIGHT);
		frame.setTitle(windowTitle);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(content);
		initKeyBindings(content, bm);
		frame.setVisible(true);
	}
	
	private static void initKeyBindings(GameContent content, BoardManager bm) {
		KeyBindings.addKeyBinding(content, "pressed LEFT", MoveLeftPressed.moveLeftPName, new MoveLeftPressed(bm, content));
		KeyBindings.addKeyBinding(content, "released LEFT", MoveLeftReleased.moveLeftRName, new MoveLeftReleased());
		KeyBindings.addKeyBinding(content, "pressed RIGHT", MoveRightPressed.moveRightPName, new MoveRightPressed(bm, content));
		KeyBindings.addKeyBinding(content, "released RIGHT", MoveRightReleased.moveRightRName, new MoveRightReleased());
		KeyBindings.addKeyBinding(content, "pressed UP", MoveUpPressed.moveUpPName, new MoveUpPressed(bm, content));
		KeyBindings.addKeyBinding(content, "released UP", MoveUpReleased.moveUpRName, new MoveUpReleased());
		KeyBindings.addKeyBinding(content, "pressed DOWN", MoveDownPressed.moveDownPName, new MoveDownPressed(bm, content));
		KeyBindings.addKeyBinding(content, "released DOWN", MoveDownReleased.moveDownRName, new MoveDownReleased());
	}
	
	public static void main(String[] args) {
		// Initialize game window
		JFrame frame = new JFrame(windowTitle);
		BoardManager bm = new BoardManager();
		bm.initializeBoard();
		GameContent content = new GameContent(bm);
		initWindow(frame, content, bm);
		
		
		// TODO: For debugging
		bm.printBoard();
	}
	
}
