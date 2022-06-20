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
	
	private static final String WINDOW_TITLE = "2048";
	private static final int WINDOW_WIDTH = 700;
	private static final int WINDOW_HEIGHT = 700;
	
	public static void setUpGame() {
		JFrame frame = new JFrame(WINDOW_TITLE);
		BoardManager bm = new BoardManagerImpl();
		bm.initializeBoard();
		BoardTileMatrix bo = new BoardOutputBoundary(bm);
		GameContent content = new GameContent(bm, bo);
		initWindow(frame, content, bm);
	}
	
	private static void initWindow(JFrame frame, GameContent content, BoardManager bm) {
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setTitle(WINDOW_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(content);
		initKeyBindings(content, bm);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private static void initKeyBindings(GameContent content, BoardManager bm) {
		KeyBindings.addKeyBinding(content, "pressed LEFT", MoveLeftPressed.MOVE_LEFT_P_NAME, new MoveLeftPressed(bm, content));
		KeyBindings.addKeyBinding(content, "released LEFT", MoveLeftReleased.MOVE_LEFT_R_NAME, new MoveLeftReleased());
		KeyBindings.addKeyBinding(content, "pressed RIGHT", MoveRightPressed.MOVE_RIGHT_P_NAME, new MoveRightPressed(bm, content));
		KeyBindings.addKeyBinding(content, "released RIGHT", MoveRightReleased.MOVE_RIGHT_R_NAME, new MoveRightReleased());
		KeyBindings.addKeyBinding(content, "pressed UP", MoveUpPressed.MOVE_UP_P_NAME, new MoveUpPressed(bm, content));
		KeyBindings.addKeyBinding(content, "released UP", MoveUpReleased.MOVE_UP_R_NAME, new MoveUpReleased());
		KeyBindings.addKeyBinding(content, "pressed DOWN", MoveDownPressed.MOVE_DOWN_P_NAME, new MoveDownPressed(bm, content));
		KeyBindings.addKeyBinding(content, "released DOWN", MoveDownReleased.MOVE_DOWN_R_NAME, new MoveDownReleased());
	}
	
}
