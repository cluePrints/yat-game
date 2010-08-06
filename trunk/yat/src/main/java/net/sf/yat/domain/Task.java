package net.sf.yat.domain;

public class Task {
	private final TaskType type;
	private final String concept;
	private final TaskComplexity complexity;
	public Task(TaskType type, String concept, TaskComplexity complexity) {
		super();
		this.type = type;
		this.concept = concept;
		this.complexity = complexity;
	}
	public TaskType getType() {
		return type;
	}
	public String getConcept() {
		return concept;
	}
	public TaskComplexity getComplexity() {
		return complexity;
	}	
	public int getPoints(){
		return complexity.ordinal();
	}
	
	
	@Override
	public String toString() {
		return type+"("+complexity+"): "+concept;
	}
}
