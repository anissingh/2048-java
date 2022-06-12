package com.anis.ia;

import com.anis.usecases.BoardManager;

public class ContentAnimator {
	
	private final GameContent content;
	private final BoardManager boardManager;
	
	public ContentAnimator(GameContent content) {
		this.content = content;
		this.boardManager = content.getBoardManager();
	}
	
	
	
}
