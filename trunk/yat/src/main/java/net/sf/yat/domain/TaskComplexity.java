package net.sf.yat.domain;

public enum TaskComplexity {
	LOW,
	NORM,
	HIGH;
	private final boolean visible;
	private TaskComplexity(boolean b) {
		visible = b;
	}
	private TaskComplexity() {
		this(true);
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public int getPoints()
	{
		return ordinal()+3;
	}
	
}
