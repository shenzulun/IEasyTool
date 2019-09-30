/**
 * File Name: TimeTaskDemo.java
 * Date: 2019-09-29 17:14:57
 */
package me.belucky.easytool.task.impl;

import me.belucky.easytool.task.AbstractTask;

/**
 * Description: 定时任务的Demo
 * @author shenzulun
 * @date 2019-09-29
 * @version 1.0
 */
public class TimeTaskDemo extends AbstractTask{

	public TimeTaskDemo(String taskName) {
		super(taskName);
	}

	@Override
	public void execute() {
		log.info("hello timer task...");
	}

}
