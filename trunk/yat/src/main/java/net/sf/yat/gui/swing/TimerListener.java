package net.sf.yat.gui.swing;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.sf.yat.domain.GameListener;
import net.sf.yat.domain.GameRound;

public class TimerListener implements GameListener{
	public static final int ROUND_TIME = 180;
	private JTabbedPane pane;
	private JTextField tfTimeLeft;
	private JProgressBar pbTimeLeft;
	private int index;
	private Timer timer = new Timer(true);
	private TimerTask task;
	private int timeLeft;
	public TimerListener(JTabbedPane pane, int index, JTextField tfTimeLeft, JProgressBar pbTimeLeft) {
		super();
		this.pane = pane;
		this.index = index;
		this.tfTimeLeft = tfTimeLeft;
		this.pbTimeLeft = pbTimeLeft;
	}
	
	@Override
	public synchronized void beforeRound(GameRound round) {
		pane.setSelectedIndex(index);
		
		timeLeft = ROUND_TIME;
		pbTimeLeft.setMaximum(timeLeft);
		pbTimeLeft.setMinimum(0);
		if (task != null) {
			task.cancel();
		}
		task = new TimerTask() {			
			@Override
			public void run() {
				timeLeft--; 
				pbTimeLeft.setValue(timeLeft);
				tfTimeLeft.setText(String.valueOf(timeLeft));
			}
		};
		timer.schedule(task, 0, 1000);
	}
	
	@Override
	public synchronized void afterRound(GameRound round) {
		if (task != null) {
			task.cancel();
			task = null;
		}
	}
}
