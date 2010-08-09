package net.sf.yat.gui.gwt.client;

import net.sf.yat.domain.Game;
import net.sf.yat.domain.GameFactory;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Entry implements EntryPoint {

	GameFactory gameFactory = new GameFactory(new HardCodedTaskDAO());
	
	public void onModuleLoad() {
		MainView view = new MainView("Hello");
		RootPanel.get().add(view);
		
		Game game = gameFactory.generateGame(5, 5, 3);
		game.start();
		view.lbConcept.setText(game.getCurrentTask().getConcept());
		view.lbType.setText(game.getCurrentTask().getType().toString());
	}

}
