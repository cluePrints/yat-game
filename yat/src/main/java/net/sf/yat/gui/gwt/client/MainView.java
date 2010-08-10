package net.sf.yat.gui.gwt.client;

import net.sf.yat.domain.Game;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends Composite {

	private static MainViewUiBinder uiBinder = GWT
			.create(MainViewUiBinder.class);

	interface MainViewUiBinder extends UiBinder<Widget, MainView> {
	}
	
	private Game game;

	@UiField
	TabLayoutPanel mainPanel;

	@UiField
	PushButton lbType;

	@UiField
	PushButton lbConcept;
	
	@UiField
	TeamSelector teamSelector;

	public MainView(Game game) {
		this.game = game;
		initWidget(uiBinder.createAndBindUi(this));
		teamSelector.setTeams(game.getTeams());
	}
}
