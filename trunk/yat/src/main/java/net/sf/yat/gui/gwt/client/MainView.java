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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends Composite {	
	private static final int TIME_PER_ROUND = 15;

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
	
	@UiField
	Button btnTimer;
	
	@UiField
	PushButton btnGuessed;
	
	@UiField
	PushButton btnFailed;
	
	Timer timer;
	TeamSelector teamSelector;
	int timeLeft = TIME_PER_ROUND;
	boolean timerRunning;
	
	private Messages locale;
	private Translator translator;
	
	private NewGameDlgRenderer newGameDlg;

	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		// TODO: IoC
		teamSelector = new TeamSelector();				
		
		// timer will be reused across games
		timer = new Timer() {			
			@Override
			public void run() {				
				if (timeLeft <= 0) {
					MainView.this.game.roundFailed();
					timeLeft = TIME_PER_ROUND;
				} else {
					tbTimer.setText(String.valueOf(timeLeft));
					timeLeft--;
				}
			}
		};
	}
	
	public void newGame() {
		newGameDlg.show();
	}
	
	public void newGame(final Game game){		
		this.game = game;		
		game.addListener(new GameListenerAdapter() {
			
			@Override
			public void beforeRound(GameRound round) {
				lbConcept.setText(game.getCurrentTask().getConcept());
				lbType.setText(translator.toString(game.getCurrentTask().getType()));
				lbCurrentPlayer.setText(game.getCurrentPlayer().toString());
				timeLeft = TIME_PER_ROUND;
				tbTimer.setText(String.valueOf(timeLeft));				
			}
			
			@Override
			public void gameOver() {
				endGame();
			}
		});
		
		timer.scheduleRepeating(1000);
		timerRunning = true;
						
		this.locale = GWT.create(Messages.class);
		this.translator = new Translator(locale);
		
		btnTimer.setText(locale.tokenPause());
		btnGuessed.setText(locale.tokenGuessed());
		btnFailed.setText(locale.tokenFailed());
		btnFailed.setEnabled(true);
		btnGuessed.setEnabled(true);
		
		
		teamSelector.setTeams(game.getTeams());
	}
	

	public void endGame() {
		pause();
		btnTimer.setText(locale.tokenNewGame());
		tbTimer.setText("--");
	}
	
	@UiHandler("btnGuessed")
	void onGuessed(ClickEvent e) {
		pause();
		final PopupPanel panel = new PopupPanel();
		panel.setSize("150px", "150px");
		panel.add(teamSelector);
		panel.setPopupPosition(0, 0);
		panel.show();
		addListeners(panel);
		teamSelector.reset();		
	}	
	
	@UiHandler("btnFailed")
	void onFail(ClickEvent e) {
		game.roundFailed();
	}
	
	@UiHandler("btnTimer")
	void onTimerBtn(ClickEvent e) {
		if (game.isInProgress()) {
			if (timerRunning) {
				pause();
			} else {
				unPause();
			}
		} else {
			newGameDlg.show();
		}
	}
	
	public void setNewGameDlg(NewGameDlgRenderer newGameDlg) {
		this.newGameDlg = newGameDlg;
	}

	private void unPause() {
		timer.scheduleRepeating(1000);
		timerRunning = true;
		btnTimer.setText(locale.tokenPause());
		btnGuessed.setEnabled(true);
		btnFailed.setEnabled(true);
	}

	private void pause() {
		timer.cancel();
		timerRunning = false;
		btnTimer.setText(locale.tokenContinue());
		btnFailed.setEnabled(false);
		btnGuessed.setEnabled(false);
	}
	
	private void addListeners(final PopupPanel panel) {
		teamSelector.btnCancel.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {				
				unPause();
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
}
