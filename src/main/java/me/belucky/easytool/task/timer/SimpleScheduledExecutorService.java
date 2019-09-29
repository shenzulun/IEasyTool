/**
 * File Name: SimpleScheduledExecutorService.java
 * Date: 2019-08-16 15:48:47
 */
package me.belucky.easytool.task.timer;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.belucky.easytool.util.DateTimeUtils;

/**
 * Description: 
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public class SimpleScheduledExecutorService {
	protected static Logger log = LoggerFactory.getLogger(SimpleScheduledExecutorService.class);
	private ScheduledExecutorService executorService;
	
	public SimpleScheduledExecutorService(){
		executorService = Executors.newScheduledThreadPool(64); 
		log.info("任务池启动......");
		log.info("当前时间,[{}]", DateTimeUtils.getDateTimeNow());	
	}
	
	public SimpleScheduledExecutorService(int corePoolSize){
		executorService = Executors.newScheduledThreadPool(corePoolSize); 
		log.info("任务池启动......");
		log.info("当前时间,[{}]", DateTimeUtils.getDateTimeNow());	
	}
	
	public Future<?> schedule(String taskName, TimerTask task, long initialDelay,long period,String expectStart){
		log.info("定时任务[{}]初始化成功...", taskName);
		log.info("预期执行日期,[" + expectStart + "]");
		return executorService.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);
	}
	
	public Future<?> schedule(TimerTask task, long initialDelay,long period){
		return executorService.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);
	}
}
