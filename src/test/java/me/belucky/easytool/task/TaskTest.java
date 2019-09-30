/**
 * File Name: TaskTest.java
 * Date: 2019-09-29 17:44:30
 */
package me.belucky.easytool.task;

import org.junit.Before;
import org.junit.Test;

/**
 * Description: 任务测试类
 * @author shenzulun
 * @date 2019-09-29
 * @version 1.0
 */
public class TaskTest {
	
	@Before
	public void before() throws Exception {
		TaskInitCenter.go();
	}
	
//	@Test
	public void test0() throws Exception {
		TaskInitCenter.go();
		while(true) {
			
		}
	}
	
	@Test
	public void testRunTask() throws Exception {
		TaskInitCenter.runTask("InstantTaskDemo");
	}

}
