package net.sf.yat.domain;

import static java.util.Arrays.*;

import static junit.framework.Assert.*;

import org.junit.Test;

public class PlayerTurnStrategyTest {
	private Team team1 = new Team(asList(new Player("11"), new Player(
			"12")), "Team1");
	private Team team2 = new Team(asList(new Player("21"), new Player(
			"22")), "Team2");
	private GameRound player11Round = new GameRound(team1, 0, null, -1, null);
	private GameRound player12Round = new GameRound(team1, 1, null, -1, null);
	private GameRound player21Round = new GameRound(team2, 0, null, -1, null);
	
	private GameRound teamOneRound = player11Round;
	private GameRound teamTwoRound = player21Round;
	
	
	private PlayerTurnStrategy unit = new PlayerTurnStrategy();

	@Test
	public void shouldReturnFirstTeamIfAllPlayedEqualTimes() {
		Player player = unit.getPlayer(asList(teamOneRound, teamTwoRound), 
				asList(team1, team2));
		assertTrue(team1.getPlayers().contains(player));
	}
	
	@Test
	public void shouldReturnSecondTeamIfFirstPlayedMore() {
		Player player = unit.getPlayer(asList(teamOneRound, teamTwoRound, teamOneRound),  
				asList(team1, team2));
		assertTrue(team2.getPlayers().contains(player));
	}
	
	@Test
	public void shouldReturnSecondPlayerIfFirstPlayed() {
		Player player = unit.getPlayer(asList(player11Round, player12Round), 
				asList(team1));
		assertEquals(0, team1.getPlayers().indexOf(player));
	}
}
