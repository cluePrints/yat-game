package net.sf.yat.domain;

/**
 * Participating player 
 */
public class Player {
	private final String name;

	public Player(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
