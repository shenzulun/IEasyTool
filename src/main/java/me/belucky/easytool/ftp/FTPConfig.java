/**
 * File Name: FTPConfig.java
 * Date: 2019-08-23 16:55:01
 */
package me.belucky.easytool.ftp;

/**
 * Description: FTP配置信息
 * @author shenzulun
 * @date 2019-08-23
 * @version 1.0
 */
public class FTPConfig {

	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * IP地址
	 */
	private String host;
	/**
	 * 端口
	 */
	private int port;
	/**
	 * 协议类型
	 * 1: FTP
	 * 2: SFTP
	 */
	private int type;
	/**
	 * 私钥
	 */
	private String privateKey;
	/**
	 * 默认目录
	 */
	private String basepath;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getBasepath() {
		return basepath;
	}
	public void setBasepath(String basepath) {
		this.basepath = basepath;
	}
	
}
