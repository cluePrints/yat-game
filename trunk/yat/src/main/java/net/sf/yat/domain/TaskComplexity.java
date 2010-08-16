package net.sf.yat.domain;

public enum TaskComplexity {
	// TODO: remove invisible to achieve lesser size?
	FOR_LINUX_FAN(false),	/*
						Calm down, guys - I've got Linux installed on my PC:)
						But spending 4 hours for making your USB drive work makes my IQ a bit less then terminally dumb:)
					*/
	FOR_TERMINALLY_DUMB(false),
	FOR_SPECTACULARLY_STUPID(false),
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
		return ordinal();
	}
	
}
