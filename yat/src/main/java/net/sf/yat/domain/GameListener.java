package net.sf.yat.domain;

public interface GameListener {
	void roundPlayed(GameRound round);
	void beforeRound(GameRound round);
}
