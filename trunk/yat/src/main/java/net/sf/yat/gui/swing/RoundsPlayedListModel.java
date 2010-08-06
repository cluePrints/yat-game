package net.sf.yat.gui.swing;

import javax.swing.AbstractListModel;

import net.sf.yat.domain.Game;
import net.sf.yat.domain.GameListener;
import net.sf.yat.domain.GameRound;

@SuppressWarnings("serial")
public class RoundsPlayedListModel extends AbstractListModel{
	private final Game game;
	public RoundsPlayedListModel(Game pGame) {
		super();
		this.game = pGame;
		
		game.addListener(new GameListener() {			
			@Override
			public void roundPlayed(final GameRound round) {
				fireContentsChanged(game, game.getPlayed().size(), game.getPlayed().size());
			}
			
			@Override
			public void beforeRound(GameRound round) {
				// do nothing
			}
		});
	}
	
	
	
	@Override
	public int getSize() {	
		return game.getPlayed().size();
	}
	
	@Override
	public Object getElementAt(int index) {
		return game.getPlayed().get(getSize() - index - 1);
	}
}
