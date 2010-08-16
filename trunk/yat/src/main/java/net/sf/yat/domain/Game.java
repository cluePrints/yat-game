package net.sf.yat.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Contains main logic, basically rules of the game.
 * 
 * Other classes in this package are immutable value classes with no logic at all. 
 *
 */
public class Game {
	private final List<Team> teams;
	private List<GameRound> played;
	private LinkedList<Team> turnsToPlay;
	private TaskProvider provider;
	private Team currentTeam;
	private Task currentTask;
	private Player currentPlayer;
	private List<GameListener> listeners = new LinkedList<GameListener>();
	private PlayerTurnStrategy playerTurnStrategy = new PlayerTurnStrategy();

	public boolean isDone() {
		return getTurnsToPlay().isEmpty();
	}

	/**
	 * Game is started and not finished yet
	 */
	public boolean isInProgress() {
		return !turnsToPlay.isEmpty() && currentTask != null
				&& currentTeam != null;
	}

	/**
	 * Starts the game if it's not started yet by determining player, team and stuff for current round
	 */
	public void start() {
		if (!isInProgress()) {
			nextMove();
		}
	}

	/**
	 * Called when round was won by some player from some team.
	 * This triggers {@link GameListener} events and start of new round.
	 */
	public void roundWon(int teamNum, int playerNum) {
		if (isInProgress()) {
			GameRound round;
			if (teamNum == -1) {
				round = new GameRound(currentTeam, currentTeam.getPlayers()
						.indexOf(currentPlayer), null, -1, currentTask);
			} else {
				Team winner = teams.get(teamNum);
				round = new GameRound(currentTeam, currentTeam.getPlayers()
						.indexOf(currentPlayer), winner, playerNum, currentTask);
			}
			played.add(round);			
			for (GameListener listener : listeners) {
				listener.afterRound(round);
			}
			nextMove();
		} else {
			for (GameListener listener : listeners) {
				listener.gameOver();
			}
		}
	}

	public void roundFailed()
	{
		roundWon(-1, -1);
	}
	
	/**
	 * Set up new round and trigger event listeners
	 */
	private void nextMove() {
		currentTeam = turnsToPlay.poll();
		currentPlayer = playerTurnStrategy.getPlayer(played, teams);
		currentTask = provider.getTask(currentPlayer.getDesiredComplexity());		
		GameRound round = new GameRound(currentTeam, currentTeam.getPlayers().indexOf(currentPlayer), null, -1, currentTask);
		for (GameListener listener : listeners) {
			listener.beforeRound(round);
		}
	}

	/**
	 * Returns list of pais, team:score, sorted descending.
	 */
	public List<Pair<Team, Integer>> getScores() {
		// initialize per team scores with 0
		Map<Team, Integer> scores = new HashMap<Team, Integer>();
		for (Team team : getTeams()) {
			scores.put(team, 0);
		}

		// walk through the history and update team scores
		List<GameRound> played = getPlayed();
		for (GameRound round : played) {
			Team winner = round.getRoundWinner();
			Integer initial = scores.get(winner);
			int wonPoints = round.getTask().getPoints();
			scores.put(winner, initial + wonPoints);
		}

		// transform results to a list of pairs
		List<Pair<Team, Integer>> results = new LinkedList<Pair<Team, Integer>>();
		for (Map.Entry<Team, Integer> entry : scores.entrySet()) {
			results.add(new Pair<Team, Integer>(entry.getKey(), entry
					.getValue()));
		}
		
		Collections.sort(results, comparator);

		return results;
	}

	// sorts pairs based on their second value so that bigger comes first (descending)
	private Comparator<Pair<Team, Integer>> comparator = new Comparator<Pair<Team, Integer>>() {
		@Override
		public int compare(Pair<Team, Integer> o1, Pair<Team, Integer> o2) {
			return -o1.second.compareTo(o2.second);
		}
	};
	
	// ==================================================================
	// Constructor & getter methods - not really interesting stuff
	public Game(List<Team> teams, LinkedList<Team> turnsToPlay,
			TaskProvider provider) {
		super();
		this.teams = new LinkedList<Team>(teams);
		this.turnsToPlay = new LinkedList<Team>(turnsToPlay);
		this.played = new LinkedList<GameRound>();
		this.provider = provider;
	}

	public void addListener(GameListener listener) {
		listeners.add(listener);
	}

	public int getCurrentTeamNumber() {
		return teams.indexOf(getCurrentTeam());
	}

	public Team getCurrentTeam() {
		return currentTeam;
	}

	public Task getCurrentTask() {
		return currentTask;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public List<GameRound> getPlayed() {
		return played;
	}

	public void setPlayed(List<GameRound> played) {
		this.played = played;
	}

	public LinkedList<Team> getTurnsToPlay() {
		return turnsToPlay;
	}
}
