package net.sf.yat.dao;

import net.sf.yat.domain.Task;
import net.sf.yat.domain.TaskComplexity;
import net.sf.yat.domain.TaskProvider;

// TODO: make it some kind of session bound to omit repeating tasks
// TODO: localisation here or on the task leveL?
public interface TaskDAO extends TaskProvider{
	public Task getTask(TaskComplexity c);
}
