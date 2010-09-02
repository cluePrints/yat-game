package net.sf.yat.gui.gwt.client;

import net.sf.yat.domain.Game;
import net.sf.yat.domain.GameFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PopupPanel;

public class NewGameDlgRenderer {
	private MainView view;
	private GameFactory factory;

	public NewGameDlgRenderer(MainView view, GameFactory factory) {
		super();
		this.view = view;
		this.factory = factory;		
	}
	
	public void show()
	{
		final PopupPanel panel = new PopupPanel();
		panel.setTitle("Test");
		panel.setSize("150px", "150px");
		final NewGameView newGame = new NewGameView(factory);
		newGame.btnOk.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {				
				Game game = newGame.getGame();
				view.newGame(game);
				game.start();
				panel.hide();
			}
		});
		panel.add(newGame);
		panel.setPopupPosition(0, 0);
		panel.show();
	}
}
