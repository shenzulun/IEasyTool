/**
 * File Name: Tail.java
 * Date: 2016-7-1 下午03:41:56
 */
package me.belucky.easytool.tail;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.websocket.Session;


/**
 * 功能说明:
 * @author Steven Haines
 * @date 2016-7-1
 * @version 1.0
 */
public class Tail implements LogFileTailerListener{
	/**
	* The log file tailer
	*/
	private LogFileTailer tailer;
	
	private String token;
	
	private StringBuffer buffer = new StringBuffer();
	
	private Session session;

	/**
	* Creates a new Tail instance to follow the specified file
	*/
	public Tail(String filename, String token) {
		this.token = token;
		tailer = new LogFileTailer(new File(filename), 1000, false);
		tailer.addLogFileTailerListener(this);
		tailer.start();
	}
	
	public Tail(String filename, StringBuffer buffer) {
		this.buffer = buffer;
		tailer = new LogFileTailer(new File(filename), 1000, false);
		tailer.addLogFileTailerListener(this);
		tailer.start();
	}
	
	public Tail(String filename, Session session) {
		this.session = session;
		tailer = new LogFileTailer(new File(filename), 1000, false);
		tailer.addLogFileTailerListener(this);
		tailer.start();
	}
	
	/**
	* Creates a new Tail instance to follow the specified file
	*/
	public Tail(String filename) {
		this(filename,"");
	}

	/**
	* A new line has been added to the tailed log file
	*
	* @param line The new line that has been added to the tailed log file
	*/
	public void newLogFileLine(String line) {
		//buffer.append(line).append("<br/>");
		try {
			session.getBasicRemote().sendText(line + "<br>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	* Command-line launcher
	*/
	public static void main(String[] args) {
		String fileName = "D:\\log\\java\\out.log";
		Tail tail = new Tail(fileName,UUID.randomUUID().toString());
		System.out.println(tail.getBuffer().toString());
	}

	public StringBuffer getBuffer() {
		try {
			Thread.sleep(1001);
		} catch (InterruptedException e) {
		}
		tailer.stopTailing();
		return buffer;
	}
}
