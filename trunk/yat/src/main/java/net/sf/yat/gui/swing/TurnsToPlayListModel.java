package net.sf.yat.gui.swing;

import javax.swing.AbstractListModel;

import net.sf.yat.domain.Game;
	import net.sf.yat.domain.GameListenerAdapter;
import net.sf.yat.domain.GameRound;

@SuppressWarnings("serial")
public class TurnsToPlayListModel extends AbstractListModel{
	private final Game game;
	public TurnsToPlayListModel(Game pGame) {
		super();
		this.game = pGame;
		
		game.addListener(new GameListenerAdapter() {			
			@Override
			public void afterRound(final GameRound round) {
				fireIntervalRemoved(game, 0, 0);
			}
		});
	}
	
	
	
	@Override
	public int getSize() {	
		return 5;
	}
	
	@Override
	public Object getElementAt(int index) {
		return "Not implemented";
	}
}