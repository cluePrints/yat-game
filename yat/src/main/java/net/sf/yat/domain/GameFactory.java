package net.sf.yat.domain;

import java.util.List;

public class GameFactory {
	private TaskProvider taskProvider;
	private EndGameCriteria criteria;
	public GameFactory(TaskProvider taskProvider) {
		super();
		this.taskProvider = taskProvider;
	}
	
	public void setCriteria(EndGameCriteria criteria) {
		this.criteria = criteria;
	}
	
	public Game newInstance(List<Team> teams) {
			Game game = new Game(teams, criteria, taskProvider);
		return game;
	}
}
