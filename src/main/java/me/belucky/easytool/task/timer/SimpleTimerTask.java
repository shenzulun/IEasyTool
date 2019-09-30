/**
 * File Name: SimpleTimerTask.java
 * Date: 2019-08-16 15:43:41
 */
package me.belucky.easytool.task.timer;

import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import me.belucky.easytool.task.AbstractTask;
import me.belucky.easytool.task.TaskInitCenter;
import me.belucky.easytool.util.DateTimeUtils;

/**
 * Description: 简单定时任务
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public class SimpleTimerTask extends TimerTask{
	protected Logger log = LoggerFactory.getLogger(super.getClass());
	protected AbstractTask task;
	
	protected String taskId;
	
	protected Object obj; 
	
	protected String logLevel = "INFO";
	
	public SimpleTimerTask(AbstractTask task){	
		super();
		this.task = task;
	}
		
	public void run(){
		if("DEBUG".equals(logLevel.toUpperCase())){
			log.debug("开始执行任务-> {}", getTaskName());
			log.debug("当前时间,[{}]", DateTimeUtils.getDateTimeNow());	
		}else{
			log.info("开始执行任务-> {}", getTaskName());
			log.info("当前时间,[{}]", DateTimeUtils.getDateTimeNow());	
		}
		task.execute();
		TaskInitCenter.refreshTaskStatus(taskId);
	}
	
	public SimpleTimerTask setObj(Object obj){
		this.obj = obj;
		return this;
	}
	
	public SimpleTimerTask setLogLevelDebug(){
		this.logLevel = "DEBUG";
		return this;
	}
	
	public String getTaskName() {
		return task.getTaskName();
	}
	
	public void setTaskName(String taskName) {
		task.setTaskName(taskName);
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}