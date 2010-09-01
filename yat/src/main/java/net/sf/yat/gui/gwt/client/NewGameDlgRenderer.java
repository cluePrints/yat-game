package net.sf.yat.gui.gwt.client;

import net.sf.yat.domain.Game;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PopupPanel;

public class NewGameDlgRenderer {
	private MainView view;

	public NewGameDlgRenderer(MainView view) {
		super();
		this.view = view;
	}
	
	public void show()
	{
		final PopupPanel panel = new PopupPanel();
		panel.setTitle("Test");
		panel.setSize("150px", "150px");
		final NewGameView newGame = new NewGameView();
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
