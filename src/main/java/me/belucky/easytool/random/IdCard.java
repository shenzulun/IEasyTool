/**
 * File Name: IdCard.java
 * Date: 2016-9-19 上午11:23:45
 */
package me.belucky.easytool.random;

import me.belucky.easytool.config.ConstCode;

/**
 * 功能说明: 身份信息
 * @author shenzl
 * @date 2016-9-19
 * @version 1.0
 */
public class IdCard implements java.io.Serializable{
	private static final long serialVersionUID = -461348276085043397L;

	private String name;
	
	private String certno;
	
	public IdCard(){}
	
	public IdCard(String name, String certno) {
		super();
		this.name = name;
		this.certno = certno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCertno() {
		return certno;
	}

	public void setCertno(String certno) {
		this.certno = certno;
	}
	
	@Override
	public String toString() {
		return new StringBuffer("name: ").append(name).append(ConstCode.LINE_BREAK).append("certno: ").append(certno).toString();
	}
	
}
