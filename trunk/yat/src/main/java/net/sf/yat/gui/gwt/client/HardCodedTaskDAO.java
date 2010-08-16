package net.sf.yat.gui.gwt.client;

import static net.sf.yat.domain.TaskComplexity.HIGH;
import static net.sf.yat.domain.TaskComplexity.LOW;
import static net.sf.yat.domain.TaskComplexity.NORM;
import static net.sf.yat.domain.TaskType.DRAW;
import static net.sf.yat.domain.TaskType.HANDPLAY;
import static net.sf.yat.domain.TaskType.VERBAL;

import java.util.Arrays;
import java.util.List;

import net.sf.yat.domain.Task;
import net.sf.yat.domain.TaskComplexity;
import net.sf.yat.domain.TaskProvider;

/**
 * Temporarily solution, suitable for web
 */
public class HardCodedTaskDAO implements TaskProvider {
	List<Task> tasks = Arrays.asList(//
			new Task(HANDPLAY, "Apple*", LOW),//
			new Task(VERBAL, "Table**", NORM), //
			new Task(DRAW, "Table***", HIGH),//
			new Task(HANDPLAY, "Flower*", LOW),//
			new Task(VERBAL, "Rock**", NORM), //
			new Task(DRAW, "Jaws***", HIGH));
	int num = 0;

	@Override
	public Task getTask(TaskComplexity c) {
		Task result = null;
		do {
			result = tasks.get(num++ % tasks.size());
		} while (result.getComplexity() != c);
		return result;
	}
}
