package net.sf.yat.gui.gwt.client;

import java.util.LinkedList;
import java.util.List;

import net.sf.yat.domain.Game;
import net.sf.yat.domain.Player;
import net.sf.yat.domain.TaskComplexity;
import net.sf.yat.domain.Team;

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
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NewGameView extends Composite {

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
	Button btnOk;
	
	@UiField
	TabLayoutPanel tabs;
	
	private Messages locale = GWT.create(Messages.class);
	
	public NewGameView() {
		initWidget(uiBinder.createAndBindUi(this));		
		onTeamAdd(null);
		onPlayerAdd(null);
		
		tabs.addSelectionHandler(new SelectionHandler<Integer>() {
			
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (tabs.getSelectedIndex() == 2) {
					plPlayerDifficulties.clear();
					for (final Player player : getPlayers()) {				
						Label label = new Label(player.getName());
						label.setWidth("50px");
						final ListBox list = new ListBox();
						for (TaskComplexity val : TaskComplexity.values()) {
							if (val.isVisible()) {
								list.addItem(val.toString());
								if (player.getDesiredComplexity() == val) {
									list.setSelectedIndex(list.getItemCount()-1);
								}
							}
						}
						list.addChangeHandler(new ChangeHandler() {					
							@Override
							public void onChange(ChangeEvent event) {
								TaskComplexity complexity = TaskComplexity.valueOf(list.getItemText(list.getSelectedIndex()));								
								player.setDesiredComplexity(complexity);
							}
						});
						HorizontalPanel pl = new HorizontalPanel();
						pl.add(label);
						pl.add(list);		
						plPlayerDifficulties.add(pl);
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
		
		LinkedList<Team> turns = new LinkedList<Team>();
		for (int i=0; i<5; i++) {
			turns.addAll(teams);
		}
		
		return new Game(teams, turns, new HardCodedTaskDAO());
	}
	
	
	private void shuffle(List<?> lst)
	{
		// TODO: implement shuffle
	}
	
	@UiHandler("btnNewTeam")
	void onTeamAdd(ClickEvent evt)
	{
		addEditor(plTeams, "Team");
	}
	
	@UiHandler("btnNewPlayer")
	void onPlayerAdd(ClickEvent evt)
	{
		addEditor(plPlayers, "Player");
	}
	
		
	
	private List<Player> getPlayers()
	{
		List<Player> players = new LinkedList<Player>();
		for (int i=0; i<plPlayers.getWidgetCount(); i++) {
			HorizontalPanel pl = (HorizontalPanel) plPlayers.getWidget(i);
			TextBox box = (TextBox) pl.getWidget(0);
			players.add(new Player(box.getText()));
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
