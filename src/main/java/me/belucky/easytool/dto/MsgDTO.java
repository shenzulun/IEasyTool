/**
 * File Name: MsgDTO.java
 * Date: 2019-10-22 16:25:30
 */
package me.belucky.easytool.dto;


/**
 * Description: 前后台交互DTO
 * @author shenzulun
 * @date 2019-10-22
 * @version 1.0
 */
public class MsgDTO implements java.io.Serializable{
	private static final long serialVersionUID = 3292126437812543072L;

	/**
	 * 请求方法
	 */
	private String method;
	
	/**
	 * 请求IP
	 */
	private String ip;
	/**
	 * 数据域
	 */
	private Object data;
	/**
	 * 返回耗时
	 */
	private long cost;
	/**
	 * 返回代码
	 * 000000：成功
	 * 其它失败
	 */
	private String code;
	/**
	 * 返回信息
	 */
	private String message;
	
	public MsgDTO() {}
	
	public MsgDTO(String method, String ip, Object data, long cost, String code, String message) {
		super();
		this.method = method;
		this.ip = ip;
		this.data = data;
		this.cost = cost;
		this.code = code;
		this.message = message;
	}

	public MsgDTO(Object data, String code, String message) {
		super();
		this.data = data;
		this.code = code;
		this.message = message;
	}
	
	public MsgDTO(Object data, String code) {
		super();
		this.data = data;
		this.code = code;
	}

	public MsgDTO(Object data) {
		super();
		this.data = data;
	}

	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public long getCost() {
		return cost;
	}
	public void setCost(long cost) {
		this.cost = cost;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
