package net.sf.yat.gui.gwt.client;

import java.util.List;

import net.sf.yat.domain.Player;
import net.sf.yat.domain.Team;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class TeamSelector extends Composite {

	private static TeamSelectorUiBinder uiBinder = GWT
			.create(TeamSelectorUiBinder.class);

	interface TeamSelectorUiBinder extends UiBinder<Widget, TeamSelector> {
	}
	
	private List<Team> teams;

	@UiField
	Button btnOk;
	
	@UiField
	Button btnCancel;
	
	@UiField
	ListBox lstTeams;
	
	@UiField
	ListBox lstPlayers;

	public TeamSelector() {
		initWidget(uiBinder.createAndBindUi(this));
	}	
	
	public void setTeams(List<Team> teams) {
		this.teams = teams;
		lstTeams.clear();
		for (Team team : teams) {
			lstTeams.addItem(team.getName());
		}
		reset();
	}

	public void reset() {
		btnOk.setEnabled(false);
		lstTeams.setSelectedIndex(-1);
		lstPlayers.clear();
	}			
	
	@UiHandler("lstTeams")
	void onTeamChange(ChangeEvent evt) {
		Team currTeam = getSelectedTeam();
		lstPlayers.clear();
		for (Player player : currTeam.getPlayers()) {
			lstPlayers.addItem(player.getName());
		}
		lstPlayers.setSelectedIndex(-1);
	}
	
	@UiHandler("lstPlayers")
	void onPlayerChange(ChangeEvent evt) {
		btnOk.setEnabled(getSelectedPlayerNum() >= 0);
	}
	
	public Player getSelectedPlayer()
	{
		Team currTeam = getSelectedTeam();
		int num = getSelectedPlayerNum();
		Player currPlayer = currTeam.getPlayers().get(num);
		return currPlayer;
	}

	public int getSelectedPlayerNum() {
		return lstPlayers.getSelectedIndex();
	}

	public Team getSelectedTeam() {
		int num = getSelectedTeamNum();
		Team currTeam = teams.get(num);
		return currTeam;
	}

	public int getSelectedTeamNum() {
		return lstTeams.getSelectedIndex();
	}
}
