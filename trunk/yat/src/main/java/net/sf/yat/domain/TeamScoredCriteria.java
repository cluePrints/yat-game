package net.sf.yat.domain;

public class TeamScoredCriteria implements EndGameCriteria{	
	private final int scoreRequired;
	public TeamScoredCriteria(int score) {
		super();
		this.scoreRequired = score;
	}
	@Override
	public boolean wasGameOver(Game game) {		
		return game.getScores().get(0).second >= scoreRequired;
	}
}
