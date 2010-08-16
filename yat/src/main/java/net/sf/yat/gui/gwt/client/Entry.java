package net.sf.yat.gui.gwt.client;

import net.sf.yat.domain.Game;
import net.sf.yat.domain.GameFactory;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class Entry implements EntryPoint {

	GameFactory gameFactory = new GameFactory(new HardCodedTaskDAO());
	
	public void onModuleLoad() {		
		final PopupPanel panel = new PopupPanel();
		panel.setTitle("Test");
		panel.setSize("150px", "150px");
		final NewGameView newGame = new NewGameView();
		newGame.btnOk.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {				
				Game game = newGame.getGame();
				MainView view = new MainView(game);
				RootPanel.get().add(view);
				game.start();
				panel.hide();
			}
		});
		panel.add(newGame);
		panel.setPopupPosition(0, 0);
		panel.show();
	}

}
