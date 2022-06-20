package com.anis.ia;

import javax.swing.JFrame;

import com.anis.actions.MoveDownPressed;
import com.anis.actions.MoveDownReleased;
import com.anis.actions.MoveLeftPressed;
import com.anis.actions.MoveLeftReleased;
import com.anis.actions.MoveRightPressed;
import com.anis.actions.MoveRightReleased;
import com.anis.actions.MoveUpPressed;
import com.anis.actions.MoveUpReleased;
import com.anis.usecases.BoardManager;
import com.anis.usecases.BoardManagerImpl;
import com.anis.usecases.BoardOutputBoundary;
import com.anis.usecases.BoardTileMatrix;

public class GameInitializer {
	
	private static final String windowTitle = "2048";
	private static final int WINDOW_WIDTH = 700;
	private static final int WINDOW_HEIGHT = 700;
	
	public static void setUpGame() {
		JFrame frame = new JFrame(windowTitle);
		BoardManager bm = new BoardManagerImpl();
		bm.initializeBoard();
		BoardTileMatrix bo = new BoardOutputBoundary(bm);
		GameContent content = new GameContent(bm, bo);
		initWindow(frame, content, bm);
	}
	
	private static void initWindow(JFrame frame, GameContent content, BoardManager bm) {
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setTitle(windowTitle);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(content);
		initKeyBindings(content, bm);
		frame.setResizable(false);
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
	
}
