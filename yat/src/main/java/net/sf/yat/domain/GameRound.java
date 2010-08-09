package net.sf.yat.domain;

/**
 * Round, could be not played yet (i.e. with now winning team), or finished.
 */
public class GameRound {
	private final Team roundOwner;
	private final int playingPlayer;
	private final Team roundWinner;
	private final int winningPlayer;
	private final Task task;
	
	public GameRound(Team roundOwner, int playingPlayer, Team roundWinner, int winningPlayer, Task task) {
		super();
		this.roundOwner = roundOwner;
		this.roundWinner = roundWinner;
		this.task = task;
		this.winningPlayer = winningPlayer;
		this.playingPlayer = playingPlayer;
	}

	public Team getRoundOwner() {
		return roundOwner;
	}

	public Team getRoundWinner() {
		return roundWinner;
	}

	public Task getTask() {
		return task;
	}	
	
	public int getWinningPlayerNum() {
		return winningPlayer;
	}
	
	public Player getPlayingPlayer() {
		return nullSafeGetPlayer(roundOwner, playingPlayer);
	}
	
	public Player getWinningPlayer() {
		return nullSafeGetPlayer(roundWinner, winningPlayer);
	}	
	
	@Override
	public String toString() {
		return getWinningPlayer()+"("+getRoundWinner()+") - "+getTask();
	}
	
	private Player nullSafeGetPlayer(Team team, int playerNum) {
		if (team == null || team.getPlayers() == null) {
			return null;
		}
		return team.getPlayers().get(playerNum);
	}
}
