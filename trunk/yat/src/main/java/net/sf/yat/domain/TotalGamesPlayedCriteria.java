package net.sf.yat.domain;

public class TotalGamesPlayedCriteria implements EndGameCriteria{	
	private final int totalGamesAllowed;
	public TotalGamesPlayedCriteria(int gamesAllowed) {
		super();
		this.totalGamesAllowed = gamesAllowed;
	}
	
	@Override
	public boolean wasGameOver(Game game) {		
		return game.getPlayed().size() >= totalGamesAllowed;
	}
}
