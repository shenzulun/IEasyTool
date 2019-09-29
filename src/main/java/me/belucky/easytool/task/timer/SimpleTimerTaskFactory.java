/**
 * File Name: SimpleTimerTaskFactory.java
 * Date: 2019-08-16 15:49:57
 */
package me.belucky.easytool.task.timer;

import java.util.TimerTask;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 简单定时任务工厂
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public class SimpleTimerTaskFactory {
	protected static Logger log = LoggerFactory.getLogger(SimpleTimerTaskFactory.class);
	private static SimpleScheduledExecutorService executorService = new SimpleScheduledExecutorService(64);
	
	public static Future<?> submit(TimerTask task, long initialDelay,long period){
		return executorService.schedule(task, initialDelay, period);
	}
	
	public static Future<?> schedule(SimpleTimerTask task, long initialDelay,long period,String expectStart){
		return executorService.schedule(task.getTaskName(), task, initialDelay, period, expectStart);
	}

	public static SimpleScheduledExecutorService getExecutorService() {
		return executorService;
	}
}
