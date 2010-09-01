package net.sf.yat.gui.gwt.client;

import net.sf.yat.domain.GameFactory;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Entry implements EntryPoint {

	GameFactory gameFactory = new GameFactory(new HardCodedTaskDAO());
	
	public void onModuleLoad() {	
		MainView view = new MainView();
		RootPanel.get().add(view);
		view.newGame();
	}

}
