package net.sf.yat.domain;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class GameTest {
	Team team1 = new Team(Arrays.asList(new Player("player11")), "team1");
	Team team2 = new Team(Arrays.asList(new Player("player21"), new Player("player22"), new Player("player23")), "team2");
	Team team3 = new Team(Arrays.asList(new Player("player13")), "team3");
	final LinkedList<Team> threeTeams = new LinkedList<Team>(Arrays.asList(team1, team2, team3));
		
	// TODO: think if this case is realistic
	public void shouldBeDoneIfNoGamesLeftNotStartedInitially()
	{
		Game game = new Game(new LinkedList(), new TotalGamesPlayedCriteria(3), null);
		assertTrue(game.isDone());
	}
	
	@Test
	public void shouldBeNotStartedInitially()
	{
		
		Game game = new Game(threeTeams, new TotalGamesPlayedCriteria(3) , null);
		assertTrue(!game.isInProgress());
	}
	
	@Test
	public void shouldBeInProgressAfterBeingStarted()
	{		
		Game game = new Game(threeTeams, new TotalGamesPlayedCriteria(3), getDumbProvider());
		game.start();
		assertTrue(game.isInProgress());
		assertNotNull(game.getCurrentTeam());
	}
	
	@Test
	public void shouldNotStartTwoTimes()
	{		
		Game game = new Game(threeTeams, new TotalGamesPlayedCriteria(3), getDumbProvider());
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
		Game game = new Game(threeTeams, new TotalGamesPlayedCriteria(3), getDumbProvider());
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
		Game game = new Game(threeTeams, new TotalGamesPlayedCriteria(3), getDumbProvider());
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
	
	@Test
	public void shouldStartNextMoveOnFailure()
	{
		Game game = new Game(threeTeams, new TotalGamesPlayedCriteria(3), getDumbProvider());
		game.start();
		
		Capture<GameRound> afterCapture = new Capture<GameRound>();
		
		GameListener listener = createMock(GameListener.class);
		checkOrder(listener, true);
		listener.afterRound(capture(afterCapture));
		listener.beforeRound(isA(GameRound.class));
		
		game.addListener(listener);
		
		replay(listener);		
		game.roundFailed();
		verify(listener);
		
		GameRound roundResults = afterCapture.getValue();
		assertEquals(-1, roundResults.getWinningPlayerNum());
		assertEquals(null, roundResults.getWinningPlayer());
		assertEquals(null, roundResults.getRoundWinner());
	}
	
	@Test
	public void shouldNotifyGameEndListener()
	{
		Game game = new Game(threeTeams, new TotalGamesPlayedCriteria(3), getDumbProvider());
		game.start();
		
		GameListener listener = createMock(GameListener.class);
		listener.afterRound(isA(GameRound.class));
		EasyMock.expectLastCall().anyTimes();
		listener.beforeRound(isA(GameRound.class));
		EasyMock.expectLastCall().anyTimes();
		listener.gameOver();
		
		game.addListener(listener);
		
		replay(listener);	
		
		game.start();
		game.roundFailed();
		game.roundFailed();
		game.roundFailed();
		verify(listener);
	}
	
	private TaskProvider getDumbProvider()
	{
		TaskProvider p = createMock(TaskProvider.class);
		expect(p.getTask(TaskComplexity.NORM)).andReturn(new Task(null, null, TaskComplexity.NORM)).anyTimes();
		replay(p);
		return p;
	}
}
