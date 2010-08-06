package net.sf.yat.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerTurnStrategy {
	public Player getPlayer(List<GameRound> roundsPlayed, List<Team> teams)
	{
		Map<Team, Integer> teamsPlayed = new HashMap<Team, Integer>();
		for (Team team : teams) {
			teamsPlayed.put(team, 0);
		}
		
		Map<Player, Integer> playersPlayed = new HashMap<Player, Integer>();
		for (Team team : teams) {
			for (Player player : team.getPlayers()) {
				playersPlayed.put(player, 0);
			}
		}
		
		for (GameRound round : roundsPlayed) {
			// plays per team
			Team playedTeam = round.getRoundOwner();			
			Integer initial = teamsPlayed.get(playedTeam);
			teamsPlayed.put(playedTeam, initial+1);
			
			// plays per player
			Player playedPlayer = round.getPlayingPlayer();
			initial = playersPlayed.get(playedPlayer);
			playersPlayed.put(playedPlayer, initial+1);
		}
		
		
		
		Team teamToPlay = getCandidate(teams, teamsPlayed);
		
		Player playerToPlay = getCandidate(teamToPlay.getPlayers(), playersPlayed);
		
		return playerToPlay;
	}

	private <T> T getCandidate(List<T> candidates, Map<T, Integer> candidatesPlayed) {
		T teamToPlay = candidates.get(0);
		int num = candidatesPlayed.get(teamToPlay);
		for (T team : candidates) {
			int teamNum = candidatesPlayed.get(team);
			if (teamNum < num) {
				teamToPlay = team;
				break;
			}
		}
		return teamToPlay;
	}
}
