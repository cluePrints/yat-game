package net.sf.yat.gui.gwt.client;

import net.sf.yat.domain.TaskComplexity;
import net.sf.yat.domain.TaskType;

public class Translator {
	private Messages messages;

	public Translator(Messages messages) {
		super();
		this.messages = messages;
	}
	
	public String toString(TaskType type) {
		switch (type) {
			case VERBAL: return messages.tokenVerbal();
			case HANDPLAY: return messages.tokenHandplay();
			case DRAW: return messages.tokenDraw();
		}
		return "x_x";
	}
	
	public String toString(TaskComplexity type) {
		switch (type) {
			case LOW: return messages.difficultyLow();
			case NORM: return messages.difficultyMedium();
			case HIGH: return messages.difficultyHigh();
		}
		return "x_x";
	}
}
