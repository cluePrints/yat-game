package net.sf.yat.gui.gwt.client;

import net.sf.yat.domain.Game;
import net.sf.yat.domain.GameFactory;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Entry implements EntryPoint {

	GameFactory gameFactory = new GameFactory(new HardCodedTaskDAO());
	
	public void onModuleLoad() {
		Game game = gameFactory.generateGame(5, 5, 3);
		MainView view = new MainView(game);
		RootPanel.get().add(view);
		
		game.start();				
	}

}
