package net.sf.yat.domain;

import java.util.List;

/**
 * Team of players participating.
 */
public class Team {
	private final List<Player> players;
	private final String name;
	
	public Team(List<Player> players, String name) {
		super();
		this.players = players;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
