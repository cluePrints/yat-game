package net.sf.yat.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.yat.dao.TextTaskDAO;

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

	public boolean isInProgress() {
		return !turnsToPlay.isEmpty() && currentTask != null
				&& currentTeam != null;
	}

	public void start() {
		if (!isInProgress()) {
			nextMove();
		}
	}

	public void roundWon(int teamNum, int playerNum) {
		if (isInProgress()) {
			Team winner = teams.get(teamNum);
			GameRound round = new GameRound(currentTeam, currentTeam.getPlayers().indexOf(currentPlayer), winner, playerNum,
					currentTask);
			played.add(round);			
			for (GameListener listener : listeners) {
				listener.afterRound(round);
			}
			nextMove();
		}
	}

	private void nextMove() {
		currentTeam = turnsToPlay.poll();
		currentTask = provider.getTask();
		currentPlayer = playerTurnStrategy.getPlayer(played, teams);
		GameRound round = new GameRound(currentTeam, currentTeam.getPlayers().indexOf(currentPlayer), null, -1, currentTask);
		for (GameListener listener : listeners) {
			listener.beforeRound(round);
		}
	}

	public List<Pair<Team, Integer>> getScores() {
		Map<Team, Integer> scores = new HashMap<Team, Integer>();
		for (Team team : getTeams()) {
			scores.put(team, 0);
		}

		List<GameRound> played = getPlayed();
		for (GameRound round : played) {
			Team winner = round.getRoundWinner();
			Integer initial = scores.get(winner);
			int wonPoints = round.getTask().getPoints();
			scores.put(winner, initial + wonPoints);
		}

		List<Pair<Team, Integer>> results = new LinkedList<Pair<Team, Integer>>();
		for (Map.Entry<Team, Integer> entry : scores.entrySet()) {
			results.add(new Pair<Team, Integer>(entry.getKey(), entry
					.getValue()));
		}
		Collections.sort(results, comparator);

		return results;
	}

	private Comparator<Pair<Team, Integer>> comparator = new Comparator<Pair<Team, Integer>>() {
		@Override
		public int compare(Pair<Team, Integer> o1, Pair<Team, Integer> o2) {
			return -o1.second.compareTo(o2.second);
		}
	};

	public static Game newInstance(List<Team> teams) {
		LinkedList<Team> turnsToPlay = new LinkedList<Team>();
		for (int i = 0; i < 5; i++) {
			turnsToPlay.addAll(teams);
		}
		Game game = new Game(teams, turnsToPlay, new TextTaskDAO(
				"tasks"));
		return game;
	}

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
		throw new RuntimeException();
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
