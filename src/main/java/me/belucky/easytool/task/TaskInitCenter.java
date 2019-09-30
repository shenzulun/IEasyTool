/**
 * File Name: TaskInitCenter.java
 * Date: 2017-5-25 下午04:59:37
 */
package me.belucky.easytool.task;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import me.belucky.easytool.config.ConstCode;
import me.belucky.easytool.task.dto.TaskDefinitionDTO;
import me.belucky.easytool.task.timer.SimpleTimerTask;
import me.belucky.easytool.task.timer.SimpleTimerTaskFactory;
import me.belucky.easytool.util.DateTimeUtils;
import me.belucky.easytool.util.ParserUtils;

/**
 * 功能说明: 任务初始化中心
 * @author shenzl
 * @date 2017-5-25
 * @version 1.0
 */
public final class TaskInitCenter {
	protected static Logger log = LoggerFactory.getLogger(TaskInitCenter.class);
	/**
	  * 任务初始化标志
	 */
	private static volatile boolean taskInitFlag = false;         
	/**
	 * 定时任务清单
	 */
	private static List<TaskDefinitionDTO> taskList = new ArrayList<TaskDefinitionDTO>();
	/**
	 * 定时任务MAP
	 */
	private static Map<String, TaskDefinitionDTO> taskMap = new ConcurrentHashMap<String, TaskDefinitionDTO>();
	
	/**
	 * 任务中心初始化
	 * @throws Exception 
	 */
	public static void go() throws Exception{	
		try{
			if(!taskInitFlag){
				//只能初始化一次
				taskInitFlag = true;
				taskInit();
			}
			for(TaskDefinitionDTO taskDTO : taskList){
				Class<?> cls = Class.forName(taskDTO.getTaskClassPath());
				int taskType = taskDTO.getTaskType();
				Constructor<?> constructor = cls.getConstructor(String.class);
				AbstractTask task = (AbstractTask) constructor.newInstance(taskDTO.getTaskName());
				if(taskType == ConstCode.TASK_TYPE_INSTANT) {
					//即时任务
					task.go();
				}else if(taskType == ConstCode.TASK_TYPE_TIMER) {
					//定时任务
					SimpleTimerTask simpleTask = new SimpleTimerTask(task);
					simpleTask.setTaskId(taskDTO.getTaskId());
					timingTask(taskDTO, simpleTask);
				}
			}
		}catch(Exception e){
			log.error("任务批量初始化失败",e);
			throw e;
		}
	}
	
	/**
	 * 定时任务
	 * @param propName
	 * @param cls
	 * @param taskName
	 * @throws Exception 
	 */
	private static <T extends SimpleTimerTask> void timingTask(TaskDefinitionDTO taskDTO, T task) throws Exception{
		String interval = taskDTO.getInterval();
		String startTime = taskDTO.getStartTime();
		String oldTaskName = task.getTaskName();
		/**
		 * startTime有2种形式：
		 * 16:57:00
		 * ${W3|22:50:00},{W6|12:30:00}
		 */
		if(startTime.startsWith("{")){
			String[] customStartArr = startTime.split(",");
			int cnt = 0;
			StringBuffer buff = new StringBuffer();
			for(String st : customStartArr){
				Date expectDate = ParserUtils.stringToDate(st);
				task.setTaskName(oldTaskName + ++cnt);
				SimpleTimerTaskFactory.schedule(task, DateTimeUtils.getDelay(expectDate), ParserUtils.stringToLong(interval),DateTimeUtils.getDateTime(expectDate));
				if(cnt != 1){
					buff.append(" | ");
				}
				buff.append(DateTimeUtils.getDateTime(expectDate));
			}
			taskDTO.setFirstExpectStart(buff.toString());
		}else{
			long delay = DateTimeUtils.getDelay(startTime);
			SimpleTimerTaskFactory.schedule(task, delay, ParserUtils.stringToLong(interval),startTime);
			taskDTO.setFirstExpectStart(DateTimeUtils.getDate(new Date()) + " " + startTime);
		}
		taskDTO.setIntervalExpr(interval);
		taskMap.put(taskDTO.getTaskId(), taskDTO);
	}
	
	/**
	 * 解析定时任务的配置
	 * @throws DocumentException 
	 */
	private static void taskInit() throws Exception{
		SAXReader saxReader = new SAXReader();
		InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream("task-init-config.xml");
		Document document;
		try {
			document = saxReader.read(ins);
			Element root = document.getRootElement();		
			String defaultClasspath = root.element("default-classpath").getTextTrim();
			List<Element> childList = root.elements("task");		
			for(Element e : childList){
				String id = e.attributeValue("id");
				String classpath = defaultClasspath;
				String classpathEle = e.attributeValue("classpath");
				if(classpathEle != null){
					classpath = classpathEle;
				}
				String title = e.attributeValue("title");
				String className = id;
				TaskDefinitionDTO taskDTO = new TaskDefinitionDTO(id,title,classpath + "." + className);
				String taskType = e.attributeValue("type");
				if(taskType == null) {
					taskType = ConstCode.TASK_TYPE_INSTANT + "";
				}
				taskDTO.setTaskType(Integer.valueOf(taskType));
				taskDTO.setStartTime(e.attributeValue("startTime"));
				taskDTO.setInterval(e.attributeValue("interval"));
				taskList.add(taskDTO);
				if(taskMap.containsKey(id)) {
					throw new Exception("task-init-config.xml内存在ID相同的task: " + id) ;
				}
				taskMap.put(id, taskDTO);
			}
		} catch (DocumentException e) {
			log.error("task-init-config.xml解析出错", e);	
			throw e;
		}
	}
	
	/**
	 * 运行指定任务
	 * @param taskId
	 * @throws Exception 
	 */
	public static void runTask(String taskId) throws Exception{
		TaskDefinitionDTO taskDTO = taskMap.get(taskId);
		runTask(taskDTO);
	}
	
	/**
	 * 运行任务
	 * @param taskDTO
	 * @throws Exception 
	 */
	public static void runTask(TaskDefinitionDTO taskDTO) throws Exception {
		Class<?> cls;
		try {
			cls = Class.forName(taskDTO.getTaskClassPath());
			int taskType = taskDTO.getTaskType();
			Constructor<?> constructor = cls.getConstructor(String.class);
			AbstractTask task = (AbstractTask) constructor.newInstance(taskDTO.getTaskName());
			if(taskType == ConstCode.TASK_TYPE_INSTANT) {
				//即时任务
				task.go();
			}else if(taskType == ConstCode.TASK_TYPE_TIMER) {
				SimpleTimerTask simpleTask = new SimpleTimerTask(task);
				simpleTask.setTaskId(taskDTO.getTaskId());
				//不能thread方式运行,不然取不到日志
				simpleTask.run();   
			}
		} catch (Exception e) {
			log.error("任务手动执行失败",e);
			throw e;
		}
	}

	public static List<TaskDefinitionDTO> getTaskList() {
		return taskList;
	}

	public static Map<String, TaskDefinitionDTO> getTaskMap() {
		return taskMap;
	}
	
	/**
	 * 刷新任务状态
	 * @param taskId
	 */
	public synchronized static void refreshTaskStatus(String taskId){
		if(taskId == null || "".equals(taskId)){
			return;
		}
		for(TaskDefinitionDTO taskDTO : taskList){
			if(taskId.equals(taskDTO.getTaskId())){
				taskDTO.setLastRunDateStr(DateTimeUtils.getDateTimeNow());
				break;
			}
		}
		TaskDefinitionDTO taskDTO = taskMap.get(taskId);
		if(taskDTO != null){
			taskDTO.setLastRunDateStr(DateTimeUtils.getDateTimeNow());
		}
	}
	
}
