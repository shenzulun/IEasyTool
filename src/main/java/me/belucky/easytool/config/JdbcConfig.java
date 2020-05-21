/**
 * File Name: JdbcConfig.java
 * Date: 2019-12-26 17:12:25
 */
package me.belucky.easytool.config;

/**
 * Description: 数据源配置类
 * @author shenzulun
 * @date 2019-12-26
 * @version 1.0
 */
public class JdbcConfig {

	private String url;
	
	private String driverClass;
	
	private String username;
	
	private String password;
	
	private String showSql;
	
	public JdbcConfig() {}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

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

	public String getShowSql() {
		return showSql;
	}

	public void setShowSql(String showSql) {
		this.showSql = showSql;
	}
	
	
}
