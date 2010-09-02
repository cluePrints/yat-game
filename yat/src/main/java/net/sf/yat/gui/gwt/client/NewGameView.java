package net.sf.yat.gui.gwt.client;

import java.util.LinkedList;
import java.util.List;

import net.sf.yat.domain.EndGameCriteria;
import net.sf.yat.domain.Game;
import net.sf.yat.domain.GameFactory;
import net.sf.yat.domain.Player;
import net.sf.yat.domain.TaskComplexity;
import net.sf.yat.domain.Team;
import net.sf.yat.domain.TeamScoredCriteria;
import net.sf.yat.domain.TotalGamesPlayedCriteria;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NewGameView extends Composite {

	private static final int TAB_PLAY = 4;

	private static final int TAB_DIFFICULTY = 2;

	private static final int MODE_POINTS = 1;

	private static final int MODE_GAMES = 0;

	private static NewGameViewUiBinder uiBinder = GWT
			.create(NewGameViewUiBinder.class);

	interface NewGameViewUiBinder extends UiBinder<Widget, NewGameView> {
	}
	
	@UiField
	VerticalPanel plTeams;
	
	@UiField
	VerticalPanel plPlayers;
	
	@UiField
	VerticalPanel plPlayerDifficulties;
		
	@UiField
	RadioButton rbModePoints;
	
	@UiField
	RadioButton rbModeGames;
	
	@UiField
	TextBox tbModeLimit;
	
	@UiField
	Button btnOk;
	
	@UiField
	TabLayoutPanel tabs;
	
	private Messages locale = GWT.create(Messages.class);
	private Translator translator = new Translator(locale);
	private GameFactory gameFactory;
	private int gameMode=0;
	
	private List<TaskComplexity> playerDesiredComplexity;
	
	public NewGameView(GameFactory gameFactory) {
		this.gameFactory = gameFactory;
		initWidget(uiBinder.createAndBindUi(this));
		
		tbModeLimit.addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {
				// TODO: annoying approach how to replace?
				boolean badValue = false;
				try {
					int val = Integer.parseInt(tbModeLimit.getText());
					if (val < 0 ) {
						badValue = true;
					}
					
				} catch (NumberFormatException ex) {
					badValue = true;
				}
				
				if (badValue) {
					tbModeLimit.setText("50");
				}
			}
		});
		
		
		rbModePoints.setText(locale.modePointsScored());
		rbModeGames.setText(locale.modeGamesPlayed());
		
		// Gui default state
		rbModePoints.setValue(true, true);
		onModePoints(null);
		onTeamAdd(null);
		onTeamAdd(null);
		onPlayerAdd(null);
		onPlayerAdd(null);
		onPlayerAdd(null);
		
		tabs.addSelectionHandler(new SelectionHandler<Integer>() {
			
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (tabs.getSelectedIndex() == TAB_DIFFICULTY || tabs.getSelectedIndex() == TAB_PLAY) {
					plPlayerDifficulties.clear();
					playerDesiredComplexity = new LinkedList<TaskComplexity>();
					int num = 0;
					for (final Player player : getPlayers()) {				
						Label label = new Label(player.getName());
						label.setWidth("50px");
						final ListBox list = new ListBox();
						for (TaskComplexity val : TaskComplexity.values()) {
							if (val.isVisible()) {
								list.addItem(translator.toString(val));								
								if (player.getDesiredComplexity() == val) {
									playerDesiredComplexity.add(val);
									list.setSelectedIndex(list.getItemCount()-1);
								}
							}
						}
						
						
						// TODO: ugly
						final int fNum = num;
						list.addChangeHandler(new ChangeHandler() {					
							@Override
							public void onChange(ChangeEvent event) {
								TaskComplexity complexity = TaskComplexity.values()[list.getSelectedIndex()];								
								playerDesiredComplexity.set(fNum, complexity);
							}
						});
						HorizontalPanel pl = new HorizontalPanel();
						pl.add(label);
						pl.add(list);		
						plPlayerDifficulties.add(pl);
						num++;
					}
				}
				
			}
		});
	}
	
	public Game getGame()
	{
		List<Player> players = getPlayers();
		List<String> teamNames = getTeams();
		shuffle(teamNames);
		shuffle(players);
		
		int playersPerTeam = players.size() / teamNames.size();
		int playersLeft = players.size() %  teamNames.size();
		
		LinkedList<Team> teams = new LinkedList<Team>();
		int usedPlayerNum=0;
		for (int i=0; i<teamNames.size(); i++) {
			List<Player> teamPlayers = new LinkedList<Player>();
			for (int j=0; j<playersPerTeam; j++) {
				teamPlayers.add(players.get(usedPlayerNum++));
			}
			if (i<playersLeft) {
				teamPlayers.add(players.get(usedPlayerNum++));
			}
			teams.add(new Team(teamPlayers, teamNames.get(i)));
		}
		
		int limit = Integer.parseInt(tbModeLimit.getText());
		
		EndGameCriteria criteria = null;
		if (gameMode == MODE_POINTS) {
			criteria = new TeamScoredCriteria(limit);
		} else {
			criteria = new TotalGamesPlayedCriteria(limit);			
		}
		gameFactory.setCriteria(criteria);
		
		return gameFactory.newInstance(teams);
	}
	
	
	private void shuffle(List<?> lst)
	{
		// TODO: implement shuffle
	}
	
	@UiHandler("rbModeGames")
	void onModeGames(ClickEvent evt)
	{
		gameMode = MODE_GAMES;
	}
	
	@UiHandler("rbModePoints")
	void onModePoints(ClickEvent evt)
	{
		gameMode = MODE_POINTS;
	}
	
	@UiHandler("btnNewTeam")
	void onTeamAdd(ClickEvent evt)
	{
		addEditor(plTeams, locale.tokenTeam());
	}
	
	@UiHandler("btnNewPlayer")
	void onPlayerAdd(ClickEvent evt)
	{
		addEditor(plPlayers, locale.tokenPlayer());
	}	
	
	private List<Player> getPlayers()
	{
		List<Player> players = new LinkedList<Player>();
		for (int i=0; i<plPlayers.getWidgetCount(); i++) {
			HorizontalPanel pl = (HorizontalPanel) plPlayers.getWidget(i);
			TextBox box = (TextBox) pl.getWidget(0);
			Player player = new Player(box.getText());
			if (playerDesiredComplexity.size() > 0) {
				player.setDesiredComplexity(playerDesiredComplexity.get(i));
			}
			players.add(player);
		}
		return players;
	}
	
	private List<String> getTeams()
	{
		List<String> teams = new LinkedList<String>();
		for (int i=0; i<plTeams.getWidgetCount(); i++) {
			HorizontalPanel pl = (HorizontalPanel) plTeams.getWidget(i);
			TextBox box = (TextBox) pl.getWidget(0);
			teams.add(box.getText());
		}
		return teams;
	}

	private void addEditor(final VerticalPanel ppp, String obj) {
		TextBox tb = new TextBox();
		tb.setWidth("80%");
		tb.setText(obj+"#"+(ppp.getWidgetCount()+1));
		Button btn = new Button("x");
		final HorizontalPanel pl = new HorizontalPanel();
		pl.add(tb);
		pl.add(btn);
		
		btn.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				ppp.remove(pl);
				updateStartGameState();		
			}
		});
		
		ppp.add(pl);
		
		updateStartGameState();
	}

	private void updateStartGameState() {
		btnOk.setEnabled(plTeams.getWidgetCount() > 1 && plTeams.getWidgetCount() <= plPlayers.getWidgetCount());
	}
}
