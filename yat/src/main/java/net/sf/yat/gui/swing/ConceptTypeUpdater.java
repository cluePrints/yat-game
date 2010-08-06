package net.sf.yat.gui.swing;

import javax.swing.JLabel;

import net.sf.yat.domain.GameListener;
import net.sf.yat.domain.GameRound;

public class ConceptTypeUpdater implements GameListener{
	private final JLabel concept;	
	private final JLabel type;
	public ConceptTypeUpdater(JLabel concept, JLabel type) {
		super();
		this.concept = concept;
		this.type = type;
	}
	
	@Override
	public void beforeRound(GameRound round) {
		concept.setText(round.getTask().getConcept());
		type.setText(round.getTask().getType().toString());
	}
	
	@Override
	public void afterRound(GameRound round) {
		// do nothing
	}
}
