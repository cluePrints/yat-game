package net.sf.yat.gui.swing;

import java.util.List;

import javax.swing.AbstractListModel;

import net.sf.yat.domain.Game;
import net.sf.yat.domain.GameListener;
import net.sf.yat.domain.GameRound;
import net.sf.yat.domain.Pair;
import net.sf.yat.domain.Team;

@SuppressWarnings("serial")
public class ScoreListModel extends AbstractListModel{
	private final Game game;
	private List<Pair<Team, Integer>> scores;
	public ScoreListModel(Game pGame) {
		super();
		this.game = pGame;
		scores = game.getScores();
		game.addListener(new GameListener() {			
			@Override
			public void roundPlayed(GameRound round) {
				scores = game.getScores();
				fireContentsChanged(game, 0, scores.size());
			}
			
			@Override
			public void beforeRound(GameRound round) {
				// do nothing
			}
		});
	}

	@Override
	public Object getElementAt(int index) {				
		return scores.get(index).first + "(" +scores.get(index).second+")";
	}
	
	@Override
	public int getSize() {
		return scores.size();
	}
}
