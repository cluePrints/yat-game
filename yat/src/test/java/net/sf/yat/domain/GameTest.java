package net.sf.yat.domain;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;

import org.easymock.Capture;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class GameTest {
	Team team1 = new Team(Arrays.asList(new Player("player11")), "team1");
	Team team2 = new Team(Arrays.asList(new Player("player21"), new Player("player22"), new Player("player23")), "team2");
	Team team3 = new Team(Arrays.asList(new Player("player13")), "team3");
	final LinkedList<Team> threeTeams = new LinkedList<Team>(Arrays.asList(team1, team2, team3));
		
	@Test
	public void shouldBeDoneIfNoGamesLeftNotStartedInitially()
	{
		Game game = new Game(new LinkedList(), new LinkedList(), null);
		assertTrue(game.isDone());
	}
	
	@Test
	public void shouldBeNotStartedInitially()
	{
		
		Game game = new Game(threeTeams, threeTeams , null);
		assertTrue(!game.isInProgress());
	}
	
	@Test
	public void shouldBeInProgressAfterBeingStarted()
	{		
		Game game = new Game(threeTeams, threeTeams, getDumbProvider());
		game.start();
		assertTrue(game.isInProgress());
		assertNotNull(game.getCurrentTeam());
	}
	
	@Test
	public void shouldNotStartTwoTimes()
	{		
		Game game = new Game(threeTeams, threeTeams, getDumbProvider());
		game.start();
		Team team = game.getCurrentTeam();
		game.start();
		Team team2 = game.getCurrentTeam();
		assertSame(team, team2);
		assertTrue(game.isInProgress());
	}
	
	@Test
	public void shouldInvokeBeforeRoundOnStart()
	{
		Game game = new Game(threeTeams, threeTeams, getDumbProvider());
		GameListener listener = createMock(GameListener.class);
		Capture<GameRound> beforeCapture = new Capture<GameRound>();
		listener.beforeRound(capture(beforeCapture));
		game.addListener(listener);
		
		replay(listener);		
		game.start();
		verify(listener);
		
		GameRound beforeNext = beforeCapture.getValue();
		assertEquals(null, beforeNext.getRoundWinner());
		assertEquals(null, beforeNext.getWinningPlayer());
		assertEquals(-1, beforeNext.getWinningPlayerNum());
	}
	
	@Test
	public void shouldInvokeListenerAfterRoundWasWon()
	{
		Game game = new Game(threeTeams, threeTeams, getDumbProvider());
		game.start();
		
		Capture<GameRound> afterCapture = new Capture<GameRound>();		
		
		GameListener listener = createMock(GameListener.class);
		checkOrder(listener, true);
		listener.afterRound(capture(afterCapture));
		listener.beforeRound(isA(GameRound.class));
		
		game.addListener(listener);
		
		replay(listener);		
		game.roundWon(1, 2);
		verify(listener);
		
		
		GameRound roundResults = afterCapture.getValue();
		assertEquals(2, roundResults.getWinningPlayerNum());
		assertEquals("player23", roundResults.getWinningPlayer().getName());
		assertEquals("team2", roundResults.getRoundWinner().getName());
		
		
		
	}
	
	private TaskProvider getDumbProvider()
	{
		TaskProvider p = createMock(TaskProvider.class);
		expect(p.getTask()).andReturn(new Task(null, null, null)).anyTimes();
		replay(p);
		return p;
	}
}
