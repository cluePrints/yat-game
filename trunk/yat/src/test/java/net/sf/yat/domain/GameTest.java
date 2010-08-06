package net.sf.yat.domain;

import java.util.Arrays;
import java.util.LinkedList;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

public class GameTest {
	Team team1 = new Team(Arrays.asList(new Player("player1")), "team1");
	Team team2 = new Team(Arrays.asList(new Player("player2")), "team2");
	Team team3 = new Team(Arrays.asList(new Player("player3")), "team3");
	final LinkedList<Team> threeTeams = new LinkedList<Team>(Arrays.asList(team1, team2, team3));
	
	@Test
	public void shouldBeDoneIfNoGamesLeftNotStartedInitially()
	{
		Game game = new Game(null, new LinkedList(), null);
		Assert.assertTrue(game.isDone());
	}
	
	@Test
	public void shouldBeNotStartedInitially()
	{
		
		Game game = new Game(threeTeams, threeTeams , null);
		Assert.assertTrue(!game.isInProgress());
	}
	
	@Test
	public void shouldBeInProgressAfterBeingStarted()
	{		
		Game game = new Game(threeTeams, threeTeams, getDumbProvider());
		game.start();
		Assert.assertTrue(game.isInProgress());
		Assert.assertNotNull(game.getCurrentTeam());
	}
	
	@Test
	public void shouldNotStartTwoTimes()
	{		
		Game game = new Game(threeTeams, threeTeams, getDumbProvider());
		game.start();
		Team team = game.getCurrentTeam();
		game.start();
		Team team2 = game.getCurrentTeam();
		Assert.assertSame(team, team2);
		Assert.assertTrue(game.isInProgress());
	}
	
	private TaskProvider getDumbProvider()
	{
		TaskProvider p = EasyMock.createMock(TaskProvider.class);
		EasyMock.expect(p.getTask()).andReturn(new Task(null, null, null)).anyTimes();
		EasyMock.replay(p);
		return p;
	}
}
