package net.sf.yat.dao;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertEquals;

import net.sf.yat.domain.TaskComplexity;

import org.junit.Test;

public class TextTaskDAOTest {
	private TaskDAO dao = new TextTaskDAO("src/test/java/net/sf/yat/dao/case1.txt");
	
	@Test
	public void shoudReturnNotNullTask() throws Exception{
		assertNotNull(dao.getTask(TaskComplexity.NORM));
	}
	
	@Test
	public void shoudReturnNotEmptyTask() {
		assertNotNull(dao.getTask(TaskComplexity.NORM).getConcept());
		assertNotNull(dao.getTask(TaskComplexity.NORM).getType());
	}
	
	@Test
	public void shoudNotReturnDuplicatesSubsequently() {
		assertNotSame(dao.getTask(TaskComplexity.NORM).getConcept(), dao.getTask(TaskComplexity.NORM).getConcept());
	}
	
	@Test
	public void shoudReturnTaskOfDesiredComplexity() {
		assertEquals(TaskComplexity.FOR_LINUX_FAN, dao.getTask(TaskComplexity.FOR_LINUX_FAN).getComplexity()); 
	}
}
