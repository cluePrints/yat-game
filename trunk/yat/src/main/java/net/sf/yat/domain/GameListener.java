package net.sf.yat.domain;

public interface GameListener {	
	void beforeRound(GameRound round);
	void afterRound(GameRound round);
	void gameOver();
}
