package net.sf.yat.domain;

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
		return roundOwner.getPlayers().get(playingPlayer);
	}
	
	public Player getWinningPlayer() {
		return roundWinner.getPlayers().get(winningPlayer);
	}
	
	@Override
	public String toString() {
		return String.format("%s(%s) - %s", getWinningPlayer(), getRoundWinner(), getTask());
	}
}
