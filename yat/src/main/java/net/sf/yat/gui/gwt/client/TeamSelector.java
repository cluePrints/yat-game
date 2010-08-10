package net.sf.yat.gui.gwt.client;

import java.util.List;

import net.sf.yat.domain.Player;
import net.sf.yat.domain.Team;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
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
		lstTeams.setSelectedIndex(-1);
	}

	@UiHandler("btnOk")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}
	
	@UiHandler("lstTeams")
	void onChange(ChangeEvent evt) {
		int num = lstTeams.getSelectedIndex();
		Team currTeam = teams.get(num);
		lstPlayers.clear();
		for (Player player : currTeam.getPlayers()) {
			lstPlayers.addItem(player.getName());
		}
	}
}
