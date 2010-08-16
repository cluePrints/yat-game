package net.sf.yat.domain;

/**
 * Participating player 
 */
public class Player {
	private final String name;
	private TaskComplexity desiredComplexity = TaskComplexity.NORM;

	public Player(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}	

	public TaskComplexity getDesiredComplexity() {
		return desiredComplexity;
	}

	public void setDesiredComplexity(TaskComplexity desiredComplexity) {
		this.desiredComplexity = desiredComplexity;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
