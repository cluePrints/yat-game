package net.sf.yat.gui.swing;

import javax.swing.AbstractListModel;

import net.sf.yat.domain.Game;
import net.sf.yat.domain.GameListener;
import net.sf.yat.domain.GameRound;

@SuppressWarnings("serial")
public class TurnsToPlayListModel extends AbstractListModel{
	private final Game game;
	public TurnsToPlayListModel(Game pGame) {
		super();
		this.game = pGame;
		
		game.addListener(new GameListener() {			
			@Override
			public void afterRound(final GameRound round) {
				fireIntervalRemoved(game, 0, 0);
			}
			
			@Override
			public void beforeRound(GameRound round) {
				// do nothing
			}
		});
	}
	
	
	
	@Override
	public int getSize() {	
		return game.getTurnsToPlay().size();
	}
	
	@Override
	public Object getElementAt(int index) {
		return game.getTurnsToPlay().get(index);
	}
}
