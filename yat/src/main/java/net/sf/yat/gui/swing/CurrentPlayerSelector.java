package net.sf.yat.gui.swing;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import net.sf.yat.domain.GameListenerAdapter;
import net.sf.yat.domain.GameRound;
import net.sf.yat.domain.Player;

public class CurrentPlayerSelector extends GameListenerAdapter {
	private Map<Player, JLabel> playerLabels = new HashMap<Player, JLabel>();
	private Map<Player, Integer> playerTabs = new HashMap<Player, Integer>();
	private JTabbedPane pane;

	public CurrentPlayerSelector(JTabbedPane pane) {
		super();
		this.pane = pane;
	}

	public void addPlayer(Player player, int tabNum, JLabel label) {
		playerLabels.put(player, label);
		playerTabs.put(player, tabNum);
	}

	@Override
	public void beforeRound(GameRound round) {		
		JLabel label = playerLabels.get(round.getPlayingPlayer());
		label.setForeground(Color.BLUE);
		
		pane.setSelectedIndex(playerTabs.get(round.getPlayingPlayer()));
	}

	@Override
	public void afterRound(GameRound round) {
		JLabel label = playerLabels.get(round.getPlayingPlayer());
		label.setForeground(Color.DARK_GRAY);	
	}
}
