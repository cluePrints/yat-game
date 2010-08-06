package net.sf.yat.dao;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

import org.junit.Test;

public class TextTaskDAOTest {
	private TaskDAO dao = new TextTaskDAO("src/test/java/net/sf/yat/dao/case1.txt");
	
	@Test
	public void shoudReturnNotNullTask() throws Exception{
		assertNotNull(dao.getTask());
	}
	
	@Test
	public void shoudReturnNotEmptyTask() {
		assertNotNull(dao.getTask().getConcept());
		assertNotNull(dao.getTask().getType());
	}
	
	@Test
	public void shoudNotReturnDuplicatesSubsequently() {
		assertNotSame(dao.getTask().getConcept(), dao.getTask().getConcept());
	}
}
