package net.sf.yat.domain;

/**
 * Simple interface, which actually is copied by TaskDAO currently. 
 * Separate version is here to make domain package self dependent.
 */
public interface TaskProvider {
	public Task getTask();
}
