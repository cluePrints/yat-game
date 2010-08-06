package net.sf.yat.gui.swing;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import net.sf.yat.domain.Game;
import net.sf.yat.domain.Player;
import net.sf.yat.domain.Team;

@SuppressWarnings("serial")
public class GameGui extends JFrame {
	Map<Team, JPanel> teamPanes = new HashMap<Team, JPanel>();

	public GameGui() {
		final Game game = generateGame(5, 5, 3);
		setLayout(new GridLayout(2, 2));
		JPanel topLeftPanel = new JPanel();
		topLeftPanel.setLayout(new GridLayout(1, 2));
		JLabel btnTaskType = createBorderedLabel();
		topLeftPanel.add(btnTaskType);
		JLabel btnConcept = createBorderedLabel();
		game.addListener(new ConceptTypeUpdater(btnConcept, btnTaskType));
		topLeftPanel.add(btnConcept);
		JPanel topRightPanel = new JPanel();
		topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.Y_AXIS));
		topRightPanel.add(new JLabel("Turns to play"));
		JList lst = new JList(new TurnsToPlayListModel(game));
		lst.setSize(200, 250);
		topRightPanel.add(lst);
		
		
		JPanel pnlPlayedHistory = new JPanel();
		pnlPlayedHistory.setLayout(new BoxLayout(pnlPlayedHistory, BoxLayout.Y_AXIS));
		pnlPlayedHistory.add(new JLabel("Turns played"));
		JList lst2 = new JList(new RoundsPlayedListModel(game));
		lst2.setSize(200, 250);
		pnlPlayedHistory.add(lst2);
		
		JList scores = new JList(new ScoreListModel(game));
		
		JTabbedPane bottomRightPanel = new JTabbedPane();
		bottomRightPanel.add("Scores", scores);
		bottomRightPanel.add("History", pnlPlayedHistory);		
		
		
		
		JTabbedPane pane = new JTabbedPane();
		CurrentPlayerSelector selector = new CurrentPlayerSelector(pane);
		game.addListener(selector);
		int tabNum=0;
		for (Team team : game.getTeams()) {
			JPanel teamFrame = new JPanel();
			pane.add(team.getName(), teamFrame);

			teamFrame.setLayout(new GridLayout(team.getPlayers().size(), 1));
			for (Player player : team.getPlayers()) {
				JPanel panel = new JPanel();
				JLabel playerLabel = new JLabel(player.getName());
				playerLabel.setForeground(Color.DARK_GRAY);
				selector.addPlayer(player, tabNum, playerLabel);
				panel.add(playerLabel);			
				JButton answerBtn = new JButton(new MarkedCheckBoxIcon());
				final int teamNum = game.getTeams().indexOf(team);
				final int playerNum = team.getPlayers().indexOf(player);
				answerBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						game.roundWon(teamNum, playerNum);
					}
				});
				panel.add(answerBtn);
				teamFrame.add(panel);
			}
			tabNum++;
		}
		add(topLeftPanel); 	add(topRightPanel);		
		add(pane);		   	add(bottomRightPanel);
		setTitle("Ѣ-game prototype");
		setSize(600, 500);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GameGui.this.dispose();
			}
		});
		
		game.start();
	}

	private JLabel createBorderedLabel() {
		JLabel btnTaskType = new JLabel();
		btnTaskType.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		btnTaskType.setHorizontalTextPosition(SwingConstants.CENTER);
		return btnTaskType;
	}

	private Game generateGame(int teamNum, int playersNum, int gamesNum) {
		List<Team> teams = new LinkedList<Team>();
		for (int j = 1; j <= teamNum; j++) {
			List<Player> players = new LinkedList<Player>();
			for (int i = 1; i <= playersNum; i++) {
				players.add(new Player("Player #" + j + "" + i));
			}
			Team team = new Team(players, "Team #" + j);
			teams.add(team);
		}

		List<Team> turnsToPlay = new LinkedList<Team>();
		for (int i = 0; i < gamesNum; i++) {
			turnsToPlay.addAll(teams);
		}
		Game game = Game.newInstance(teams);		
		return game;
	}
}
