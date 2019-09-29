/**
 * File Name: InstantTaskDemo.java
 * Date: 2019-09-29 17:17:05
 */
package me.belucky.easytool.task.impl;

import me.belucky.easytool.task.AbstractTask;

/**
 * Description: 即时任务的Demo
 * @author shenzulun
 * @date 2019-09-29
 * @version 1.0
 */
public class InstantTaskDemo extends AbstractTask{

	public InstantTaskDemo(String taskName) {
		super(taskName);
	}

	@Override
	public void execute() {
		log.info("hello instant task...");
	}

}
