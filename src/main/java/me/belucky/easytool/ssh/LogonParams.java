/**
 * File Name: LogonParams.java
 * Date: 2016-12-23 下午04:43:53
 */
package me.belucky.easytool.ssh;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import me.belucky.easytool.config.ConstCode;
import me.belucky.easytool.util.CacheUtils;

/**
 * 功能说明: 登陆参数集
 * @author shenzl
 * @date 2016-7-5
 * @version 1.0
 */
public class LogonParams implements java.io.Serializable{
	private static final long serialVersionUID = -4733950130983785853L;

	private String host;
	
	private int port;
	
	private String username;
	
	private String passwd;
	
	public LogonParams(){}
	
	public LogonParams(String host, int port, String username, String passwd){
		this.host = host;
		this.port = port;
		this.username = username;
		this.passwd = passwd;
	}
	
	public LogonParams(String host, String username, String passwd){
		this.host = host;
		this.username = username;
		this.passwd = passwd;
	}
	
	public LogonParams initFromProp(){
		return initFromProp(null);
	}
	
	/**
	 * 从配置文件初始化
	 * @param propFileName
	 * @return
	 */
	public LogonParams initFromProp(String propFileName){
		Prop prop = PropKit.getProp();
		if(propFileName != null){
			prop = PropKit.getProp(propFileName);
		}
		boolean isCache = PropKit.getBoolean(ConstCode.IS_READ_FROM_CACHE);
		if(isCache){
			String key = prop.get(ConstCode.READ_CACHE_KEY);
			Object param = CacheUtils.getCache(key);
			if(param != null){
				return (LogonParams)param;
			}
		}
		this.host = prop.get(ConstCode.REMOTE_HOST);
		this.port = prop.getInt(ConstCode.REMOTE_PORT);
		this.username = prop.get(ConstCode.REMOTE_USERNAME);
		this.passwd = prop.get(ConstCode.REMOTE_PASSWD);
		if(isCache){
			String key = prop.get(ConstCode.READ_CACHE_KEY);
			CacheUtils.putCache(key, this);
		}
		return this;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	
}