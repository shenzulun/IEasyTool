/**
 * File Name: TaskDefinitionDTO.java
 * Date: 2019-08-16 15:34:07
 */
package me.belucky.easytool.task.dto;

/**
 * Description: 定时任务定义DTO
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public class TaskDefinitionDTO {
	/**
	 * 任务类型：1-定时任务  2-一次性
	 */
	private int taskType;
	/**
	 * 任务ID
	 */
	private String taskId;
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 任务实例
	 */
	private String taskClassPath;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 运行间隔
	 */
	private String interval;
	/**
	 * 首次运行日期
	 */
	private String firstExpectStart;
	/**
	 * 运行间隔
	 */
	private String intervalExpr;
	/**
	 * 最近运行日期
	 */
	private String lastRunDateStr;
	/**
	 * startTime为空时生效
	 */
	private String delay;
	
	public TaskDefinitionDTO(){}
	
	public TaskDefinitionDTO(String taskId, String taskName, String taskClassPath) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskClassPath = taskClassPath;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskClassPath() {
		return taskClassPath;
	}

	public void setTaskClassPath(String taskClassPath) {
		this.taskClassPath = taskClassPath;
	}

	public String getFirstExpectStart() {
		return firstExpectStart;
	}

	public void setFirstExpectStart(String firstExpectStart) {
		this.firstExpectStart = firstExpectStart;
	}

	public String getIntervalExpr() {
		return intervalExpr;
	}

	public void setIntervalExpr(String intervalExpr) {
		this.intervalExpr = intervalExpr;
	}

	public String getLastRunDateStr() {
		return lastRunDateStr == null ? "" : lastRunDateStr;
	}

	public void setLastRunDateStr(String lastRunDateStr) {
		this.lastRunDateStr = lastRunDateStr;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}
	
}
