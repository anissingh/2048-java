package com.anis.usecases;

public class ScoreManager implements ScoreIncrementer, ScoreFetcher {
	
	private static final int STARTING_SCORE = 0;
	private int score;
	
	public ScoreManager() {
		this.score = STARTING_SCORE;
	}
	
	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void incrementScore(int incrementVal) {
		score += incrementVal;
	}

}
