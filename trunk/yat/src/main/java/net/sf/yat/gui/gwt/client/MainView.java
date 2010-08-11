package net.sf.yat.gui.gwt.client;

import net.sf.yat.domain.Game;
import net.sf.yat.domain.GameListenerAdapter;
import net.sf.yat.domain.GameRound;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends Composite {

	private static final int TIME_PER_ROUND = 180;

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
	PushButton lbCurrentPlayer;
	
	@UiField
	TextBox tbTimer;
	
	Timer timer;
	TeamSelector teamSelector;
	int timeLeft = TIME_PER_ROUND;

	public MainView(final Game game) {
		this.game = game;
		
		game.addListener(new GameListenerAdapter() {
			
			@Override
			public void beforeRound(GameRound round) {
				lbConcept.setText(game.getCurrentTask().getConcept());
				lbType.setText(game.getCurrentTask().getType().toString());
				lbCurrentPlayer.setText(game.getCurrentPlayer().toString());
				timeLeft = TIME_PER_ROUND;
				tbTimer.setText(String.valueOf(timeLeft));
			}
			
			@Override
			public void gameOver() {
				timer.cancel();
				tbTimer.setText("--");
			}
		});
		
		timer = new Timer() {			
			@Override
			public void run() {				
				if (timeLeft <= 0) {
					game.roundFailed();
					if (game.isInProgress()) {
						timeLeft = TIME_PER_ROUND;
					} else {
						// game finished
						timer.cancel();
					}
				}	
				tbTimer.setText(String.valueOf(timeLeft));
				timeLeft--;
			}
		};
		
		timer.scheduleRepeating(1000);
		
		initWidget(uiBinder.createAndBindUi(this));
		teamSelector = new TeamSelector();
		teamSelector.setTeams(game.getTeams());
	}
	
	@UiHandler("btnGuessed")
	void onGuessed(ClickEvent e) {
		final PopupPanel panel = new PopupPanel();
		panel.setSize("150px", "150px");
		panel.add(teamSelector);
		panel.setPopupPosition(0, 0);
		panel.show();
		teamSelector.reset();
		teamSelector.btnCancel.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				panel.hide();				
			}
		});
		
		teamSelector.btnOk.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				game.roundWon(teamSelector.getSelectedTeamNum(), teamSelector.getSelectedPlayerNum());
				panel.hide();
			}
		});
	}
	
	@UiHandler("btnFailed")
	void onFail(ClickEvent e) {
		game.roundFailed();
	}
}
