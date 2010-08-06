package net.sf.yat.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.sf.yat.domain.Task;
import net.sf.yat.domain.TaskComplexity;
import net.sf.yat.domain.TaskType;
import net.sf.yat.util.Unchecked;

public class TextTaskDAO implements TaskDAO {
	private final String fileName;
	
	public TextTaskDAO(String fileName) {
		super();
		this.fileName = fileName;
	}

	@Override
	public Task getTask() {
		BufferedReader reader = null;
		List<Task> tasks = new LinkedList<Task>();
		try {
			reader = new BufferedReader(new FileReader(new File(
					fileName)));
			String line = null;
			do {
				line = reader.readLine();
				if (line != null) {
					String[] parts = line.split(";");				
					int nComplexity = Integer.parseInt(parts[0]);
					int nType = Integer.parseInt(parts[1]);
					String concept = parts[2];
					TaskComplexity compexity = TaskComplexity.values()[nComplexity];
					TaskType type = TaskType.values()[nType];
					
					Task task = new Task(type, concept, compexity);
					tasks.add(task);	
				}
			} while (line != null);
			Collections.shuffle(tasks);
			return tasks.get(0);
		} catch (IOException ex) {
			Unchecked.chuck(ex);
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
					Unchecked.chuck(ex);
				}
			}
		}
	}
}
