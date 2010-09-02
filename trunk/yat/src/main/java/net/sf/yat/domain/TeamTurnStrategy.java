package net.sf.yat.domain;

import java.util.LinkedList;
import java.util.List;

public class TeamTurnStrategy {
	public Team getTeam(LinkedList<GameRound> roundsPlayed, List<Team> teams)
	{
		if (roundsPlayed == null || roundsPlayed.size() == 0) {
			return teams.get(0);
		}
		GameRound round = roundsPlayed.getLast();
		Team lastPlayingTeam = round.getRoundOwner();
		int lastTeamPos = teams.indexOf(lastPlayingTeam);	
		int nextTeamPos = (lastTeamPos + 1) % teams.size();
		return teams.get(nextTeamPos);
	}
}
