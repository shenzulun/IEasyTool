/**
 * File Name: ConstCode.java
 * Date: 2019-09-29 16:24:28
 */
package me.belucky.easytool.config;

/**
 * Description: 常量类
 * @author shenzulun
 * @date 2019-09-29
 * @version 1.0
 */
public class ConstCode {
	/**
 	 * 空白字符串
 	 */
 	public static final String BLANK_STRING = "";
 	/**
 	 * 换行符
 	 */
 	public static final String LINE_BREAK = System.getProperty("line.separator","\n");
 	/**
 	 * 行内分隔符  |
 	 */
 	public static final String LINE_SEPARATOR = "|";
 	/**
 	 * 任务类型-定时任务
 	 */
 	public static final int TASK_TYPE_TIMER = 1;
 	/**
 	 * 任务类型-即时任务
 	 */
 	public static final int TASK_TYPE_INSTANT = 2;
 	/**
 	 * 默认的文件字符集
 	 */
 	public static final String DEFAULT_FILE_ENCODE = "UTF-8";
}
