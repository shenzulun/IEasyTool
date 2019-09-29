/**
 * File Name: LogFileTailerListener.java
 * Date: 2016-7-1 下午03:36:55
 */
package me.belucky.easytool.tail;

/**
 * 功能说明:
 * @author Steven Haines
 * @date 2016-7-1
 * @version 1.0
 */
public interface LogFileTailerListener {
	
	/**
    * A new line has been added to the tailed log file
    * 
    * @param line   The new line that has been added to the tailed log file
    */
	public void newLogFileLine(String line);
}
