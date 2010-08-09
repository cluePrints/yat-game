package net.sf.yat.domain;

import java.util.LinkedList;
import java.util.List;

public class GameFactory {
	private TaskProvider taskProvider;
	public GameFactory(TaskProvider taskProvider) {
		super();
		this.taskProvider = taskProvider;
	}
	public Game generateGame(int teamNum, int playersNum, int gamesNum) {
		List<Team> teams = new LinkedList<Team>();
		for (int j = 1; j <= teamNum; j++) {
			List<Player> players = new LinkedList<Player>();
			for (int i = 1; i <= playersNum; i++) {
				players.add(new Player("Player #" + j + "" + i));
			}
			Team team = new Team(players, "Team #" + j);
			teams.add(team);
		}

		LinkedList<Team> turnsToPlay = new LinkedList<Team>();
		for (int i = 0; i < gamesNum; i++) {
			turnsToPlay.addAll(teams);
		}
		Game game = new Game(teams, turnsToPlay, taskProvider);
		return game;
	}
}
