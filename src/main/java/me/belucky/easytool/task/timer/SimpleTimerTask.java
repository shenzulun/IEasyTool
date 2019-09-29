/**
 * File Name: SimpleTimerTask.java
 * Date: 2019-08-16 15:43:41
 */
package me.belucky.easytool.task.timer;

import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import me.belucky.easytool.task.TaskInitCenter;
import me.belucky.easytool.util.DateTimeUtils;


/**
 * Description: 简单定时任务
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public abstract class SimpleTimerTask extends TimerTask{
	protected Logger log = LoggerFactory.getLogger(super.getClass());
	protected String taskName;
	
	protected String taskId;
	
	protected Object obj; 
	
	protected String logLevel = "INFO";
	
	public SimpleTimerTask(String taskName){	
		super();
		this.taskName = taskName;
//		log.info("任务-["+taskName+"]运行中......");
	}
	
	public abstract void execute();
	
	public void run(){
		if("DEBUG".equals(logLevel.toUpperCase())){
			log.debug("开始执行任务-> {}", taskName);
			log.debug("当前时间,[{}]", DateTimeUtils.getDateTimeNow());	
		}else{
			log.info("开始执行任务-> {}", taskName);
			log.info("当前时间,[{}]", DateTimeUtils.getDateTimeNow());	
		}
		execute();
		TaskInitCenter.refreshTaskStatus(taskId);
	}
	
	public SimpleTimerTask setObj(Object obj){
		this.obj = obj;
		return this;
	}
	
	public SimpleTimerTask setLogLevelDebug(){
		logLevel = "DEBUG";
		return this;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}