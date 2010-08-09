package net.sf.yat.gui.gwt.client;

import static net.sf.yat.domain.TaskComplexity.HIGH;
import static net.sf.yat.domain.TaskComplexity.LOW;
import static net.sf.yat.domain.TaskComplexity.NORMAL;
import static net.sf.yat.domain.TaskType.DRAW;
import static net.sf.yat.domain.TaskType.HANDPLAY;
import static net.sf.yat.domain.TaskType.VERBAL;

import java.util.Arrays;
import java.util.List;

import net.sf.yat.domain.Task;
import net.sf.yat.domain.TaskProvider;

/**
 * Temporarily solution, suitable for web
 */
public class HardCodedTaskDAO implements TaskProvider {
	List<Task> tasks = Arrays.asList(//
			new Task(HANDPLAY, "Apple", LOW),//
			new Task(VERBAL, "Table", NORMAL), //
			new Task(DRAW, "Table", HIGH));
	int num = 0;

	@Override
	public Task getTask() {
		// TODO: Collections.shuffle(tasks);
		return tasks.get(num++ % tasks.size());
	}
}
